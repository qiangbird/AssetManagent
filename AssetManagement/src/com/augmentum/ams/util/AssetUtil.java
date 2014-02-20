package com.augmentum.ams.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.enumeration.AssetTypeEnum;
import com.augmentum.ams.model.enumeration.MachineSubtypeEnum;
import com.augmentum.ams.model.enumeration.ProcessTypeEnum;
import com.augmentum.ams.model.enumeration.StatusEnum;
import com.augmentum.ams.web.vo.asset.AssetVo;

public class AssetUtil {
    private static Logger logger = Logger.getLogger(AssetUtil.class);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 5, 2013 8:09:57 PM
     * @param asset
     * @param assetVo
     */
    public static void assetToAssetVo(Asset asset, AssetVo assetVo, String timeOffset) {
        try {
            BeanUtils.copyProperties(assetVo, asset);
            assetVo.setSite(asset.getLocation().getSite().replace("Augmentum ", ""));
            assetVo.setLocation(asset.getLocation().getRoom());

            assetVo.setCheckInTime(UTCTimeUtil.utcToLocalTime(asset.getCheckInTime(), timeOffset,
                    "yyyy-MM-dd"));
            assetVo.setCheckOutTime(UTCTimeUtil.utcToLocalTime(asset.getCheckOutTime(), timeOffset,
                    "yyyy-MM-dd"));
            assetVo.setWarrantyTime(UTCTimeUtil.utcToLocalTime(asset.getWarrantyTime(), timeOffset,
                    "yyyy-MM-dd"));
            assetVo.setSoftwareExpiredTime(UTCTimeUtil.formatUTCToLocalString(asset.getSoftware()
                    .getSoftwareExpiredTime(), timeOffset));
        } catch (Exception e) {
            logger.error("Asset to assetVo error!", e);
        }
    }

    public static void setKeeperForAssetVo(AssetVo assetVo, Asset asset) {
        // save keeper and Remove duplicate
        String[] keeper = assetVo.getKeeper().split(";");
        Set<String> keeperSet = new HashSet<String>();
        for (int i = 0; i < keeper.length; i++) {
            keeperSet.add(keeper[i]);
        }
        StringBuffer keeprs = new StringBuffer();
        for (String str : keeperSet) {
            keeprs.append(str).append(";");
        }
        asset.setKeeper(keeprs.toString());

    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getAssetStatus() {
        List assetStatus = new ArrayList();
        for (int i = 0; i < StatusEnum.values().length; i++) {
            // assetStatus.add(StatusEnum.values()[i].getStatus());
            assetStatus.add(StatusEnum.values()[i].toString());
        }
        return assetStatus;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getAssetTypes() {
        List assetType = new ArrayList();
        for (int i = 0; i < AssetTypeEnum.values().length; i++) {
            assetType.add(AssetTypeEnum.values()[i]);
        }
        return assetType;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getMachineTypes() {
        List machineTypes = new ArrayList();
        for (int i = 0; i < MachineSubtypeEnum.values().length; i++) {
            machineTypes.add(MachineSubtypeEnum.values()[i]);
        }
        return machineTypes;
    }

    public static String generateAssetId(int count) {
        String assetId = "Asset"
                + UTCTimeUtil.formatDateToString(UTCTimeUtil.localDateToUTC(), "yyyy-mm-dd")
                        .substring(0, 4) + ((count + 100000) + "").substring(1, 6);
        return assetId;
    }

    public static List<String> getBatchId(String firstId, int batchCount) {

        List<String> assetIdList = new ArrayList<String>();

        for (int i = 0; i < batchCount; i++) {
            // generate getting the assetId when batch create Asset
            String laterNum = (Integer.parseInt(firstId.substring(firstId.length() - 5,
                    firstId.length())) + 100000 + i)
                    + "";
            String batchAssetId = firstId.substring(0, firstId.length() - 5)
                    + laterNum.substring(1, laterNum.length());
            assetIdList.add(batchAssetId);
        }
        return assetIdList;
    }

    public static List<String> getSiteLocation(List<Location> list) {
        List<String> locationList = new ArrayList<String>();
        for (Location location : list) {
            locationList.add(location.getSite() + location.getRoom());
        }

        return locationList;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static List getProcessTypes() {
        List processTypes = new ArrayList();
        for (int i = 0; i < ProcessTypeEnum.values().length; i++) {
            processTypes.add(ProcessTypeEnum.values()[i]);
        }
        return processTypes;
    }
    
    public static String getErrorMessage(BindingResult bindingResult){
        List<FieldError> errors = bindingResult.getFieldErrors();
        StringBuffer sb = new StringBuffer();
        
        for (FieldError error : errors) {
            String errorCode = error.getDefaultMessage();
            sb.append(errorCode).append(",");
            logger.info(sb.toString());
        }
        return sb.toString();
    }

    public static String getErrorField(BindingResult bindingResult){
        List<FieldError> errors = bindingResult.getFieldErrors();
        StringBuffer sb = new StringBuffer();
        
        for (FieldError error : errors) {
            String errorCode = error.getField();
            sb.append(errorCode).append(",");
            logger.info(sb.toString());
        }
        return sb.toString();
    }

}
