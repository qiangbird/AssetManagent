package com.augmentum.ams.web.controller.asset;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.mrbean.BeanUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.asset.LocationService;
import com.augmentum.ams.service.remote.RemoteSiteService;
import com.augmentum.ams.util.FormatEntityListToEntityVoList;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.AssetListVo;
import com.augmentum.ams.web.vo.asset.SiteVo;
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
//    @RequestMapping("/list")
//    public ModelAndView listLocation() {
//        ModelAndView modelAndView = new ModelAndView();
//        @SuppressWarnings({ "unchecked", "rawtypes" })
//        List<Location> locationList = new ArrayList();
//        try {
//            locationList = locationService.getAllLocation();
//        } catch (ParseException e) {
//            log.info("Get location List error!");
//            e.printStackTrace();
//        }
//        modelAndView.addObject("locationList", locationList);
//        modelAndView.setViewName("location/locationList");
//        return modelAndView;
//    }
    
    @RequestMapping("/listLocation")
    public ModelAndView goLocation(HttpServletRequest request) throws DataException {
        Location location = new Location();
        ModelAndView modelAndView = new ModelAndView();
//        List<SiteVo> siteList = remoteSiteService.getSiteFromIAP(request);
//      modelAndView.addObject("siteList",siteList);
        modelAndView.addObject("location",location);
        modelAndView.setViewName("location/locationList");
        return modelAndView;
    }
    @RequestMapping(value = "/list")
    public ModelAndView findAllAssetsBySearchCondition(SearchCondition searchCondition,
            HttpSession sessiont) throws BaseException {

        Page<Location> page = locationService.findAllLocationBySearchCondition(searchCondition);
        List<Location> locationList = page.getResult();
        System.out.println(locationList.size());
        JSONArray array = new JSONArray();
        array = SearchCommonUtil.formatLocationListTOJSONArray(locationList);
        
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("fieldsData", array);
        modelAndView.addObject("count", page.getRecordCount());
        modelAndView.addObject("totalPage", page.getTotalPage());
        
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
        modelAndView.setViewName("redirect:/location/listLocation");
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
    
    @RequestMapping("/getLocationSites")
    public ModelAndView getLocationSites(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        List<SiteVo> siteList = null;
        try {
            siteList = remoteSiteService.getSiteFromIAP(request);
        } catch (DataException e) {
            logger.error("Cannot get site information from IAP",e);
        }
        modelAndView.addObject("siteList", siteList);
        return modelAndView;
    }
}
