package com.augmentum.ams.dao.asset.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.MonitorDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Monitor;


@Repository("monitorDao")
public class MonitorDaoImpl extends BaseDaoImpl<Monitor> implements MonitorDao{

    @Override
    public Monitor getByAssetId(String assetId) {
        
        String hql = "from Monitor where asset.id= ?";
        
        return super.getUnique(hql, assetId);
    }

}
