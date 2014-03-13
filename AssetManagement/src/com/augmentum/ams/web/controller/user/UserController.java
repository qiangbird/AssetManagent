package com.augmentum.ams.web.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.constants.IAPConstans;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.exception.ParameterException;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.common.LabelAndValue;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("userController")
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RemoteEmployeeService remoteEmployeeService;
    @Autowired
    private UserCustomColumnsService userCustomColumnsService;

    @RequestMapping("/roleList")
    public String roleList() throws ParameterException {
        return "user/roleList";
    }

    @RequestMapping(value = "/getEmployeeDataSource")
    public ModelAndView getEmployeeDataSource(HttpServletRequest request) throws BusinessException {
        JSONArray employeeInfo = remoteEmployeeService.findRemoteEmployees(request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employeeInfo", employeeInfo);

        List<LabelAndValue> labelAndValueList = new ArrayList<LabelAndValue>();
        for (int i = 0; i < employeeInfo.size(); i++) {
            JSONObject employeeJsonObject = JSONObject.fromObject(employeeInfo.get(i));
            LabelAndValue labelAndValue = new LabelAndValue();
            String employeeLabel = employeeJsonObject.getString("label");
            String employeeValue = "";

            if (0 < employeeJsonObject.getString("value").split("#").length) {
                employeeValue = employeeJsonObject.getString("value").split("#")[1];
            }

            labelAndValue.setLabel(employeeLabel);
            labelAndValue.setValue(employeeValue);

            labelAndValueList.add(labelAndValue);
        }
        modelAndView.addObject("employeeLabel", labelAndValueList);

        return modelAndView;
    }

    @RequestMapping(value = "/saveUserRole", method = RequestMethod.POST)
    @ResponseBody
    public String saveUserRole(String usersRoleInfo) throws BusinessException {

        List<UserVo> userVos = new ArrayList<UserVo>();
        JSONArray usersRoleInfoArray = JSONArray.fromObject(usersRoleInfo);
        JSONObject userRole = null;
        for (int i = 0; i < usersRoleInfoArray.size(); i++) {
            UserVo userVo = new UserVo();
            userRole = JSONObject.fromObject(usersRoleInfoArray.get(i));
            userVo.setEmployeeId(userRole.getString("employeeId"));
            userVo.setEmployeeName(userRole.getString("employeeName"));
            userVo.setITRole(userRole.getBoolean("itRole"));
            userVo.setSystemAdminRole(userRole.getBoolean("systemAdminRole"));
            userVo.setDelete(userRole.getBoolean("isDelete"));
            userVos.add(userVo);
        }
        userService.saveUserRole(userVos);
        return null;
    }

    @RequestMapping(value = "/getUserInfoTips", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getUserInfoTips(String employeeName, HttpServletRequest request)
            throws Exception {

        JSONObject object = new JSONObject();
        List<String> userNames = new ArrayList<String>();
        userNames.add(employeeName);

        UserVo userVo = remoteEmployeeService.getRemoteUserByName(userNames, request).get(0);
        object.put(IAPConstans.EMPLOYEE_EMPLOYEE_ID, userVo.getEmployeeId());
        object.put(IAPConstans.EMPLOYEE_NAME, userVo.getEmployeeName());
        object.put(IAPConstans.EMPLOYEE_POSITION, userVo.getPositionNameEn());
        object.put(IAPConstans.EMPLOYEE_DEPARTMENT, userVo.getDepartmentNameEn());
        object.put(IAPConstans.EMPLOYEE_MANAGERNAME, userVo.getManagerName());
        return object;
    }

    @RequestMapping("getEmployeeAsProject")
    public ModelAndView getEmployeeAsProject(String projectCode, HttpServletRequest request)
            throws BusinessException {
        JSONArray employeeInfo = remoteEmployeeService.findRemoteEmployeeByProjectCode(projectCode,
                request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employeeInfo", employeeInfo);
        return modelAndView;
    }

    @RequestMapping("getEmployeeAsCustomer")
    public ModelAndView getEmployeeAsCustomer(String customerCode, HttpServletRequest request)
            throws BusinessException {
        JSONArray employeeInfo = remoteEmployeeService.findRemoteEmployeeByCustomerCode(
                customerCode, request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("employeeInfo", employeeInfo);
        return modelAndView;
    }

    @RequestMapping(value = "findUserRoleList", method = RequestMethod.GET)
    public ModelAndView findUserBySearchCondition(SearchCondition searchCondition) {

        if (null == searchCondition) {
            searchCondition = new SearchCondition();
        }

        Page<User> page = userService.findUserBySearchCondition(searchCondition);

        List<UserCustomColumn> userCustomColumnList = userCustomColumnsService
                .findUserCustomColumns("user role", getUserIdByShiro());
        JSONArray array = SearchCommonUtil.formatUserRoleToJSONArray(page.getResult(),
                userCustomColumnList);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("fieldsData", array);
        modelAndView.addObject("count", page.getRecordCount());
        modelAndView.addObject("totalPage", page.getTotalPage());
        modelAndView.addObject("searchCondition", searchCondition);

        return modelAndView;
    }

}
