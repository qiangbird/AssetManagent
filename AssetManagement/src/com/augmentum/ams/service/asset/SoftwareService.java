package com.augmentum.ams.service.asset;

import com.augmentum.ams.model.asset.Software;

public interface SoftwareService {
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 7, 2013 5:41:39 PM
     * @param software
     */
    void saveSoftware(Software software);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 28, 2013 9:33:58 AM
     * @param software
     */
    void updateSoftware(Software software);
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:04:57 AM
     * @param id
     * @return
     */
    Software findById(String id);
}
