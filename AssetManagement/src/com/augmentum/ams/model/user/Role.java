package com.augmentum.ams.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

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
 * @time Sep 12, 2013 9:55:06 AM
 */
@Entity
@Table(name = "role")
@Indexed(index = "role")
@Analyzer(impl = IKAnalyzer.class)
public class Role extends BaseModel {

	private static final long serialVersionUID = 4757756304505582829L;

	/**
	 * The role name of an user
	 */
	@Column(name = "role_name", nullable = false, unique = true, length = 75)
	@Field(name = "role_name", index = Index.UN_TOKENIZED, store = Store.YES)
    @FieldBridge(impl = ConvertStringToLowerCase.class)
	private String roleName;

	@ManyToMany(targetEntity = Authority.class, fetch = FetchType.LAZY)
	@JoinTable(name = "role_authority", joinColumns = { @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "authority_id", referencedColumnName = "id", nullable = false) })
	private List<Authority> authorities = new ArrayList<Authority>();

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "roles", targetEntity = User.class)
	@ContainedIn
	private List<User> users = new ArrayList<User>();

	@Transient
	private boolean isSelected;

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
