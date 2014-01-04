package com.augmentum.ams.model.customized;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.augmentum.ams.model.base.BaseModel;

@Entity
@Table(name = "customized_view_item")
public class CustomizedViewItem extends BaseModel {

    private static final long serialVersionUID = 1781928723327610552L;

    @Column(name = "column_name", length = 32)
    private String columnName;

    /**
     * The search conditions like: !=, = etc.
     */
    @Column(name = "search_condition", length = 32)
    @Enumerated(value = EnumType.STRING)
    private String searchCondition;

    @Column(length = 64)
    private String value;

    /**
     * The real type of the choose column
     */
    @Column(name = "column_type", length = 32)
    private String columnType;
    
    /**
     * The real search column in database
     */
    @Column(name = "search_column", length = 80)
    private String searchColumn;
    
    /**
     * The real table in database
     */
    @Column(name = "real_table", length = 80)
    private String realTable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customized_view_id")
    private CustomizedView customizedView;

    public String getRealTable() {
        return realTable;
    }

    public void setRealTable(String realTable) {
        this.realTable = realTable;
    }

    public String getSearchColumn() {
        return searchColumn;
    }

    public void setSearchColumn(String searchColumn) {
        this.searchColumn = searchColumn;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public CustomizedView getCustomizedView() {
        return customizedView;
    }

    public void setCustomizedView(CustomizedView customizedView) {
        this.customizedView = customizedView;
    }

}
