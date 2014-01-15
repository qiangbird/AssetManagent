package com.augmentum.ams.dao.audit.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.audit.AuditFileDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.util.UTCTimeUtil;


@Repository("auditFileDao")
public class AuditFileDaoImpl extends BaseDaoImpl<AuditFile> implements AuditFileDao{

    @Override
    public AuditFile getAuditFileByFileName(String auditFileName) {
        
        String hql = "FROM AuditFile WHERE fileName = ? AND isExpired = ? ";
        List<AuditFile> auditFiles = find(hql, auditFileName, Boolean.FALSE);
        
        if(0 < auditFiles.size()){
            return auditFiles.get(0);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<String> getAuditFilesName() {
    	
        String hql = "SELECT fileName FROM AuditFile WHERE isExpired = ? AND fileName like ?";
        String prefixName = UTCTimeUtil.formatDateToString(new Date());
        return super.getHibernateTemplate().find(hql, Boolean.FALSE, "%" + prefixName + "%");
    }

    @Override
    public List<AuditFile> findDoneAuditList() {
    	
        DetachedCriteria criteria = DetachedCriteria.forClass(AuditFile.class).setFetchMode("operator", FetchMode.JOIN);
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        criteria.add(Restrictions.isNotNull("operator"));
        criteria.addOrder(Order.desc("updatedTime"));
        return super.findByCriteria(criteria);
    }

    @Override
    public List<AuditFile> findProcessingAuditList() {
    	
        String hql = "FROM AuditFile WHERE isExpired = ? AND operator IS NULL ORDER BY updatedTime DESC";
        return super.find(hql, Boolean.FALSE);
    }

    @Override
    public int getDoneAuditFilesCount() {
    	
        String hql = "SELECT COUNT(*) FROM AuditFile WHERE isExpired = ? AND operator IS NOT NULL";
        return getCountByHql(hql, Boolean.FALSE);
    }

    @Override
    public int getProcessingAuditFilesCount() {
    	
        String hql = "SELECT COUNT(*) FROM AuditFile WHERE isExpired = ? AND operator IS NULL";
        return getCountByHql(hql, Boolean.FALSE);
    }

    @Override
    public String getEmployeeNameByFileName(String auditFileName) {
        
        String hql = "SELECT user.userName FROM User user WHERE user.id = (SELECT auditFile.operator.id " +
        		"FROM AuditFile auditFile WHERE auditFile.fileName = ? AND auditFile.isExpired = ?)";
        
        @SuppressWarnings("unchecked")
        List<String> employeeNames = getHibernateTemplate().find(hql, auditFileName, Boolean.FALSE);
        
        if(null != employeeNames){
            return employeeNames.get(0);
        }
        return null;
    }
}
