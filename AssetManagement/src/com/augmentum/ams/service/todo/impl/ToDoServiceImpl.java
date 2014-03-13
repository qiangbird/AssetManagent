package com.augmentum.ams.service.todo.impl;

import java.util.List;

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

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.dao.todo.ToDoDao;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.todo.ToDoService;
import com.augmentum.ams.util.CommonSearchUtil;
import com.augmentum.ams.util.FormatUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Service("toDoService")
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    private ToDoDao todoDao;
    @Autowired
    private BaseDao<ToDo> baseDao;
    @Autowired
    private BaseHibernateDao<ToDo> baseHibernateDao;

    @Autowired
    protected SessionFactory sessionFactory;

    private Logger logger = Logger.getLogger(ToDoServiceImpl.class);

    @Override
    public List<ToDo> findReturnedAsset() {
        return todoDao.findReturnedAsset();
    }

    @Override
    public void confirmReturnedAndReceivedAsset(String ids, String status) {

        logger.info("enter confirmReturnedAndReceivedAsset method successfully, parameters [ids], [status]: "
                + ids + "," + status);
        String[] idArr = FormatUtil.splitString(ids,
                SystemConstants.SPLIT_COMMA);

        for (String id : idArr) {

            ToDo todo = todoDao.getToDoById(id);
            Asset asset = todo.getAsset();

            if (null != asset) {
                asset.setStatus(status);
                baseDao.update(todo);
            }
            baseDao.delete(todo);
        }
        logger.info("leave confirmReturnedAndReceivedAsset method successfully");
    }

    @Override
    public List<ToDo> findReceivedAsset(User user) {
        return todoDao.findReceivedAsset(user);
    }

    @Override
    public Page<ToDo> findToDoListBySearchCondition(
            SearchCondition searchCondition, User user) {

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity(ToDo.class).get();

        // create ordinary query, it contains search by keyword
        BooleanQuery keyWordQuery = CommonSearchUtil.searchByKeyWord(
                ToDo.class, qb, searchCondition.getKeyWord(),
                searchCondition.getSearchFields());

        // create filter based on advanced search condition, it used for further
        // filtering query result
        BooleanQuery filterQuery = new BooleanQuery();

        // if user is null, current todo list flag is: returned assets
        // else todo list is received assets
        if (null == user) {

            filterQuery.add(new TermQuery(new Term("receivedTime", SystemConstants.MAX_DATE_INDEX)),
                            Occur.MUST);
            searchCondition.setSortName("returnedTime");
            
            Query returnedTimeQuery = CommonSearchUtil.searchByTimeRangeQuery(
                    "returnedTime", searchCondition.getFromTime(),
                    searchCondition.getToTime());

            if (null != returnedTimeQuery) {
                filterQuery.add(returnedTimeQuery, Occur.MUST);
            }
        } else {

            filterQuery.add(new TermQuery(new Term("returnedTime", SystemConstants.MAX_DATE_INDEX)),
                            Occur.MUST);
            searchCondition.setSortName("receivedTime");

            filterQuery.add(
                    new TermQuery(new Term("asset.user.id", user.getId())),
                    Occur.MUST);
            
            Query receivedTimeQuery = CommonSearchUtil.searchByTimeRangeQuery(
                    "receivedTime", searchCondition.getFromTime(),
                    searchCondition.getToTime());

            if (null != receivedTimeQuery) {
                filterQuery.add(receivedTimeQuery, Occur.MUST);
            }
        }

        QueryWrapperFilter filter = new QueryWrapperFilter(filterQuery);

        // add entity associate
        Criteria criteria = session.createCriteria(ToDo.class)
                .setFetchMode("asset", FetchMode.JOIN)
                .setFetchMode("returner", FetchMode.JOIN)
                .setFetchMode("assigner", FetchMode.JOIN)
                .setFetchMode("asset.customer", FetchMode.JOIN)
                .setFetchMode("asset.project", FetchMode.JOIN)
                .setFetchMode("asset.user", FetchMode.JOIN);

        Page<ToDo> page = new Page<ToDo>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(searchCondition.getSortName());

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
                keyWordQuery, ToDo.class).setCriteriaQuery(criteria);

        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page);
        fullTextSession.close();
        return page;

    }

}
