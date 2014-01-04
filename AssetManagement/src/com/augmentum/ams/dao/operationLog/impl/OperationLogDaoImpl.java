package com.augmentum.ams.dao.operationLog.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.operationLog.OperationLogDao;
import com.augmentum.ams.model.operationLog.OperationLog;


@Repository("operationLogDao")
public class OperationLogDaoImpl extends BaseDaoImpl<OperationLog> implements OperationLogDao{

}
