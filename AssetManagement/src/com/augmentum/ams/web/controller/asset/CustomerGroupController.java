package com.augmentum.ams.web.controller.asset;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.service.asset.CustomerGroupService;
import com.augmentum.ams.util.AssetUtil;
import com.augmentum.ams.util.CommonUtil;
@Controller("customerGroupController")
@RequestMapping(value="/group")
public class CustomerGroupController {
    @Autowired
    private CustomerGroupService customerGroupService;
    
    @RequestMapping("/list")
    public ModelAndView listCustomerGroup(){
        ModelAndView modelAndView = new ModelAndView();
        List<CustomerGroup> groupList = customerGroupService.findAllGroups();
        //get processType
        @SuppressWarnings("rawtypes")
        List processTypeList = AssetUtil.getProcessTypes();
        modelAndView.addObject("groupList", groupList);
        modelAndView.addObject("processTypeList",processTypeList);
        modelAndView.setViewName("asset/groupManagement");
        return modelAndView;
    }
    
    /** 　edit　 */
    @RequestMapping(value = "/update")
    public ModelAndView updateGroup(CustomerGroup group) {
        ModelAndView modelAndView = new ModelAndView();
        if(group.getId().equals("")){
            customerGroupService.saveCustomerGroup(group);
        }else{
        CustomerGroup customerGroup = null;
        customerGroup = customerGroupService.getCustomerGroupById(group.getId());
        customerGroup.setGroupName(group.getGroupName());
        customerGroup.setDescription(group.getDescription());
        customerGroup.setProcessType(group.getProcessType());
        customerGroupService.updateCustomerGroup(customerGroup);
        }
        modelAndView.setViewName("redirect:/group/list");
        return modelAndView;
    }
    
    /** 　edit　 */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/edit")
    public ModelAndView editGroup(String id,HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        CustomerGroup customerGroup = customerGroupService.getCustomerGroupById(id);
        List processTypeList = AssetUtil.getProcessTypes();
        modelAndView.addObject("processTypeList",processTypeList);
        modelAndView.addObject("customerGroup", customerGroup);
        request.setAttribute("customerGroup", customerGroup);
        modelAndView.setViewName("asset/groupEdit");
        return modelAndView;
    }
    /**
     * delete
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteGroup(String id,HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        customerGroupService.deleteCustomerGroupById(id);
        return modelAndView;
    }
    
    @RequestMapping(value="manageGroupCustomer")
    public ModelAndView manageGroupCustomer(String id,HttpServletRequest request){
        CustomerGroup customerGroup = customerGroupService.getCustomerGroupById(id);
        return new ModelAndView("/asset/groupCustomerManagement","customerGroup",customerGroup);
    }
}
