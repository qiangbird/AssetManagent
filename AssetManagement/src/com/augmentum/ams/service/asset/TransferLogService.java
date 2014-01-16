package com.augmentum.ams.service.asset;

import com.augmentum.ams.model.asset.TransferLog;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public interface TransferLogService {
	
	/**
	 * 
	 * @param transferLog
	 */
	void saveTransferLog(TransferLog transferLog);
	/**
	 * 
	 * @param searchCondition
	 * @return
	 */
	Page<TransferLog> findTransferLogBySearchCondition(
			SearchCondition searchCondition);
}
