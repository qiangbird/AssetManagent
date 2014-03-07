/**
 * 
 */
package com.augmentum.ams.service.search;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

/**
 * @author Grylls.Xu
 * @time Sep 25, 2013 7:40:53 PM
 */
public interface SearchAssetService {

    /**
     * @author Geoffrey.Zhao
     * @param  searchCondition
     * @return
     * @throws BaseException 
     */
    Page<Asset> findAllAssetsBySearchCondition(SearchCondition searchCondition, String type) throws BaseException;

    /**
     * @author Grylls.Xu
     * @time Sep 28, 2013 3:16:15 PM
     * @description TODO
     * @param classes
     */
    void createIndex(Class<Asset>... classes);
    
}
