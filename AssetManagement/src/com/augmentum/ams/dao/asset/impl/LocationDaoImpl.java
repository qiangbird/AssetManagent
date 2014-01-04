package com.augmentum.ams.dao.asset.impl;

import java.text.ParseException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.LocationDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.Location;

@Repository("locationDao")
public class LocationDaoImpl extends BaseDaoImpl<Location> implements LocationDao {

    public List<Location> getLocation(String site) throws ParseException {
        List<Location> list = super.find("from Location l"
                + " where l.site = ? and l.isExpired = ? order by l.site", site, Boolean.FALSE);
        return list;
    }

    @Override
    public Location getLocationBySiteAndRoom(String site, String room) {
        String hql = "from Location where site = ? and room = ? and isExpired = false";
        return super.getUnique(hql, site.trim(), room.trim());
    }

    @Override
    public Location getLocationById(String id) {
        String hql = "from Location where id = ? and isExpired = false";
        return super.getUnique(hql, id);
    }

}
