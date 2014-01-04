package com.augmentum.ams.model.operationLog;

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
 * @time Sep 12, 2013 9:04:46 AM
 */
@Entity
@Table(name = "operation_log")
@Indexed(index = "operation_log")
@Analyzer(impl = IKAnalyzer.class)
public class OperationLog extends BaseModel {

	private static final long serialVersionUID = 7314725364102909644L;

	@Column(name = "operator_name", length = 32)
	@Field(name = "operatorName", index = Index.TOKENIZED, store = Store.YES)
	private String operatorName;

	/** The operator ID(userId), for example YT00000 */
	@Column(name = "operator_id", length = 32, nullable = false)
	@Field(name = "operatorID", index = Index.TOKENIZED, store = Store.YES)
	private String operatorID;

	/**
	 * The detail operation modified by operator
	 */
	@Column(length = 512)
	@Field(name = "operation", index = Index.TOKENIZED, store = Store.YES)
	private String operation;

	/**
	 * The operation object name, for example asset object
	 */
	@Column(name = "operation_object", length = 32)
	@Field(name = "operationObject", index = Index.TOKENIZED, store = Store.YES)
	private String operationObject;

	/**
	 * It refers to a record uuid
	 */
	@Column(name = "operation_object_id", length = 32)
	@Field(name = "operationObjectID", index = Index.TOKENIZED, store = Store.YES)
	private String operationObjectID;

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorID() {
		return operatorID;
	}

	public void setOperatorID(String operatorID) {
		this.operatorID = operatorID;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperationObject() {
		return operationObject;
	}

	public void setOperationObject(String operationObject) {
		this.operationObject = operationObject;
	}

	public String getOperationObjectID() {
		return operationObjectID;
	}

	public void setOperationObjectID(String operationObjectID) {
		this.operationObjectID = operationObjectID;
	}

}
