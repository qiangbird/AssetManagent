package com.augmentum.ams.model.asset;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 8:57:42 AM
 */
@Entity
@Table(name = "device_subtype")
@Indexed(index = "device_subtype")
@Analyzer(impl = IKAnalyzer.class)
public class DeviceSubtype extends BaseModel {

	private static final long serialVersionUID = -5203944671581959257L;

	@Column(name = "subtype_name", length = 64, nullable = false)
	@Field(name = "subtypeName", index = Index.TOKENIZED, store = Store.YES)
	private String subtypeName;

	public String getSubtypeName() {
		return subtypeName;
	}

	public void setSubtypeName(String subtypeName) {
		this.subtypeName = subtypeName;
	}


}
