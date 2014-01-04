/**
 * 
 */
package com.augmentum.ams.dao.base;

import org.apache.lucene.search.QueryWrapperFilter;
import org.hibernate.search.FullTextQuery;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.web.vo.system.Page;

/**
 * @author Grylls.Xu
 * @time Sep 25, 2013 4:14:32 PM
 */
public interface BaseHibernateDao<T extends BaseModel> {

    /**
     * @author Grylls.Xu
     * @time Sep 25, 2013 4:34:30 PM
     * @description List the object by hibernate index.
     * @param <T>
     * @param query
     * @param filter
     * @param page
     * @param clazz
     * @return
     */
    Page<T> findByIndex(FullTextQuery fullTextQuery, QueryWrapperFilter filter, Page<T> page, Class<T> clazz);

    /**
     * @author Grylls.Xu
     * @time Sep 28, 2013 3:18:37 PM
     * @description TODO
     * @param <T>
     * @param classes
     * @throws InterruptedException
     */
    void createIndex(Class<T>... classes) throws InterruptedException;

}
