package com.augmentum.ams.service.audit.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.asset.AssetDao;
import com.augmentum.ams.dao.audit.AuditDao;
import com.augmentum.ams.dao.audit.AuditFileDao;
import com.augmentum.ams.dao.audit.InconsistentDao;
import com.augmentum.ams.exception.AuditHandleException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.audit.Audit;
import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.model.audit.Inconsistent;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.audit.AuditFileService;
import com.augmentum.ams.service.audit.AuditService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.CsvFileParser;
import com.augmentum.ams.util.PropertyParser;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.user.UserVo;

@Service("auditFileService")
public class AuditFileServiceImpl implements AuditFileService {

    private Logger logger = Logger.getLogger(AuditFileServiceImpl.class);

    @Autowired
    private AuditFileDao auditFileDao;

    @Autowired
    private AuditDao auditDao;

    @Autowired
    private AssetDao assetDao;

    @Autowired
    private InconsistentDao inconsistentDao;

    @Autowired
    private AuditService auditService;

    @Autowired
    private UserService userService;

    @Override
    public int checkInventory(UserVo userVoByShiro, File file, String auditFileName)
            throws AuditHandleException {

        // if the file is null
        if (file == null) {
            throw new AuditHandleException("There is no file");
        } else if (("").equals(auditFileName) && auditFileName == null) {
            throw new AuditHandleException("The param auditFileName is null");
        }

        List<String> auditBarCodeList = new ArrayList<String>();
        List<String> csvBarCodeList = new ArrayList<String>();

        // get bar-code from the csv file
        csvBarCodeList = findBarcodeFromFile(csvBarCodeList, file);
        // get bar-code from the database
        AuditFile auditFile = auditFileDao.getAuditFileByFileName(auditFileName);
        auditBarCodeList = findBarCodeFromDatabase(auditBarCodeList, auditFile
                .getId());

        for (int i = 0; i < csvBarCodeList.size(); i++) {
            String csvBarCode = csvBarCodeList.get(i);

            // have the corresponding bar-code
            if (auditBarCodeList.contains(csvBarCode)) {
                updateAudit(auditBarCodeList, csvBarCode, auditFile);
            } else {// bar-code is inconsistent or not.
                updateInconsistent(csvBarCode, auditFile);
            }
        }
        // percentage = calculatePercentage(auditFile);

        return auditService.getAuditPercentage(auditFile.getFileName());
    }

    // update the inconsistent table
    private void updateInconsistent(String csvBarCode, AuditFile auditFile) {

        List<Asset> assetsList = assetDao.findAssetByBarCode(csvBarCode);
        
        if (assetsList.size() > 0) {
            for (int j = 0; j < assetsList.size(); j++) {
                Inconsistent inconsistent = new Inconsistent();
                inconsistent.setAsset(assetsList.get(j));
                inconsistent.setAuditFile(auditFile);
                inconsistent.setBarCode(csvBarCode);
                inconsistentDao.save(inconsistent);
            }
        } else {
            Inconsistent inconsistent = new Inconsistent();
            inconsistent.setBarCode(csvBarCode);
            inconsistent.setAuditFile(auditFile);
            inconsistentDao.save(inconsistent);
        }
    }

    // update the audit table
    private void updateAudit(List<String> auditBarCodeList,
            String csvBarCode, AuditFile auditFile) {

        for (int j = 0; j < auditBarCodeList.size(); j++) {
            String auditBarCode = auditBarCodeList.get(j);
            if (csvBarCode.equals(auditBarCode)) {
                List<Asset> assetsList = assetDao.findAssetByBarCode(auditBarCode);
                logger.info("The count of the " + auditBarCode + " asset is " + assetsList.size());

                for (int z = 0; z < assetsList.size(); z++) {
                    Audit audit = auditDao.getByAssetIdAndFileId(assetsList.get(z).getId(),
                            auditFile.getId());
                    audit.setStatus(true);
                    auditDao.update(audit);
                }
            }
        }
    }

    // find bar-code of the asset which in the database
    private List<String> findBarCodeFromDatabase(List<String> auditBarCodeList,
            String auditFileId) {

        return auditDao.findAssetsBarCodeByFileId(auditFileId);
    }

    // find the bar-code from the uploaded csv file
    private List<String> findBarcodeFromFile(List<String> csvBarCodeList, File file) {

        try {
            CsvFileParser ca = new CsvFileParser();
            List<String[]> strList = ca.readCsvFile(file.toString());
            for (int i = 0; i < strList.size(); i++) {
                String[] str = strList.get(i);
                for (String string : str) {
                    if (string.length() >= 5) {
                        String digit = string.substring(0, 5);
                        String barCodeFormat = PropertyParser.readProperties("barCodeFormat");

                        if (barCodeFormat.contains(digit)
                                && (string.length() == 11 || string.length() == 15)) {
                            csvBarCodeList.add(string);
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        logger.info("CSV file have " + csvBarCodeList.size() + " barcode!");
        return csvBarCodeList;
    }


    @Override
    public Map<AuditFile, Integer> getDoneAuditList() {
    	
    	logger.info("getDoneAuditList start");
        List<AuditFile> auditFiles = auditFileDao.findDoneAuditList();
        logger.info("get auditFile list when getDoneAuditList, list size: " + auditFiles.size());

        Map<AuditFile, Integer> map = new LinkedHashMap<AuditFile, Integer>();
        for (AuditFile auditFile : auditFiles) {
            int percentage = auditService.getAuditPercentage(auditFile.getFileName());
            map.put(auditFile, percentage);

        }
        
        logger.info("getDoneAuditList end, Map<AuditFile, percentage> size: " + map.size());
        return map;
    }

    @Override
    public Map<AuditFile, Integer> getProcessingAuditList() {
    	
    	logger.info("getProcessingAuditList start");
        List<AuditFile> auditFiles = auditFileDao.findProcessingAuditList();
        logger.info("get auditFile list when getProcessingAuditList, list size: " + auditFiles.size());

        Map<AuditFile, Integer> map = new LinkedHashMap<AuditFile, Integer>();
        for (AuditFile auditFile : auditFiles) {
            int percentage = auditService.getAuditPercentage(auditFile.getFileName());
            map.put(auditFile, percentage);

        }
        
        logger.info("getProcessingAuditList end, Map<AuditFile, percentage> size: " + map.size());
        return map;
    }

    @Override
    public int getDoneAuditFilesCount() {
        return auditFileDao.getDoneAuditFilesCount();
    }

    @Override
    public int getProcessingAuditFilesCount() {
        return auditFileDao.getProcessingAuditFilesCount();
    }

    @Override
    public void updateAuditFile(String auditFileName, UserVo userVoByShiro) {

        AuditFile auditFile = auditFileDao.getAuditFileByFileName(auditFileName);
        User user = userService.getUserByUserId(userVoByShiro.getEmployeeId());

        auditFile.setOperationTime(UTCTimeUtil.localDateToUTC());
        auditFile.setOperator(user);

        auditFileDao.update(auditFile);
    }

    @Override
    public void deleteFile(String auditFileName, UserVo userVoByShiro) {

        AuditFile auditFile = auditFileDao.getAuditFileByFileName(auditFileName);
        auditFileDao.delete(auditFile);

        // delete relative audit entity
        List<Audit> auditList = auditDao.findAuditByFileId(auditFile.getId());

        for (int i = 0; i < auditList.size(); i++) {
            auditDao.delete(auditList.get(i));
        }

        // delete relative inconsistent entity
        List<Inconsistent> inconsistentList = inconsistentDao
                .findInconsistentAssetsByFileId(auditFile.getId());
        for (int i = 0; i < inconsistentList.size(); i++) {
            inconsistentDao.delete(inconsistentList.get(i));
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.audit.AuditFileService#getByFileName(java.lang
     * .String)
     */
    @Override
    public AuditFile getByFileName(String auditFileName) {

        AuditFile auditFile = auditFileDao.getAuditFileByFileName(auditFileName);

        return auditFile;
    }

    @Override
    public String getEmployeeNameByFileName(String auditFileName) {
        
        
        return auditFileDao.getEmployeeNameByFileName(auditFileName);
    }

}
