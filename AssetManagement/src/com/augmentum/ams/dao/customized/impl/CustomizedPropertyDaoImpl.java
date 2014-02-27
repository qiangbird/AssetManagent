package com.augmentum.ams.dao.customized.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.customized.CustomizedPropertyDao;
import com.augmentum.ams.model.customized.CustomizedProperty;

@Repository("customizedPropertyDao")
public class CustomizedPropertyDaoImpl extends BaseDaoImpl<CustomizedProperty> implements
        CustomizedPropertyDao {

    @Override
    public CustomizedProperty getCustomizedPropertyById(String id) {
        return super.get(CustomizedProperty.class, id);
    }

    @Override
    public void saveCustomizedProperty(CustomizedProperty customizedProperty) {
        super.save(customizedProperty);
    }

    @Override
    public void updateCustomizedProperty(CustomizedProperty customizedProperty) {
        super.update(customizedProperty);
    }

    @Override
    public CustomizedProperty getByAssetIdAndTemplateId(String assetId, String templateId) {
        String hql = "from CustomizedProperty where asset.id = ? and propertyTemplate.id = ?";
        return super.getUnique(hql, assetId, templateId);
    }

}
