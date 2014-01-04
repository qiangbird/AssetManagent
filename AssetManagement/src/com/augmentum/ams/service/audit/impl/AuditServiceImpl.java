package com.augmentum.ams.service.audit.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.audit.AuditDao;
import com.augmentum.ams.exception.AuditHandleException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.audit.Audit;
import com.augmentum.ams.service.audit.AuditService;

@Service("auditService")
public class AuditServiceImpl implements AuditService {

    private Logger logger = Logger.getLogger(AuditServiceImpl.class);

    @Autowired
    private AuditDao auditDao;

    @Override
    public int calculatePercentageByFile(String auditFileName) throws AuditHandleException {

        int percentage = 0;
        List<Audit> auditLists = null;
        try {
            auditLists = auditDao.findByFileName(auditFileName);
            List<Audit> auditedLists = new ArrayList<Audit>();
            for (Audit audit : auditLists) {
                if (audit.getStatus() == true) {
                    auditedLists.add(audit);
                }
            }
            logger.info("All Audit:" + auditLists.size());
            logger.info("Audited Asset:" + auditedLists.size());
            if (auditedLists.size() == 0) {
                percentage = 0;
            } else {
                percentage = 394 * auditedLists.size() / auditLists.size();

            }
            return percentage;

        } catch (Exception e) {
            throw new AuditHandleException("Calculate Percentage Failure!");
        }
    }

    @Override
    public int getUnAuditedCount(String auditFileName) {

        return auditDao.findUnAuditedAssets(auditFileName).size();
    }

    @Override
    public int getAuditedCount(String auditFileName) {

        return auditDao.findAuditedAssets(auditFileName).size();
    }

    @Override
    public JSONArray findAudited(String auditFileName, int iDisplayStart, int iDisplayLength) {

        List<Asset> auditedAssets = auditDao.findAuditedAssets(auditFileName, iDisplayStart,
                iDisplayLength);
        JSONArray arrays = new JSONArray();

        for (int i = 0; i < auditedAssets.size(); i++) {
            JSONArray array = new JSONArray();
//            Audit audit = auditedAssets.get(i);
            array.add(i + 1 + iDisplayStart);
            if (auditedAssets.get(i).getBarCode() == null) {
                array.add("");
            } else {
                array.add(auditedAssets.get(i).getBarCode());
            }
            array.add(auditedAssets.get(i).getAssetName());
            array.add(auditedAssets.get(i).getType());
            arrays.add(array);
        }
        return arrays;
    }

    @Override
    public JSONArray findUnAudited(String auditFileName, int iDisplayStart, int iDisplayLength) {

        List<Asset> unAuditedAssets = auditDao.findUnAuditedAssets(auditFileName, iDisplayStart,
                iDisplayLength);
        JSONArray arrays = new JSONArray();

        for (int i = 0; i < unAuditedAssets.size(); i++) {
            JSONArray array = new JSONArray();
//            Audit audit = unAuditedAssets.get(i);
            array.add(i + 1 + iDisplayStart);
            if (unAuditedAssets.get(i).getBarCode() == null) {
                array.add("");
            } else {
                array.add(unAuditedAssets.get(i).getBarCode());
            }
            array.add(unAuditedAssets.get(i).getAssetName());
            array.add(unAuditedAssets.get(i).getType());
            arrays.add(array);
        }
        return arrays;
    }

    @Override
    public int getAuditPercentage(String auditFileName) {

        int percentage = 0;
        List<Audit> auditLists = auditDao.findByFileName(auditFileName);
        List<Audit> auditedLists = new ArrayList<Audit>();
        
        for (Audit audit : auditLists) {
            if (audit.getStatus()) {
                auditedLists.add(audit);
            }
        }
        logger.info("All Audit:" + auditLists.size());
        logger.info("Audited Asset:" + auditedLists.size());
        
        if (0 == auditedLists.size()) {
            percentage = 0;
        } else {
            percentage = 100 * auditedLists.size() / auditLists.size();

        }
        return percentage;
    }

}
