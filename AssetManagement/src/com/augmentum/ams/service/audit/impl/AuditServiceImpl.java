package com.augmentum.ams.service.audit.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
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

import com.augmentum.ams.dao.audit.AuditDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.exception.AuditHandleException;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.audit.Audit;
import com.augmentum.ams.service.audit.AuditService;
import com.augmentum.ams.service.customized.CustomizedViewItemService;
import com.augmentum.ams.util.CommonSearchUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Service("auditService")
public class AuditServiceImpl implements AuditService {

    private Logger logger = Logger.getLogger(AuditServiceImpl.class);

    @Autowired
    private AuditDao auditDao;

    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    private BaseHibernateDao<Asset> baseHibernateDao;

    @Autowired
    private CustomizedViewItemService customizedViewItemService;

    @Override
    public int calculatePercentageByFile(String auditFileName)
            throws AuditHandleException {

        int percentage = 0;
        List<Audit> auditLists = null;
        try {
            auditLists = auditDao.findByFileName(auditFileName);
            List<Audit> auditedLists = new ArrayList<Audit>();
            for (Audit audit : auditLists) {
                if (audit.getStatus() == true) {
                    auditedLists.add(audit);
                }
            }
            logger.info("All Audit:" + auditLists.size());
            logger.info("Audited Asset:" + auditedLists.size());
            if (auditedLists.size() == 0) {
                percentage = 0;
            } else {
                percentage = 394 * auditedLists.size() / auditLists.size();

            }
            return percentage;

        } catch (Exception e) {
            throw new AuditHandleException("Calculate Percentage Failure!");
        }
    }

    @Override
    public int getUnAuditedCount(String auditFileName) {

        return auditDao.findUnAuditedAssets(auditFileName).size();
    }

    @Override
    public int getAuditedCount(String auditFileName) {

        return auditDao.findAuditedAssets(auditFileName).size();
    }

    @Override
    public JSONArray findAudited(String auditFileName, int iDisplayStart,
            int iDisplayLength) {

        List<Asset> auditedAssets = auditDao.findAuditedAssets(auditFileName,
                iDisplayStart, iDisplayLength);
        JSONArray arrays = new JSONArray();

        for (int i = 0; i < auditedAssets.size(); i++) {
            JSONArray array = new JSONArray();
            // Audit audit = auditedAssets.get(i);
            array.add(i + 1 + iDisplayStart);
            if (auditedAssets.get(i).getBarCode() == null) {
                array.add("");
            } else {
                array.add(auditedAssets.get(i).getBarCode());
            }
            array.add(auditedAssets.get(i).getAssetName());
            array.add(auditedAssets.get(i).getType());
            arrays.add(array);
        }
        return arrays;
    }

    @Override
    public JSONArray findUnAudited(String auditFileName, int iDisplayStart,
            int iDisplayLength) {

        List<Asset> unAuditedAssets = auditDao.findUnAuditedAssets(
                auditFileName, iDisplayStart, iDisplayLength);
        JSONArray arrays = new JSONArray();

        for (int i = 0; i < unAuditedAssets.size(); i++) {
            JSONArray array = new JSONArray();
            // Audit audit = unAuditedAssets.get(i);
            array.add(i + 1 + iDisplayStart);
            if (unAuditedAssets.get(i).getBarCode() == null) {
                array.add("");
            } else {
                array.add(unAuditedAssets.get(i).getBarCode());
            }
            array.add(unAuditedAssets.get(i).getAssetName());
            array.add(unAuditedAssets.get(i).getType());
            arrays.add(array);
        }
        return arrays;
    }

    @Override
    public int getAuditPercentage(String auditFileName) {

        int percentage = 0;
        List<Audit> auditLists = auditDao.findByFileName(auditFileName);
        List<Audit> auditedLists = new ArrayList<Audit>();

        for (Audit audit : auditLists) {
            if (audit.getStatus()) {
                auditedLists.add(audit);
            }
        }
        logger.info("All Audit:" + auditLists.size());
        logger.info("Audited Asset:" + auditedLists.size());

        if (0 == auditedLists.size()) {
            percentage = 0;
        } else {
            percentage = 100 * auditedLists.size() / auditLists.size();

        }
        return percentage;
    }

    @Override
    public Page<Asset> findAssetForInventory(SearchCondition searchCondition)
            throws BaseException {

        // get asset UUID based on isAudited status and audit file name
        Boolean boo = Boolean.TRUE;
        if ("audited".equals(searchCondition.getAuditFlag())) {
            boo = Boolean.TRUE;
        } else if ("unaudited".equals(searchCondition.getAuditFlag())) {
            boo = Boolean.FALSE;
        }
        List<String> assetIds = auditDao.findInventoryAssetId(boo,
                searchCondition.getAuditFileName());

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

        // filter asset by asset Id
        filterQuery.add(getAssetIdQuery(assetIds), Occur.MUST);

        // If customizedViewId is not empty, only use the
        // customizedViewItemQuery
        if (null != searchCondition.getCustomizedViewId()
                && !"".equals(searchCondition.getCustomizedViewId())) {
            BooleanQuery customizedViewItemQuery = customizedViewItemService
                    .getCustomizedViewItemQuery(searchCondition
                            .getCustomizedViewId());

            filterQuery.add(customizedViewItemQuery, Occur.MUST);
        } else {
            BooleanQuery statusQuery = CommonSearchUtil.searchByAssetStatus(
                    searchCondition.getAssetStatus(), Asset.class);
            BooleanQuery typeQuery = CommonSearchUtil.searchByAssetType(
                    searchCondition.getAssetType(), Asset.class);
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
        page.setSortColumn(searchCondition.getSortName());

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
                keyWordQuery, Asset.class).setCriteriaQuery(criteria);
        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page);
        fullTextSession.close();
        return page;
    }

    private BooleanQuery getAssetIdQuery(List<String> assetIds) {

        BooleanQuery assetQuery = new BooleanQuery();

        for (int i = 0; i < assetIds.size(); i++) {
            assetQuery.add(new TermQuery(new Term("id", assetIds.get(i))),
                    Occur.SHOULD);
        }
        return assetQuery;
    }
}
