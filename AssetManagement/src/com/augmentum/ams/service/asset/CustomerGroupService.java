package com.augmentum.ams.service.asset;

import java.util.List;

import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public interface CustomerGroupService {
    public void saveCustomerGroup(CustomerGroup customerGroup);
    public List<CustomerGroup> findAllGroups();
    public CustomerGroup getCustomerGroupById(String customerGroupId);
    public void deleteCustomerGroupById(String customerGroupId);
    public void updateCustomerGroup(CustomerGroup customerGroup);
    
    public Page<CustomerGroup> findCustomerGroupBySearchCondition(SearchCondition searchCondition);
}
