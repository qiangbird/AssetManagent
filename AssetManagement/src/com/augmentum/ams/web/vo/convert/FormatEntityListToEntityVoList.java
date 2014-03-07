package com.augmentum.ams.web.vo.convert;

import com.augmentum.ams.exception.SystemException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.asset.PurchaseItem;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.AssetVo;

public class FormatEntityListToEntityVoList {

    public static void purchaseItemToAssetVo(PurchaseItem purchaseItem,
            AssetVo assetVo, String timeOffset) {

        try {
            assetVo.setPurchaseItemId(purchaseItem.getId());

            Customer customer = new Customer();

            customer.setCustomerCode(purchaseItem.getCustomerCode());
            customer.setCustomerName(purchaseItem.getCustomerName());

            assetVo.setCustomer(customer);
            assetVo.setType(purchaseItem.getItemType());
            assetVo.setSite(purchaseItem.getDataSite()
                    .replace("Augmentum ", ""));
            assetVo.setEntity(purchaseItem.getEntitySite());
            assetVo.setPoNo(purchaseItem.getPoNo());
            assetVo.setAssetName(purchaseItem.getItemName());

            Project project = new Project();

            project.setProjectCode(purchaseItem.getProjectCode());
            project.setProjectName(purchaseItem.getProjectName());

            assetVo.setProject(project);
            assetVo.setVendor(purchaseItem.getVendorName());
            assetVo.setBatchNumber(String.valueOf(purchaseItem
                    .getFinalQuantity()));
            assetVo.setCheckInTime(UTCTimeUtil.utcToLocalTime(
                    purchaseItem.getDeliveryDate(), timeOffset, "yyyy-MM-dd"));

        } catch (Exception e) {
            throw new SystemException(e, ErrorCodeUtil.DATA_NOT_EXIST,
                    "Convert purchase to assetVo");
        }
    }

}
