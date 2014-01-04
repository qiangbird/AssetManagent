package com.augmentum.ams.web.controller.customized;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.customized.CustomizedProperty;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.customized.CustomizedPropertyService;
import com.augmentum.ams.service.customized.PropertyTemplateService;

@Controller("customizedProperyController")
@RequestMapping(value="/customizedPropery")
public class CustomizedProperyController {
	
	@Autowired
	private CustomizedPropertyService customizedPropertyService;
	@Autowired
	private PropertyTemplateService propertyTemplateService;
	@Autowired
	private AssetService assetService;
	
	@RequestMapping(value="updateSelfDefinedProperties" )
	public ModelAndView updateSelfDefinedProperties(String assetId,String selfDefinedNames, String selfDefinedIds, String selfDefinedValues) throws ParseException{
		ModelAndView modelAndView = new ModelAndView();
		String selfIds[] = selfDefinedIds.split(",");
		String selfValues[] = selfDefinedValues.split(",");
		System.out.println(selfDefinedNames+"\t\t"+selfDefinedIds+"\t\t"+selfDefinedValues);
		
		Asset asset = assetService.getAsset(assetId);
		for(int i=0;i<selfIds.length;i++){
			CustomizedProperty customizedProperty = new CustomizedProperty();
			PropertyTemplate propertyTemplate = propertyTemplateService.getPropertyTemplateById(selfIds[i]);
			customizedProperty.setAsset(asset);
			customizedProperty.setPropertyTemplate(propertyTemplate);
			customizedProperty.setValue(selfValues[i]);
			CustomizedProperty cp = null;
			cp = customizedPropertyService.getCustomizedPropertyByTemplateId(propertyTemplate.getId());
			
			if(null==cp){
			customizedPropertyService.saveCustomizedProperty(customizedProperty);
			}else{
				customizedProperty.setId(cp.getId());
				customizedProperty.setExpired(Boolean.FALSE);
				try {
					customizedPropertyService.updateCustomizedProperty(customizedProperty);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
		
		return modelAndView;
	}
}
