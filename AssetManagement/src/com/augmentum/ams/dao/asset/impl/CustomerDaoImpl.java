package com.augmentum.ams.dao.asset.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.CustomerDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Customer;

@Repository("customerDao")
public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDao {

    @Override
    public Customer getCustomerByCode(String customerCode) {
        String hql = "from Customer customer where customer.customerCode=?";
        return super.getUnique(hql, customerCode.trim());
    }

    @Override
    public Customer getCustomerByName(String customerName) {
        String hql = "from Customer customer where customer.customerName=?";
        return super.getUnique(hql, customerName.trim());
    }

}
