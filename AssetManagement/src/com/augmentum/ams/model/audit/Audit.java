package com.augmentum.ams.model.audit;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:02:14 AM
 */
@Entity
@Table(name = "audit")
public class Audit extends BaseModel {

	private static final long serialVersionUID = 6328315396440633867L;

	/**
	 * Which asset checked or not. Default is false
	 */
	private boolean status = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "asset_id")
	private Asset asset;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_file_id")
	private AuditFile auditFile;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public AuditFile getAuditFile() {
		return auditFile;
	}

	public void setAuditFile(AuditFile auditFile) {
		this.auditFile = auditFile;
	}

}
