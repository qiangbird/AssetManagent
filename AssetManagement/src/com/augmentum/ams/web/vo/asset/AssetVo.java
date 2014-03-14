package com.augmentum.ams.web.vo.asset;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Device;
import com.augmentum.ams.model.asset.Machine;
import com.augmentum.ams.model.asset.Monitor;
import com.augmentum.ams.model.asset.OtherAssets;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.asset.Software;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.web.vo.validation.GroupValidation;

/**
 * Asset Vo for get data from front page
 * 
 * @author Jay.He
 * 
 */
public class AssetVo {

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

    private Software software;

    private Machine machine;

    private Monitor monitor;

    private Device device;

    private OtherAssets otherAssets;

    @NotBlank(message = ErrorCodeUtil.ASSET_LOCATION_FAILED)
    private String location;

    @NotBlank(message = ErrorCodeUtil.ASSET_ENTITY_VALIDATOR_FAILED)
    private String entity;

    private Project project;
    @Valid
    private Customer customer;

    @NotBlank(message = ErrorCodeUtil.ASSET_SITE_VALIDATOR_FAILED)
    private String site;

    private User user;

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

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Monitor getMonitor() {
        return monitor;
    }

    public void setMonitor(Monitor monitor) {
        this.monitor = monitor;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public OtherAssets getOtherAssets() {
        return otherAssets;
    }

    public void setOtherAssets(OtherAssets otherAssets) {
        this.otherAssets = otherAssets;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "AssetVo [id=" + id + ", assetId=" + assetId + ", assetName=" + assetName
                + ", manufacturer=" + manufacturer + ", type=" + type + ", barCode=" + barCode
                + ", seriesNo=" + seriesNo + ", poNo=" + poNo + ", photoPath=" + photoPath
                + ", ownerShip=" + ownerShip + ", fixed=" + fixed + ", status=" + status
                + ", memo=" + memo + ", checkInTime=" + checkInTime + ", checkOutTime="
                + checkOutTime + ", warrantyTime=" + warrantyTime + ", vendor=" + vendor
                + ", software=" + software + ", machine=" + machine + ", monitor=" + monitor
                + ", device=" + device + ", otherAssets=" + otherAssets + ", location=" + location
                + ", entity=" + entity + ", project=" + project + ", customer=" + customer
                + ", user=" + user + ", keeper=" + keeper + "]";
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
