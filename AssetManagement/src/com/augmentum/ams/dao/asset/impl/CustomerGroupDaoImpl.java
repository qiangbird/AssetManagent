package com.augmentum.ams.dao.asset.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.CustomerGroupDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.CustomerGroup;
@Repository("customerGroupDao")
public class CustomerGroupDaoImpl  extends BaseDaoImpl<CustomerGroup> implements CustomerGroupDao{

	@Override
	public void updateCustomerGroup(CustomerGroup customerGroup) {
		hibernateTemplate.merge(customerGroup);
		
	}


}
