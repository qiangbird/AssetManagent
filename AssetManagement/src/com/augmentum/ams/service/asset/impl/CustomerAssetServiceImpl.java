package com.augmentum.ams.service.asset.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.AssetDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.dao.todo.ToDoDao;
import com.augmentum.ams.excel.AssetTemplateParser;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.enumeration.ProcessTypeEnum;
import com.augmentum.ams.model.enumeration.StatusEnum;
import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.asset.CustomerAssetService;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.asset.ProjectService;
import com.augmentum.ams.service.customized.CustomizedViewItemService;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.remote.RemoteProjectService;
import com.augmentum.ams.service.search.impl.SearchAssetServiceImpl;
import com.augmentum.ams.service.user.SpecialRoleService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.CommonSearchUtil;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.RoleLevelUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.asset.ExportVo;
import com.augmentum.ams.web.vo.asset.ProjectVo;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
import com.augmentum.ams.web.vo.user.UserVo;

@Service("customerAssetService")
public class CustomerAssetServiceImpl implements CustomerAssetService {
    private static Logger logger = Logger
            .getLogger(SearchAssetServiceImpl.class);

    @Autowired
    private BaseHibernateDao<Asset> baseHibernateDao;
    @Autowired
    protected SessionFactory sessionFactory;
    @Autowired
    AssetService assetService;
    @Autowired
    CustomerService customerService;
    @Autowired
    private SpecialRoleService specialRoleService;
    @Autowired
    private UserService userService;
    @Autowired
    private RemoteEmployeeService remoteEmployeeService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private RemoteProjectService remoteProjectService;
    @Autowired
    private ToDoDao todoDao;
    @Autowired
    private AssetDao assetDao;
    @Autowired
    private CustomizedViewItemService customizedViewItemService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.hibernate.HibernateSearchService#searchCommon
     * (com.augmentum.ams.web.vo.system.Page)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Asset> findCustomerAssetsBySearchCondition(
            SearchCondition searchCondition, String[] customerIds) {

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(Asset.class).get();

        // create ordinary query, it contains search by keyword
        BooleanQuery keyWordQuery = CommonSearchUtil.searchByKeyWord(
                Asset.class, qb, searchCondition.getKeyWord(),
                searchCondition.getSearchFields());

        // create filter based on advanced search condition, it used for further
        // filtering query result
        BooleanQuery filterQuery = new BooleanQuery();

        // filter query by customer
        filterQuery.add(getCustomerQuery(customerIds), Occur.MUST);

        // If customizedViewId is not empty, only use the
        // customizedViewItemQuery
        if (null != searchCondition.getCustomizedViewId()
                && !"".equals(searchCondition.getCustomizedViewId())) {
            BooleanQuery customizedViewItemQuery = customizedViewItemService
                    .getCustomizedViewItemQuery(searchCondition
                            .getCustomizedViewId());

            filterQuery.add(customizedViewItemQuery, Occur.MUST);
        } else {
            
            CommonSearchUtil.addFilterQueryForAsset(searchCondition,
                    filterQuery, "checkInTime");
        }

        QueryWrapperFilter filter = new QueryWrapperFilter(filterQuery);

        // add entity associate
        Criteria criteria = session.createCriteria(Asset.class);
        criteria.setFetchMode("user", FetchMode.JOIN)
                .setFetchMode("project", FetchMode.JOIN)
                .setFetchMode("location", FetchMode.JOIN)
                .setFetchMode("customer", FetchMode.JOIN);

        Page<Asset> page = new Page<Asset>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(CommonSearchUtil.transferSortName(searchCondition
                .getSortName()));

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
                keyWordQuery, Asset.class).setCriteriaQuery(criteria);

        if (null != searchCondition.getIsGetAllRecords()
                && searchCondition.getIsGetAllRecords()) {

            fullTextQuery.setFilter(filter);
            List<Asset> allRecords = fullTextQuery.list();
            page.setAllRecords(allRecords);
            return page;
        }

        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page);
        fullTextSession.close();
        return page;
    }

    private BooleanQuery getCustomerQuery(String[] customerIds) {

        BooleanQuery customerQuery = new BooleanQuery();

        for (int i = 0; i < customerIds.length; i++) {
            TermQuery termQery = new TermQuery(new Term("customer.id",
                    customerIds[i]));
            customerQuery.add(termQery, Occur.SHOULD);
        }
        return customerQuery;
    }

    @Override
    public List<Asset> findAssetsByCustomerCode(String customerCode) {
        Customer customer = customerService.getCustomerByCode(customerCode);
        List<Asset> customerAssetsList = assetService
                .findAssetsByCustomerId(customer.getId());
        return customerAssetsList;
    }

    @Override
    public List<Customer> findVisibleCustomerList(UserVo userVo,
            List<CustomerVo> list) {
        List<Customer> customerVisibleList = new ArrayList<Customer>();

        List<Customer> nonGroupCustomerList = new ArrayList<Customer>();

        // TODO: need code review

        boolean isSharedCustomer = false;

        for (CustomerVo cv : list) {

            // get customer from local database
            Customer customer = customerService.getCustomerByCode(cv
                    .getCustomerCode());

            if (null != customer) {

                // check customer is shared
                if (null != customer.getCustomerGroup()
                        && customer.getCustomerGroup().getProcessType()
                                .equals(ProcessTypeEnum.SHARED.name())) {

                    isSharedCustomer = true;

                    List<Customer> groupCustomerList = customerService
                            .getCustomerByGroup(customer.getCustomerGroup()
                                    .getId());

                    customerVisibleList.addAll(groupCustomerList);

                }

            } else {
                customer = new Customer();

                customer.setCustomerName(cv.getCustomerName());
                customer.setCustomerCode(cv.getCustomerCode());
            }

            if (!isSharedCustomer) {

                // TODO check employee is not employee or leader and not special
                // role user
                if (RoleLevelUtil.checkEmployeeCanViewCustomerAssets(userVo)
                        || specialRoleService.findSpecialRoleByUserId(userVo
                                .getEmployeeId())) {

                    nonGroupCustomerList.add(customer);
                }

            }
        }

        customerVisibleList.addAll(nonGroupCustomerList);

        Set<Customer> customers = new HashSet<Customer>(customerVisibleList);
        customerVisibleList.clear();
        customerVisibleList.addAll(customers);

        return customerVisibleList;

    }

    @Override
    public void returnCustomerAsset(User returner, String status, String ids) {
        String assetId[] = ids.split(",");
        Date date = UTCTimeUtil.localDateToUTC();
        for (String id : assetId) {
            Asset asset = assetService.getAsset(id);
            asset.setStatus(status);
            asset.setUser(null);
            assetService.updateAsset(asset);

            // generate todo list for return to IT operation
            if ("RETURNING_TO_IT".equals(status)) {
                ToDo todo = new ToDo();
                todo.setAsset(asset);
                todo.setReturnedTime(date);
                todo.setReturner(returner);
                todoDao.save(todo);
            }
        }
    }

    @Override
    public void takeOverCustomerAsset(String assetsId, String userCode,
            HttpServletRequest request) {
        String ids[] = assetsId.split(",");
        for (String id : ids) {
            Asset asset = assetService.getAsset(id);
            asset.setStatus(StatusEnum.IN_USE.toString());
            User user = userService.getUserByUserId(userCode);
            if (null == user) {
                UserVo userVo = null;
                try {
                    userVo = remoteEmployeeService.getRemoteUserById(userCode,
                            request);
                } catch (BusinessException e) {
                    logger.error("Get user error from IAP", e);
                }
                userService.saveUserAsUserVo(userVo);
                user = userService.getUserByUserId(userCode);
            }
            asset.setUser(user);
            assetService.updateAsset(asset);
        }
    }

    @Override
    public void assginCustomerAsset(String customerCode, String ids,
            String projectCode, String userName, String assetUserCode,
            HttpServletRequest request) throws BusinessException {
        String uuId[] = ids.split(",");
        Customer customer = customerService.getCustomerByCode(customerCode);
        Project project = projectService.getProjectByProjectCode(projectCode);
        User user = userService.getUserByName(userName);
        if (null == project) {
            ProjectVo projectVo = remoteProjectService.getProjectByProjectCode(
                    projectCode, request);
            Project project2 = new Project();
            project2.setProjectName(projectVo.getProjectName());
            project2.setProjectCode(projectVo.getProjectCode());
            project2.setCustomer(customer);
            projectService.saveProject(project2);
            project = projectService.getProjectByProjectCode(projectCode);
        }
        if (null == user) {
            User user2 = new User();
            user2.setUserName(userName);
            user2.setUserId(assetUserCode);
            userService.saveUser(user2);
            user = userService.getUserByName(userName);
        }
        for (String id : uuId) {
            Asset asset = assetService.getAsset(id);
            asset.setProject(project);
            asset.setUser(user);
            asset.setStatus(StatusEnum.IN_USE.toString());
            assetService.updateAsset(asset);
        }
    }

    @Override
    public String exportAssetsForAll(SearchCondition condition,
            String[] customerIds, HttpServletRequest request)
            throws ExcelException, SQLException {

        Page<Asset> page = findCustomerAssetsBySearchCondition(condition,
                customerIds);

        if (null == page || null == page.getAllRecords()
                || 0 == page.getAllRecords().size()) {
            throw new ExcelException(ErrorCodeUtil.ASSET_EXPORT_FAILED,
                    "There is no asset when export customer assets for search result");
        } else {
            String[] assetIdArr = new String[page.getAllRecords().size()];

            for (int i = 0; i < page.getAllRecords().size(); i++) {
                if (null != page.getAllRecords().get(i)) {
                    assetIdArr[i] = page.getAllRecords().get(i).getId();
                } else {
                    throw new ExcelException(ErrorCodeUtil.ASSET_EXPORT_FAILED,
                            "The i'th asset is not exist when export customer assets for search result");
                }
            }
            List<ExportVo> exportVos = assetDao
                    .findAssetsByIdsForExport(assetIdArr);
            AssetTemplateParser assetTemplateParser = new AssetTemplateParser();

            return assetTemplateParser.parse(exportVos, request);
        }
    }
}
