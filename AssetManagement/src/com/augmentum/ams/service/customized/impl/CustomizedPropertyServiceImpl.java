package com.augmentum.ams.service.customized.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.customized.CustomizedPropertyDao;
import com.augmentum.ams.model.customized.CustomizedProperty;
import com.augmentum.ams.service.customized.CustomizedPropertyService;

@Service("customizedPropertyService")
public class CustomizedPropertyServiceImpl implements CustomizedPropertyService {

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
    public CustomizedProperty getByAssetIdAndTemplateId(String assetId, String templateId) {
        return customizedPropertyDao.getByAssetIdAndTemplateId(assetId, templateId);
    }

    @Override
    public void deleteByTemplateId(String id) {
        customizedPropertyDao.deleteByTemplateId(id);
    }

    @Override
    public List<CustomizedProperty> getByPropertyTemplateId(String id) {
        return customizedPropertyDao.getByPropertyTemplateId(id);
    }

}
