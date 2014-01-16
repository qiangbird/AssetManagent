package com.augmentum.ams.web.controller.asset;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.dao.asset.CustomerDao;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.CustomerGroup;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.asset.CustomerGroupService;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.util.AssetUtil;
import com.augmentum.ams.util.CommonUtil;
import com.augmentum.ams.util.FormatEntityListToEntityVoList;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.vo.asset.AssetListVo;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Controller("customerGroupController")
@RequestMapping(value = "/group")
public class CustomerGroupController {
    Logger logger = Logger.getLogger(CustomerGroupController.class);
    @Autowired
    private CustomerGroupService customerGroupService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RemoteCustomerService remoteCustomerService;
    @Autowired
    private CustomerDao customerDao;

    // @RequestMapping("/list")
    // public ModelAndView listCustomerGroup(){
    // ModelAndView modelAndView = new ModelAndView();
    // List<CustomerGroup> groupList = customerGroupService.findAllGroups();
    // //get processType
    // @SuppressWarnings("rawtypes")
    // List processTypeList = AssetUtil.getProcessTypes();
    // modelAndView.addObject("groupList", groupList);
    // modelAndView.addObject("processTypeList",processTypeList);
    // modelAndView.setViewName("asset/groupList");
    // return modelAndView;
    // }

    @RequestMapping("/list")
    public ModelAndView listCustomerGroup() {
        ModelAndView modelAndView = new ModelAndView();
        List processTypeList = AssetUtil.getProcessTypes();

        modelAndView.addObject("processTypeList", processTypeList);
        modelAndView.setViewName("asset/groupList");
        return modelAndView;
    }

    @RequestMapping("/search")
    public ModelAndView listCustomerGroup(SearchCondition searchCondition, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
        Page<CustomerGroup> groupPage = customerGroupService
                .findCustomerGroupBySearchCondition(searchCondition);
        String clientTimeOffset = (String) session.getAttribute("timeOffset");
        List<CustomerGroup> groupList = groupPage.getResult();
        System.out.println(groupList.size());
        JSONArray array = new JSONArray();
        array = SearchCommonUtil.formatGroupListTOJSONArray(groupList);
        List processTypeList = AssetUtil.getProcessTypes();

        modelAndView.addObject("processTypeList", processTypeList);
        modelAndView.addObject("fieldsData", array);
        modelAndView.addObject("count", groupPage.getRecordCount());
        modelAndView.addObject("totalPage", groupPage.getTotalPage());
        return modelAndView;
    }

    /** 　edit　 */
    @RequestMapping(value = "/update")
    public ModelAndView updateGroup(CustomerGroup group, String customerCodes,
            HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        String codes[] = customerCodes.split(",");
        if (group.getId().equals("")) {
            List<Customer> customerList = new ArrayList<Customer>();
            try {
                // customerList =
                // customerService.getCustomerListByCodes(codes,request);
                for (String code : codes) {
                    Customer customer = customerService.getCustomerByCode(code);
                    if (null != customer) {
                        customerList.add(customer);
                    } else {
                        Customer remoteCustomer = remoteCustomerService.getCustomerByCodefromIAP(
                                request, code);
                        Customer customer1 = customerDao.save(remoteCustomer);
                        customerList.add(customer1);
                    }
                }
            } catch (DataException e) {
                logger.error(e);
            }
            group.setCustomers(customerList);
            customerGroupService.saveCustomerGroup(group);
        } else {
            CustomerGroup customerGroup = customerGroupService.getCustomerGroupById(group.getId());
            customerGroup.setGroupName(group.getGroupName());
            customerGroup.setDescription(group.getDescription());
            customerGroup.setProcessType(group.getProcessType());
            
            List<Customer> customerList = new ArrayList<Customer>();
            try {
                // customerList =
                // customerService.getCustomerListByCodes(codes,request);
                for (String code : codes) {
                    Customer customer = customerService.getCustomerByCode(code);
                    if (null != customer) {
                        customerList.add(customer);
                    } else {
                        Customer remoteCustomer = remoteCustomerService.getCustomerByCodefromIAP(
                                request, code);
                        Customer customer1 = customerDao.save(remoteCustomer);
                        customerList.add(customer1);
                    }
                }
            } catch (DataException e) {
                logger.error(e);
            }
            customerGroup.setCustomers(customerList);
            
            customerGroupService.updateCustomerGroup(customerGroup);
        }
        modelAndView.setViewName("redirect:/group/list");
        return modelAndView;
    }

    /** 　edit　 */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/edit")
    public ModelAndView editGroup(String id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();

        logger.info("editGroup start");
        CustomerGroup customerGroup = customerGroupService.getCustomerGroupById(id);
        List<Customer> customerList = customerService.getCustomerByGroup(id);
        List processTypeList = AssetUtil.getProcessTypes();

        modelAndView.addObject("processTypeList", processTypeList);
        modelAndView.addObject("customerGroup", customerGroup);
        modelAndView.addObject("customerList", customerList);
        modelAndView.setViewName("asset/groupEdit");
        logger.info("editGroup end");
        return modelAndView;
    }

    /**
     * delete
     */
    @RequestMapping(value = "/delete")
    public ModelAndView deleteGroup(String id, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        customerGroupService.deleteCustomerGroupById(id);
        return modelAndView;
    }

    @RequestMapping(value = "manageGroupCustomer")
    public ModelAndView manageGroupCustomer(String id, HttpServletRequest request) {
        CustomerGroup customerGroup = customerGroupService.getCustomerGroupById(id);
        return new ModelAndView("/asset/groupDetail", "customerGroup", customerGroup);
    }

}
