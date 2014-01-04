/**
 * 
 */
package com.augmentum.ams.service.customized;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.customized.CustomizedView;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.web.vo.customized.CustomizedViewVo;


/**
 * @author John.li
 * @time Oct 31, 2013 9:14:38 AM
 */
public class CustomizedViewServiceImplTest extends BaseCaseTest {

    @Autowired
    protected HibernateTemplate hibernateTemplate;
    
    @Autowired
    private CustomizedViewService customizedViewService;
    
    private CustomizedViewVo initCustomizedViewVo() {
        
        String[] columns = {"columns"};
        String[] searchConditions = {"searchConditions"};
        String[] values = {"values"};
        String[] columnTypes = {"values"};
        String[] searchColumns = {"searchColumns"};
        String[] realTables = {"realTables"};
        CustomizedViewVo customizedViewVo =  new CustomizedViewVo("operator", "viewName",
                columns, searchConditions,values, "creatorId", "creatorName", columnTypes,
                searchColumns, realTables);
        
        return customizedViewVo;
    }

    /**
     * Test method for {@link com.augmentum.ams.service.customized.impl.CustomizedViewServiceImpl#saveCusomzieView(com.augmentum.ams.model.customized.CustomizedView)}.
     */
    @Test
    public void testSaveCustomizedView() {
        
        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewService.saveCustomizedView(customizedViewVo);
        
        String sqlStr = " select id from CustomizedView where creatorId='creatorId'";
        String id =  (String) hibernateTemplate.find(sqlStr).get(0);
        
        Assert.assertNotNull("customizedView id is null", id);
    }

    /**
     * Test method for {@link com.augmentum.ams.service.customized.impl.CustomizedViewServiceImpl#deleteCusomzieView(com.augmentum.ams.model.customized.CustomizedView)}.
     * @throws BaseException 
     */
    @Test
    public void testDeleteCustomizedView() throws BaseException {
        
        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewService.saveCustomizedView(customizedViewVo);
        
        String sqlStr = " select id from CustomizedView where creatorId='creatorId'";
        customizedViewVo.setCustomizedViewId((String) hibernateTemplate.find(sqlStr).get(0));
        
        customizedViewService.deleteCustomizedView(customizedViewVo);
        
        String sql = " select isExpired from CustomizedView where creatorId='creatorId'";
        boolean isExpired = (Boolean) hibernateTemplate.find(sql).get(0);
        
        Assert.assertTrue(isExpired);
    }

    /**
     * Test method for {@link com.augmentum.ams.service.customized.impl.CustomizedViewServiceImpl#updateCusomzieView(com.augmentum.ams.model.customized.CustomizedView)}.
     * @throws BaseException 
     */
    @Test
    public void testUpdateCustomizedView() throws BaseException {

        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewService.saveCustomizedView(customizedViewVo);
        
        String sqlStr = " select id from CustomizedView where creatorId='creatorId'";
        customizedViewVo.setCustomizedViewId((String) hibernateTemplate.find(sqlStr).get(0));
        
        customizedViewVo.setViewName("updateViewName");
        customizedViewService.updateCustomizedView(customizedViewVo);
        
        String sql1 = " select viewName from CustomizedView where creatorId='creatorId'";
        String viewName = (String) hibernateTemplate.find(sql1).get(0);
        
        Assert.assertEquals("updateViewName", viewName);
    }

    /**
     * Test method for {@link com.augmentum.ams.service.customized.impl.CustomizedViewServiceImpl#getCustomizedViewById(java.lang.String)}.
     * @throws BaseException 
     */
    @Test
    public void testGetCustomizedViewById() throws BaseException {
        
        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewService.saveCustomizedView(customizedViewVo);
        
        String sqlStr = " select id from CustomizedView where creatorId='creatorId'";
        String customizedViewId = (String) hibernateTemplate.find(sqlStr).get(0);
        
        CustomizedView customizedView = customizedViewService.getCustomizedViewById(customizedViewId);
        Assert.assertNotNull("customizedView is null", customizedView);
    }

    /**
     * Test method for {@link com.augmentum.ams.service.customized.impl.CustomizedViewServiceImpl#findCustomizedViewByUser(java.lang.String)}.
     */
    @Test
    public void testFindCustomizedViewByUser() {
        
        CustomizedViewVo customizedViewVo = this.initCustomizedViewVo();
        customizedViewService.saveCustomizedView(customizedViewVo);
        
        List<CustomizedView> customizedViews = customizedViewService.findCustomizedViewByUser("creatorId");
        Assert.assertTrue(1 == customizedViews.size());
    }
    
}
