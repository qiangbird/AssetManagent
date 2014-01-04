package com.augmentum.ams.web.vo.customized;

public class PropertyTemplateVo {
	
	private String selfProperties;
	
	private String customerCode;
	
	private String assetType;
	
	public PropertyTemplateVo(String selfProperties,
	        String customerCode, String assetType){
	    
	    this.selfProperties = selfProperties;
	    this.customerCode = customerCode;
	    this.assetType = assetType;
	}
	
	private String defaultValue;

	public String getSelfProperties() {
		return selfProperties;
	}

	public void setSelfProperties(String selfProperties) {
		this.selfProperties = selfProperties;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
	
	
}
