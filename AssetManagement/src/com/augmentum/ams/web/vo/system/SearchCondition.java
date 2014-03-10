package com.augmentum.ams.web.vo.system;


public class SearchCondition {

    private String keyWord;
    
    private String sortName;
    
    private String sortSign = "desc";
    
    private Integer pageSize = 10;
    
    private Integer pageNum = 1;
    
    private String searchFields;
    
    private String assetStatus;
    
    private String assetType;
    
    private String fromTime;
    
    private String toTime;
    
    private String customizedViewId;
    
    private String userUuid;

    private String auditFileName;
    
    private String auditFlag;
    
    private Boolean isFixedAsset;
    
    private Boolean isWarrantyExpired;
    
    private Boolean isGetAllRecords;
    
    private Boolean isITRole;
    
    private Boolean isSystemAdminRole;
    
    public String getCustomizedViewId() {
        return customizedViewId;
    }

    public void setCustomizedViewId(String customizedViewId) {
        this.customizedViewId = customizedViewId;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortSign() {
        return sortSign;
    }

    public void setSortSign(String sortSign) {
        this.sortSign = sortSign;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(String searchFields) {
        this.searchFields = searchFields;
    }

    public String getAssetStatus() {
        return assetStatus;
    }

    public void setAssetStatus(String assetStatus) {
        this.assetStatus = assetStatus;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    public String getToTime() {
        return toTime;
    }

    public void setToTime(String toTime) {
        this.toTime = toTime;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }
	public String getAuditFileName() {
		return auditFileName;
	}

	public void setAuditFileName(String auditFileName) {
		this.auditFileName = auditFileName;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public Boolean getIsFixedAsset() {
		return isFixedAsset;
	}

	public void setIsFixedAsset(Boolean isFixedAsset) {
		this.isFixedAsset = isFixedAsset;
	}

	public Boolean getIsWarrantyExpired() {
		return isWarrantyExpired;
	}

	public void setIsWarrantyExpired(Boolean isWarrantyExpired) {
		this.isWarrantyExpired = isWarrantyExpired;
	}

	public Boolean getIsGetAllRecords() {
		return isGetAllRecords;
	}

	public void setIsGetAllRecords(Boolean isGetAllRecords) {
		this.isGetAllRecords = isGetAllRecords;
	}

    public Boolean getIsITRole() {
        return isITRole;
    }

    public void setIsITRole(Boolean isITRole) {
        this.isITRole = isITRole;
    }

    public Boolean getIsSystemAdminRole() {
        return isSystemAdminRole;
    }

    public void setIsSystemAdminRole(Boolean isSystemAdminRole) {
        this.isSystemAdminRole = isSystemAdminRole;
    }

}