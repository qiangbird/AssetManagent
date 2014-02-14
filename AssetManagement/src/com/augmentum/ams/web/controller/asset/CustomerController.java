package com.augmentum.ams.web.controller.asset;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.CustomerVo;

@Controller("customerController")
@RequestMapping(value="/customer")
public class CustomerController extends BaseController{

    @Autowired
    private RemoteCustomerService remoteCustomerService;
    
    @RequestMapping("/getCustomerInfo")
    public ModelAndView getCustomerInfo(HttpServletRequest request)
            throws BusinessException {

        ModelAndView modelAndView = new ModelAndView();
        List<CustomerVo> customerList = remoteCustomerService.getAllCustomerFromIAP(request);
        modelAndView.addObject("customerList", customerList);

        return modelAndView;
    }
}
