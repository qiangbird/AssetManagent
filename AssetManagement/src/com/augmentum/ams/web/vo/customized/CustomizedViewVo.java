package com.augmentum.ams.web.vo.customized;

/**
 * 
 * @author John.Li
 * @time Oct 31, 2013 11:07:50 AM
 */
public class CustomizedViewVo {

    private String operator;

    private String viewName;

    private String creatorId;

    private String categoryType;

    private String prePage;

    private String creatorName;

    private String[] columns;

    private String[] searchConditions;

    private String[] values;

    private String customizedViewId;

    private String[] customizedViewItemIds;

    private String[] isDeletes;

    private String[] columnTypes;

    private String[] searchColumns;

    private String[] realTables;

    public String getPrePage() {
        return prePage;
    }

    public void setPrePage(String prePage) {
        this.prePage = prePage;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String[] getRealTables() {
        return realTables;
    }

    public void setRealTables(String[] realTables) {
        this.realTables = realTables;
    }

    public String[] getSearchColumns() {
        return searchColumns;
    }

    public void setSearchColumns(String[] searchColumns) {
        this.searchColumns = searchColumns;
    }

    public String[] getColumnTypes() {
        return columnTypes;
    }

    public void setColumnTypes(String[] columnTypes) {
        this.columnTypes = columnTypes;
    }

    public CustomizedViewVo() {
    }

    // create
    public CustomizedViewVo(String operator, String viewName, String[] columns,
            String[] searchConditions, String[] values, String creatorId, String creatorName,
            String[] columnTypes, String[] searchColumns, String[] realTables) {

        this.values = values;
        this.columns = columns;
        this.operator = operator;
        this.viewName = viewName;
        this.creatorId = creatorId;
        this.creatorName = creatorName;
        this.columnTypes = columnTypes;
        this.searchColumns = searchColumns;
        this.realTables = realTables;
        this.searchConditions = searchConditions;

    }

    // update
    public CustomizedViewVo(String operator, String viewName, String[] columns,
            String[] searchConditions, String[] values, String[] customizedViewItemIds,
            String[] isDeletes, String[] columnTypes, String[] searchColumns,
            String customizedViewId, String[] realTables) {

        this.values = values;
        this.columns = columns;
        this.operator = operator;
        this.viewName = viewName;
        this.isDeletes = isDeletes;
        this.columnTypes = columnTypes;
        this.searchConditions = searchConditions;
        this.searchColumns = searchColumns;
        this.customizedViewId = customizedViewId;
        this.realTables = realTables;
        this.customizedViewItemIds = customizedViewItemIds;

    }

    public String[] getCustomizedViewItemIds() {
        return customizedViewItemIds;
    }

    public void setCustomizedViewItemIds(String[] customizedViewItemIds) {
        this.customizedViewItemIds = customizedViewItemIds;
    }

    public String[] getIsDeletes() {
        return isDeletes;
    }

    public void setIsDeletes(String[] isDeletes) {
        this.isDeletes = isDeletes;
    }

    public String getCustomizedViewId() {
        return customizedViewId;
    }

    public void setCustomizedViewId(String customizedViewId) {
        this.customizedViewId = customizedViewId;
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

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] getSearchConditions() {
        return searchConditions;
    }

    public void setSearchConditions(String[] searchConditions) {
        this.searchConditions = searchConditions;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }
}
