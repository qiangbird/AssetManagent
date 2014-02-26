/**
 * 
 */
package com.augmentum.ams.service.search.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
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

import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.service.customized.CustomizedViewItemService;
import com.augmentum.ams.service.search.SearchAssetService;
import com.augmentum.ams.util.CommonSearchUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

/**
 * @author Grylls.Xu
 * @time Sep 25, 2013 7:42:56 PM
 */
@Service("searchAssetService")
public class SearchAssetServiceImpl implements SearchAssetService {

    private static Logger logger = Logger
            .getLogger(SearchAssetServiceImpl.class);

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
    @SuppressWarnings("unchecked")
    @Override
    public Page<Asset> findAllAssetsBySearchCondition(
            SearchCondition searchCondition) throws BaseException {

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

        if (!StringUtils.isBlank(searchCondition.getUserUuid())) {
            filterQuery.add(
                    new TermQuery(new Term("user.id", searchCondition
                            .getUserUuid())), Occur.MUST);
        }

        // get fixed assets list
        if (null != searchCondition.getIsFixedAsset()
                && searchCondition.getIsFixedAsset()) {
            filterQuery.add(
                    new TermQuery(new Term("fixed", Boolean.TRUE.toString())),
                    Occur.MUST);
        }

        // get warranty expired asset list
        if (null != searchCondition.getIsWarrantyExpired()
                && searchCondition.getIsWarrantyExpired()) {
            String fromTime = UTCTimeUtil.formatCurrentTimeForFilterTime();
            String toTime = UTCTimeUtil.getAssetExpiredTimeForFilterTime();
            filterQuery.add(new TermRangeQuery("warrantyTime", fromTime,
                    toTime, true, true), Occur.MUST);
        }

        // If customizedViewId is not empty, only use the
        // customizedViewItemQuery
        if (null != searchCondition.getCustomizedViewId()
                && !"".equals(searchCondition.getCustomizedViewId())) {
            BooleanQuery customizedViewItemQuery = customizedViewItemService
                    .getCustomizedViewItemQuery(searchCondition
                            .getCustomizedViewId());

            filterQuery.add(customizedViewItemQuery, Occur.MUST);
        } else {
            BooleanQuery statusQuery = CommonSearchUtil
                    .searchByAssetStatus(searchCondition.getAssetStatus());
            BooleanQuery typeQuery = CommonSearchUtil
                    .searchByAssetType(searchCondition.getAssetType());
            Query checkInTimeQuery = CommonSearchUtil.searchByTimeRangeQuery(
                    "checkInTime", searchCondition.getFromTime(),
                    searchCondition.getToTime());

            filterQuery.add(statusQuery, Occur.MUST);
            filterQuery.add(typeQuery, Occur.MUST);

            if (null != checkInTimeQuery) {
                filterQuery.add(checkInTimeQuery, Occur.MUST);
            }
        }
        QueryWrapperFilter filter = new QueryWrapperFilter(filterQuery);

        // add entity associate
        Criteria criteria = session.createCriteria(Asset.class)
                .setFetchMode("user", FetchMode.JOIN)
                .setFetchMode("customer", FetchMode.JOIN)
                .setFetchMode("project", FetchMode.JOIN)
                .setFetchMode("location", FetchMode.JOIN);

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

    @Override
    public void createIndex(Class<Asset>... classes) {
        try {
            baseHibernateDao.createIndex(classes);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

}
