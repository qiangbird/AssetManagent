package com.augmentum.ams.web.vo.asset;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.web.vo.validation.GroupValidation;

/**
 * Asset Vo for get data from front page
 * 
 * @author Jay.He
 * 
 */
public class SaveAssetVo {

    private String id;

    private String assetId;

    @NotBlank(message = ErrorCodeUtil.ASSET_NANE_VALIDATOR_FAILED)
    @Size(max = 36, message = ErrorCodeUtil.ASSET_NANE_VALIDATOR_LENGTH_FAILED, groups = GroupValidation.class)
    private String assetName;

    private String manufacturer;
    @NotBlank(message = ErrorCodeUtil.ASSET_TYPE_VALIDATOR_FAILED)
    private String type;

    private String barCode;

    private String seriesNo;

    private String poNo;

    private String photoPath;

    @NotBlank(message = ErrorCodeUtil.ASSET_OWNERSHIP_INVALID)
    private String ownerShip;

    private boolean fixed;
    @NotBlank(message = ErrorCodeUtil.ASSET_STATUS_INVALID)
    private String status;

    private String memo;

    private String checkInTime;

    private String checkOutTime;

    private String warrantyTime;

    private String vendor;

    private String softwareId;

    private String softwareVersion;

    private String softwareAdditionalInfo;

    private String softwareLicenseKey;

    private String softwareManagerVisible;

    private String machineId;

    private String machineSubtype;

    private String machineSpecification;

    private String machineConfiguration;

    private String machineAddress;

    private String monitorId;

    private String monitorSize;

    private String monitorDetail;

    private String deviceId;

    private String deviceSubtypeName;

    private String deviceConfiguration;

    private String otherAssetsId;

    private String otherAssetsDetail;

    private String projectId;

    private String projectCode;

    private String projectName;

    private String customerId;

    private String customerCode;

    private String customerName;

    private String userId;

    private String userName;

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }

    public String getSoftwareAdditionalInfo() {
        return softwareAdditionalInfo;
    }

    public void setSoftwareAdditionalInfo(String softwareAdditionalInfo) {
        this.softwareAdditionalInfo = softwareAdditionalInfo;
    }

    public String getSoftwareLicenseKey() {
        return softwareLicenseKey;
    }

    public void setSoftwareLicenseKey(String softwareLicenseKey) {
        this.softwareLicenseKey = softwareLicenseKey;
    }

    public String getSoftwareManagerVisible() {
        return softwareManagerVisible;
    }

    public void setSoftwareManagerVisible(String softwareManagerVisible) {
        this.softwareManagerVisible = softwareManagerVisible;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getMachineSubtype() {
        return machineSubtype;
    }

    public void setMachineSubtype(String machineSubtype) {
        this.machineSubtype = machineSubtype;
    }

    public String getMachineSpecification() {
        return machineSpecification;
    }

    public void setMachineSpecification(String machineSpecification) {
        this.machineSpecification = machineSpecification;
    }

    public String getMachineConfiguration() {
        return machineConfiguration;
    }

    public void setMachineConfiguration(String machineConfiguration) {
        this.machineConfiguration = machineConfiguration;
    }

    public String getMachineAddress() {
        return machineAddress;
    }

    public void setMachineAddress(String machineAddress) {
        this.machineAddress = machineAddress;
    }

    public String getMonitorId() {
        return monitorId;
    }

    public void setMonitorId(String monitorId) {
        this.monitorId = monitorId;
    }

    public String getMonitorSize() {
        return monitorSize;
    }

    public void setMonitorSize(String monitorSize) {
        this.monitorSize = monitorSize;
    }

    public String getMonitorDetail() {
        return monitorDetail;
    }

    public void setMonitorDetail(String monitorDetail) {
        this.monitorDetail = monitorDetail;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceSubtypeName() {
        return deviceSubtypeName;
    }

    public void setDeviceSubtypeName(String deviceSubtypeName) {
        this.deviceSubtypeName = deviceSubtypeName;
    }

    public String getDeviceConfiguration() {
        return deviceConfiguration;
    }

    public void setDeviceConfiguration(String deviceConfiguration) {
        this.deviceConfiguration = deviceConfiguration;
    }

    public String getOtherAssetsId() {
        return otherAssetsId;
    }

    public void setOtherAssetsId(String otherAssetsId) {
        this.otherAssetsId = otherAssetsId;
    }

    public String getOtherAssetsDetail() {
        return otherAssetsDetail;
    }

    public void setOtherAssetsDetail(String otherAssetsDetail) {
        this.otherAssetsDetail = otherAssetsDetail;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    @NotBlank(message = ErrorCodeUtil.ASSET_LOCATION_FAILED)
    private String location;

    @NotBlank(message = ErrorCodeUtil.ASSET_ENTITY_VALIDATOR_FAILED)
    private String entity;

    @NotBlank(message = ErrorCodeUtil.ASSET_SITE_VALIDATOR_FAILED)
    private String site;

    private String keeper;

    private String purchaseItemId;

    private String batchNumber;

    private String batchCreate;

    private String batchCount;

    public String getBatchCreate() {
        return batchCreate;
    }

    public void setBatchCreate(String batchCreate) {
        this.batchCreate = batchCreate;
    }

    public String getBatchCount() {
        return batchCount;
    }

    public void setBatchCount(String batchCount) {
        this.batchCount = batchCount;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(String seriesNo) {
        this.seriesNo = seriesNo;
    }

    public String getPoNo() {
        return poNo;
    }

    public void setPoNo(String poNo) {
        this.poNo = poNo;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getOwnerShip() {
        return ownerShip;
    }

    public void setOwnerShip(String ownerShip) {
        this.ownerShip = ownerShip;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public String getWarrantyTime() {
        return warrantyTime;
    }

    public void setWarrantyTime(String warrantyTime) {
        this.warrantyTime = warrantyTime;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(String purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

}
