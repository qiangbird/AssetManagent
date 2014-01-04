package com.augmentum.ams.service.user;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.model.base.PageSize;
import com.augmentum.ams.service.BaseCaseTest;
import com.augmentum.ams.service.search.UserPageSizeService;

public class UserPageSizeServiceTest extends BaseCaseTest{

    @Autowired
    private UserPageSizeService userPageSizeService;
    
    @Test
    public void testGetUserPageSizeService() {
        PageSize pageSize = userPageSizeService.getUserPageSize("T04300", 1);
        Assert.assertEquals(pageSize.getPageSizeValue(), 10);
    }
    
    @Test
    public void testUpdatePageSizeService() {
        PageSize pageSize = userPageSizeService.updateUserPageSize("T04300", 1, 30);
        Assert.assertEquals(pageSize.getPageSizeValue(), 30);
    }
    
}
