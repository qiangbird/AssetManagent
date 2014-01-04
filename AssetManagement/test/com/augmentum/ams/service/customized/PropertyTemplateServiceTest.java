package com.augmentum.ams.service.customized;

import java.text.ParseException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.web.vo.customized.PropertyTemplateVo;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class PropertyTemplateServiceTest{

    @Autowired
    protected HibernateTemplate hibernateTemplate;
    
	@Autowired
	private PropertyTemplateService propertyTemplateService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CustomerService customerService;
	
	private PropertyTemplateVo initPropertyTemplateVo(){
	    
	    String selfProperties = "[{'id':'BB6B4E2F-C05B-4D0B-804F-08788B6699EB'," +
                "'isDelete':false," +
                "'isNew':true," +
                "'propertyType':'inputType'," +
                "'required':'true'," +
                "'position':'sortableLeft'," +
                "'value':'testinput'," +
                "'enName':'testinput'," +
                "'zhName':'testinput'," +
                "'propertyDescription':'testinput'," +
                "'sequence':0}]";
	    PropertyTemplateVo propertyTemplateVo = new PropertyTemplateVo(selfProperties, "00100", "SOFTWARE");
	    
	    return propertyTemplateVo;
	}
	
	@Test
	public void getPropertyTemplateTest() throws ParseException{
		
		User user = userService.getUserByUserId("T04301");
        Customer customer = customerService.getCustomerByCode("00100");
        propertyTemplateService.savePropertyTemplate(initPropertyTemplateVo(), customer, user);
        @SuppressWarnings("unchecked")
        List<PropertyTemplate> list = propertyTemplateService.findPropertyTemplateByCustomerAndAssetType(customer.getCustomerName(),"SOFTWARE");
		
		Assert.assertTrue(0 < list.size());
	}
	
	@Test
	public void savePropertyTemplateTest() throws DataException, ParseException{
		
    	User user = userService.getUserByUserId("T04301");
    	Customer customer = customerService.getCustomerByCode("00100");
    	propertyTemplateService.savePropertyTemplate(initPropertyTemplateVo(), customer, user);
    	
    	String sqlStr = "select id from PropertyTemplate where creator.id=?";
        String id =  (String) hibernateTemplate.find(sqlStr, user.getId()).get(0);
        
    	Assert.assertTrue(!"".equals(id));
	}
}
