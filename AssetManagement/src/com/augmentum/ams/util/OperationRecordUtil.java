package com.augmentum.ams.util;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Device;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.asset.Machine;
import com.augmentum.ams.model.asset.Monitor;
import com.augmentum.ams.model.asset.OtherAssets;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.asset.Software;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.web.vo.asset.AssetVo;

public class OperationRecordUtil {

    /**
     * 
     * @param asset
     * @param asset
     * @param timeOffset
     * @return
     */
    // TODO: each type asset can not be saved in transfer log, and the compareCommonProperty method is not fine
    // so extract asset part to handle respective property for machine, monitor, device etc.
    public static String compareAssetProperty(Asset asset, AssetVo assetVo, String timeOffset) {
        
        StringBuilder str = new StringBuilder();
        str.append("update asset");
        
        str.append(compareStringValue(asset.getAssetName(), assetVo.getAssetName(), "AssetName"));
        str.append(compareStringValue(asset.getBarCode(), assetVo.getBarCode(), "Barcode"));
        str.append(compareStringValue(asset.getSeriesNo(), assetVo.getSeriesNo(), "SeriesNo"));
        str.append(compareStringValue(asset.getPoNo(), assetVo.getPoNo(), "PoNo"));
        str.append(compareStringValue(asset.getStatus(), assetVo.getStatus(), "Status"));
        str.append(compareStringValue(asset.getEntity(), assetVo.getEntity(), "Entity"));
        str.append(compareStringValue(asset.getManufacturer(), assetVo.getManufacturer(), "Manufacturer"));
        str.append(compareStringValue(asset.getVendor(), assetVo.getVendor(), "Vendor"));
        str.append(compareStringValue(asset.getMemo(), assetVo.getMemo(), "Memo"));
        str.append(compareStringValue(asset.getPhotoPath(), assetVo.getPhotoPath(), "PhotoPath"));
        
        str.append(compareBooleanValue(asset.isFixed(), assetVo.isFixed(), "Fixed"));
        
        str.append(compareDateValue(asset.getCheckInTime(), assetVo.getCheckInTime(), "CheckInTime", timeOffset));
        str.append(compareDateValue(asset.getCheckOutTime(), assetVo.getCheckOutTime(), "CheckOutTime", timeOffset));
        str.append(compareDateValue(asset.getWarrantyTime(), assetVo.getWarrantyTime(), "WarrantyTime", timeOffset));
        
        Location location = asset.getLocation();
        String shortSite = location.getSite().replace("Augmentum ", "");
        
        if (!assetVo.getSite().equals(shortSite)) {
            str.append(" [Site: " + shortSite + SystemConstants.OPERATION_PLACEHOLDER + assetVo.getSite() + "]");
        }
        
        if (!assetVo.getLocation().equals(location.getRoom())) {
            str.append(" [Room: " + location.getRoom() + SystemConstants.OPERATION_PLACEHOLDER + assetVo.getLocation() + "]");
        }
        
        str.append(compareUserValue(asset.getUser(), assetVo.getUser(), "User"));
        str.append(compareCustomerValue(asset.getCustomer(), assetVo.getCustomer(), "Customer"));
        str.append(compareProjectValue(asset.getProject(), assetVo.getProject(), "Project"));
        
        str.append(compareMachineValue(asset.getMachine(), assetVo.getMachine()));
        str.append(compareMonitorValue(asset.getMonitor(), assetVo.getMonitor()));
        str.append(compareDeviceValue(asset.getDevice(), assetVo.getDevice()));
        str.append(compareSoftwareValue(asset.getSoftware(), assetVo.getSoftware()));
        str.append(compareOtherAssetsValue(asset.getOtherAssets(), assetVo.getOtherAssets()));
        
        return str.toString();
    }
    
    private static String compareStringValue(String oldValue, String newValue, String propertyName) {
        
        String str = "";
        if (null != oldValue && null != newValue) {
                
            if (!oldValue.equals(newValue)) {
                str = " [" + propertyName + ": " + oldValue + SystemConstants.OPERATION_PLACEHOLDER + newValue + "]";
            }
        } else if (null == oldValue && null != newValue) {
            
            str = " [" + propertyName + ": NULL" + SystemConstants.OPERATION_PLACEHOLDER + newValue + "]";
        } else if (null != oldValue && null == newValue) {
            
            str = " [" + propertyName + ": " + oldValue + SystemConstants.OPERATION_PLACEHOLDER + "NULL]";
        }
        return str;
    }
    
    private static String compareBooleanValue(Boolean oldValue, Boolean newValue, String propertyName) {
        
        String str = "";
        if (null != oldValue && null != newValue) {
                
            if (!oldValue.equals(newValue)) {
                str = " [" + propertyName + ": " + oldValue + SystemConstants.OPERATION_PLACEHOLDER + newValue + "]";
            }
        } else if (null == oldValue && null != newValue) {
            
            str = " [" + propertyName + ": NULL" + SystemConstants.OPERATION_PLACEHOLDER + newValue + "]";
        } else if (null != oldValue && null == newValue) {
            
            str = " [" + propertyName + ": " + oldValue + SystemConstants.OPERATION_PLACEHOLDER + "NULL]";
        }
        return str;
    }
    
    private static String compareDateValue(Date oldDate, String newDate, String propertyName, String timeOffset) {
        
        String str = "";
        if (null != oldDate && null != newDate) {
            
            String oldValue = UTCTimeUtil.utcToLocalTime(oldDate, timeOffset, SystemConstants.DATE_DAY_PATTERN);
            
            if (!oldValue.equals(newDate)) {
                str = " [" + propertyName + ": " + oldValue + SystemConstants.OPERATION_PLACEHOLDER + newDate + "]";
            }
        } else if (null == oldDate && null != newDate) {
            
            str = " [" + propertyName + ": NULL" + SystemConstants.OPERATION_PLACEHOLDER + newDate + "]";
        } else if (null != oldDate && null == newDate) {
            
            String oldValue = UTCTimeUtil.utcToLocalTime(oldDate, timeOffset, SystemConstants.DATE_DAY_PATTERN);
            str = " [" + propertyName + ": " + oldValue + SystemConstants.OPERATION_PLACEHOLDER + "NULL]";
        }
        return str;
    }
    
    private static String compareUserValue(User oldUser, User newUser, String propertyName) {
        
        String str = "";
        if (null != oldUser && null != newUser) {
            
            if (!oldUser.getUserId().equals(newUser.getUserId())) {
                str = " [" + propertyName + ": " + oldUser.getUserName() + SystemConstants.OPERATION_PLACEHOLDER + newUser.getUserName() + "]";
            }
        } else if (null == oldUser && null != newUser) {
            
            str = " [" + propertyName + ": NULL" + SystemConstants.OPERATION_PLACEHOLDER + newUser.getUserName() + "]";
        } else if (null != oldUser && null == newUser) {
            
            str = " [" + propertyName + ": " + oldUser.getUserName() + SystemConstants.OPERATION_PLACEHOLDER + "NULL]";
        }
        return str;
    }
    
    private static String compareCustomerValue(Customer oldCustomer, Customer newCustomer, String propertyName) {
        
        String str = "";
        if (null != oldCustomer && null != newCustomer) {
            
            if (!oldCustomer.getCustomerCode().equals(newCustomer.getCustomerCode())) {
                str = " [" + propertyName + ": " + oldCustomer.getCustomerName() + SystemConstants.OPERATION_PLACEHOLDER + newCustomer.getCustomerName() + "]";
            }
        } else if (null == oldCustomer && null != newCustomer) {
            
            str = " [" + propertyName + ": NULL" + SystemConstants.OPERATION_PLACEHOLDER + newCustomer.getCustomerName() + "]";
        } else if (null != oldCustomer && null == newCustomer) {
            
            str = " [" + propertyName + ": " + oldCustomer.getCustomerName() + SystemConstants.OPERATION_PLACEHOLDER + "NULL]";
        }
        return str;
    }
    
    private static String compareProjectValue(Project oldProject, Project newProject, String propertyName) {
        
        String str = "";
        
        if (null != oldProject && null != newProject) {
            
            if (!StringUtils.isBlank(oldProject.getProjectCode()) && !StringUtils.isBlank(newProject.getProjectCode())) {
                
                if (!oldProject.getProjectCode().equals(newProject.getProjectCode())) {
                    str = " [" + propertyName + ": " + oldProject.getProjectName() + SystemConstants.OPERATION_PLACEHOLDER + newProject.getProjectName() + "]";
                }
            } else if (StringUtils.isBlank(oldProject.getProjectCode()) && !StringUtils.isBlank(newProject.getProjectCode())) {
                str = " [" + propertyName + ": NULL" + SystemConstants.OPERATION_PLACEHOLDER + newProject.getProjectName() + "]";
            } else if (!StringUtils.isBlank(oldProject.getProjectCode()) && StringUtils.isBlank(newProject.getProjectCode())) {
                str = " [" + propertyName + ": " + oldProject.getProjectName() + SystemConstants.OPERATION_PLACEHOLDER + "NULL]";
            }
        } else if (null == oldProject && null != newProject) {
            
            if (!StringUtils.isBlank(newProject.getProjectCode())) {
                
                str = " [" + propertyName + ": NULL" + SystemConstants.OPERATION_PLACEHOLDER + newProject.getProjectName() + "]";
            }
        } else if (null != oldProject && null == newProject) {
            
            if (!StringUtils.isBlank(oldProject.getProjectCode())) {
                
                str = " [" + propertyName + ": " + oldProject.getProjectName() + SystemConstants.OPERATION_PLACEHOLDER + "NULL]";
            }
        }
        return str;
    }
    
    private static String compareMachineValue(Machine oldMachine, Machine newMachine) {
        
        StringBuilder str = new StringBuilder();
        
        if (null != oldMachine && null != newMachine) {
            
            str.append(compareStringValue(oldMachine.getSubtype(), newMachine.getSubtype(), "Subtype"));
            str.append(compareStringValue(oldMachine.getSpecification(), newMachine.getSpecification(), "Specification"));
            str.append(compareStringValue(oldMachine.getAddress(), newMachine.getAddress(), "Address"));
            str.append(compareStringValue(oldMachine.getConfiguration(), newMachine.getConfiguration(), "Configuration"));
        }
        return str.toString();
    }
    
    private static String compareMonitorValue(Monitor oldMonitor, Monitor newMonitor) {
        
        StringBuilder str = new StringBuilder();
        
        if (null != oldMonitor && null != newMonitor) {
            
            str.append(compareStringValue(oldMonitor.getSize(), newMonitor.getSize(), "Size"));
            str.append(compareStringValue(oldMonitor.getDetail(), newMonitor.getDetail(), "Detail"));
        }
        return str.toString();
    }
    
    private static String compareDeviceValue(Device oldDevice, Device newDevice) {
        
        StringBuilder str = new StringBuilder();
        
        if (null != oldDevice && null != newDevice) {
            
            str.append(compareStringValue(oldDevice.getConfiguration(), newDevice.getConfiguration(), "Configuration"));
            
            if (null != oldDevice.getDeviceSubtype() && null != newDevice.getDeviceSubtype()) {
                
                str.append(compareStringValue(oldDevice.getDeviceSubtype().getSubtypeName(), 
                        newDevice.getDeviceSubtype().getSubtypeName(), "SubtypeName"));
            }
        }
        return str.toString();
    }
    
    private static String compareSoftwareValue(Software oldSoftware, Software newSoftware) {
        
        StringBuilder str = new StringBuilder();
        
        if (null != oldSoftware && null != newSoftware) {
            
            str.append(compareStringValue(oldSoftware.getVersion(), newSoftware.getVersion(), "Version"));
            str.append(compareStringValue(oldSoftware.getLicenseKey(), newSoftware.getLicenseKey(), "LicenseKey"));
            str.append(compareStringValue(oldSoftware.getAdditionalInfo(), newSoftware.getAdditionalInfo(), "AdditionalInfo"));
        }
        return str.toString();
    }
    
    private static String compareOtherAssetsValue(OtherAssets oldOtherAssets, OtherAssets newOtherAssets) {
        
        StringBuilder str = new StringBuilder();
        
        if (null != oldOtherAssets && null != newOtherAssets) {
            
            str.append(compareStringValue(oldOtherAssets.getDetail(), newOtherAssets.getDetail(), "Details"));
        }
        return str.toString();
    }
}
