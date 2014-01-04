package com.augmentum.ams.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.enumeration.RoleEnum;
import com.augmentum.ams.service.user.RoleService;

/**
 * @author Rudy.Gao
 * @time Sep 16, 2013 2:04:21 PM
 */
public class RoleServiceTest extends BaseCaseTest{
	
	@Autowired
	private RoleService roleService;
	
	@Test
	public void testSaveInitRole() {
		roleService.saveInitRole();
	}

	@Test
	public void testGetRoleByName() throws DataException {
		roleService.getRoleByName(RoleEnum.IT);
	}
}
