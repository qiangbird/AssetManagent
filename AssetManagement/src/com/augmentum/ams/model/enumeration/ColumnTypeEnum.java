package com.augmentum.ams.model.enumeration;


/**
 * @author John.Li
 * @timme Oct 31, 2013 9:38:30 AM
 */
public enum ColumnTypeEnum {
    
    INT_TYPE("int"),
    
    STRING_TYPE("string"),
    
    DATE_TYPE("date"),
    
    BOOLEAN_TYPE("boolean");
    
    private String columnType;

    private ColumnTypeEnum(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnType() {
        return columnType;
    }

    public static ColumnTypeEnum getColumnTypeEnum(String columnType) {
        ColumnTypeEnum columnTypeEnum = null;
        for (ColumnTypeEnum pt : ColumnTypeEnum.values()) {
            if (pt.getColumnType() == columnType) {
                columnTypeEnum = pt;
                break;
            }
        }
        return columnTypeEnum;
    }
}
