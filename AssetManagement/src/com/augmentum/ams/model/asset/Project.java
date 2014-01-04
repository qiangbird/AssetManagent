package com.augmentum.ams.model.asset;

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
import org.hibernate.search.annotations.Store;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:00:02 AM
 */
@Entity
@Table(name = "project")
@Indexed(index = "project")
@Analyzer(impl = IKAnalyzer.class)
public class Project extends BaseModel {

	private static final long serialVersionUID = 7794992399275180367L;

	@Column(name = "project_code", length = 32, nullable = false)
	@Field(name = "projectCode", index = Index.UN_TOKENIZED, store = Store.YES)
	private String projectCode;

	@Column(name = "project_name", length = 64)
	@Field(name = "projectName", index = Index.TOKENIZED, store = Store.YES)
	private String projectName;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
