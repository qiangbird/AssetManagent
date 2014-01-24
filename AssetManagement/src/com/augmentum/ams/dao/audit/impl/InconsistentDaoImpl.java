package com.augmentum.ams.dao.audit.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.audit.InconsistentDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.audit.Inconsistent;

@Repository("inconsistentDao")
public class InconsistentDaoImpl extends BaseDaoImpl<Inconsistent> implements
		InconsistentDao {

	@Override
	public List<Inconsistent> findInconsistentAssetsByFileId(String fileId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(
				Inconsistent.class).setFetchMode("auditFile", FetchMode.JOIN);

		criteria.createAlias("auditFile", "auditFile");
		criteria.add(Restrictions.eq("auditFile.id", fileId));
		criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));

		return super.findByCriteria(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findInconsistentAssetByFileName(String fileName) {

		String hql = "SELECT asset.id FROM Inconsistent WHERE isExpired = ? AND auditFile.fileName = ? AND asset.id is NOT NULL";
		return getHibernateTemplate().find(hql, Boolean.FALSE, fileName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> findInconsistentBarcode(String fileName) {
		String hql = "SELECT barCode FROM Inconsistent WHERE isExpired = ? AND auditFile.fileName = ? AND asset.id is NULL";
		return getHibernateTemplate().find(hql, Boolean.FALSE, fileName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Inconsistent> findInconsistentList(String fileName,
			int iDisplayStart, int iDisplayLength) {
		Criteria criteria = getHibernateTemplate().getSessionFactory()
				.openSession().createCriteria(Inconsistent.class)
				.setFetchMode("asset", FetchMode.JOIN);
		criteria.createAlias("auditFile", "auditFile");

		criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
		criteria.add(Restrictions.eq("auditFile.fileName", fileName));

		criteria.setFirstResult(iDisplayStart);
		criteria.setMaxResults(iDisplayLength);

		return (List<Inconsistent>) criteria.list();
	}

}
