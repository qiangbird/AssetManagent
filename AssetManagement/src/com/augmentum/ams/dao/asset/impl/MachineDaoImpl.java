package com.augmentum.ams.dao.asset.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.MachineDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Machine;


@Repository("machineDao")
public class MachineDaoImpl extends BaseDaoImpl<Machine> implements MachineDao{

    @Override
    public Machine getByAssetId(String assetId) {

        String hql = "from Machine where asset.id = ?";
        
        return super.getUnique(hql, assetId);
    }

}
