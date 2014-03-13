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
import com.augmentum.ams.model.enumeration.AssetTypeEnum;
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
            SearchCondition searchCondition, String type) throws BaseException {

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(Asset.class).get();

        // create ordinary query, it contains search by keyword
        BooleanQuery keyWordQuery = CommonSearchUtil.searchAssetByKeyWord(
                type, qb, searchCondition.getKeyWord(),
                searchCondition.getSearchFields());

        // create filter based on advanced search condition, it used for further
        // filtering query result
        BooleanQuery filterQuery = null;

        if (!StringUtils.isBlank(searchCondition.getUserUuid())) {

            if (null == filterQuery) {
                filterQuery = new BooleanQuery();
            }
            filterQuery.add(
                    new TermQuery(new Term("user.id", searchCondition
                            .getUserUuid())), Occur.MUST);
        }

        // get fixed assets list
        if (null != searchCondition.getIsFixedAsset()
                && searchCondition.getIsFixedAsset()) {

            if (null == filterQuery) {
                filterQuery = new BooleanQuery();
            }
            filterQuery.add(
                    new TermQuery(new Term("fixed", Boolean.TRUE.toString())),
                    Occur.MUST);
        }

        // get warranty expired asset list
        if (null != searchCondition.getIsWarrantyExpired()
                && searchCondition.getIsWarrantyExpired()) {
            String fromTime = UTCTimeUtil.formatCurrentTimeForFilterTime();
            String toTime = UTCTimeUtil.getAssetExpiredTimeForFilterTime();

            if (null == filterQuery) {
                filterQuery = new BooleanQuery();
            }
            filterQuery.add(new TermRangeQuery("warrantyTime", fromTime,
                    toTime, true, true), Occur.MUST);
        }

        // If customizedViewId is not empty, only use the
        // customizedViewItemQuery
        if (null != searchCondition.getCustomizedViewId()
                && !"".equals(searchCondition.getCustomizedViewId())) {
            BooleanQuery customizedViewItemQuery = customizedViewItemService
                    .getCustomizedViewItemQuery(qb, searchCondition
                            .getCustomizedViewId());

            if (null == filterQuery) {
                filterQuery = new BooleanQuery();
            }
            filterQuery.add(customizedViewItemQuery, Occur.MUST);
        } else {
            
            BooleanQuery booleanQuery = CommonSearchUtil.addFilterQueryForAsset(
                    searchCondition, "checkInTime", Asset.class);
            if (null != booleanQuery) {
                
                if (null == filterQuery) {
                    filterQuery = new BooleanQuery();
                }
                filterQuery.add(booleanQuery, Occur.MUST);
            }
        }

        QueryWrapperFilter filter = null;

        if (null != filterQuery) {
            filter = new QueryWrapperFilter(filterQuery);
        }

        // add entity associate
        Criteria criteria = session.createCriteria(Asset.class)
                .setFetchMode("user", FetchMode.JOIN)
                .setFetchMode("customer", FetchMode.JOIN)
                .setFetchMode("project", FetchMode.JOIN)
                .setFetchMode("location", FetchMode.JOIN);
        
        if (AssetTypeEnum.MACHINE.name().equalsIgnoreCase(type)) {
            
            criteria.setFetchMode("machine", FetchMode.JOIN);
        } else if (AssetTypeEnum.MONITOR.name().equalsIgnoreCase(type)) {
            
            criteria.setFetchMode("monitor", FetchMode.JOIN);
        } else if (AssetTypeEnum.DEVICE.name().equalsIgnoreCase(type)) {
            
            criteria.setFetchMode("device", FetchMode.JOIN)
                    .setFetchMode("device.deviceSubtype", FetchMode.JOIN);
        } else if (AssetTypeEnum.SOFTWARE.name().equalsIgnoreCase(type)) {
            
            criteria.setFetchMode("software", FetchMode.JOIN);
        } else if (AssetTypeEnum.OTHERASSETS.name().equalsIgnoreCase(type)) {
            
            criteria.setFetchMode("otherAssets", FetchMode.JOIN);
        }

        Page<Asset> page = new Page<Asset>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(searchCondition.getSortName());

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
