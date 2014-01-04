package com.augmentum.ams.dao.asset;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.asset.Customer;

public interface CustomerDao extends BaseDao<Customer>{
	/**
	 * 
	 * @description Find customer as given customer code
	 * @author Jay.He
	 * @time Oct 28, 2013 1:55:28 PM
	 * @param customerCode
	 */
	public Customer getCustomerByCode(String customerCode);
	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Nov 6, 2013 3:34:02 PM
	 * @param customerName
	 * @return
	 */
	public Customer getCustomerByName(String customerName);
}
