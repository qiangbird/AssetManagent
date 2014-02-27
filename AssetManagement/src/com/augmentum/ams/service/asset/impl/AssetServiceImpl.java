package com.augmentum.ams.service.asset.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.augmentum.ams.constants.SystemConstants;
import com.augmentum.ams.dao.asset.AssetDao;
import com.augmentum.ams.dao.audit.AuditDao;
import com.augmentum.ams.dao.audit.AuditFileDao;
import com.augmentum.ams.dao.todo.ToDoDao;
import com.augmentum.ams.excel.AssetTemplateParser;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.exception.SystemException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Device;
import com.augmentum.ams.model.asset.DeviceSubtype;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.asset.Machine;
import com.augmentum.ams.model.asset.Monitor;
import com.augmentum.ams.model.asset.OtherAssets;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.model.asset.PurchaseItem;
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
import com.augmentum.ams.service.asset.PurchaseItemService;
import com.augmentum.ams.service.asset.SoftwareService;
import com.augmentum.ams.service.customized.CustomizedPropertyService;
import com.augmentum.ams.service.customized.PropertyTemplateService;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.search.SearchAssetService;
import com.augmentum.ams.service.search.impl.SearchAssetServiceImpl;
import com.augmentum.ams.service.user.SpecialRoleService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.AssetStatusOperateUtil;
import com.augmentum.ams.util.AssetUtil;
import com.augmentum.ams.util.CommonUtil;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.ExceptionHelper;
import com.augmentum.ams.util.FileOperateUtil;
import com.augmentum.ams.util.FormatUtil;
import com.augmentum.ams.util.RoleLevelUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.AssetVo;
import com.augmentum.ams.web.vo.asset.AssignAssetCondition;
import com.augmentum.ams.web.vo.asset.ExportVo;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
import com.augmentum.ams.web.vo.user.UserVo;

@Service("assetService")
public class AssetServiceImpl extends SearchAssetServiceImpl implements AssetService {

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
    private PurchaseItemService purchaseItemService;
    @Autowired
    private SearchAssetService searchAssetService;

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

        if (operation.equals(SystemConstants.SAVE)) {
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
                            .getDeviceSubtypeByName(assetVo.getDevice().getDeviceSubtype()
                                    .getSubtypeName());

                    if (deviceSubtypesList.size() > 0) {
                        assetVo.getDevice().setDeviceSubtype(deviceSubtypesList.get(0));
                    } else {
                        DeviceSubtype newDeviceSubtype = new DeviceSubtype();
                        newDeviceSubtype.setSubtypeName(assetVo.getDevice().getDeviceSubtype()
                                .getSubtypeName());
                        deviceSubtypeService.saveDeviceSubtype(newDeviceSubtype);
                        assetVo.getDevice().setDeviceSubtype(newDeviceSubtype);
                    }
                    deviceService.saveDevice(assetVo.getDevice());

                } else if (AssetTypeEnum.OTHERASSETS.toString().equals(assetType)) {
                    assetVo.getOtherAssets().setAsset(asset);
                    otherAssetsService.saveOtherAssets(assetVo.getOtherAssets());
                }
            }
            if (!StringUtils.isBlank(assetVo.getPurchaseItemId())) {
                PurchaseItem purchaseItem = purchaseItemService.getPurchaseItemById(assetVo
                        .getPurchaseItemId());
                if (null == purchaseItem) {
                    throw new BusinessException(ErrorCodeUtil.DATA_NOT_EXIST,
                            "The PurchaseITem with id : " + assetVo.getPurchaseItemId()
                                    + " is not exist in the database!");
                } else {
                    purchaseItem.setUsed(Boolean.TRUE);
                    try {
                        purchaseItem.setUsedQuantity(Integer.valueOf(assetVo.getBatchNumber()));
                    } catch (NumberFormatException e) {
                        throw new SystemException(e, ErrorCodeUtil.SYSTEM_ERROR,
                                "The batchCount is not a number!");
                    }
                    purchaseItemService.deletePurchaseItem(purchaseItem);
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
                soft.setManagerVisible(newSoft.isManagerVisible());
                softwareService.updateSoftware(soft);
                asset.setSoftware(soft);
                updateAsset(asset);

            } else {

                updateAsset(asset);

                if (AssetTypeEnum.MACHINE.toString().equals(assetType)) {
                    Machine machine = machineService.getMachineById(assetVo.getMachine().getId());
                    machine.setSubtype(assetVo.getMachine().getSubtype());
                    machine.setSpecification(assetVo.getMachine().getSpecification());
                    machine.setConfiguration(assetVo.getMachine().getConfiguration());
                    machine.setAddress(assetVo.getMachine().getAddress());
                    machineService.updateMachine(machine);

                } else if (AssetTypeEnum.MONITOR.toString().equals(assetType)) {
                    Monitor monitor = monitorService.getMonitorById(assetVo.getMonitor().getId());
                    monitor.setSize(assetVo.getMonitor().getSize());
                    monitor.setDetail(assetVo.getMonitor().getDetail());
                    monitorService.updateMonitor(monitor);

                } else if (AssetTypeEnum.DEVICE.toString().equals(assetType)) {
                    Device device = deviceService.findDeviceById(assetVo.getDevice().getId());
                    device.setConfiguration(assetVo.getDevice().getConfiguration());

                    List<DeviceSubtype> deviceSubtypesList = deviceSubtypeService
                            .getDeviceSubtypeByName(assetVo.getDevice().getDeviceSubtype()
                                    .getSubtypeName());

                    if (deviceSubtypesList.size() > 0) {
                        DeviceSubtype deviceSubtype = deviceSubtypesList.get(0);
                        device.setDeviceSubtype(deviceSubtype);
                    } else {
                        DeviceSubtype newDeviceSubtype = new DeviceSubtype();
                        newDeviceSubtype.setSubtypeName(assetVo.getDevice().getDeviceSubtype()
                                .getSubtypeName());
                        deviceSubtypeService.saveDeviceSubtype(newDeviceSubtype);
                        device.setDeviceSubtype(deviceSubtypeService.getDeviceSubtypeByName(
                                newDeviceSubtype.getSubtypeName()).get(0));
                    }
                    deviceService.updateDevice(device);

                } else if (AssetTypeEnum.OTHERASSETS.toString().equals(assetType)) {
                    OtherAssets otherAssets = otherAssetsService.getOtherAssetsById(assetVo
                            .getOtherAssets().getId());
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
    public List<PropertyTemplate> showOrViewSelfDefinedProperties(Asset asset, String operation)
            throws ParseException {

        @SuppressWarnings("unchecked")
        // TODO: fix bugs when using self property
        List<PropertyTemplate> defaultPropertyTemplatesList = JSONArray.toList(
                propertyTemplateService.findPropertyTemplateByCustomerAndAssetType(asset
                        .getCustomer().getCustomerName(), asset.getType()), PropertyTemplate.class);
        List<PropertyTemplate> propertyTemplatesList = new ArrayList<PropertyTemplate>();

        for (PropertyTemplate pt : defaultPropertyTemplatesList) {
            CustomizedProperty customizedProperty = customizedPropertyService
                    .getByAssetIdAndTemplateId(asset.getId(), pt.getId());
            /*
             * if (null == customizedProperty) { CustomizedProperty
             * newCustomizedProperty = new CustomizedProperty();
             * newCustomizedProperty.setValue(pt.getValue());
             * newCustomizedProperty.setAsset(asset);
             * newCustomizedProperty.setPropertyTemplate(pt);
             * customizedPropertyService
             * .saveCustomizedProperty(newCustomizedProperty);
             * propertyTemplatesList.add(pt); } else { if
             * (operation.equals("show")) { if
             * (!(pt.getPropertyType().equals("selectType"))) {
             * pt.setValue(customizedProperty.getValue()); } } else {
             * pt.setValue(customizedProperty.getValue()); }
             * propertyTemplatesList.add(pt); }
             */
            if (null != customizedProperty) {
                if (operation.equals("show")) {
                    if (!(pt.getPropertyType().equals("selectType"))) {
                        pt.setValue(customizedProperty.getValue());
                    }
                } else {
                    pt.setValue(customizedProperty.getValue());
                }
            }
            propertyTemplatesList.add(pt);
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

    @Override
    public void assetVoToAsset(AssetVo assetVo, Asset asset) {
        try {
            Project project = projectService.getProjectForAsset(assetVo, asset);
            if (null != assetVo.getId()) {
                asset.setId(assetVo.getId());
            }
            User assetUser = null;
            if (null != assetVo.getUser()) {
                assetUser = userService.getUserByName(assetVo.getUser().getUserName());
            }
            // String locationAddr = assetVo.getLocation();
            // Location location = null;
            // try {
            // location = locationService.getLocationBySiteAndRoom(
            // locationAddr.trim().substring(0, 6), locationAddr
            // .trim().substring(6, locationAddr.length()));
            // } catch (Exception e) {
            // logger.error("Get location error!", e);
            // }
            Location location = locationService.getLocationBySiteAndRoom(
                    "Augmentum " + assetVo.getSite(), assetVo.getLocation());
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
            asset.setCheckInTime(UTCTimeUtil.localDateToUTC(assetVo.getCheckInTime()));
            asset.setCheckOutTime(UTCTimeUtil.localDateToUTC(assetVo.getCheckOutTime()));
            asset.setWarrantyTime(UTCTimeUtil.localDateToUTC(assetVo.getWarrantyTime()));
            asset.setVendor(assetVo.getVendor());
            asset.setMemo(assetVo.getMemo());
            // asset.setSoftware(assetVo.getSoftware());
            // if(null != assetVo.getSoftware()){
            // assetVo.getSoftware().setSoftwareExpiredTime(UTCTimeUtil.localDateToUTC(assetVo
            // .getSoftwareExpiredTime()));
            // }
            // asset.getSoftware().setSoftwareExpiredTime((UTCTimeUtil.localDateToUTC(assetVo
            // .getSoftwareExpiredTime())));
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
    public void itAssignAssets(User assigner, AssignAssetCondition condition,
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
                userVo = remoteEmployeeService.getRemoteUserById(userId, request);
                logger.info("judge IAP userVo is null when it assign assets, userVo is null: "
                        + (null == userVo));
            } catch (BusinessException e) {
                logger.error("get userVo from IAP failed when IT assign asset", e);
                // TODO throw IAP exception
            }

            if (null == userService.getUserByUserId(userId)) {
                logger.info("judge DB user is null when it assign assets, user is null: "
                        + (null == userService.getUserByUserId(userId)));
                userService.saveUserAsUserVo(userVo);
            }
            receiver = userService.getUserByUserId(userId);

            if (RoleLevelUtil.getRoleByUserVo(userVo).equals(RoleEnum.MANAGER.name())
                    || specialRoleService.findSpecialRoleByUserId(userId)) {
                assigningFlag = true;
            }
        }

        String[] ids = FormatUtil.splitString(assetIds, SystemConstants.SPLIT_COMMA);
        Map<String, ExceptionHelper> errorCodes = new LinkedHashMap<String, ExceptionHelper>();
        Date date = UTCTimeUtil.localDateToUTC();

        for (String id : ids) {
            Asset asset = assetDao.getAssetById(id);

            if (null == asset) {
                logger.error("asset is null when IT return assets to customer, asset uuid: " + id);
                errorCodes.put(id, new ExceptionHelper(ErrorCodeUtil.DATA_NOT_EXIST));
            } else {

                if (!AssetStatusOperateUtil.canITAssignAsset(asset)) {
                    logger.error("asset status is invalid when IT assign assets, asset status is: "
                            + asset.getStatus());
                    errorCodes.put(id, new ExceptionHelper(ErrorCodeUtil.ASSET_STATUS_INVALID));
                } else {

                    if (assigningFlag) {
                        asset.setStatus(TransientStatusEnum.ASSIGNING.name());
                        asset.setProject(project);
                        asset.setCustomer(customer);
                        asset.setUser(receiver);
                        assetDao.update(asset);

                        // generate todo list for assign to manager operation
                        ToDo todo = new ToDo();
                        todo.setAsset(asset);
                        todo.setReceivedTime(date);
                        todo.setAssigner(assigner);
                        todoDao.save(todo);

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
    public void returnAssetsToCustomer(String assetIds) throws ExceptionHelper {

        logger.info("enter returnAssetsToCustomer service successfully");
        if (null == assetIds || "".equals(assetIds)) {
            logger.error("The param assetIds is null when assets return to Customer");
            throw new ExceptionHelper(ErrorCodeUtil.ASSET_RETURN_FAILED);
        } else {

            String[] assetIdArr = FormatUtil.splitString(assetIds, SystemConstants.SPLIT_COMMA);
            Map<String, ExceptionHelper> errorCodes = new LinkedHashMap<String, ExceptionHelper>();

            for (String id : assetIdArr) {
                Asset asset = assetDao.getAssetById(id);

                if (null == asset) {
                    logger.error("asset is null when return assets to customer, asset uuid: " + id);
                    errorCodes.put(id, new ExceptionHelper(ErrorCodeUtil.DATA_NOT_EXIST));
                } else {
                    if (!AssetStatusOperateUtil.canITReturnToCustomer(asset)) {
                        logger.error("asset status is invalid when IT return assets to customer, asset uuid: "
                                + id);
                        errorCodes.put(id, new ExceptionHelper(ErrorCodeUtil.ASSET_STATUS_INVALID));
                    } else {
                        asset.setStatus(StatusEnum.RETURNED.name());
                        asset.setUser(null);
                        assetDao.update(asset);
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
            throw new ExceptionHelper(ErrorCodeUtil.ASSET_CHANGE_TO_FIXED_FAILED);
        } else {

            String[] assetIdArr = FormatUtil.splitString(assetIds, SystemConstants.SPLIT_COMMA);
            Map<String, ExceptionHelper> errorCodes = new LinkedHashMap<String, ExceptionHelper>();

            for (String assetId : assetIdArr) {
                Asset asset = assetDao.getAssetById(assetId);

                if (null == asset) {
                    logger.error("asset is null when return assets to customer, asset uuid: "
                            + assetId);
                    errorCodes.put(assetId, new ExceptionHelper(ErrorCodeUtil.DATA_NOT_EXIST));
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
            throw new ExceptionHelper(ErrorCodeUtil.ASSET_CHANGE_TO_NONFIXED_FAILED);
        } else {

            String[] assetIdArr = FormatUtil.splitString(assetIds, SystemConstants.SPLIT_COMMA);
            Map<String, ExceptionHelper> errorCodes = new LinkedHashMap<String, ExceptionHelper>();

            for (String assetId : assetIdArr) {
                Asset asset = assetDao.getAssetById(assetId);

                if (null == asset) {
                    logger.error("asset is null when return assets to customer, asset uuid: "
                            + assetId);
                    errorCodes.put(assetId, new ExceptionHelper(ErrorCodeUtil.DATA_NOT_EXIST));
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
    public void addAssetsToAuditForSearchResult(SearchCondition condition) throws ExceptionHelper {

        logger.info("enter addAssetsToAuditForSearchResult service successfully");
        Page<Asset> page = searchAssetService.findAllAssetsBySearchCondition(condition);
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
    public void addAssetsToAuditForSelected(String assetIds) throws ExceptionHelper {

        logger.info("enter addAssetsToAuditForSelected service successfully");
        if (null == assetIds || "".equals(assetIds)) {
            logger.error("The param assetIds is null when add assets to audit for selected");
            throw new ExceptionHelper(ErrorCodeUtil.ASSET_ADD_TO_AUDIT_FAILED);
        } else {

            String[] assetIdArr = FormatUtil.splitString(assetIds, SystemConstants.SPLIT_COMMA);

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
                    errorCodes.put(assetId, new ExceptionHelper(ErrorCodeUtil.DATA_NOT_EXIST));
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
    public void uploadAndDisplayImage(MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) {

        FileOperateUtil.upload(request, response, file);
        String pathName = request.getContextPath() + "/upload/" + file.getOriginalFilename();
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
    public String exportAssetsByIds(String assetIds, HttpServletRequest request)
            throws ExcelException, SQLException {

        String[] assetIdArr = FormatUtil.splitString(assetIds, SystemConstants.SPLIT_COMMA);
        List<ExportVo> exportVos = assetDao.findAssetsByIdsForExport(assetIdArr);

        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();

        return assetTemplateParser.parse(exportVos, request);
    }

    @Override
    public String exportAssetsForAll(SearchCondition condition, HttpServletRequest request)
            throws ExcelException, SQLException {

        Page<Asset> page = searchAssetService.findAllAssetsBySearchCondition(condition);
        if (null == page || null == page.getAllRecords() || 0 == page.getAllRecords().size()) {
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
            List<ExportVo> exportVos = assetDao.findAssetsByIdsForExport(assetIdArr);
            AssetTemplateParser assetTemplateParser = new AssetTemplateParser();

            return assetTemplateParser.parse(exportVos, request);
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

    @Override
    public JSONArray findIdleAssetForPanel(List<Customer> customers) {

        List<Asset> list = assetDao.findIdleAssetForPanel(customers);

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size() && i < 3; i++) {

            JSONObject obj = new JSONObject();
            Asset asset = list.get(i);

            obj.put("id", asset.getId());
            obj.put("assetName", asset.getAssetName());
            if (null == asset.getCustomer()) {
                obj.put("customerName", "");
            } else {
                obj.put("customerName", asset.getCustomer().getCustomerName());
            }

            obj.put("keeper", asset.getKeeper());
            jsonArray.add(obj);
        }
        return jsonArray;
    }

    @Override
    public JSONArray findWarrantyExpiredAssetForPanel(String clientTimeOffset) {

        List<Asset> list = assetDao.findWarrantyExpiredAssetForPanel();

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size() && i < 3; i++) {

            JSONObject obj = new JSONObject();
            Asset asset = list.get(i);

            obj.put("id", asset.getId());
            obj.put("assetId", asset.getAssetId());
            obj.put("assetName", asset.getAssetName());

            if (null == asset.getCustomer()) {
                obj.put("customerName", "");
            } else {
                obj.put("customerName", asset.getCustomer().getCustomerName());
            }

            if (null == asset.getWarrantyTime()) {
                obj.put("warrantyTime", "");
            } else {
                obj.put("warrantyTime", UTCTimeUtil.utcToLocalTime(asset.getWarrantyTime(),
                        clientTimeOffset, SystemConstants.DATE_DAY_PATTERN));
            }

            jsonArray.add(obj);
        }
        return jsonArray;
    }

}