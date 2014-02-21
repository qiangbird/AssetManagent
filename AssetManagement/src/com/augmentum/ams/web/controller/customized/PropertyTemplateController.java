package com.augmentum.ams.web.controller.customized;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.exception.ParameterException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.enumeration.AssetTypeEnum;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.customized.PropertyTemplateService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.CustomerUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.customized.PropertyTemplateVo;

@Controller("propertyTemplateController")
@RequestMapping(value = "/self")
public class PropertyTemplateController extends BaseController {

    @Autowired
    private PropertyTemplateService propertyTemplateService;

    @Autowired
    private RemoteCustomerService remoteCustomerService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @RequestMapping("/selfProperty")
    public String roleList() {
        return "customized/customizedProperty/customizedPropertyList";
    }

    @RequestMapping("/getCustomerAndAssetType")
    public ModelAndView getCustomerAndAssetType(HttpServletRequest httpServletRequest)
            throws BusinessException {

        ModelAndView modelAndView = new ModelAndView();
        String employeeId = getUserIdByShiro();
        List<CustomerVo> customerInfo = remoteCustomerService.getCustomerByEmployeeId(employeeId,
                httpServletRequest);
        JSONArray customers = CustomerUtil.changeCustomerToJson(customerInfo);

        modelAndView.addObject("assetTypes", AssetTypeEnum.values());
        modelAndView.addObject("customers", customers);
        return modelAndView;
    }

    @RequestMapping(value = "/saveSelfProperty", method = RequestMethod.POST)
    @ResponseBody
    public String saveSelfProperty(String selfProperties, String customerCode, String assetType)
            throws ParameterException, BusinessException {

        PropertyTemplateVo propertyTemplateVo = new PropertyTemplateVo(selfProperties,
                customerCode, assetType);
        User user = userService.getUserByUserId(getUserIdByShiro());
        Customer dataBaseCustomer = customerService.getCustomer(propertyTemplateVo
                .getCustomerCode());
        propertyTemplateService.savePropertyTemplate(propertyTemplateVo, dataBaseCustomer, user);

        return null;
    }

    @RequestMapping(value = "getPropertyTemplates")
    public ModelAndView getPropertyTemplates(String customerCode, String assetType)
            throws ParseException {

        ModelAndView modelAndView = new ModelAndView();
        PropertyTemplateVo propertyTemplateVo = new PropertyTemplateVo(null, customerCode,
                assetType);
        Customer customer = customerService.getCustomer(propertyTemplateVo.getCustomerCode());
        JSONArray propertyTemplates = propertyTemplateService
                .findPropertyTemplateByCustomerAndAssetType(customer.getCustomerName(),
                        propertyTemplateVo.getAssetType());

        modelAndView.addObject("propertyTemplates", propertyTemplates);

        return modelAndView;
    }

}
