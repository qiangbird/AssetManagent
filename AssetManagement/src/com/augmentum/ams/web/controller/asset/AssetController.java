package com.augmentum.ams.web.controller.asset;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.DeviceSubtype;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.asset.AssetImportParserService;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.asset.CustomerAssetService;
import com.augmentum.ams.service.asset.DeviceSubtypeService;
import com.augmentum.ams.service.asset.LocationService;
import com.augmentum.ams.service.asset.TransferLogService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.remote.RemoteEntityService;
import com.augmentum.ams.service.remote.RemoteSiteService;
import com.augmentum.ams.service.search.SearchAssetService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.AssetUtil;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.ErrorCodeConvertToJSON;
import com.augmentum.ams.util.ExceptionHelper;
import com.augmentum.ams.util.FileOperateUtil;
import com.augmentum.ams.util.FormatEntityListToEntityVoList;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.AssetListVo;
import com.augmentum.ams.web.vo.asset.AssetVo;
import com.augmentum.ams.web.vo.asset.AssignAssetCondition;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.asset.ImportVo;
import com.augmentum.ams.web.vo.asset.SiteVo;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("assetController")
@RequestMapping(value = "/asset")
public class AssetController extends BaseController {

	@Autowired
	private AssetService assetService;
	@Autowired
	private RemoteSiteService remoteSiteService;
	@Autowired
	private LocationService locationService;
	@Autowired
	private RemoteCustomerService remoteCustomerService;
	@Autowired
	private UserService userService;
	@Autowired
	private DeviceSubtypeService deviceSubtypeService;
	@Autowired
	private RemoteEmployeeService remoteEmployeeService;
	@Autowired
	private RemoteEntityService remoteEntityService;
	@Autowired
	private UserCustomColumnsService userCustomColumnsService;
	@Autowired
	private SearchAssetService searchAssetService;
	@Autowired
	private CustomerAssetService customerAssetService;
	@Autowired
	private AssetImportParserService assetImportParserService;
	@Autowired
	private TransferLogService transferLogService;

	private Page<Asset> pageForAudit;

	private Logger logger = Logger.getLogger(AssetController.class);

	@RequestMapping("/software")
	public String softwareList() {
		return "asset/assetList";
	}

	@RequestMapping("/software/detail")
	public String softwareDetail() {
		return "asset/assetDetail";
	}

	/**
	 * @author Jay.He
	 * @time Nov 15, 2013 10:59:52 AM
	 * @param request
	 * @param assetVo
	 * @return
	 * @throws DataException
	 * @throws ParseException
	 */
	@RequestMapping("/createAsset")
	public ModelAndView createAsset(HttpServletRequest request, AssetVo assetVo)
			throws DataException, ParseException {

		logger.info("createAsset method start!");

		ModelAndView modelAndView = getCommonInfoForAsset(request);
		assetVo.setAssetId(assetService.getGenerateAssetId());
		modelAndView.setViewName("asset/createAsset");

		logger.info("createAsset method end!");
		return modelAndView;

	}

	private ModelAndView getCommonInfoForAsset(HttpServletRequest request)
			throws DataException, ParseException {

		logger.info("getCommonInfoForAsset method start!");

		ModelAndView modelAndView = new ModelAndView();
		List<SiteVo> siteList = remoteSiteService.getSiteFromIAP(request);
		List<String> locationList = new ArrayList<String>();
		for (SiteVo siteVo : siteList) {
			locationList.addAll(AssetUtil.getSiteLocation(locationService
					.getAllLocation(siteVo.getSiteNameAbbr())));
		}
		List<String> list = remoteEntityService.getAllEntityFromIAP(request);
		List<DeviceSubtype> deviceSubtypeList = deviceSubtypeService
				.getAllDeviceSubtype();

		modelAndView.addObject("siteList", locationList);
		modelAndView.addObject("allEntity", list);
		modelAndView.addObject("assetStatus", AssetUtil.getAssetStatus());
		modelAndView.addObject("assetType", AssetUtil.getAssetTypes());
		modelAndView.addObject("machineTypes", AssetUtil.getMachineTypes());
		modelAndView.addObject("deviceSubtypeList", deviceSubtypeList);

		logger.info("getCommonInfoForAsset method end!");
		return modelAndView;
	}

	/**
	 * 
	 * @author Jay.He
	 * @time Nov 15, 2013 10:59:47 AM
	 * @param request
	 * @param assetVo
	 * @param batchCreate
	 * @param batchCount
	 * @return
	 * @throws DataException
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/saveAsset", method = RequestMethod.POST)
	public ModelAndView saveAsset(HttpServletRequest request, AssetVo assetVo,
			String batchCreate, String batchCount) throws DataException,
			UnsupportedEncodingException {

		logger.info("saveAsset method start!");

		logger.info(assetVo.toString());
		ModelAndView modelAndView = new ModelAndView();
		Asset asset = new Asset();

		voToAsset(request, assetVo, asset);
		AssetUtil.setKeeperForAssetVo(assetVo, asset);

		if (null == batchCreate) {
			asset.setAssetId(assetVo.getAssetId());
			try {
				assetService.saveAssetAsType(assetVo, asset, "save");
			} catch (Exception e) {
				logger.error("Save asset error", e);
			}
		} else {
			try {
				// TODO
				int batchNum = Integer.parseInt(batchCount);

				List<String> batchIdList = AssetUtil.getBatchId(
						assetVo.getAssetId(), batchNum);
				for (int i = 0; i < batchNum; i++) {
					asset.setAssetId(batchIdList.get(i));
					assetService.saveAssetAsType(assetVo, asset, "save");
				}
			} catch (NumberFormatException e) {
				// TODO
				logger.error("Number format error!", e);
			}
		}
		modelAndView.setViewName("redirect:/asset/allAssets");

		logger.info("saveAsset method end!");
		return modelAndView;
	}

	/**
	 * 
	 * @description copy assetVo elements to asset
	 * @author Jay.He
	 * @time Nov 11, 2013 3:59:30 PM
	 * @param assetVo
	 * @param asset
	 * @throws UnsupportedEncodingException
	 * @throws DataException
	 */
	private void voToAsset(HttpServletRequest request, AssetVo assetVo,
			Asset asset) throws UnsupportedEncodingException {
		// get Location local with location sent from front page
		// TODO vo --> User -> username,userId
		if (!assetVo.getUser().getUserName().equals("")) {
			User user = userService.getUserByName(assetVo.getUser()
					.getUserName());
			if (null == user) {
				UserVo userVo = null;
				try {
					List<String> userNames = new ArrayList<String>();
					userNames.add(assetVo.getUser().getUserName());
					userVo = remoteEmployeeService.getRemoteUserByName(
							userNames, request).get(0);
				} catch (DataException e) {
					logger.error("Get user error from IAP", e);
				}
				userService.saveUserAsUserVo(userVo);
			}
		} else {
			assetVo.setUser(null);
		}
		setCustomerToAsset(request, assetVo, asset);
		assetService.assetVoToAsset(assetVo, asset);

	}

	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Nov 22, 2013 10:00:15 AM
	 * @param request
	 * @param assetVo
	 * @param asset
	 */
	private void setCustomerToAsset(HttpServletRequest request,
			AssetVo assetVo, Asset asset) {
		String custCode = assetVo.getCustomer().getCustomerCode();
		Customer cust = null;
		try {
			cust = remoteCustomerService.getCustomerByCodefromIAP(request,
					custCode);
		} catch (DataException e1) {
			e1.printStackTrace();
		}
		assetService.setAssetCustomer(asset, custCode, cust);
	}

	@RequestMapping(value = "/allAssets")
	public ModelAndView redirectPage(String type, String status,
			Boolean isFixedAsset, Boolean isWarrantyExpired,
			Boolean isLicenseExpired) {

		ModelAndView modelAndView = new ModelAndView("asset/allAssetsList");
		modelAndView.addObject("type", type);
		modelAndView.addObject("status", status);
		modelAndView.addObject("isFixedAsset", isFixedAsset);
		modelAndView.addObject("isWarrantyExpired", isWarrantyExpired);
		modelAndView.addObject("isLicenseExpired", isLicenseExpired);
		
		return modelAndView;
	}

	/**
	 * 
	 * @description Upload and show the file
	 * @author Jay.He
	 * @time Nov 21, 2013 4:15:42 PM
	 * @param file
	 * @param assetVo
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(
			@RequestParam(value = "file", required = false) MultipartFile file,
			AssetVo assetVo, HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("uploadFile method start!");

		assetService.uploadAndDisplayImage(file, request, response);

		logger.info("uploadFile method end!");
	}

	@RequestMapping(value = "/edit/{id}")
	public ModelAndView editAssetById(@PathVariable String id,
			HttpServletRequest request) throws Exception {

		logger.info("editAssetById method start!");

		ModelAndView modelAndView = getCommonInfoForAsset(request);
		Asset asset = assetService.getAsset(id);
		AssetVo assetVo = new AssetVo();
		assetVo.setId(id);
		String timeOffset = (String) request.getSession().getAttribute(
				"timeOffset");
		AssetUtil.assetToAssetVo(asset, assetVo, timeOffset);
		// get self-defined properties
		getSelfDefinedProperties(asset, modelAndView, "show");
		modelAndView.addObject("asset", assetVo);
		modelAndView.setViewName("asset/editAsset");

		logger.info("editAssetById method end!");
		return modelAndView;
	}

	@RequestMapping(value = "/view/{id}")
	public ModelAndView viewAssetById(@PathVariable String id,
			HttpServletRequest request, String uuid) throws Exception {

		logger.info("viewAssetById method start!");

		ModelAndView modelAndView = getCommonInfoForAsset(request);
		Asset asset = assetService.getAsset(id);
		AssetVo assetVo = new AssetVo();
		assetVo.setId(id);
		String timeOffset = (String) request.getSession().getAttribute(
				"timeOffset");
		AssetUtil.assetToAssetVo(asset, assetVo, timeOffset);
		// get self-defined properties
		getSelfDefinedProperties(asset, modelAndView, "view");
		modelAndView.addObject("asset", assetVo);
		modelAndView.addObject("uuid", uuid);
		modelAndView.setViewName("asset/assetDetail");

		logger.info("viewAssetById method end!");
		return modelAndView;
	}

	private void getSelfDefinedProperties(Asset asset,
			ModelAndView modelAndView, String operation) throws Exception {
		List<PropertyTemplate> propertyTemplatesList = assetService
				.showOrViewSelfDefinedProperties(asset, operation);
		modelAndView.addObject("selfPropertyCount",
				propertyTemplatesList.size());
		modelAndView.addObject("showSelfProperties", propertyTemplatesList);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView updateAsset(AssetVo assetVo, HttpServletRequest request)
			throws UnsupportedEncodingException {

	    logger.info("updateAsset method start!");

        ModelAndView modelAndView = new ModelAndView();
        Asset asset = assetService.getAsset(assetVo.getId().trim());
        voToAsset(request, assetVo, asset);
        AssetUtil.setKeeperForAssetVo(assetVo, asset);
        try {
            assetService.saveAssetAsType(assetVo, asset, "update");
            if(asset.getUser()!=assetVo.getUser()){
                transferLogService.saveTransferLog(asset.getId(),"Assign");
            }
        } catch (Exception e) {
            logger.error("Update asset error!", e);
        }
        modelAndView.setViewName("redirect:/asset/allAssets");

        logger.info("updateAsset method end!");
        return modelAndView;
	}

	@RequestMapping(value = "/copy/{id}")
	public ModelAndView copyAssetById(@PathVariable String id,
			HttpServletRequest request) throws DataException, ParseException {

		logger.info("copyAssetById method start!");

		ModelAndView modelAndView = getCommonInfoForAsset(request);
		Asset asset = assetService.getAsset(id);
		asset.setAssetId(assetService.getGenerateAssetId());
		AssetVo assetVo = new AssetVo();
		String timeOffset = (String) request.getSession().getAttribute(
				"timeOffset");
		AssetUtil.assetToAssetVo(asset, assetVo, timeOffset);
		modelAndView.addObject("assetVo", assetVo);
		modelAndView.setViewName("asset/copyAsset");

		logger.info("copyAssetById method end!");
		return modelAndView;
	}

	@RequestMapping("/delete/{id}")
	public ModelAndView deleteAssetById(@PathVariable String id)
			throws DataException, ParseException {

		logger.info("deleteAssetById method satrt!");

		ModelAndView modelAndView = new ModelAndView();
		assetService.deleteAssetById(id);
		modelAndView.setViewName("redirect:/asset/allAssets");

		logger.info("deleteAssetById method end!");
		return modelAndView;
	}

	@RequestMapping(value = "/itAssignAssets")
	public ModelAndView itAssignAssets(AssignAssetCondition condition,
			HttpServletRequest request) {

		User assigner = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");

		ModelAndView modelAndView = new ModelAndView();
		// TODO test condition is null
		// condition = null;
		try {
			assetService.itAssignAssets(assigner, condition, request);
		} catch (ExceptionHelper e) {
			modelAndView = this.addErrorCode(e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/returnAssetsToCustomer")
	public ModelAndView returnAssetsToCustomer(String assetIds) {

		ModelAndView modelAndView = new ModelAndView();
		User returner = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");
		try {
			assetService.returnAssetsToCustomer(returner, assetIds);
		} catch (ExceptionHelper e) {
			modelAndView = this.addErrorCode(e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/changeAssetsToFixed")
	public ModelAndView changeAssetsToFixed(String assetIds) {

		ModelAndView modelAndView = new ModelAndView();
		try {
			assetService.changeAssetsToFixed(assetIds);
		} catch (ExceptionHelper e) {
			modelAndView = this.addErrorCode(e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/changeAssetsToNonFixed")
	public ModelAndView changeAssetsToNonFixed(String assetIds) {

		ModelAndView modelAndView = new ModelAndView();
		try {
			assetService.changeAssetsToNonFixed(assetIds);
		} catch (ExceptionHelper e) {
			modelAndView = this.addErrorCode(e);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/addAssetsToAuditForSearchResult")
	public ModelAndView addAssetsToAuditForSearchResult() {

		ModelAndView modelAndView = new ModelAndView();
		try {
			assetService.addAssetsToAuditForSearchResult(pageForAudit);
		} catch (ExceptionHelper e) {
			modelAndView = this.addErrorCode(e);
		}
		modelAndView.setViewName("redirect:/auditFile/inventoryList");
		return modelAndView;
	}

	@RequestMapping(value = "/addAssetsToAuditForSelected")
	public ModelAndView addAssetsToAuditForSelected(String assetIds) {

		ModelAndView modelAndView = new ModelAndView();
		try {
			assetService.addAssetsToAuditForSelected(assetIds);
		} catch (ExceptionHelper e) {
			modelAndView = this.addErrorCode(e);
		}
		modelAndView.setViewName("redirect:/auditFile/inventoryList");
		return modelAndView;
	}

	public ModelAndView addErrorCode(ExceptionHelper e) {

		ModelAndView modelAndView = new ModelAndView();
		if (null != e.getErrorCodes()) {
			JSONArray errorCodes = ErrorCodeConvertToJSON.convertToJSONArray(e
					.getErrorCodes());
			modelAndView.addObject("errorCodes", errorCodes);
		} else if (null != e.getErrorCode()) {
			JSONObject errorCode = ErrorCodeConvertToJSON.convertToJSONObject(e
					.getErrorCode());
			modelAndView.addObject("errorCode", errorCode);
		} else {
			modelAndView.addObject("errorCode", "");
		}
		return modelAndView;
	}

	@RequestMapping(value = "/listMyAssets")
	public String redirectToMyassetsPage(HttpSession session,
			HttpServletRequest request, String type) {

		logger.info("redirectToMyassetsPage method satrt!");

		UserVo userVo = (UserVo) session.getAttribute("userVo");
		User user = userService.getUserByUserId(userVo.getEmployeeId());
		request.setAttribute("userUuid", user.getId());
		request.setAttribute("type", type);

		logger.info("redirectToMyassetsPage method end!");
		return "asset/allAssetsList";
	}

	// TODO get asset data list
	@RequestMapping(value = "/allAssetsList", method = RequestMethod.GET)
	public ModelAndView findMyAssetsBySearchCondition(
			SearchCondition searchCondition, String uuid, HttpSession session)
			throws BaseException {

		logger.info("findMyAssetsBySearchCondition method end!");

		if (null == searchCondition) {
			searchCondition = new SearchCondition();
		}
		if (null != uuid && !uuid.equals("")) {
			searchCondition.setUserUuid(uuid);
		}
		Page<Asset> page = searchAssetService
				.findAllAssetsBySearchCondition(searchCondition);
		pageForAudit = page;
		String clientTimeOffset = (String) session.getAttribute("timeOffset");
		List<AssetListVo> list = FormatEntityListToEntityVoList
				.formatAssetListToAssetVoList(page.getResult(),
						clientTimeOffset);
		List<UserCustomColumn> userCustomColumnList = userCustomColumnsService
				.findUserCustomColumns("asset", getUserIdByShiro());
		JSONArray array = SearchCommonUtil.formatAssetVoListTOJSONArray(list,
				userCustomColumnList, uuid);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("fieldsData", array);
		modelAndView.addObject("count", page.getRecordCount());
		modelAndView.addObject("totalPage", page.getTotalPage());
		modelAndView.addObject("searchCondition", searchCondition);

		logger.info("findMyAssetsBySearchCondition method end!");
		return modelAndView;
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	@ResponseBody
	public void exportAssets(HttpServletRequest request,
			HttpServletResponse response, String assetIds) {

		String outPutPath = null;
		try {

			if (null == assetIds || "".equals(assetIds)) {
				outPutPath = assetService.exportAssetsForAll(pageForAudit);
			} else {
				outPutPath = assetService.exportAssetsByIds(assetIds);
			}
			FileOperateUtil.download(request, response, outPutPath);

		} catch (ExcelException e) {
			logger.error(e.getMessage());
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	@RequestMapping(value = "/import")
	public String importAssets() {
		return "asset/importAssets";
	}

	@RequestMapping(value = "/upload")
	@ResponseBody
	public JSONObject uploadAssetsExcelFile(MultipartFile file, String flag,
			HttpServletRequest request, HttpServletResponse response) {

		File targetFile = FileOperateUtil.upload(request, response, file);
		ImportVo importVo = null;
		JSONObject jsonObject = new JSONObject();

		try {
			importVo = assetImportParserService.importAsset(targetFile,
					request, flag);
		} catch (ExcelException e) {
			jsonObject.put("error", e.getErrorCode());
			return jsonObject;
		} catch (DataException e) {
			jsonObject.put("error", e.getErrorCode());
			return jsonObject;
		}
		jsonObject.put("all", importVo.getAllImportRecords());
		jsonObject.put("success", importVo.getSuccessRecords());
		jsonObject.put("failure", importVo.getFailureRecords());
		jsonObject.put("failureFileName", importVo.getFailureFileName());

		return jsonObject;
	}

	@RequestMapping(value = "/download")
	public void downloadFailureAssets(String fileName,
			HttpServletRequest request, HttpServletResponse response) {

		String outPutPath = FileOperateUtil.getBasePath()
				+ Constant.CONFIG_TEMPLATES_PATH + fileName;
		try {
			FileOperateUtil.download(request, response, outPutPath);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

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

		UserVo userVo = (UserVo) SecurityUtils.getSubject().getSession()
				.getAttribute("userVo");
		User user = (User) SecurityUtils.getSubject().getSession()
				.getAttribute("currentUser");

		List<CustomerVo> list = null;
		try {
			list = remoteCustomerService.getCustomerByEmployeeId(
					userVo.getEmployeeId(), request);
		} catch (DataException e) {
			logger.error("get customerVo failed from IAP", e);
		}

		List<Customer> customers = customerAssetService
				.findVisibleCustomerList(userVo, list);
		Map<String, Integer> map = assetService.getAssetCountForManager(user,
				customers);

		JSONArray jsonArray = new JSONArray();

		for (Customer customer : customers) {
			JSONObject obj = new JSONObject();
			obj.put("customerName", customer.getCustomerName());
			obj.put("customerCode", customer.getCustomerCode());

			jsonArray.add(obj);
		}

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("assetCount", JSONObject.fromObject(map));
		jsonObject.put("customer", jsonArray);
		return jsonObject;
	}

	@RequestMapping(value = "/viewIdleAssetPanel", method = RequestMethod.GET)
	public JSONObject viewIdleAssetPanel(HttpServletRequest request) {

		UserVo userVo = (UserVo) SecurityUtils.getSubject().getSession()
				.getAttribute("userVo");

		List<CustomerVo> list = null;
		try {
			list = remoteCustomerService.getCustomerByEmployeeId(
					userVo.getEmployeeId(), request);
		} catch (DataException e) {
			logger.error("get customerVo failed from IAP", e);
		}

		List<Customer> customers = customerAssetService
				.findVisibleCustomerList(userVo, list);

		JSONArray jsonArray = assetService.findIdleAssetForPanel(customers);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("idleAssetList", jsonArray);
		return jsonObject;
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

	@RequestMapping(value = "/viewLicenseExpiredAssetPanel", method = RequestMethod.GET)
	public JSONObject viewLicenseExpiredAssetPanel(HttpSession session) {

		String clientTimeOffset = (String) session.getAttribute("timeOffset");
		JSONArray jsonArray = assetService
				.findLicenseExpiredAssetForPanel(clientTimeOffset);

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("licenseExpiredAssetList", jsonArray);
		return jsonObject;
	}

}
