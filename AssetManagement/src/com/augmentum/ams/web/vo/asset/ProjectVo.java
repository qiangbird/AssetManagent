package com.augmentum.ams.web.vo.asset;

public class ProjectVo {
	private String projectName;
	private String projectCode;
	private String projectManagerEmployeeId;
	private String projectManagerName;
	private String projectInfo;
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectManagerEmployeeId() {
		return projectManagerEmployeeId;
	}
	public void setProjectManagerEmployeeId(String projectManagerEmployeeId) {
		this.projectManagerEmployeeId = projectManagerEmployeeId;
	}
	public String getProjectManagerName() {
		return projectManagerName;
	}
	public void setProjectManagerName(String projectManagerName) {
		this.projectManagerName = projectManagerName;
	}
    public String getProjectInfo() {
        return projectInfo;
    }
    public void setProjectInfo(String projectInfo) {
        this.projectInfo = projectInfo;
    }
	
}
