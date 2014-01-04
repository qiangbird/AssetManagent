package com.augmentum.ams.model.enumeration;


/**
 * @author John.Li
 * @timme Oct 31, 2013 9:38:30 AM
 */
public enum SearchConditionEnum {
    
    IS("is"),
    
    IS_NOT("is not"),
    
    LESS_THAN("less than"),
    
    GREATER_THAN("greater than"),
    
    LESS_OR_EQUAL("less or equal"),
    
    GREATER_OR_EQUAL("greater or equal");
    
    private String searchCondition;
    
    private SearchConditionEnum(String searchCondition) {
        this.searchCondition = searchCondition;
    }
    
    public String getSearchCondition() {
        return searchCondition;
    }
    
    public static SearchConditionEnum getSearchConditionEnum(String searchCondition) {
        
        SearchConditionEnum searchConditionEnum = null;
        for(SearchConditionEnum cvo : SearchConditionEnum.values()) {
            if (cvo.getSearchCondition().equalsIgnoreCase(searchCondition)) {
                searchConditionEnum = cvo;
                break;
            }
        }
        return searchConditionEnum;
    }
    
}
