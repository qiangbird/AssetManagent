package com.augmentum.ams.service.asset.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.CustomerDao;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.common.LabelAndValue;

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
        DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class);
        criteria.createAlias("customerGroup", "customerGroup")
                .add(Restrictions.eq("customerGroup.isExpired", Boolean.FALSE))
                .add(Restrictions.eq("customerGroup.id", groupId))
                .add(Restrictions.eq("isExpired", Boolean.FALSE));
        return customerDao.findByCriteria(criteria);
    }

    @Override
    public List<Customer> getCustomerListByCodes(String[] codes, HttpServletRequest request)
            throws BusinessException {
        List<Customer> list = new ArrayList<Customer>();
        for (String code : codes) {
            Customer customer = getCustomerByCode(code);
            if (null != customer) {
                list.add(getCustomerByCode(code));
            } else {
                Customer remoteCustomer = remoteCustomerService.getCustomerByCodefromIAP(request,
                        code);
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
        List<Customer> customers = customerDao.findAll(Customer.class);

        for (Customer customer : customers) {
            localCustomers.put(customer.getCustomerName(), customer);
        }

        return localCustomers;
    }

    @Override
    public List<LabelAndValue> changeCustomerToLabelAndValue(List<CustomerVo> customerList) {

        List<LabelAndValue> labelAndValueCustomer = new ArrayList<LabelAndValue>();

        for (CustomerVo customerVo : customerList) {
            LabelAndValue customer = new LabelAndValue();
            customer.setLabel(customerVo.getCustomerName());
            customer.setValue(customerVo.getCustomerCode());
            labelAndValueCustomer.add(customer);
        }
        return labelAndValueCustomer;
    }

}
