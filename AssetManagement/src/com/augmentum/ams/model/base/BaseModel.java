package com.augmentum.ams.model.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;


/**
 * The base model which all the models contains these columns.
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:03:51 AM
 */
@MappedSuperclass
public class BaseModel implements Serializable {

	private static final long serialVersionUID = -6662367012798533564L;

	/**
	 * The id of a record.
	 */
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(length = 32)
	private String id;

	/**
	 * The create time of a record.
	 */
	@Column(name = "created_time", nullable = false)
	@Field(name = "createdTime", index = Index.UN_TOKENIZED, store = Store.YES)
	private Date createdTime;

	/**
	 * If expired
	 */
	@Column(name = "is_expired", insertable = false, nullable = false, columnDefinition = "bit(1) default 0")
	@Field(name = "isExpired", index = Index.UN_TOKENIZED, store = Store.YES)
	private boolean isExpired;
	
	/**
	 * The update time of a record.
	 */
	@Column(name = "updated_time", nullable = false)
	@Field(name = "updatedTime", index = Index.UN_TOKENIZED, store = Store.YES)
	private Date updatedTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}
