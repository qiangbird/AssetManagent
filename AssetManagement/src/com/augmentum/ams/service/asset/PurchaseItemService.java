package com.augmentum.ams.service.asset;

import java.util.List;
import java.util.Map;

import com.augmentum.ams.model.asset.PurchaseItem;

public interface PurchaseItemService {
    PurchaseItem getPurchaseItemById(String purchaseItemId);

    /**
     * Delete unused purchaseItem which comes from PMS system
     * @param purchaseItem
     * @throws DataAccessException
     */
    void deletePurchaseItem(PurchaseItem purchaseItem);

    List<PurchaseItem> getAllPurchaseItem();

    void createPurchaseItem(Map<String, Object> dataMap);
}