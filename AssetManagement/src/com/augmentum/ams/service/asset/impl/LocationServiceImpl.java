package com.augmentum.ams.service.asset.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.LocationDao;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.service.asset.LocationService;

@Service("locationService")
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationDao locationDao;

    @Override
    public List<Location> getAllLocation(String site) throws ParseException {
        return locationDao.getLocation(site);
    }

    @Override
    public Location getLocationBySiteAndRoom(String site, String room) {
        return locationDao.getLocationBySiteAndRoom(site, room);
    }

    @Override
    public List<Location> getAllLocation() throws ParseException {
        return locationDao.findAll(Location.class);
    }

    @Override
    public void saveLocation(Location location) {
        locationDao.save(location);
    }

    @Override
    public void updateLcoation(Location location) {
        locationDao.update(location);
    }

    @Override
    public void deleteLocation(String id) {
        Location location = locationDao.getLocationById(id);
        locationDao.delete(location);
    }

    @Override
    public Location getLocationById(String id) {
        return locationDao.getLocationById(id);
    }

}
