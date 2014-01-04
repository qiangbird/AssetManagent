package com.augmentum.ams.dao.user.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.user.UserDao;
import com.augmentum.ams.model.user.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public User getUserByName(String username) {
        String hql = "FROM User where userName=?";
        return super.getUnique(hql, username.trim());
    }

    @Override
    public User getUserByUserId(String userId) {
        String hql = "FROM User WHERE isExpired = ? AND userId = ?";
        User user = super.getUnique(hql, Boolean.FALSE, userId);

        return user;
    }

    @Override
    public void saveUser(User user) {
        super.save(user);
    }

    @Override
    public User findUserByUserId(String userId) {
        String hql = "SELECT DISTINCT user FROM User user INNER JOIN FETCH user.pageSizeList WHERE user.isExpired = ? AND user.userId = ?";
        User user = super.getUnique(hql, Boolean.FALSE, userId);

        if (null != user) {
            return user;
        }
        return null;
    }

    @Override
    public void deleteUserRoleById(String userId) {
        // String sql = "delete from user_role where user_id=?";
        StringBuffer sql = new StringBuffer();
        sql.append("delete from user_role where user_id=");
        sql.append("'" + userId + "'");
        bulkUpdateBySQL(sql.toString());
    }

}
