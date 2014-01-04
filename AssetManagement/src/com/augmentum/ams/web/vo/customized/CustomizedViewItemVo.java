package com.augmentum.ams.web.vo.customized;

import com.augmentum.ams.model.enumeration.SearchConditionEnum;

/**
 * 
 * @author John.Li
 * @time Oct 31, 2013 11:07:50 AM
 */
public class CustomizedViewItemVo {
    
    private SearchConditionEnum SearchCondition;
    
    private String columnName;
    
    private String value;

    public SearchConditionEnum getSearchCondition() {
        return SearchCondition;
    }

    public void setSearchCondition(SearchConditionEnum searchCondition) {
        SearchCondition = searchCondition;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    
}
