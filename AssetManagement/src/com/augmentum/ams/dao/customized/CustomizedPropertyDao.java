package com.augmentum.ams.dao.customized;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.customized.CustomizedProperty;

public interface CustomizedPropertyDao extends BaseDao<CustomizedProperty>{
	public CustomizedProperty getCustomizedPropertyById(String id);
	
	public void saveCustomizedProperty(CustomizedProperty customizedProperty);
	
	public void updateCustomizedProperty(CustomizedProperty customizedProperty);
	
	public CustomizedProperty getCustomizedPropertyByTemplateId(String templateId);
}
