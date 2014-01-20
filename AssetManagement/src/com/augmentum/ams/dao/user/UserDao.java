package com.augmentum.ams.dao.user;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.user.User;
/**
 * 
 * @author Jay.He
 *
 */
public interface UserDao extends BaseDao<User>{
	/**
	 * 
	 * @description TODO
	 * @author Jay.He
	 * @time Nov 7, 2013 4:02:31 PM
	 * @param username
	 * @return
	 */
	public User getUserByName(String username);
	
	/**
	 * @author Geoffrey.Zhao
	 * @param userId
	 * @return
	 */
	User getUserByUserId(String userId);
	
	/**
	 * It associates other model, such as pageSize
	 * @author Geoffrey.Zhao
	 * @param userId
	 * @return
	 */
	User findUserByUserId(String userId);
	
	/**
	 * @description TODO
	 * @author Jay.He
	 * @time Nov 18, 2013 2:16:50 PM
	 * @param user
	 */
	void saveUser(User user) ;
	
	/**
     * @description Clear user_role table records which role_id is IT or SystemAdmin role
     * @author Rudy.Gao
     * @time Sep 17, 2013 3:38:31 PM
     */
	void deleteUserRoleById(String userId);

}
