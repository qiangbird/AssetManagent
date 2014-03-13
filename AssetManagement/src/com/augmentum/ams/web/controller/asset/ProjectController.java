package com.augmentum.ams.web.controller.asset;

import java.util.ArrayList;
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
import com.augmentum.ams.web.vo.common.LabelAndValue;

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
            logger.error("Get data as customerCode from IAP error", e);
        }
        List<LabelAndValue> labelAndValueProjects = new ArrayList<LabelAndValue>();
        List<String> projectManager = new ArrayList<String>();

        for (ProjectVo projectVo : list) {
            LabelAndValue project = new LabelAndValue();
            project.setLabel(projectVo.getProjectName());
            project.setValue(projectVo.getProjectCode());
            String manager = projectVo.getProjectManagerName();

            labelAndValueProjects.add(project);
            projectManager.add(manager);
        }
        modelAndView.addObject("projectList", labelAndValueProjects);
        modelAndView.addObject("projectManager", projectManager);

        logger.info("getProjectByCustomerCode method end!");
        return modelAndView;
    }
}
