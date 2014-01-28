package com.augmentum.ams.model.enumeration;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:45:54 AM
 */
public enum StatusEnum {

    AVAILABLE,

    IDLE,

    IN_USE,

    BORROWED,

    RETURNED,

    BROKEN,

    WRITE_OFF;
    
    public static boolean isRightStatus(String assetStatus){
        
        StatusEnum[] status = StatusEnum.values();
        boolean isRightStatu = Boolean.FALSE;

        for (StatusEnum statu : status) {
            if (statu.name().equals(assetStatus)) {
                isRightStatu = Boolean.TRUE;
                break;
            }
        }
        return isRightStatu;
    }
}
