package com.augmentum.ams.model.todo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.user.User;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:55:25 AM
 */
@Entity
@Table(name = "todo")
public class ToDo extends BaseModel {

	private static final long serialVersionUID = -8600070952058905093L;
	
	@Column(name = "task")
	private String task;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "received_time")
	private Date receivedTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "returned_time")
	private Date returnedTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "assigner_id")
	private User assigner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "returner_id")
	private User returner;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "asset_id")
	private Asset asset;

	public Date getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}

	public Date getReturnedTime() {
		return returnedTime;
	}

	public void setReturnedTime(Date returnedTime) {
		this.returnedTime = returnedTime;
	}

	public User getAssigner() {
		return assigner;
	}

	public void setAssigner(User assigner) {
		this.assigner = assigner;
	}

	public User getReturner() {
		return returner;
	}

	public void setReturner(User returner) {
		this.returner = returner;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }
	
}
