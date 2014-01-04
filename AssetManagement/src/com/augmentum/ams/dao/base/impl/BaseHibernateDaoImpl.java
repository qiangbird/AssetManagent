/**
 * 
 */
package com.augmentum.ams.dao.base.impl;

import java.util.List;
import java.util.Locale;

import org.apache.lucene.search.QueryWrapperFilter;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.web.vo.system.Page;

/**
 * @author Grylls.Xu
 * @time Sep 25, 2013 4:14:46 PM
 */
@Component
public class BaseHibernateDaoImpl<T extends BaseModel> implements BaseHibernateDao<T> {

    @Autowired
    protected SessionFactory sessionFactory;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.base.BaseHibernateDao#findByIndex(org.apache.lucene
     * .search.Query, org.apache.lucene.search.QueryWrapperFilter,
     * com.augmentum.ams.web.vo.system.Page, java.lang.Class)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<T> findByIndex(FullTextQuery fullTextQuery, QueryWrapperFilter filter,
            Page<T> page, Class<T> clazz) {

        if (null != filter) {
            fullTextQuery.setFilter(filter);
        }
        Sort sort = null;

        // reverse is true means sort descending and false means sort ascending.
        Boolean reverse;

        /**
         * If the order is asc now, we should change to desc(Set reverse true).
         * And vice verse.
         */
        if ("asc".equals(page.getSortOrder())) {
            reverse = false;
            page.setSortOrder("desc");
        } else {
            reverse = true;
            page.setSortOrder("asc");
        }

        String sortColumn = page.getSortColumn();
        
        if (null != sortColumn && !("".equals(sortColumn))) {
            sort = new Sort(new SortField(sortColumn, Locale.ENGLISH, reverse));
            fullTextQuery.setSort(sort);
        }

        page.setAllRecords(fullTextQuery.list());
        
        page.setRecordCount(fullTextQuery.getResultSize());
        page.setTotalPage(page.getRecordCount() % page.getPageSize() == 0 ? page.getRecordCount()
                / page.getPageSize() : page.getRecordCount() / page.getPageSize() + 1);
        page.setStartRecord((page.getCurrentPage() - 1) * page.getPageSize());

        fullTextQuery.setFirstResult(page.getStartRecord());
        fullTextQuery.setMaxResults(page.getPageSize());
        List<T> queryList = fullTextQuery.list();

        page.setResult(queryList);

        return page;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.base.BaseHibernateDao#createIndex(java.lang.Class
     * <T>[])
     */
    @Override
    public void createIndex(Class<T>... classes) throws InterruptedException {
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        fullTextSession.createIndexer(classes).purgeAllOnStart(true).optimizeAfterPurge(true)
                .optimizeOnFinish(true).startAndWait();

        session.close();
    }

}
