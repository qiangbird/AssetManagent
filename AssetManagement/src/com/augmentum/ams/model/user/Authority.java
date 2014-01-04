package com.augmentum.ams.model.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:51:17 AM
 */
@Entity
@Table(name = "authority")
public class Authority extends BaseModel{

    private static final long serialVersionUID = -7340375086250966383L;

    /**
     * The name of the authority like: createAsset
     */
    @Column(nullable = false,unique = true,length=128)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorities",targetEntity = Role.class)
    private List<Role> roles = new ArrayList<Role>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
