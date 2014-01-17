package com.augmentum.ams.service.audit;

import java.util.Set;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public class InconsistentServiceTest extends BaseCaseTest {

	@Autowired
	private InconsistentService inconsistentService;
	
	private Logger logger = Logger.getLogger(InconsistentService.class);
	
	@Test
	public void testFindInconsistentAssetByFileName() {
		Set<String> list = inconsistentService.findInconsistentAssetByFileName("2014-01-08_01");
		logger.info("inconsistent size: " + list.size());
		int acount = 0;
		int bcount = 0;
		for (String s : list) {
			if (null == s) {
				acount++;
			} 
			if (null != s) {
				bcount++;
				logger.info(s);
			}
		}
		logger.info("asset is null size:" + acount);
		logger.info("asset is not null size: " + bcount);
	}
	
	@Test
	public void findAssetForInconsistent() throws BaseException {
		SearchCondition sc = new SearchCondition();
		sc.setAuditFileName("2014-01-08_01");
		sc.setPageSize(100);
		Page<Asset> page = inconsistentService.findAssetForInconsistent(sc);
		logger.info(page.getAllRecords().size());
		for (Asset asset : page.getResult()) {
			logger.info(asset.getAssetId());
		}
	}
}
