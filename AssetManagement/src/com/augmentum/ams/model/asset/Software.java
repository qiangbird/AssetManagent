package com.augmentum.ams.model.asset;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.FieldBridge;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.base.ConvertStringToLowerCase;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:02:00 AM
 */
@Entity
@Table(name = "software")
@Indexed(index = "software")
@Analyzer(impl = IKAnalyzer.class)
public class Software extends BaseModel {

	private static final long serialVersionUID = 5823987705676610947L;

	/**
	 * The version of a software
	 */
	@Column(length = 32)
	@Field(name = "version", index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = ConvertStringToLowerCase.class)
	private String version;

	/**
	 * The license key of software
	 */
	@Column(length = 256, name = "license_key")
	@Field(name = "licenseKey", index = Index.UN_TOKENIZED, store = Store.YES)
	@FieldBridge(impl = ConvertStringToLowerCase.class)
	private String licenseKey;

	@Column(name = "additional_info")
	@Field(name = "additionalInfo", index = Index.TOKENIZED, store = Store.YES)
	private String additionalInfo;
	
	@OneToMany(mappedBy = "software", cascade = CascadeType.ALL)
	@ContainedIn
	private List<Asset> assets = new ArrayList<Asset>();
	
	private boolean managerVisible;

    public boolean isManagerVisible() {
        return managerVisible;
    }

    public void setManagerVisible(boolean managerVisible) {
        this.managerVisible = managerVisible;
    }

    public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLicenseKey() {
		return licenseKey;
	}

	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
}
