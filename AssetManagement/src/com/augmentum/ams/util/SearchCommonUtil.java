package com.augmentum.ams.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.web.vo.asset.AssetListVo;

public class SearchCommonUtil {

    static Logger logger = Logger.getLogger(SearchCommonUtil.class);

    private static String getAssetListVoValueByProperty(AssetListVo assetListVo, String fieldName) {
        PropertyDescriptor pd = null;
        Object obj = null;
        try {
            pd = new PropertyDescriptor(fieldName, assetListVo.getClass());
        } catch (IntrospectionException e) {
            // TODO Auto-generated catch block
            logger.error("TODO", e);
        }
        Method method = pd.getReadMethod();

        try {
            obj = method.invoke(assetListVo);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            logger.error("TODO", e);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            logger.error("TODO", e);
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            logger.error("TODO", e);
        }

        if (null == obj) {
            return "";
        } else {
            return obj.toString();
        }
    }

    public static JSONArray formatAssetVoListTOJSONArray(List<AssetListVo> assetVoList,
            List<UserCustomColumn> userCustomColumnList) {
        JSONArray arrays = new JSONArray();

        for (AssetListVo assetListVo : assetVoList) {
            JSONArray array = new JSONArray();
            array.add(assetListVo.getId());

            for (UserCustomColumn userCustomColumn : userCustomColumnList) {
                String fieldName = userCustomColumn.getCustomizedColumn().getSortName();
                String value = "";
                if ("assetId".equals(fieldName)) {
                    value = "<a href='asset/view/" + assetListVo.getId()
                            + "' style='color: #418FB5'>"
                            + getAssetListVoValueByProperty(assetListVo, fieldName) + "</a>";
                } else {
                    value = getAssetListVoValueByProperty(assetListVo, fieldName);
                }
                array.add(value);
            }
            arrays.add(array);
        }
        return arrays;
    }
    
    //TODO test BeanUtils convert list to JSONArray
    public static JSONArray convertAssetListToJSONArray(List<Asset> assetList) {
    	JSONArray arrays = new JSONArray();
    	List<String> propertyNames = new ArrayList<String>();
    	propertyNames.add("assetId");
    	propertyNames.add("assetName");
//    	propertyNames.add("user.userName");
    	propertyNames.add("project");
    	propertyNames.add("project.projectName");
    	propertyNames.add("customer.customerName");
    	propertyNames.add("checkInTime");
    	
    		
    	
    	for (Asset asset : assetList) {
    		JSONArray array = new JSONArray();
    		
    		
    		for (String propertyName : propertyNames) {
    			String value = "";
    			try {
    				
    				//TODO judge associate is null
//    				if (null != BeanUtils.getProperty(asset, "user")) {
//    					value = BeanUtils.getProperty(asset, "user.userName");
//    				}
    				
					value = BeanUtils.getProperty(asset, propertyName);
					logger.info(propertyName + "--" + value);
				} catch (IllegalAccessException e) {
					logger.info("IllegalAccessException");
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					logger.info("InvocationTargetException");
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					logger.info("NoSuchMethodException");
					e.printStackTrace();
				}
    			array.add(value);
    		}
    		arrays.add(array);
    	}
    	return arrays;
    }

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Jan 3, 2014 4:04:07 PM
     * @param assetVoList
     * @param userCustomColumnList
     * @return
     */
    public static JSONArray formatLocationListTOJSONArray(List<Location> locationList) {
        JSONArray arrays = new JSONArray();

        for (Location location : locationList) {
            JSONArray array = new JSONArray();
            array.add(location.getId());
            String value = "";
            value = location.getSite();
            array.add(value);
            value = location.getRoom();
            array.add(value);
            value = "<div class='editLocationIcon'></div>/<div class='deleteLocationIcon'></div>";
            array.add(value);
            arrays.add(array);
        }
        return arrays;
    }
}
