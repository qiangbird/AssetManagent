package com.augmentum.ams.service.asset;

import java.util.List;

import com.augmentum.ams.model.asset.CustomerGroup;

public interface CustomerGroupService {
    public void saveCustomerGroup(CustomerGroup customerGroup);
    public List<CustomerGroup> findAllGroups();
    public CustomerGroup getCustomerGroupById(String customerGroupId);
    public void deleteCustomerGroupById(String customerGroupId);
    public void updateCustomerGroup(CustomerGroup customerGroup);
}
