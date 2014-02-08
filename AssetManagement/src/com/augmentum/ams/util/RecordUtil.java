package com.augmentum.ams.util;

import java.util.Date;

public class RecordUtil {
	private int id;
	private String entity; // 实体类名,标记是哪个实体类
	private String entityId; // 实体对象的Id
	private String property; // 实体的属性名,标记实体的哪个属性发生修改
	private String user; // 修改人
	private Date modifyDate; // 修改时间
	private String desc; // 描述


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
