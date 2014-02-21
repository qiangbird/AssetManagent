package com.augmentum.ams.service.asset;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.augmentum.ams.model.asset.PurchaseItem;

public interface PurchaseItemService {
    PurchaseItem getPurchaseItemById(String purchaseItemId);

    /**
     * Delete unused purchaseItem which comes from PMS system
     * @param purchaseItem
     * @throws DataAccessException
     */
    void deletePurchaseItem(PurchaseItem purchaseItem);

    JSONArray findAllPurchaseItem();

    void createPurchaseItem(Map<String, Object> dataMap);
    
    void deletePurchaseItemAsId(String id);
    
    void savePurchaseItem(PurchaseItem item);
}