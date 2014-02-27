package com.augmentum.ams.service.customized;

import java.util.List;

import com.augmentum.ams.model.customized.CustomizedProperty;

public interface CustomizedPropertyService {
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 2, 2013 2:12:31 PM
     * @param id
     * @return
     */
    public CustomizedProperty getCustomizedPropertyById(String id);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 2, 2013 2:12:34 PM
     * @param customizedProperty
     */
    public void saveCustomizedProperty(CustomizedProperty customizedProperty);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 2, 2013 2:12:37 PM
     * @param customizedProperty
     */
    public void updateCustomizedProperty(CustomizedProperty customizedProperty);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 2, 2013 2:20:12 PM
     * @param templateId
     * @return
     */
    public CustomizedProperty getByAssetIdAndTemplateId(String assetId, String templateId);

    public void deleteByTemplateId(String id);

    public List<CustomizedProperty> getByPropertyTemplateId(String id);
}
