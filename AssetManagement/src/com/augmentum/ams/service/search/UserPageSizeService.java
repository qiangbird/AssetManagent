package com.augmentum.ams.service.search;

import com.augmentum.ams.model.base.PageSize;

/**
 * used to realize user custom page size
 * @author Geoffrey.Zhao
 *
 */
public interface UserPageSizeService {

    /**
     * @param userId
     * @param categoryFlag
     * @return
     */
    PageSize getUserPageSize(String userId, int categoryFlag);
    
    /**
     * @param userId
     * @param categoryFlag
     * @param pageSizeValue
     * @return
     */
    PageSize updateUserPageSize(String userId, int categoryFlag, int pageSizeValue) ;
}
