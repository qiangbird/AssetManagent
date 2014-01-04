package com.augmentum.ams.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * This base class is the common Junit tests' base class, which means that the
 * Junit tests work with the spring transaction without parameterized.
 * 
 * @author Rudy.Gao
 * @time Sep 16, 2013 2:04:41 PM
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(transactionManager = "txManager")
public class BaseCaseTest  extends AbstractTransactionalJUnit4SpringContextTests{

}
