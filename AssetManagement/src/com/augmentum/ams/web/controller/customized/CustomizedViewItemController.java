package com.augmentum.ams.web.controller.customized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.customized.CustomizedViewItem;
import com.augmentum.ams.service.customized.CustomizedViewItemService;
import com.augmentum.ams.web.controller.base.BaseController;

@Controller("customizedViewItemController")
@RequestMapping(value = "/customizedViewItem")
public class CustomizedViewItemController extends BaseController {

    @Autowired
    private CustomizedViewItemService customizedViewItemService;

    @RequestMapping(value = "/getCustomizedViewItemDetail")
    public ModelAndView getCustomizedViewItemDetail(String customizedViewItemId) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("newCustomizedView");
        CustomizedViewItem customizedViewItem = customizedViewItemService
                .getCustomizedViewItemById(customizedViewItemId);
        modelAndView.addObject("customizedViewItem", customizedViewItem);

        return modelAndView;
    }
}
