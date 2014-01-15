package com.augmentum.ams.service.search;

import java.util.List;

import junit.framework.Assert;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

public class SearchAssetServiceImplTest extends BaseCaseTest{

    @Autowired
    private SearchAssetService searchAssetService;
    
    @Autowired
    protected SessionFactory sessionFactory;
    
    @Autowired
    private AssetService assetService;
    
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
        searchAssetService.createIndex(clazzes);
    }
    
    @Test
    public void testSearchAllAssetsBySearchCondition() throws BaseException {
        Page<Asset> page = new Page<Asset>();
        
        SearchCondition sc = new SearchCondition();
        sc.setKeyWord("");
        sc.setPageSize(20);
        sc.setPageNum(1);
        
        page = searchAssetService.findAllAssetsBySearchCondition(sc);
        Assert.assertTrue(page.getResult().size() > 0);
        logger.info(page.getResult().size());
        SearchCommonUtil.convertAssetListToJSONArray(page.getResult());
    }
        
}
