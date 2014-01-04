package com.augmentum.ams.service.asset;

import com.augmentum.ams.model.asset.Monitor;

public interface MonitorService {

	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Nov 28, 2013 10:29:27 AM
	 * @param monitor
	 */
	void saveMonitor(Monitor monitor);
	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Nov 28, 2013 10:29:56 AM
	 * @param monitor
	 */
	void updateMonitor(Monitor monitor);
	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Nov 28, 2013 10:30:01 AM
	 * @param id
	 * @return
	 */
	Monitor getMonitorById(String id);
}
