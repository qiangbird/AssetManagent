package com.augmentum.ams.model.asset;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @time Sep 12, 2013 8:57:18 AM
 */
@Entity
@Table(name = "device")
@Indexed(index = "device")
@Analyzer(impl = IKAnalyzer.class)
public class Device extends BaseModel {

	private static final long serialVersionUID = -4242916450679437499L;

	@Column(length = 32)
	@Field(name = "configuration", index = Index.TOKENIZED, store = Store.YES)
	private String configuration;

	@OneToOne
	@JoinColumn(name = "asset_id", unique = true)
	@IndexedEmbedded
	private Asset asset;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "device_subtype_id")
	@IndexedEmbedded
	private DeviceSubtype deviceSubtype;

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

	public DeviceSubtype getDeviceSubtype() {
		return deviceSubtype;
	}

	public void setDeviceSubtype(DeviceSubtype deviceSubtype) {
		this.deviceSubtype = deviceSubtype;
	}
}
