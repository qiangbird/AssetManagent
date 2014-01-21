package com.augmentum.ams.service.user.impl;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.user.Authority;
import com.augmentum.ams.model.user.Role;
import com.augmentum.ams.service.user.AuthorityService;
import com.augmentum.ams.util.UTCTimeUtil;

@Service("authorityService")
public class AuthorityServiceImpl implements AuthorityService {

	private static Logger logger = Logger.getLogger(AuthorityService.class);

	@Autowired
	private BaseDao<Authority> baseDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.augmentum.ams.service.user.AuthorityService#deleteAllAuthority()
	 */
	@Override
	public void deleteAllAuthority() {
		logger.info("Begin to delete all the authorities from database");

		String sql = "delete from role_authority";
		baseDao.bulkUpdateBySQL(sql);
		String hql = "delete from Authority";
		baseDao.bulkUpdateByHQL(hql);

		logger.info("Success delete all the authorities from database");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.augmentum.ams.service.user.AuthorityService#saveAuthority(java.lang
	 * .String)
	 */
	@Override
	public void saveAuthority(String authorityName) {

		Authority authority = this.getAuthorityByName(authorityName);

		if (null == authority) {
			authority = new Authority();
			authority.setName(authorityName);
			Date date = UTCTimeUtil.localDateToUTC();
			authority.setCreatedTime(date);
			authority.setUpdatedTime(date);
		}

		logger.info("Add authority, authority name is " + authorityName);

		baseDao.save(authority);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.augmentum.ams.service.user.AuthorityService#getAuthorityByName(java
	 * .lang.String)
	 */
	@Override
	public Authority getAuthorityByName(String authorityName) {
		String hql = "from Authority where name = ? and isExpired = ?";
		List<Authority> authorities = null;
		authorities = baseDao.find(hql, authorityName, Boolean.FALSE);
		if (authorities.size() > 0) {
			Authority authority = authorities.get(0);
			authority.setRoles(authority.getRoles());
			return authority;
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAuthorityByRole(Role role) {

		logger.info("getAuthorityByRole method start!");

		String hql = "select auth.name from Authority auth join auth.roles as role where role.roleName= ?";

		logger.info("getAuthorityByRole method end!");
		return baseDao.getHibernateTemplate().find(hql,role.getRoleName());
	}

}
