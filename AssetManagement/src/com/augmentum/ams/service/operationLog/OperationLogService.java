package com.augmentum.ams.service.operationLog;

import com.augmentum.ams.model.operationLog.OperationLog;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public interface OperationLogService {

	/**
	 * 
	 * @param operationLog
	 */
	void save(OperationLog operationLog);
	/**
	 * 
	 * @param searchCondition
	 * @param id
	 * @return
	 */
	Page<OperationLog> findOperationLogBySearchCondition(
			SearchCondition searchCondition);
}
