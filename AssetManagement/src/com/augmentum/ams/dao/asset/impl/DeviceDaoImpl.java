package com.augmentum.ams.dao.asset.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.DeviceDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Device;


@Repository("deviceDao")
public class DeviceDaoImpl extends BaseDaoImpl<Device> implements DeviceDao{

}
