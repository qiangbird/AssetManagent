package com.augmentum.ams.service.operationLog.impl;

import org.apache.log4j.Logger;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.QueryWrapperFilter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.dao.operationLog.OperationLogDao;
import com.augmentum.ams.model.operationLog.OperationLog;
import com.augmentum.ams.service.customized.CustomizedViewItemService;
import com.augmentum.ams.service.operationLog.OperationLogService;
import com.augmentum.ams.util.CommonSearchUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService {

    Logger logger = Logger.getLogger(OperationLogServiceImpl.class);
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private OperationLogDao operationLogDao;
    @Autowired
    private BaseHibernateDao<OperationLog> baseHibernateDao;
    @Autowired
    private CustomizedViewItemService customizedViewItemService;

    @Override
    public void save(OperationLog operationLog) {
        operationLogDao.save(operationLog);

    }

    @Override
    public Page<OperationLog> findOperationLogBySearchCondition(
            SearchCondition searchCondition) {

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);

        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(OperationLog.class).get();

     // create ordinary query, it contains search by keyword
        BooleanQuery keyWordQuery = CommonSearchUtil.searchByKeyWord(
                OperationLog.class, qb, searchCondition.getKeyWord(),
                searchCondition.getSearchFields());
        
        // create filter based on advanced search condition, it used for further
        // filtering query result
        BooleanQuery filterQuery = null;
        
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
            
            Query timeQuery = CommonSearchUtil.searchByTimeRangeQuery(
                    "createdTime", searchCondition.getFromTime(),
                    searchCondition.getToTime());
            
            if (null != timeQuery) {
                filterQuery = new BooleanQuery();
                filterQuery.add(timeQuery, Occur.MUST);
            }
        }

        QueryWrapperFilter filter = null;
        
        if (null != filterQuery) {
            filter = new QueryWrapperFilter(filterQuery);
        }

        // add entity associate
        Criteria criteria = session.createCriteria(OperationLog.class);

        Page<OperationLog> page = new Page<OperationLog>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(searchCondition.getSortName());

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
                keyWordQuery, OperationLog.class).setCriteriaQuery(criteria);
        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page);
        fullTextSession.close();
        return page;
    }
}
