package com.augmentum.ams.service.asset;

import java.util.List;

import com.augmentum.ams.model.asset.Machine;

public interface MachineService {

    List<Machine> findMachines(String conditions, String keyword);
    
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 7, 2013 5:43:21 PM
     * @param machine
     */
    void saveMachine(Machine machine);
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 28, 2013 10:27:38 AM
     * @param id
     * @return
     */
    Machine getMachineById(String id);
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 28, 2013 10:27:41 AM
     * @param machine
     */
    void updateMachine(Machine machine);

    /**
     * @author John.Li
     * @param id
     * @return
     */
    Machine getByAssetId(String assetId);
    
}
