package com.augmentum.ams.service.customized.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.customized.PropertyTemplateDao;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.model.enumeration.PropertyTypeEnum;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.customized.PropertyTemplateService;
import com.augmentum.ams.util.LogHelper;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.customized.PropertyTemplateVo;

@Service("propertyTemplateService")
public class PropertyTemplateServiceImpl implements PropertyTemplateService {

    private Logger logger = Logger.getLogger(PropertyTemplateServiceImpl.class);
    
	@Autowired
	private PropertyTemplateDao propertyTemplateDao;

	/* (non-Javadoc)
	 * @see com.augmentum.ams.service.customized.PropertyTemplateService#savePropertyTemplate(com.augmentum.ams.web.vo.customized.PropertyTemplateVo, com.augmentum.ams.model.asset.Customer, com.augmentum.ams.model.user.User)
	 */
	@Override
	public void savePropertyTemplate(PropertyTemplateVo propertyTemplateVo, Customer customer, User user){
		
	    logger.info(LogHelper.getLogInfo("Save property template start"));
	    
		JSONObject selfProperty = null;
		Date nowTime = UTCTimeUtil.localDateToUTC();
		List<PropertyTemplate> propertyTemplates = new ArrayList<PropertyTemplate>();
		JSONArray selfPropertyArray = JSONArray.fromObject(propertyTemplateVo.getSelfProperties());
		
		try {
			for(int i = 0; i < selfPropertyArray.size(); i++) {
				PropertyTemplate propertyTemplate = new PropertyTemplate();
				selfProperty = JSONObject.fromObject(selfPropertyArray.get(i));
				
				if(Boolean.TRUE == selfProperty.getBoolean("isNew")){ //create
					propertyTemplate.setCreatedTime(nowTime);
					propertyTemplate.setAssetType(propertyTemplateVo.getAssetType());
					propertyTemplate.setCustomer(customer);
					propertyTemplate.setCreator(user);
				}else { //update
					propertyTemplate = getPropertyTemplateById(selfProperty.getString("id"));
					if(Boolean.TRUE == selfProperty.getBoolean("isDelete")){
						propertyTemplate.setExpired(Boolean.TRUE);
					}
				}
				setValueOfPropertyTemplate(propertyTemplate,
			            selfProperty,  nowTime);
				
				propertyTemplates.add(propertyTemplate);
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
		propertyTemplateDao.saveOrUpdateAll(propertyTemplates);
		
		logger.info(LogHelper.getLogInfo("Save property template end"));
	}
	
	/* (non-Javadoc)
	 * @see com.augmentum.ams.service.customized.PropertyTemplateService#getPropertyTemplate(java.lang.String)
	 */
	@Override
	public PropertyTemplate getPropertyTemplateById(String id) throws ParseException {
		
	    logger.info(LogHelper.getLogInfo("Get property template by id start"));
	    
	    PropertyTemplate propertyTemplate = propertyTemplateDao.getPropertyTemplate(id);
	    
	    logger.info(LogHelper.getLogInfo("Get property template end"));
	    
		return propertyTemplate;
	}

	/* (non-Javadoc)
	 * @see com.augmentum.ams.service.customized.PropertyTemplateService#getPropertyTemplate(java.lang.String, java.lang.String)
	 */
	@Override
	public JSONArray findPropertyTemplateByCustomerAndAssetType(String customerName,
			String assetType) throws ParseException {
		
	    logger.info(LogHelper.getLogInfo("Get property template by customer and assetType start"));
	    
	    List<PropertyTemplate> propertyTemplates = propertyTemplateDao
	            .findSelfDefinedProperties(customerName, assetType);
	    JSONArray selfPropertyArray = new JSONArray();
	    
        for(PropertyTemplate propertyTemplate : propertyTemplates) {
            JSONObject selfProperty = new JSONObject();
            
            selfProperty.put("id", propertyTemplate.getId());
            selfProperty.put("propertyType", propertyTemplate.getPropertyType());
            
            if(PropertyTypeEnum.SELECT_TYPE.getPropertyType()
                    .equals(propertyTemplate.getPropertyType())) {
                String propertyValue = propertyTemplate.getValue();
                selfProperty.put("value", propertyValue.split("#"));
            }else{
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
        logger.info(LogHelper.getLogInfo("Get property template by customer and assetType end"));
		
		return selfPropertyArray;
		
	}

	private PropertyTemplate setValueOfPropertyTemplate(PropertyTemplate propertyTemplate,
            JSONObject selfProperty, Date nowTime){
        
	    logger.info(LogHelper.getLogInfo("Set value of property template start"));
	    
        propertyTemplate.setUpdatedTime(nowTime);
        propertyTemplate.setPropertyType(selfProperty.getString("propertyType"));
        propertyTemplate.setValue(selfProperty.getString("value"));
        propertyTemplate.setPosition(selfProperty.getString("position"));
        propertyTemplate.setEnName(selfProperty.getString("enName"));
        propertyTemplate.setZhName(selfProperty.getString("zhName"));
        propertyTemplate.setRequired(selfProperty.getBoolean("required"));
        propertyTemplate.setSequence(selfProperty.getInt("sequence"));
        propertyTemplate.setDescription(selfProperty.getString("propertyDescription"));
        
        logger.info(LogHelper.getLogInfo("Set value of property template end"));
        
        return propertyTemplate;
    }
}

