package com.augmentum.ams.dao.asset.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.PurchaseItemDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.PurchaseItem;


@Repository("purchaseItemDao")
public class PurchaseItemDaoImpl extends BaseDaoImpl<PurchaseItem> implements PurchaseItemDao{

}
