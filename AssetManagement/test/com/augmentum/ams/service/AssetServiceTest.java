/**
 * 
 */
package com.augmentum.ams.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.dao.asset.DeviceDao;
import com.augmentum.ams.dao.asset.DeviceSubtypeDao;
import com.augmentum.ams.dao.asset.LocationDao;
import com.augmentum.ams.dao.asset.MachineDao;
import com.augmentum.ams.dao.asset.MonitorDao;
import com.augmentum.ams.dao.asset.OtherAssetsDao;
import com.augmentum.ams.dao.asset.SoftwareDao;
import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.dao.user.UserDao;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Device;
import com.augmentum.ams.model.asset.DeviceSubtype;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.asset.Machine;
import com.augmentum.ams.model.asset.Monitor;
import com.augmentum.ams.model.asset.OtherAssets;
import com.augmentum.ams.model.asset.Software;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.model.enumeration.AssetTypeEnum;
import com.augmentum.ams.model.enumeration.MachineSubtypeEnum;
import com.augmentum.ams.model.enumeration.StatusEnum;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.asset.CustomerAssetService;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.web.vo.asset.AssetVo;

/**
 * @author Grylls.Xu
 * @time Sep 24, 2013 4:46:38 PM
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class AssetServiceTest {

    Logger logger = Logger.getLogger(AssetServiceTest.class);
    
    @Autowired
    private AssetService assetService;
    @Autowired
    private SoftwareDao softwareDao;
    @Autowired
    private MachineDao machineDao;
    @Autowired
    private MonitorDao monitorDao;
    @Autowired
    private DeviceDao deviceDao;
    @Autowired
    private OtherAssetsDao otherAssetsDao;
    @Autowired
    private LocationDao locationDao;
    @Autowired
    private DeviceSubtypeDao deviceSubtypeDao;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerAssetService customerAssetService;
    @Autowired
    private BaseDao baseDao;
    @Autowired
    private UserDao userDao;
    
    @Test
    public void testSaveAsset() {
        Asset asset = this.initAsset();
        
        Software software = this.initSofrware();
        Machine machine = this.initMachine();
        Monitor monitor = this.initMonitor();
        Device device = this.initDevice();
        OtherAssets oAssets = this.initOtherAssets();
        Location location = this.initLocation();
        
        asset.setAssetId("Asset11111");
        asset.setAssetName("111111");
        asset.setType("MACHINE");
        asset.setStatus("AVAILABLE");
        softwareDao.save(software);
        locationDao.save(location);
        asset.setSoftware(software);
        asset.setLocation(location);
        assetService.saveAsset(asset);
        
        machine.setAsset(asset);
        monitor.setAsset(asset);
        device.setAsset(asset);
        oAssets.setAsset(asset);
        machineDao.save(machine);
        monitorDao.save(monitor);
        deviceDao.save(device);
        otherAssetsDao.save(oAssets);
    }
    
    
    private Asset initAsset() {
        Asset asset = new Asset();
        
        asset.setAssetName("assetName");
        asset.setManufacturer("manufacturer");
        asset.setBarCode("barCode");
        asset.setSeriesNo("seriesNo");
        asset.setPoNo("poNo");
        asset.setPhotoPath("photoPath");
        asset.setOwnerShip("ownerShip");
        asset.setFixed(true);
        asset.setMemo("memo");
        asset.setCheckInTime(new Date());
        asset.setCheckOutTime(new Date());
        asset.setWarrantyTime(new Date());
        asset.setCreatedTime(new Date());
        
        return asset;
    }

    /**
     * @author Grylls.Xu
     * @time Sep 24, 2013 5:30:44 PM
     * @description init location
     * @return
     */
    private Location initLocation() {
        Location location = new Location();
        location.setRoom("room");
        location.setSite("site");
        location.setCreatedTime(new Date());
        return location;
    }

    /**
     * @author Grylls.Xu
     * @time Sep 24, 2013 5:30:42 PM
     * @description init otherAssets
     * @return
     */
    private OtherAssets initOtherAssets() {
        OtherAssets otherAssets = new OtherAssets();
        otherAssets.setDetail("detail");
        otherAssets.setCreatedTime(new Date());
        return otherAssets;
    }

    /**
     * @author Grylls.Xu
     * @time Sep 24, 2013 5:30:40 PM
     * @description init device
     * @return
     */
    private Device initDevice() {
        Device device = new Device();
        DeviceSubtype deviceSubtype = this.initDeviceSubtype();
        
        deviceSubtypeDao.save(deviceSubtype);
        
        device.setConfiguration("configuration");
        device.setDeviceSubtype(deviceSubtype);
        device.setCreatedTime(new Date());
        return device;
    }
    
    private DeviceSubtype initDeviceSubtype() {
        DeviceSubtype deviceSubtype = new DeviceSubtype();
        deviceSubtype.setSubtypeName("subtypeName");
        deviceSubtype.setCreatedTime(new Date());
        return deviceSubtype;
    }

    /**
     * @author Grylls.Xu
     * @time Sep 24, 2013 5:30:38 PM
     * @description init monitor
     * @return
     */
    private Monitor initMonitor() {
        Monitor monitor = new Monitor();
        monitor.setSize("size");
        monitor.setDetail("detail");
        monitor.setCreatedTime(new Date());
        return monitor;
    }

    /**
     * @author Grylls.Xu
     * @time Sep 24, 2013 5:30:35 PM
     * @description init machine
     * @return
     */
    private Machine initMachine() {
        Machine machine = new Machine();
        machine.setSubtype(MachineSubtypeEnum.DESKTOP.toString());
        machine.setSpecification("specification");
        machine.setAddress("address");
        machine.setConfiguration("configuration");
        machine.setCreatedTime(new Date());
        return machine;
    }

    /**
     * @author Grylls.Xu
     * @time Sep 24, 2013 5:35:36 PM
     * @description init software
     * @return
     */
    private Software initSofrware() {
        Software software = new Software();
        software.setVersion("version");
        software.setLicenseKey("licenseKey");
        software.setSoftwareExpiredTime(new Date());
        software.setMaxUseNum(10);
        software.setCreatedTime(new Date());
        return software;
    }
    
    /**
     * 
     * @description Test saveAsset method in assetService
     * @author Jay.He
     * @time Oct 18, 2013 2:53:02 PM
     */
    @Test
    public void saveAssetTest(){
    	Asset asset=new Asset();
    	asset.setId("0000000000000000000000000");
    	asset.setAssetId("gfdgdfgfdg");
    	asset.setAssetName("aaa");
    	asset.setFixed(false);
    	asset.setStatus(StatusEnum.AVAILABLE.toString());
    	asset.setType(AssetTypeEnum.DEVICE.toString());
    	assetService.saveAsset(asset);
    }
    
    /**
     * 
     * @description Test updateAsset method in assetService
     * @author Jay.He
     * @time Dec 9, 2013 9:35:24 AM
     */
    @Test
    public void updateAssetTest(){
        Asset asset=new Asset();
        asset.setAssetId("gfdgdfgfdg");
        asset.setAssetName("aaa");
        asset.setFixed(false);
        asset.setStatus(StatusEnum.AVAILABLE.toString());
        asset.setType(AssetTypeEnum.DEVICE.toString());
        assetService.saveAsset(asset);
       asset.setAssetName("MyAssetTest");
       asset.setBarCode("wawawa");
       assetService.updateAsset(asset);
       Assert.assertEquals(assetService.getAsset(asset.getId()).getAssetName(),"MyAssetTest");
    }
    
    /**
     * 
     * @description Test getAsset method in assetService
     * @author Jay.He
     * @time Dec 9, 2013 9:36:16 AM
     */
    @Test
    public void getAssetByIdTest(){
        Asset asset=new Asset();
        asset.setAssetId("gfdgdfgfdg");
        asset.setAssetName("aaa");
        asset.setFixed(false);
        asset.setStatus(StatusEnum.AVAILABLE.toString());
        asset.setType(AssetTypeEnum.DEVICE.toString());
        assetService.saveAsset(asset);
        Assert.assertNotNull(assetService.getAsset(asset.getId()));
    }
    /**
     * 
     * @description Test deleteAssetById method in assetService
     * @author Jay.He
     * @time Dec 9, 2013 10:28:21 AM
     */
    @Test
    public void deleteAssetByIdTest(){
        Asset asset=new Asset();
        asset.setAssetId("gfdgdfgfdg");
        asset.setAssetName("aaa");
        asset.setFixed(false);
        asset.setStatus(StatusEnum.AVAILABLE.toString());
        asset.setType(AssetTypeEnum.DEVICE.toString());
        assetService.saveAsset(asset);
        assetService.deleteAssetById(asset.getId());
        Asset asset2 = assetService.getAsset(asset.getId());
        Assert.assertNull(asset2);
    }
    
    /**
     * 
     * @description Test findAllAssets method in assetService
     * @author Jay.He
     * @throws ParseException 
     * @time Nov 6, 2013 8:35:01 AM
     */
    @Test
    public void findAllAssetsTest() {
        Asset asset=new Asset();
        asset.setAssetId("gfdgdfgfdg");
        asset.setAssetName("aaa");
        asset.setFixed(false);
        asset.setStatus(StatusEnum.AVAILABLE.toString());
        asset.setType(AssetTypeEnum.DEVICE.toString());
        assetService.saveAsset(asset);
    	List<Asset> list=assetService.findAllAssets();
    	Assert.assertTrue(list.size()>0);
    	logger.info(list.size());
    }
    
    /**
     * 
     * @description Test getAllAssetCount method in assetService
     * @author Jay.He
     * @time Dec 9, 2013 9:53:49 AM
     */
    @Test
    public void getAllAssetCountTest(){
        Asset asset=new Asset();
        asset.setAssetId("gfdgdfgfdg");
        asset.setAssetName("aaa");
        asset.setFixed(false);
        asset.setStatus(StatusEnum.AVAILABLE.toString());
        asset.setType(AssetTypeEnum.DEVICE.toString());
        assetService.saveAsset(asset);
        int allCount = assetService.getAllAssetCount();
        Assert.assertTrue(allCount>0);
        logger.info(allCount);
    }
    
    /**
     * 
     * @description Test saveAssetAsType method in assetService
     * @author Jay.He
     * @time Dec 9, 2013 10:28:30 AM
     */
    @Test
    public void saveAssetAsTypeTest(){
        AssetVo assetVo = new AssetVo();
        Asset asset = new Asset();
        
        assetVo.setAssetId("AssetSaveTest1");
        assetVo.setAssetName("AssetSaveTest1");
        assetVo.setType("MACHINE");
        assetVo.setStatus("AssetSaveTest1");
        Machine machine = new Machine();
        machine.setAddress("AssetSaveTest1");
        assetVo.setMachine(machine);
        assetService.saveAssetAsType(assetVo,asset,"save");
    }
    /**
     * 
     * @description Test getGenerateAssetId method in assetService
     * @author Jay.He
     * @time Dec 9, 2013 10:28:33 AM
     */
    @Test
    public void getGenerateAssetIdTest(){
        String assetId = assetService.getGenerateAssetId();
        logger.info(assetId);
        String allCount = assetService.getAllAssetCount()+"";
        Assert.assertEquals(assetId.substring(assetId.length()-3,assetId.length()),allCount);
    }
    /**
     * 
     * @description Test showOrViewSelfDefinedProperties method in assetService
     * @author Jay.He
     * @time Dec 9, 2013 10:28:36 AM
     * @throws ParseException
     */
    @Test
    public void showOrViewSelfDefinedPropertiesTest(){
        Asset asset=new Asset();
        asset.setAssetId("rrrrrrrrrrr");
        asset.setAssetName("aaa");
        asset.setFixed(false);
        asset.setStatus(StatusEnum.AVAILABLE.toString());
        asset.setType(AssetTypeEnum.DEVICE.toString());
        
        Customer customer = new Customer();
        customer.setCustomerName("Augmentum");
        customerService.saveCustomer(customer);
        asset.setCustomer(customer);
        assetService.saveAsset(asset);
        Asset asset1 = assetService.getAsset(asset.getId());
        List<PropertyTemplate> list = null;
        try {
            list = assetService.showOrViewSelfDefinedProperties(asset1,"show");
        } catch (Exception e) {
            logger.error("Get Self-define property error!",e);
        }
        Assert.assertEquals(list.size(),0);
    }
    @Test
    public void findAssetsByCustomerIdTest(){
        
        
        Asset asset=new Asset();
        asset.setAssetId("rrrrrrrrrrr");
        asset.setAssetName("aaa");
        asset.setFixed(false);
        asset.setStatus(StatusEnum.AVAILABLE.toString());
        asset.setType(AssetTypeEnum.DEVICE.toString());
        
        Customer customer = new Customer();
        customer.setCustomerName("Augmentum");
        customer.setCustomerCode("11111111");
        customerService.saveCustomer(customer);
        asset.setCustomer(customer);
        assetService.saveAsset(asset);
        Customer customer1 = customerService.getCustomerByCode("11111111");
        List<Asset> list = assetService.findAssetsByCustomerId(customer1.getId());
        System.out.println(list.size());
        Assert.assertTrue(list.size()>0);
    }
    
	@Test
    public void testGetAssetCount() {
    	String hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false GROUP BY type ORDER BY type";
		List list = baseDao.getHibernateTemplate().find(hql);
		List<Integer> result = new ArrayList<Integer>();
		String[] type = {"DEVICE", "MACHINE", "MONITOR", "OTHERASSETS", "SOFTWARE"};
		
		if (0 == list.size()) {
			for (int i = 0; i < type.length; i++) {
				result.add(0);
			}
		} else {
			
			for (int i = 0; i < type.length; i++) {
				
				loop: for (int j = 0; j < list.size(); j++) {
					
					Object[] obj = (Object[])list.get(j);
					if (type[i].equals(obj[0])) {
						result.add(Integer.valueOf((obj[1]).toString()));
						break loop;
					}
					if (j == list.size() - 1) {
						result.add(0);
					}
				}
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[])list.get(i);
			logger.info(obj[0] + "--" + obj[1]);
		}
		
		for (Integer i : result) {
			logger.info(i);
		}
    }
	
	@Test
	public void testGetAssetCountForPanel() {
		User user = userDao.getUserByUserId("T00245");
		Map<String, Integer> map = assetService.getAssetCountForPanel(user);
		logger.info(map.size());
		
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			   logger.info(entry.getKey() + "----" + entry.getValue());
		}
		
		logger.info(map);
		logger.info("convertToJSONObject" + JSONObject.fromObject(map));
	}
	
	@Test
	public void testGetAssetCountForManagerInHql() {
		String[] customerIds = {"402896124300cb0e014300cc3c7d0000", "402896124303f9f7014303fe41300001"};
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		String hql = "SELECT type, COUNT(*) FROM Asset WHERE isExpired = false AND customer.id in (:customerIds) GROUP BY type ORDER BY type";
		Session session = baseDao.getHibernateTemplate().getSessionFactory().openSession();
		Query query = null;
		query = session.createQuery(hql).setParameterList("customerIds", customerIds);
		List list = query.list();
		map.put("type", query.list().size());
		logger.info("type: " + query.list().size());
		
		hql = "SELECT COUNT(*) FROM Asset WHERE isExpired = false AND customer.id in (:customerIds)";
		query = session.createQuery(hql).setParameterList("customerIds", customerIds);
		map.put("count", Integer.parseInt(query.list().get(0).toString()));
		logger.info("count: " + query.list().get(0));
		
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[])list.get(i);
			logger.info(obj[0] + "--" + obj[1]);
		}
		for (Entry<String, Integer> entry : map.entrySet()) {
			logger.info(entry.getKey() + "--" + entry.getValue());
		}
	}
	
}
