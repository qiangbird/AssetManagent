package com.augmentum.ams.web.controller.home;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.CustomerAssetService;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.asset.LocationService;
import com.augmentum.ams.service.asset.ProjectService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.remote.RemoteProjectService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.MemcachedUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("homeController")
@RequestMapping(value = "/home")
public class HomeController extends BaseController {
    
    @Autowired
    private RemoteEmployeeService remoteEmployeeService;
    @Autowired
    private RemoteCustomerService remoteCustomerService;
    @Autowired
    private CustomerAssetService customerAssetService;
    @Autowired
    private RemoteProjectService remoteProjectService;
    @Autowired
    private UserService userService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private UserCustomColumnsService userCustomColumnsService;

    private Logger logger = Logger.getLogger(HomeController.class);

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        
        logger.info("index() start... ");
        
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserVo userVo = (UserVo) session.getAttribute("userVo");
        
        logger.info("userVo in index() of homeController" + userVo.toString()); 
        
        // Avoid repeat get data from IAP each time when refresh the page.
        if(null == session.getAttribute("customerList")){
            logger.info(userVo.getEmployeeId() + " has already sign in"); 
            List<CustomerVo> list = null;
            
            try {
                list = remoteCustomerService.getCustomerByEmployeeId(userVo.getEmployeeId(), request);
            } catch (DataException e) {
                logger.error("Get customer by employeeId[ " + userVo.getEmployeeId()
                        + " ] from IAP error", e);
            }
            // Get the list of customer shown in subMenu
            List<Customer> customerVisibleList = customerAssetService.findVisibleCustomerList(userVo,
                    list);
            String userRole = userService.getUserRole(userVo);
            session.setAttribute("userRole",userRole);
            session.setAttribute("customerList", customerVisibleList);
            
            getLocaleLanguage(request);
            initCacheData(request);
            
            logger.info("index() end... ");
        }
        
        return "common/header";
    }

    private void initCacheData(HttpServletRequest request) {
        
        
        Map<String, User> localEmployees = userService.findAllUsersFromLocal();
        // temporary store the customer
        Map<String, Customer> localCustomers = customerService.findAllCustomersFromLocal();
        // temporary store the project
        Map<String, Project> localProjects = projectService.findAllCustomersFromLocal();
        // temporary store the location
        Map<String, Location> localLocations = locationService.findAllLocationsFromIAP();
        
        Map<String, String> remoteEmployees = new HashMap<String, String>();
        Map<String, String> remoteProjects = new HashMap<String, String>();
        Map<String, String> remoteCustomers = new HashMap<String, String>();
        
        try{
            remoteEmployees = remoteEmployeeService.findRemoteEmployeesForCache(request);
            remoteProjects = remoteProjectService.findAllProjectsFromIAP(request);
            
            List<CustomerVo> customers = remoteCustomerService.getAllCustomerFromIAP(request);
            for (CustomerVo customerVo : customers) {
                remoteCustomers.put(customerVo.getCustomerName(), customerVo.getCustomerCode());
            }
        }catch(DataException e){
            logger.error("Get date from IAP failure!", e);
        }
        MemcachedUtil.put("remoteEmployees", remoteEmployees);
        MemcachedUtil.put("localEmployees", localEmployees);
        MemcachedUtil.put("localCustomers", localCustomers);
        MemcachedUtil.put("remoteCustomers", remoteCustomers);
        MemcachedUtil.put("localProjects", localProjects);
        MemcachedUtil.put("remoteProjects", remoteProjects);
        MemcachedUtil.put("localLocations", localLocations);
    }

    private void getLocaleLanguage(HttpServletRequest request) {
        
        logger.info("getLocaleLanguage() start... ");
        
        //Get locale of browser
        String locale = request.getLocale().getLanguage();
        setLocaleToSession(request, locale);
        
        logger.info("getLocaleLanguage() end... ");
    }
    
    @RequestMapping(value = "/changeLocale")
    public void changeLocale(@RequestParam("locale") String newLocale, HttpServletRequest request,
            HttpServletResponse response){
        
        setLocaleToSession(request, newLocale);
    }
    
    private void setLocaleToSession(HttpServletRequest request, String newLocale){
        
        logger.info("setLocaleToSession() start... ");
        logger.info("newLocale: " + newLocale);
        logger.info("SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME: " + 
        request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME));
        
        Locale locale =  null;
        //Check the locale
        if("en".equals(newLocale)){
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

        logger.info("getTimeOffset() start... ");
        logger.info("timeOffset: " + timeOffset);
        
        if (session.getAttribute("timeOffset") == null) {
            session.setAttribute("timeOffset", timeOffset);
        }
        
        logger.info("client browser timeOffset: " + session.getAttribute("timeOffset"));
        logger.info("getTimeOffset() end... ");
        return null;
    }
    
    @RequestMapping("/initUserCustomColumn")
    @ResponseBody
    public String initUserCustomColumn(HttpSession session) {
    	UserVo userVo = (UserVo) session.getAttribute("userVo");
    	logger.info("get userVo from session when user login, userVo is null: " + (null == userVo));
    	String userId = userVo.getEmployeeId();
    	logger.info("init userCustomColumn start when user login first time, userId is: " + userId);
    	userCustomColumnsService.initUserCustomColumn(userId);
    	logger.info("init userCustomColumn end when user login first time, userId is: " + userId);
    	return null;
    }
    
}
