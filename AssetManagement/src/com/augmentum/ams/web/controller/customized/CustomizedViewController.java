package com.augmentum.ams.web.controller.customized;

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
import com.augmentum.ams.exception.BusinessException;
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
    public ModelAndView goToNewCustomizedView(String categoryType, String prePage) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customized/customizedView/createCustomizedView");

        CustomizedView customizedView = new CustomizedView();

        customizedView.setCategoryType(categoryType);
        modelAndView.addObject("customizedView", customizedView);
        modelAndView.addObject("prePage", prePage);

        return modelAndView;
    }

    @RequestMapping(value = "/newCustomizedView", method = RequestMethod.POST)
    public ModelAndView saveCustomizedViewAndItem(CustomizedViewVo customizedViewVo)
            throws ParameterException, BusinessException {

        String categoryType = customizedViewVo.getCategoryType();
        String prePage = customizedViewVo.getPrePage();

        String newURL = "findCustomizedViewByUserForManagement?categoryType=" + categoryType
                + "&prePage=" + prePage;
        RedirectView redirectView = new RedirectView(newURL);
        ModelAndView modelAndView = new ModelAndView(redirectView);

        customizedViewVo.setCreatorId(getUserIdByShiro());
        customizedViewVo.setCreatorName(getUserNameByShiro());

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

        String categoryType = customizedViewVo.getCategoryType();
        String prePage = customizedViewVo.getPrePage();

        String newURL = "findCustomizedViewByUserForManagement?categoryType=" + categoryType
                + "&prePage=" + prePage;
        RedirectView redirectView = new RedirectView(newURL);
        ModelAndView modelAndView = new ModelAndView(redirectView);

        customizedViewItemService.updateCustomizedViewItem(customizedViewVo);
        return modelAndView;
    }

    @RequestMapping(value = "/findCustomizedViewByUserForManagement")
    public ModelAndView findCustomizedViewByUserForManagement(String categoryType, String prePage) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customized/customizedView/customizedViewList");
        String creatorId = getUserIdByShiro();

        List<CustomizedView> customizedViews = customizedViewService.findByUserAndCategoryType(
                creatorId, categoryType);

        CustomizedView customizedView = new CustomizedView();
        customizedView.setCategoryType(categoryType);

        modelAndView.addObject("customizedViews", customizedViews);
        modelAndView.addObject("customizedView", customizedView);
        modelAndView.addObject("prePage", prePage);

        return modelAndView;
    }

    @RequestMapping(value = "/findCustomizedViewByUserForManu")
    @ResponseBody
    public JSONArray findCustomizedViewByUserForManu(String categoryType) {

        String creatorId = getUserIdByShiro();

        List<CustomizedView> customizedViews = customizedViewService.findByUserAndCategoryType(
                creatorId, categoryType);

        return customizedViewService.changeCustomizedViewToJson(customizedViews);
    }

    @RequestMapping(value = "/getCustomizedViewDetail")
    public ModelAndView getCustomizedViewDetail(String customizedViewId, String prePage)
            throws BaseException {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("customized/customizedView/createCustomizedView");
        CustomizedView customizedView = customizedViewService
                .getCustomizedViewById(customizedViewId);

        List<CustomizedViewItem> customizedViewItems = customizedViewItemService
                .findByCustomizedViewId(customizedViewId);
        modelAndView.addObject("customizedView", customizedView);
        modelAndView.addObject("customizedViewItems", customizedViewItems);
        modelAndView.addObject("prePage", prePage);

        return modelAndView;
    }
}
