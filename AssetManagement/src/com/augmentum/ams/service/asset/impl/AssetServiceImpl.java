package com.augmentum.ams.service.asset.impl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.augmentum.ams.dao.asset.AssetDao;
import com.augmentum.ams.dao.audit.AuditDao;
import com.augmentum.ams.dao.audit.AuditFileDao;
import com.augmentum.ams.dao.todo.ToDoDao;
import com.augmentum.ams.excel.AssetTemplateParser;
import com.augmentum.ams.excel.ExcelBuilder;
import com.augmentum.ams.excel.ExcelUtil;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Device;
import com.augmentum.ams.model.asset.DeviceSubtype;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.asset.Machine;
import com.augmentum.ams.model.asset.Monitor;
import com.augmentum.ams.model.asset.OtherAssets;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.asset.Software;
import com.augmentum.ams.model.audit.Audit;
import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.model.customized.CustomizedProperty;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.model.enumeration.AssetTypeEnum;
import com.augmentum.ams.model.enumeration.RoleEnum;
import com.augmentum.ams.model.enumeration.StatusEnum;
import com.augmentum.ams.model.enumeration.TransientStatusEnum;
import com.augmentum.ams.model.todo.ToDo;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.asset.DeviceService;
import com.augmentum.ams.service.asset.DeviceSubtypeService;
import com.augmentum.ams.service.asset.LocationService;
import com.augmentum.ams.service.asset.MachineService;
import com.augmentum.ams.service.asset.MonitorService;
import com.augmentum.ams.service.asset.OtherAssetsService;
import com.augmentum.ams.service.asset.ProjectService;
import com.augmentum.ams.service.asset.SoftwareService;
import com.augmentum.ams.service.customized.CustomizedPropertyService;
import com.augmentum.ams.service.customized.PropertyTemplateService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.remote.RemoteProjectService;
import com.augmentum.ams.service.search.impl.SearchAssetServiceImpl;
import com.augmentum.ams.service.user.SpecialRoleService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.AssetStatusOperateUtil;
import com.augmentum.ams.util.AssetUtil;
import com.augmentum.ams.util.CommonUtil;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.ExceptionHelper;
import com.augmentum.ams.util.FileOperateUtil;
import com.augmentum.ams.util.FormatUtil;
import com.augmentum.ams.util.RoleLevelUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.AssetVo;
import com.augmentum.ams.web.vo.asset.AssignAssetCondition;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.asset.ExportVo;
import com.augmentum.ams.web.vo.asset.ImportVo;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.user.UserVo;

@Service("assetService")
public class AssetServiceImpl extends SearchAssetServiceImpl implements
		AssetService {

    @Autowired
    private AssetDao assetDao;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private MachineService machineService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private MonitorService monitorService;
    @Autowired
    private OtherAssetsService otherAssetsService;
    @Autowired
    private DeviceSubtypeService deviceSubtypeService;
    @Autowired
    private PropertyTemplateService propertyTemplateService;
    @Autowired
    private CustomizedPropertyService customizedPropertyService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RemoteEmployeeService remoteEmployeeService;
    @Autowired
    private SpecialRoleService specialRoleService;
    @Autowired
    private AuditFileDao auditFileDao;
    @Autowired
    private AuditDao auditDao;
    @Autowired
    private ToDoDao todoDao;
    @Autowired
    private RemoteCustomerService remoteCustomerService;
    @Autowired
    private RemoteProjectService remoteProjectService;

    private static Logger logger = Logger.getLogger(AssetServiceImpl.class);

	@Override
	public void saveAsset(Asset asset) {
		assetDao.save(asset);
	}

	@Override
	public void updateAsset(Asset asset) {
		assetDao.update(asset);
	}

	@Override
	public Asset getAsset(String id) {
		return assetDao.getAssetById(id);
	}

	@Override
	public void deleteAssetById(String id) {
		assetDao.delete(assetDao.getAssetById(id));
	}

	@Override
	public List<Asset> findAllAssets() {
		return assetDao.findAllAssets();
	}

	@Override
	public int getAllAssetCount() {
		return assetDao.getAllAssetCount();
	}

	@Override
	public void saveAssetAsType(AssetVo assetVo, Asset asset, String operation) {

		String assetType = assetVo.getType().trim();

		if (operation.equals(Constant.SAVE)) {
			// TODO need save operation log
			if (AssetTypeEnum.SOFTWARE.toString().equals(assetType)) {
				softwareService.saveSoftware(assetVo.getSoftware());
				asset.setSoftware(assetVo.getSoftware());
				saveAsset(asset);
				
			} else {

				saveAsset(asset);

				if (AssetTypeEnum.MACHINE.toString().equals(assetType)) {
					assetVo.getMachine().setAsset(asset);
					machineService.saveMachine(assetVo.getMachine());
					
				} else if (AssetTypeEnum.MONITOR.toString().equals(assetType)) {
					assetVo.getMonitor().setAsset(asset);
					monitorService.saveMonitor(assetVo.getMonitor());
					
				} else if (AssetTypeEnum.DEVICE.toString().equals(assetType)) {
					
					assetVo.getDevice().setAsset(asset);
					List<DeviceSubtype> deviceSubtypesList = deviceSubtypeService
					.getDeviceSubtypeByName(assetVo.getDevice().getDeviceSubtype().getSubtypeName());
					
					if (deviceSubtypesList.size() > 0) {
						assetVo.getDevice().setDeviceSubtype(deviceSubtypesList.get(0));
					} else {
						DeviceSubtype newDeviceSubtype = new DeviceSubtype();
						newDeviceSubtype.setSubtypeName(assetVo.getDevice()
								.getDeviceSubtype().getSubtypeName());
						deviceSubtypeService.saveDeviceSubtype(newDeviceSubtype);
						assetVo.getDevice().setDeviceSubtype(newDeviceSubtype);
					}
					deviceService.saveDevice(assetVo.getDevice());
					
				} else if (AssetTypeEnum.OTHERASSETS.toString().equals(assetType)) {
					assetVo.getOtherAssets().setAsset(asset);
					otherAssetsService.saveOtherAssets(assetVo.getOtherAssets());
				}
			}

		} else {
			// TODO need update operation log
			if (AssetTypeEnum.SOFTWARE.toString().equals(assetType)) {
				Software soft = softwareService.findById(assetVo.getSoftware().getId());
				Software newSoft = assetVo.getSoftware();
				soft.setVersion(newSoft.getVersion());
				soft.setMaxUseNum(newSoft.getMaxUseNum());
				soft.setAdditionalInfo(newSoft.getAdditionalInfo());
				soft.setLicenseKey(newSoft.getLicenseKey());
				softwareService.updateSoftware(soft);
				asset.setSoftware(soft);
				updateAsset(asset);
				
			} else {
				
				updateAsset(asset);
				
				if (AssetTypeEnum.MACHINE.toString().equals(assetType)) {
					Machine machine = machineService.getMachineById(assetVo
							.getMachine().getId());
					machine.setSubtype(assetVo.getMachine().getSubtype());
					machine.setSpecification(assetVo.getMachine()
							.getSpecification());
					machine.setConfiguration(assetVo.getMachine()
							.getConfiguration());
					machine.setAddress(assetVo.getMachine().getAddress());
					machineService.updateMachine(machine);
					
				} else if (AssetTypeEnum.MONITOR.toString().equals(assetType)) {
					Monitor monitor = monitorService.getMonitorById(assetVo
							.getMonitor().getId());
					monitor.setSize(assetVo.getMonitor().getSize());
					monitor.setDetail(assetVo.getMonitor().getDetail());
					monitorService.updateMonitor(monitor);
					
				} else if (AssetTypeEnum.DEVICE.toString().equals(assetType)) {
					Device device = deviceService.findDeviceById(assetVo
							.getDevice().getId());
					device.setConfiguration(assetVo.getDevice()
							.getConfiguration());

					List<DeviceSubtype> deviceSubtypesList = deviceSubtypeService
							.getDeviceSubtypeByName(assetVo.getDevice()
									.getDeviceSubtype().getSubtypeName());

					if (deviceSubtypesList.size() > 0) {
						DeviceSubtype deviceSubtype = deviceSubtypesList.get(0);
						device.setDeviceSubtype(deviceSubtype);
					} else {
						DeviceSubtype newDeviceSubtype = new DeviceSubtype();
						newDeviceSubtype.setSubtypeName(assetVo.getDevice()
								.getDeviceSubtype().getSubtypeName());
						deviceSubtypeService.saveDeviceSubtype(newDeviceSubtype);
						device.setDeviceSubtype(deviceSubtypeService.getDeviceSubtypeByName(
									newDeviceSubtype.getSubtypeName()).get(0));
					}
					deviceService.updateDevice(device);
					
				} else if (AssetTypeEnum.OTHERASSETS.toString().equals(
						assetType)) {
					OtherAssets otherAssets = otherAssetsService
							.getOtherAssetsById(assetVo.getOtherAssets().getId());
					otherAssets.setDetail(assetVo.getOtherAssets().getDetail());
					otherAssetsService.updateOtherAssets(otherAssets);
				}
			}

		}

	}

	@Override
	public String getGenerateAssetId() {
		int assetCount = getAllAssetCount();
		return AssetUtil.generateAssetId(assetCount);
	}

	@Override
	public List<PropertyTemplate> showOrViewSelfDefinedProperties(Asset asset,
			String operation) throws ParseException {
		@SuppressWarnings("unchecked")
		List<PropertyTemplate> defaultPropertyTemplatesList = JSONArray.toList(
				propertyTemplateService
						.findPropertyTemplateByCustomerAndAssetType(asset
								.getCustomer().getCustomerName(), asset
								.getType()), PropertyTemplate.class);
		List<PropertyTemplate> propertyTemplatesList = new ArrayList<PropertyTemplate>();

		for (PropertyTemplate pt : defaultPropertyTemplatesList) {
			CustomizedProperty customizedProperty = customizedPropertyService
					.getCustomizedPropertyByTemplateId(pt.getId());
			if (null == customizedProperty) {
				CustomizedProperty newCustomizedProperty = new CustomizedProperty();
				newCustomizedProperty.setValue(pt.getValue());
				newCustomizedProperty.setAsset(asset);
				newCustomizedProperty.setPropertyTemplate(pt);
				customizedPropertyService
						.saveCustomizedProperty(newCustomizedProperty);
				propertyTemplatesList.add(pt);
			} else {
				if (operation.equals("show")) {
					if (!(pt.getPropertyType().equals("selectType"))) {
						pt.setValue(customizedProperty.getValue());
					}
				} else {
					pt.setValue(customizedProperty.getValue());
				}
				propertyTemplatesList.add(pt);
			}
		}
		return propertyTemplatesList;
	}

	@Override
	public void setAssetCustomer(Asset asset, String custCode, Customer cust) {
		Customer customer = customerService.getCustomerByCode(custCode);
		if (null == customer) {
			customer = new Customer();
			customer.setCustomerName(cust.getCustomerName());
			customer.setCustomerCode(custCode);
			customerService.saveCustomer(customer);
			Customer newCustomer = customerService.getCustomerByCode(custCode);
			asset.setCustomer(newCustomer);
		} else {
			asset.setCustomer(customer);
		}
	}

	public void assetVoToAsset(AssetVo assetVo, Asset asset) {
		try {
			Project project = projectService.getProjectForAsset(assetVo, asset);
			if (null != assetVo.getId()) {
				asset.setId(assetVo.getId());
			}
			User assetUser = null;
			if (null != assetVo.getUser()) {
				assetUser = userService.getUserByName(assetVo.getUser()
						.getUserName());
			}
			String locationAddr = assetVo.getLocation();
			Location location = null;
			try {
				location = locationService.getLocationBySiteAndRoom(
						locationAddr.trim().substring(0, 6), locationAddr
								.trim().substring(6, locationAddr.length()));
			} catch (Exception e) {
				logger.error("Get location error!", e);
			}
			asset.setProject(project);
			asset.setAssetName(assetVo.getAssetName());
			asset.setOwnerShip(assetVo.getOwnerShip());
			asset.setType(assetVo.getType());
			asset.setBarCode(assetVo.getBarCode());
			asset.setUser(assetUser);
			asset.setLocation(location);
			asset.setStatus(assetVo.getStatus());
			asset.setSeriesNo(assetVo.getSeriesNo());
			asset.setPoNo(assetVo.getPoNo());
			asset.setPhotoPath(assetVo.getPhotoPath());
			asset.setEntity(CommonUtil.stringToUTF8(assetVo.getEntity()));
			asset.setManufacturer(assetVo.getManufacturer());
			asset.setCheckInTime(UTCTimeUtil.localDateToUTC(assetVo
					.getCheckInTime()));
			asset.setCheckOutTime(UTCTimeUtil.localDateToUTC(assetVo
					.getCheckOutTime()));
			asset.setWarrantyTime(UTCTimeUtil.localDateToUTC(assetVo
					.getWarrantyTime()));
			asset.setVendor(assetVo.getVendor());
			asset.setMemo(assetVo.getMemo());
		} catch (Exception e) {
			logger.error("AssetVo to asset error!", e);
		}
	}

	@Override
	public List<Asset> findAssetsByCustomerId(String customerId) {
		String hql = "from Asset where customer.id = ? and isExpired = false ";
		return assetDao.find(hql, customerId);
	}

	/**
	 * assign assets(manager and IT)
	 */

	@Override
	public void itAssignAssets(AssignAssetCondition condition,
			HttpServletRequest request) throws ExceptionHelper {

		logger.info("enter itAssignAssets service successfully");
		if (null == condition) {
			logger.error("The param condition is null when it assign assets!");
			throw new ExceptionHelper(ErrorCodeUtil.ASSET_ASSIGN_FAILED);
		}
		String userId = condition.getUserId();
		String assetIds = condition.getAssetIds();
		String projectName = condition.getProjectName();
		String projectCode = condition.getProjectCode();
		String customerCode = condition.getCustomerCode();
		String customerName = condition.getCustomerName();

		if (StringUtils.isEmpty(assetIds)) {
			logger.error("The param assetIds is null when it assign assets!");
			throw new ExceptionHelper(ErrorCodeUtil.ASSET_ASSIGN_FAILED);
		} else if (StringUtils.isEmpty(customerName)) {
			logger.error("The param customerName is null when it assign assets!");
			throw new ExceptionHelper(ErrorCodeUtil.ASSET_ASSIGN_FAILED);
		}

		Customer customer = customerService.getCustomerByCode(customerCode);
		logger.info("judge customer is null when it assign assets, customer is null: "
				+ (null == customer));

		if (customer == null) {
			customer = new Customer();
			customer.setCustomerCode(customerCode);
			customer.setCustomerName(customerName);
			customerService.saveCustomer(customer);
			logger.info("save customer successfully when it assign assets");
		}

		Project project = null;
		if (StringUtils.isNotEmpty(projectName)) {
			project = projectService.getProjectByProjectCode(projectCode);
			logger.info("judge project is null when it assign assets, project is null: "
					+ (null == project));

			if (project == null) {
				project = new Project();
				project.setProjectCode(projectCode);
				project.setProjectName(projectName);
				project.setCustomer(customer);
				projectService.saveProject(project);
				logger.info("save project successfully when it assign assets");
			}
		}

		User receiver = null;
		UserVo userVo = null;
		Boolean assigningFlag = false;
		if (StringUtils.isNotEmpty(userId)) {

			try {
				userVo = remoteEmployeeService.getRemoteUserById(userId,
						request);
				logger.info("judge IAP userVo is null when it assign assets, userVo is null: "
						+ (null == userVo));
			} catch (DataException e) {
				logger.error("get userVo from IAP failed when IT assign asset",
						e);
				// TODO throw IAP exception
			}

			if (null == userService.getUserByUserId(userId)) {
				logger.info("judge DB user is null when it assign assets, user is null: "
						+ (null == userService.getUserByUserId(userId)));
				userService.saveUserAsUserVo(userVo);
			}
			receiver = userService.getUserByUserId(userId);

			if (RoleLevelUtil.getRoleByUserVo(userVo).name()
					.equals(RoleEnum.MANAGER.name())
					|| specialRoleService.findSpecialRoleByUserId(userId)) {
				assigningFlag = true;
			}
		}

		String[] ids = FormatUtil.splitString(assetIds, Constant.SPLIT_COMMA);
		Map<String, ExceptionHelper> errorCodes = new LinkedHashMap<String, ExceptionHelper>();

		for (String id : ids) {
			Asset asset = assetDao.getAssetById(id);

			if (null == asset) {
				logger.error("asset is null when IT return assets to customer, asset uuid: "
						+ id);
				errorCodes.put(id, new ExceptionHelper(
						ErrorCodeUtil.DATA_NOT_EXIST));
			} else {

				if (!AssetStatusOperateUtil.canITAssignAsset(asset)) {
					logger.error("asset status is invalid when IT assign assets, asset status is: "
							+ asset.getStatus());
					errorCodes.put(id, new ExceptionHelper(
							ErrorCodeUtil.ASSET_STATUS_INVALID));
				} else {

					if (assigningFlag) {
						asset.setStatus(TransientStatusEnum.ASSIGNING.name());
						asset.setProject(project);
						asset.setCustomer(customer);
						asset.setUser(receiver);
						assetDao.update(asset);
						
					} else {
						asset.setStatus(StatusEnum.IN_USE.name());
						asset.setProject(project);
						asset.setCustomer(customer);
						asset.setUser(receiver);
						assetDao.update(asset);
					}
				}
			}
		}
		if (0 != errorCodes.size()) {
			throw new ExceptionHelper(errorCodes);
		}
		logger.info("enter itAssignAssets service successfully");
	}

	@Override
	public void returnAssetsToCustomer(User returner, String assetIds) throws ExceptionHelper {

		logger.info("enter returnAssetsToCustomer service successfully");
		if (null == assetIds || "".equals(assetIds)) {
			logger.error("The param assetIds is null when assets return to Customer");
			throw new ExceptionHelper(ErrorCodeUtil.ASSET_RETURN_FAILED);
		} else {

			String[] assetIdArr = FormatUtil.splitString(assetIds,
					Constant.SPLIT_COMMA);
			Map<String, ExceptionHelper> errorCodes = new LinkedHashMap<String, ExceptionHelper>();
			Date date = UTCTimeUtil.localDateToUTC();
			
			for (String id : assetIdArr) {
				Asset asset = assetDao.getAssetById(id);

				if (null == asset) {
					logger.error("asset is null when return assets to customer, asset uuid: "
							+ id);
					errorCodes.put(id, new ExceptionHelper(
							ErrorCodeUtil.DATA_NOT_EXIST));
				} else {
					if (!AssetStatusOperateUtil.canITReturnToCustomer(asset)) {
						logger.error("asset status is invalid when IT return assets to customer, asset uuid: "
								+ id);
						errorCodes.put(id, new ExceptionHelper(
								ErrorCodeUtil.ASSET_STATUS_INVALID));
					} else {
						asset.setStatus(TransientStatusEnum.RETURNING_TO_CUSTOMER
								.name());
						assetDao.update(asset);
						
						//TODO generate todo list for return operation
						ToDo todo = new ToDo();
						todo.setAsset(asset);
						todo.setReturnedTime(date);
						todo.setReturner(returner);
						todoDao.save(todo);
					}
				}
			}
			if (0 != errorCodes.size()) {
				throw new ExceptionHelper(errorCodes);
			}
			logger.info("leave returnAssetsToCustomer service successfully");
		}
	}

	@Override
	public void changeAssetsToFixed(String assetIds) throws ExceptionHelper {

		logger.info("enter changeAssetsToFixed servie successfully");
		if (null == assetIds || "".equals(assetIds)) {
			logger.error("The param assetIds is null when assets return to Customer");
			throw new ExceptionHelper(
					ErrorCodeUtil.ASSET_CHANGE_TO_FIXED_FAILED);
		} else {

			String[] assetIdArr = FormatUtil.splitString(assetIds,
					Constant.SPLIT_COMMA);
			Map<String, ExceptionHelper> errorCodes = new LinkedHashMap<String, ExceptionHelper>();

			for (String assetId : assetIdArr) {
				Asset asset = assetDao.getAssetById(assetId);

				if (null == asset) {
					logger.error("asset is null when return assets to customer, asset uuid: "
							+ assetId);
					errorCodes.put(assetId, new ExceptionHelper(
							ErrorCodeUtil.DATA_NOT_EXIST));
				} else {
					asset.setFixed(Boolean.TRUE);
					assetDao.update(asset);
				}
			}
			if (0 != errorCodes.size()) {
				throw new ExceptionHelper(errorCodes);
			}
			logger.info("leave changeAssetsToFixed servie successfully");
		}
	}

	@Override
	public void changeAssetsToNonFixed(String assetIds) throws ExceptionHelper {

		logger.info("enter changeAssetsToNonFixed servie successfully");
		if (null == assetIds || "".equals(assetIds)) {
			logger.error("The param assetIds is null when assets return to Customer");
			throw new ExceptionHelper(
					ErrorCodeUtil.ASSET_CHANGE_TO_NONFIXED_FAILED);
		} else {

			String[] assetIdArr = FormatUtil.splitString(assetIds,
					Constant.SPLIT_COMMA);
			Map<String, ExceptionHelper> errorCodes = new LinkedHashMap<String, ExceptionHelper>();

			for (String assetId : assetIdArr) {
				Asset asset = assetDao.getAssetById(assetId);

				if (null == asset) {
					logger.error("asset is null when return assets to customer, asset uuid: "
							+ assetId);
					errorCodes.put(assetId, new ExceptionHelper(
							ErrorCodeUtil.DATA_NOT_EXIST));
				} else {
					asset.setFixed(Boolean.FALSE);
					assetDao.update(asset);
				}
			}
			if (0 != errorCodes.size()) {
				throw new ExceptionHelper(errorCodes);
			}
			logger.info("leave changeAssetsToNonFixed servie successfully");
		}
	}

	@Override
	public void addAssetsToAuditForSearchResult(Page<Asset> page)
			throws ExceptionHelper {

		logger.info("enter addAssetsToAuditForSearchResult service successfully");
		if (null == page || null == page.getAllRecords()) {
			logger.error("there is no asset when add assets to audit for search result");
			throw new ExceptionHelper(ErrorCodeUtil.ASSET_ADD_TO_AUDIT_FAILED);
		} else {

			List<String> fileNameList = auditFileDao.getAuditFilesName();
			logger.info("get auditFile name list when add assets to audit for search result, auditFile name list size: "
					+ fileNameList.size());

			AuditFile auditFile = new AuditFile();
			auditFile.setFileName(FormatUtil.generateFileName(fileNameList));
			auditFileDao.save(auditFile);
			logger.info("save audit file successfully when add assets to audit for search result");

			for (Asset asset : page.getAllRecords()) {

				Audit audit = new Audit();
				audit.setAsset(asset);
				audit.setAuditFile(auditFile);
				audit.setStatus(false);
				auditDao.save(audit);
			}
			logger.info("leave addAssetsToAuditForSearchResult service successfully");
		}
	}

	@Override
	public void addAssetsToAuditForSelected(String assetIds)
			throws ExceptionHelper {

		logger.info("enter addAssetsToAuditForSelected service successfully");
		if (null == assetIds || "".equals(assetIds)) {
			logger.error("The param assetIds is null when add assets to audit for selected");
			throw new ExceptionHelper(ErrorCodeUtil.ASSET_ADD_TO_AUDIT_FAILED);
		} else {

			String[] assetIdArr = FormatUtil.splitString(assetIds,
					Constant.SPLIT_COMMA);

			List<String> fileNameList = auditFileDao.getAuditFilesName();
			logger.info("get auditFile name list when add assets to audit for search result, auditFile name list size: "
					+ fileNameList.size());

			AuditFile auditFile = new AuditFile();
			auditFile.setFileName(FormatUtil.generateFileName(fileNameList));
			auditFileDao.save(auditFile);
			logger.info("save audit file successfully when add assets to audit for selected asset");

			Map<String, ExceptionHelper> errorCodes = new LinkedHashMap<String, ExceptionHelper>();

			for (String assetId : assetIdArr) {
				Asset asset = assetDao.getAssetById(assetId);

				if (null == asset) {
					logger.error("asset is null when add assets to audit for selected, asset uuid: "
							+ assetId);
					errorCodes.put(assetId, new ExceptionHelper(
							ErrorCodeUtil.DATA_NOT_EXIST));
				} else {

					Audit audit = new Audit();
					audit.setAsset(asset);
					audit.setAuditFile(auditFile);
					audit.setStatus(false);
					auditDao.save(audit);
				}
			}
			if (0 != errorCodes.size()) {
				throw new ExceptionHelper(errorCodes);
			}
			logger.info("leave addAssetsToAuditForSelected service successfully");
		}
	}


	@Override
	public void uploadAndDisplayImage(MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		FileOperateUtil.upload(request, response, file);
		String pathName = request.getContextPath() + "/upload/"
				+ file.getOriginalFilename();
		StringBuilder sbmsg = new StringBuilder(request.getScheme());
		sbmsg.append("://").append(request.getServerName()).append(":")
				.append(request.getServerPort()).append(pathName);

		try {
			response.getWriter().print(sbmsg);
		} catch (IOException e) {
			logger.error("Get image error!", e);
		}
	}

	@Override
	public String exportAssetsByIds(String assetIds) throws ExcelException,
			SQLException {

		String[] assetIdArr = FormatUtil.splitString(assetIds,
				Constant.SPLIT_COMMA);
		List<ExportVo> exportVos = assetDao
				.findAssetsByIdsForExport(assetIdArr);

		AssetTemplateParser assetTemplateParser = new AssetTemplateParser();

		return assetTemplateParser.parse(exportVos);
	}

	@Override
	public String exportAssetsForAll(Page<Asset> page) throws ExcelException,
			SQLException {

		if (null == page || null == page.getResult()
				|| 0 == page.getResult().size()) {
			throw new ExcelException(ErrorCodeUtil.ASSET_EXPORT_FAILED,
					"There is no asset when export assets for search result");
		} else {
			String[] assetIdArr = new String[page.getAllRecords().size()];

			for (int i = 0; i < page.getAllRecords().size(); i++) {
				if (null != page.getAllRecords().get(i)) {
					assetIdArr[i] = page.getAllRecords().get(i).getId();
				} else {
				    throw new ExcelException(ErrorCodeUtil.ASSET_EXPORT_FAILED,
		                    "The i'th asset is not exist when export assets for search result");
				}
			}
			List<ExportVo> exportVos = assetDao
					.findAssetsByIdsForExport(assetIdArr);
			AssetTemplateParser assetTemplateParser = new AssetTemplateParser();

			return assetTemplateParser.parse(exportVos);
		}
	}
//	@Override
	public void analyseUploadExcelFile(File file, HttpServletRequest request)
			throws ExcelException, DataException {

		ExcelUtil excelUtil = ExcelUtil.getInstance();
		Workbook workbookImport = null;

		try {
			workbookImport = excelUtil.getWorkbook(file);
		} catch (Exception e) {
			logger.error("backUp xls error : " + e.getMessage(), e);
		}
		AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
		Workbook workbookTemplate = assetTemplateParser
				.getWorkbook(AssetTemplateParser.ASSET_TEMPLATE);

		WritableWorkbook writableWorkbook = ExcelBuilder
				.createWritableWorkbook(assetTemplateParser.getOutputPath(
						"Asset", UTCTimeUtil.formatDateToString(new Date(),
								Constant.FILTER_TIME_PATTERN)),
						workbookTemplate);

		Sheet templateMachineSheet = workbookTemplate.getSheet("Machine");
		Sheet templateMonitorSheet = workbookTemplate.getSheet("Monitor");
		Sheet templateDeviceSheet = workbookTemplate.getSheet("Device");
		Sheet templateSoftwaresheet = workbookTemplate.getSheet("Software");
		Sheet templateOtherAssetsSheet = workbookTemplate
				.getSheet("OtherAsset");

		Cell[] templateMachineTitleCells = excelUtil.getRows(
				templateMachineSheet, 0);
		Cell[] templateMonitorTitleCells = excelUtil.getRows(
				templateMonitorSheet, 0);
		Cell[] templateDeviceTitleCells = excelUtil.getRows(
				templateDeviceSheet, 0);
		Cell[] templateSoftwareTitleCells = excelUtil.getRows(
				templateSoftwaresheet, 0);
		Cell[] templateOtherAssetTitleCells = excelUtil.getRows(
				templateOtherAssetsSheet, 0);

		Sheet machineSheet = workbookImport.getSheet("Machine");
		Sheet monitorSheet = workbookImport.getSheet("Monitor");
		Sheet deviceSheet = workbookImport.getSheet("Device");
		Sheet softwareSheet = workbookImport.getSheet("Software");
		Sheet otherAssetsSheet = workbookImport.getSheet("OtherAsset");

		logger.info("------------Start to parse Excel-------------");
		Cell[] machineTitleCells = excelUtil.getRows(machineSheet, 0);
		Cell[] monitorTitleCells = excelUtil.getRows(monitorSheet, 0);
		Cell[] deviceTitleCells = excelUtil.getRows(deviceSheet, 0);
		Cell[] softwareTitleCells = excelUtil.getRows(softwareSheet, 0);
		Cell[] otherAssetTitleCells = excelUtil.getRows(otherAssetsSheet, 0);

		int machineTotalRecords = excelUtil.getRows(machineSheet);
		int monitorTotalRecords = excelUtil.getRows(monitorSheet);
		int deviceTotalRecords = excelUtil.getRows(deviceSheet);
		int softwareTotalRecords = excelUtil.getRows(softwareSheet);
		int otherAssetTotalRecords = excelUtil.getRows(otherAssetsSheet);

		logger.info("-----------The machine type totally has："
				+ (machineTotalRecords - 1) + " records-----------");
		logger.info("-----------The monitor type totally has："
				+ (monitorTotalRecords - 1) + " records-----------");
		logger.info("-----------The device type totally has："
				+ (deviceTotalRecords - 1) + " records-----------");
		logger.info("-----------The software type totally has："
				+ (softwareTotalRecords - 1) + " records-----------");
		logger.info("-----------The otherAsset type totally has："
				+ (otherAssetTotalRecords - 1) + " records-----------");

		compareCellTitle(templateMachineTitleCells, machineTitleCells,
				AssetTypeEnum.MACHINE.toString());
		compareCellTitle(templateMonitorTitleCells, monitorTitleCells,
				AssetTypeEnum.MONITOR.toString());
		compareCellTitle(templateDeviceTitleCells, deviceTitleCells,
				AssetTypeEnum.DEVICE.toString());
		compareCellTitle(templateSoftwareTitleCells, softwareTitleCells,
				AssetTypeEnum.SOFTWARE.toString());
		compareCellTitle(templateOtherAssetTitleCells, otherAssetTitleCells,
				AssetTypeEnum.OTHERASSETS.toString());

		// temporary store the employee
		Map<String, String> remoteEmployees = remoteEmployeeService
				.findRemoteEmployeesForCache(request);
		Map<String, User> localEmployees = userService.findAllUsersFromLocal();

		// temporary store the customer
		Map<String, Customer> localCustomers = customerService
				.findAllCustomersFromLocal();
		Map<String, String> remoteCustomers = new HashMap<String, String>();
		List<CustomerVo> customers = remoteCustomerService
				.getAllCustomerFromIAP(request);
		for (CustomerVo customerVo : customers) {
			remoteCustomers.put(customerVo.getCustomerName(),
					customerVo.getCustomerCode());
		}

		// temporary store the project
		Map<String, Project> localProjects = projectService
				.findAllCustomersFromLocal();
		Map<String, String> remoteProjects = remoteProjectService
				.findAllProjectsFromIAP(request);

		// temporary store the location
		Map<String, Location> localLocations = locationService
				.findAllLocationsFromIAP();

		Map<String, Object> cacheData = new HashMap<String, Object>();
		cacheData.put("remoteEmployees", remoteEmployees);
		cacheData.put("localEmployees", localEmployees);
		cacheData.put("localCustomers", localCustomers);
		cacheData.put("remoteCustomers", remoteCustomers);
		cacheData.put("localProjects", localProjects);
		cacheData.put("remoteProjects", remoteProjects);
		cacheData.put("localLocations", localLocations);

		if (1 < machineTotalRecords) {
			setExcelToMachine(machineSheet, machineTotalRecords, excelUtil,
					cacheData, writableWorkbook);
		}
		if (1 < monitorTotalRecords) {
			setExcelToMonitor(monitorSheet, monitorTotalRecords, excelUtil,
					cacheData, writableWorkbook);
		}
		if (1 < deviceTotalRecords) {
			setExcelToDevice(deviceSheet, deviceTotalRecords, excelUtil,
					cacheData, writableWorkbook);
		}
		if (1 < softwareTotalRecords) {
			setExcelToSoftware(softwareSheet, softwareTotalRecords, excelUtil,
					cacheData, writableWorkbook);
		}
		if (1 < otherAssetTotalRecords) {
			setExcelToOtherAsset(otherAssetsSheet, otherAssetTotalRecords,
					excelUtil, cacheData, writableWorkbook);
		}
	}

	private void compareCellTitle(Cell[] template, Cell[] upload,
			String compareType) throws ExcelException {

		if (template.length != upload.length + 1) {
			throw new ExcelException("errorCode", compareType
					+ " type Title not match!");
		}
		for (int i = 0; i < upload.length; i++) {
			if (!template[i + 1].getContents().equals(upload[i].getContents())) {
				throw new ExcelException("errorCode", compareType
						+ " type Title not match!");
			}
		}
	}

	@SuppressWarnings("unchecked")
	private boolean setExcelToAsset(Cell[] currentRowCells, Asset asset,
			Map<String, Object> cacheData) throws ExcelException {

		boolean isErrorRecorde = Boolean.FALSE;

		Map<String, String> remoteEmployees = (Map<String, String>) cacheData
				.get("remoteEmployees");
		Map<String, User> localEmployees = (Map<String, User>) cacheData
				.get("localEmployees");
		Map<String, Customer> localCustomers = (Map<String, Customer>) cacheData
				.get("localCustomers");
		Map<String, String> remoteCustomers = (Map<String, String>) cacheData
				.get("remoteCustomers");
		Map<String, Project> localProjects = (Map<String, Project>) cacheData
				.get("localProjects");
		Map<String, String> remoteProjects = (Map<String, String>) cacheData
				.get("remoteProjects");
		Map<String, Location> localLocations = (Map<String, Location>) cacheData
				.get("localLocations");

		asset.setAssetId(generateAssetId(assetDao.getTotalCount(Asset.class)));

		if ("".equals(currentRowCells[1].getContents())
				|| null == currentRowCells[1].getContents()) {
			isErrorRecorde = Boolean.TRUE;
		}
		asset.setAssetName(currentRowCells[1].getContents());

		StatusEnum[] status = StatusEnum.values();
		boolean isNotRightStatu = Boolean.FALSE;

		for (StatusEnum statu : status) {
			if (!statu.toString().equals(currentRowCells[2].getContents())) {
				isNotRightStatu = Boolean.TRUE;
				break;
			}
		}

		if ("".equals(currentRowCells[2].getContents())
				|| null == currentRowCells[2].getContents() || isNotRightStatu) {
			isErrorRecorde = Boolean.TRUE;
		}
		asset.setStatus(currentRowCells[2].getContents());

		User user = new User();

		if ("".equals(currentRowCells[3].getContents())
				|| null == currentRowCells[3].getContents()) {
			isErrorRecorde = Boolean.TRUE;
		} else {
			if (localEmployees.containsKey(currentRowCells[3].getContents())) {
				user = localEmployees.get(currentRowCells[3].getContents());
			} else if (remoteEmployees.containsKey(currentRowCells[3]
					.getContents())) {
				user.setUserId(remoteEmployees.get(currentRowCells[3]
						.getContents()));
				user.setUserName(currentRowCells[3].getContents());
				userService.saveUser(user);
			} else {
				isErrorRecorde = Boolean.TRUE;
				user.setUserName(currentRowCells[3].getContents());
				logger.info("The " + currentRowCells[5].getContents()
						+ " user is not exsit in the IAP");
			}
		}
		asset.setUser(user);
		asset.setKeeper(currentRowCells[4].getContents());

		Customer customer = new Customer();

		if ("".equals(currentRowCells[5].getContents())
				|| null == currentRowCells[5].getContents()) {
			isErrorRecorde = Boolean.TRUE;
		} else {
			if (localCustomers.containsKey(currentRowCells[5].getContents())) {
				customer = localCustomers.get(currentRowCells[5].getContents());
			} else if (remoteCustomers.containsKey(currentRowCells[5]
					.getContents())) {
				customer.setCustomerCode(remoteCustomers.get(currentRowCells[5]
						.getContents()));
				customer.setCustomerName(currentRowCells[5].getContents());
				customerService.saveCustomer(customer);
			} else {
				isErrorRecorde = Boolean.TRUE;
				customer.setCustomerName(currentRowCells[5].getContents());
				logger.info("The " + currentRowCells[5].getContents()
						+ " customer is not exsit in the IAP");
			}
		}
		asset.setCustomer(customer);

		Project project = new Project();

		if (localProjects.containsKey(currentRowCells[6].getContents())) {
			project = localProjects.get(currentRowCells[6].getContents());
		} else if (remoteProjects.containsKey(currentRowCells[6].getContents())) {
			project.setProjectCode(remoteProjects.get(currentRowCells[6]
					.getContents()));
			project.setProjectName(currentRowCells[6].getContents());
			// TODO check whether the project belong to the customer?
			project.setCustomer(asset.getCustomer());
			projectService.saveProject(project);
		} else {
			isErrorRecorde = Boolean.TRUE;
			project.setProjectName(currentRowCells[6].getContents());
			logger.info("The " + currentRowCells[6].getContents()
					+ " project is not exsit in the IAP");
		}
		asset.setProject(project);

		AssetTypeEnum[] assetTypes = AssetTypeEnum.values();
		boolean isNotRightType = Boolean.FALSE;

		for (AssetTypeEnum assetType : assetTypes) {
			if (!assetType.toString().equals(currentRowCells[7].getContents())) {
				isNotRightType = Boolean.TRUE;
				break;
			}
		}

		if ("".equals(currentRowCells[2].getContents())
				|| null == currentRowCells[2].getContents() || isNotRightType) {
			isErrorRecorde = Boolean.TRUE;
		}
		asset.setType(currentRowCells[7].getContents());
		asset.setBarCode(currentRowCells[8].getContents());

		Location location = new Location();

		if (("".equals(currentRowCells[9].getContents()) || null == currentRowCells[9]
				.getContents())) {
			isErrorRecorde = Boolean.TRUE;
		} else {
			if (currentRowCells[9].getContents().lastIndexOf(
					Constant.SPLIT_UNDERLINE) < 0) {
				isErrorRecorde = Boolean.TRUE;
				location.setSite(currentRowCells[9].getContents());
				location.setRoom("");
			} else {
				int splitIndex = currentRowCells[9].getContents().lastIndexOf(
						Constant.SPLIT_UNDERLINE);
				int length = currentRowCells[9].getContents().length();
				String site = currentRowCells[9].getContents().substring(0,
						splitIndex);
				String room = currentRowCells[9].getContents().substring(
						splitIndex + 1, length);

				if (localLocations.containsKey(site)
						&& localLocations.get(site).getRoom().equals(room)) {
					location = localLocations.get(site);
				} else {
					location.setSite(site);
					location.setRoom(room);
					locationService.saveLocation(location);
				}
			}
		}
		asset.setLocation(location);
		asset.setManufacturer(currentRowCells[10].getContents());

		if (null == currentRowCells[11].getContents()
				|| "".equals(currentRowCells[11].getContents())) {
			isErrorRecorde = Boolean.TRUE;
		}
		asset.setOwnerShip(currentRowCells[11].getContents());

		if (null == currentRowCells[12].getContents()
				|| "".equals(currentRowCells[12].getContents())) {
			isErrorRecorde = Boolean.TRUE;
		}
		asset.setEntity(currentRowCells[12].getContents());
		asset.setMemo(currentRowCells[13].getContents());
		if ("N".equals(currentRowCells[14].getContents())
				|| "0".equals(currentRowCells[14].getContents())) {
			asset.setFixed(Boolean.FALSE);
		}
		if ("Y".equals(currentRowCells[14].getContents())
				|| "1".equals(currentRowCells[14].getContents())) {
			asset.setFixed(Boolean.TRUE);
		}
		asset.setSeriesNo(currentRowCells[15].getContents());
		asset.setPoNo(currentRowCells[16].getContents());
		asset.setCheckInTime(UTCTimeUtil.formatStringToDate(currentRowCells[17]
				.getContents()));
		asset.setCheckOutTime(UTCTimeUtil
				.formatStringToDate(currentRowCells[18].getContents()));
		asset.setVendor(currentRowCells[19].getContents());
		asset.setWarrantyTime(UTCTimeUtil
				.formatStringToDate(currentRowCells[20].getContents()));

		return isErrorRecorde;
	}

	private void setExcelToMachine(Sheet machineSheet, int machineTotalRecords,
			ExcelUtil excelUtil, Map<String, Object> cacheData,
			WritableWorkbook writableWorkbook) throws ExcelException {

		Asset asset = new Asset();
		// List<Machine> machines = new ArrayList<Machine>();

		for (int i = 1; i < machineTotalRecords; i++) {
			Cell[] currentRowCells = excelUtil.getRows(machineSheet, i);

			Machine machine = new Machine();

			boolean isErrorRecorde = setExcelToAsset(currentRowCells, asset,
					cacheData);
			boolean emptySubtype = Boolean.FALSE;

			if (null == currentRowCells[21].getContents()
					|| "".equals(currentRowCells[21].getContents())) {
				emptySubtype = Boolean.TRUE;
			}

			machine.setSubtype(currentRowCells[21].getContents());
			machine.setSpecification(currentRowCells[22].getContents());
			machine.setAddress(currentRowCells[23].getContents());
			machine.setConfiguration(currentRowCells[24].getContents());

			if (isErrorRecorde || emptySubtype) {
				AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
				// assetTemplateParser.fillOneCell(c, r, value, ws);
//				setErrorCommonAsset();
			} else {
				Asset newAsset = assetDao.save(asset);
				// Machine machine = machines.get(i);
				machine.setAsset(asset);
				machineService.saveMachine(machine);
			}
			// machines.add(machine);
		}
		// if((assets.size() > 0) && (machines.size() > 0) && (assets.size() ==
		// machines.size())){
		// for(int i = 0; i < assets.size(); i++){
		// Asset asset = assetDao.save(assets.get(i));
		// Machine machine = machines.get(i);
		// machine.setAsset(asset);
		// machineService.saveMachine(machine);
		// }
		//
		// }else{
		// //TODO cheng error code
		// throw new ExcelException("errorcode", "setExcelToMachine error!");
		// }
	}

	private void setExcelToMonitor(Sheet monitorSheet, int monitorTotalRecords,
			ExcelUtil excelUtil, Map<String, Object> cacheData,
			WritableWorkbook writableWorkbook) throws ExcelException {

		Asset asset = new Asset();
		List<Monitor> monitors = new ArrayList<Monitor>();

		for (int i = 1; i < monitorTotalRecords; i++) {
			Cell[] currentRowCells = excelUtil.getRows(monitorSheet, i);

			setExcelToAsset(currentRowCells, asset, cacheData);
			Monitor monitor = new Monitor();

			monitor.setSize(currentRowCells[21].getContents());
			monitor.setDetail(currentRowCells[22].getContents());

			monitors.add(monitor);
		}
		/*
		 * if((assets.size() > 0) && (monitors.size() > 0) && (assets.size() ==
		 * monitors.size())){ for(int i = 0; i < assets.size(); i++){ Asset
		 * asset = assetDao.save(assets.get(i)); Monitor monitor =
		 * monitors.get(i); monitor.setAsset(asset);
		 * monitorService.saveMonitor(monitor); } }else{ //TODO cheng error code
		 * throw new ExcelException("errorcode", "setExcelToMonitor error!"); }
		 */
	}

	private void setExcelToDevice(Sheet deviceSheet, int deviceTotalRecords,
			ExcelUtil excelUtil, Map<String, Object> cacheData,
			WritableWorkbook writableWorkbook) throws ExcelException {

		Asset asset = new Asset();
		List<Device> devices = new ArrayList<Device>();
		List<DeviceSubtype> deviceSubtypes = new ArrayList<DeviceSubtype>();

		for (int i = 1; i < deviceTotalRecords; i++) {
			Cell[] currentRowCells = excelUtil.getRows(deviceSheet, i);

			setExcelToAsset(currentRowCells, asset, cacheData);
			Device device = new Device();

			DeviceSubtype deviceSubtype = new DeviceSubtype();
			deviceSubtype.setSubtypeName(currentRowCells[21].getContents());
			device.setConfiguration(currentRowCells[22].getContents());

			deviceSubtypes.add(deviceSubtype);
			devices.add(device);
		}
		/*
		 * if((assets.size() > 0) && (devices.size() > 0) &&
		 * (deviceSubtypes.size() > 0) && (assets.size() == devices.size()) &&
		 * (assets.size() == deviceSubtypes.size())){
		 * 
		 * for(int i = 0; i < assets.size(); i++){ Asset asset =
		 * assetDao.save(assets.get(i)); Device device = devices.get(i);
		 * DeviceSubtype deviceSubtype = deviceSubtypes.get(i);
		 * device.setAsset(asset); device.setDeviceSubtype(deviceSubtype);
		 * deviceService.saveDevice(device); }
		 * 
		 * }else{ //TODO cheng error code throw new ExcelException("errorcode",
		 * "setExcelToDevice error!"); }
		 */
	}

	private void setExcelToSoftware(Sheet softwareSheet,
			int softwareTotalRecords, ExcelUtil excelUtil,
			Map<String, Object> cacheData, WritableWorkbook writableWorkbook)
			throws ExcelException {

		Asset asset = new Asset();
		List<Software> softwares = new ArrayList<Software>();

		for (int i = 1; i < softwareTotalRecords; i++) {
			Cell[] currentRowCells = excelUtil.getRows(softwareSheet, i);

			Software software = new Software();
			setExcelToAsset(currentRowCells, asset, cacheData);

			software.setVersion(currentRowCells[21].getContents());
			software.setLicenseKey(currentRowCells[22].getContents());
			software.setSoftwareExpiredTime(UTCTimeUtil
					.formatStringToDate(currentRowCells[23].getContents()));
			software.setMaxUseNum(Integer.valueOf(currentRowCells[24]
					.getContents()));
			software.setAdditionalInfo(currentRowCells[25].getContents());

			softwares.add(software);
		}
		/*
		 * if((assets.size() > 0) && (softwares.size() > 0) && (assets.size() ==
		 * softwares.size())){
		 * 
		 * for(int i = 0; i < assets.size(); i++){ Asset asset = assets.get(i);
		 * Software software = softwares.get(i);
		 * softwareService.saveSoftware(software); asset.setSoftware(software);
		 * assetDao.save(asset); } }else{ //TODO cheng error code throw new
		 * ExcelException("errorcode", "setExcelToSoftware error!"); }
		 */
	}

	private void setExcelToOtherAsset(Sheet otherAssetsSheet,
			int otherAssetTotalRecords, ExcelUtil excelUtil,
			Map<String, Object> cacheData, WritableWorkbook writableWorkbook)
			throws ExcelException {

		Asset asset = new Asset();
		List<OtherAssets> otherAssets = new ArrayList<OtherAssets>();

		for (int i = 1; i < otherAssetTotalRecords; i++) {
			Cell[] currentRowCells = excelUtil.getRows(otherAssetsSheet, i);

			setExcelToAsset(currentRowCells, asset, cacheData);
			OtherAssets otherAsset = new OtherAssets();

			otherAsset.setDetail(currentRowCells[21].getContents());
			otherAssets.add(otherAsset);
		}
		/*
		 * if((assets.size() > 0) && (otherAssets.size() > 0) && (assets.size()
		 * == otherAssets.size())){
		 * 
		 * for(int i = 0; i < assets.size(); i++){ Asset asset =
		 * assetDao.save(assets.get(i)); OtherAssets otherAsset =
		 * otherAssets.get(i); otherAsset.setAsset(asset);
		 * otherAssetsService.saveOtherAssets(otherAsset); } }else{ //TODO cheng
		 * error code throw new ExcelException("errorcode",
		 * "setExcelToOtherAsset error!"); }
		 */
	}

   
    @Override
    public ImportVo analyseUploadExcelFile(File file, HttpServletRequest request, String flag) throws ExcelException, DataException {
        
        ExcelUtil excelUtil = ExcelUtil.getInstance();
        Workbook workbookImport = null;
        int allImportRecords = 0;
        int successRecords = 0;
        int failureRecords = 0;
        
        try {
            workbookImport = excelUtil.getWorkbook(file);
        } catch (Exception e) {
            logger.error("backUp xls error : " + e.getMessage(), e);
        }
        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
        Workbook workbookTemplate = assetTemplateParser.getWorkbook(AssetTemplateParser.ASSET_TEMPLATE);
        
        String outPutPath = assetTemplateParser.getOutputPath("Asset", UTCTimeUtil
                .formatDateToString(new Date(),Constant.FILTER_TIME_PATTERN));
        WritableWorkbook writableWorkbook = ExcelBuilder.createWritableWorkbook(outPutPath, workbookTemplate);
        
        Sheet templateMachineSheet = workbookTemplate.getSheet("Machine");
        Sheet templateMonitorSheet = workbookTemplate.getSheet("Monitor");
        Sheet templateDeviceSheet = workbookTemplate.getSheet("Device");
        Sheet templateSoftwaresheet = workbookTemplate.getSheet("Software");
        Sheet templateOtherAssetsSheet = workbookTemplate.getSheet("OtherAsset");
        
        Cell[] templateMachineTitleCells = excelUtil.getRows(templateMachineSheet, 0);
        Cell[] templateMonitorTitleCells = excelUtil.getRows(templateMonitorSheet, 0);
        Cell[] templateDeviceTitleCells = excelUtil.getRows(templateDeviceSheet, 0);
        Cell[] templateSoftwareTitleCells = excelUtil.getRows(templateSoftwaresheet, 0);
        Cell[] templateOtherAssetTitleCells = excelUtil.getRows(templateOtherAssetsSheet, 0);
        
        Sheet machineSheet = workbookImport.getSheet("Machine");
        Sheet monitorSheet = workbookImport.getSheet("Monitor");
        Sheet deviceSheet = workbookImport.getSheet("Device");
        Sheet softwareSheet = workbookImport.getSheet("Software");
        Sheet otherAssetsSheet = workbookImport.getSheet("OtherAsset");
        
        logger.info("------------Start to parse Excel-------------");
        Cell[] machineTitleCells = excelUtil.getRows(machineSheet, 0);
        Cell[] monitorTitleCells = excelUtil.getRows(monitorSheet, 0);
        Cell[] deviceTitleCells = excelUtil.getRows(deviceSheet, 0);
        Cell[] softwareTitleCells = excelUtil.getRows(softwareSheet, 0);
        Cell[] otherAssetTitleCells = excelUtil.getRows(otherAssetsSheet, 0);
        
        int machineTotalRecords = excelUtil.getRows(machineSheet); 
        int monitorTotalRecords = excelUtil.getRows(monitorSheet); 
        int deviceTotalRecords = excelUtil.getRows(deviceSheet); 
        int softwareTotalRecords = excelUtil.getRows(softwareSheet); 
        int otherAssetTotalRecords = excelUtil.getRows(otherAssetsSheet); 
        
        logger.info("-----------The machine type totally has："+(machineTotalRecords - 1)+" records-----------");
        logger.info("-----------The monitor type totally has："+(monitorTotalRecords - 1)+" records-----------");
        logger.info("-----------The device type totally has："+(deviceTotalRecords - 1)+" records-----------");
        logger.info("-----------The software type totally has："+(softwareTotalRecords - 1)+" records-----------");
        logger.info("-----------The otherAsset type totally has："+(otherAssetTotalRecords - 1)+" records-----------");
        
        allImportRecords = machineTotalRecords + monitorTotalRecords + deviceTotalRecords + 
                softwareTotalRecords + otherAssetTotalRecords - 5;
        
        compareCellTitle(templateMachineTitleCells, machineTitleCells, AssetTypeEnum.MACHINE.toString());
        compareCellTitle(templateMonitorTitleCells, monitorTitleCells, AssetTypeEnum.MONITOR.toString());
        compareCellTitle(templateDeviceTitleCells, deviceTitleCells, AssetTypeEnum.DEVICE.toString());
        compareCellTitle(templateSoftwareTitleCells, softwareTitleCells, AssetTypeEnum.SOFTWARE.toString());
        compareCellTitle(templateOtherAssetTitleCells, otherAssetTitleCells, AssetTypeEnum.OTHERASSETS.toString());
        
        //temporary store the employee
        Map<String, String> remoteEmployees = remoteEmployeeService.findRemoteEmployeesForCache(request);
        Map<String, User> localEmployees = userService.findAllUsersFromLocal();
        
        //temporary store the customer
        Map<String, Customer> localCustomers = customerService.findAllCustomersFromLocal();
        Map<String, String> remoteCustomers = new HashMap<String, String>();
        List<CustomerVo> customers = remoteCustomerService.getAllCustomerFromIAP(request);
        for(CustomerVo customerVo : customers){
            remoteCustomers.put(customerVo.getCustomerName(), customerVo.getCustomerCode());
        }
        
        //temporary store the project
        Map<String, Project> localProjects = projectService.findAllCustomersFromLocal();
        Map<String, String> remoteProjects = remoteProjectService.findAllProjectsFromIAP(request);
        
        //temporary store the location
        Map<String, Location> localLocations = locationService.findAllLocationsFromIAP();
        
        Map<String, Object> cacheData = new HashMap<String, Object>();
        cacheData.put("remoteEmployees", remoteEmployees);
        cacheData.put("localEmployees", localEmployees);
        cacheData.put("localCustomers", localCustomers);
        cacheData.put("remoteCustomers", remoteCustomers);
        cacheData.put("localProjects", localProjects);
        cacheData.put("remoteProjects", remoteProjects);
        cacheData.put("localLocations", localLocations);
        
        int failureMachineRecords = 0;
        int failureMonitorRecords = 0;
        int failureDeviceRecords = 0;
        int failureSoftwareRecords = 0;
        int failureOtherAssetRecords = 0;
        
        if(1 < machineTotalRecords){
            failureMachineRecords = setExcelToMachine(machineSheet, machineTotalRecords, excelUtil, 
                    cacheData, writableWorkbook, flag);
        }
        if(1 < monitorTotalRecords){
            failureMonitorRecords = setExcelToMonitor(monitorSheet, monitorTotalRecords, excelUtil,
                    cacheData, writableWorkbook, flag);
        }
        if(1 < deviceTotalRecords){
            failureDeviceRecords = setExcelToDevice(deviceSheet, deviceTotalRecords, excelUtil, 
                    cacheData, writableWorkbook, flag);
        }
        if(1 < softwareTotalRecords){
            failureSoftwareRecords = setExcelToSoftware(softwareSheet, softwareTotalRecords, excelUtil, 
                    cacheData, writableWorkbook, flag);
        }
        if(1 < otherAssetTotalRecords){
            failureOtherAssetRecords = setExcelToOtherAsset(otherAssetsSheet, otherAssetTotalRecords, 
                    excelUtil, cacheData, writableWorkbook, flag);
        }
        
        failureRecords = failureMachineRecords + failureMonitorRecords + failureDeviceRecords + 
                failureSoftwareRecords + failureOtherAssetRecords;
        successRecords = allImportRecords - failureRecords;
        
        Workbook [] workbooks = {workbookImport, workbookTemplate};
        closeWorkbook(workbooks, writableWorkbook, failureRecords);
        
        ImportVo importVo =  new ImportVo();
        
        importVo.setAllImportRecords(allImportRecords);
        importVo.setSuccessRecords(successRecords);
        importVo.setFailureRecords(failureRecords);
        
        File failureFile = new File(outPutPath);
        importVo.setFailureFileName(failureFile.getName());
        
        return importVo;
    }
    
    @SuppressWarnings("unchecked")
    private ImportVo setExcelToAsset(Cell[] currentRowCells,Map<String, Object> cacheData,
            String flag) throws ExcelException{
        
        boolean isErrorRecorde = Boolean.FALSE;
        Asset asset = new Asset();
        
        Map<String, String> remoteEmployees = (Map<String, String>) cacheData.get("remoteEmployees");
        Map<String, User> localEmployees = (Map<String, User>) cacheData.get("localEmployees");
        Map<String, Customer> localCustomers = (Map<String, Customer>) cacheData.get("localCustomers");
        Map<String, String> remoteCustomers = (Map<String, String>) cacheData.get("remoteCustomers");
        Map<String, Project> localProjects = (Map<String, Project>) cacheData.get("localProjects");
        Map<String, String> remoteProjects = (Map<String, String>) cacheData.get("remoteProjects");
        Map<String, Location> localLocations = (Map<String, Location>) cacheData.get("localLocations");
        
        if(Constant.CREATE.equals(flag)){
            if(null != currentRowCells[0].getContents() && !"".equals(currentRowCells[0].getContents())){
                isErrorRecorde = Boolean.TRUE;
                asset.setAssetId(currentRowCells[0].getContents());
            }else{
                asset.setAssetId(generateAssetId(assetDao.getTotalCount(Asset.class)));
            }
        }else{
            if(null == currentRowCells[0].getContents() || "".equals(currentRowCells[0].getContents())){
                isErrorRecorde = Boolean.TRUE;
            }else{
                asset = assetDao.getByAssetId(currentRowCells[0].getContents());
                
                if(null == asset){
                    isErrorRecorde = Boolean.TRUE;
                    asset.setAssetId(currentRowCells[0].getContents());
                }
            }
            
        }
        
        if("".equals(currentRowCells[1].getContents()) || null == currentRowCells[1].getContents()){
            isErrorRecorde = Boolean.TRUE;
        }
        asset.setAssetName(currentRowCells[1].getContents());
        
        StatusEnum [] status = StatusEnum.values();
        boolean isRightStatu = Boolean.FALSE;
        
        for(StatusEnum statu : status){
           if(statu.toString().equals(currentRowCells[2].getContents())){
               isRightStatu = Boolean.TRUE;
               break;
           }
        }
        
        if("".equals(currentRowCells[2].getContents()) || null == currentRowCells[2].getContents() ||
                !isRightStatu){
            isErrorRecorde = Boolean.TRUE;
        }
        asset.setStatus(currentRowCells[2].getContents());
        

        User user = new User();
        
        if("".equals(currentRowCells[3].getContents()) || null == currentRowCells[2].getContents()){
            isErrorRecorde = Boolean.TRUE;
        }else {
            if(localEmployees.containsKey(currentRowCells[3].getContents())){
                user = localEmployees.get(currentRowCells[3].getContents());
            }else if(remoteEmployees.containsKey(currentRowCells[3].getContents())){
                user.setUserId(remoteEmployees.get(currentRowCells[3].getContents()));
                user.setUserName(currentRowCells[3].getContents());
                userService.saveUser(user);
            }else{
                isErrorRecorde = Boolean.TRUE;
                user.setUserName(currentRowCells[3].getContents());
                logger.info("The " + currentRowCells[3].getContents() + " user is not exsit in the IAP");
            }
        }
        asset.setUser(user);
        asset.setKeeper(currentRowCells[4].getContents());
        
        Customer customer = new Customer();
        
        if("".equals(currentRowCells[5].getContents()) || null == currentRowCells[4].getContents()){
            isErrorRecorde = Boolean.TRUE;
        }else{
            if(localCustomers.containsKey(currentRowCells[5].getContents())){
                customer = localCustomers.get(currentRowCells[5].getContents());
            }else if(remoteCustomers.containsKey(currentRowCells[5].getContents())){
                customer.setCustomerCode(remoteCustomers.get(currentRowCells[5].getContents()));
                customer.setCustomerName(currentRowCells[5].getContents());
                customerService.saveCustomer(customer);
            }else{
                isErrorRecorde = Boolean.TRUE;
                customer.setCustomerName(currentRowCells[5].getContents());
                logger.info("The " + currentRowCells[5].getContents() + " customer is not exsit in the IAP");
            }
        }
        asset.setCustomer(customer);
        
        Project project = new Project();
        
        if (localProjects.containsKey(currentRowCells[6].getContents())) {
            project = localProjects.get(currentRowCells[6].getContents());
        } else if (remoteProjects.containsKey(currentRowCells[6].getContents())) {
            project.setProjectCode(remoteProjects.get(currentRowCells[6].getContents()));
            project.setProjectName(currentRowCells[6].getContents());
            // TODO check whether the project belong to the customer?
            project.setCustomer(asset.getCustomer());
            projectService.saveProject(project);
        } else {
            isErrorRecorde = Boolean.TRUE;
            project.setProjectName(currentRowCells[6].getContents());
            logger.info("The " + currentRowCells[6].getContents()
                    + " project is not exsit in the IAP");
        }
        asset.setProject(project);
        
        AssetTypeEnum [] assetTypes = AssetTypeEnum.values();
        boolean isRightType = Boolean.FALSE;
        
        for(AssetTypeEnum assetType : assetTypes){
           if(assetType.toString().equals(currentRowCells[7].getContents())){
               isRightType = Boolean.TRUE;
               break;
           }
        }
        
        if("".equals(currentRowCells[7].getContents()) || null == currentRowCells[7].getContents() ||
                !isRightType){
            isErrorRecorde = Boolean.TRUE;
        }else{
            if(!currentRowCells[7].getContents().equals(asset.getType())){
                isErrorRecorde = Boolean.TRUE;
            }
        }
        asset.setType(currentRowCells[7].getContents());
        asset.setBarCode(currentRowCells[8].getContents());
        
        Location location = new Location();
        
        if(("".equals(currentRowCells[9].getContents()) || null == currentRowCells[9].getContents())){
            isErrorRecorde = Boolean.TRUE;
        }else {
            if(currentRowCells[9].getContents().lastIndexOf(Constant.SPLIT_UNDERLINE) < 0){
                isErrorRecorde = Boolean.TRUE;
                location.setSite(currentRowCells[9].getContents());
                location.setRoom("");
            }else{
                int splitIndex = currentRowCells[9].getContents().lastIndexOf(Constant.SPLIT_UNDERLINE);
                int length = currentRowCells[9].getContents().length();
                String site = currentRowCells[9].getContents().substring(0, splitIndex);
                String room = currentRowCells[9].getContents().substring(splitIndex + 1, length);
                
                if(localLocations.containsKey(site) && localLocations.get(site).getRoom()
                        .equals(room)){
                    location = localLocations.get(site);
                }else{
                    location.setSite(site);
                    location.setRoom(room);
                    locationService.saveLocation(location);
                }
            }
        }
        asset.setLocation(location);
        asset.setManufacturer(currentRowCells[10].getContents());
        
        if(null == currentRowCells[11].getContents() || "".equals(currentRowCells[11].getContents())){
            isErrorRecorde = Boolean.TRUE;
        }
        asset.setOwnerShip(currentRowCells[11].getContents());
        
        if(null == currentRowCells[12].getContents() || "".equals(currentRowCells[12].getContents())){
            isErrorRecorde = Boolean.TRUE;
        }
        asset.setEntity(currentRowCells[12].getContents());
        asset.setMemo(currentRowCells[13].getContents());
        if("N".equals(currentRowCells[14].getContents()) ||
                "0".equals(currentRowCells[14].getContents())){
            asset.setFixed(Boolean.FALSE);
        }
        if("Y".equals(currentRowCells[14].getContents()) ||
                "1".equals(currentRowCells[14].getContents())){
            asset.setFixed(Boolean.TRUE);
        }
        asset.setSeriesNo(currentRowCells[15].getContents());
        asset.setPoNo(currentRowCells[16].getContents());
        asset.setCheckInTime(UTCTimeUtil.formatStringToDate(currentRowCells[17].getContents()));
        asset.setCheckOutTime(UTCTimeUtil.formatStringToDate(currentRowCells[18].getContents()));
        asset.setVendor(currentRowCells[19].getContents());
        asset.setWarrantyTime(UTCTimeUtil.formatStringToDate(currentRowCells[20].getContents()));
        
        ImportVo importVo = new ImportVo();
        importVo.setErrorRecorde(isErrorRecorde);
        importVo.setAsset(asset);
        
        return importVo;
    }
    
    private int setExcelToMachine(Sheet machineSheet, int machineTotalRecords,
            ExcelUtil excelUtil, Map<String, Object> cacheData, WritableWorkbook writableWorkbook,
            String flag) throws ExcelException{
        
        int failureRecords = 0;
        
        for(int i = 1; i < machineTotalRecords; i++){
            Cell[] currentRowCells = excelUtil.getRows(machineSheet, i);
            Machine machine = new Machine();
            
            ImportVo importVo = setExcelToAsset(currentRowCells, cacheData, flag);
            boolean emptySubtype = Boolean.FALSE;
            
            if(null == currentRowCells[21].getContents() || "".equals(currentRowCells[21].getContents())){
                emptySubtype = Boolean.TRUE;
            }
            if(Constant.UPDATE.equals(flag) && !importVo.isErrorRecorde() && !emptySubtype && 
                    AssetTypeEnum.MACHINE.toString().equals(importVo.getAsset().getType())){
                
                machine = machineService.getByAssetId(importVo.getAsset().getId());
            }
            
            machine.setSubtype(currentRowCells[21].getContents());
            machine.setSpecification(currentRowCells[22].getContents());
            machine.setAddress(currentRowCells[23].getContents());
            machine.setConfiguration(currentRowCells[24].getContents());
            
            if(importVo.isErrorRecorde() || emptySubtype || 
                    !AssetTypeEnum.MACHINE.toString().equals(importVo.getAsset().getType())){
                
                WritableSheet sheet = writableWorkbook.getSheet("Machine");
                int errorRecords = excelUtil.getRows(sheet);
                int column = setErrorCommonAsset(importVo.getAsset(), sheet, errorRecords);
                sheet = setErrorMachine(machine, sheet, column, errorRecords);
                failureRecords++;
            } else{
                if(Constant.CREATE.equals(flag)){
                    Asset newAsset = assetDao.save(importVo.getAsset());
                    machine.setAsset(newAsset);
                    machineService.saveMachine(machine);
                }else{
                    Asset newAsset = assetDao.update(importVo.getAsset());
                    machine.setAsset(newAsset);
                    machineService.updateMachine(machine);
                }
                
            }
        }
        return failureRecords;
    }
    
    private int setExcelToMonitor(Sheet monitorSheet, int monitorTotalRecords,
            ExcelUtil excelUtil, Map<String, Object> cacheData, WritableWorkbook writableWorkbook,
            String flag) throws ExcelException{
        
        int failureRecords = 0;
        
        for(int i = 1; i < monitorTotalRecords; i++){
            Cell[] currentRowCells = excelUtil.getRows(monitorSheet, i);
            
            ImportVo importVo = setExcelToAsset(currentRowCells, cacheData, flag);
            Monitor monitor = new Monitor();
            
            if(Constant.UPDATE.equals(flag) && !importVo.isErrorRecorde() && 
                    AssetTypeEnum.MONITOR.toString().equals(importVo.getAsset().getType())){
                
                monitor = monitorService.getByAssetId(importVo.getAsset().getId());
            }
            
            monitor.setSize(currentRowCells[21].getContents());
            monitor.setDetail(currentRowCells[22].getContents());
            
            if(importVo.isErrorRecorde() || !AssetTypeEnum.MONITOR.toString()
                    .equals(importVo.getAsset().getType())){
                WritableSheet sheet = writableWorkbook.getSheet("Monitor");
                int errorRecords = excelUtil.getRows(sheet);
                int column = setErrorCommonAsset(importVo.getAsset(), sheet, errorRecords);
                sheet = setErrorMonitor(monitor, sheet, column, errorRecords);
                failureRecords++;
            }else{
                if(Constant.CREATE.equals(flag)){
                    Asset newAsset = assetDao.save(importVo.getAsset());
                    monitor.setAsset(newAsset);
                    monitorService.saveMonitor(monitor);
                }else{
                    Asset newAsset = assetDao.update(importVo.getAsset());
                    monitor.setAsset(newAsset);
                    monitorService.updateMonitor(monitor);
                }
                
            }
        }
        return failureRecords;
    }
    
    private int setExcelToDevice(Sheet deviceSheet, int deviceTotalRecords,
            ExcelUtil excelUtil, Map<String, Object> cacheData, WritableWorkbook writableWorkbook,
            String flag) throws ExcelException{
        
        int failureRecords = 0;
        
        for(int i = 1; i < deviceTotalRecords; i++){
            Cell[] currentRowCells = excelUtil.getRows(deviceSheet, i);
            
            ImportVo importVo = setExcelToAsset(currentRowCells, cacheData, flag);
            Device device = new Device();
            DeviceSubtype deviceSubtype = new DeviceSubtype();
            
            if(Constant.UPDATE.equals(flag) && !importVo.isErrorRecorde() && 
                    AssetTypeEnum.DEVICE.toString().equals(importVo.getAsset().getType())){
                
                device = deviceService.getByAssetId(importVo.getAsset().getId());
                deviceSubtype = device.getDeviceSubtype();
            }
            
            deviceSubtype.setSubtypeName(currentRowCells[21].getContents());
            device.setConfiguration(currentRowCells[22].getContents());
            
            if(importVo.isErrorRecorde() || !AssetTypeEnum.DEVICE.toString()
                    .equals(importVo.getAsset().getType())){
                WritableSheet sheet = writableWorkbook.getSheet("Device");
                int errorRecords = excelUtil.getRows(sheet);
                int column = setErrorCommonAsset(importVo.getAsset(), sheet, errorRecords);
                sheet = setErrorDevice(device, sheet, column, errorRecords);
                failureRecords++;
            }else{
                if(Constant.CREATE.equals(flag)){
                    Asset newAsset = assetDao.save(importVo.getAsset());
                    device.setAsset(newAsset);
                    device.setDeviceSubtype(deviceSubtype);
                    deviceService.saveDevice(device);
                }else{
                    Asset newAsset = assetDao.update(importVo.getAsset());
                    device.setAsset(newAsset);
                    device.setDeviceSubtype(deviceSubtype);
                    deviceService.updateDevice(device);
                }
                
            }
        }
        return failureRecords;
    }
    
    private int setExcelToSoftware(Sheet softwareSheet, int softwareTotalRecords,
            ExcelUtil excelUtil, Map<String, Object> cacheData, WritableWorkbook writableWorkbook,
            String flag) throws ExcelException{
        
        int failureRecords = 0;
        
        for(int i = 1; i < softwareTotalRecords; i++){
            Cell[] currentRowCells = excelUtil.getRows(softwareSheet, i);
            
            Software software = new Software();
            ImportVo importVo = setExcelToAsset(currentRowCells, cacheData, flag);
            
            if(Constant.UPDATE.equals(flag) && !importVo.isErrorRecorde() && 
                    AssetTypeEnum.SOFTWARE.toString().equals(importVo.getAsset().getType())){
                
                software = importVo.getAsset().getSoftware();
            }
            
            software.setVersion(currentRowCells[21].getContents());
            software.setLicenseKey(currentRowCells[22].getContents());
            //TODO check whether the right time format
            software.setSoftwareExpiredTime(UTCTimeUtil.formatStringToDate(currentRowCells[23].getContents()));
            
            boolean isNum = currentRowCells[24].getContents().matches("[0-9]+"); 
            
            if(isNum){
                software.setMaxUseNum(Integer.valueOf(currentRowCells[24].getContents()));
            }else{
                importVo.setErrorRecorde(Boolean.TRUE);
            }
            software.setAdditionalInfo(currentRowCells[25].getContents());
            
            if(importVo.isErrorRecorde() || !AssetTypeEnum.SOFTWARE.toString().equals(importVo.getAsset().getType())){
                WritableSheet sheet = writableWorkbook.getSheet("Software");
                int errorRecords = excelUtil.getRows(sheet);
                int column = setErrorCommonAsset(importVo.getAsset(), sheet, errorRecords);
                sheet = setErrorSoftware(software, sheet, column, errorRecords,
                        currentRowCells[24].getContents());
                failureRecords++;
            }else{
                if(Constant.CREATE.equals(flag)){
                    softwareService.saveSoftware(software);
                    importVo.getAsset().setSoftware(software);
                    assetDao.save(importVo.getAsset());
                }else{
                    softwareService.updateSoftware(software);
                    importVo.getAsset().setSoftware(software);
                    assetDao.update(importVo.getAsset());
                }
            }
        }
        return failureRecords;
    }
    
    private int setExcelToOtherAsset(Sheet otherAssetsSheet, int otherAssetTotalRecords,
            ExcelUtil excelUtil, Map<String, Object> cacheData, WritableWorkbook writableWorkbook,
            String flag) throws ExcelException{
        
        int failureRecords = 0;
        
        for(int i = 1; i < otherAssetTotalRecords; i++){
            Cell[] currentRowCells = excelUtil.getRows(otherAssetsSheet, i);
            
            ImportVo importVo = setExcelToAsset(currentRowCells, cacheData, flag);
            OtherAssets otherAsset = new OtherAssets();
            
            if(Constant.UPDATE.equals(flag) && !importVo.isErrorRecorde() && 
                    AssetTypeEnum.OTHERASSETS.toString().equals(importVo.getAsset().getType())){
                
                otherAsset = otherAssetsService.getByAssetId(importVo.getAsset().getId());
            }
            
            otherAsset.setDetail(currentRowCells[21].getContents());
            
            if(importVo.isErrorRecorde() || AssetTypeEnum.OTHERASSETS.toString().equals(importVo.getAsset().getType())){
                WritableSheet sheet = writableWorkbook.getSheet("OtherAsset");
                int errorRecords = excelUtil.getRows(sheet);
                int column = setErrorCommonAsset(importVo.getAsset(), sheet, errorRecords);
                sheet = setErrorOtherAsset(otherAsset, sheet, column, errorRecords);
                failureRecords++;
            }else{
                if(Constant.CREATE.equals(flag)){
                    Asset newAsset = assetDao.save(importVo.getAsset());
                    otherAsset.setAsset(newAsset);
                    otherAssetsService.saveOtherAssets(otherAsset);
                }else{
                    Asset newAsset = assetDao.update(importVo.getAsset());
                    otherAsset.setAsset(newAsset);
                    otherAssetsService.updateOtherAssets(otherAsset);
                }
            }
        }
        return failureRecords;
    }
    
    public static String generateAssetId(int count) {
        String assetId = "Asset"
                + UTCTimeUtil.formatDateToString(UTCTimeUtil.localDateToUTC(), "yyyy-mm-dd")
                        .substring(0, 4) + ((count + 100000) + "").substring(1, 6);
        return assetId;
    }
    
    private int setErrorCommonAsset(Asset asset, WritableSheet sheet, int row) throws ExcelException{
        
        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
        int column = 1;

        assetTemplateParser.fillOneCell(column++, row, asset.getAssetName(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getStatus(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getUser().getUserName(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getKeeper(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getCustomer().getCustomerName(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getProject().getProjectName(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getType(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getBarCode(), sheet);
        
        if(null != asset.getLocation().getRoom() || !"".equals(asset.getLocation().getRoom())){
            assetTemplateParser.fillOneCell(column++, row, asset.getLocation().getSite() + Constant.SPLIT_UNDERLINE 
                    + asset.getLocation().getRoom(), sheet);
        }else{
            assetTemplateParser.fillOneCell(column++, row, asset.getLocation().getSite(), sheet);
        }
        assetTemplateParser.fillOneCell(column++, row, asset.getManufacturer(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getOwnerShip(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getEntity(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getMemo(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.isFixed(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getSeriesNo(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getPoNo(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getCheckInTime(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getCheckOutTime(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getVendor(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getWarrantyTime(), sheet);
        
        return column;
    }
    
    private WritableSheet setErrorMachine(Machine machine, WritableSheet sheet, int column, int row)
            throws ExcelException{
        
        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
        
        assetTemplateParser.fillOneCell(column++, row, machine.getSubtype(), sheet);
        assetTemplateParser.fillOneCell(column++, row, machine.getSpecification(), sheet);
        assetTemplateParser.fillOneCell(column++, row, machine.getAddress(), sheet);
        assetTemplateParser.fillOneCell(column, row, machine.getConfiguration(), sheet);
        
        return sheet;
    }
    
    private WritableSheet setErrorMonitor(Monitor monitor, WritableSheet sheet, int column, int row)
            throws ExcelException {
        
        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
        
        assetTemplateParser.fillOneCell(column++, row, monitor.getSize(), sheet);
        assetTemplateParser.fillOneCell(column, row, monitor.getDetail(), sheet);
        
        return sheet;
    }
    private WritableSheet setErrorDevice(Device device, WritableSheet sheet, int column, int row)
            throws ExcelException{
        
        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
        
        assetTemplateParser.fillOneCell(column++, row, device.getDeviceSubtype().getSubtypeName(), sheet);
        assetTemplateParser.fillOneCell(column, row, device.getConfiguration(), sheet);
        
        return sheet;
        
    }
    private WritableSheet setErrorSoftware(Software software, WritableSheet sheet, int column, int row,
            String maxUseNum) 
            throws ExcelException{
        
        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
        
        assetTemplateParser.fillOneCell(column++, row, software.getVersion(), sheet);
        assetTemplateParser.fillOneCell(column++, row, software.getLicenseKey(), sheet);
        assetTemplateParser.fillOneCell(column++, row, software.getSoftwareExpiredTime(), sheet);
        assetTemplateParser.fillOneCell(column++, row, maxUseNum, sheet);
        assetTemplateParser.fillOneCell(column, row, software.getAdditionalInfo(), sheet);
        
        return sheet;
    }
    private WritableSheet setErrorOtherAsset(OtherAssets otherAssets, WritableSheet sheet, int column, int row)
            throws ExcelException{
        
        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
        
        assetTemplateParser.fillOneCell(column, row, otherAssets.getDetail(), sheet);
        
        return sheet;
    }
    
    private void closeWorkbook(Workbook [] workbooks, WritableWorkbook writableWorkbook, int failureRecords)
            throws ExcelException{
        
        for(Workbook workbook : workbooks){
            workbook.close();
        }
        try {
            if(0 < failureRecords){
                writableWorkbook.write();
            }
            writableWorkbook.close();
        } catch (Exception e) {
            throw new ExcelException("Exception when closing WritableWorkbook: " + e);
        }
        
    }

	@Override
	public Map<String, Integer> getAssetCountForPanel(User user) {
		return assetDao.getAssetCountForIT(user);
	}

	@Override
	public Map<String, Integer> getAssetCountForManager(User user, List<Customer> customers) {
		return assetDao.getAssetCountForManager(user, customers);
	}
            
}
