package com.augmentum.ams.web.vo.user;

public class SpecialRoleVo {
	
	private String customerCode;
	
	private String customerName;
	
	private String employeeId;
	
	private String employeeName;
	
	private boolean isDelete;
	
	private String specialRoles;
	
	public SpecialRoleVo(){
    }
	
	public SpecialRoleVo(String customerCode, String customerName,
	        String employeeId, String employeeName, boolean isDelete,
	        String specialRoles){
	    
	    this.customerCode = customerCode;
	    this.customerName = customerName;
	    this.employeeId = employeeId;
	    this.employeeName = employeeName;
	    this.isDelete = isDelete;
	    this.specialRoles = specialRoles;
	}
	
	public String getSpecialRoles() {
        return specialRoles;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setSpecialRoles(String specialRoles) {
        this.specialRoles = specialRoles;
    }

    public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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

    public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	
	
}
