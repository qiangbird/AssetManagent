package com.augmentum.ams.dao.asset.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.TransferLogDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.TransferLog;


@Repository("transferLogDao")
public class TransferLogDaoImpl extends BaseDaoImpl<TransferLog> implements TransferLogDao{

}
