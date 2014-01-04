package com.augmentum.ams.web.controller.asset;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.asset.DeviceSubtype;
import com.augmentum.ams.service.asset.DeviceSubtypeService;

@Controller("deviceSubtypeController")
@RequestMapping(value = "/deviceSubtype")
public class DeviceSubtypeController {

    @Autowired
    private DeviceSubtypeService deviceSubtypeService;

    @RequestMapping("/getAllSubtypes")
    public ModelAndView getAllSubTypes() {
        System.out.println("herehere");
        ModelAndView modelAndView = new ModelAndView();
        List<DeviceSubtype> deviceSubtypeList = deviceSubtypeService.getAllDeviceSubtype();
        modelAndView.addObject("deviceSubtypeList", deviceSubtypeList);
        return modelAndView;
    }
}
