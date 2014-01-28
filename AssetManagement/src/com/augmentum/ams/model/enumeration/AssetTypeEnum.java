package com.augmentum.ams.model.enumeration;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:53:42 AM
 */
public enum AssetTypeEnum {

    DEVICE,

    MACHINE,

    MONITOR,

    SOFTWARE,

    OTHERASSETS;
    
    public static boolean isRightType(String assetType){
        
        AssetTypeEnum[] assetTypes = AssetTypeEnum.values();
        boolean isRightType = Boolean.FALSE;

        for (AssetTypeEnum type : assetTypes) {
            if (type.name().equals(assetType)) {
                isRightType = Boolean.TRUE;
                break;
            }
        }
        return isRightType;

    }
}
