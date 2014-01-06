package com.augmentum.ams.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.base.PageSize;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:55:40 AM
 */
@Entity
@Table(name = "user")
@Indexed(index = "user")
public class User extends BaseModel {

	private static final long serialVersionUID = -5454470926592265175L;

	/**
	 * The name of a user, e.g: Grylls Xu
	 */
	@Column(name = "user_name", nullable = false, length = 32)
	@Field(name = "userName", index = Index.TOKENIZED, store = Store.YES)
	private String userName;

	/**
	 * The userId of user, e.g: YT00173
	 */
	@Column(nullable = false, unique = true, length = 32)
	@Field(name = "userId", index = Index.UN_TOKENIZED, store = Store.YES)
	private String userId;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false) })
	private List<Role> roles = new ArrayList<Role>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<UserCustomColumn> userCustomColumn = new ArrayList<UserCustomColumn>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_page_size", joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "page_size_id", referencedColumnName = "id", nullable = false) })
	private List<PageSize> pageSizeList = new ArrayList<PageSize>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	@ContainedIn
	private List<Asset> assets = new ArrayList<Asset>();

	public List<PageSize> getPageSizeList() {
		return pageSizeList;
	}

	public void setPageSizeList(List<PageSize> pageSizeList) {
		this.pageSizeList = pageSizeList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<UserCustomColumn> getUserCustomColumns() {
		return userCustomColumn;
	}

	public void setUserCustomColumns(List<UserCustomColumn> userCustomColumn) {
		this.userCustomColumn = userCustomColumn;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

}
