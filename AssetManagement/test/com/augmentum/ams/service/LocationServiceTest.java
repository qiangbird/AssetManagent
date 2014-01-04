package com.augmentum.ams.service;

import java.text.ParseException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
/**
 * Test for LocationService
 * @author Jay.He
 *
 */

import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.service.asset.LocationService;
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class LocationServiceTest {

	@Autowired
	private LocationService locationService;
	
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
		Assert.assertTrue(list.size()==2);
	}
	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Dec 9, 2013 10:35:17 AM
	 */
	@Test
	public void getLocationBySiteAndRoomTest(){
		String site="IES_SH";
		String room="280001";
		Assert.assertTrue(locationService.getLocationBySiteAndRoom(site,room)!=null);
	}
	/**
	 * 
	 * @author Jay.He
	 * @time Dec 9, 2013 10:51:28 AM
	 */
	@Test
	public void saveLocationTest(){
	    Location location = new Location();
	    location.setSite("IES_SH");
	    location.setRoom("280004");
	    locationService.saveLocation(location);
	    Assert.assertNotNull(locationService.getLocationBySiteAndRoom("IES_SH","280004"));
	}
	/**
	 * 
	 * @author Jay.He
	 * @time Dec 9, 2013 10:51:33 AM
	 */
	@Test
	public void updateLocationTest(){
	    Location location = locationService.getLocationBySiteAndRoom("IES_SH","280004");
	    location.setSite("IES_WH");
	    locationService.updateLcoation(location);
	    Assert.assertNotNull(locationService.getLocationBySiteAndRoom("IES_WH","280004"));
	}
	/**
	 * 
	 * @author Jay.He
	 * @time Dec 9, 2013 10:51:38 AM
	 */
	@Test
	public void deleteLcoationTest(){
	    Location location = locationService.getLocationBySiteAndRoom("IES_WH","280004");
	    Assert.assertNotNull(location);
	    locationService.deleteLocation("40285eba42d4f3d10142d4f3f1e20002");
	    Assert.assertNull(locationService.getLocationById("40285eba42d4f3d10142d4f3f1e20002"));
	}
	/**
	 * 
	 * @author Jay.He
	 * @time Dec 9, 2013 10:51:42 AM
	 */
	@Test
	public void getLocationByIdTest(){
	    Location location = locationService.getLocationById("4028961342d538d30142d538e8260000");
	    Assert.assertNotNull(location);
	}
}
