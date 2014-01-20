package com.augmentum.ams.service.audit;

import java.util.Set;

import net.sf.json.JSONArray;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public interface InconsistentService {

    /**
     * Get the inconsistent asset by auditFileName
     * @param auditFileName
     * @return
     */
    int getInconsistentAssetsCount(String auditFileName);

    /**
     * Find the inconsistent asset by page
     * @param auditFileName
     * @return
     */
    JSONArray findInconsistentAssets(String auditFileName, int iDisplayStart,
            int iDisplayLength);
    
    /**
     * find inconsistent asset by file name
     * @param fileName
     * @return
     */
    Set<String> findInconsistentAssetByFileName(String fileName);

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    Page<Asset> findAssetForInconsistent(SearchCondition condition) throws BaseException;
    
}
