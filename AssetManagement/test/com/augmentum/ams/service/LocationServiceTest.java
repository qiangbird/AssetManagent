package com.augmentum.ams.service;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.dao.asset.LocationDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.service.asset.LocationService;
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class LocationServiceTest {

    Logger logger = Logger.getLogger(LocationServiceTest.class);
    
	@Autowired
	private LocationService locationService;
	@Autowired
	private LocationDao locationDao;
	@Autowired
    private BaseHibernateDao<Location> baseHibernateDao;
	
	/**
	 * 
	 * @author Jay.He
	 * @time Dec 9, 2013 10:34:59 AM
	 * @throws ParseException
	 */
	@Test
	public void getAllLocationTest() throws ParseException{
		String site="IES_SH";
		List<Location> list = locationService.getAllLocation(site);
		Assert.assertTrue(list.size()>=0);
	}
	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Dec 9, 2013 10:35:17 AM
	 */
	@Test
	public void getLocationBySiteAndRoomTest(){
		String site="Augmentum Shanghai";
		String room="A4301";
		Assert.assertTrue(locationService.getLocationBySiteAndRoom(site,room)!=null);
	}
	
	@SuppressWarnings("unchecked")
	@Test 
	public void createIndexForLocationTest() {
	    String hql = "FROM Location";
	    List<Location> list = (List<Location>)locationDao.getHibernateTemplate().find(hql);
        Class<Location>[] clazzes = new Class[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Location location = list.get(i);
            Class clazz =  location.getClass();
            clazzes[i] = clazz;
        }
        try {
            baseHibernateDao.createIndex(clazzes);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
}
