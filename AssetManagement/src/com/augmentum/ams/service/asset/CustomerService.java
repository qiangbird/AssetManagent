package com.augmentum.ams.service.asset;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.asset.Customer;

public interface CustomerService {

    public Customer getCustomer(String customerCode);

    public void saveCustomer(Customer customer);

    /**
     * @description TODO
     * @author Rudy.Gao
     * @time Oct 17, 2013 10:48:50 AM
     * @param customers
     */
    void saveCustomer(List<Customer> customers);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Oct 28, 2013 1:58:37 PM
     * @param customerCode
     */
    public Customer getCustomerByCode(String customerCode);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 6, 2013 3:35:18 PM
     * @param customername
     * @return
     */
    public Customer getCustomerByName(String customername);
    
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 6, 2013 3:35:18 PM
     * @param customername
     * @return
     */
    public List<Customer> getCustomerByGroup(String groupId);
    
    public List<Customer> getCustomerListByCodes(String[] codes, HttpServletRequest request)  throws DataException;
    
    public void updateCustomer(Customer customer);

}
