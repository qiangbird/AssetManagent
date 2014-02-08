package com.augmentum.ams.web.controller.base;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("baseController")
@RequestMapping(value = "/base")
public class BaseController {

	private Logger logger = Logger.getLogger(BaseController.class);

	@Autowired
	private RemoteEmployeeService remoteEmployeeService;
	@Autowired
	private RemoteCustomerService remoteCustomerService;

	public String getUserIdByShiro() {

		String userId = getUserVoByShiro().getEmployeeId();

		return userId;
	}

	public String getUserNameByShiro() {

		return getUserVoByShiro().getEmployeeName();
	}

	public UserVo getUserVoByShiro() {

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();

		return (UserVo) session.getAttribute("userVo");
	}

	@RequestMapping(value = "/getEmployeeDataSource", method = RequestMethod.POST)
	public ModelAndView getEmployeeDataSource(HttpServletRequest request)
			throws DataException {

		JSONArray employeeInfo = remoteEmployeeService.findRemoteEmployees(request);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("employeeInfo", employeeInfo);

		return modelAndView;
	}

	@RequestMapping("/getCustomerInfo")
	public ModelAndView getCustomerInfo(HttpServletRequest request)
			throws DataException {

		ModelAndView modelAndView = new ModelAndView();
		List<CustomerVo> customerList = remoteCustomerService.getAllCustomerFromIAP(request);
		modelAndView.addObject("customerList", customerList);

		return modelAndView;
	}
}
