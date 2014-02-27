package com.augmentum.ams.service.customized.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.customized.PropertyTemplateDao;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.customized.CustomizedProperty;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.model.enumeration.PropertyTypeEnum;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.customized.CustomizedPropertyService;
import com.augmentum.ams.service.customized.PropertyTemplateService;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.customized.PropertyTemplateVo;

@Service("propertyTemplateService")
public class PropertyTemplateServiceImpl implements PropertyTemplateService {

    private Logger logger = Logger.getLogger(PropertyTemplateServiceImpl.class);

    @Autowired
    private PropertyTemplateDao propertyTemplateDao;

    @Autowired
    private CustomizedPropertyService customizedPropertyService;

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.customized.PropertyTemplateService#
     * savePropertyTemplate
     * (com.augmentum.ams.web.vo.customized.PropertyTemplateVo,
     * com.augmentum.ams.model.asset.Customer,
     * com.augmentum.ams.model.user.User)
     */
    @Override
    public void savePropertyTemplate(PropertyTemplateVo propertyTemplateVo, Customer customer,
            User user) {

        JSONObject selfProperty = null;
        Date nowTime = UTCTimeUtil.localDateToUTC();
        List<PropertyTemplate> propertyTemplates = new ArrayList<PropertyTemplate>();
        List<PropertyTemplate> originalProperties = new ArrayList<PropertyTemplate>();
        JSONArray selfPropertyArray = JSONArray.fromObject(propertyTemplateVo.getSelfProperties());

        try {
            for (int i = 0; i < selfPropertyArray.size(); i++) {
                PropertyTemplate propertyTemplate = new PropertyTemplate();
                selfProperty = JSONObject.fromObject(selfPropertyArray.get(i));

                if (Boolean.TRUE == selfProperty.getBoolean("isNew")) { // create
                    propertyTemplate.setCreatedTime(nowTime);
                    propertyTemplate.setAssetType(propertyTemplateVo.getAssetType());
                    propertyTemplate.setCustomer(customer);
                    propertyTemplate.setCreator(user);
                } else { // update
                    propertyTemplate = getPropertyTemplateById(selfProperty.getString("id"));
                    if (Boolean.TRUE == selfProperty.getBoolean("isDelete")) {
                        propertyTemplate.setExpired(Boolean.TRUE);

                        customizedPropertyService.deleteByTemplateId(propertyTemplate.getId());
                    } else {
                        PropertyTemplate updatedpropertyTemplate = new PropertyTemplate();
                        BeanUtils.copyProperties(updatedpropertyTemplate, propertyTemplate);
                        originalProperties.add(updatedpropertyTemplate);
                    }
                }
                setValueOfPropertyTemplate(propertyTemplate, selfProperty, nowTime);

                propertyTemplates.add(propertyTemplate);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeUtil.UNKNOWN_EXCEPTION_0401001,
                    "Save PropertyTemplate failure!");
        }
        propertyTemplateDao.saveOrUpdateAll(propertyTemplates);
        checkChangeTypeOrValue(originalProperties, propertyTemplates);
    }

    private void checkChangeTypeOrValue(List<PropertyTemplate> originalProperties,
            List<PropertyTemplate> propertyTemplates) {

        List<PropertyTemplate> updatedproperties = new ArrayList<PropertyTemplate>();

        for (PropertyTemplate propertyTemplate : propertyTemplates) {
            if (null != propertyTemplate.getId()) { // update
                updatedproperties.add(propertyTemplate);
            }
        }

        if (null == originalProperties || null == updatedproperties
                || originalProperties.size() != updatedproperties.size()) {
            logger.info("OriginalProperties or updatedproperties is not exist or don't have the same size!");
            return;
        } else {
            // put them into the map
            Map<String, PropertyTemplate> originalPropertiesMap = new HashMap<String, PropertyTemplate>();

            for (PropertyTemplate propertyTemplate : originalProperties) {
                originalPropertiesMap.put(propertyTemplate.getId(), propertyTemplate);
            }

            for (int i = 0; i < updatedproperties.size(); i++) {
                if (originalPropertiesMap.containsKey(updatedproperties.get(i).getId())) {
                    String updatePropertyType = updatedproperties.get(i).getPropertyType();
                    String originalPropertyType = originalPropertiesMap.get(
                            updatedproperties.get(i).getId()).getPropertyType();

                    if (StringUtils.isBlank(updatePropertyType)
                            || StringUtils.isBlank(originalPropertyType)) {
                        throw new BusinessException(ErrorCodeUtil.DATA_NOT_EXIST,
                                "updatePropertyType or originalPropertyType is null!");
                    }

                    // temporary ignore the situation that only update the value
                    // of the properyTemplate.
                    if (!updatePropertyType.equals(originalPropertyType)) {
                        // update the corresponding value of the
                        // customizedPropery
                        List<CustomizedProperty> customizedProperties = customizedPropertyService
                                .getByPropertyTemplateId(updatedproperties.get(i).getId());

                        if (null != customizedProperties) {
                            for (CustomizedProperty customizedProperty : customizedProperties) {
                                customizedProperty.setValue(updatedproperties.get(i).getValue());
                                customizedPropertyService
                                        .updateCustomizedProperty(customizedProperty);
                            }
                        } else {
                            throw new BusinessException(ErrorCodeUtil.DATA_NOT_EXIST,
                                    "The customizedProperty with id : "
                                            + updatedproperties.get(i).getId()
                                            + " is not exist in the database!");
                        }
                    }
                } else {
                    throw new BusinessException(ErrorCodeUtil.DATA_NOT_EXIST,
                            "Can't find updatedproperties in originalProperties of the database!");
                }
            }
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.customized.PropertyTemplateService#
     * getPropertyTemplate(java.lang.String)
     */
    @Override
    public PropertyTemplate getPropertyTemplateById(String id) throws ParseException {

        PropertyTemplate propertyTemplate = propertyTemplateDao.getPropertyTemplate(id);

        return propertyTemplate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.customized.PropertyTemplateService#
     * getPropertyTemplate(java.lang.String, java.lang.String)
     */
    @Override
    public JSONArray findPropertyTemplateByCustomerAndAssetType(String customerName,
            String assetType) throws ParseException {

        List<PropertyTemplate> propertyTemplates = propertyTemplateDao.findSelfDefinedProperties(
                customerName, assetType);
        JSONArray selfPropertyArray = new JSONArray();

        for (PropertyTemplate propertyTemplate : propertyTemplates) {
            JSONObject selfProperty = new JSONObject();

            selfProperty.put("id", propertyTemplate.getId());
            selfProperty.put("propertyType", propertyTemplate.getPropertyType());

            if (PropertyTypeEnum.SELECT_TYPE.getPropertyType().equals(
                    propertyTemplate.getPropertyType())) {
                String propertyValue = propertyTemplate.getValue();
                selfProperty.put("value", propertyValue.split("#"));
            } else {
                selfProperty.put("value", propertyTemplate.getValue());
            }
            selfProperty.put("position", propertyTemplate.getPosition());
            selfProperty.put("enName", propertyTemplate.getEnName());
            selfProperty.put("zhName", propertyTemplate.getZhName());
            selfProperty.put("sequence", propertyTemplate.getSequence());
            selfProperty.put("required", propertyTemplate.isRequired());
            selfProperty.put("propertyDescription", propertyTemplate.getDescription());
            selfPropertyArray.add(selfProperty);
        }

        return selfPropertyArray;

    }

    private PropertyTemplate setValueOfPropertyTemplate(PropertyTemplate propertyTemplate,
            JSONObject selfProperty, Date nowTime) {

        propertyTemplate.setUpdatedTime(nowTime);
        propertyTemplate.setPropertyType(selfProperty.getString("propertyType"));
        propertyTemplate.setValue(selfProperty.getString("value"));
        propertyTemplate.setPosition(selfProperty.getString("position"));
        propertyTemplate.setEnName(selfProperty.getString("enName"));
        propertyTemplate.setZhName(selfProperty.getString("zhName"));
        propertyTemplate.setRequired(selfProperty.getBoolean("required"));
        propertyTemplate.setSequence(selfProperty.getInt("sequence"));
        propertyTemplate.setDescription(selfProperty.getString("propertyDescription"));

        return propertyTemplate;
    }
}
