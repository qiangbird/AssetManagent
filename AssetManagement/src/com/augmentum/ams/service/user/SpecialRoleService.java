package com.augmentum.ams.service.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.web.vo.user.SpecialRoleVo;

public interface SpecialRoleService {
	
	/**
	 * Batch save or update the special role.
	 * 
	 * @author John.Li
	 * @time Dec 4, 2013 18:19:00 PM
	 * @param specialRoleVo
	 */
	void saveOrUpdateSpecialRole(SpecialRoleVo specialRoleVo);
	
	/**
	 * Get the special role from the database.
	 * 
	 * @author John.Li
	 * @param customerCodes
	 * @time Dec 4, 2013 18:19:00 PM
	 * @return
	 */
	List<SpecialRoleVo> findSpecialRolesByCustomerCodes(List<String> customerCodes);
	
	/**
     * change the vo object to jsonArry.
     * 
     * @author John.Li
     * @param specialRoleVos
     * @time Dec 9, 2013 10:19:00 AM
     * @return
	 * @throws BusinessException 
     */
	JSONArray changeVOToJSON(List<SpecialRoleVo> specialRoleVos, HttpServletRequest httpServletRequest) throws BusinessException;
	/**
	 * 
	 * @description find if a user is a special by given userId
	 * @author Jay.He
	 * @time Dec 12, 2013 4:58:38 PM
	 * @param userId
	 * @return
	 */
	boolean findSpecialRoleByUserId(String userId);
	
}
