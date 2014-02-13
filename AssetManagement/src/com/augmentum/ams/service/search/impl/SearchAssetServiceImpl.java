/**
 * 
 */
package com.augmentum.ams.service.search.impl;

import org.apache.commons.lang.StringUtils;
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

import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.service.customized.CustomizedViewItemService;
import com.augmentum.ams.service.search.SearchAssetService;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.FormatUtil;
import com.augmentum.ams.util.SearchFieldHelper;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

/**
 * @author Grylls.Xu
 * @time Sep 25, 2013 7:42:56 PM
 */
@Service("searchAssetService")
public class SearchAssetServiceImpl implements SearchAssetService {

    private static Logger logger = Logger.getLogger(SearchAssetServiceImpl.class);

    @Autowired
    private BaseHibernateDao<Asset> baseHibernateDao;

    @Autowired
    private CustomizedViewItemService customizedViewItemService;

    @Autowired
    protected SessionFactory sessionFactory;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.hibernate.HibernateSearchService#searchCommon
     * (com.augmentum.ams.web.vo.system.Page)
     */
    @Override
    public Page<Asset> findAllAssetsBySearchCondition(SearchCondition searchCondition)
            throws BaseException {
    	
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
        if (StringUtils.isBlank(keyWord)) {
            Query defaultQuery = new TermQuery(new Term("isExpired", Boolean.FALSE.toString()));
            query.add(defaultQuery, Occur.MUST);
        } else {

            keyWord = FormatUtil.formatKeyword(keyWord);

            // judge if keyword contains space, if yes, search keyword as a
            // sentence
            if (-1 != keyWord.indexOf(" ")) {
                query = getSentenceQuery(qb, fieldNames, keyWord);
            }
            BooleanQuery bq = new BooleanQuery();

            Query parseQuery = null;

            try {
                parseQuery = MultiFieldQueryParser.parse(Version.LUCENE_30, keyWord, fieldNames,
                        clauses, new IKAnalyzer());
            } catch (ParseException e) {
                logger.error("parse keyword error", e);
            }
            bq.add(parseQuery, Occur.SHOULD);

            for (int i = 0; i < fieldNames.length; i++) {

                Query keyWordPrefixQuery = new PrefixQuery(new Term(fieldNames[i], keyWord));
                bq.add(keyWordPrefixQuery, Occur.SHOULD);
            }

            query.add(bq, Occur.MUST);
        }

        // create filter based on advanced search condition, it used for further
        // filtering query result
        BooleanQuery booleanQuery = new BooleanQuery();

        if (!StringUtils.isBlank(searchCondition.getUserUuid())) {
            booleanQuery.add(new TermQuery(new Term("user.id", searchCondition.getUserUuid())),
                    Occur.MUST);
        }

        // If customizedViewId is not empty, only use the
        // customizedViewItemQuery
        if (null != searchCondition.getCustomizedViewId()
                && !"".equals(searchCondition.getCustomizedViewId())) {
            BooleanQuery customizedViewItemQuery = customizedViewItemService
                    .getCustomizedViewItemQuery(searchCondition.getCustomizedViewId());

            booleanQuery.add(customizedViewItemQuery, Occur.MUST);
        } else {
            BooleanQuery statusQuery = getStatusQuery(searchCondition.getAssetStatus());
            BooleanQuery typeQuery = getTypeQuery(searchCondition.getAssetType());
            Query trq = getTimeRangeQuery(searchCondition.getFromTime(),
                    searchCondition.getToTime());
            
            // get fixed assets list
            if (null != searchCondition.getIsFixedAsset() && searchCondition.getIsFixedAsset()) {
            	booleanQuery.add(new TermQuery(new Term("fixed", Boolean.TRUE.toString())),
                        Occur.MUST);
            }
            
            // get warranty expired asset list
            if (null != searchCondition.getIsWarrantyExpired() && searchCondition.getIsWarrantyExpired()) {
            	String fromTime = UTCTimeUtil.formatCurrentTimeForFilterTime();
            	String toTime = UTCTimeUtil.getAssetExpiredTimeForFilterTime();
            	booleanQuery.add(new TermRangeQuery("warrantyTime", fromTime, toTime, true, true), Occur.MUST);
            }
            
            // get license expired asset list
            if (null != searchCondition.getIsLicenseExpired() && searchCondition.getIsLicenseExpired()) {
            	String fromTime = UTCTimeUtil.formatCurrentTimeForFilterTime();
            	String toTime = UTCTimeUtil.getAssetExpiredTimeForFilterTime();
            	booleanQuery.add(new TermRangeQuery("software.softwareExpiredTime", fromTime, toTime, true, true), Occur.MUST);
            }
            

            booleanQuery.add(new TermQuery(new Term("isExpired", Boolean.FALSE.toString())),
                    Occur.MUST);
            booleanQuery.add(statusQuery, Occur.MUST);
            booleanQuery.add(typeQuery, Occur.MUST);
            booleanQuery.add(trq, Occur.MUST);
        }
        QueryWrapperFilter filter = new QueryWrapperFilter(booleanQuery);

        // add entity associate
        Criteria criteria = session.createCriteria(Asset.class)
                .setFetchMode("user", FetchMode.JOIN).setFetchMode("customer", FetchMode.JOIN)
                .setFetchMode("project", FetchMode.JOIN).setFetchMode("location", FetchMode.JOIN);

        if (!StringUtils.isBlank(searchCondition.getUserUuid())) {
        	criteria.createAlias("user", "user");
            criteria.add(Restrictions.eq("user.id", searchCondition.getUserUuid()));
        }
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        
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
    public void createIndex(Class<Asset>... classes) {
        try {
            baseHibernateDao.createIndex(classes);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

}
