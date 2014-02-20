package com.augmentum.ams.web.controller.home;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.aop.OperationLogAnnotation;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.PurchaseItem;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.asset.CustomerAssetService;
import com.augmentum.ams.service.asset.PurchaseItemService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.util.AssetUtil;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.AssetVo;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.convert.FormatEntityListToEntityVoList;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("dashboardController")
@RequestMapping(value = "/dashboard")
public class DashboardController extends BaseController {
	
	@Autowired
	private AssetService assetService;
	@Autowired
	private RemoteCustomerService remoteCustomerService;
	@Autowired
	private CustomerAssetService customerAssetService;
	@Autowired
    private PurchaseItemService purchaseItemService;
	
	Logger logger = Logger.getLogger(DashboardController.class);

	@RequestMapping(value = "/getAssetCountForPanel", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getAssetCountForPanel(HttpServletRequest request) {
		User user = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");
		Map<String, Integer> map = assetService.getAssetCountForPanel(user);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("assetCount", JSONObject.fromObject(map));
		return jsonObject;
	}

	@RequestMapping(value = "/getAssetCountForManager", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getAssetCountForManager(HttpServletRequest request) {

		JSONObject jsonObject = new JSONObject();
		
		UserVo userVo = (UserVo) SecurityUtils.getSubject().getSession()
				.getAttribute("userVo");
		User user = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");

		List<CustomerVo> list = null;
		try {
			list = remoteCustomerService.getCustomerByEmployeeId(
					userVo.getEmployeeId(), request);
		} catch (BusinessException e) {
			logger.error("get customerVo failed from IAP", e);
		}

		List<Customer> customers = customerAssetService
				.findVisibleCustomerList(userVo, list);
		
		if (null == customers) {
			return jsonObject;
		} else {
			
			Map<String, Integer> map = assetService.getAssetCountForManager(user,
					customers);
		
			JSONArray jsonArray = new JSONArray();
		
			for (Customer customer : customers) {
				JSONObject obj = new JSONObject();
				obj.put("customerName", customer.getCustomerName());
				obj.put("customerCode", customer.getCustomerCode());
		
				jsonArray.add(obj);
			}
		
			jsonObject.put("assetCount", JSONObject.fromObject(map));
			jsonObject.put("customer", jsonArray);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/viewIdleAssetPanel", method = RequestMethod.GET)
	public JSONObject viewIdleAssetPanel(HttpServletRequest request) {

		JSONObject jsonObject = new JSONObject();
		
		UserVo userVo = (UserVo) SecurityUtils.getSubject().getSession()
				.getAttribute("userVo");

		List<CustomerVo> list = null;
		try {
			list = remoteCustomerService.getCustomerByEmployeeId(
					userVo.getEmployeeId(), request);
		} catch (BusinessException e) {
			logger.error("get customerVo failed from IAP", e);
		}

		List<Customer> customers = customerAssetService
				.findVisibleCustomerList(userVo, list);
		
		if (null == customers) {
			return jsonObject;
		} else {
			
			JSONArray jsonArray = assetService.findIdleAssetForPanel(customers);
			
			jsonObject.put("idleAssetList", jsonArray);
			return jsonObject;
		}
	}

	@RequestMapping(value = "/viewWarrantyExpiredAssetPanel", method = RequestMethod.GET)
	public JSONObject viewWarranyExpiredAssetPanel(HttpSession session) {

		String clientTimeOffset = (String) session.getAttribute("timeOffset");
		JSONArray jsonArray = assetService
				.findWarrantyExpiredAssetForPanel(clientTimeOffset);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("warrantyExpiredAssetList", jsonArray);
		return jsonObject;
	}
	
	@RequestMapping(value = "/viewNewlyPurchaseItemsPanel", method = RequestMethod.GET)
    public JSONObject viewNewlyPurchaseItemsPanel() {

        JSONArray jsonArray = purchaseItemService.findAllPurchaseItem();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("newlyPurchaseItemsList", jsonArray);
        
        return jsonObject;
    }
	
	@RequestMapping(value = "/deletePurchaseItem", method = RequestMethod.GET)
	@ResponseBody
//	@OperationLogAnnotation(operateDescribe="Delete PurchaseItem", operateObject="PurchaseItem", operateObjectId="%")
    public String deletePurchaseItem(String id) {

        purchaseItemService.deletePurchaseItemAsId(id);
        
        return null;
    }
}
