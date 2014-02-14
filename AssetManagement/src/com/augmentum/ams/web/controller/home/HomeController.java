package com.augmentum.ams.web.controller.home;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.service.asset.CustomerAssetService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("homeController")
@RequestMapping(value = "/")
public class HomeController extends BaseController {

	@Autowired
	private RemoteCustomerService remoteCustomerService;
	@Autowired
	private CustomerAssetService customerAssetService;
	@Autowired
	private UserService userService;
	@Autowired
	private UserCustomColumnsService userCustomColumnsService;

	private Logger logger = Logger.getLogger(HomeController.class);

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {

		ModelAndView modelAndView = new ModelAndView();
		
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		UserVo userVo = (UserVo) session.getAttribute("userVo");

		logger.info("userVo in index() of homeController" + userVo.toString());

		// Avoid repeat get data from IAP each time when refresh the page.
		if (null == session.getAttribute("customerList")) {
			logger.info(userVo.getEmployeeId() + " has already sign in");
			List<CustomerVo> list = null;

			try {
				list = remoteCustomerService.getCustomerByEmployeeId(
						userVo.getEmployeeId(), request);
			} catch (BusinessException e) {
				logger.error(
						"Get customer by employeeId[ " + userVo.getEmployeeId()
								+ " ] from IAP error", e);
			}
			// Get the list of customer shown in subMenu
			List<Customer> customerVisibleList = customerAssetService
					.findVisibleCustomerList(userVo, list);
			String userRole = userService.getUserRole(userVo);
			session.setAttribute("userRole", userRole);
			session.setAttribute("customerList", customerVisibleList);
		}

		modelAndView.setViewName("home/dashboard");
		
		return modelAndView;
	}

	@RequestMapping(value = "/changeLocale")
	public void changeLocale(@RequestParam("locale") String newLocale,
			HttpServletRequest request, HttpServletResponse response) {

	    logger.info("setLocaleToSession() start... ");
        logger.info("newLocale: " + newLocale);
        logger.info("SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME: "
                + request.getSession().getAttribute(
                        SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));

        Locale locale = null;
        // Check the locale
        if ("en".equals(newLocale)) {
            locale = new Locale("en", "US");
            request.getSession().setAttribute("localeLanguage", locale);
        } else {
            locale = new Locale("zh", "CN");
            request.getSession().setAttribute("localeLanguage", locale);
        }
        logger.info("setLocaleToSession() end... ");
	}

	@RequestMapping("/getTimeOffset")
	@ResponseBody
	public String getTimeOffset(String timeOffset, HttpSession session) {

		logger.info("timeOffset: " + timeOffset);

		if (session.getAttribute("timeOffset") == null) {
			session.setAttribute("timeOffset", timeOffset);
		}

		logger.info("client browser timeOffset: "
				+ session.getAttribute("timeOffset"));
		return null;
	}

	@RequestMapping("/initUserCustomColumn")
	@ResponseBody
	public String initUserCustomColumn(HttpSession session) {
	    
		UserVo userVo = (UserVo) session.getAttribute("userVo");
		logger.info("get userVo from session when user login, userVo is null: "
				+ (null == userVo));
		String userId = userVo.getEmployeeId();
		logger.info("init userCustomColumn start when user login first time, userId is: "
				+ userId);
		userCustomColumnsService.initUserCustomColumn(userId);
		logger.info("init userCustomColumn end when user login first time, userId is: "
				+ userId);
		return null;
	}

	@RequestMapping("/authorityError")
    public String showAuthorityError() {
        return "/error/authorityError";
    }
	
	@RequestMapping("/error_404")
    public String showLoginError() {
        return "/error/error_404";
    }
}
