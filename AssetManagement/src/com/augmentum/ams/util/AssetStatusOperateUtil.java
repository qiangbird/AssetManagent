package com.augmentum.ams.util;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.enumeration.StatusEnum;
import com.augmentum.ams.model.enumeration.TransientStatusEnum;

public class AssetStatusOperateUtil {

    public static boolean canITAssignAsset(Asset asset) {
        if (StatusEnum.RETURNED.toString().equals(asset.getStatus())
                || StatusEnum.BORROWED.toString().equals(asset.getStatus())
                || StatusEnum.IDLE.toString().equals(asset.getStatus())
                || TransientStatusEnum.ASSIGNING.toString().equals(asset.getStatus())
                || TransientStatusEnum.RETURNING_TO_IT.toString().equals(asset.getStatus())) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean canManagerAssignAsset(Asset asset) {
        if (StatusEnum.AVAILABLE.toString().equals(asset.getStatus())
                || StatusEnum.IN_USE.toString().equals(asset.getStatus())) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean canITReturnToCustomer(Asset asset) {
        if (StatusEnum.RETURNED.toString().equals(asset.getStatus())
                || TransientStatusEnum.ASSIGNING.toString().equals(asset.getStatus())
                || TransientStatusEnum.RETURNING_TO_IT.toString().equals(asset.getStatus())) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean canManagerReturnToIT(Asset asset) {
        return canITReturnToCustomer(asset);
    }

    public static boolean canManagerReturnToProject(Asset asset) {
        if (StatusEnum.RETURNED.toString().equals(asset.getStatus())
                || StatusEnum.AVAILABLE.toString().equals(asset.getStatus())
                || TransientStatusEnum.ASSIGNING.toString().equals(asset.getStatus())
                || TransientStatusEnum.RETURNING_TO_IT.toString().equals(asset.getStatus())) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean canManagerTakeOver(Asset asset) {
        return canManagerAssignAsset(asset);
    }

    public static boolean canEmployeeTakeOver(Asset asset) {
        if (StatusEnum.AVAILABLE.toString().equals(asset.getStatus())) {
            return true;
        } else {
            return false;
        }
    }

}
