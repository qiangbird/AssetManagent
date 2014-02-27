package com.augmentum.ams.dao.customized;

import java.text.ParseException;
import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.customized.PropertyTemplate;

public interface PropertyTemplateDao extends BaseDao<PropertyTemplate> {

    // get Self-defined properties as given assetType,customer
    List<PropertyTemplate> findSelfDefinedProperties(String customerName, String assetType)
            throws ParseException;

    /**
     * @description Get the propertyTemplate entity by id
     * @param id
     * @return PropertyTemplate
     */
    PropertyTemplate getPropertyTemplate(String id);

}
