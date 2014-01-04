package com.augmentum.ams.dao.search;

import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.base.PageSize;

public interface UserPageSizeDao extends BaseDao<PageSize>{

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    List<PageSize> findPageSizes();
    
    /**
     * @author Geoffrey.Zhao
     * @param categoryFlag
     * @param pageSizeValue
     * @return
     */
    PageSize getPageSize(int categoryFlag, int pageSizeValue);
}
