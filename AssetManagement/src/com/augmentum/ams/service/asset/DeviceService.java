package com.augmentum.ams.service.asset;

import com.augmentum.ams.model.asset.Device;

public interface DeviceService {
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 7, 2013 5:42:23 PM
     * @param device
     */
    void saveDevice(Device device);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:03:26 AM
     * @param device
     */
    void updateDevice(Device device);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:03:29 AM
     * @param id
     * @return
     */
    Device findDeviceById(String id);

    /**
     * @author John.Li
     * @param id
     * @return
     */
    Device getByAssetId(String id);
}
