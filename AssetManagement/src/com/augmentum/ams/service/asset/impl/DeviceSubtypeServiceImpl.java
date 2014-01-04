package com.augmentum.ams.service.asset.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.DeviceSubtypeDao;
import com.augmentum.ams.model.asset.DeviceSubtype;
import com.augmentum.ams.service.asset.DeviceSubtypeService;

@Service("deviceSubtypeService")
public class DeviceSubtypeServiceImpl implements DeviceSubtypeService {

    @Autowired
    private DeviceSubtypeDao deviceSubtypeDao;

    @Override
    public List<DeviceSubtype> getAllDeviceSubtype() {
        return deviceSubtypeDao.findAll(DeviceSubtype.class);
    }

    @Override
    public void saveDeviceSubtype(DeviceSubtype deviceSubtype) {
        deviceSubtypeDao.save(deviceSubtype);
    }

    @Override
    public List<DeviceSubtype> getDeviceSubtypeByName(String subtypeName) {
        return deviceSubtypeDao.getDeviceSubtypeByName(subtypeName);
    }

}
