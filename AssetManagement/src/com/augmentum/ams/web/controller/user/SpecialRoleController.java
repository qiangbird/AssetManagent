package com.augmentum.ams.web.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.user.SpecialRoleService;
import com.augmentum.ams.util.CustomerUtil;
import com.augmentum.ams.util.LogHelper;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.user.SpecialRoleVo;

@Controller("specialRoleController")
@RequestMapping(value = "/specialRole")
public class SpecialRoleController extends BaseController {

    private static Logger logger = Logger.getLogger(SpecialRoleController.class);

    @Autowired
    private SpecialRoleService specialRoleService;

    @Autowired
    private RemoteCustomerService remoteCustomerService;

    @Autowired
    private RemoteEmployeeService remoteEmployeeService;

    @RequestMapping(value = "/saveOrUpdateSpecialRole", method = RequestMethod.POST)
    @ResponseBody
    public String saveOrUpdateSpecialRole(SpecialRoleVo specialRoleVo) {

        logger.info(LogHelper.getLogInfo("Save or update specialRole at controller start",
                specialRoleVo.getSpecialRoles()));

        specialRoleService.saveOrUpdateSpecialRole(specialRoleVo);

        logger.info(LogHelper.getLogInfo("Save or update specialRole at controller end"));

        return null;
    }

    @RequestMapping(value = "/findSpecialRoles", method = RequestMethod.GET)
    public ModelAndView findSpecialRoles(HttpServletRequest httpServletRequest)
            throws BusinessException {

        logger.info(LogHelper.getLogInfo("Find specialRoles at controller start"));

        String employeeId = getUserIdByShiro();
        ModelAndView modelAndView = new ModelAndView("user/specialRoleList");
        List<CustomerVo> customerInfo = remoteCustomerService.getCustomerByEmployeeId(employeeId,
                httpServletRequest);
        JSONArray customers = CustomerUtil.changeCustomerToJson(customerInfo);
        List<String> customerCodes = new ArrayList<String>();

        for (CustomerVo customer : customerInfo) {
            customerCodes.add(customer.getCustomerCode());
        }
        List<SpecialRoleVo> specialRoleVos = specialRoleService
                .findSpecialRolesByCustomerCodes(customerCodes);
        JSONArray specialRoles = specialRoleService.changeVOToJSON(specialRoleVos,
                httpServletRequest);

        modelAndView.addObject("specialRoles", specialRoles);
        modelAndView.addObject("customers", customers);

        logger.info(LogHelper.getLogInfo("Find specialRoles at controller end"));

        return modelAndView;
    }

    @RequestMapping(value = "/findEmployeesByCustomerCode", method = RequestMethod.POST)
    @ResponseBody
    public JSONArray findEmployeesByCustomerCode(SpecialRoleVo specialRolevo,
            HttpServletRequest httpServletRequest) {

        JSONArray employees = null;

        try {
            employees = remoteEmployeeService.findRemoteEmployeeByCustomerCode(
                    specialRolevo.getCustomerCode(), httpServletRequest);
        } catch (BusinessException e) {
            logger.info(e);
        }

        return employees;
    }
}
