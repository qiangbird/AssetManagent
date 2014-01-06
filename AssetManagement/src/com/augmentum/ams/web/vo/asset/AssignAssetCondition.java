package com.augmentum.ams.web.vo.asset;

public class AssignAssetCondition {

    private String userId;
    private String projectCode;
    private String projectName;
    private String customerCode;
    private String customerName;
    private String assetIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAssetIds() {
        return assetIds;
    }

    public void setAssetIds(String assetIds) {
        this.assetIds = assetIds;
    }

}
