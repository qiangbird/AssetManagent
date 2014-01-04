package com.augmentum.ams.service.asset.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.DeviceDao;
import com.augmentum.ams.model.asset.Device;
import com.augmentum.ams.service.asset.DeviceService;

@Service("deviceService")
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public void saveDevice(Device device) {
        deviceDao.save(device);
    }

    @Override
    public void updateDevice(Device device) {
        deviceDao.update(device);
    }

    @Override
    public Device findDeviceById(String id) {
        return deviceDao.get(Device.class, id);
    }

}
