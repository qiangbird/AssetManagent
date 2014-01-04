package com.augmentum.ams.service.user;

import java.util.List;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.enumeration.RoleEnum;
import com.augmentum.ams.model.user.Role;

public interface RoleService {

    /**
     * @description initialize system role in database
     * @author Rudy.Gao
     * @time Sep 16, 2013 2:03:00 PM
     */
    void saveInitRole();

    /**
     * @description get role info by roleName which is an instance of RoleEnum,
     *              notice that it will return null if roleName is not in
     *              database.
     * @author Rudy.Gao
     * @time Sep 16, 2013 3:13:36 PM
     * @param roleEnum
     * @return Role
     * @throws DataException
     */
    Role getRoleByName(RoleEnum roleName) throws DataException;

    /**
     * @author Grylls.Xu
     * @time Oct 11, 2013 5:06:40 PM
     * @description Init Role and it's authorities from authority.xml.
     * @param roleName
     * @param authorityName
     * @throws DataException
     */
    void saveRoleAuthorities(String roleName, String authorityName) throws DataException;

    /**
     * @author Grylls.Xu
     * @time Oct 14, 2013 2:40:20 PM
     * @description Get Authorities by role.
     * @param roleName
     * @return
     */
    List<String> getAuthoritiesByRoleName(RoleEnum roleName);

}
