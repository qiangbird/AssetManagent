package com.augmentum.ams.dao.user;

import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.user.SpecialRole;


public interface SpecialRoleDao extends BaseDao<SpecialRole>{

	/**
	 * Get the specialrole by id.
	 * @author John.Li
	 * @param userId
	 * @return
	 */
	SpecialRole getSpecialRoleByUserId(String userId);
	
	/**
	 * Get the specialroles from database.
	 * @author John.Li
	 * @param customerCodes
	 * @return List<SpecialRole>
	 */
	List<SpecialRole> findSpecialRolesByCustomerCodes(List<String> customerCodes);
}
