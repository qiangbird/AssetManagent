package com.augmentum.ams.dao.asset;

import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.asset.DeviceSubtype;

public interface DeviceSubtypeDao extends BaseDao<DeviceSubtype>{
	
	/**
	 * 
	 * @description Get Device Subtype by given name
	 * @author Jay.He
	 * @time Nov 8, 2013 10:48:39 AM
	 * @param subtypeName
	 * @return
	 */
	List<DeviceSubtype> getDeviceSubtypeByName(String subtypeName);
}
