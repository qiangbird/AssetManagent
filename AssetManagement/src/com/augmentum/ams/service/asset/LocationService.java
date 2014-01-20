package com.augmentum.ams.service.asset;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public interface LocationService {
    /**
     * Get all location as given site
     * 
     * @description TODO
     * @author Jay.He
     * @time Oct 23, 2013 4:31:01 PM
     * @param site
     * @return
     */
    List<Location> getAllLocation(String site) throws ParseException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 7, 2013 3:54:41 PM
     * @param site
     * @param room
     * @return
     */
    Location getLocationBySiteAndRoom(String site, String room);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:03:54 AM
     * @return
     * @throws ParseException
     */
    List<Location> getAllLocation() throws ParseException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:04:15 AM
     * @param location
     */
    void saveLocation(Location location);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:04:19 AM
     * @param location
     */
    void updateLcoation(Location location);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:04:23 AM
     * @param id
     */
    void deleteLocation(String id);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:04:27 AM
     * @param id
     * @return
     */
    Location getLocationById(String id);
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Jan 4, 2014 5:34:45 PM
     * @param searchCondition
     * @return
     */
    Page<Location> findAllLocationBySearchCondition(SearchCondition searchCondition);
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Jan 6, 2014 8:43:20 AM
     * @param classes
     */
    void createIndexForLocation(Class<Location>... classes);

    /**
     * @author John.Li
     * @return
     */
    Map<String, Location> findAllLocationsFromIAP();
}
