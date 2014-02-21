package com.augmentum.ams.util;

import com.augmentum.ams.model.enumeration.RoleEnum;
import com.augmentum.ams.web.vo.user.UserVo;

public class RoleLevelUtil {

    public static RoleEnum getRoleByUserVo(UserVo userVo) {

        String employeeLevel = userVo.getEmployeeLevel();
        RoleEnum role = null;
        if (employeeLevel.equalsIgnoreCase("employee") || employeeLevel.equalsIgnoreCase("leader")) {
            role = RoleEnum.EMPLOYEE;
        } else {
            role = RoleEnum.MANAGER;
        }
        return role;
    }

    public static boolean checkEmployeeCanViewCustomerAssets(UserVo userVo) {

        String employeeLevel = userVo.getEmployeeLevel();
        RoleEnum role = null;
        if (employeeLevel.equalsIgnoreCase("employee") || employeeLevel.equalsIgnoreCase("leader")) {
            return false;
        } else {
            return true;
        }
    }
}
