package com.augmentum.ams.dao.asset;

import java.text.ParseException;
import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.asset.Location;

public interface LocationDao extends BaseDao<Location>{
	/**
	 * 
	 * @description Get location as given site location eg:site-room
	 * @author Jay.He
	 * @time Oct 23, 2013 4:24:25 PM
	 * @param site
	 * @return
	 */
	public List<Location> getLocation(String site) throws ParseException ;
	
	/**
	 * 
	 * @description Get location by site and room
	 * @author Jay.He
	 * @time Nov 7, 2013 3:52:53 PM
	 * @return
	 */
	public Location getLocationBySiteAndRoom(String site,String room);
	/**
	 * 
	 * @description Get location by id
	 * @author Jay.He
	 * @time Dec 9, 2013 11:11:51 AM
	 * @param id
	 * @return
	 */
	public Location getLocationById(String id);
	
}
