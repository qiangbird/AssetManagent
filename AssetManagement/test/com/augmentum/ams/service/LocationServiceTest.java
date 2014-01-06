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
/**
 * Test for LocationService
 * @author Jay.He
 *
 */

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.service.asset.LocationService;
import com.augmentum.ams.service.search.SearchAssetService;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class LocationServiceTest {

    Logger logger = Logger.getLogger(LocationServiceTest.class);
    
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
		String site="IES_SH";
		String room="280001";
		Location location = new Location();
        location.setSite(site);
        location.setRoom(room);
        locationService.saveLocation(location);
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
	    location.setSite("IES_BJ");
	    location.setRoom("100110");
	    locationService.saveLocation(location);
	    Assert.assertNotNull(locationService.getLocationBySiteAndRoom("IES_BJ","100110"));
	}
	/**
	 * 
	 * @author Jay.He
	 * @time Dec 9, 2013 10:51:33 AM
	 */
	@Test
	public void updateLocationTest(){
	    Location location1 = new Location();
        location1.setSite("IES_BJ");
        location1.setRoom("100110");
        locationService.saveLocation(location1);
	    Location location = locationService.getLocationBySiteAndRoom("IES_BJ","100110");
	    location.setSite("IES_WH");
	    locationService.updateLcoation(location);
	    Assert.assertNotNull(locationService.getLocationBySiteAndRoom("IES_BJ","100110"));
	}
	/**
	 * 
	 * @author Jay.He
	 * @time Dec 9, 2013 10:51:38 AM
	 */
	@Test
	public void deleteLcoationTest(){
//	    Location location = locationService.getLocationBySiteAndRoom("IES_WH","280004");
//	    Assert.assertNotNull(location);
//	    locationService.deleteLocation("40285eba42d4f3d10142d4f3f1e20002");
//	    Assert.assertNull(locationService.getLocationById("40285eba42d4f3d10142d4f3f1e20002"));
	}
	/**
	 * 
	 * @author Jay.He
	 * @time Dec 9, 2013 10:51:42 AM
	 */
	@Test
	public void getLocationByIdTest(){
//	    Location location = locationService.getLocationById("4028961342d538d30142d538e8260000");
//	    Assert.assertNotNull(location);
	}
	
	@Test
	public void findAllLocationBySearchConditionTest(){
	    SearchCondition searchCondition = new SearchCondition();
	    searchCondition.setPageNum(1);
	    searchCondition.setPageSize(20);
	    searchCondition.setSortName("site");
	    searchCondition.setSortSign("desc");
	    searchCondition.setKeyWord("SH");
	 Page<Location> locations =  locationService.findAllLocationBySearchCondition(searchCondition);
	 Assert.assertNotNull(locations);
	 logger.info(locations.getResult().size());
	 for (Location l : locations.getResult()) {
        logger.info(l.getSite() + "--" + l.getRoom());
    }
	}
	
	@Test 
	public void createIndexForLocationTest() throws ParseException{
	    List<Location> list = locationService.getAllLocation();
        Class<Location>[] clazzes = new Class[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Location location = list.get(i);
            Class clazz =  location.getClass();
            clazzes[i] = clazz;
        }
	    locationService.createIndexForLocation(clazzes);
	}
}
