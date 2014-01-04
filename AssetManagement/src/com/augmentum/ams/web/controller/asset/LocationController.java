package com.augmentum.ams.web.controller.asset;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.service.asset.LocationService;
import com.augmentum.ams.web.controller.base.BaseController;

@Controller("locationController")
@RequestMapping(value = "/location")
public class LocationController extends BaseController {
    private Logger log = Logger.getLogger(LocationController.class);
    @Autowired
    private LocationService locationService;

    @RequestMapping("/list")
    public ModelAndView listLocation() {
        ModelAndView modelAndView = new ModelAndView();
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<Location> locationList = new ArrayList();
        try {
            locationList = locationService.getAllLocation();
        } catch (ParseException e) {
            log.info("Get location List error!");
            e.printStackTrace();
        }
        modelAndView.addObject("locationList", locationList);
        modelAndView.setViewName("location/locationList");
        return modelAndView;
    }

    /** new */
    @RequestMapping(value = "/new")
    public ModelAndView createLocation(Location location) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("location/createLocation");
        return modelAndView;
    }

    /** 　save　 */
    @RequestMapping(value = "/save")
    public ModelAndView saveLocation(Location location) {
        ModelAndView modelAndView = new ModelAndView();
        location.setCreatedTime(new Date());
        location.setUpdatedTime(new Date());
        locationService.saveLocation(location);
        modelAndView.setViewName("redirect:/location/list");
        return modelAndView;
    }

    /** delete */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteLocation(@PathVariable String id) {
        locationService.deleteLocation(id);
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
}
