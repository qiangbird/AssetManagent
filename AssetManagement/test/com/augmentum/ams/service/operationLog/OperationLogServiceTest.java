package com.augmentum.ams.service.operationLog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.model.operationLog.OperationLog;
import com.augmentum.ams.service.asset.AssetService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class OperationLogServiceTest {

    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private AssetService assetService;

    @Test
    public void saveOperationLogTest() {

        // Asset asset1 =
        // assetService.getAsset("4028960f442fa7a501442fa963d90004");
        // Asset asset2 = new Asset();
        // try {
        // BeanUtils.copyProperties(asset2, asset1);
        // } catch (IllegalAccessException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // } catch (InvocationTargetException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // // asset2.setBarCode("222222");
        //
        // OperationRecordUtil<Asset> r1 = new OperationRecordUtil<Asset>();
        // r1.record(Asset.class, asset1, asset2,
        // "40289613441973ee0144197d4cc8001d", "Jay He");
        // // String messge =

        OperationLog operationLog = new OperationLog();
        operationLog.setOperatorID("T04291");
        operationLog.setOperatorName("Jay He");
        operationLog.setOperation("update Asset201400011 change User");
        operationLog.setOperationObject("Asset");
        operationLog.setOperationObjectID("4028960f442fa7a501442fa963d90004");
        operationLogService.save(operationLog);
    }

}
