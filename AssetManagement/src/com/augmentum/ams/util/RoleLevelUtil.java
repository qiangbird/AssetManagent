package com.augmentum.ams.util;

import com.augmentum.ams.web.vo.user.UserVo;

public class RoleLevelUtil {

    public static String getRoleByUserVo(UserVo userVo) {

        String employeeLevel = userVo.getEmployeeLevel();
        String role = null;
        if (employeeLevel.equalsIgnoreCase("employee") || employeeLevel.equalsIgnoreCase("leader")) {
            role = "EMPLOYEE";
        } else {
            role = "MANAGER";
        }
        return role;
    }

    public static boolean checkEmployeeCanViewCustomerAssets(UserVo userVo) {

        String employeeLevel = userVo.getEmployeeLevel();
        if (employeeLevel.equalsIgnoreCase("employee") || employeeLevel.equalsIgnoreCase("leader")) {
            return false;
        } else {
            return true;
        }
    }
}
