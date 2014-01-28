package com.augmentum.ams.web.controller.user;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.common.constants.IAPConstans;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.exception.ParameterException;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("userController")
@RequestMapping(value="/user")
public class UserController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RemoteEmployeeService remoteEmployeeService;
	
	@RequestMapping("/roleList")
	public String roleList() throws ParameterException {
		return "user/roleList";
	}
	
	@RequestMapping(value = "/getEmployeeDataSource")
	public ModelAndView getEmployeeDataSource(HttpServletRequest request) throws DataException {
		JSONArray employeeInfo = remoteEmployeeService.findRemoteEmployees(request);
		ModelAndView modelAndView = new ModelAndView(); 
		modelAndView.addObject("employeeInfo", employeeInfo);
     	return modelAndView;
	}
	
	@RequestMapping (value = "/saveUserRole", method=RequestMethod.POST)
	@ResponseBody
	public String saveUserRole(String usersRoleInfo) throws DataException {

		List<UserVo> userVos = new ArrayList<UserVo>();
		JSONArray usersRoleInfoArray = JSONArray.fromObject(usersRoleInfo);
		JSONObject userRole = null;
		for(int i = 0; i < usersRoleInfoArray.size(); i++){
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
	
	@RequestMapping(value = "/getUserRoleInfo", method=RequestMethod.POST)
	public ModelAndView getUserInfo() throws DataException {
		List<UserVo> userVos = userService.findUserRole();
		ModelAndView modelAndView = new ModelAndView(); 
		JSONArray userRoleInfo = new JSONArray();
		for(UserVo info : userVos) {
			JSONObject userInfoObject = new JSONObject();
			userInfoObject.put("employeeId", info.getEmployeeId());
			userInfoObject.put("employeeName", info.getEmployeeName());
			userInfoObject.put("itRole", info.isITRole());
			userInfoObject.put("systemAdminRole", info.isSystemAdminRole());
			userRoleInfo.add(userInfoObject);
		}
		modelAndView.addObject("userRoleInfo", userRoleInfo);
     	return modelAndView;
	}
	
	@RequestMapping(value = "/getUserInfoTips", method=RequestMethod.POST)
	@ResponseBody
	public JSONObject getUserInfoTips(String employeeName, HttpServletRequest request) throws Exception {
		
		JSONObject object = new JSONObject();
		List<String> userNames = new ArrayList<String>();
		userNames.add(employeeName);
		
		UserVo userVo = remoteEmployeeService.getRemoteUserByName(userNames,request).get(0);
		object.put(IAPConstans.EMPLOYEE_EMPLOYEE_ID, userVo.getEmployeeId());
		object.put(IAPConstans.EMPLOYEE_NAME, userVo.getEmployeeName());
		object.put(IAPConstans.EMPLOYEE_POSITION, userVo.getPositionNameEn());
		object.put(IAPConstans.EMPLOYEE_DEPARTMENT, userVo.getDepartmentNameEn());
		object.put(IAPConstans.EMPLOYEE_MANAGERNAME, userVo.getManagerName());
		return object;
	}
	
	@RequestMapping("getEmployeeAsProject")
	public ModelAndView getEmployeeAsProject(String projectCode, HttpServletRequest request) throws DataException {
        JSONArray employeeInfo = remoteEmployeeService.findRemoteEmployeeByProjectCode(projectCode,request);
        ModelAndView modelAndView = new ModelAndView(); 
        modelAndView.addObject("employeeInfo", employeeInfo);
        return modelAndView;
    }

	@RequestMapping("getEmployeeAsCustomer")
    public ModelAndView getEmployeeAsCustomer(String customerCode, HttpServletRequest request) throws DataException {
        JSONArray employeeInfo = remoteEmployeeService.findRemoteEmployeeByCustomerCode(customerCode,request);
        ModelAndView modelAndView = new ModelAndView(); 
        modelAndView.addObject("employeeInfo", employeeInfo);
        return modelAndView;
    }
	
}
