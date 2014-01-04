package com.augmentum.ams.service.audit;

import net.sf.json.JSONArray;

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

}
