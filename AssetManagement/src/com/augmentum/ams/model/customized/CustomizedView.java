package com.augmentum.ams.model.customized;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.augmentum.ams.model.base.BaseModel;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:40:03 AM
 */
@Entity
@Table(name = "customized_view")
public class CustomizedView extends BaseModel {

    private static final long serialVersionUID = -7477644860669562800L;

    @Column(name = "view_name", length = 32, nullable = false)
    private String viewName;

    /**
     * The operators like: or, and
     */
    @Column(length = 32, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private String operators;

    @Column(name = "creator_id", length = 32)
    private String creatorId;

    @Column(name = "creator_name", length = 32)
    private String creatorName;

    @Column(name = "category_type", length = 32)
    private String categoryType;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customizedView")
    private List<CustomizedViewItem> selfViewItems = new ArrayList<CustomizedViewItem>();

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public List<CustomizedViewItem> getSelfViewItems() {
        return selfViewItems;
    }

    public void setSelfViewItems(List<CustomizedViewItem> selfViewItems) {
        this.selfViewItems = selfViewItems;
    }

}
