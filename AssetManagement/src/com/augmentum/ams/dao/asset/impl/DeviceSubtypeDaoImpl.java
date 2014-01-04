package com.augmentum.ams.dao.asset.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.DeviceSubtypeDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.DeviceSubtype;

@Repository("deviceSubtypeDao")
public class DeviceSubtypeDaoImpl extends BaseDaoImpl<DeviceSubtype> implements DeviceSubtypeDao {

    @Override
    public List<DeviceSubtype> getDeviceSubtypeByName(String subtypeName) {
        String hql = "from DeviceSubtype where subtypeName = ?";
        return super.find(hql, subtypeName);
    }

}
