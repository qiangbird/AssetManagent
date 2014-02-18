package com.augmentum.ams.service.asset.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.dao.asset.PurchaseItemDao;
import com.augmentum.ams.model.asset.PurchaseItem;
import com.augmentum.ams.model.enumeration.EntityEnum;
import com.augmentum.ams.service.asset.PurchaseItemService;
import com.augmentum.ams.util.CommonUtil;

@Service("purchaseItemService")
public class PurchaseItemServiceImpl implements PurchaseItemService {

    private static final Logger logger = Logger.getLogger(PurchaseItemServiceImpl.class);
    
    @Autowired
    private PurchaseItemDao purchaseItemDao;

    @Override
    public PurchaseItem getPurchaseItemById(String purchaseItemId)  {
        PurchaseItem purchaseItem = (PurchaseItem) purchaseItemDao.get(PurchaseItem.class, purchaseItemId);

        return purchaseItem;
    }

    /* (non-Javadoc)
     * @see com.augmentum.am.service.PurchaseItemService#deletePurchaseItem(com.augmentum.am.model.PurchaseItem)
     */
    @Override
    public void deletePurchaseItem(PurchaseItem purchaseItem)  {
        purchaseItemDao.delete(purchaseItem);
    }

    public void setPurchaseItemDao(PurchaseItemDao purchaseItemDao) {
        this.purchaseItemDao = purchaseItemDao;
    }

    @Override
    public List<PurchaseItem> getAllPurchaseItem()  {
        List<PurchaseItem> purchaseItems = purchaseItemDao.findAll(PurchaseItem.class);

        return purchaseItems;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void createPurchaseItem(Map<String, Object> dataMap)  {
        String processType = null;
        String dataSite = null;
        String entitySite = null;
        Timestamp deliveryDate = null;
        int finalQuantity = 0;

        List<Map<String, Object>> orderList = (List<Map<String, Object>>) dataMap.get("orders");
        for (Map<String, Object> orderMap : orderList) {
            
            List<Map<String, Object>> itemList = (List<Map<String, Object>>) orderMap.get("items");
            for (Map<String, Object> itemMap : itemList) {
                PurchaseItem purchaseItem = new PurchaseItem();
                purchaseItem.setPoNo(CommonUtil.objectToString(orderMap.get("poNo")));

                processType = CommonUtil.objectToString(dataMap.get("processType"));
                if ("NO_CHARGE_IT".equals(processType) || "NO_CHARGE_NON_IT".equals(processType)) {
                    processType = SystemConstants.AUGMENTUM;
                }
                if ("CHARGE_NEED_COFIRM".equals(processType) || "CHARGE_CONFIRMED".equals(processType)) {
                    processType = CommonUtil.objectToString(dataMap.get("customerName"));
                }
                purchaseItem.setProcessType(processType);

                purchaseItem.setCustomerName(CommonUtil.objectToString(dataMap.get("customerName")));

                purchaseItem.setCustomerCode(CommonUtil.objectToString(dataMap.get("customerCode")));

                purchaseItem.setProjectName(CommonUtil.objectToString(dataMap.get("projectName")));

                purchaseItem.setProjectCode(CommonUtil.objectToString(dataMap.get("projectCode")));

                purchaseItem.setVendorName(CommonUtil.objectToString(orderMap.get("vendorName")));

                purchaseItem.setItemName(CommonUtil.objectToString(itemMap.get("itemName")));

                purchaseItem.setItemRealName(CommonUtil.objectToString(itemMap.get("realName")));

                purchaseItem.setItemDescription(CommonUtil.objectToString(itemMap.get("description")));

                dataSite = CommonUtil.objectToString(dataMap.get("dataSite"));
                if ("IES_SH".equals(dataSite)) {
                    dataSite = "Augmentum Shanghai";
                }
                if ("IES_BJ".equals(dataSite)) {
                    dataSite = "Augmentum Beijing";
                }
                if ("IES_WH".equals(dataSite)) {
                    dataSite = "Augmentum Wuhan";
                }
                if ("IES_YZ".equals(dataSite)) {
                    dataSite = "Augmentum Yangzhou";
                }
                purchaseItem.setDataSite(dataSite);

                entitySite = CommonUtil.objectToString(orderMap.get("entitySite"));
                if ("QSSH".equals(entitySite)) {
                    entitySite = EntityEnum.QunshuoShanghai.toString();
                }
                if ("QSBJ".equals(entitySite)) {
                    entitySite = EntityEnum.QunshuoBeijing.toString();
                }
                if ("QSWH".equals(entitySite)) {
                    entitySite = EntityEnum.QunshuoWuhan.toString();
                }
                if ("QSYZ".equals(entitySite)) {
                    entitySite = EntityEnum.QunshuoYangzhou.toString();
                }
                purchaseItem.setEntitySite(entitySite);

                if (!StringUtils.isEmpty(CommonUtil.objectToString(orderMap.get("deliveryDate")))) {
                    deliveryDate = Timestamp.valueOf(CommonUtil.objectToString(orderMap.get("deliveryDate")));
                }
                purchaseItem.setDeliveryDate(deliveryDate);

                if (!StringUtils.isEmpty(CommonUtil.objectToString(itemMap.get("finalQuantity")))) {
                    finalQuantity = Integer.parseInt(CommonUtil.objectToString(itemMap.get("finalQuantity")));
                }
                purchaseItem.setFinalQuantity(finalQuantity);
                
                purchaseItem.setUsed(false);
                logger.info("AMS get callback : " + dataMap.get("customerName"));
                logger.info("get request body successful" + purchaseItem.getCustomerCode() + purchaseItem.getCustomerName());

                purchaseItemDao.save(purchaseItem);
            }
        }
    }

}
