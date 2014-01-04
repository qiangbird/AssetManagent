package com.augmentum.ams.dao.base;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.web.vo.system.Page;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 10:07:50 AM
 * @param <T>
 */
public interface BaseDao<T extends BaseModel>{
    
	/**
     * Save a model.
     * 
     * @param model
     * @return Returns the {@code id} of the model you save in to the database.
     */
    T save(T model);

    /**
     * Delete a model.
     * 
     * @param model
     */
    void delete(T model);
    
    /**
     * Update a model.
     * 
     * @param model
     */
    T update(T model);

    /**
     * If the model has been exited in the database, this operation will update
     * it, or this operation will add a new model in to the database.
     * 
     * @param model
     */
    T saveOrUpdate(T model);

    /**
     * This is merge operation, add a model in to the database.
     * 
     * @param model
     * @return the model you add to the database with the {@code id} generated.
     */
    T merge(T model);

    /**
     * Check whether the given object is in the Session cache.
     * 
     * @param model
     * @return
     */
    boolean contains(T model);

    /**
     * Load a model by model id.
     * 
     * @param <T>
     * @param modelClass
     * @param id
     * @return
     */
    T load(Class<T> modelClass, String id);

    /**
     * Get a model by model id.
     * 
     * @param <T>
     * @param modelClass
     * @param id
     * @return
     */
    T get(Class<T> modelClass, String id);

    /**
     * It is used to get a unique result, your condition should satisfy this.
     * 
     * @param hql
     * @param values
     * @return
     */
    T getUnique(final String hql, final Object... values);
    
    /**
     * It is used to get a unique result, your condition should satisfy this.
     * 
     * @param criteria
     * @return
     */
    T getUnique(final DetachedCriteria criteria);
    

    /**
     * Find all the records of the model class.
     * 
     * @param modelClass
     * @return
     */
    List<T> findAll(Class<T> modelClass);

    /**
     * Find the records which satisfy the conditions.
     * 
     * @param hql
     * @param values
     * @return
     */
    List<T> find(String hql, Object... values);

    /**
     * Execute a query based on a given Hibernate criteria object
     * 
     * @param criteria
     * @return
     */
    List<T> findByCriteria(DetachedCriteria criteria);
    

    /**
     * Execute a query based on a given Hibernate criteria object, this is used
     * for pagination.
     * 
     * @param criteria
     * @param page
     *            The page contains the information for pagination.
     * @return
     */
    Page<T> findByCriteria(DetachedCriteria criteria, Page<T> page);

    /**
     * Execute a query based on a given Hibernate criteria object
     * 
     * @param model
     * @return
     */
    List<T> findByExample(T exampleModel);

    /**
     * Execute a query based on a given Hibernate criteria object, this is used
     * for pagination.
     * 
     * @param examplemodel
     * @param page
     *            The page contains the information for pagination.
     * @return
     */
    Page<T> findByExample(T exampleModel, Page<T> page);

    /**
     * This is used for pagination which needs the hql and your parameters.
     * 
     * @param page
     * @param hql
     * @param values
     * @return
     */
    Page<T> findByHql(final Page<T> page, final String hql,
            final Object... values);

    /**
     * Get the total count of the model records.
     * 
     * @param modelClass
     * @return
     * @ 
     * @throws NumberFormatException 
     */
    int getTotalCount(Class<T> modelClass) ;

    /**
     * Get the total count of the model records.
     * 
     * @param DetachedCriteria
     * @return the total count of the model records.
     */
    int getCountByCriteria(DetachedCriteria criteria);
    
    /**
     * Get the total count of the model records.
     * 
     * @param hql
     * @param values
     * @return the total count of the model records.
     */
    int getCountByHql(String hql,final Object... values);
    
    
    HibernateTemplate getHibernateTemplate();

	/**
	 * Bulk save or update recoreds in database
     * @param entities
     * @return List<T>
     */
    List<T> saveOrUpdateAll(List<T> entities);

	/**
	 * Bulk update records by using hql
     * @param hql
     * @param values
     * @return the total count of affected by queryString
     */
    int bulkUpdateByHQL(String hql ,final Object... values);
    
    /**
	 * Bulk update records by using sql
     * @param sql
     * @param values
     * @return the total count of affected by queryString
     */
    int bulkUpdateBySQL(final String sql ,final Object... values);
    
}
