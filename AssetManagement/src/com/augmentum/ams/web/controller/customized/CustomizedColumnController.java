package com.augmentum.ams.web.controller.customized;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.model.asset.CustomizedColumn;
import com.augmentum.ams.model.enumeration.ColumnTypeEnum;
import com.augmentum.ams.service.asset.CustomizedColumnService;
import com.augmentum.ams.web.controller.base.BaseController;

@Controller("customizedColumnController")
@RequestMapping(value = "/customizedColumn")
public class CustomizedColumnController extends BaseController {

    @Autowired
    private CustomizedColumnService customizedColumnService;

    @RequestMapping(value = "/getDefaultCustomizedColumn", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray getDefaultCustomizedColumn(String categoryType) {

        List<CustomizedColumn> customizedColumns = customizedColumnService
                .findDefaultCustomizedColumns(categoryType);
        JSONArray columns = new JSONArray();

        for (CustomizedColumn column : customizedColumns) {
            JSONObject temp = new JSONObject();
            temp.put("columnType", column.getColumnType());
            temp.put("enName", column.getEnName());
            temp.put("zhName", column.getZhName());
            temp.put("realName", column.getSortName());
            temp.put("realTable", column.getRealTable());
            temp.put("searchColumn", column.getSearchColumn());
            columns.add(temp);
        }
        return columns;
    }

    @RequestMapping(value = "/getSearchCondition", method = RequestMethod.POST)
    public ModelAndView getSearchCondition(String type) {

        ModelAndView modelAndView = new ModelAndView();
        List<String> searchConditions = new ArrayList<String>();

        if (ColumnTypeEnum.STRING_TYPE.getColumnType().equals(type)) {
            searchConditions.add(SystemConstants.IS);
            searchConditions.add(SystemConstants.IS_NOT);
            searchConditions.add(SystemConstants.CONTAINS);
            searchConditions.add(SystemConstants.NOT_CONTAINS);
            searchConditions.add(SystemConstants.START_WITH);
        }

        if (ColumnTypeEnum.DATE_TYPE.getColumnType().equals(type)
                || ColumnTypeEnum.INT_TYPE.getColumnType().equals(type)) {
            searchConditions.add(SystemConstants.IS);
            searchConditions.add(SystemConstants.IS_NOT);
            searchConditions.add(SystemConstants.LESS_THAN);
            searchConditions.add(SystemConstants.GREATER_THAN);
            searchConditions.add(SystemConstants.LESS_OR_EQUAL);
            searchConditions.add(SystemConstants.GREATER_OR_EQUAL);
        }

        if (ColumnTypeEnum.BOOLEAN_TYPE.getColumnType().equals(type)) {
            searchConditions.add(SystemConstants.TRUE);
            searchConditions.add(SystemConstants.FALSE);
        }
        modelAndView.addObject("searchCondition", searchConditions);

        return modelAndView;
    }

    @RequestMapping(value = "/getValues", method = RequestMethod.POST)
    public ModelAndView getValue(String realName, String realTable) {

        ModelAndView modelAndView = new ModelAndView();
        List<String> values = customizedColumnService.getValueOfColumn(realName, realTable);
        modelAndView.addObject("values", values);

        return modelAndView;
    }

}
