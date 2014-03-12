package com.augmentum.ams.dao.base.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.system.Page;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 10:07:58 AM
 * @param <T>
 */
@Repository("baseDao")
public class BaseDaoImpl<T extends BaseModel> implements BaseDao<T> {

    @Autowired
    protected HibernateTemplate hibernateTemplate;

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#contains(java.lang.Object)
     */
    @Override
    public boolean contains(T model) {
        return hibernateTemplate.contains(model);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#delete(java.io.Serializable)
     */
    @Override
    public void delete(T model) {
    	Date date = UTCTimeUtil.localDateToUTC();
    	model.setUpdatedTime(date);
        model.setExpired(Boolean.TRUE);
        hibernateTemplate.update(model);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#find(java.lang.String,
     * java.lang.Object[])
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> find(String hql, Object... values) {
        return hibernateTemplate.find(hql, values);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#findAll(java.lang.Class)
     */

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll(Class<T> modelClass) {
        String hql = "from " + modelClass.getName() + " WHERE isExpired = ?";
        return hibernateTemplate.find(hql, Boolean.FALSE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.pms.dao.BaseDao#findByCriteria(org.hibernate.criterion.
     * DetachedCriteria)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByCriteria(DetachedCriteria criteria) {
        return hibernateTemplate.findByCriteria(criteria);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.pms.dao.BaseDao#findByCriteria(org.hibernate.criterion.
     * DetachedCriteria, com.augmentum.pms.vo.Page)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<T> findByCriteria(DetachedCriteria criteria, Page<T> page) {
    	
        int pageSize = page.getPageSize();
        int currentPage = page.getCurrentPage();
        page.setStartRecord((currentPage - 1) * pageSize);
        int firstResult = page.getStartRecord();
        
        List<T> list = null;

        if (page.getRecordCount() == 0) {
            int recordCount = 0;
            // Gain the count
            criteria.setProjection(Projections.rowCount());
            recordCount = Integer.parseInt(hibernateTemplate.findByCriteria(criteria).get(0)
                    .toString());
            criteria.setProjection(null);
            page.setRecordCount(recordCount);
        }
        
        int recordCount = page.getRecordCount();
        int totalPage = recordCount % pageSize == 0 ? recordCount / pageSize : recordCount / pageSize + 1;
        page.setTotalPage(totalPage);
        
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        // Gain result of query
        list = hibernateTemplate.findByCriteria(criteria, firstResult, pageSize);

        page.setResult(list);

        return page;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#findByExample(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findByExample(T examplemodel) {
        return hibernateTemplate.findByExample(examplemodel);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#findByExample(java.io.Serializable,
     * com.augmentum.pms.vo.Page)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<T> findByExample(T examplemodel, Page<T> page) {
        int firstResult = page.getStartRecord();
        int maxResults = page.getPageSize();

        List<T> list = hibernateTemplate.findByExample(examplemodel, firstResult, maxResults);
        if (page.getRecordCount() == 0) {
            int recordCount = hibernateTemplate.findByExample(examplemodel).size();
            page.setRecordCount(recordCount);
        }
        page.setResult(list);
        return page;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#findByPage(com.augmentum.pms.vo.Page,
     * java.lang.String, java.lang.Object[])
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Page<T> findByHql(final Page<T> page, final String hql, final Object... values) {
        return (Page<T>) hibernateTemplate.execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = createQuery(session, hql, values);
                query.setFirstResult(page.getStartRecord());
                query.setMaxResults(page.getPageSize());
                page.setResult(query.list());
                if (page.getRecordCount() == 0) {
                    int recordCount = 0;
                    recordCount = query.list().size();
                    page.setRecordCount(recordCount);
                }
                return page;
            }
        });
    }

    private Query createQuery(Session session, String queryString, Object... values) {
        Query query = session.createQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#findTotalCount(java.lang.Class)
     */
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public int getTotalCount(Class<T> modelClass) {
        final String hql = "select count(*) from " + modelClass.getName();
        long count = (Long) hibernateTemplate.execute(new HibernateCallback(){
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                Query query = session.createQuery(hql);
            return query.uniqueResult();
        }
        });
        return (int) count;
    }
    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#findUnique(java.lang.String,
     * java.lang.Object[])
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public T getUnique(final String hql, final Object... values) {
        return (T) getHibernateTemplate().execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = createQuery(session, hql, values);
                return query.uniqueResult();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#get(java.lang.Class, java.lang.String)
     */
    @Override
    public T get(Class<T> modelClass, String id) {
        return hibernateTemplate.get(modelClass, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#load(java.lang.Class,
     * java.lang.String)
     */
    @Override
    public T load(Class<T> modelClass, String id) {
        return hibernateTemplate.load(modelClass, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#merge(java.lang.Object)
     */
    @Override
    public T merge(T model) {
        return hibernateTemplate.merge(model);
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.augmentum.pms.dao.BaseDao#save(java.io.Serializable)
	 */
	@Override
	public T save(T model) {
		Date date = UTCTimeUtil.localDateToUTC();
		model.setCreatedTime(date);
		model.setUpdatedTime(date);
		hibernateTemplate.save(model);
		return model;
	}

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#saveOrUpdate(java.io.Serializable)
     */
    @Override
    public T saveOrUpdate(T model) {
        Date utcTime = UTCTimeUtil.localDateToUTC();
        model.setUpdatedTime(utcTime);
        hibernateTemplate.saveOrUpdate(model);
        return model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.pms.dao.BaseDao#update(java.io.Serializable)
     */
    @Override
    public T update(T model) {
        Date utcTime = UTCTimeUtil.localDateToUTC();
        model.setUpdatedTime(utcTime);
        try {
            hibernateTemplate.merge(model);
        } catch (DataAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return model;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.BaseDao#getCountByCriteria(org.hibernate.criterion
     * .DetachedCriteria)
     */
    @Override
    public int getCountByCriteria(DetachedCriteria criteria) {
        int count = 0;
        criteria.setProjection(Projections.rowCount());
        count = Integer.parseInt(hibernateTemplate.findByCriteria(criteria).get(0).toString());
        return count;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.dao.BaseDao#getHibernateTemplate()
     */
    @Override
    public HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.dao.BaseDao#findUnique(org.hibernate.criterion.
     * DetachedCriteria)
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getUnique(final DetachedCriteria criteria) {
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        
        return hibernateTemplate.execute(new HibernateCallback<T>() {
            public T doInHibernate(Session session) throws HibernateException,
                    SQLException {
                return (T) criteria.getExecutableCriteria(session)
                        .uniqueResult();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.dao.BaseDao#getCountByHql(java.lang.String,
     * java.lang.Object[])
     */
    @Override
    public int getCountByHql(String hql, Object... values) {
        return Integer.parseInt(hibernateTemplate.find(hql, values).get(0).toString());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.dao.base.BaseDao#bulkUpdate(java.lang.String,
     * java.lang.Object[])
     */
    @Override
    public int bulkUpdateByHQL(String hql, Object... values) {
        return hibernateTemplate.bulkUpdate(hql, values);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.dao.base.BaseDao#bulkUpdateBySQL(java.lang.String,
     * java.lang.Object[])
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public int bulkUpdateBySQL(final String sql, final Object... values) {
        return (Integer) hibernateTemplate.execute(new HibernateCallback() {
            public Object doInHibernate(Session session) throws HibernateException, SQLException {
                Query query = session.createSQLQuery(sql);
                for (int i = 0; i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
                return query.executeUpdate();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.dao.base.BaseDao#saveOrUpdateAll(java.util.List)
     */
    @Override
    public List<T> saveOrUpdateAll(List<T> entities) {
        hibernateTemplate.saveOrUpdateAll(entities);
        return entities;
    }
}
