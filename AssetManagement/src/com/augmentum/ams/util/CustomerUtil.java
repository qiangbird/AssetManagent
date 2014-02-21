package com.augmentum.ams.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.augmentum.ams.web.vo.asset.CustomerVo;

public class CustomerUtil {

    public static JSONArray changeCustomerToJson(List<CustomerVo> customerInfo) {

        JSONArray customers = new JSONArray();
        for (CustomerVo customer : customerInfo) {
            JSONObject customerObject = new JSONObject();
            customerObject.put("customerName", customer.getCustomerName());
            customerObject.put("customerCode", customer.getCustomerCode());
            customers.add(customerObject);
        }
        return customers;
    }
}
