package com.augmentum.ams.service.asset.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.MonitorDao;
import com.augmentum.ams.model.asset.Monitor;
import com.augmentum.ams.service.asset.MonitorService;

@Service("monitorService")
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private MonitorDao monitorDao;

    @Override
    public void saveMonitor(Monitor monitor) {
        monitorDao.save(monitor);
    }

    @Override
    public void updateMonitor(Monitor monitor) {
        monitorDao.update(monitor);
    }

    @Override
    public Monitor getMonitorById(String id) {
        return monitorDao.get(Monitor.class, id);
    }

    @Override
    public Monitor getByAssetId(String assetId) {
        
        return monitorDao.getByAssetId(assetId);
    }

}
