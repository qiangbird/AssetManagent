package com.augmentum.ams.service.audit.impl;

import java.util.List;

import net.sf.json.JSONArray;

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

import com.augmentum.ams.dao.audit.AuditFileDao;
import com.augmentum.ams.dao.audit.InconsistentDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.model.audit.Inconsistent;
import com.augmentum.ams.service.audit.InconsistentService;
import com.augmentum.ams.service.customized.CustomizedViewItemService;
import com.augmentum.ams.util.CommonSearchUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Service("inconsistentService")
public class InconsistentServciceImpl implements InconsistentService {

    @Autowired
    private InconsistentDao inconsistentDao;

    @Autowired
    private AuditFileDao auditFileDao;

    @Autowired
    protected SessionFactory sessionFactory;

    @Autowired
    private CustomizedViewItemService customizedViewItemService;

    @Autowired
    private BaseHibernateDao<Inconsistent> baseHibernateDao;

    private Logger logger = Logger.getLogger(InconsistentServciceImpl.class);

    @Override
    public int getInconsistentAssetsCount(String auditFileName) {

        AuditFile auditFile = auditFileDao
                .getAuditFileByFileName(auditFileName);

        if (null == auditFile) {
            return 0;
        }

        return inconsistentDao
                .findInconsistentAssetsByFileId(auditFile.getId()).size();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.audit.InconsistentService#findInconsistentAssets
     * (java.lang.String, int, int)
     */
    @Override
    public JSONArray findInconsistentAssets(String auditFileName,
            int iDisplayStart, int iDisplayLength) {

        JSONArray arrays = new JSONArray();

        List<Inconsistent> inconsistentList = inconsistentDao
                .findInconsistentList(auditFileName, iDisplayStart,
                        iDisplayLength);

        for (int i = 0; i < inconsistentList.size(); i++) {
            JSONArray array = new JSONArray();
            Inconsistent incons = inconsistentList.get(i);

            if (null != incons.getAsset()) {
                array.add(i + 1 + iDisplayStart);
                if (null == incons.getAsset().getBarCode()) {
                    array.add("");
                } else {
                    array.add(incons.getAsset().getBarCode());
                }
                array.add(incons.getAsset().getAssetName());
                array.add(incons.getAsset().getType());
            } else {
                array.add(i + 1 + iDisplayStart);
                array.add(incons.getBarCode());
                array.add("");
                array.add("");
            }
            arrays.add(array);
        }
        return arrays;
    }

    // TODO refine search code
    // ------------------------------------------------------------------------
    @Override
    public Page<Asset> findAssetForInconsistent(SearchCondition searchCondition)
            throws BaseException {

        Page<Asset> page = new Page<Asset>();
        return page;
    }

    @Override
    public Page<Inconsistent> findInconsistentBySearchCondition(
            SearchCondition searchCondition, String auditFileId)
            throws BaseException {

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(Inconsistent.class).get();

        // create ordinary query, it contains search by keyword
        BooleanQuery keyWordQuery = CommonSearchUtil.searchByKeyWord(
                Inconsistent.class, qb, searchCondition.getKeyWord(),
                searchCondition.getSearchFields());

        // create filter based on advanced search condition, it used for further
        // filtering query result
        BooleanQuery filterQuery = new BooleanQuery();

        filterQuery.add(new TermQuery(new Term("auditFile.id", auditFileId)),
                Occur.MUST);

        // If customizedViewId is not empty, only use the
        // customizedViewItemQuery
        if (null != searchCondition.getCustomizedViewId()
                && !"".equals(searchCondition.getCustomizedViewId())) {
            BooleanQuery customizedViewItemQuery = customizedViewItemService
                    .getCustomizedViewItemQuery(qb, searchCondition
                            .getCustomizedViewId());

            filterQuery.add(customizedViewItemQuery, Occur.MUST);
        } else {

            BooleanQuery booleanQuery = CommonSearchUtil.addFilterQueryForAsset(
                    searchCondition, "asset.checkInTime", Inconsistent.class);
            if (null != booleanQuery) {
                
                filterQuery.add(CommonSearchUtil.addFilterQueryForAsset(
                        searchCondition, "asset.checkInTime", Inconsistent.class), Occur.MUST);
            }
        }
        QueryWrapperFilter filter = new QueryWrapperFilter(filterQuery);

        // add entity associate
        Criteria criteria = session.createCriteria(Inconsistent.class)
                .setFetchMode("asset", FetchMode.JOIN)
                .setFetchMode("asset.user", FetchMode.JOIN)
                .setFetchMode("asset.customer", FetchMode.JOIN)
                .setFetchMode("asset.project", FetchMode.JOIN)
                .setFetchMode("asset.location", FetchMode.JOIN);

        Page<Inconsistent> page = new Page<Inconsistent>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(CommonSearchUtil.transferSortName(searchCondition
                .getSortName()));

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
                keyWordQuery, Inconsistent.class).setCriteriaQuery(criteria);

        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page);
        fullTextSession.close();
        return page;
    }

}
