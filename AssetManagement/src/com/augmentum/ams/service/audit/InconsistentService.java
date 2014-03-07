package com.augmentum.ams.service.audit;

import net.sf.json.JSONArray;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.audit.Inconsistent;
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
     * @author Geoffrey.Zhao
     * @return
     */
    Page<Inconsistent> findInconsistentBySearchCondition(SearchCondition condition, String auditFileId) throws BaseException;

}
