package com.augmentum.ams.web.controller.customized;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.exception.ParameterException;
import com.augmentum.ams.model.customized.CustomizedView;
import com.augmentum.ams.model.customized.CustomizedViewItem;
import com.augmentum.ams.service.customized.CustomizedViewItemService;
import com.augmentum.ams.service.customized.CustomizedViewService;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.customized.CustomizedViewVo;

@Controller("customizedViewController")
@RequestMapping(value = "/customizedView")
public class CustomizedViewController extends BaseController {

    @Autowired
    private CustomizedViewService customizedViewService;

    @Autowired
    private CustomizedViewItemService customizedViewItemService;

    @RequestMapping(value = "/goToNewCustomizedView")
    public String goToNewCustomizedView() {
        return "customize/customizedView/newCustomizedView";
    }

    @RequestMapping(value = "/newCustomizedView", method = RequestMethod.POST)
    public ModelAndView saveCustomizedViewAndItem(CustomizedViewVo customizedViewVo)
            throws ParameterException, DataException {

        RedirectView redirectView = new RedirectView("findCustomizedViewByUserForManagement");
        ModelAndView modelAndView = new ModelAndView(redirectView);

        customizedViewItemService.saveCustomizedViewItem(customizedViewVo);
        return modelAndView;
    }

    @RequestMapping(value = "/deleteCustomizedView", method = RequestMethod.POST)
    public ModelAndView deleteCustomizedViewAndItem(String customizedViewId) throws BaseException {

        ModelAndView modelAndView = new ModelAndView();
        CustomizedViewVo customizedViewVo = new CustomizedViewVo();

        customizedViewVo.setCustomizedViewId(customizedViewId);
        customizedViewService.deleteCustomizedView(customizedViewVo);
        customizedViewItemService.deleteCustomizedViewItem(customizedViewVo);
        return modelAndView;
    }

    @RequestMapping(value = "/updateCustomizedView")
    public ModelAndView updateCustomizedViewAndItem(CustomizedViewVo customizedViewVo)
            throws BaseException {

        RedirectView redirectView = new RedirectView("findCustomizedViewByUserForManagement");
        ModelAndView modelAndView = new ModelAndView(redirectView);

        customizedViewItemService.updateCustomizedViewItem(customizedViewVo);
        return modelAndView;
    }

    @RequestMapping(value = "/findCustomizedViewByUserForManagement")
    public ModelAndView findCustomizedViewByUserForManagement() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customize/customizedView/customizedViewList");
        String creatorId = getUserIdByShiro();

        List<CustomizedView> customizedViews = customizedViewService
                .findCustomizedViewByUser(creatorId);
        modelAndView.addObject("customizedViews", customizedViews);

        return modelAndView;
    }

    @RequestMapping(value = "/findCustomizedViewByUserForManu")
    @ResponseBody
    public JSONArray findCustomizedViewByUserForManu() {

        String creatorId = getUserIdByShiro();

        List<CustomizedView> customizedViews = customizedViewService
                .findCustomizedViewByUser(creatorId);

        return customizedViewService.changeCustomizedViewToJson(customizedViews);
    }

    @RequestMapping(value = "/getCustomizedViewDetail")
    public ModelAndView getCustomizedViewDetail(String customizedViewId) throws BaseException {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customize/customizedView/newCustomizedView");
        CustomizedView customizedView = customizedViewService
                .getCustomizedViewById(customizedViewId);

        List<CustomizedViewItem> customizedViewItems = customizedViewItemService
                .findByCustomizedViewId(customizedViewId);
        modelAndView.addObject("customizedView", customizedView);
        modelAndView.addObject("customizedViewItems", customizedViewItems);

        return modelAndView;
    }
}
