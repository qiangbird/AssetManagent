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
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:59:20 AM
 */
@Entity
@Table(name = "machine")
@Indexed(index = "machine")
@Analyzer(impl = IKAnalyzer.class)
public class Machine extends BaseModel {

	private static final long serialVersionUID = -5138104941153729661L;

	/**
	 * Laptop, Desktop
	 */
	@Column(name = "sub_type", length = 32, nullable = false)
	@Field(name = "subtype", index = Index.UN_TOKENIZED, store = Store.YES)
	private String subtype;

	@Column(length = 512)
	@Field(name = "specification", index = Index.TOKENIZED, store = Store.YES)
	private String specification;

	/**
	 * Address of MAC
	 */
	@Column(length = 128)
	@Field(name = "address", index = Index.TOKENIZED, store = Store.YES)
	private String address;

	@Column(length = 512)
	@Field(name = "configuration", index = Index.TOKENIZED, store = Store.YES)
	private String configuration;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "asset_id", unique = true)
	@IndexedEmbedded
	private Asset asset;


	public String getSubtype() {
		return subtype;
	}

	public void setSubtype(String subtype) {
		this.subtype = subtype;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
}
