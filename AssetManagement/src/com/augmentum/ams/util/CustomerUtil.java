package com.augmentum.ams.util;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.augmentum.ams.model.asset.Customer;

public class CustomerUtil {

    public static JSONArray changeCustomerToJson(List<Customer> customerInfo) {
        
        JSONArray customers = new JSONArray();
        for(Customer customer : customerInfo) {
            JSONObject customerObject = new JSONObject();
            customerObject.put("customerName", customer.getCustomerName());
            customerObject.put("customerCode", customer.getCustomerCode());
            customers.add(customerObject);
        }
        return customers;
    }
}
