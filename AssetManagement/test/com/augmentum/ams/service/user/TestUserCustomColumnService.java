package com.augmentum.ams.service.user;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.service.search.UserCustomColumnsService;

public class TestUserCustomColumnService extends BaseCaseTest{

    @Autowired
    private UserCustomColumnsService userCustomColumnsService;
    
    @Test
    public void testGetUserCustomColumns() {
        
        List<UserCustomColumn> userCustomColumns = userCustomColumnsService.findUserCustomColumns("asset", "T04300");
        Assert.assertTrue(userCustomColumns.size() == 21);
    }        
    
    @Test
    public void testUpdateCustomizedColumns() {
        String userId = "T04300";
        Map<String, Boolean> customizedColumnIds = new LinkedHashMap<String, Boolean>();
        customizedColumnIds.put("4028961242d0a7f20142d0a8fa8d0000", false);
        userCustomColumnsService.updateCustomizedColumns(customizedColumnIds, userId);

        List<UserCustomColumn> userCustomColumns = userCustomColumnsService.findUserCustomColumns("asset", userId);
        Assert.assertFalse(userCustomColumns.get(0).getShowDefault());
    }
    
    @Test
    public void testInitUseCustomColumn() {
    	String userId = "T00245";
    	userCustomColumnsService.initUserCustomColumn(userId);
    }
    
}
