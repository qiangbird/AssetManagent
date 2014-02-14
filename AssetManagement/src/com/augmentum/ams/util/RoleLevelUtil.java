package com.augmentum.ams.util;

import com.augmentum.ams.model.enumeration.RoleEnum;
import com.augmentum.ams.web.vo.user.UserVo;

public class RoleLevelUtil {
    
    public static RoleEnum getRoleByUserVo(UserVo userVo) {
        
        String employeeLevel = userVo.getEmployeeLevel();
        RoleEnum role = null;
        if(employeeLevel.equalsIgnoreCase("employee")||employeeLevel.equalsIgnoreCase("leader")||
                employeeLevel.equalsIgnoreCase("Architect")||employeeLevel.equalsIgnoreCase("SE")||
                employeeLevel.equalsIgnoreCase("SCE")){
        	role = RoleEnum.EMPLOYEE;
        }else{
        	role = RoleEnum.MANAGER;
        }
        return role;
    }
}
