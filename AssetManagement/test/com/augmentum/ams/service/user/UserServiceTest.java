package com.augmentum.ams.service.user;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.dao.base.BaseHibernateDao;
import com.augmentum.ams.dao.user.UserDao;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.asset.Location;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.BaseCaseTest;

public class UserServiceTest extends BaseCaseTest{

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BaseHibernateDao baseHibernateDao;
    
    @Test
    public void testFindUserRole() throws BusinessException {
        userService.findUserRole();
    }
    
    @Test
    public void testGetUserByUserId() {
        try {
            User user1 = userService.getUserByUserId("T03768");
            System.out.println(user1);
            User user2 = userService.getUserByUserId("mockUserId");
            System.out.println(user2);
        } 
        catch (Exception e) {
           e.printStackTrace();
        }
    }
    
    @Test
    public void createIndex() {
    	List<User> list = userDao.findAll(User.class);
    	Class<Location>[] clazzes = new Class[list.size()];
        for (int i = 0; i < list.size(); i++) {
            User user = list.get(i);
            Class clazz =  user.getClass();
            clazzes[i] = clazz;
        }
	    try {
			baseHibernateDao.createIndex(clazzes);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
