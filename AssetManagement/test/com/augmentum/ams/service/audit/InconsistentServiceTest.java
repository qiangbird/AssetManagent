package com.augmentum.ams.service.audit;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.dao.audit.InconsistentDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.audit.Inconsistent;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public class InconsistentServiceTest extends BaseCaseTest {

	@Autowired
	private InconsistentService inconsistentService;
	@Autowired
	private InconsistentDao inconsistentDao;
	@Autowired
	private BaseHibernateDao<Inconsistent> baseHibernateDao;
	
	private Logger logger = Logger.getLogger(InconsistentService.class);
	
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
	
	@Test
	public void testFindInconsistentAssets() {
		JSONArray json = inconsistentService.findInconsistentAssets("2014-01-08_01", 0, 100);
		logger.info(json.size() + "---" + json);
	}
	
	@Test
	public void testFindInconsistentBarcode() throws BaseException {
		SearchCondition sc = new SearchCondition();
		sc.setSortName("barcode");
		sc.setSortSign("asc");
		sc.setAuditFileName("2014-01-08_01");
		sc.setPageSize(100);
//		sc.setKeyWord("0110102");
		
		Page<Inconsistent> page = inconsistentService.findInconsistentBarcode(sc);
		for (Inconsistent incons : page.getResult()) {
			logger.info(page.getResult() + "---" +incons.getBarCode());
		}
	}
	
	@Test
	public void testFindInconsistentList() {
		JSONArray array = inconsistentService.findInconsistentAssets("2014-01-08_01", 0, 100);
		logger.info(array.size());
		for (int i = 0 ; i < array.size(); i++) {
			logger.info(array.get(i));
		}
	}
	
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateIndex() {
        String hql = "FROM Inconsistent";
        List<Inconsistent> list = (List<Inconsistent>)inconsistentDao.getHibernateTemplate().find(hql);
        Class<Inconsistent>[] clazzes = new Class[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Inconsistent inconsistent = list.get(i);
            Class clazz =  inconsistent.getClass();
            clazzes[i] = clazz;
        }
        try {
            baseHibernateDao.createIndex(clazzes);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
