package com.augmentum.ams.service.asset.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.SoftwareDao;
import com.augmentum.ams.model.asset.Software;
import com.augmentum.ams.service.asset.SoftwareService;

@Service("softwareService")
public class SoftwareServiceImpl implements SoftwareService {

    @Autowired
    private SoftwareDao softwareDao;

    @Override
    public void saveSoftware(Software software) {
        softwareDao.save(software);

    }

    @Override
    public void updateSoftware(Software software) {
        softwareDao.update(software);
    }

    @Override
    public Software findById(String id) {
        return softwareDao.get(Software.class, id);
    }

}
