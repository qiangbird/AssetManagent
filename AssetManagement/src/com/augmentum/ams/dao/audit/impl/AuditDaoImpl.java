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

import com.augmentum.ams.dao.audit.AuditDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.audit.Audit;


@Repository("auditDao")
public class AuditDaoImpl extends BaseDaoImpl<Audit> implements AuditDao{

    @Override
    public List<Audit> findAuditByFileId(String id) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Audit.class)
                .setFetchMode("auditFile", FetchMode.JOIN);
        
        criteria.createAlias("auditFile", "auditFile");
        criteria.add(Restrictions.eq("auditFile.id", id));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        
        return super.findByCriteria(criteria);
    }

    @Override
    public Audit getByAssetIdAndFileId(String assetId, String fileId) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Audit.class)
                .setFetchMode("auditFile", FetchMode.JOIN)
                .setFetchMode("asset", FetchMode.JOIN);
        
        criteria.createAlias("auditFile", "auditFile");
        criteria.createAlias("asset", "asset");
        criteria.add(Restrictions.eq("asset.id", assetId));
        criteria.add(Restrictions.eq("auditFile.id", fileId));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        
        List<Audit> audits = super.findByCriteria(criteria);
        
        if(0 < audits.size()){
            return audits.get(0);
        }
        return null;
    }

    @Override
    public List<Audit> findByFileName(String fileName) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Audit.class)
                .setFetchMode("auditFile", FetchMode.JOIN);
        
        criteria.createAlias("auditFile", "auditFile");
        criteria.add(Restrictions.eq("auditFile.fileName", fileName));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        
        return super.findByCriteria(criteria);
    }

    @Override
    public List<Audit> findUnAuditedAssets(String auditFileName) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Audit.class)
                .setFetchMode("auditFile", FetchMode.JOIN);
        
        criteria.createAlias("auditFile", "auditFile");
        criteria.add(Restrictions.eq("auditFile.fileName", auditFileName));
        criteria.add(Restrictions.eq("status", Boolean.FALSE));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        
        return super.findByCriteria(criteria);
    }

    @Override
    public List<Audit> findAuditedAssets(String auditFileName) {
        
        DetachedCriteria criteria = DetachedCriteria.forClass(Audit.class)
                .setFetchMode("auditFile", FetchMode.JOIN);
        
        criteria.createAlias("auditFile", "auditFile");
        criteria.add(Restrictions.eq("auditFile.fileName", auditFileName));
        criteria.add(Restrictions.eq("status", Boolean.TRUE));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        
        return super.findByCriteria(criteria);
    }

    @Override
    public List<Asset> findAuditedAssets(String auditFileName, int iDisplayStart, int iDisplayLength) {
        
//        String hql="from Audit a where a.auditFile.fileName = ? and a.status = ? and a.isExpired = ?";
        String hql="FROM Asset asset WHERE id IN " +
                "(SELECT audit.asset.id FROM Audit audit WHERE audit.auditFile.fileName = ? AND audit.status " +
                "= ? AND audit.isExpired = ?)";
        
        return getListForPage(hql,auditFileName,iDisplayStart,iDisplayLength, Boolean.TRUE);
    }
    
    @Override
    public List<Asset> findUnAuditedAssets(String auditFileName, int iDisplayStart,
            int iDisplayLength) {
        
        String hql="FROM Asset asset WHERE id IN " +
        		"(SELECT audit.asset.id FROM Audit audit WHERE audit.auditFile.fileName = ? AND audit.status " +
        		"= ? AND audit.isExpired = ?)";
        
        return getListForPage(hql,auditFileName,iDisplayStart,iDisplayLength, Boolean.FALSE);
    }
    
    private List<Asset> getListForPage (final String hql ,final String fileName,
            final int offset,final int length, final boolean status) {
        
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Asset> list=getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {
                List<Asset> result=session.createQuery(hql).setParameter(0, fileName).setParameter(1, status)
                        .setParameter(2, Boolean.FALSE).setFirstResult(offset).setMaxResults(length).list();
                return result;
            }
        });
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> findAssetsBarCodeByFileId(String auditFileId) {
        
        String hql = "SELECT ass.barCode FROM Asset ass WHERE id IN " +
        		"( SELECT au.asset.id FROM Audit au WHERE au.auditFile.id= ?)";
        
        return getHibernateTemplate().find(hql, auditFileId);
    }

}
