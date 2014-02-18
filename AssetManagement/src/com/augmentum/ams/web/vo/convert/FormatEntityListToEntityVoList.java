package com.augmentum.ams.web.vo.convert;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.exception.SystemException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.asset.PurchaseItem;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.AssetListVo;
import com.augmentum.ams.web.vo.asset.AssetVo;

public class FormatEntityListToEntityVoList {

    public static List<AssetListVo> formatAssetListToAssetVoList(List<Asset> assetList,
            String clientTimeOffset) {
        List<AssetListVo> assetListVos = new ArrayList<AssetListVo>();

        for (Asset asset : assetList) {

            AssetListVo assetListVo = new AssetListVo();
            assetListVo.setId(asset.getId());
            assetListVo.setAssetId(asset.getAssetId());
            assetListVo.setAssetName(asset.getAssetName());
            assetListVo.setBarCode(asset.getBarCode());
            assetListVo.setFixed(asset.isFixed() ? SystemConstants.YES_FLAG : SystemConstants.NO_FLAG);
            assetListVo.setManufacturer(asset.getManufacturer());
            assetListVo.setMemo(asset.getMemo());
            assetListVo.setOwnerShip(asset.getOwnerShip());
            assetListVo.setPoNo(asset.getPoNo());
            assetListVo.setSeriesNo(asset.getSeriesNo());
            assetListVo.setStatus(asset.getStatus());
            assetListVo.setType(asset.getType());
            assetListVo.setVendor(asset.getVendor());
            assetListVo.setKeeper(asset.getKeeper());
            assetListVo.setEntityName(asset.getEntity());

            if (null != asset.getCheckInTime()) {
                assetListVo.setCheckInTime(UTCTimeUtil.formatDateToString(UTCTimeUtil
                        .utcToLocalTime(asset.getCheckInTime(), clientTimeOffset),
                        SystemConstants.DATE_DAY_PATTERN));
            }

            if (null != asset.getCheckOutTime()) {
                assetListVo.setCheckOutTime(UTCTimeUtil.formatDateToString(UTCTimeUtil
                        .utcToLocalTime(asset.getCheckOutTime(), clientTimeOffset),
                        SystemConstants.DATE_DAY_PATTERN));
            }

            if (null != asset.getWarrantyTime()) {
                assetListVo.setWarrantyTime(UTCTimeUtil.formatDateToString(UTCTimeUtil
                        .utcToLocalTime(asset.getWarrantyTime(), clientTimeOffset),
                        SystemConstants.DATE_DAY_PATTERN));
            }

            if (null != asset.getCustomer()) {
                assetListVo.setCustomerName(asset.getCustomer().getCustomerName());
            }

            if (null != asset.getUser()) {
                assetListVo.setUserName(asset.getUser().getUserName());
            }

            if (null != asset.getLocation()) {
                assetListVo.setSite(asset.getLocation().getSite() + asset.getLocation().getRoom());
            }

            if (null != asset.getProject()) {
                assetListVo.setProjectName(asset.getProject().getProjectName());
            }

            assetListVos.add(assetListVo);
        }
        return assetListVos;
    }
    
    public static void purchaseItemToAssetVo(PurchaseItem purchaseItem, AssetVo assetVo, String timeOffset){
        
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
            
            Project project = new Project();
            
            project.setProjectCode(purchaseItem.getProjectCode());
            project.setProjectName(purchaseItem.getProjectName());
            
            assetVo.setProject(project);
            assetVo.setVendor(purchaseItem.getVendorName());
            assetVo.setCheckInTime(UTCTimeUtil.utcToLocalTime(purchaseItem.getDeliveryDate(), timeOffset,
                    "yyyy-MM-dd"));
            
        } catch (Exception e) {
            throw new SystemException(e, ErrorCodeUtil.DATA_NOT_EXIST, "Convert purchase to assetVo");
        }
    }
    
}
