package com.augmentum.ams.dao.audit;

import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.audit.Inconsistent;

public interface InconsistentDao extends BaseDao<Inconsistent>{

    /**
     * Find inconsistent assets by file id
     * @param auditFileName
     * @return
     */
    List<Inconsistent> findInconsistentAssetsByFileId(String fileId);

    /**
     * find inconsistent asset by audit file name
     * @author Geoffrey.Zhao
     * @param fileName
     * @return
     */
    List<String> findInconsistentAssetByFileName(String fileName);

    /** find barcode in inconsistent
     * @author Geoffrey.Zhao
     * @param fileName
     * @return
     */
    List<String> findInconsistentBarcode(String fileName);
    
    /**
     * @author Geoffrey.Zhao
     * @return
     */
    List<Inconsistent> findInconsistentList(String fileName, int iDisplayStart,
            int iDisplayLength);
    
}
