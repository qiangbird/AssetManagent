/**
 * 
 */
package com.augmentum.ams.service.asset;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.model.asset.Machine;

/**
 * @author Grylls.Xu
 * @time Sep 24, 2013 7:26:02 PM
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class MachineServiceTest {

    @Autowired
    private MachineService machineService;
    
    @Test
    public void testFindMachines() {
        String conditions = "asset.assetName";
        String keyword = "assetName";
        List<Machine> machines = machineService.findMachines(conditions, keyword);
        System.out.println(machines.size());
    }
    
    @Test
    public void testFindMachinesfo() {
        String conditions = "asset.location.site";
        String keyword = "site";
        List<Machine> machines = machineService.findMachines(conditions, keyword);
        System.out.println(machines.size());
    }
}
