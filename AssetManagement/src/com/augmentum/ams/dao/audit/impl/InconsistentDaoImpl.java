package com.augmentum.ams.dao.audit.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.audit.InconsistentDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.audit.Audit;
import com.augmentum.ams.model.audit.Inconsistent;


@Repository("inconsistentDao")
public class InconsistentDaoImpl extends BaseDaoImpl<Inconsistent> implements InconsistentDao{

    @Override
    public List<Inconsistent> findInconsistentAssetsByFileId(String fileId) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Inconsistent.class)
                .setFetchMode("auditFile", FetchMode.JOIN);
        
        criteria.createAlias("auditFile", "auditFile");
        criteria.add(Restrictions.eq("auditFile.id", fileId));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        
        return super.findByCriteria(criteria);
    }

    @Override
    public List<Inconsistent> getInconsistentByFileId(String id, int iDisplayStart,
            int iDisplayLength) {
        
        String hql="from Inconsistent i where i.auditFile.id = ? and i.isExpired = ?";
        
        return getListForPage(hql, id, iDisplayStart, iDisplayLength);
    }
    
    private List<Inconsistent> getListForPage (final String hql ,final String fileId,final int offset,final int length) {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Inconsistent> list=getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                List<Inconsistent> result=session.createQuery(hql).setParameter(0, fileId).setParameter(1, Boolean.FALSE)
                        .setFirstResult(offset).setMaxResults(length).list();
                return result;
            }
        });
        return list;
    }

}
