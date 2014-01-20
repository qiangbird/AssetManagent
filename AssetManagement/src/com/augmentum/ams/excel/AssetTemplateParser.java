package com.augmentum.ams.excel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jxl.write.WritableSheet;
import org.apache.log4j.Logger;

import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.model.enumeration.AssetTypeEnum;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.asset.ExportVo;

public class AssetTemplateParser extends ExcelParser {

    private static final Logger logger = Logger.getLogger(AssetTemplateParser.class);

    public static final String ASSET_TEMPLATE = "AssetTemplates.xls";

    @SuppressWarnings("unchecked")
    @Override
    public String parse(Collection<?> collection) throws ExcelException {
        
        super.check(collection);
        logger.info("Export excel for asset detail, total: " + collection.size());

        writableWorkbook = ExcelBuilder.createWritableWorkbook(getOutputPath("Asset", UTCTimeUtil
                .formatDateToString(new Date(),Constant.FILTER_TIME_PATTERN)), getWorkbook(ASSET_TEMPLATE));
        fillAssetToTemplate((Collection<ExportVo>) collection);
        super.close();

        return super.outputFile;
    }
    
    private void fillAssetToTemplate(Collection<ExportVo> assets) throws ExcelException {
        
        WritableSheet sheet = null;
        List<ExportVo> machines = new ArrayList<ExportVo>();
        List<ExportVo> monitors = new ArrayList<ExportVo>();
        List<ExportVo> devices = new ArrayList<ExportVo>();
        List<ExportVo> softwares = new ArrayList<ExportVo>();
        List<ExportVo> otherAssets = new ArrayList<ExportVo>();
        

        for (ExportVo asset : assets) {
            // moves the certain sheet to the first place according to assetType 
            if (AssetTypeEnum.MACHINE.toString().equals(asset.getType())) {
                machines.add(asset);
            } else if (AssetTypeEnum.MONITOR.toString().equals(asset.getType())) {
                monitors.add(asset);
            } else if (AssetTypeEnum.DEVICE.toString().equals(asset.getType())) {
                devices.add(asset);
            } else if (AssetTypeEnum.SOFTWARE.toString().equals(asset.getType())) {
                softwares.add(asset);
            } else {
                // otherAssets
                otherAssets.add(asset);
            }
        }
        
        for(int i = 0; i < machines.size(); i++){
            sheet = writableWorkbook.getSheet(0);
            sheet = fillMachineTemlate(sheet, machines.get(i), i + 1);
        }
        for(int i = 0; i < monitors.size(); i++){
            sheet = writableWorkbook.getSheet(1);
            sheet = fillMonitorTemlate(sheet, monitors.get(i), i + 1);
        }
        for(int i = 0; i < devices.size(); i++){
            sheet = writableWorkbook.getSheet(2);
            sheet = fillDeviceTemlate(sheet, devices.get(i), i + 1);
        }
        for(int i = 0; i < softwares.size(); i++){
            sheet = writableWorkbook.getSheet(3);
            sheet = fillSoftwareTemlate(sheet, softwares.get(i), i + 1);
        }
        for(int i = 0; i < otherAssets.size(); i++){
            sheet = writableWorkbook.getSheet(4);
            sheet = fillOtherAssetTemlate(sheet, otherAssets.get(i), i + 1);
        }
    }

    private WritableSheet fillMachineTemlate(WritableSheet sheet, ExportVo asset, int row) throws ExcelException{
        
        if(null == asset){
            return sheet;
        }
        int column = generateCommonValue(row, sheet, asset);
        
        fillOneCell(column++, row, asset.getMachineSubtype(), sheet);
        fillOneCell(column++, row, asset.getMachineSpecification(), sheet);
        fillOneCell(column++, row, asset.getMachineAddress(), sheet);
        fillOneCell(column, row, asset.getMachineConfiguration(), sheet);
        return sheet;
    }

    private WritableSheet fillMonitorTemlate(WritableSheet sheet, ExportVo asset, int row) throws ExcelException{

        if(null == asset){
            return sheet;
        }
        int column = generateCommonValue(row, sheet, asset);
        
        fillOneCell(column++, row, asset.getMonitorSize(), sheet);
        fillOneCell(column, row, asset.getMonitorDetail(), sheet);
        
        return sheet;
    }

    private WritableSheet fillDeviceTemlate(WritableSheet sheet, ExportVo asset, int row) throws ExcelException{

        if(null == asset){
            return sheet;
        }
        int column = generateCommonValue(row, sheet, asset);
        
        fillOneCell(column++, row, asset.getDeviceSubtypeName(), sheet);
        fillOneCell(column, row, asset.getDeviceConfiguration(), sheet);
        
        return sheet;
    }

    private WritableSheet fillSoftwareTemlate(WritableSheet sheet, ExportVo asset, int row) throws ExcelException{

        if(null == asset){
            return sheet;
        }
        int column = generateCommonValue(row, sheet, asset);
        
        fillOneCell(column++, row, asset.getSoftwareVersion(), sheet);
        fillOneCell(column++, row, asset.getSoftwareLicenseKey(), sheet);
        fillOneCell(column++, row, asset.getSoftwareExpiredTime(), sheet);
        fillOneCell(column++, row, asset.getSoftwareMaxUseNum(), sheet);
        fillOneCell(column, row, asset.getSoftwareAdditionalInfo(), sheet);
        
        return sheet;
    }

    private WritableSheet fillOtherAssetTemlate(WritableSheet sheet, ExportVo asset, int row) throws ExcelException{

        if(null == asset){
            return sheet;
        }
        int column = generateCommonValue(row, sheet, asset);
        
        fillOneCell(column, row, asset.getOtherAssetDetail(), sheet);
        
        return sheet;
    }
    
    private int generateCommonValue(int row, WritableSheet sheet, ExportVo asset)
            throws ExcelException {

        if(null == asset){
            return row;
        }
        int column = 0;

        fillOneCell(column++, row, asset.getAssetId(), sheet);
        fillOneCell(column++, row, asset.getAssetName(), sheet);
        fillOneCell(column++, row, asset.getStatus(), sheet);
        fillOneCell(column++, row, asset.getUserName(), sheet);
        fillOneCell(column++, row, asset.getKeeper(), sheet);
        fillOneCell(column++, row, asset.getCustomerName(), sheet);
        fillOneCell(column++, row, asset.getProjectName(), sheet);
        fillOneCell(column++, row, asset.getType(), sheet);
        fillOneCell(column++, row, asset.getBarCode(), sheet);
        fillOneCell(column++, row, asset.getLocationSite() + "_" + asset.getLocationRoom(), sheet);
        fillOneCell(column++, row, asset.getManufacturer(), sheet);
        fillOneCell(column++, row, asset.getOwnerShip(), sheet);
        fillOneCell(column++, row, asset.getEntity(), sheet);
        fillOneCell(column++, row, asset.getMemo(), sheet);
        fillOneCell(column++, row, asset.isFixed(), sheet);
        fillOneCell(column++, row, asset.getSeriesNo(), sheet);
        fillOneCell(column++, row, asset.getPoNo(), sheet);
        fillOneCell(column++, row, asset.getCheckInTime(), sheet);
        fillOneCell(column++, row, asset.getCheckOutTime(), sheet);
        fillOneCell(column++, row, asset.getVendor(), sheet);
        fillOneCell(column++, row, asset.getWarrantyTime(), sheet);
        
        return column;
    }

}
