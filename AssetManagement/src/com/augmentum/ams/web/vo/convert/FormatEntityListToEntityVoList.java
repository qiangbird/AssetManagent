package com.augmentum.ams.web.vo.convert;

import com.augmentum.ams.exception.SystemException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Device;
import com.augmentum.ams.model.asset.DeviceSubtype;
import com.augmentum.ams.model.asset.Machine;
import com.augmentum.ams.model.asset.Monitor;
import com.augmentum.ams.model.asset.OtherAssets;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.asset.PurchaseItem;
import com.augmentum.ams.model.asset.Software;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.AssetVo;
import com.augmentum.ams.web.vo.asset.SaveAssetVo;

public class FormatEntityListToEntityVoList {

    public static void purchaseItemToAssetVo(PurchaseItem purchaseItem, AssetVo assetVo,
            String timeOffset) {

        try {
            assetVo.setPurchaseItemId(purchaseItem.getId());

            Customer customer = new Customer();

            customer.setCustomerCode(purchaseItem.getCustomerCode());
            customer.setCustomerName(purchaseItem.getCustomerName());

            assetVo.setCustomer(customer);
            assetVo.setType(purchaseItem.getItemType());
            assetVo.setSite(purchaseItem.getDataSite().replace("Augmentum ", ""));
            assetVo.setEntity(purchaseItem.getEntitySite());
            assetVo.setPoNo(purchaseItem.getPoNo());
            assetVo.setAssetName(purchaseItem.getItemName());

            Project project = new Project();

            project.setProjectCode(purchaseItem.getProjectCode());
            project.setProjectName(purchaseItem.getProjectName());

            assetVo.setProject(project);
            assetVo.setVendor(purchaseItem.getVendorName());
            assetVo.setBatchNumber(String.valueOf(purchaseItem.getFinalQuantity()));
            assetVo.setCheckInTime(UTCTimeUtil.utcToLocalTime(purchaseItem.getDeliveryDate(),
                    timeOffset, "yyyy-MM-dd"));

        } catch (Exception e) {
            throw new SystemException(e, ErrorCodeUtil.DATA_NOT_EXIST,
                    "Convert purchase to assetVo");
        }
    }

    public static void saveAssetVoToAssetVo(AssetVo assetVo, SaveAssetVo saveAssetVo) {
        Customer customer = new Customer();
        User user = new User();
        Project project = new Project();
        Machine machine = new Machine();
        Device device = new Device();
        DeviceSubtype deviceSubtype = new DeviceSubtype();
        Monitor monitor = new Monitor();
        Software software = new Software();
        OtherAssets otherAssets = new OtherAssets();

        customer.setId(saveAssetVo.getCustomerId());
        customer.setCustomerCode(saveAssetVo.getCustomerCode());
        customer.setCustomerName(saveAssetVo.getCustomerName());

        user.setUserId(saveAssetVo.getUserId());
        user.setUserName(saveAssetVo.getUserName());

        project.setId(saveAssetVo.getProjectId());
        project.setProjectCode(saveAssetVo.getProjectCode());
        project.setProjectName(saveAssetVo.getProjectName());

        machine.setId(saveAssetVo.getMachineId());
        machine.setAddress(saveAssetVo.getMachineAddress());
        machine.setConfiguration(saveAssetVo.getMachineConfiguration());
        machine.setSpecification(saveAssetVo.getMachineSpecification());
        machine.setSubtype(saveAssetVo.getMachineSubtype());

        device.setConfiguration(saveAssetVo.getDeviceConfiguration());
        device.setId(saveAssetVo.getDeviceId());
        deviceSubtype.setSubtypeName(saveAssetVo.getDeviceSubtypeName());
        device.setDeviceSubtype(deviceSubtype);

        monitor.setId(saveAssetVo.getMonitorId());
        monitor.setDetail(saveAssetVo.getMonitorDetail());
        monitor.setSize(saveAssetVo.getMonitorSize());

        software.setId(saveAssetVo.getSoftwareId());
        software.setAdditionalInfo(saveAssetVo.getSoftwareAdditionalInfo());
        software.setLicenseKey(saveAssetVo.getSoftwareLicenseKey());
        software.setManagerVisible(Boolean.getBoolean(saveAssetVo.getSoftwareManagerVisible()));
        software.setVersion(saveAssetVo.getSoftwareVersion());

        otherAssets.setId(saveAssetVo.getOtherAssetsId());
        otherAssets.setDetail(saveAssetVo.getOtherAssetsDetail());

        assetVo.setId(saveAssetVo.getId());
        assetVo.setAssetId(saveAssetVo.getAssetId());
        assetVo.setAssetName(saveAssetVo.getAssetName());
        assetVo.setBarCode(saveAssetVo.getBarCode());
        assetVo.setBatchCount(saveAssetVo.getBatchCount());
        assetVo.setBatchCreate(saveAssetVo.getBatchCreate());
        assetVo.setBatchNumber(saveAssetVo.getBatchNumber());
        assetVo.setCheckInTime(saveAssetVo.getCheckInTime());
        assetVo.setCheckOutTime(saveAssetVo.getCheckOutTime());
        assetVo.setCustomer(customer);
        assetVo.setDevice(device);
        assetVo.setEntity(saveAssetVo.getEntity());
        assetVo.setFixed(saveAssetVo.isFixed());
        assetVo.setKeeper(saveAssetVo.getKeeper());
        assetVo.setLocation(saveAssetVo.getLocation());
        assetVo.setMachine(machine);
        assetVo.setManufacturer(saveAssetVo.getManufacturer());
        assetVo.setMemo(saveAssetVo.getMemo());
        assetVo.setMonitor(monitor);
        assetVo.setOtherAssets(otherAssets);
        assetVo.setOwnerShip(saveAssetVo.getOwnerShip());
        assetVo.setPhotoPath(saveAssetVo.getPhotoPath());
        assetVo.setPoNo(saveAssetVo.getPoNo());
        assetVo.setProject(project);
        assetVo.setPurchaseItemId(saveAssetVo.getPurchaseItemId());
        assetVo.setSeriesNo(saveAssetVo.getSeriesNo());
        assetVo.setSite(saveAssetVo.getSite());
        assetVo.setSoftware(software);
        assetVo.setStatus(saveAssetVo.getStatus());
        assetVo.setType(saveAssetVo.getType());
        assetVo.setUser(user);
        assetVo.setVendor(saveAssetVo.getVendor());
        assetVo.setWarrantyTime(saveAssetVo.getWarrantyTime());
    }
}
