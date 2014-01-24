package com.augmentum.ams.dao.asset;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.asset.Device;

public interface DeviceDao extends BaseDao<Device>{

    /**
     * @author John.Li
     * @param id
     * @return
     */
    Device getByAssetId(String id);
	
}
