package com.augmentum.ams.service.asset.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.CustomerDao;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.remote.RemoteCustomerService;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {

    private static Logger logger = Logger.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private RemoteCustomerService remoteCustomerService;
    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.asset.CustomerService#getCustomer(java.lang
     * .String)
     */
    @Override
    public Customer getCustomer(String customerCode) {
        String hql = "from Customer customer where customer.customerCode=?";
        Customer customer = customerDao.getUnique(hql, customerCode);
        logger.info("Get customer by customer code:" + customerCode);
        return customer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.asset.CustomerService#SaveCustomer(com.augmentum
     * .ams.model.asset.Customer)
     */
    @Override
    public void saveCustomer(Customer customer) {
        customerDao.save(customer);
    }

    @Override
    public void saveCustomer(List<Customer> customers) {
        List<Customer> saveCustomers = new ArrayList<Customer>();
        for (Customer customer : customers) {
            Customer existCustomer = this.getCustomer(customer.getCustomerCode());
            if (existCustomer == null) {
                customer.setCreatedTime(new Date());
                customer.setUpdatedTime(new Date());
                saveCustomers.add(customer);
            }
        }
        customerDao.saveOrUpdateAll(saveCustomers);
    }

    @Override
    public Customer getCustomerByCode(String customerCode) {
        return customerDao.getCustomerByCode(customerCode);
    }

    @Override
    public Customer getCustomerByName(String customername) {
        return customerDao.getCustomerByName(customername);
    }

    @Override
    public List<Customer> getCustomerByGroup(String groupId) {
        String hql = "from Customer customer where customer.customerGroup.id=?";
        return customerDao.find(hql,groupId);
    }

	@Override
	public List<Customer> getCustomerListByCodes(String[] codes, HttpServletRequest request) throws BusinessException {
		List<Customer> list = new ArrayList<Customer>();
		for(String code : codes){
			Customer customer = getCustomerByCode(code);
			if(null != customer){
			list.add(getCustomerByCode(code));
			}else{
			Customer remoteCustomer = remoteCustomerService.getCustomerByCodefromIAP(request, code);
			Customer customer1 = customerDao.save(remoteCustomer);
			list.add(customer1);
			}
		}
		return list;
	}

	@Override
	public void updateCustomer(Customer customer) {
		customerDao.update(customer);
	}

    @Override
    public Map<String, Customer> findAllCustomersFromLocal() {
        
        Map<String, Customer> localCustomers = new HashMap<String, Customer>();
        List<Customer> customers =  customerDao.findAll(Customer.class);
        
        for(Customer customer : customers){
            localCustomers.put(customer.getCustomerName(), customer);
        }
        
        return localCustomers;
    }

}
