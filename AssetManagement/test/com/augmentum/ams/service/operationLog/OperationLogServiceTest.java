package com.augmentum.ams.service.operationLog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.model.operationLog.OperationLog;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class OperationLogServiceTest {

@Autowired
private OperationLogService operationLogService;

@Test
public void saveOperationLogTest(){
	
	OperationLog operationLog = new OperationLog();
	operationLog.setOperatorID("T04291");
	operationLog.setOperatorName("Jay He");
	operationLog.setOperation("Update Asset201400002");
	operationLog.setOperationObject("Asset");
	operationLog.setOperationObjectID("40289613437103a4014371076ba10003");
	operationLogService.save(operationLog);
}

}
