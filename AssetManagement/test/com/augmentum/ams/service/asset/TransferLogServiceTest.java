package com.augmentum.ams.service.asset;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.dao.asset.TransferLogDao;
import com.augmentum.ams.model.asset.TransferLog;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class TransferLogServiceTest {

    @Autowired
    private TransferLogService transferLogService;
    @Autowired
    private TransferLogDao transferLogDao;
    
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
    
    @SuppressWarnings("unchecked")
    @Test 
	public void createIndexForTransferLogTest() throws ParseException{
        String hql = "FROM TransferLog";
        List<TransferLog> list = (List<TransferLog>)transferLogDao.getHibernateTemplate().find(hql);
        Class<TransferLog>[] clazzes = new Class[list.size()];
        for (int i = 0; i < list.size(); i++) {
        	TransferLog transferLog = list.get(i);
            Class clazz =  transferLog.getClass();
            clazzes[i] = clazz;
        }
        transferLogService.createIndexForTransferLog(clazzes);
	}
    
}
