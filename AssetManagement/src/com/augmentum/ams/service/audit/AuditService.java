package com.augmentum.ams.service.audit;

import net.sf.json.JSONArray;

import com.augmentum.ams.exception.AuditHandleException;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public interface AuditService {

    /**
     * Calculate percentage by file name
     * @param auditFileName
     * @return
     * @throws AuditHandleException 
     */
    int calculatePercentageByFile(String auditFileName) throws AuditHandleException;

    /**
     * Get the count of the unaudited asset by auditFileName 
     * @param auditFileName
     * @return
     */
    int getUnAuditedCount(String auditFileName);

    /**
     * Get the count of the unaudited asset by auditFileName 
     * @param auditFileName
     * @return
     */
    int getAuditedCount(String auditFileName);

    /**
     * Find audited asset by page 
     * @param auditFileName
     * @return
     */
    JSONArray findAudited(String auditFileName, int iDisplayStart, int iDisplayLength);

    /**
     * Find unaudited asset by page 
     * @param auditFileName
     * @return
     */
    JSONArray findUnAudited(String auditFileName, int iDisplayStart, int iDisplayLength);

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    int getAuditPercentage(String auditFileName);

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    Page<Asset> findAssetForInventory(SearchCondition condition) throws BaseException;
}
