package com.augmentum.ams.service.asset.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.augmentum.ams.dao.asset.AssetDao;
import com.augmentum.ams.dao.audit.AuditDao;
import com.augmentum.ams.dao.audit.AuditFileDao;
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
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.search.impl.SearchAssetServiceImpl;
import com.augmentum.ams.service.user.SpecialRoleService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.AssetStatusOperateUtil;
import com.augmentum.ams.util.AssetUtil;
import com.augmentum.ams.util.CommonUtil;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.ExceptionHelper;
import com.augmentum.ams.util.FormatUtil;
import com.augmentum.ams.util.RoleLevelUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.AssetVo;
import com.augmentum.ams.web.vo.asset.AssignAssetCondition;
import com.augmentum.ams.web.vo.system.Page;
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
        if (AssetTypeEnum.SOFTWARE.toString().equals(assetVo.getType().trim())) {
            if (operation.equals("save")) {
                softwareService.saveSoftware(assetVo.getSoftware());
                asset.setSoftware(assetVo.getSoftware());
                saveAsset(asset);
            } else {
                Software soft = softwareService.findById(assetVo.getSoftware().getId());
                Software newSoft = assetVo.getSoftware();
                soft.setVersion(newSoft.getVersion());
                soft.setMaxUseNum(newSoft.getMaxUseNum());
                soft.setAdditionalInfo(newSoft.getAdditionalInfo());
                soft.setLicenseKey(newSoft.getLicenseKey());
                softwareService.updateSoftware(soft);
                asset.setSoftware(soft);
                updateAsset(asset);
            }
        } else if (AssetTypeEnum.MACHINE.toString().equals(assetVo.getType().trim())) {
            if (operation.equals("save")) {
                saveAsset(asset);
                assetVo.getMachine().setAsset(asset);
                machineService.saveMachine(assetVo.getMachine());
            } else {
                updateAsset(asset);
                Machine machine = machineService.getMachineById(assetVo.getMachine().getId());
                machine.setSubtype(assetVo.getMachine().getSubtype());
                machine.setSpecification(assetVo.getMachine().getSpecification());
                machine.setConfiguration(assetVo.getMachine().getConfiguration());
                machine.setAddress(assetVo.getMachine().getAddress());
                machineService.updateMachine(machine);
            }
        } else if (AssetTypeEnum.MONITOR.toString().equals(assetVo.getType().trim())) {
            if (operation.equals("save")) {
                saveAsset(asset);
                assetVo.getMonitor().setAsset(asset);
                monitorService.saveMonitor(assetVo.getMonitor());
            } else {
                updateAsset(asset);
                Monitor monitor = monitorService.getMonitorById(assetVo.getMonitor().getId());
                monitor.setSize(assetVo.getMonitor().getSize());
                monitor.setDetail(assetVo.getMonitor().getDetail());
                monitorService.updateMonitor(monitor);
            }
        } else if (AssetTypeEnum.DEVICE.toString().equals(assetVo.getType().trim())) {
            if (operation.equals("save")) {
                saveAsset(asset);
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
            } else {

                updateAsset(asset);
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
            }
        } else if (AssetTypeEnum.OTHERASSETS.toString().equals(assetVo.getType().trim())) {
            if (operation.equals("save")) {
                saveAsset(asset);
                assetVo.getOtherAssets().setAsset(asset);
                otherAssetsService.saveOtherAssets(assetVo.getOtherAssets());
            } else {
                updateAsset(asset);
                OtherAssets otherAssets = otherAssetsService.getOtherAssetsById(assetVo
                        .getOtherAssets().getId());
                otherAssets.setDetail(assetVo.getOtherAssets().getDetail());
                otherAssetsService.updateOtherAssets(otherAssets);
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
        List<PropertyTemplate> defaultPropertyTemplatesList = propertyTemplateService
                .findPropertyTemplateByCustomerAndAssetType(asset.getCustomer().getCustomerName(),
                        asset.getType());
        List<PropertyTemplate> propertyTemplatesList = new ArrayList<PropertyTemplate>();

        for (PropertyTemplate pt : defaultPropertyTemplatesList) {
            CustomizedProperty customizedProperty = customizedPropertyService
                    .getCustomizedPropertyByTemplateId(pt.getId());
            if (null == customizedProperty) {
                CustomizedProperty newCustomizedProperty = new CustomizedProperty();
                newCustomizedProperty.setValue(pt.getValue());
                newCustomizedProperty.setAsset(asset);
                newCustomizedProperty.setPropertyTemplate(pt);
                customizedPropertyService.saveCustomizedProperty(newCustomizedProperty);
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
                assetUser = userService.getUserByName(assetVo.getUser().getUserName());
            }
            String locationAddr = assetVo.getLocation();
            Location location = null;
            try {
                location = locationService.getLocationBySiteAndRoom(locationAddr.trim().substring(
                        0, 6), locationAddr.trim().substring(6, locationAddr.length()));
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
            asset.setCheckInTime(UTCTimeUtil.localDateToUTC(assetVo.getCheckInTime()));
            asset.setCheckOutTime(UTCTimeUtil.localDateToUTC(assetVo.getCheckOutTime()));
            asset.setWarrantyTime(UTCTimeUtil.localDateToUTC(assetVo.getWarrantyTime()));
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
    public void itAssignAssets(AssignAssetCondition condition, HttpServletRequest request)
            throws ExceptionHelper {
        
        String userId = condition.getUserId();
        String assetIds = condition.getAssetIds();
        String projectName = condition.getProjectName();
        String projectCode = condition.getProjectCode();
        String customerCode = condition.getCustomerCode();
        String customerName = condition.getCustomerName();

        if (null == assetIds || "".equals(assetIds)) {
            logger.error("The param assetIds is null when it/manager assign assets!");
        } else if (null == customerName || "".equals(customerName)) {
            logger.error("The param customerName is null when it/manager assign assets!");
        }

        try {
            Customer customer = customerService.getCustomerByCode(customerCode);
            if (customer == null) {
                customer = new Customer();
                customer.setCustomerCode(customerCode);
                customer.setCustomerName(customerName);
                customerService.saveCustomer(customer);
            }

            Project project = null;
            if (null != projectName && !"".equals(projectName)) {
                project = projectService.getProjectByProjectCode(projectCode);

                if (project == null) {
                    project = new Project();
                    project.setProjectCode(projectCode);
                    project.setProjectName(projectName);
                    project.setCustomer(customer);
                    projectService.saveProject(project);
                }
            }

            User receiver = null;
            UserVo userVo = null;
            Boolean assigningFlag = false;
            if (null != userId && !"".equals(userId)) {

                userVo = remoteEmployeeService.getRemoteUserById(userId, request);

                if (null == userService.getUserByUserId(userId)) {
                    userService.saveUserAsUserVo(userVo);
                }
                receiver = userService.getUserByUserId(userId);

                if (RoleLevelUtil.getRoleByUserVo(userVo).name().equals(RoleEnum.MANAGER.name())
                        || specialRoleService.findSpecialRoleByUserId(userId)) {
                    assigningFlag = true;
                }
            }

            String[] ids = FormatUtil.splitString(assetIds, Constant.SPLIT_COMMA);

            for (String id : ids) {
                Asset asset = assetDao.getAssetById(id);

                if (null == asset) {
                    logger.error("asset is null when IT return assets to customer");
                    throw new ExceptionHelper(ErrorCodeUtil.DATA_ASSET_NOT_EXIST);
                } else {

                    if (!AssetStatusOperateUtil.canITAssignAsset(asset)) {
                        logger
                                .error("asset status is invalid when IT assign assets, asset status is: "
                                        + asset.getStatus());
                        throw new ExceptionHelper(ErrorCodeUtil.ASSET_ASSIGN_STATUS_INVALID);
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
        } catch (Exception e) {
            logger.error("IT assign assets failed", e);
            throw new ExceptionHelper(ErrorCodeUtil.ASSET_ASSIGN_FAILED);
        }
    }

    @Override
    public void returnAssetsToCustomer(String assetIds) throws ExceptionHelper {

        if (null == assetIds || "".equals(assetIds)) {
            logger.error("The param assetIds is null when assets return to Customer");
        } else {

            String[] assetIdArr = FormatUtil.splitString(assetIds, Constant.SPLIT_COMMA);
            try {

                for (String assetId : assetIdArr) {
                    Asset asset = assetDao.getAssetById(assetId);

                    if (null == asset) {
                        logger.error("asset is null when return assets to customer");
                        throw new ExceptionHelper(ErrorCodeUtil.DATA_ASSET_NOT_EXIST);
                    } else {
                        if (!AssetStatusOperateUtil.canITReturnToCustomer(asset)) {
                            logger
                                    .error("asset status is invalid when IT return assets to customer");
                            throw new ExceptionHelper(
                                    ErrorCodeUtil.ASSET_RETURNING_TO_CUSTOMER_STATUS_INVALID);
                        } else {
                            asset.setStatus(TransientStatusEnum.RETURNING_TO_CUSTOMER.name());
                            asset.setUser(null);
                            assetDao.update(asset);

                        }
                    }
                }
            } catch (Exception e) {
                logger.error("IT return assets to customer failed", e);
                throw new ExceptionHelper(ErrorCodeUtil.ASSET_RETURNING_TO_CUSTOMER_FAILED);
            }
        }
    }

    @Override
    public void changeAssetsToFixed(String assetIds) throws ExceptionHelper {

        if (null == assetIds || "".equals(assetIds)) {
            logger.error("The param assetIds is null when assets return to Customer");
        } else {

            String[] assetIdArr = FormatUtil.splitString(assetIds, Constant.SPLIT_COMMA);
            try {

                for (String assetId : assetIdArr) {
                    Asset asset = assetDao.getAssetById(assetId);

                    if (null == asset) {
                        logger.error("asset is null when return assets to customer");
                        throw new ExceptionHelper(ErrorCodeUtil.DATA_ASSET_NOT_EXIST);
                    } else {
                        asset.setFixed(Boolean.TRUE);
                        assetDao.update(asset);
                    }
                }
            } catch (ExceptionHelper e) {
                logger.error("change assets to fixed failed", e);
                throw new ExceptionHelper(ErrorCodeUtil.ASSET_CHANGE_TO_FIXED_FAILED);
            }
        }
    }

    @Override
    public void changeAssetsToNonFixed(String assetIds) throws ExceptionHelper {
        if (null == assetIds || "".equals(assetIds)) {
            logger.error("The param assetIds is null when assets return to Customer");
        } else {

            String[] assetIdArr = FormatUtil.splitString(assetIds, Constant.SPLIT_COMMA);
            try {

                for (String assetId : assetIdArr) {
                    Asset asset = assetDao.getAssetById(assetId);

                    if (null == asset) {
                        logger.error("asset is null when return assets to customer");
                        throw new ExceptionHelper(ErrorCodeUtil.DATA_ASSET_NOT_EXIST);
                    } else {
                        asset.setFixed(Boolean.FALSE);
                        assetDao.update(asset);
                    }
                }
            } catch (ExceptionHelper e) {
                logger.error("change assets to fixed failed", e);
                throw new ExceptionHelper(ErrorCodeUtil.ASSET_CHANGE_TO_NOT_FIXED_FAILED);
            }
        }
    }

    @Override
    public void addAssetsToAuditForSearchResult(Page<Asset> page) {

        if (null == page || null == page.getResult() || 0 == page.getResult().size()) {
            logger.error("there is no asset when add assets to audit for search result");
        } else {

            List<String> fileNameList = auditFileDao.getAuditFilesName();
            AuditFile auditFile = new AuditFile();
            auditFile.setFileName(FormatUtil.generateFileName(fileNameList));
            auditFileDao.save(auditFile);

            for (Asset asset : page.getAllRecords()) {

                Audit audit = new Audit();
                audit.setAsset(asset);
                audit.setAuditFile(auditFile);
                audit.setStatus(false);
                auditDao.save(audit);
            }
        }
    }

    @Override
    public void addAssetsToAuditForSelected(String assetIds) {

        if (null == assetIds || "".equals(assetIds)) {
            logger.error("The param assetIds is null when add assets to audit for selected");
        } else {

            String[] assetIdArr = FormatUtil.splitString(assetIds, Constant.SPLIT_COMMA);

            List<String> fileNameList = auditFileDao.getAuditFilesName();
            AuditFile auditFile = new AuditFile();
            auditFile.setFileName(FormatUtil.generateFileName(fileNameList));
            auditFileDao.save(auditFile);

            for (String assetId : assetIdArr) {
                Asset asset = assetDao.getAssetById(assetId);

                if (null == asset) {
                    logger.error("asset is null when add assets to audit for selected");
                } else {

                    Audit audit = new Audit();
                    audit.setAsset(asset);
                    audit.setAuditFile(auditFile);
                    audit.setStatus(false);
                    auditDao.save(audit);
                }
            }
        }
    }

    @Override
    public void uploadAndDisplayImage(MultipartFile file, HttpServletRequest request,
            HttpServletResponse response) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        // request.getSession().getServletContext().getRealPath("C:/AMS/upload");
        //TODO create upload Utils
        String fileName = file.getOriginalFilename();
        File targetFile = new File(path, fileName);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        try {
            file.transferTo(targetFile);
        } catch (Exception e) {
            logger.error("Upload image error!",e);
        }
        String pathName = request.getContextPath() + "/upload/" + fileName;
        // File f=new File("C:/AMS/upload"+fileName);
        StringBuilder sbmsg = new StringBuilder(request.getScheme());
        sbmsg.append("://").append(request.getServerName()).append(":")
                .append(request.getServerPort()).append(pathName);
        try {
            response.getWriter().print(sbmsg);
        } catch (IOException e) {
            logger.error("Get image error!",e);
        }
        
    }
}
