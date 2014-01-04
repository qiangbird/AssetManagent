package com.augmentum.ams.model.enumeration;

public enum CategoryTypeEnum {
    
    ASSET("asset"),
    
	DEVICE("device"),
	
	MACHINE("machine"),
	
	MONITOR("monitor"),
	
	SOFTWARE("software"),
	
	OTHERASSETS("other assets"),
    
    LOCATION("location"),
    
    TRANSFER_LOG("transfer log"),
    
    OPERATION_LOG("operation log");
    
    private String categoryType;
    
    private CategoryTypeEnum(String categoryType) {
        this.categoryType = categoryType;
    }
    
    public String getCategoryType() {
        return categoryType;
    }
    
    public static CategoryTypeEnum getCategoryTypeEnum(String categoryType) {
        CategoryTypeEnum categoryTypeEnum = null;
        for (CategoryTypeEnum ct : CategoryTypeEnum.values()) {
            if (ct.getCategoryType().equalsIgnoreCase(categoryType)) {
                categoryTypeEnum = ct;
                break;
            }
        }
        return categoryTypeEnum;
    }
}
