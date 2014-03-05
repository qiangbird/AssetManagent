package com.augmentum.ams.web.controller.asset;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.service.asset.LocationService;
import com.augmentum.ams.service.remote.RemoteSiteService;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Controller("locationController")
@RequestMapping(value = "/location")
public class LocationController extends BaseController {
    private Logger logger = Logger.getLogger(LocationController.class);
    @Autowired
    private LocationService locationService;

    @Autowired
    RemoteSiteService remoteSiteService;

    @RequestMapping("/listLocation")
    public ModelAndView listLocation(HttpServletRequest request) throws BusinessException {

        Location location = new Location();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("location", location);
        modelAndView.setViewName("location/locationList");

        return modelAndView;
    }

    @RequestMapping(value = "/searchLocation")
    public ModelAndView findAllAssetsBySearchCondition(SearchCondition searchCondition,
            HttpSession session) throws BaseException {

        if (null == searchCondition) {
            searchCondition = new SearchCondition();
        }
        Page<Location> page = locationService.findAllLocationBySearchCondition(searchCondition);
        List<Location> locationList = page.getResult();
        JSONArray array = SearchCommonUtil.formatLocationListTOJSONArray(locationList);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("fieldsData", array);
        modelAndView.addObject("count", page.getRecordCount());
        modelAndView.addObject("totalPage", page.getTotalPage());
        
        return modelAndView;
    }

    /** new */
    @RequestMapping(value = "/new")
    public ModelAndView createLocation(Location location) {

        logger.info("createLocation method start!");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("location/createLocation");

        logger.info("createLocation method end!");
        return modelAndView;
    }

    /** 　save　 */
    @RequestMapping(value = "/save")
    public ModelAndView saveLocation(Location location) {

        logger.info("saveLocation method start!");

        ModelAndView modelAndView = new ModelAndView();
        if(StringUtils.isBlank(location.getId())){
        location.setCreatedTime(new Date());
        location.setUpdatedTime(new Date());
        locationService.saveLocation(location);
        }else{
            Location location1 = locationService.getLocationById(location.getId());
            location1.setRoom(location.getRoom());
            location1.setSite(location.getSite());
            locationService.updateLcoation(location1);
        }
        modelAndView.setViewName("redirect:/location/listLocation");

        logger.info("saveLocation method end!");
        return modelAndView;
    }

    /** delete */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteLocation(@PathVariable String id) {

        logger.info("deleteLocation method start!");

        locationService.deleteLocation(id);

        logger.info("deleteLocation method end!");
        return new ModelAndView();
    }

    /** 　edit　 */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView editLocation(@PathVariable String id) {

        Location location = locationService.getLocationById(id);

        return new ModelAndView("/location/editLocation", "location", location);
    }

    /** save update */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ModelAndView updateLocation(@PathVariable String id, Location location1) {

        Location location = locationService.getLocationById(id);
        location.setRoom(location1.getRoom());
        location.setSite(location1.getSite());
        locationService.updateLcoation(location);

        return new ModelAndView("redirect:/location/list#" + id);

    }

    @RequestMapping("/getLocationSites")
    public ModelAndView getLocationSites(HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();
        List<String> siteList = locationService.findAllSite();
        modelAndView.addObject("siteList", siteList);

        return modelAndView;
    }
    @RequestMapping("getLocationRoom")
    public ModelAndView getLocationRoomBySite(HttpServletRequest request, String currentSite){

        ModelAndView modelAndView = new ModelAndView();
        List<Location> locationList = null;
        List<String> locationRoomList = new ArrayList<String>();
        try {
            locationList = locationService.getAllLocation(currentSite);
        } catch (ParseException e) {
           logger.error("Get location error",e);
        }
        for(Location location : locationList){
            locationRoomList.add(location.getRoom());
        }
        modelAndView.addObject("locationRoomList", locationRoomList);

        return modelAndView;
    }
    
    @RequestMapping("checkLocation")
    public ModelAndView checkRepeatedLocation(String id, String site, String room) {
        
        ModelAndView modelAndView = new ModelAndView();
        Location oldLocation = locationService.getLocationById(id);
        Location newLocation = locationService.getLocationBySiteAndRoom(site, room);
        
        modelAndView.addObject("oldLocation", oldLocation);
        modelAndView.addObject("newLocation", newLocation);
        return modelAndView;
    }
}
