package com.augmentum.ams.service.asset.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.BooleanClause.Occur;
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

import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.enumeration.ProcessTypeEnum;
import com.augmentum.ams.model.enumeration.StatusEnum;
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
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.FormatUtil;
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

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.hibernate.HibernateSearchService#searchCommon
     * (com.augmentum.ams.web.vo.system.Page)
     */
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
        criteria.setFetchMode("user", FetchMode.JOIN).setFetchMode("customer", FetchMode.JOIN)
                .setFetchMode("project", FetchMode.JOIN).setFetchMode("location", FetchMode.JOIN)
                .createAlias("customer", "customer");
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
        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page, Asset.class);
        fullTextSession.close();
        return page;
    }

    private String[] getSearchFieldNames(String searchConditions) {
        String[] fieldNames = FormatUtil.splitString(searchConditions, Constant.SPLIT_COMMA);

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
                    Constant.SPLIT_COMMA);
        } else {
            statusConditions = FormatUtil.splitString(status, Constant.SPLIT_COMMA);
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
                    Constant.SPLIT_COMMA);
        } else {
            typeConditions = FormatUtil.splitString(type, Constant.SPLIT_COMMA);
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
            fromTime = Constant.SEARCH_MIN_DATE;
            toTime = UTCTimeUtil.formatFilterTime(toTime);
            return new TermRangeQuery("checkInTime", fromTime, toTime, true, true);
        } else if (isNullToTime && !isNullFromTime) {
            toTime = Constant.SEARCH_MAX_DATE;
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
            sortName = "user.userName";
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
        boolean flag = false;
        if (null != list) {
            for (CustomerVo cv : list) {
                Customer customer = null;
                customer = customerService.getCustomerByCode(cv.getCustomerCode());
                if (null != customer) {
                    if (null != customer.getCustomerGroup()) {
                        flag = true;
                        if (customer.getCustomerGroup().getProcessType() == ProcessTypeEnum.SHARED) {

                            List<Customer> groupCustomerList = customerService
                                    .getCustomerByGroup(customer.getCustomerGroup().getId());
                            customerVisibleList.addAll(groupCustomerList);
                            // customerVisibleList.add(customer);
                        }
                        if (customer.getCustomerGroup().getProcessType() == ProcessTypeEnum.NONSHARE) {
                            if (userVo.getEmployeeLevel().equals("Manager")
                                    || userVo.getEmployeeLevel().equals("Director")
                                    || specialRoleService.findSpecialRoleByUserId(userVo
                                            .getEmployeeId())) {
                                customerVisibleList.add(customer);
                            }

                        }
                    } else {
                        flag = true;
                        customerVisibleList.add(customer);
                    }
                }
            }
            if (flag) {
                return customerVisibleList;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    @Override
    public void returnCustomerAsset(String status, String ids) {
        String assetId[] = ids.split(",");
        for (String id : assetId) {
            Asset asset = assetService.getAsset(id);
            asset.setStatus(status);
            asset.setUser(null);
            assetService.updateAsset(asset);
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
                } catch (DataException e) {
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
            String userName, String assetUserCode, HttpServletRequest request) throws DataException {
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

	@Override
	public List<Asset> getIdleCustomerAsset(List<Customer> customers) {
//		Page<Asset> page = findCustomerAssetsBySearchCondition(searchCondition, customerId);
		return null;
	}
    
    

}
