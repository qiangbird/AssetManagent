package com.augmentum.ams.dao.customized.impl;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.customized.PropertyTemplateDao;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.util.LogHelper;

@Repository("propertyTemplateDao")
public class PropertyTemplateDaoImpl extends BaseDaoImpl<PropertyTemplate> implements
        PropertyTemplateDao {

    private Logger logger = Logger.getLogger(PropertyTemplateDaoImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.customized.PropertyTemplateDao#getSelfDefinedProperties
     * (java.lang.String, java.lang.String)
     */
    @Override
    public List<PropertyTemplate> findSelfDefinedProperties(String customerName, String assetType)
            throws ParseException {

        logger.info(LogHelper.getLogInfo("Find self defined properties start", customerName,
                assetType));

        DetachedCriteria criteria = DetachedCriteria.forClass(PropertyTemplate.class);

        criteria.add(Restrictions.eq("assetType", assetType));
        criteria.createAlias("customer", "customer");
        criteria.add(Restrictions.eq("customer.customerName", customerName));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));

        criteria.addOrder(Order.asc("sequence"));

        List<PropertyTemplate> propertyTemplates = findByCriteria(criteria);

        logger.info(LogHelper.getLogInfo(
                "Find self defined properties end, propertyTemplates size",
                propertyTemplates.size()));

        return propertyTemplates;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.customized.PropertyTemplateDao#getPropertyTemplate
     * (java.lang.String)
     */
    @Override
    public PropertyTemplate getPropertyTemplate(String id) {

        logger.info(LogHelper.getLogInfo("Get propertyTemplate start", id));

        DetachedCriteria criteria = DetachedCriteria.forClass(PropertyTemplate.class);
        criteria.add(Restrictions.eq("id", id));

        logger.info(LogHelper.getLogInfo("Get propertyTemplate end"));

        return getUnique(criteria);
    }
}
