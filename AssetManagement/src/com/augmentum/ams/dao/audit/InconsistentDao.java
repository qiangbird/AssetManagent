package com.augmentum.ams.dao.audit;

import java.util.List;
import java.util.Set;

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
     * Find inconsistent assets by file id and page
     * @param id
     * @param iDisplayStart
     * @param iDisplayLength
     * @return
     */
    List<Inconsistent> getInconsistentByFileId(String id, int iDisplayStart, int iDisplayLength);
    
    /**
     * find inconsistent asset by audit file name
     * @param fileName
     * @return
     */
    Set<String> findInconsistentAssetByFileName(String fileName);

}
