package com.augmentum.ams.model.customized;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.model.user.UserCustomColumn;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:06:14 AM
 */
@Entity
@Table(name = "property_template")
public class PropertyTemplate extends BaseModel {
    
    private static final long serialVersionUID = 7888833894523440164L;

	/**
     * The property type like, select, input, date, please refer to @PropertyTypeEnum.
     */
	@Column(name = "proeperty_type", length = 32, nullable = false)
    private String propertyType;
	
	@Column(name = "asset_type", length = 32, nullable = false)
    private String assetType;
    
	/**
     * The real value of the property
     */
	@Column(length = 512)
    private String value;

	/**
     * The default sequence
     */
	@Column(length = 32, nullable = false)
    private int sequence;
    
	@Column(name = "en_name", length = 32, nullable = false)
    private String enName;
    
	@Column(name = "zh_name", length = 32, nullable = false)
    private String zhName;
    
	/**
     * If true, the attribute must be filled or selected
     */
	@Column(name="is_required")
    private boolean isRequired;
    
	/**
     * The position of the property, e.g: left or right.
     */
	@Column(length = 32, nullable = false)
    private String position;
	
	/**
     * The description of the property.
     */
	@Column(length = 300)
	private String description;

	@ManyToOne
	@JoinColumn(name = "customer_id",nullable = false)
    private Customer customer;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "propertyTemplate")
    private List<CustomizedProperty> propertySelfs = new ArrayList<CustomizedProperty>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "propertyTemplate")
    private List<UserCustomColumn> userCustomColumn = new ArrayList<UserCustomColumn>();

	@ManyToOne
	@JoinColumn(name = "creator_id",nullable = false)
    private User creator;

	public String getPropertyType() {
    	return propertyType;
    }

	public void setPropertyType(String propertyType) {
    	this.propertyType = propertyType;
    }

	public String getValue() {
    	return value;
    }

	public void setValue(String value) {
    	this.value = value;
    }

	public int getSequence() {
    	return sequence;
    }

	public void setSequence(int sequence) {
    	this.sequence = sequence;
    }

	public String getEnName() {
    	return enName;
    }

	public void setEnName(String enName) {
    	this.enName = enName;
    }

	public String getZhName() {
    	return zhName;
    }

	public void setZhName(String zhName) {
    	this.zhName = zhName;
    }

	public boolean isRequired() {
    	return isRequired;
    }

	public void setRequired(boolean isRequired) {
    	this.isRequired = isRequired;
    }

	public String getPosition() {
    	return position;
    }

	public void setPosition(String position) {
    	this.position = position;
    }

	public List<CustomizedProperty> getPropertySelfs() {
    	return propertySelfs;
    }

	public void setPropertySelfs(List<CustomizedProperty> propertySelfs) {
    	this.propertySelfs = propertySelfs;
    }

	public List<UserCustomColumn> getUserCustomColumns() {
    	return userCustomColumn;
    }

	public void setUserCustomColumns(List<UserCustomColumn> userCustomColumn) {
    	this.userCustomColumn = userCustomColumn;
    }

	public User getCreator() {
    	return creator;
    }

	public void setCreator(User creator) {
    	this.creator = creator;
    }

	public Customer getCustomer() {
    	return customer;
    }

	public void setCustomer(Customer customer) {
    	this.customer = customer;
    }

	public String getAssetType() {
    	return assetType;
    }

	public void setAssetType(String assetType) {
    	this.assetType = assetType;
    }

	public List<UserCustomColumn> getUserCustomColumn() {
    	return userCustomColumn;
    }

	public void setUserCustomColumn(List<UserCustomColumn> userCustomColumn) {
    	this.userCustomColumn = userCustomColumn;
    }
	
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
     
}
