package com.augmentum.ams.service.operationLog.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.operationLog.OperationLogDao;
import com.augmentum.ams.model.operationLog.OperationLog;
import com.augmentum.ams.service.operationLog.OperationLogService;

@Service("operationLogService")
public class OperationLogServiceImpl implements OperationLogService{
	
	@Autowired
	private OperationLogDao operationLogDao;

	@Override
    public void save(OperationLog operationLog) {
		operationLogDao.save(operationLog);
	    
    }

}
