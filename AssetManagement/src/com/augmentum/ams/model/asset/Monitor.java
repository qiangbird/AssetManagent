package com.augmentum.ams.model.asset;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.base.ConvertStringToLowerCase;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:59:26 AM
 */
@Entity
@Table(name = "monitor")
@Indexed(index = "monitor")
@Analyzer(impl = IKAnalyzer.class)
public class Monitor extends BaseModel {

	private static final long serialVersionUID = 6055166652837654014L;

	@Column(length = 32)
	@Field(name = "size", index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = ConvertStringToLowerCase.class)
	private String size;

	@Column(length = 512)
	@Field(name = "detail", index = Index.TOKENIZED, store = Store.YES)
	private String detail;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "asset_id", unique = true)
	@IndexedEmbedded
	private Asset asset;

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
}
