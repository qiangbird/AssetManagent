package com.augmentum.ams.dao.user.impl;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.user.RoleDao;
import com.augmentum.ams.model.user.Role;


@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao{

}
