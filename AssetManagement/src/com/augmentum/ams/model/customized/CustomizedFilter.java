package com.augmentum.ams.model.customized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.user.User;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:12:55 AM
 */
@Entity
@Table(name = "customized_filter")
public class CustomizedFilter extends BaseModel{

    private static final long serialVersionUID = -5078951974981620561L;

	@Column(name = "sort_name", length = 32, nullable = false)
    private String sortName;
    
	/**
     * Which kind of filter like: select, check box
     */
	@Column(length = 32, nullable = false)
    private String type;
    
	/**
     * The enName of column
     */
	@Column(name = "column_en", length = 32, nullable = false)
    private String columnEn;
	
	/**
     * The zhName of column
     */
	@Column(name = "column_zh", length = 32, nullable = false)
    private String columnZh;
    
	/**
     * The value of property, if property select type is select, the values are split by #
     */
	@Column(length = 512)
    private String value;
    
	@ManyToOne
	@JoinColumn(name = "creator_Id", nullable = false)
    private User creatorId;

	public String getSortName() {
    	return sortName;
    }

	public void setSortName(String sortName) {
    	this.sortName = sortName;
    }

	public String getType() {
    	return type;
    }

	public void setType(String type) {
    	this.type = type;
    }

	public String getColumnEn() {
    	return columnEn;
    }

	public void setColumnEn(String columnEn) {
    	this.columnEn = columnEn;
    }

	public String getColumnZh() {
    	return columnZh;
    }

	public void setColumnZh(String columnZh) {
    	this.columnZh = columnZh;
    }

	public String getValue() {
    	return value;
    }

	public void setValue(String value) {
    	this.value = value;
    }

	public User getCreatorId() {
    	return creatorId;
    }

	public void setCreatorId(User creatorId) {
    	this.creatorId = creatorId;
    }
	
	
}
