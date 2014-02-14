package com.augmentum.ams.web.controller.asset;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.ProjectVo;

@Controller("projectController")
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {

    Logger logger = Logger.getLogger(ProjectController.class);
    @Autowired
    private RemoteCustomerService remoteCustomerService;

    @RequestMapping("/getProjectByCustomerCode")
    public ModelAndView getProjectByCustomerCode(String customerCode, HttpServletRequest request) {

        logger.info("getProjectByCustomerCode method start!");

        ModelAndView modelAndView = new ModelAndView();
        List<ProjectVo> list = null;
        try {
            list = remoteCustomerService.getProjectByCustomerCode(customerCode, request);
        } catch (BusinessException e) {
            e.printStackTrace();
            logger.error("Get data as customerCode from IAP error", e);
        }
        modelAndView.addObject("projectList", list);

        logger.info("getProjectByCustomerCode method end!");
        return modelAndView;
    }

}
