package com.augmentum.ams.dao.customized.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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
        String hql = "from CustomizedProperty where asset.id = ? and propertyTemplate.id = ? and isExpired = ?";
        return super.getUnique(hql, assetId, templateId, Boolean.FALSE);
    }

    @Override
    public void deleteByTemplateId(String id) {

        String hql = "Update CustomizedProperty set isExpired = ? where propertyTemplate.id = ?";
        bulkUpdateByHQL(hql, Boolean.TRUE, id);

    }

    @Override
    public List<CustomizedProperty> getByPropertyTemplateId(String id) {

        DetachedCriteria criteria = DetachedCriteria.forClass(CustomizedProperty.class);

        criteria.createAlias("propertyTemplate", "propertyTemplate");
        criteria.add(Restrictions.eq("propertyTemplate.id", id));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));

        return findByCriteria(criteria);
    }

}
