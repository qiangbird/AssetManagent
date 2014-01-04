package com.augmentum.ams.model.enumeration;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:51:36 AM
 */
public enum RoleEnum {

	IT("IT"),
	
	SYSTEM_ADMIN("system_admin"),
	
	EMPLOYEE("employee"),
	
	MANAGER("manager"),
	
	SPECIAL_ROLE("special_role");
	
	private String role;

	private RoleEnum(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public static RoleEnum getRoleEnum(String role) {
		RoleEnum roleEnum = null;
		for (RoleEnum re : RoleEnum.values()) {
			if (re.getRole().equalsIgnoreCase(role)) {
				roleEnum = re;
				break;
			}
		}
		return roleEnum;
	}

}
