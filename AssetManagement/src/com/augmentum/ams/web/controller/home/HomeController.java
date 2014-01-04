package com.augmentum.ams.web.controller.home;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.service.asset.CustomerAssetService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("homeController")
@RequestMapping(value = "/home")
public class HomeController extends BaseController {
    @Autowired
    private RemoteCustomerService remoteCustomerService;
    @Autowired
    private CustomerAssetService customerAssetService;
    @Autowired
    private UserService userService;

    private Logger logger = Logger.getLogger(HomeController.class);

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        UserVo userVo = (UserVo) session.getAttribute("userVo");
        System.out.println(userVo.toString());
        List<CustomerVo> list = null;
        try {
            list = remoteCustomerService.getCustomerByEmployeeId(userVo.getEmployeeId(), request);
        } catch (DataException e) {
            logger.error("Get customer by employeeId[ " + userVo.getEmployeeId()
                    + " ] from IAP error", e);
        }
        // get the list of customer shown in subMenu
        List<Customer> customerVisibleList = customerAssetService.findVisibleCustomerList(userVo,
                list);
        String userRole = userService.getUserRole(userVo);
        session.setAttribute("userRole",userRole);
        session.setAttribute("customerList", customerVisibleList);
        return "home/head";
    }

    @RequestMapping(value = "/getLanguage")
    @ResponseBody
    public JSONObject getLanguage(HttpServletRequest request) {
        HttpSession session = request.getSession();
        JSONObject jsonObject = new JSONObject();
        String language = "";
        if (null == session.getAttribute("i18n")) {
            language = "en";
            session.setAttribute("i18n", "en");
        } else {
            language = (String) session.getAttribute("i18n");
        }
        jsonObject.put("i18n", language);
        return jsonObject;
    }

    @RequestMapping(value = "/changeLanguage")
    @ResponseBody
    public void changeLanguage(HttpServletRequest request, String newlanguage) {
        HttpSession session = request.getSession();
        session.setAttribute("i18n", newlanguage);

    }

    @RequestMapping("/getTimeOffset")
    @ResponseBody
    public String getTimeOffset(String timeOffset, HttpSession session) {

        if (session.getAttribute("timeOffset") == null) {
            session.setAttribute("timeOffset", timeOffset);
        }
        logger.info("client browser timeOffset: " + session.getAttribute("timeOffset"));
        return null;
    }
}
