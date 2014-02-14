package com.augmentum.ams.service.user;

import java.util.List;
import java.util.Map;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.web.vo.user.UserVo;

public interface UserService {

    /**
     * @description save user info and their roles in database. If user is not
     *              saved in database, then save their info and roles in
     *              database; If user is saved in database already, but their
     *              info not in userVos, then remove their roles in database; If
     *              user is saved in database already and their info also in
     *              userVos, then just update theirs roles.
     * @author Rudy.Gao
     * @time Sep 16, 2013 1:41:43 PM
     * @param userVos
     * @throws BusinessException
     */

    void saveUserRole(List<UserVo> userVos) throws BusinessException;

    /**
     * @description find all users have IT role or System_Admin role, then
     *              encapsulated then in UserVo
     * @author Rudy.Gao
     * @time Sep 16, 2013 6:28:02 PM
     * @return List<UserVo>
     * @throws BusinessException
     */
    List<UserVo> findUserRole() throws BusinessException;

    /**
     * @description get user info by UserId, notice that it will return null if
     *              userId does not exists in database
     * @author Rudy.Gao
     * @time Sep 17, 2013 10:58:00 AM
     * @param userId
     * @return User
     */
    User getUserByUserId(String userId);
    
    /**
     * @author Geoffrey.Zhao
     * @param userId
     * @return
     */
    User getUserByUserIdForUserRoles(String userId);

    /**
     * @author Grylls.Xu
     * @time Oct 16, 2013 7:48:27 PM
     * @description When get user info from iap we should change it to user obj.
     * And user object has roles, it will be put into shiro.
     * @param userVo
     * @return
     */
    User validateEmployee(UserVo userVo);
    
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 7, 2013 4:04:01 PM
     * @param username
     * @return
     */
    User getUserByName(String username);
    
    void saveUser(User user);
    
    void saveUserAsUserVo(UserVo userVo);
    
    /**
     * 
     * @author Jay.He
     * @time Dec 17, 2013 10:26:31 AM
     * @param userVo
     * @return
     */
    String getUserRole(UserVo userVo);

    /**
     * 
     * @author John.li
     * @time Dec 17, 2014 
     * @param userVo
     * @return
     */
    Map<String, User> findAllUsersFromLocal();
}
