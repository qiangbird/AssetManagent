package com.augmentum.ams.service.asset.impl;

import java.io.File;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.AssetDao;
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
import com.augmentum.ams.model.enumeration.AssetTypeEnum;
import com.augmentum.ams.model.enumeration.StatusEnum;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.asset.AssetImportParserService;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.asset.DeviceService;
import com.augmentum.ams.service.asset.DeviceSubtypeService;
import com.augmentum.ams.service.asset.MachineService;
import com.augmentum.ams.service.asset.MonitorService;
import com.augmentum.ams.service.asset.OtherAssetsService;
import com.augmentum.ams.service.asset.ProjectService;
import com.augmentum.ams.service.asset.SoftwareService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.MemcachedUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.ImportVo;

@Service("assetImportParserService")
public class AssetImportParserServiceImpl implements AssetImportParserService{

    @Autowired
    private AssetDao assetDao;
    @Autowired
    private SoftwareService softwareService;
    @Autowired
    private MachineService machineService;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private DeviceSubtypeService deviceSubtypeService;
    @Autowired
    private MonitorService monitorService;
    @Autowired
    private OtherAssetsService otherAssetsService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;

    private static Logger logger = Logger.getLogger(AssetImportParserService.class);

    @Override
    public ImportVo importAsset(File file, HttpServletRequest request, String flag)
            throws ExcelException, DataException {

        ExcelUtil excelUtil = ExcelUtil.getInstance();
        Workbook workbookImport = null;

        // Get the uploaded file
        try {
            workbookImport = excelUtil.getWorkbook(file);
        } catch (Exception e) {
            logger.error("Get import excel error!", e);
            throw new ExcelException(ErrorCodeUtil.ASSET_IMPORT_FAILED);
        }
        // Get the template file
        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
        Workbook workbookTemplate = assetTemplateParser
                .getWorkbook(AssetTemplateParser.ASSET_TEMPLATE);

        // Create the excel file that contains the illegal assets
        String outPutPath = assetTemplateParser.getOutputPath("Asset",
                UTCTimeUtil.formatDateToString(new Date(), Constant.FILTER_TIME_PATTERN));
        WritableWorkbook writableWorkbook = ExcelBuilder.createWritableWorkbook(outPutPath,
                workbookTemplate);

        // Check the title of the sheet
        ImportVo importVo = checkSheetTitle(workbookImport, workbookTemplate, writableWorkbook);

        // Begin to import the asset to the database
        importVo = importAsset(importVo, excelUtil, writableWorkbook, flag);

        // Close the resource
        Workbook[] workbooks = { workbookImport, workbookTemplate };
        closeWorkbook(workbooks, writableWorkbook, importVo.getFailureRecords());
        
        // Save the path of failure excel file which records the illegal assets
        File failureFile = new File(outPutPath);
        importVo.setFailureFileName(failureFile.getName());

        return importVo;
    }

    private ImportVo checkSheetTitle(Workbook workbookImport, Workbook workbookTemplate,
            WritableWorkbook writableWorkbook) throws ExcelException {

        ExcelUtil excelUtil = ExcelUtil.getInstance();
        ImportVo importVo = new ImportVo();
        int allImportRecords = 0;

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

        logger.info("-----------The machine type totally has：" + (machineTotalRecords - 1)
                + " records-----------");
        logger.info("-----------The monitor type totally has：" + (monitorTotalRecords - 1)
                + " records-----------");
        logger.info("-----------The device type totally has：" + (deviceTotalRecords - 1)
                + " records-----------");
        logger.info("-----------The software type totally has：" + (softwareTotalRecords - 1)
                + " records-----------");
        logger.info("-----------The otherAsset type totally has：" + (otherAssetTotalRecords - 1)
                + " records-----------");

        allImportRecords = machineTotalRecords + monitorTotalRecords + deviceTotalRecords
                + softwareTotalRecords + otherAssetTotalRecords - 5;

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

        importVo.setMachineSheet(machineSheet);
        importVo.setMonitorSheet(monitorSheet);
        importVo.setDeviceSheet(deviceSheet);
        importVo.setSoftwareSheet(softwareSheet);
        importVo.setOtherAssetsSheet(otherAssetsSheet);
        importVo.setMachineTotalRecords(machineTotalRecords);
        importVo.setMonitorTotalRecords(monitorTotalRecords);
        importVo.setDeviceTotalRecords(deviceTotalRecords);
        importVo.setSoftwareTotalRecords(softwareTotalRecords);
        importVo.setOtherAssetTotalRecords(otherAssetTotalRecords);
        importVo.setAllImportRecords(allImportRecords);

        return importVo;
    }

    private void compareCellTitle(Cell[] template, Cell[] upload, String compareType)
            throws ExcelException {

        //check length of title
        if (template.length != upload.length) {
            
            throw new ExcelException(ErrorCodeUtil.ASSET_IMPORT_FAILED, compareType + " type title length not match! " +
            		"For asset template is: " + template.length + 
                    ", and for upload file is: " + upload.length);
        }
        
        //check content of title
        for (int i = 0; i < upload.length; i++) {
            if (!template[i].getContents().equals(upload[i].getContents())) {

                throw new ExcelException(ErrorCodeUtil.ASSET_IMPORT_FAILED, compareType + " type title not match! " +
                		"The column of template is: " + template[i].getContents() + 
                        " and for import file is: " +  upload[i].getContents());
            }
        }
    }

    private ImportVo importAsset(ImportVo importVo, ExcelUtil excelUtil,
            WritableWorkbook writableWorkbook, String flag)
            throws ExcelException {

        int successRecords = 0;
        int failureRecords = 0;
        int failureMachineRecords = 0;
        int failureMonitorRecords = 0;
        int failureDeviceRecords = 0;
        int failureSoftwareRecords = 0;
        int failureOtherAssetRecords = 0;

        Sheet machineSheet = importVo.getMachineSheet();
        Sheet monitorSheet = importVo.getMonitorSheet();
        Sheet deviceSheet = importVo.getDeviceSheet();
        Sheet softwareSheet = importVo.getSoftwareSheet();
        Sheet otherAssetsSheet = importVo.getOtherAssetsSheet();

        int machineTotalRecords = importVo.getMachineTotalRecords();
        int monitorTotalRecords = importVo.getMonitorTotalRecords();
        int deviceTotalRecords = importVo.getDeviceTotalRecords();
        int softwareTotalRecords = importVo.getSoftwareTotalRecords();
        int otherAssetTotalRecords = importVo.getOtherAssetTotalRecords();

        if (1 < machineTotalRecords) {
            failureMachineRecords = setExcelToMachine(machineSheet, machineTotalRecords, excelUtil,
                    writableWorkbook, flag);
        }
        if (1 < monitorTotalRecords) {
            failureMonitorRecords = setExcelToMonitor(monitorSheet, monitorTotalRecords, excelUtil,
                    writableWorkbook, flag);
        }
        if (1 < deviceTotalRecords) {
            failureDeviceRecords = setExcelToDevice(deviceSheet, deviceTotalRecords, excelUtil,
                    writableWorkbook, flag);
        }
        if (1 < softwareTotalRecords) {
            failureSoftwareRecords = setExcelToSoftware(softwareSheet, softwareTotalRecords,
                    excelUtil, writableWorkbook, flag);
        }
        if (1 < otherAssetTotalRecords) {
            failureOtherAssetRecords = setExcelToOtherAsset(otherAssetsSheet,
                    otherAssetTotalRecords, excelUtil, writableWorkbook, flag);
        }

        failureRecords = failureMachineRecords + failureMonitorRecords + failureDeviceRecords
                + failureSoftwareRecords + failureOtherAssetRecords;
        successRecords = importVo.getAllImportRecords() - failureRecords;

        importVo.setFailureRecords(failureRecords);
        importVo.setSuccessRecords(successRecords);

        return importVo;
    }

    private int setExcelToMachine(Sheet machineSheet, int machineTotalRecords, ExcelUtil excelUtil,
            WritableWorkbook writableWorkbook, String flag)
            throws ExcelException {

        int failureRecords = 0;

        for (int i = 1; i < machineTotalRecords; i++) {
            Cell[] currentRowCells = excelUtil.getRows(machineSheet, i);
            Machine machine = new Machine();

            ImportVo importVo = setExcelToAsset(currentRowCells, flag);
            boolean emptySubtype = Boolean.FALSE;

            if (null == currentRowCells[22].getContents()
                    || "".equals(currentRowCells[22].getContents())) {
                emptySubtype = Boolean.TRUE;
            }
            if (Constant.UPDATE.equals(flag) && !importVo.isErrorRecorde() && !emptySubtype
                    && AssetTypeEnum.MACHINE.toString().equals(importVo.getAsset().getType())) {

                machine = machineService.getByAssetId(importVo.getAsset().getId());
            }

            machine.setSubtype(currentRowCells[22].getContents());
            machine.setSpecification(currentRowCells[23].getContents());
            machine.setAddress(currentRowCells[24].getContents());
            machine.setConfiguration(currentRowCells[25].getContents());

            if (importVo.isErrorRecorde() || emptySubtype
                    || !AssetTypeEnum.MACHINE.name().equals(importVo.getAsset().getType())) {

                WritableSheet sheet = writableWorkbook.getSheet("Machine");
                int errorRecords = excelUtil.getRows(sheet);
                int column = setErrorCommonAsset(importVo.getAsset(), sheet, errorRecords);
                sheet = setErrorMachine(machine, sheet, column, errorRecords);
                failureRecords++;
            } else {
                if (Constant.CREATE.equals(flag)) {
                    Asset newAsset = assetDao.save(importVo.getAsset());
                    machine.setAsset(newAsset);
                    machineService.saveMachine(machine);
                } else {
                    Asset newAsset = assetDao.update(importVo.getAsset());
                    machine.setAsset(newAsset);
                    machineService.updateMachine(machine);
                }
            }
        }
        return failureRecords;
    }

    private int setExcelToMonitor(Sheet monitorSheet, int monitorTotalRecords, ExcelUtil excelUtil,
            WritableWorkbook writableWorkbook, String flag)
            throws ExcelException {

        int failureRecords = 0;

        for (int i = 1; i < monitorTotalRecords; i++) {
            Cell[] currentRowCells = excelUtil.getRows(monitorSheet, i);

            ImportVo importVo = setExcelToAsset(currentRowCells, flag);
            Monitor monitor = new Monitor();

            if (Constant.UPDATE.equals(flag) && !importVo.isErrorRecorde()
                    && AssetTypeEnum.MONITOR.name().equals(importVo.getAsset().getType())) {

                monitor = monitorService.getByAssetId(importVo.getAsset().getId());
            }

            monitor.setSize(currentRowCells[22].getContents());
            monitor.setDetail(currentRowCells[23].getContents());

            if (importVo.isErrorRecorde()
                    || !AssetTypeEnum.MONITOR.name().equals(importVo.getAsset().getType())) {
                WritableSheet sheet = writableWorkbook.getSheet("Monitor");
                int errorRecords = excelUtil.getRows(sheet);
                int column = setErrorCommonAsset(importVo.getAsset(), sheet, errorRecords);
                sheet = setErrorMonitor(monitor, sheet, column, errorRecords);
                failureRecords++;
            } else {
                if (Constant.CREATE.equals(flag)) {
                    Asset newAsset = assetDao.save(importVo.getAsset());
                    monitor.setAsset(newAsset);
                    monitorService.saveMonitor(monitor);
                } else {
                    Asset newAsset = assetDao.update(importVo.getAsset());
                    monitor.setAsset(newAsset);
                    monitorService.updateMonitor(monitor);
                }
            }
        }
        return failureRecords;
    }

    private int setExcelToDevice(Sheet deviceSheet, int deviceTotalRecords, ExcelUtil excelUtil,
            WritableWorkbook writableWorkbook, String flag)
            throws ExcelException {

        int failureRecords = 0;

        for (int i = 1; i < deviceTotalRecords; i++) {
            Cell[] currentRowCells = excelUtil.getRows(deviceSheet, i);

            ImportVo importVo = setExcelToAsset(currentRowCells, flag);
            Device device = new Device();
            DeviceSubtype deviceSubtype = new DeviceSubtype();

            if (Constant.UPDATE.equals(flag) && !importVo.isErrorRecorde()
                    && AssetTypeEnum.DEVICE.name().equals(importVo.getAsset().getType())) {

                device = deviceService.getByAssetId(importVo.getAsset().getId());
                deviceSubtype = device.getDeviceSubtype();
            }

            deviceSubtype.setSubtypeName(currentRowCells[22].getContents());
            device.setConfiguration(currentRowCells[23].getContents());
            device.setDeviceSubtype(deviceSubtype);

            if (importVo.isErrorRecorde()
                    || !AssetTypeEnum.DEVICE.name().equals(importVo.getAsset().getType())) {
                WritableSheet sheet = writableWorkbook.getSheet("Device");
                int errorRecords = excelUtil.getRows(sheet);
                int column = setErrorCommonAsset(importVo.getAsset(), sheet, errorRecords);
                sheet = setErrorDevice(device, sheet, column, errorRecords);
                failureRecords++;
            } else {
                if (Constant.CREATE.equals(flag)) {
                    if(null == deviceSubtype.getSubtypeName() || "".equals(deviceSubtype.getSubtypeName())){
                        device.setDeviceSubtype(null);
                    }else{
                        deviceSubtypeService.saveDeviceSubtype(deviceSubtype);
                    }
                    Asset newAsset = assetDao.save(importVo.getAsset());
                    device.setAsset(newAsset);
                    deviceService.saveDevice(device);
                } else {
                    Asset newAsset = assetDao.update(importVo.getAsset());
                    device.setAsset(newAsset);
                    deviceService.updateDevice(device);
                }
            }
        }
        return failureRecords;
    }

    private int setExcelToSoftware(Sheet softwareSheet, int softwareTotalRecords,
            ExcelUtil excelUtil, WritableWorkbook writableWorkbook,
            String flag) throws ExcelException {

        int failureRecords = 0;

        for (int i = 1; i < softwareTotalRecords; i++) {
            Cell[] currentRowCells = excelUtil.getRows(softwareSheet, i);

            Software software = new Software();
            ImportVo importVo = setExcelToAsset(currentRowCells, flag);

            if (Constant.UPDATE.equals(flag) && !importVo.isErrorRecorde()
                    && AssetTypeEnum.SOFTWARE.name().equals(importVo.getAsset().getType())) {

                software = importVo.getAsset().getSoftware();
            }

            software.setVersion(currentRowCells[22].getContents());
            software.setLicenseKey(currentRowCells[23].getContents());
            // TODO check whether the right time format
            software.setSoftwareExpiredTime(UTCTimeUtil.formatStringToDate(currentRowCells[24]
                    .getContents()));

            boolean isNum = currentRowCells[25].getContents().matches("[0-9]+");

            if (isNum) {
                software.setMaxUseNum(Integer.valueOf(currentRowCells[25].getContents()));
            } else {
                importVo.setErrorRecorde(Boolean.TRUE);
            }
            software.setAdditionalInfo(currentRowCells[26].getContents());

            if (importVo.isErrorRecorde()
                    || !AssetTypeEnum.SOFTWARE.name().equals(importVo.getAsset().getType())) {
                WritableSheet sheet = writableWorkbook.getSheet("Software");
                int errorRecords = excelUtil.getRows(sheet);
                int column = setErrorCommonAsset(importVo.getAsset(), sheet, errorRecords);
                sheet = setErrorSoftware(software, sheet, column, errorRecords,
                        currentRowCells[25].getContents());
                failureRecords++;
            } else {
                if (Constant.CREATE.equals(flag)) {
                    softwareService.saveSoftware(software);
                    importVo.getAsset().setSoftware(software);
                    assetDao.save(importVo.getAsset());
                } else {
                    softwareService.updateSoftware(software);
                    importVo.getAsset().setSoftware(software);
                    assetDao.update(importVo.getAsset());
                }
            }
        }
        return failureRecords;
    }

    private int setExcelToOtherAsset(Sheet otherAssetsSheet, int otherAssetTotalRecords,
            ExcelUtil excelUtil, WritableWorkbook writableWorkbook,
            String flag) throws ExcelException {

        int failureRecords = 0;

        for (int i = 1; i < otherAssetTotalRecords; i++) {
            Cell[] currentRowCells = excelUtil.getRows(otherAssetsSheet, i);

            ImportVo importVo = setExcelToAsset(currentRowCells, flag);
            OtherAssets otherAsset = new OtherAssets();

            if (Constant.UPDATE.equals(flag) && !importVo.isErrorRecorde()
                    && AssetTypeEnum.OTHERASSETS.name().equals(importVo.getAsset().getType())) {

                otherAsset = otherAssetsService.getByAssetId(importVo.getAsset().getId());
            }

            otherAsset.setDetail(currentRowCells[22].getContents());

            if (importVo.isErrorRecorde()
                    || AssetTypeEnum.OTHERASSETS.name().equals(importVo.getAsset().getType())) {
                WritableSheet sheet = writableWorkbook.getSheet("OtherAsset");
                int errorRecords = excelUtil.getRows(sheet);
                int column = setErrorCommonAsset(importVo.getAsset(), sheet, errorRecords);
                sheet = setErrorOtherAsset(otherAsset, sheet, column, errorRecords);
                failureRecords++;
            } else {
                if (Constant.CREATE.equals(flag)) {
                    Asset newAsset = assetDao.save(importVo.getAsset());
                    otherAsset.setAsset(newAsset);
                    otherAssetsService.saveOtherAssets(otherAsset);
                } else {
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

    private int setErrorCommonAsset(Asset asset, WritableSheet sheet, int row)
            throws ExcelException {

        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();
        int column = 0;
        
        assetTemplateParser.fillOneCell(column++, row, asset.getAssetId(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getAssetName(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getManufacturer(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getType(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getBarCode(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getCheckInTime(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getCheckOutTime(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getSeriesNo(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getPoNo(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getCustomer().getCustomerName(), sheet);
        
        if(null == asset.getProject()){
            assetTemplateParser.fillOneCell(column++, row, "", sheet);
        }else{
            assetTemplateParser.fillOneCell(column++, row, asset.getProject().getProjectName(), sheet);
        }
        assetTemplateParser.fillOneCell(column++, row, asset.getOwnerShip(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getEntity(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.isFixed(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getLocation().getSite(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getLocation().getRoom(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getUser().getUserName(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getStatus(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getKeeper(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getMemo(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getVendor(), sheet);
        assetTemplateParser.fillOneCell(column++, row, asset.getWarrantyTime(), sheet);

        return column;
    }

    private WritableSheet setErrorMachine(Machine machine, WritableSheet sheet, int column, int row)
            throws ExcelException {

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
            throws ExcelException {

        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();

        assetTemplateParser.fillOneCell(column++, row, device.getDeviceSubtype().getSubtypeName(),
                sheet);
        assetTemplateParser.fillOneCell(column, row, device.getConfiguration(), sheet);

        return sheet;

    }

    private WritableSheet setErrorSoftware(Software software, WritableSheet sheet, int column,
            int row, String maxUseNum) throws ExcelException {

        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();

        assetTemplateParser.fillOneCell(column++, row, software.getVersion(), sheet);
        assetTemplateParser.fillOneCell(column++, row, software.getLicenseKey(), sheet);
        assetTemplateParser.fillOneCell(column++, row, software.getSoftwareExpiredTime(), sheet);
        assetTemplateParser.fillOneCell(column++, row, maxUseNum, sheet);
        assetTemplateParser.fillOneCell(column, row, software.getAdditionalInfo(), sheet);

        return sheet;
    }

    private WritableSheet setErrorOtherAsset(OtherAssets otherAssets, WritableSheet sheet,
            int column, int row) throws ExcelException {

        AssetTemplateParser assetTemplateParser = new AssetTemplateParser();

        assetTemplateParser.fillOneCell(column, row, otherAssets.getDetail(), sheet);

        return sheet;
    }

    private ImportVo setExcelToAsset(Cell[] currentRowCells, String flag) throws ExcelException {

        ImportVo importVo = new ImportVo();

        importVo.setErrorRecorde(Boolean.FALSE);
        importVo.setAsset(new Asset());
        int column = 0;

        importVo = setCommonAssetId(importVo, currentRowCells[column++].getContents(), flag);
        importVo = setCommonAssetName(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetManufacturer(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetType(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetBarCode(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetCheckInTime(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetCheckOutTime(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetSeriesNo(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetPoNo(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetCustomer(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetProject(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetOwnerShip(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetEntity(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetFixed(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetLocation(importVo, currentRowCells[column++].getContents(), 
                currentRowCells[column++].getContents());
        importVo = setCommonAssetUser(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetStatus(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetKeeper(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetMemo(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetVendor(importVo, currentRowCells[column++].getContents());
        importVo = setCommonAssetWarrantyTime(importVo, currentRowCells[column++].getContents());

        return importVo;
    }

    /**************************** SetCommonAssetValue*******Start **********************************/

    private ImportVo setCommonAssetId(ImportVo importVo, String assetId, String flag) {

        if (Constant.CREATE.equals(flag)) {
            if (null != assetId && !"".equals(assetId)) {
                importVo.setErrorRecorde(Boolean.TRUE);
                importVo.getAsset().setAssetId(assetId);
            } else {
                importVo.getAsset()
                        .setAssetId(generateAssetId(assetDao.getTotalCount(Asset.class)));
            }
        } else {
            if (null == assetId || "".equals(assetId)) {
                importVo.setErrorRecorde(Boolean.TRUE);
            } else {
                importVo.setAsset(assetDao.getByAssetId(assetId));

                if (null == importVo.getAsset()) {
                    importVo.setErrorRecorde(Boolean.TRUE);
                    importVo.getAsset().setAssetId(assetId);
                }
            }

        }
        return importVo;
    }

    private ImportVo setCommonAssetName(ImportVo importVo, String assetName) {

        if ("".equals(assetName) || null == assetName) {
            importVo.setErrorRecorde(Boolean.TRUE);
        }
        importVo.getAsset().setAssetName(assetName);

        return importVo;
    }

    private ImportVo setCommonAssetStatus(ImportVo importVo, String assetStatus) {

        boolean isRightStatu = StatusEnum.isRightStatus(assetStatus);

        if ("".equals(assetStatus) || null == assetStatus || !isRightStatu) {
            importVo.setErrorRecorde(Boolean.TRUE);
        }
        importVo.getAsset().setStatus(assetStatus);

        return importVo;
    }

    @SuppressWarnings("unchecked")
    private ImportVo setCommonAssetUser(ImportVo importVo, String assetUser) {

        User user = new User();
        Map<String, String> remoteEmployees = (Map<String, String>) MemcachedUtil
                .get("remoteEmployees");
        Map<String, User> localEmployees = (Map<String, User>) MemcachedUtil.get("localEmployees");

        if ("".equals(assetUser) || null == assetUser) {
            importVo.setErrorRecorde(Boolean.TRUE);
        } else {
            if (localEmployees.containsKey(assetUser)) {
                user = localEmployees.get(assetUser);
            } else if (remoteEmployees.containsKey(assetUser)) {
                user.setUserId(remoteEmployees.get(assetUser));
                user.setUserName(assetUser);
                userService.saveUser(user);
                
                localEmployees.put(user.getUserName(), user);
                MemcachedUtil.replace("localEmployees", localEmployees);
            } else {
                importVo.setErrorRecorde(Boolean.TRUE);
                user.setUserName(assetUser);
                logger.info("The " + assetUser + " user is not exsit in the IAP");
            }
        }
        importVo.getAsset().setUser(user);

        return importVo;
    }

    private ImportVo setCommonAssetKeeper(ImportVo importVo, String assetKeeper) {

        importVo.getAsset().setKeeper(assetKeeper);

        return importVo;
    }

    @SuppressWarnings("unchecked")
    private ImportVo setCommonAssetCustomer(ImportVo importVo, String assetCustomer) {

        Customer customer = new Customer();
        Map<String, Customer> localCustomers = (Map<String, Customer>) MemcachedUtil
                .get("localCustomers");
        Map<String, String> remoteCustomers = (Map<String, String>) MemcachedUtil
                .get("remoteCustomers");

        if ("".equals(assetCustomer) || null == assetCustomer) {
            importVo.setErrorRecorde(Boolean.TRUE);
        } else {
            if (localCustomers.containsKey(assetCustomer)) {
                customer = localCustomers.get(assetCustomer);
            } else if (remoteCustomers.containsKey(assetCustomer)) {
                customer.setCustomerCode(remoteCustomers.get(assetCustomer));
                customer.setCustomerName(assetCustomer);
                customerService.saveCustomer(customer);
                
                localCustomers.put(customer.getCustomerName(), customer);
                MemcachedUtil.replace("localCustomers", localCustomers);
            } else {
                importVo.setErrorRecorde(Boolean.TRUE);
                customer.setCustomerName(assetCustomer);
                logger.info("The " + assetCustomer + " customer is not exsit in the IAP");
            }
        }
        importVo.getAsset().setCustomer(customer);

        return importVo;
    }

    @SuppressWarnings("unchecked")
    private ImportVo setCommonAssetProject(ImportVo importVo, String assetProject) {

        Project project = new Project();
        Map<String, Project> localProjects = (Map<String, Project>) MemcachedUtil.get("localProjects");
        Map<String, String> remoteProjects = (Map<String, String>) MemcachedUtil.get("remoteProjects");

        if(null == assetProject || "".equals(assetProject)){
            project = null;
        }else{
            if (localProjects.containsKey(assetProject)) {
                project = localProjects.get(assetProject);
            } else if (remoteProjects.containsKey(assetProject) && 
                    null != importVo.getAsset().getCustomer().getId()) {
                project.setProjectCode(remoteProjects.get(assetProject));
                project.setProjectName(assetProject);
                // TODO check whether the project belong to the customer?
                project.setCustomer(importVo.getAsset().getCustomer());
                projectService.saveProject(project);
                
                localProjects.put(project.getProjectName(), project);
                MemcachedUtil.replace("localProjects", localProjects);
            } else {
                importVo.setErrorRecorde(Boolean.TRUE);
                project.setProjectName(assetProject);
                logger.info("The " + assetProject + " project is not exsit in the IAP");
            }
        }
        importVo.getAsset().setProject(project);

        return importVo;
    }

    private ImportVo setCommonAssetType(ImportVo importVo, String assetType) {

        boolean isRightType = AssetTypeEnum.isRightType(assetType);

        if ("".equals(assetType) || null == assetType || !isRightType) {
            importVo.setErrorRecorde(Boolean.TRUE);
        }
        importVo.getAsset().setType(assetType);

        return importVo;
    }

    private ImportVo setCommonAssetBarCode(ImportVo importVo, String barCode) {

        importVo.getAsset().setBarCode(barCode);

        return importVo;
    }

    @SuppressWarnings("unchecked")
    private ImportVo setCommonAssetLocation(ImportVo importVo, String assetLocationSite,
            String assetLocationRoom) {

        Location location = new Location();
        Map<String, Location> localLocations = (Map<String, Location>) MemcachedUtil
                .get("localLocations");

        if ("".equals(assetLocationSite) || null == assetLocationSite
                || "".equals(assetLocationRoom) || null == assetLocationRoom) {
            importVo.setErrorRecorde(Boolean.TRUE);
        } else {
            if (localLocations.containsKey(assetLocationSite + 
                    Constant.SPLIT_UNDERLINE + assetLocationRoom)) {
                
                location = localLocations.get(assetLocationSite);
            } else {
                importVo.setErrorRecorde(Boolean.TRUE);
                location.setSite(assetLocationSite);
                location.setRoom(assetLocationRoom);
            }
        }
        importVo.getAsset().setLocation(location);

        return importVo;
    }

    private ImportVo setCommonAssetManufacturer(ImportVo importVo, String manufacturer) {

        importVo.getAsset().setManufacturer(manufacturer);

        return importVo;
    }

    private ImportVo setCommonAssetOwnerShip(ImportVo importVo, String ownerShip) {

        if (null == ownerShip || "".equals(ownerShip)) {
            importVo.setErrorRecorde(Boolean.TRUE);
        }
        importVo.getAsset().setOwnerShip(ownerShip);

        return importVo;
    }

    private ImportVo setCommonAssetEntity(ImportVo importVo, String assetEntity) {

        if (null == assetEntity || "".equals(assetEntity)) {
            importVo.setErrorRecorde(Boolean.TRUE);
        }
        importVo.getAsset().setEntity(assetEntity);

        return importVo;
    }

    private ImportVo setCommonAssetMemo(ImportVo importVo, String assetMemo) {

        importVo.getAsset().setMemo(assetMemo);

        return importVo;
    }

    private ImportVo setCommonAssetFixed(ImportVo importVo, String assetFixed) {

        if (assetFixed.equalsIgnoreCase("yes") || "1".equals(assetFixed)) {
            importVo.getAsset().setFixed(Boolean.TRUE);
        } else {
            importVo.getAsset().setFixed(Boolean.FALSE);
        }

        return importVo;
    }

    private ImportVo setCommonAssetSeriesNo(ImportVo importVo, String assetSeriesNo) {

        importVo.getAsset().setSeriesNo(assetSeriesNo);

        return importVo;
    }

    private ImportVo setCommonAssetPoNo(ImportVo importVo, String assetPoNo) {

        importVo.getAsset().setPoNo(assetPoNo);

        return importVo;
    }

    private ImportVo setCommonAssetCheckInTime(ImportVo importVo, String checkInTime) {

        importVo.getAsset().setCheckInTime(UTCTimeUtil.formatStringToDate(checkInTime));

        return importVo;
    }

    private ImportVo setCommonAssetCheckOutTime(ImportVo importVo, String checkOutTime) {

        importVo.getAsset().setCheckOutTime(UTCTimeUtil.formatStringToDate(checkOutTime));

        return importVo;
    }

    private ImportVo setCommonAssetVendor(ImportVo importVo, String assetVendor) {

        importVo.getAsset().setVendor(assetVendor);

        return importVo;
    }

    private ImportVo setCommonAssetWarrantyTime(ImportVo importVo, String warrantyTime) {

        importVo.getAsset().setWarrantyTime(UTCTimeUtil.formatStringToDate(warrantyTime));

        return importVo;
    }

    /**************************** SetCommonAssetValue******End ***********************************/

    private void closeWorkbook(Workbook[] workbooks, WritableWorkbook writableWorkbook,
            int failureRecords) throws ExcelException {

        for (Workbook workbook : workbooks) {
            workbook.close();
        }
        try {
            if (0 < failureRecords) {
                writableWorkbook.write();
            }
            writableWorkbook.close();
        } catch (Exception e) {
            logger.error("Exception when closing WritableWorkbook.");
            throw new ExcelException(ErrorCodeUtil.ASSET_IMPORT_FAILED);
        }

    }
}
