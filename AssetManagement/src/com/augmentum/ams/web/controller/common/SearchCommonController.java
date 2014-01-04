package com.augmentum.ams.web.controller.common;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.base.PageSize;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.service.search.UserPageSizeService;
import com.augmentum.ams.util.Formater;
import com.augmentum.ams.web.controller.base.BaseController;

@Controller("searchCommonController")
@RequestMapping(value="/searchCommon")
public class SearchCommonController extends BaseController {

    @Autowired
    private UserCustomColumnsService userCustomColumnsService;

    @Autowired
    private UserPageSizeService userPageSizeService;

    /**
     * Get user custom columns common method
     * 
     * @author Geoffrey.Zhao
     * @param session
     * @param userId
     * @param category
     * @return
     */
    @RequestMapping(value = "/column/getColumns")
    public ModelAndView getUserCustomColumn(String category) {

        ModelAndView modelAndView = new ModelAndView();
        JSONArray columns;
        String showFields = "";

        List<UserCustomColumn> userCustomColumns = userCustomColumnsService.findUserCustomColumns(
                category, getUserIdByShiro());
        columns = Formater.formateUserColumnListToJSONArray(userCustomColumns);
        showFields = Formater.formateUserColumnNameToString(userCustomColumns);

        modelAndView.addObject("columns", columns);
        modelAndView.addObject("showFields", showFields);
        return modelAndView;
    }

    /**
     * Update user custom columns common method
     * 
     * @author Geoffrey.Zhao
     * @param customization
     * @param showDefault
     */
    @RequestMapping(value = "/column/updateColumns")
    @ResponseBody
    public String updateColumns(String[] customization, Boolean[] showDefault) {
        
        Map<String, Boolean> customColumns = Formater.formateArrayToMap(customization, showDefault);
        userCustomColumnsService.updateCustomizedColumns(customColumns, getUserIdByShiro());
        return null;
    }

    /** 
     * Prepare data for dataList, such as columns, fields, searchCondition etc.
     * @param category
     * @return
     */
    @RequestMapping(value = "/searchCommon")
    public ModelAndView findDataListInfo(String category) {
        
        return getUserCustomColumn(category);
    }

    /**
     * Get page size based on current user and category
     * @param categoryFlag
     * @return
     */
    @RequestMapping(value = "/pageSize/getPageSize")
    public ModelAndView getPageSize(int categoryFlag) {
        
        ModelAndView modelAndView = new ModelAndView();
        
        PageSize pageSize = userPageSizeService.getUserPageSize(getUserIdByShiro(), categoryFlag);
        
        modelAndView.addObject("pageSize", pageSize.getPageSizeValue());
        return modelAndView;
    }

    /**
     * Update user customized page size
     * @param categoryFlag
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/pageSize/updatePageSize")
    public ModelAndView updatePageSize(int categoryFlag, int pageSize) {
        
        ModelAndView modelAndView = new ModelAndView();
        
        PageSize ps = userPageSizeService.updateUserPageSize(getUserIdByShiro(), categoryFlag, pageSize);
        
        modelAndView.addObject("pageSize", ps.getPageSizeValue());
        return modelAndView;
    }

}
