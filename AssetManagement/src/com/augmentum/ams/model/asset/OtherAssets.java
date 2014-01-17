package com.augmentum.ams.model.asset;

import javax.persistence.CascadeType;
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

import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:59:49 AM
 */
@Entity
@Table(name = "other_assets")
@Indexed(index = "other_assets")
@Analyzer(impl = IKAnalyzer.class)
public class OtherAssets extends BaseModel {

	private static final long serialVersionUID = -3920079178379939884L;

	@Column(length = 512)
	@Field(name = "detail", index = Index.TOKENIZED, store = Store.YES)
	private String detail;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "asset_id", unique = true)
	@IndexedEmbedded
	private Asset asset;

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
