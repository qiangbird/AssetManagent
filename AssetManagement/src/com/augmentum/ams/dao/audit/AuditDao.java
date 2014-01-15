package com.augmentum.ams.dao.audit;

import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.audit.Audit;

public interface AuditDao extends BaseDao<Audit>{

    /**
     * Find audits by file id
     * @param id
     * @return
     */
    List<Audit> findAuditByFileId(String id);

    /**
     * Get audit entity by asset id
     * @param assetId
     * @param fileId
     * @return
     */
    Audit getByAssetIdAndFileId(String assetId, String fileId);

    /**
     * Find audit by file name
     * @param fileName
     * @return
     */
    List<Audit> findByFileName(String fileName);

    /**
     * Find the unaudited asset by auditFileName
     * @param auditFileName
     * @return
     */
    List<Audit> findUnAuditedAssets(String auditFileName);

    /**
     * Find the audited asset by auditFileName
     * @param auditFileName
     * @return
     */
    List<Audit> findAuditedAssets(String auditFileName);

    /**
     * Find the audited asset by page
     * @param auditFileName
     * @return
     */
    List<Asset> findAuditedAssets(String auditFileName, int iDisplayStart, int iDisplayLength);

    /**
     * Find the unaudited asset by page
     * @param auditFileName
     * @return
     */
    List<Asset> findUnAuditedAssets(String auditFileName, int iDisplayStart, int iDisplayLength);

    /**
     * Find assets bar-code from asset and audit table by audit file id 
     * @param auditFileId
     * @return
     */
    List<String> findAssetsBarCodeByFileId(String auditFileId);
    
    /**
     * @author Geoffrey.Zhao
     * @param isAudited
     * @param auditFileName
     * @return
     */
    List<String> findInventoryAssetId(Boolean isAudited, String auditFileName);

}
