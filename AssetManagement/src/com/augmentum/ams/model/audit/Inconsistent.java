package com.augmentum.ams.model.audit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:58:09 AM
 */
@Entity
@Table(name = "inconsistent")
@Indexed(index = "inconsistent")
@Analyzer(impl = IKAnalyzer.class)
public class Inconsistent extends BaseModel {

	private static final long serialVersionUID = 6638347160078914685L;

	/**
	 * The bar code which asset is not inconsistent
	 */
	@Column(name = "bar_code", length = 64)
	@Field(name = "barcode", index = Index.UN_TOKENIZED, store = Store.YES)
	private String barCode;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "asset_id", unique = false)
	@IndexedEmbedded(depth = 2)
	private Asset asset;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "audit_file_id")
	@IndexedEmbedded(depth = 1)
	private AuditFile auditFile;

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
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
