package com.augmentum.ams.util;

import java.util.Date;

public class RecordUtil {
	private int id;
	private String entity; 
	private String entityId; 
	private String property; 
	private String user; 
	private Date modifyDate; 
	private String desc; 


	@Override
	public String toString() {
		return "\n History [id=" + id + ", entity=" + entity + ",\n entityId="
				+ entityId + ", property=" + property + ",\n user=" + user
				+ ", modifyDate=" + modifyDate + ", desc=" + desc + "]\n";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
