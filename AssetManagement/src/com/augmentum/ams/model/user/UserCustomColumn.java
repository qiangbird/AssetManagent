package com.augmentum.ams.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.augmentum.ams.model.asset.CustomizedColumn;
import com.augmentum.ams.model.base.BaseModel;
import com.augmentum.ams.model.customized.PropertyTemplate;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:55:53 AM
 */
@Entity
@Table(name = "user_custom_column")
public class UserCustomColumn extends BaseModel {

    private static final long serialVersionUID = -8535302422572427638L;

    /**
     * The sequence defined by user
     */
    @Column(length = 6)
    private int sequence;

    /**
     * The column show or hide defined by user in list
     */
    private boolean showDefault = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "custom_column_id")
    private CustomizedColumn customizedColumn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_template_id")
    private PropertyTemplate propertyTemplate;

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public boolean getShowDefault() {
        return showDefault;
    }

    public void setShowDefault(boolean showDefault) {
        this.showDefault = showDefault;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getEmployee() {
        return user;
    }

    public void setEmployee(User user) {
        this.user = user;
    }

    public CustomizedColumn getCustomizedColumn() {
        return customizedColumn;
    }

    public void setCustomizedColumn(CustomizedColumn customizedColumn) {
        this.customizedColumn = customizedColumn;
    }

    public PropertyTemplate getPropertyTemplate() {
        return propertyTemplate;
    }

    public void setPropertyTemplate(PropertyTemplate propertyTemplate) {
        this.propertyTemplate = propertyTemplate;
    }

}
