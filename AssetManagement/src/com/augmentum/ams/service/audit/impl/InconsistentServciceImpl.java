package com.augmentum.ams.service.audit.impl;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.audit.AuditFileDao;
import com.augmentum.ams.dao.audit.InconsistentDao;
import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.model.audit.Inconsistent;
import com.augmentum.ams.service.audit.InconsistentService;

@Service("inconsistentService")
public class InconsistentServciceImpl implements InconsistentService{

    @Autowired
    private InconsistentDao inconsistentDao;
    
    @Autowired
    private AuditFileDao auditFileDao;
    
    @Override
    public int getInconsistentAssetsCount(String auditFileName) {
        
        AuditFile auditFile = auditFileDao.getAuditFileByFileName(auditFileName);
        
        if(null == auditFile){
            return 0;
        }
        
        return inconsistentDao.findInconsistentAssetsByFileId(auditFile.getId()).size();
    }

    /* (non-Javadoc)
     * @see com.augmentum.ams.service.audit.InconsistentService#findInconsistentAssets(java.lang.String, int, int)
     */
    @Override
    public JSONArray findInconsistentAssets(String auditFileName, int iDisplayStart,
            int iDisplayLength) {
        
        AuditFile auditFile = auditFileDao.getAuditFileByFileName(auditFileName);
        JSONArray arrays = new JSONArray();
        
        if(null == auditFile){
            return arrays;
        }
        List<Inconsistent> inconsistentList = inconsistentDao.getInconsistentByFileId(auditFile.getId(),
                iDisplayStart, iDisplayLength);
        
        for (int i = 0; i < inconsistentList.size(); i++) {
            JSONArray array = new JSONArray();
            Inconsistent incon = inconsistentList.get(i);

            if (incon.getAsset() != null) {
                array.add(i + 1 + iDisplayStart);
                if (incon.getAsset().getBarCode() == null) {
                    array.add("");
                } else {
                    array.add(incon.getAsset().getBarCode());
                }
                array.add(incon.getAsset().getAssetName());
                array.add(incon.getAsset().getType());
            } else {
                array.add(i + 1 + iDisplayStart);
                array.add(incon.getBarCode());
                array.add("");
                array.add("");
            }
            arrays.add(array);
        }
        return arrays;
    }
	
}
