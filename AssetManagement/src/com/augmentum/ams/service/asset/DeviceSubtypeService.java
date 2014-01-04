package com.augmentum.ams.service.asset;

import java.util.List;

import com.augmentum.ams.model.asset.DeviceSubtype;

public interface DeviceSubtypeService {
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 8, 2013 10:51:53 AM
     * @return
     */
    List<DeviceSubtype> getAllDeviceSubtype();

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 8, 2013 10:51:56 AM
     * @param deviceSubtype
     */
    void saveDeviceSubtype(DeviceSubtype deviceSubtype);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 8, 2013 10:51:59 AM
     * @param subtypeName
     * @return
     */
    List<DeviceSubtype> getDeviceSubtypeByName(String subtypeName);
}
