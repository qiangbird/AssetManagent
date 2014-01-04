package com.augmentum.ams.web.controller.asset;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.ProjectVo;

@Controller("projectController")
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {

    @Autowired
    private RemoteCustomerService remoteCustomerService;

    @RequestMapping("/getProjectByCustomer")
    public ModelAndView getProjectByCustomer(String customerName, HttpServletRequest request)
            throws DataException {
        // Customer customer=customerService.getCustomerByName(customerName);
        // String customerCode=customer.getCustomerCode();
        ModelAndView modelAndView = new ModelAndView();
        // List<Project> projectList=
        // remoteCustomerService.getProjectByCustomerCodes(customerCode,request);
        // modelAndView.addObject("projectList",projectList);
        //
        // String projectManageIds="";

        // List<User> pmList=new ArrayList<User>();
        // for(Project project:projectList){
        // projectManageIds+=remoteProjectService.getManagerIdByProjectCode(project.getProjectCode(),request)+" ";
        // }
        // modelAndView.addObject("projectManageIds",projectManageIds);
        return modelAndView;
    }

    @RequestMapping("/getProjectByCustomerCode")
    public ModelAndView getProjectByCustomerCode(String customerCode, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        List<ProjectVo> list = null;
        try {
            list = remoteCustomerService.getProjectByCustomerCode(customerCode, request);
        } catch (DataException e) {
            e.printStackTrace();
        }
        modelAndView.addObject("projectList", list);
        return modelAndView;
    }

}
