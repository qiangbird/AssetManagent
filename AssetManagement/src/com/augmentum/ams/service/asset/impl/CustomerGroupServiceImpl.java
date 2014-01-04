package com.augmentum.ams.service.asset.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.CustomerGroupDao;
import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.service.asset.CustomerGroupService;
@Service("customerGroupService")
public class CustomerGroupServiceImpl implements CustomerGroupService{
    @Autowired
    private CustomerGroupDao customerGroupDao;
    @Override
    public void saveCustomerGroup(CustomerGroup customerGroup) {
        customerGroupDao.save(customerGroup);
    }
    @Override
    public List<CustomerGroup> findAllGroups() {
        return customerGroupDao.findAll(CustomerGroup.class);
    }
    @Override
    public CustomerGroup getCustomerGroupById(String customerGroupId) {
        String hql = "from CustomerGroup where id = ?";
        return customerGroupDao.getUnique(hql, customerGroupId);
    }
    @Override
    public void deleteCustomerGroupById(String customerGroupId) {
        CustomerGroup customerGroup = getCustomerGroupById(customerGroupId);
        customerGroupDao.delete(customerGroup);
    }
    @Override
    public void updateCustomerGroup(CustomerGroup customerGroup) {
        customerGroupDao.update(customerGroup);
    }
}
