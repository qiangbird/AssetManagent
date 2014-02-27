package com.augmentum.ams.web.controller.customized;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.customized.CustomizedProperty;
import com.augmentum.ams.service.customized.CustomizedPropertyService;
import com.augmentum.ams.util.ErrorCodeUtil;

@Controller("customizedProperyController")
@RequestMapping(value = "/customizedPropery")
public class CustomizedProperyController {

    @Autowired
    private CustomizedPropertyService customizedPropertyService;

    @RequestMapping(value = "updateSelfDefinedProperties")
    public ModelAndView updateSelfDefinedProperties(String assetId, String selfDefinedNames,
            String selfDefinedIds, String selfDefinedValues) throws ParseException {
        ModelAndView modelAndView = new ModelAndView();

        String selfIds[] = selfDefinedIds.substring(0, selfDefinedIds.lastIndexOf(",")).split(",");
        String selfValues[] = selfDefinedValues.substring(0, selfDefinedValues.lastIndexOf(","))
                .split(",");

        for (int i = 0; i < selfIds.length; i++) {
            CustomizedProperty customizedProperty = customizedPropertyService
                    .getByAssetIdAndTemplateId(assetId, selfIds[i]);

            if (null != customizedProperty) {
                customizedProperty.setValue(selfValues[i]);
                customizedPropertyService.updateCustomizedProperty(customizedProperty);
            } else {
                throw new BusinessException(ErrorCodeUtil.DATA_NOT_EXIST,
                        "CustomizedProperty with id : " + selfIds[i]
                                + " is not exist in the database!");
            }
        }

        return modelAndView;
    }
}