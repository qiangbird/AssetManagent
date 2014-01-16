package com.augmentum.ams.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.sf.json.JSONArray;

import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.asset.TransferLog;
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
            List<UserCustomColumn> userCustomColumnList, String uuid) {
        JSONArray arrays = new JSONArray();

        for (AssetListVo assetListVo : assetVoList) {
            JSONArray array = new JSONArray();
            array.add(assetListVo.getId());

            for (UserCustomColumn userCustomColumn : userCustomColumnList) {
                String fieldName = userCustomColumn.getCustomizedColumn().getSortName();
                String value = "";
                if ("assetId".equals(fieldName)) {
                	if(!StringUtils.isBlank(uuid)){
                    value = "<a href='asset/view/" + assetListVo.getId()
                            +"?uuid="+uuid+ "' style='color: #418FB5'>"
                            + getAssetListVoValueByProperty(assetListVo, fieldName) + "</a>";
                	}else{
                		 value = "<a href='asset/view/" + assetListVo.getId()
                                 +"' style='color: #418FB5'>"
                                 + getAssetListVoValueByProperty(assetListVo, fieldName) + "</a>";
                	}
                } else {
                    value = getAssetListVoValueByProperty(assetListVo, fieldName);
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
    
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Jan 3, 2014 4:04:07 PM
     * @param assetVoList
     * @param userCustomColumnList
     * @return
     */
    public static JSONArray formatGroupListTOJSONArray(List<CustomerGroup> groupList) {
        JSONArray arrays = new JSONArray();

        for (CustomerGroup group : groupList) {
            JSONArray array = new JSONArray();
            array.add(group.getId());
            String value = "";
            value = group.getGroupName();
            array.add(value);
            value = group.getDescription();
            array.add(value);
            value = group.getProcessType().toString();
            array.add(value);
            StringBuffer sb = new StringBuffer();
            List<Customer> list = group.getCustomers();
            for(Customer customer : list){
            	sb.append(customer.getCustomerName()).append(";");
            }
            array.add(sb.toString());
            
            value = "<div class='editGroupIcon'></div>/<div class='deleteGroupIcon'></div>";
            array.add(value);
            arrays.add(array);
        }
        return arrays;
    }

	public static JSONArray formatTransferLogListTOJSONArray(
			List<TransferLog> result,List<UserCustomColumn> userCustomColumnList) {
		
	      JSONArray arrays = new JSONArray();

	        for (TransferLog transferLog : result) {
	            JSONArray array = new JSONArray();
	            array.add(transferLog.getId());
	            array.add(transferLog.getAsset().getAssetId());
				array.add(transferLog.getAsset().getAssetName());
				array.add(transferLog.getEmployee().getUserName());
				array.add(UTCTimeUtil.formatDateToString(transferLog.getTime()));
				array.add(transferLog.getAction());
				array.add(transferLog.getAsset().getManufacturer());
				array.add(transferLog.getAsset().getBarCode());
				array.add(UTCTimeUtil.formatDateToString(transferLog.getAsset().getCheckInTime()));
				array.add(transferLog.getAsset().getSeriesNo());
				array.add(transferLog.getAsset().getPoNo());

	            arrays.add(array);
	        }
	        return arrays;
		
	}
}
