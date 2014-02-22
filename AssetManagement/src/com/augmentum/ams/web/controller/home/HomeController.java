package com.augmentum.ams.web.controller.home;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.augmentum.ams.exception.SystemException;
import com.augmentum.ams.service.asset.PurchaseItemService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.user.UserVo;
import com.augmentum.iaphelper.model.Message;
import com.augmentum.iaphelper.util.JsonUtils;
import com.augmentum.iaphelper.util.RequestHelper;

@Controller("homeController")
@RequestMapping(value = "/")
public class HomeController extends BaseController {

    @Autowired
    private UserCustomColumnsService userCustomColumnsService;

    @Autowired
    private PurchaseItemService purchaseItemService;

    private Logger logger = Logger.getLogger(HomeController.class);

    @RequestMapping("/index")
    public String index() {
        return "home/dashboard";
    }

    @RequestMapping(value = "/changeLocale")
    public void changeLocale(@RequestParam("locale") String newLocale, HttpServletRequest request,
            HttpServletResponse response) {

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
    }

    @RequestMapping("/getTimeOffset")
    @ResponseBody
    public String getTimeOffset(String timeOffset, HttpSession session) {

        logger.info("timeOffset: " + timeOffset);

        if (null == session.getAttribute("timeOffset")) {
            session.setAttribute("timeOffset", timeOffset);
        }

        logger.info("client browser timeOffset: " + session.getAttribute("timeOffset"));
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

    @SuppressWarnings("unchecked")
    @RequestMapping("/callbackEntry")
    public void callbackEntry(HttpServletRequest servletRequest) throws SystemException {

        logger.info("get in callbackEntry method");
        String requestBody = null;
        try {
            requestBody = new RequestHelper().getRequestBody(servletRequest);
            logger.info("get request body successful");
        } catch (IOException e) {
            throw new SystemException(e, ErrorCodeUtil.SYSTEM_ERROR, "get message error from PMS");
        }
        // convert JSON String to Message Object
        Message message = JsonUtils.convertJsonToMessage(requestBody);
        if (message != null) {
            if ("PMS_PurchaseRequest".equals(message.getName())) {
                logger.info("get message successful");
                // purchaseItemService.createPurchaseItem(message);
                JSONObject object = JSONObject.fromObject(message.getBody());
                Map<String, Object> dataMap = (Map<String, Object>) object.get("request");
                purchaseItemService.createPurchaseItem(dataMap);
            } else {
                logger.info("this message not come from PSM");
            }
        }
    }

    @RequestMapping("/authorityError")
    public String showAuthorityError() {
        return "/error/authorityError";
    }

    @RequestMapping("/error_404")
    public String showLoginError() {
        return "/error/error_404";
    }

    @RequestMapping(value = "/home")
    public String redirectIndex() {
        return "home/dashboard";
    }
}
