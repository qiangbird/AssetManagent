package com.augmentum.ams.model.enumeration;


/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:07:20 AM
 */
public enum PropertyTypeEnum {
	
	TEXTAREA_TYPE("textAreaType"),
	
	SELECT_TYPE("selectType"),
	
	INPUT_TYPE("inputType"),
	
	DATE_TYPE("dateType");
	
	private String propertyType;

	private PropertyTypeEnum(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public static PropertyTypeEnum getPropertyTypeEnum(String propertyType) {
		PropertyTypeEnum propertyTypeEnum = null;
		for (PropertyTypeEnum pt : PropertyTypeEnum.values()) {
			if (pt.getPropertyType().equalsIgnoreCase(propertyType)) {
				propertyTypeEnum = pt;
				break;
			}
		}
		return propertyTypeEnum;
	}

}
