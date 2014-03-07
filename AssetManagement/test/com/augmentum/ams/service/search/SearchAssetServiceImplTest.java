package com.augmentum.ams.service.search;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public class SearchAssetServiceImplTest extends BaseCaseTest{

    @Autowired
    private SearchAssetService searchAssetService;
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    @Autowired
    private AssetService assetService;
    
    @Autowired
    private BaseHibernateDao<Asset> baseHibernateDao;
    
    @SuppressWarnings("unchecked")
    @Test
    public void testCreateIndex() {
        List<Asset> list = assetService.findAllAssets();
        Class<Asset>[] clazzes = new Class[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Asset asset = list.get(i);
            Class clazz =  asset.getClass();
            clazzes[i] = clazz;
        }
        
        try {
            baseHibernateDao.createIndex(clazzes);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    @Test
    public void testSearchAllAssetsBySearchCondition() throws BaseException {
        Page<Asset> page = new Page<Asset>();
        
        SearchCondition sc = new SearchCondition();
        sc.setAssetStatus("in_use");
        sc.setAssetType("device");
//        sc.setKeyWord("in_use");
        sc.setPageSize(1000);
        sc.setPageNum(1);
//        sc.setUserUuid("4028961242e5d4d70142e5d589880000");
        
        page = searchAssetService.findAllAssetsBySearchCondition(sc, "");
        logger.info(page.getResult().size());
    }
    
    @Test
    public void testQueryPerformance() {
    	Criteria criteria = sessionFactory.openSession().createCriteria(Asset.class);
    	criteria.createAlias("user", "user");
    	criteria.add(Restrictions.eq("user.userId", "T04300"));
    	criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
    	@SuppressWarnings("unchecked")
		List<Asset> list = (List<Asset>)criteria.list();
    	for (Asset asset : list) {
    		logger.info(asset.getAssetId() + "---" + asset.getUser().getUserName());
    	}
    }
    
    @Test
    public void testGetWarrantyExpired() {
    	Calendar cal = Calendar.getInstance();
    	logger.info("date: " + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH));
    	String s1 = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
    	logger.info(UTCTimeUtil.localDateToUTC(s1));
    	
    	cal.add(Calendar.DAY_OF_MONTH, 30);
    	logger.info("date: " + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH));
    	String s = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH);
    	logger.info(UTCTimeUtil.formatFilterTime(s));
    }
    
    @Test
    public void testGetAssetExpiredTime() {
    	logger.info(UTCTimeUtil.getAssetExpiredTime());
    }
    
    @Test
    public void testFormatCurrentDateToUTC() {
    	logger.info(UTCTimeUtil.formatCurrentDateToUTC());
    }
        
}
