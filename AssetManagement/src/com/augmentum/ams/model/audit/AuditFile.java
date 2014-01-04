package com.augmentum.ams.model.audit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.user.User;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:03:26 AM
 */
@Entity
@Table(name = "audit_file")
public class AuditFile extends BaseModel {

	private static final long serialVersionUID = 7910432215158859830L;

	/**
	 * The upload file name
	 */
	@Column(name = "file_name", nullable = false, length = 256)
	private String fileName;
	
	/**
	 * The asset is whether audited complete or not
	 */
	@Column(name = "complete", nullable = false, columnDefinition = "bit(1) default 0")
	private boolean complete;

	/**
	 * The time of file is uploaded
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "operation_time", nullable = true)
	private Date operationTime;

	@OneToMany(mappedBy = "auditFile")
	private List<Audit> audits = new ArrayList<Audit>();

	@OneToMany(mappedBy = "auditFile")
	private List<Inconsistent> inconsistents = new ArrayList<Inconsistent>();

	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "employee_id")
	private User operator;

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getOperationTime() {
		return operationTime;
	}

	public void setOperationTime(Date operationTime) {
		this.operationTime = operationTime;
	}

	public List<Audit> getAudits() {
		return audits;
	}

	public void setAudits(List<Audit> audits) {
		this.audits = audits;
	}

	public List<Inconsistent> getInconsistents() {
		return inconsistents;
	}

	public void setInconsistents(List<Inconsistent> inconsistents) {
		this.inconsistents = inconsistents;
	}

	public User getOperator() {
		return operator;
	}

	public void setOperator(User operator) {
		this.operator = operator;
	}
}
