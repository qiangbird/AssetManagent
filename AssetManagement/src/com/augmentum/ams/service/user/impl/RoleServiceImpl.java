package com.augmentum.ams.service.user.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.user.RoleDao;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.enumeration.RoleEnum;
import com.augmentum.ams.model.user.Authority;
import com.augmentum.ams.model.user.Role;
import com.augmentum.ams.service.user.AuthorityService;
import com.augmentum.ams.service.user.RoleService;
import com.augmentum.ams.util.ErrorCodeUtil;
import com.augmentum.ams.util.ExceptionUtil;
import com.augmentum.ams.util.UTCTimeUtil;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private static Logger logger = Logger.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private AuthorityService authorityService;

    /*
     * (non-Javadoc)
     * 
     * @see com.augmentum.ams.service.user.RoleService#saveInitRole()
     */
    @Override
    public void saveInitRole() {
        List<Role> roles = new ArrayList<Role>();
        for (RoleEnum roleEnum : RoleEnum.values()) {

            // Because RoleEnum.SPECIAL_ROLE info in special_role table, so here
            // needn't save its info in database
            if (!roleEnum.equals(RoleEnum.SPECIAL_ROLE)) {
                Role role = new Role();
                role.setRoleName(roleEnum);
                Date date = UTCTimeUtil.localDateToUTC();
                role.setCreatedTime(date);
                role.setUpdatedTime(date);
                roles.add(role);
                logger.info("Save role:" + roleEnum.toString()+ " in database");
            }
        }
        roleDao.saveOrUpdateAll(roles);
    }

    /* (non-Javadoc)
     * @see com.augmentum.ams.service.user.RoleService#getRoleByName(com.augmentum.ams.model.user.RoleEnum)
     */
    @Override
    public Role getRoleByName(RoleEnum roleName) throws DataException{
        DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);

        //roleName can not be cast to String for restrictions
        criteria.add(Restrictions.eq("roleName", roleName)).add(Restrictions.eq("isExpired", Boolean.FALSE));
        Role role =  roleDao.getUnique(criteria);
        if(role == null) {
            ExceptionUtil.dataException(ErrorCodeUtil.DATA_ROLE_0203001, roleName.toString());
        }
        return role;
    }

    /* (non-Javadoc)
     * @see com.augmentum.ams.service.user.RoleService#saveRoleAuthorities(java.lang.String, java.lang.String)
     */
    @Override
    public void saveRoleAuthorities(String roleName, String authorityName) throws DataException {
        Role role = this.getRoleByName(RoleEnum.getRoleEnum(roleName));
        List<Authority> authorities = role.getAuthorities();
        //        boolean roleAuthorityExist = false;
        //        if(authorities != null) {
        //            for (Authority authority : authorities) {
        //                if (authorityName.equals(authority.getName())) {
        //                    roleAuthorityExist = true;
        //                    break;
        //                }
        //            }
        //        }
        //
        //        if (!roleAuthorityExist) {
        Authority authority = authorityService.getAuthorityByName(authorityName);
        List<Role> roles = authority.getRoles();
        roles.add(role);
        authorities.add(authority);
        role.setAuthorities(authorities);
        authority.setRoles(roles);

        role.setUpdatedTime(new Date());
        roleDao.update(role);
        logger.info("Update role authority. Role name:"+roleName+", authority name:"+authorityName);
        //        }
    }

    /* (non-Javadoc)
     * @see com.augmentum.ams.service.user.RoleService#getAuthoritiesByRoleName(com.augmentum.ams.model.user.RoleEnum)
     */
    @Override
    public List<String> getAuthoritiesByRoleName(RoleEnum roleName) {
        DetachedCriteria criteria = DetachedCriteria.forClass(Role.class).setFetchMode("authorities", FetchMode.JOIN);
        criteria.add(Restrictions.eq("roleName", roleName)).add(Restrictions.eq("isExpired", Boolean.FALSE));

        List<Role> roles = roleDao.findByCriteria(criteria);
        List<String> authorities = new ArrayList<String>();
        if (0 == roles.size()) {
            // TODO
            return null;
        } else {
            Role role = roles.get(0);
            for (Authority authority : role.getAuthorities()) {
                authorities.add(authority.getName());
            }
        }
        return authorities;
    }


}
