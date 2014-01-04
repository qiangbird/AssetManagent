package com.augmentum.ams.service.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.BaseCaseTest;

public class UserServiceTest extends BaseCaseTest{

    @Autowired
    private UserService userService;
    
    @Test
    public void testFindUserRole() throws DataException {
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
}
