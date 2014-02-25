/**
 * 
 */
package com.augmentum.ams.service.asset;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.model.enumeration.ProcessTypeEnum;

/**
 * 
 * @author Jay.He
 *
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class CustomerGroupServiceTest {

    Logger logger = Logger.getLogger(CustomerGroupServiceTest.class);
    @Autowired
    private CustomerGroupService customerGroupService;
    
    @Test
    public void saveCustomerGroupTest(){
        CustomerGroup customerGroup =  new CustomerGroup();
        customerGroup.setDescription("bbbbb");
        customerGroup.setGroupName("bbbb");
        customerGroup.setProcessType(ProcessTypeEnum.NONSHARE.name());
        customerGroupService.saveCustomerGroup(customerGroup);
    }
    
}
