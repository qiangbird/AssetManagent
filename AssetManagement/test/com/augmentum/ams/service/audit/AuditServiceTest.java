package com.augmentum.ams.service.audit;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public class AuditServiceTest extends BaseCaseTest {

    @Autowired
    private AuditService auditService;

    private Logger logger = Logger.getLogger(AuditServiceTest.class);
    
    @Test
    public void testFindAssetForInventory() throws BaseException {
    	SearchCondition condition = new SearchCondition();
    	condition.setIsAudited(true);
    	condition.setAuditFileName("2014-01-08_01");
    	condition.setKeyWord("");
        condition.setPageSize(100);
        condition.setPageNum(1);
        
    	Page<Asset> page = auditService.findAssetForInventory(condition);
    	logger.info(page.getResult().size());
    	for (Asset asset : page.getResult()) {
    		logger.info(asset.getAssetName());
    	}
    }
    
}
