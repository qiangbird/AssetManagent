package com.augmentum.ams.service.asset.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.Version;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.dao.todo.ToDoDao;
import com.augmentum.ams.exception.BusinessException;
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
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.remote.RemoteProjectService;
import com.augmentum.ams.service.search.impl.SearchAssetServiceImpl;
import com.augmentum.ams.service.user.SpecialRoleService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.FormatUtil;
import com.augmentum.ams.util.RoleLevelUtil;
import com.augmentum.ams.util.SearchFieldHelper;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.asset.ProjectVo;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
import com.augmentum.ams.web.vo.user.UserVo;

@Service("customerAssetService")
public class CustomerAssetServiceImpl implements CustomerAssetService {
    private static Logger logger = Logger.getLogger(SearchAssetServiceImpl.class);

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

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.hibernate.HibernateSearchService#searchCommon
     * (com.augmentum.ams.web.vo.system.Page)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<Asset> findCustomerAssetsBySearchCondition(SearchCondition searchCondition,
            String customerId) {

        // init base search columns and associate way
        String[] fieldNames = getSearchFieldNames(searchCondition.getSearchFields());
        Occur[] clauses = new Occur[fieldNames.length];

        for (int i = 0; i < fieldNames.length; i++) {
            clauses[i] = Occur.SHOULD;
        }

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(Asset.class).get();

        // create ordinary query, it contains search by keyword and field names
        BooleanQuery query = new BooleanQuery();

        String keyWord = searchCondition.getKeyWord();

        // if keyword is null or "", search condition is "*", it will search all
        // the value based on some one field
        if (null == keyWord || "".equals(keyWord) || "*".equals(keyWord)) {
            Query defaultQuery = new TermQuery(new Term("isExpired", Boolean.FALSE.toString()));
            query.add(defaultQuery, Occur.MUST);
        } else {

            keyWord = FormatUtil.formatKeyword(keyWord);

            // judge if keyword contains space, if yes, search keyword as a
            // sentence
            if (-1 != keyWord.indexOf(" ")) {
                String[] sentenceFields = SearchFieldHelper.getSentenceFields();
                query = getSentenceQuery(qb, sentenceFields, keyWord);
            } else {

                // if keyword doesn't contain space, search keyword as tokenized
                // index string

                BooleanQuery bq = new BooleanQuery();

                Query parseQuery = null;

                try {
                    parseQuery = MultiFieldQueryParser.parse(Version.LUCENE_30, keyWord,
                            fieldNames, clauses, new IKAnalyzer());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    logger.error("parse keyword error", e);
                }
                bq.add(parseQuery, Occur.SHOULD);

                for (int i = 0; i < fieldNames.length; i++) {
                    Query keyWordPrefixQuery = new PrefixQuery(new Term(fieldNames[i], keyWord));
                    bq.add(keyWordPrefixQuery, Occur.SHOULD);
                }

                query.add(bq, Occur.MUST);
            }
        }

        // create filter based on advanced search condition, it used for further
        // filtering query result
        BooleanQuery booleanQuery = new BooleanQuery();

        BooleanQuery statusQuery = getStatusQuery(searchCondition.getAssetStatus());
        BooleanQuery typeQuery = getTypeQuery(searchCondition.getAssetType());
        Query trq = getTimeRangeQuery(searchCondition.getFromTime(), searchCondition.getToTime());

        TermQuery termQery = new TermQuery(new Term("customer.id", customerId));

        booleanQuery
                .add(new TermQuery(new Term("isExpired", Boolean.FALSE.toString())), Occur.MUST);
        booleanQuery.add(statusQuery, Occur.MUST);
        booleanQuery.add(typeQuery, Occur.MUST);
        booleanQuery.add(trq, Occur.MUST);
        booleanQuery.add(termQery, Occur.MUST);

        QueryWrapperFilter filter = new QueryWrapperFilter(booleanQuery);

        // add entity associate
        Criteria criteria = session.createCriteria(Asset.class);
        criteria.setFetchMode("user", FetchMode.JOIN).setFetchMode("project", FetchMode.JOIN)
                .setFetchMode("location", FetchMode.JOIN).createAlias("customer", "customer");

        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        criteria.add(Restrictions.eq("customer.id", customerId));

        Page<Asset> page = new Page<Asset>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(transferSortName(searchCondition.getSortName()));

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, Asset.class)
                .setCriteriaQuery(criteria);

        if (null != searchCondition.getIsGetAllRecords() && searchCondition.getIsGetAllRecords()) {
            fullTextQuery.setFilter(filter);
            List<Asset> allRecords = fullTextQuery.list();
            page.setAllRecords(allRecords);
            return page;
        }

        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page, Asset.class);
        fullTextSession.close();
        return page;
    }

    private String[] getSearchFieldNames(String searchConditions) {
        String[] fieldNames = FormatUtil.splitString(searchConditions, SystemConstants.SPLIT_COMMA);

        if (null == fieldNames || 0 == fieldNames.length) {
            fieldNames = SearchFieldHelper.getAssetFields();
        }
        return fieldNames;
    }

    private BooleanQuery getSentenceQuery(QueryBuilder qb, String[] sentenceFields, String keyWord) {
        BooleanQuery sentenceQuery = new BooleanQuery();

        for (int i = 0; i < sentenceFields.length; i++) {
            Query query = qb.phrase().onField(sentenceFields[i]).sentence(keyWord).createQuery();
            sentenceQuery.add(query, Occur.SHOULD);
        }
        return sentenceQuery;
    }

    private BooleanQuery getStatusQuery(String status) {
        String[] statusConditions;
        BooleanQuery statusQuery = new BooleanQuery();

        if (null == status || "".equals(status)) {
            statusConditions = FormatUtil.splitString(SearchFieldHelper.getAssetStatus(),
                    SystemConstants.SPLIT_COMMA);
        } else {
            statusConditions = FormatUtil.splitString(status, SystemConstants.SPLIT_COMMA);
        }

        if (null != statusConditions && 0 < statusConditions.length) {

            for (int i = 0; i < statusConditions.length; i++) {
                statusQuery.add(new TermQuery(new Term("status", statusConditions[i])),
                        Occur.SHOULD);
            }
        }
        return statusQuery;
    }

    private BooleanQuery getTypeQuery(String type) {
        String[] typeConditions;
        BooleanQuery typeQuery = new BooleanQuery();

        if (null == type || "".equals(type)) {
            typeConditions = FormatUtil.splitString(SearchFieldHelper.getAssetType(),
                    SystemConstants.SPLIT_COMMA);
        } else {
            typeConditions = FormatUtil.splitString(type, SystemConstants.SPLIT_COMMA);
        }

        if (null != typeConditions && 0 < typeConditions.length) {

            for (int i = 0; i < typeConditions.length; i++) {
                typeQuery.add(new TermQuery(new Term("type", typeConditions[i])), Occur.SHOULD);
            }
        }
        return typeQuery;
    }

    private Query getTimeRangeQuery(String fromTime, String toTime) {
        boolean isNullFromTime = (null == fromTime || "".equals(fromTime));
        boolean isNullToTime = (null == toTime || "".equals(toTime));

        if (isNullFromTime && !isNullToTime) {
            fromTime = SystemConstants.SEARCH_MIN_DATE;
            toTime = UTCTimeUtil.formatFilterTime(toTime);
            return new TermRangeQuery("checkInTime", fromTime, toTime, true, true);
        } else if (isNullToTime && !isNullFromTime) {
            toTime = SystemConstants.SEARCH_MAX_DATE;
            fromTime = UTCTimeUtil.formatFilterTime(fromTime);
            return new TermRangeQuery("checkInTime", fromTime, toTime, true, true);
        } else if (!isNullFromTime && !isNullToTime) {
            fromTime = UTCTimeUtil.formatFilterTime(fromTime);
            toTime = UTCTimeUtil.formatFilterTime(toTime);
            return new TermRangeQuery("checkInTime", fromTime, toTime, true, true);
        } else {
            return new TermQuery(new Term("isExpired", Boolean.FALSE.toString()));
        }
    }

    private String transferSortName(String sortName) {

        if ("userName".equals(sortName)) {
            sortName = "user.userName_forSort";
        }
        return sortName;
    }

    @Override
    public List<Asset> findAssetsByCustomerCode(String customerCode) {
        Customer customer = customerService.getCustomerByCode(customerCode);
        List<Asset> customerAssetsList = assetService.findAssetsByCustomerId(customer.getId());
        return customerAssetsList;
    }

    @Override
    public List<Customer> findVisibleCustomerList(UserVo userVo, List<CustomerVo> list) {
        List<Customer> customerVisibleList = new ArrayList<Customer>();

        List<Customer> nonGroupCustomerList = new ArrayList<Customer>();

        // TODO: need code review

        boolean isSharedCustomer = false;

        for (CustomerVo cv : list) {

            // get customer from local database
            Customer customer = customerService.getCustomerByCode(cv.getCustomerCode());

            if (null != customer) {

                // check customer is shared
                if (null != customer.getCustomerGroup()
                        && customer.getCustomerGroup().getProcessType().equals(ProcessTypeEnum.SHARED.name())) {

                    isSharedCustomer = true;

                    // TODO: customer group using string not using enum
                    List<Customer> groupCustomerList = customerService.getCustomerByGroup(customer
                            .getCustomerGroup().getId());

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
                        || specialRoleService.findSpecialRoleByUserId(userVo.getEmployeeId())) {

                    nonGroupCustomerList.add(customer);
                }

            }
        }

        customerVisibleList.addAll(nonGroupCustomerList);

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
    public void takeOverCustomerAsset(String assetsId, String userCode, HttpServletRequest request) {
        String ids[] = assetsId.split(",");
        for (String id : ids) {
            Asset asset = assetService.getAsset(id);
            asset.setStatus(StatusEnum.IN_USE.toString());
            User user = userService.getUserByUserId(userCode);
            if (null == user) {
                UserVo userVo = null;
                try {
                    userVo = remoteEmployeeService.getRemoteUserById(userCode, request);
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
    public void assginCustomerAsset(String customerCode, String ids, String projectCode,
            String userName, String assetUserCode, HttpServletRequest request)
            throws BusinessException {
        String uuId[] = ids.split(",");
        Customer customer = customerService.getCustomerByCode(customerCode);
        Project project = projectService.getProjectByProjectCode(projectCode);
        User user = userService.getUserByName(userName);
        if (null == project) {
            ProjectVo projectVo = remoteProjectService
                    .getProjectByProjectCode(projectCode, request);
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

    @SuppressWarnings("unchecked")
    @Override
    public Page<Asset> findAllCustomerAssetBySearchCondition(SearchCondition searchCondition,
            List<Customer> customers) {

        String[] customerIds = new String[customers.size()];

        for (int i = 0; i < customers.size(); i++) {
            customerIds[i] = customers.get(i).getId();
        }

        // init base search columns and associate way
        String[] fieldNames = getSearchFieldNames(searchCondition.getSearchFields());
        Occur[] clauses = new Occur[fieldNames.length];

        for (int i = 0; i < fieldNames.length; i++) {
            clauses[i] = Occur.SHOULD;
        }

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(Asset.class).get();

        // create ordinary query, it contains search by keyword and field names
        BooleanQuery query = new BooleanQuery();

        String keyWord = searchCondition.getKeyWord();

        // if keyword is null or "", search condition is "*", it will search all
        // the value based on some one field
        if (null == keyWord || "".equals(keyWord) || "*".equals(keyWord)) {
            Query defaultQuery = new TermQuery(new Term("isExpired", Boolean.FALSE.toString()));
            query.add(defaultQuery, Occur.MUST);
        } else {

            keyWord = FormatUtil.formatKeyword(keyWord);

            // judge if keyword contains space, if yes, search keyword as a
            // sentence
            if (-1 != keyWord.indexOf(" ")) {
                String[] sentenceFields = SearchFieldHelper.getSentenceFields();
                query = getSentenceQuery(qb, sentenceFields, keyWord);
            } else {

                // if keyword doesn't contain space, search keyword as tokenized
                // index string

                BooleanQuery bq = new BooleanQuery();

                Query parseQuery = null;

                try {
                    parseQuery = MultiFieldQueryParser.parse(Version.LUCENE_30, keyWord,
                            fieldNames, clauses, new IKAnalyzer());
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    logger.error("parse keyword error", e);
                }
                bq.add(parseQuery, Occur.SHOULD);

                for (int i = 0; i < fieldNames.length; i++) {
                    Query keyWordPrefixQuery = new PrefixQuery(new Term(fieldNames[i], keyWord));
                    bq.add(keyWordPrefixQuery, Occur.SHOULD);
                }

                query.add(bq, Occur.MUST);
            }
        }

        // create filter based on advanced search condition, it used for further
        // filtering query result
        BooleanQuery booleanQuery = new BooleanQuery();

        BooleanQuery statusQuery = getStatusQuery(searchCondition.getAssetStatus());
        BooleanQuery typeQuery = getTypeQuery(searchCondition.getAssetType());
        Query trq = getTimeRangeQuery(searchCondition.getFromTime(), searchCondition.getToTime());

        booleanQuery
                .add(new TermQuery(new Term("isExpired", Boolean.FALSE.toString())), Occur.MUST);
        booleanQuery.add(getCustomerQuery(customerIds), Occur.MUST);
        booleanQuery.add(statusQuery, Occur.MUST);
        booleanQuery.add(typeQuery, Occur.MUST);
        booleanQuery.add(trq, Occur.MUST);

        QueryWrapperFilter filter = new QueryWrapperFilter(booleanQuery);

        // add entity associate
        Criteria criteria = session.createCriteria(Asset.class);
        criteria.setFetchMode("user", FetchMode.JOIN).setFetchMode("customer", FetchMode.JOIN)
                .setFetchMode("project", FetchMode.JOIN).setFetchMode("location", FetchMode.JOIN);

        Page<Asset> page = new Page<Asset>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(transferSortName(searchCondition.getSortName()));

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(query, Asset.class)
                .setCriteriaQuery(criteria);

        if (null != searchCondition.getIsGetAllRecords() && searchCondition.getIsGetAllRecords()) {
            fullTextQuery.setFilter(filter);
            List<Asset> allRecords = fullTextQuery.list();
            page.setAllRecords(allRecords);
            return page;
        }

        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page, Asset.class);
        fullTextSession.close();
        return page;

    }

    private BooleanQuery getCustomerQuery(String[] customerIds) {

        BooleanQuery customerQuery = new BooleanQuery();

        for (int i = 0; i < customerIds.length; i++) {
            TermQuery termQery = new TermQuery(new Term("customer.id", customerIds[i]));
            customerQuery.add(termQery, Occur.SHOULD);
        }
        return customerQuery;
    }

}
