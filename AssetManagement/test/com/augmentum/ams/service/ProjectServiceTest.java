package com.augmentum.ams.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
/**
 * Test for ProjectService
 * @author Jay.He
 *
 */

import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.service.asset.ProjectService;
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class ProjectServiceTest {
	@Autowired
	private ProjectService projectService;
	@Test
	public void getAllProjectTest(){
		List<Project> list=projectService.getAllProject();
		Assert.assertTrue(list.size()==1);
		System.out.println(((Project)list.get(0)).getProjectName());
		
		
		
	}
	
}
