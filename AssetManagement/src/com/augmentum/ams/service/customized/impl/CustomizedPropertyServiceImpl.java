package com.augmentum.ams.service.customized.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.customized.CustomizedPropertyDao;
import com.augmentum.ams.model.customized.CustomizedProperty;
import com.augmentum.ams.service.customized.CustomizedPropertyService;

@Service("customizedPropertyService")
public class CustomizedPropertyServiceImpl implements CustomizedPropertyService{
	
	@Autowired
	private CustomizedPropertyDao customizedPropertyDao;

	@Override
	public CustomizedProperty getCustomizedPropertyById(String id) {
		return customizedPropertyDao.getCustomizedPropertyById(id);
	}

	@Override
	public void saveCustomizedProperty(CustomizedProperty customizedProperty) {
		customizedPropertyDao.save(customizedProperty);
	}

	@Override
	public void updateCustomizedProperty(CustomizedProperty customizedProperty) {
		customizedPropertyDao.updateCustomizedProperty(customizedProperty);
	}

	@Override
	public CustomizedProperty getCustomizedPropertyByTemplateId(String templateId) {
		return customizedPropertyDao.getCustomizedPropertyByTemplateId(templateId);
	}

}
