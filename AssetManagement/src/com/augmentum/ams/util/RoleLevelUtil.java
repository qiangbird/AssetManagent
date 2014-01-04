package com.augmentum.ams.util;

import com.augmentum.ams.model.enumeration.RoleEnum;
import com.augmentum.ams.web.vo.user.UserVo;

public class RoleLevelUtil {
    
    public static RoleEnum getRoleByUserVo(UserVo userVo) {
        
        String employeeLevel = userVo.getEmployeeLevel();
        RoleEnum role = null;
        if (employeeLevel.equals("ExtManager") || employeeLevel.equals("Manager")
                || employeeLevel.equals("SrManager") || employeeLevel.equals("Director")
                || employeeLevel.equals("SrDirector") || employeeLevel.equals("VP")) {
            role = RoleEnum.MANAGER;
        } else {
            role = RoleEnum.EMPLOYEE;
        }
        return role;
    }
}
