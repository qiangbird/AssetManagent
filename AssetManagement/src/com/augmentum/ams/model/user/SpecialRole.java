package com.augmentum.ams.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.augmentum.ams.model.base.BaseModel;


/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 10:03:36 AM
 */
@Entity
@Table(name = "special_role")
public class SpecialRole extends BaseModel{
	
    private static final long serialVersionUID = -7000358517994480442L;

	/**
	 * The ID of the user who has special_role
	 */
	@Column(name = "user_id", length= 32)
	private String userId;
	
	/**
	 * The name of the user who has special_role
	 */
	@Column(name = "user_name", length= 32)
	private String userName;
	
	/**
	 * The customer code of customer which managed by special_role
	 */
	@Column(name = "customer_code", length= 32)
	private String customerCode;

	public String getUserId() {
    	return userId;
    }

	public void setUserId(String userId) {
    	this.userId = userId;
    }

	public String getUserName() {
    	return userName;
    }

	public void setUserName(String userName) {
    	this.userName = userName;
    }

	public String getCustomerCode() {
    	return customerCode;
    }

	public void setCustomerCode(String customerCode) {
    	this.customerCode = customerCode;
    }
}
