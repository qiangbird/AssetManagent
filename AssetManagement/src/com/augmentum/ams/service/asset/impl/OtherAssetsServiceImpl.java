package com.augmentum.ams.service.asset.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.OtherAssetsDao;
import com.augmentum.ams.model.asset.OtherAssets;
import com.augmentum.ams.service.asset.OtherAssetsService;

@Service("otherAssetsService")
public class OtherAssetsServiceImpl implements OtherAssetsService {

    @Autowired
    private OtherAssetsDao otherAssetsDao;

    @Override
    public void saveOtherAssets(OtherAssets otherAssets) {
        otherAssetsDao.save(otherAssets);
    }

    @Override
    public void updateOtherAssets(OtherAssets otherAssets) {
        otherAssetsDao.update(otherAssets);
    }

    @Override
    public OtherAssets getOtherAssetsById(String id) {
        return otherAssetsDao.get(OtherAssets.class, id);
    }

    @Override
    public OtherAssets getByAssetId(String id) {
        
        return otherAssetsDao.getOtherAssetsById(id);
    }

}
