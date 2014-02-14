package com.augmentum.ams.web.controller.asset;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.dao.asset.CustomerDao;
import com.augmentum.ams.exception.BusinessException;
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

	@RequestMapping("/list")
	public ModelAndView listCustomerGroup() {

		logger.info("listCustomerGroup method in CustomerGroupController start!");

		ModelAndView modelAndView = new ModelAndView();
		List processTypeList = AssetUtil.getProcessTypes();

		modelAndView.addObject("processTypeList", processTypeList);
		modelAndView.setViewName("asset/groupList");

		logger.info("listCustomerGroup method in CustomerGroupController end!");
		return modelAndView;
	}

	@RequestMapping("/search")
	public ModelAndView searchCustomerGroup(SearchCondition searchCondition,
			HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();

		logger.info("searchCustomerGroup method in CustomerGroupController start!");

		Page<CustomerGroup> groupPage = customerGroupService
				.findCustomerGroupBySearchCondition(searchCondition);
		List<CustomerGroup> groupList = groupPage.getResult();
		JSONArray array = new JSONArray();
		array = SearchCommonUtil.formatGroupListTOJSONArray(groupList);
		List processTypeList = AssetUtil.getProcessTypes();

		modelAndView.addObject("fieldsData", array);
		modelAndView.addObject("count", groupPage.getRecordCount());
		modelAndView.addObject("totalPage", groupPage.getTotalPage());
		modelAndView.addObject("processTypeList", processTypeList);

		logger.info("searchCustomerGroup method in CustomerGroupController end!");
		return modelAndView;
	}

	/** 　edit　 */
	@RequestMapping(value = "/update")
	public ModelAndView updateGroup(CustomerGroup group, String customerCodes,
			HttpServletRequest request) {

		logger.info("updateGroup method in CustomerGroupController start!");

		ModelAndView modelAndView = new ModelAndView();
		String codes[] = customerCodes.split(",");
		if (group.getId().equals("")) {
			if (!StringUtils.isBlank(customerCodes)) {
				List<Customer> customerList = new ArrayList<Customer>();
				try {
					for (String code : codes) {
						Customer customer = customerService
								.getCustomerByCode(code);
						if (null != customer) {
							customerList.add(customer);
						} else {
							Customer remoteCustomer = remoteCustomerService
									.getCustomerByCodefromIAP(request, code);
							Customer customer1 = customerDao
									.save(remoteCustomer);
							customerList.add(customer1);
						}
					}
				} catch (BusinessException e) {
					logger.error(e);
				}
				group.setCustomers(customerList);
			}
			customerGroupService.saveCustomerGroup(group);
		} else {
			CustomerGroup customerGroup = customerGroupService
					.getCustomerGroupById(group.getId());
			customerGroup.setGroupName(group.getGroupName());
			customerGroup.setDescription(group.getDescription());
			customerGroup.setProcessType(group.getProcessType());
			if (!StringUtils.isBlank(customerCodes)) {
				List<Customer> customerList = new ArrayList<Customer>();
				try {
					for (String code : codes) {
						Customer customer = customerService
								.getCustomerByCode(code);
						if (null != customer) {
							customerList.add(customer);
						} else {
							Customer remoteCustomer = remoteCustomerService
									.getCustomerByCodefromIAP(request, code);
							Customer customer1 = customerDao
									.save(remoteCustomer);
							customerList.add(customer1);
						}
					}
				} catch (BusinessException e) {
					logger.error(e);
				}
				customerGroup.setCustomers(customerList);
			}
			customerGroupService.updateCustomerGroup(customerGroup);
		}
		modelAndView.setViewName("redirect:/group/list");

		logger.info("updateGroup method in CustomerGroupController end!");
		return modelAndView;
	}

	/** 　edit　 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/edit")
	public ModelAndView editGroup(String id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		logger.info("editGroup in CustomerGroupController  start");
		CustomerGroup customerGroup = customerGroupService
				.getCustomerGroupById(id);
		List<Customer> customerList = customerService.getCustomerByGroup(id);
		List processTypeList = AssetUtil.getProcessTypes();

		modelAndView.addObject("processTypeList", processTypeList);
		modelAndView.addObject("customerGroup", customerGroup);
		modelAndView.addObject("customerList", customerList);
		modelAndView.setViewName("asset/groupEdit");

		logger.info("editGroup in CustomerGroupController  end");
		return modelAndView;
	}

	/**
	 * delete
	 */
	@RequestMapping(value = "/delete")
	public ModelAndView deleteGroup(String id, HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();

		logger.info("deleteGroup in CustomerGroupController  start!");

		customerGroupService.deleteCustomerGroupById(id);

		logger.info("deleteGroup in CustomerGroupController  end!");
		return modelAndView;
	}

	@RequestMapping(value = "manageGroupCustomer")
	public ModelAndView manageGroupCustomer(String id,
			HttpServletRequest request) {

		logger.info("manageGroupCustomer in CustomerGroupController  start!");

		CustomerGroup customerGroup = customerGroupService
				.getCustomerGroupById(id);

		logger.info("manageGroupCustomer in CustomerGroupController  end!");
		return new ModelAndView("/asset/groupDetail", "customerGroup",
				customerGroup);
	}

}
