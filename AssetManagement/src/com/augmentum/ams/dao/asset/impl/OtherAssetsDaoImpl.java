package com.augmentum.ams.dao.asset.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.OtherAssetsDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.OtherAssets;


@Repository("otherAssetsDao")
public class OtherAssetsDaoImpl extends BaseDaoImpl<OtherAssets> implements OtherAssetsDao{

}
