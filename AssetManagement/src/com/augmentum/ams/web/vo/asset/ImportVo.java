package com.augmentum.ams.web.vo.asset;

import jxl.Sheet;

import com.augmentum.ams.model.asset.Asset;

public class ImportVo {

    private int allImportRecords;
    
    private int successRecords;
    
    private int failureRecords;
    
    private String failureFileName;
    
    private boolean isErrorRecorde;
    
    private Asset asset;
    
    private Sheet machineSheet;
    
    private Sheet monitorSheet;
    
    private Sheet deviceSheet;
    
    private Sheet softwareSheet;
    
    private Sheet otherAssetsSheet;
    
    private int machineTotalRecords;
    
    private int monitorTotalRecords;
    
    private int deviceTotalRecords;
    
    private int softwareTotalRecords;
    
    private int otherAssetTotalRecords;

    public int getAllImportRecords() {
        return allImportRecords;
    }

    public void setAllImportRecords(int allImportRecords) {
        this.allImportRecords = allImportRecords;
    }

    public int getSuccessRecords() {
        return successRecords;
    }

    public void setSuccessRecords(int successRecords) {
        this.successRecords = successRecords;
    }

    public int getFailureRecords() {
        return failureRecords;
    }

    public void setFailureRecords(int failureRecords) {
        this.failureRecords = failureRecords;
    }

    public String getFailureFileName() {
        return failureFileName;
    }

    public void setFailureFileName(String failureFileName) {
        this.failureFileName = failureFileName;
    }

    public boolean isErrorRecorde() {
        return isErrorRecorde;
    }

    public void setErrorRecorde(boolean isErrorRecorde) {
        this.isErrorRecorde = isErrorRecorde;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public Sheet getMachineSheet() {
        return machineSheet;
    }

    public void setMachineSheet(Sheet machineSheet) {
        this.machineSheet = machineSheet;
    }

    public Sheet getMonitorSheet() {
        return monitorSheet;
    }

    public void setMonitorSheet(Sheet monitorSheet) {
        this.monitorSheet = monitorSheet;
    }

    public Sheet getDeviceSheet() {
        return deviceSheet;
    }

    public void setDeviceSheet(Sheet deviceSheet) {
        this.deviceSheet = deviceSheet;
    }

    public Sheet getSoftwareSheet() {
        return softwareSheet;
    }

    public void setSoftwareSheet(Sheet softwareSheet) {
        this.softwareSheet = softwareSheet;
    }

    public Sheet getOtherAssetsSheet() {
        return otherAssetsSheet;
    }

    public void setOtherAssetsSheet(Sheet otherAssetsSheet) {
        this.otherAssetsSheet = otherAssetsSheet;
    }

    public int getMachineTotalRecords() {
        return machineTotalRecords;
    }

    public void setMachineTotalRecords(int machineTotalRecords) {
        this.machineTotalRecords = machineTotalRecords;
    }

    public int getMonitorTotalRecords() {
        return monitorTotalRecords;
    }

    public void setMonitorTotalRecords(int monitorTotalRecords) {
        this.monitorTotalRecords = monitorTotalRecords;
    }

    public int getDeviceTotalRecords() {
        return deviceTotalRecords;
    }

    public void setDeviceTotalRecords(int deviceTotalRecords) {
        this.deviceTotalRecords = deviceTotalRecords;
    }

    public int getSoftwareTotalRecords() {
        return softwareTotalRecords;
    }

    public void setSoftwareTotalRecords(int softwareTotalRecords) {
        this.softwareTotalRecords = softwareTotalRecords;
    }

    public int getOtherAssetTotalRecords() {
        return otherAssetTotalRecords;
    }

    public void setOtherAssetTotalRecords(int otherAssetTotalRecords) {
        this.otherAssetTotalRecords = otherAssetTotalRecords;
    }
    
}
