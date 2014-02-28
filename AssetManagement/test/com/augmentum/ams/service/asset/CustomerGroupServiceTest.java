/**
 * 
 */
package com.augmentum.ams.service.asset;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.model.enumeration.ProcessTypeEnum;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

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
    @Autowired
    private CustomerService customerService;
    
    @Test
    public void saveCustomerGroupTest(){
        CustomerGroup customerGroup =  new CustomerGroup();
        customerGroup.setDescription("bbbbb");
        customerGroup.setGroupName("bbbb");
        customerGroup.setProcessType(ProcessTypeEnum.NONSHARE.name());
        customerGroupService.saveCustomerGroup(customerGroup);
    }
    
    @Test
    public void getCustomerByGroup() {
        List<Customer> list = customerService.getCustomerByGroup("402896124466e075014466e2488b0000");
        
        Set<Customer> set = new HashSet<Customer>(list);
        logger.info(set.size());
        
        for (Customer customer : set) {
            logger.info(customer.getCustomerName());
        }
    }
    
    @Test
    public void deleteCustomerGroup() {
        customerGroupService.deleteCustomerGroupById("402896124466e075014466e2488b0000");
    }
    
    @Test
    public void testFindCustomerGroupBySearchCondition() {
        SearchCondition sc = new SearchCondition();
        
        Page<CustomerGroup> page = customerGroupService.findCustomerGroupBySearchCondition(sc);
        logger.info(page.getResult().size());
    }
    
}
