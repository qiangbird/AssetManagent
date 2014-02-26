package com.augmentum.ams.service.asset.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.TermQuery;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
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

import com.augmentum.ams.dao.asset.TransferLogDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.TransferLog;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.asset.TransferLogService;
import com.augmentum.ams.util.CommonSearchUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Service("transferLogService")
public class TransferLogServiceImpl implements TransferLogService {

    Logger logger = Logger.getLogger(TransferLogServiceImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private BaseHibernateDao<TransferLog> baseHibernateDao;
    @Autowired
    private TransferLogDao transferLogDao;
    @Autowired
    private AssetService assetService;

    @Override
    public Page<TransferLog> findTransferLogBySearchCondition(
            SearchCondition searchCondition, String id) {

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(TransferLog.class).get();

        // create ordinary query, it contains search by keyword
        BooleanQuery keyWordQuery = CommonSearchUtil.searchByKeyWord(
                TransferLog.class, qb, searchCondition.getKeyWord(),
                searchCondition.getSearchFields());

        // create filter based on advanced search condition, it used for further
        // filtering query result
        BooleanQuery filterQuery = null;

        Query timeQuery = CommonSearchUtil.searchByTimeRangeQuery(
                "time", searchCondition.getFromTime(),
                searchCondition.getToTime());
        
        if (null != timeQuery) {
            if (null == filterQuery) {
                filterQuery = new BooleanQuery();
            }
            filterQuery.add(timeQuery, Occur.MUST);
        }
        
        if (StringUtils.isNotBlank(id)) {
            if (null == filterQuery) {
                filterQuery = new BooleanQuery();
            }
            filterQuery.add(new TermQuery(new Term("asset.id", id)), Occur.MUST);
        }
        
        QueryWrapperFilter filter = null;
        
        if (null != filterQuery) {
            filter = new QueryWrapperFilter(filterQuery);
        }

        // add entity associate
        Criteria criteria = session.createCriteria(TransferLog.class);
        criteria.setFetchMode("user", FetchMode.JOIN)
                .setFetchMode("asset", FetchMode.JOIN);

        Page<TransferLog> page = new Page<TransferLog>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(CommonSearchUtil.transferSortName(searchCondition.getSortName()));

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
                keyWordQuery, TransferLog.class).setCriteriaQuery(criteria);
        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page);
        fullTextSession.close();
        return page;
    }

    @Override
    public void saveTransferLog(String assetIds, String action) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getSession().getAttribute("currentUser");
        String ids[] = assetIds.split(",");
        Date date = UTCTimeUtil.localDateToUTC();
        for (String id : ids) {
            Asset asset = assetService.getAsset(id);
            TransferLog transferLog = new TransferLog();
            transferLog.setAsset(asset);
            transferLog.setUser(user);
            transferLog.setAction(action);
            transferLog.setTime(date);
            transferLogDao.save(transferLog);
        }

    }

    @Override
    public List<TransferLog> findAllTransferLog() {
        return transferLogDao.findAll(TransferLog.class);
    }

    @Override
    public void createIndexForTransferLog(Class<TransferLog>... classes) {
        try {
            baseHibernateDao.createIndex(classes);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }
}
