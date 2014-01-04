package com.augmentum.ams.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.augmentum.ams.model.asset.CustomizedColumn;
import com.augmentum.ams.model.user.UserCustomColumn;

public class Formater {

    /**
     * Transfer customizedColumnList to JSONArray, the result you get will be used in DataList.js "columns" parameter. 
     * @author Geoffrey.Zhao
     * @param list
     * @return
     */
    public static JSONArray formateUserColumnListToJSONArray(List<UserCustomColumn> list) {
        JSONArray jsonArray = new JSONArray();
        
        for (UserCustomColumn userCustomColumn : list) {
            JSONObject temp = new JSONObject();
            CustomizedColumn customizedColumn = userCustomColumn.getCustomizedColumn();
            temp.put("headerId", userCustomColumn.getId());
            temp.put("EN", customizedColumn.getEnName());
            temp.put("ZH", customizedColumn.getZhName());
            temp.put("sortName", customizedColumn.getSortName());
            temp.put("width", customizedColumn.getWidth());
            temp.put("isMustShow", customizedColumn.getIsMustShow());
            temp.put("showDefault", userCustomColumn.getShowDefault());
            jsonArray.add(temp);
        }
        return jsonArray;
    }
    
    /**
     * Transfer columnName in customizedColumnList to string, the result you get will be used in DataList.js "setShow" function parameter.
     * @author Geoffrey.Zhao
     * @param list
     * @return
     */
    public static String formateUserColumnNameToString (List<UserCustomColumn> list) {
        StringBuilder stringBuilder = new StringBuilder();
        
        for (UserCustomColumn userCustomColumn : list) {
            stringBuilder.append(userCustomColumn.getCustomizedColumn().getEnName());
            stringBuilder.append(',');
        }
        return stringBuilder.substring(0, stringBuilder.length() - 1);
    }
    
    /**
     * Transfer two array to one LinkedHashMap, it will be used at updateCustomColumns param.
     * @author Geoffrey.Zhao
     * @param arr1
     * @param arr2
     * @return
     */
    public static Map<String, Boolean> formateArrayToMap(String[] arr1, Boolean[] arr2) {
        Map<String, Boolean> result = new LinkedHashMap<String, Boolean>();
        
        for (int i = 0; i < arr1.length; i++) {
            result.put(arr1[i], arr2[i]);
        }
        return result;
    }
}
