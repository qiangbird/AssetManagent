package com.augmentum.ams.web.vo.convert;

import java.util.ArrayList;
import java.util.List;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.AssetListVo;

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
            assetListVo.setFixed(asset.isFixed() ? Constant.YES_FLAG : Constant.NO_FLAG);
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
                        Constant.DATE_DAY_PATTERN));
            }

            if (null != asset.getCheckOutTime()) {
                assetListVo.setCheckOutTime(UTCTimeUtil.formatDateToString(UTCTimeUtil
                        .utcToLocalTime(asset.getCheckOutTime(), clientTimeOffset),
                        Constant.DATE_DAY_PATTERN));
            }

            if (null != asset.getWarrantyTime()) {
                assetListVo.setWarrantyTime(UTCTimeUtil.formatDateToString(UTCTimeUtil
                        .utcToLocalTime(asset.getWarrantyTime(), clientTimeOffset),
                        Constant.DATE_DAY_PATTERN));
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
    
}
