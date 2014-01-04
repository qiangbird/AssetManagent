package com.augmentum.ams.web.vo.user;

/**
 * @author Rudy.Gao
 * @time Sep 16, 2013 9:49:44 AM
 */
public class UserVo {

    private String id;

    private String employeeId;

    private String employeeName;

    private String employeeLevel;
    
    private String positionCode;
    
    private boolean isITRole;

    private boolean isSystemAdminRole;
    
	private String positionNameEn;

	private String positionNameCn;
	
	private String departmentNameEn;
	
	private String departmentNameCn;
	
	private String managerName;
	
	private String belongCustomer;
	
	private boolean isDelete;
	
	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public boolean isITRole() {
        return isITRole;
    }

    public void setITRole(boolean isITRole) {
        this.isITRole = isITRole;
    }

    public boolean isSystemAdminRole() {
        return isSystemAdminRole;
    }

    public void setSystemAdminRole(boolean isSystemAdminRole) {
        this.isSystemAdminRole = isSystemAdminRole;
    }

    public String getEmployeeLevel() {
        return employeeLevel;
    }

    public void setEmployeeLevel(String employeeLevel) {
        this.employeeLevel = employeeLevel;
    }

	public String getPositionCode() {
		return positionCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionNameEn() {
		return positionNameEn;
	}

	public void setPositionNameEn(String positionNameEn) {
		this.positionNameEn = positionNameEn;
	}

	public String getPositionNameCn() {
		return positionNameCn;
	}

	public void setPositionNameCn(String positionNameCn) {
		this.positionNameCn = positionNameCn;
	}

	public String getDepartmentNameEn() {
		return departmentNameEn;
	}

	public void setDepartmentNameEn(String departmentNameEn) {
		this.departmentNameEn = departmentNameEn;
	}

	public String getDepartmentNameCn() {
		return departmentNameCn;
	}

	public void setDepartmentNameCn(String departmentNameCn) {
		this.departmentNameCn = departmentNameCn;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
    public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	
	public String getBelongCustomer() {
        return belongCustomer;
    }

    public void setBelongCustomer(String belongCustomer) {
        this.belongCustomer = belongCustomer;
    }

    @Override
	public String toString() {
		return "UserVo [id=" + id + ", employeeId=" + employeeId
				+ ", employeeName=" + employeeName + ", employeeLevel="
				+ employeeLevel + ", positionCode=" + positionCode
				+ ", isITRole=" + isITRole + ", isSystemAdminRole="
				+ isSystemAdminRole + ", positionNameEn=" + positionNameEn
				+ ", positionNameCn=" + positionNameCn + ", departmentNameEn="
				+ departmentNameEn + ", departmentNameCn=" + departmentNameCn
				+ ", managerName=" + managerName + "]";
	}

    
}
