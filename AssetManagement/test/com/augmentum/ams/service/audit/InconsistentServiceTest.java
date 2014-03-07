package com.augmentum.ams.service.audit;

import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.dao.audit.InconsistentDao;
import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.model.audit.Inconsistent;
import com.augmentum.ams.service.BaseCaseTest;

public class InconsistentServiceTest extends BaseCaseTest {

	@Autowired
	private InconsistentService inconsistentService;
	@Autowired
	private InconsistentDao inconsistentDao;
	@Autowired
	private BaseHibernateDao<Inconsistent> baseHibernateDao;
	
	private Logger logger = Logger.getLogger(InconsistentService.class);
	
	@Test
	public void testFindInconsistentAssets() {
		JSONArray json = inconsistentService.findInconsistentAssets("2014-01-08_01", 0, 100);
		logger.info(json.size() + "---" + json);
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
