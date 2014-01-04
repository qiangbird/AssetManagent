package com.augmentum.ams.base;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.augmentum.ams.service.user.UserService;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:appliCationcontext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class BaseShiroTestCase  extends AbstractTransactionalJUnit4SpringContextTests{

    /**
     * The transaction manager and context manager needed in the test.
     */
    public static DefaultWebSecurityManager sm;
    public static Subject subject = null;
    public static HttpServletRequest httpRequest;
    public static HttpServletResponse httpResponse;
    public static ApplicationContext ac;

    @Resource
    public UserService userService;

    /**
     * Setup the context of the test instance before the test.
     * And you can get subject in test environment.
     * 
     * @throws Exception
     */
    @BeforeClass
    public static void setUpSpringContext() throws Exception {
        ac = new ClassPathXmlApplicationContext("classpath:/applicationContext.xml");
        sm = (DefaultWebSecurityManager) ac.getBean("securityManager");
        sm.setSessionManager(new MockShiroWebSessionManager());
        httpRequest = new MockHttpServletRequest();
        httpResponse = new MockHttpServletResponse();
        subject = SecurityUtils.getSubject();

    }


    /**
     * This is used to logout system by shiro after test.
     */
    @AfterClass
    public static void tearDown() {
        Assert.assertNotNull(subject);
        subject.logout();
    }

}
