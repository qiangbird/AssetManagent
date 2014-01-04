/**
 * 
 */
package com.augmentum.ams.service.asset;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.model.asset.CustomizedColumn;
import com.augmentum.ams.service.BaseCaseTest;

/**
 * @author John.Li
 *
 */
public class CustomizedColumnServiceImplTest extends BaseCaseTest{

    @Autowired
    private CustomizedColumnService customizedColumnService;
    /**
     * Test method for {@link com.augmentum.ams.service.asset.impl.CustomizedColumnServiceImpl#findDefaultCustomizedColumns(java.lang.String)}.
     */
    @Test
    public void testGetDefaultCustomizedColumn() {
        String category = "asset";
        List<CustomizedColumn> list = customizedColumnService.findDefaultCustomizedColumns(category);
        System.out.println(list.size());
        Assert.assertEquals(20, list.size());
    }

    /**
     * Test method for {@link com.augmentum.ams.service.asset.impl.CustomizedColumnServiceImpl#getValueOfColumn(java.lang.String, java.lang.String)}.
     */
    @Test
    public void testGetValueOfColumn() {

    String realName = "assetName";
    String realTable = "Asset";
    List<String> values = new ArrayList<String>();
    values = customizedColumnService.getValueOfColumn(realName, realTable);
    Assert.assertTrue("values is null", values.size() > 0);
    }

}
