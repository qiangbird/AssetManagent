/**
 * 
 */
package com.augmentum.ams.service.user;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.web.vo.user.SpecialRoleVo;

/**
 * @author John.Li
 *
 */
public class SpecialRoleServiceImplTest extends BaseCaseTest{
    
    @Autowired
    private SpecialRoleService specialRoleService;
    
    @Autowired
    private HibernateTemplate hibernateTemplate;

    private SpecialRoleVo initSpecialRoleVo(){
        
        String  specialRoles = "[{'employeeId':'T03306'," +
        		                "'employeeName':'Selena Shen'," +
        		                "'customerName':'Internal Systems'," +
        		                "'customerCode':'00100'," +
        		                "'isNew':'true'," +
        		                "'isDelete':'false'}]";
        SpecialRoleVo specialRoleVo = new SpecialRoleVo(null, null,
                null, null, false,
                specialRoles);
        
        return specialRoleVo;
    }
    
    private List<String> initCustomerCodes(){
            
        List<String> customerCodes = new ArrayList<String>();
        customerCodes.add("00900");
        customerCodes.add("00100");
        return customerCodes;
    }
    
    /**
     * Test method for {@link com.augmentum.ams.service.user.impl.SpecialRoleServiceImpl#saveOrUpdateSpecialRole(com.augmentum.ams.web.vo.user.SpecialRoleVo)}.
     */
    @Test
    public void testSaveOrUpdateSpecialRole() {
        
        SpecialRoleVo specialRoleVo = initSpecialRoleVo();
        specialRoleService.saveOrUpdateSpecialRole(specialRoleVo);
        
        String sqlStr = " select id from SpecialRole where customerCode='00100'";
        String id =  (String) hibernateTemplate.find(sqlStr).get(0);
        
        Assert.assertNotNull("SpecialRole id is null", id);
    }

    /**
     * Test method for {@link com.augmentum.ams.service.user.impl.SpecialRoleServiceImpl#findSpecialRolesByCustomerCodes(java.util.List)}.
     */
    @Test
    public void testFindSpecialRolesByCustomerCodes() {
        
        List<String> customerCodes = initCustomerCodes();
        SpecialRoleVo specialRoleVo = initSpecialRoleVo();
        specialRoleService.saveOrUpdateSpecialRole(specialRoleVo);
        
        List<SpecialRoleVo> specialRoleVos  = specialRoleService.findSpecialRolesByCustomerCodes(customerCodes);
        
        Assert.assertNotNull("SpecialRoles is null", specialRoleVos);
    }

/*    *//**
     * Test method for {@link com.augmentum.ams.service.user.impl.SpecialRoleServiceImpl#changeVOToJSON(java.util.List)}.
     *//*
    @Test
    public void testChangeVOToJSON() {
        
        JSONArray array = new JSONArray();
        
        List<String> customerCodes = initCustomerCodes();
        SpecialRoleVo specialRoleVo = initSpecialRoleVo();
        specialRoleService.saveOrUpdateSpecialRole(specialRoleVo);
        
        List<SpecialRoleVo> specialRoleVos  = specialRoleService.findSpecialRolesByCustomerCodes(customerCodes);
        array = specialRoleService.changeVOToJSON(specialRoleVos);
        
        Assert.assertNotNull("JSONArray is null", array);
    }*/

    /**
     * Test method for {@link com.augmentum.ams.service.user.impl.SpecialRoleServiceImpl#findSpecialRoleByUserId(java.lang.String)}.
     */
    @Test
    public void testFindSpecialRoleByUserId() {
        
        SpecialRoleVo specialRoleVo = initSpecialRoleVo();
        specialRoleService.saveOrUpdateSpecialRole(specialRoleVo);
        
        boolean isExist = specialRoleService.findSpecialRoleByUserId("T03306");
        
        Assert.assertTrue(isExist);
    }

}
