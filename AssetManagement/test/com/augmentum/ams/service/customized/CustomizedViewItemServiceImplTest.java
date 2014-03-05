/**
 * 
 */
package com.augmentum.ams.service.customized;

import java.util.List;

import junit.framework.Assert;

import org.apache.lucene.search.BooleanQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.customized.CustomizedViewItem;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.web.vo.customized.CustomizedViewVo;

/**
 * @author John.li
 * @time Oct 31, 2013 9:14:38 AM
 */
public class CustomizedViewItemServiceImplTest extends BaseCaseTest {

    @Autowired
    protected HibernateTemplate hibernateTemplate;
    
    @Autowired
    private CustomizedViewItemService customizedViewItemService;
    
    private CustomizedViewVo initCustomizedViewVo() {
        
        String[] columns = {"columns"};
        String[] searchConditions = {"searchConditions"};
        String[] values = {"values"};
        String[] columnTypes = {"values"};
        String[] searchColumns = {"searchColumns"};
        String[] realTables = {"realTables"};
        CustomizedViewVo customizedViewVo =  new CustomizedViewVo("operator", "viewName",
                columns, searchConditions,values, "creatorId", "creatorName",columnTypes, 
                searchColumns, realTables);
        
        return customizedViewVo;
    }

    /**
     * Test method for
     * {@link com.augmentum.ams.service.customized.impl.CustomizedViewItemServiceImpl#saveCusomzieViewItem(com.augmentum.ams.model.customized.CustomizedViewItem)}
     * .
     */
    @Test
    public void testSaveCustomizedViewItem() {

        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewItemService.saveCustomizedViewItem(customizedViewVo);
        
        String sqlStr = " select value from CustomizedViewItem where searchCondition='searchConditions'";
        String value =  (String) hibernateTemplate.find(sqlStr).get(0);
        
        Assert.assertEquals("values", value);
    }

    /**
     * Test method for
     * {@link com.augmentum.ams.service.customized.impl.CustomizedViewItemServiceImpl#deleteCusomzieViewItem(com.augmentum.ams.model.customized.CustomizedViewItem)}
     * .
     */
    @Test
    public void testDeleteCustomizedViewItem() {

        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewItemService.saveCustomizedViewItem(customizedViewVo);
        
        String sqlStr = " select customizedView.id from CustomizedViewItem where searchCondition='searchConditions'";
        customizedViewVo.setCustomizedViewId((String) hibernateTemplate.find(sqlStr).get(0));
        
        customizedViewItemService.deleteCustomizedViewItem(customizedViewVo);

        String sql = " select isExpired from CustomizedViewItem where searchCondition='searchConditions'";
        boolean isEpired =  (Boolean) hibernateTemplate.find(sql).get(0);
        
        Assert.assertTrue(isEpired);
    }

    /**
     * Test method for
     * {@link com.augmentum.ams.service.customized.impl.CustomizedViewItemServiceImpl#updateCusomzieViewItem(com.augmentum.ams.model.customized.CustomizedViewItem)}
     * .
     * @throws BaseException 
     */
    @Test
    public void testUpdateCustomizedViewItem() throws BaseException {
        
        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewItemService.saveCustomizedViewItem(customizedViewVo);
        
        String sql1 = " select customizedView.id from CustomizedViewItem where searchCondition='searchConditions'";
        customizedViewVo.setCustomizedViewId((String) hibernateTemplate.find(sql1).get(0));
        
        String sql2 = " select id from CustomizedViewItem where searchCondition='searchConditions'";
        String[] ids = {(String) hibernateTemplate.find(sql2).get(0)};
        customizedViewVo.setCustomizedViewItemIds(ids);
        
        String[] values = {"newValue"};
        customizedViewVo.setValues(values);
        String[] isDeletes = {"false"};
        customizedViewVo.setIsDeletes(isDeletes);
        
        customizedViewItemService.updateCustomizedViewItem(customizedViewVo);
        
        String sql3 = " select value from CustomizedViewItem where searchCondition='searchConditions'";
        String value = (String) hibernateTemplate.find(sql3).get(0);
        
        Assert.assertEquals("newValue", value);
    }

    /**
     * Test method for
     * {@link com.augmentum.ams.service.customized.impl.CustomizedViewItemServiceImpl#getCustomizedViewItemById(java.lang.String)}
     * .
     */
    @Test
    public void testGetCustomizedViewItemById() {
        
        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewItemService.saveCustomizedViewItem(customizedViewVo);
        
        String sql1 = " select id from CustomizedViewItem where searchCondition='searchConditions'";
        String customizedViewItemId = (String) hibernateTemplate.find(sql1).get(0);
        CustomizedViewItem customizedViewItem = customizedViewItemService
                .getCustomizedViewItemById(customizedViewItemId);
        Assert.assertEquals("columns", customizedViewItem.getColumnName());
    }

    /**
     * Test method for
     * {@link com.augmentum.ams.service.customized.impl.CustomizedViewItemServiceImpl#findByCustomizedViewId(java.lang.String)}
     * .
     */
    @Test
    public void testfindByCustomizedViewId() {
        
        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewItemService.saveCustomizedViewItem(customizedViewVo);
        
        String sql1 = " select customizedView.id from CustomizedViewItem where searchCondition='searchConditions'";
        String customizedViewId = (String) hibernateTemplate.find(sql1).get(0);
        
        List<CustomizedViewItem> customizedViewItems = customizedViewItemService.findByCustomizedViewId(customizedViewId);
        Assert.assertEquals(1, customizedViewItems.size());
    }
    
    @Test
    public void testGetCustomizedViewItemQuery() throws BaseException{
        
        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewItemService.saveCustomizedViewItem(customizedViewVo);
        
        String sqlStr = " select id from CustomizedView where viewName='viewName'";
        String id =  (String) hibernateTemplate.find(sqlStr).get(0);
        
//        BooleanQuery booleanQuery = customizedViewItemService.getCustomizedViewItemQuery(id);
//        
//        Assert.assertNotNull(booleanQuery);
    }

}
