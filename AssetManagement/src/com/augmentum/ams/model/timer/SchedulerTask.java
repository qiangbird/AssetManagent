package com.augmentum.ams.model.timer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.user.User;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:51:22 AM
 */
@Entity
@Table(name = "scheduler_task")
public class SchedulerTask extends BaseModel {

	private static final long serialVersionUID = 5048915547592465730L;

	/**
	 * The user who create the scheduler
	 */
	@ManyToOne
	@JoinColumn(name = "creator_id")
	private User creatorId;

	/**
	 * The timer type of the task, or task name like: send email to IT
	 */
	@Column(name = "timer_type", length = 32, nullable = false)
	private String timerType;

	/**
	 * The trigger of the scheduler
	 */
	@Column(name = "trigger_value", length = 32, nullable = false)
	private String triggerValue;

	/**
	 * The scheduler is running or stop
	 */
	@Column(name = "is_running", length = 32, nullable = false)
	private boolean isRunning = false;

	@Column(length = 512)
	private String description;

	public User getCreatorId() {
    	return creatorId;
    }

	public void setCreatorId(User creatorId) {
    	this.creatorId = creatorId;
    }

	public String getTimerType() {
    	return timerType;
    }

	public void setTimerType(String timerType) {
    	this.timerType = timerType;
    }

	public String getTriggerValue() {
    	return triggerValue;
    }

	public void setTriggerValue(String triggerValue) {
    	this.triggerValue = triggerValue;
    }

	public boolean isRunning() {
    	return isRunning;
    }

	public void setRunning(boolean isRunning) {
    	this.isRunning = isRunning;
    }

	public String getDescription() {
    	return description;
    }

	public void setDescription(String description) {
    	this.description = description;
    }

	
}
