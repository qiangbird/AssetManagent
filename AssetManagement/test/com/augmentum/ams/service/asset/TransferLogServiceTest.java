package com.augmentum.ams.service.asset;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Machine;
import com.augmentum.ams.model.asset.TransferLog;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.user.UserService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class TransferLogServiceTest {

    @Autowired
    private TransferLogService transferLogService;
    @Autowired
    private AssetService assetService;
    @Autowired
    private UserService userService;
    
    @Test
    public void saveTransferLogService() {
    	/*	
    	TransferLog transferLog = new TransferLog();
    	Asset asset = assetService.getAsset("40289613438b32ca01438b388260000e");
    	User user = userService.getUserByUserId("YT00250");
    	transferLog.setAsset(asset);
    	transferLog.setEmployee(user);
    	transferLog.setAction("Take Over");
    	transferLog.setTime(new Date());
    	transferLog.setCreatedTime(new Date());
    	transferLog.setUpdatedTime(new Date());
    	transferLogService.saveTransferLog("40289613438b32ca01438b388260000e", "Take Over");
    	*/
    }
    
}
