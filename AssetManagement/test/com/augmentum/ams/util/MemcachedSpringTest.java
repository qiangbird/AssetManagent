package com.augmentum.ams.util;

import junit.framework.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.service.BaseCaseTest;
import com.danga.MemCached.MemCachedClient;

public class MemcachedSpringTest extends BaseCaseTest {
    
    @Autowired
    private MemCachedClient memcachedClient;  
    
    @Test  
    public void testMemcachedUtil() {  
        
        MemcachedUtil.put("hello", "world", 60);  
        String hello = (String) MemcachedUtil.get("hello");
        
        Assert.assertEquals("world", hello);  
    } 
    
    @Test  
    public void testMemcachedSpring() {  
        
        memcachedClient.set("hello", "world", 60);  
        String hello = (String) memcachedClient.get("hello");  
        
        Assert.assertEquals("world", hello);  
    }  
}
