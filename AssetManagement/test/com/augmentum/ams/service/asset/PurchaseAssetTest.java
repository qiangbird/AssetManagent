package com.augmentum.ams.service.asset;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.model.asset.PurchaseItem;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class PurchaseAssetTest {
    @Autowired
    private PurchaseItemService purchaseItemService;
    
    @Test
    public void SavePurchaseItem(){
        PurchaseItem item = new PurchaseItem();
        item.setCustomerName("Augmentum");
        item.setCustomerCode("00900");
        item.setItemName("aarr");
        item.setFinalQuantity(5);
        item.setDataSite("Augmentum Wuhan");
        item.setItemType("MACHINE");
        item.setEntitySite("鹏智上海");
        item.setCreatedTime(new Date());
        item.setUpdatedTime(new Date());
        item.setPoNo("aarr");
        item.setProcessType("Augmentum");
        item.setVendorName("Augmentum");
        item.setDeliveryDate(new Date());
        
        purchaseItemService.savePurchaseItem(item);
//        Assert.assertNotNull(purchaseItemService)
    }
}
