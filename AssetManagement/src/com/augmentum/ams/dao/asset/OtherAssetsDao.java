package com.augmentum.ams.dao.asset;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.asset.OtherAssets;

public interface OtherAssetsDao extends BaseDao<OtherAssets>{

    /**
     * @author John.Li
     * @param id
     * @return
     */
    OtherAssets getOtherAssetsById(String id);

}
