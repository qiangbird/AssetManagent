package com.augmentum.ams.model.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.augmentum.ams.model.user.User;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:04:23 AM
 */
@Entity
@Table(name = "page_size")
public class PageSize extends BaseModel {

	private static final long serialVersionUID = 8993002689736549922L;

	@Column(name = "category_flag", nullable = false, length = 6)
	private int categoryFlag;

	/** The value of the size */
	@Column(name = "page_size_value", nullable = false, length = 8)
	private int pageSizeValue;

	/** Whether the value is the default value. '1' means yes, '0' means no */
	@Column(name = "is_default_value", nullable = true)
	private boolean isDefaultValue;

	/** Employees who use this PageSize for customizing. */
	@ManyToMany(mappedBy = "pageSizeList", fetch = FetchType.LAZY, targetEntity = User.class)
	private List<User> users = new ArrayList<User>();

	public int getCategoryFlag() {
		return categoryFlag;
	}

	public void setCategoryFlag(int categoryFlag) {
		this.categoryFlag = categoryFlag;
	}

	public int getPageSizeValue() {
		return pageSizeValue;
	}

	public void setPageSizeValue(int pageSizeValue) {
		this.pageSizeValue = pageSizeValue;
	}

	public boolean getIsDefaultValue() {
		return isDefaultValue;
	}

	public void setIsDefaultValue(boolean isDefaultValue) {
		this.isDefaultValue = isDefaultValue;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
