package com.augmentum.ams.service.asset.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.QueryWrapperFilter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.dao.asset.LocationDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.service.asset.LocationService;
import com.augmentum.ams.util.CommonSearchUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Service("locationService")
public class LocationServiceImpl implements LocationService {

    private static Logger logger = Logger.getLogger(LocationServiceImpl.class);
    @Autowired
    private BaseHibernateDao<Location> baseHibernateDao;
    @Autowired
    protected SessionFactory sessionFactory;

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

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.hibernate.HibernateSearchService#searchCommon
     * (com.augmentum.ams.web.vo.system.Page)
     */
    @Override
    public Page<Location> findAllLocationBySearchCondition(SearchCondition searchCondition) {

        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder()
                .forEntity(Location.class).get();

        // create ordinary query, it contains search by keyword
        BooleanQuery keyWordQuery = CommonSearchUtil.searchByKeyWord(
                Location.class, qb, searchCondition.getKeyWord(),
                searchCondition.getSearchFields());

        QueryWrapperFilter filter = null;

        // add entity associate
        Criteria criteria = session.createCriteria(Location.class);

        Page<Location> page = new Page<Location>();

        // set page parameters, sort column, sort sign, page size, current page
        // num
        page.setPageSize(searchCondition.getPageSize());
        page.setCurrentPage(searchCondition.getPageNum());
        page.setSortOrder(searchCondition.getSortSign());
        page.setSortColumn(searchCondition.getSortName());

        FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(keyWordQuery, Location.class)
                .setCriteriaQuery(criteria);
        page = baseHibernateDao.findByIndex(fullTextQuery, filter, page);
        fullTextSession.close();
        return page;
    }

    @Override
    public void createIndexForLocation(Class<Location>... classes) {
        try {
            baseHibernateDao.createIndex(classes);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    @Override
    public Map<String, Location> findAllLocationsFromIAP() {
        
        Map<String, Location> localLocations =  new HashMap<String, Location>();
        List<Location> lcoations = locationDao.findAll(Location.class);
        
        for(Location location : lcoations){
            localLocations.put(location.getSite() + SystemConstants.SPLIT_UNDERLINE
                     + location.getRoom(), location);
        }
        return localLocations;
    }

}
