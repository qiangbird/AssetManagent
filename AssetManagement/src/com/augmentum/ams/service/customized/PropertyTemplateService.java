package com.augmentum.ams.service.customized;

import java.text.ParseException;

import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.web.vo.customized.PropertyTemplateVo;
import net.sf.json.JSONArray;

public interface PropertyTemplateService {

	void savePropertyTemplate(PropertyTemplateVo propertyTemplateVo, Customer customer, User user);

	/**
     * @description TODO
     * @author Rudy.Gao
     * @time Oct 16, 2013 4:32:34 PM
     * @param id
     * @return
	 * @throws ParseException 
     */
    PropertyTemplate getPropertyTemplateById(String id) throws ParseException;

    /**
     * @description TODO
     * @author Jay.He
     * @time Oct 25, 2013 9:45:35 AM
     * @param userId
     * @param customerCode
     * @param assetType
     * @return
     * @throws ParseException 
     */
    JSONArray findPropertyTemplateByCustomerAndAssetType(String customerName,String assetType) throws ParseException;
    
}
