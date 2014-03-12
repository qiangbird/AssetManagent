package com.augmentum.ams.web.controller.customized;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.customized.CustomizedProperty;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.customized.CustomizedPropertyService;
import com.augmentum.ams.service.customized.PropertyTemplateService;
import com.augmentum.ams.util.ErrorCodeUtil;

@Controller("customizedProperyController")
@RequestMapping(value = "/customizedPropery")
public class CustomizedProperyController {

    @Autowired
    private CustomizedPropertyService customizedPropertyService;

    @Autowired
    private AssetService assetService;

    @Autowired
    private PropertyTemplateService propertyTemplateService;

    @RequestMapping(value = "updateSelfDefinedProperties")
    public ModelAndView updateSelfDefinedProperties(String assetId, String selfDefinedNames,
            String selfDefinedIds, String selfDefinedValues) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();

        String selfIds[] = {};
        String selfValues[] = {};
        
        if (-1 != selfDefinedIds.indexOf(",")) {
            selfIds = selfDefinedIds.substring(0, selfDefinedIds.lastIndexOf(",")).split(",");
        }
        
        if (-1 != selfDefinedValues.indexOf(",")) {
            selfValues = selfDefinedValues.substring(0, selfDefinedValues.lastIndexOf(","))
                    .split(",");
        }

        for (int i = 0; i < selfIds.length; i++) {
            CustomizedProperty originalCustomizedProperty = customizedPropertyService
                    .getByAssetIdAndTemplateId(assetId, selfIds[i]);

            if (null != originalCustomizedProperty) { // update
                originalCustomizedProperty.setValue(selfValues[i]);
                customizedPropertyService.updateCustomizedProperty(originalCustomizedProperty);
            } else { // save
                CustomizedProperty newCustomizedProperty = new CustomizedProperty();
                Asset asset = assetService.getAsset(assetId);
                PropertyTemplate propertyTemplate = propertyTemplateService
                        .getPropertyTemplateById(selfIds[i]);

                if (null != asset && null != propertyTemplate) {
                    newCustomizedProperty.setValue(selfValues[i]);
                    newCustomizedProperty.setAsset(asset);
                    newCustomizedProperty.setPropertyTemplate(propertyTemplate);
                } else {
                    throw new BusinessException(ErrorCodeUtil.DATA_NOT_EXIST,
                            "CustomizedProperty with id : " + selfIds[i]
                                    + " is not exist in the database!");
                }
                customizedPropertyService.saveCustomizedProperty(newCustomizedProperty);
            }
        }
        return modelAndView;
    }
}