/**
 * 
 */
package com.augmentum.ams.util;

/**
 * @author Grylls.Xu
 * @time Oct 16, 2013 7:27:33 PM
 */
public class Constant {

    // DateTime pattern
    public static final String TIME_SECOND_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String TIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String TIME_HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String DATE_DAY_PATTERN = "yyyy-MM-dd";
    public static final String DATE_MONTH_PATTERN = "yyyy-MM";
    public static final String DATE_YEAR_PATTERN = "yyyy";

    // max and min time for search range time
    public static final String SEARCH_MIN_DATE = "10010101000000000";
    public static final String SEARCH_MAX_DATE = "99991231000000000";

    public static final String FILTER_TIME_PATTERN = "yyyyMMddHHmmSS";
    public static final String FILTER_MILLISECOND_SUFFIX = "000";

    // UTC Zone
    public static final String UTC_ZONE = "UTC";

    // Yes or No Flag
    public static final String YES_FLAG = "Yes";
    public static final String NO_FLAG = "No";

    // split mark
    public static final String SPLIT_COMMA = ",";
    public static final String SPLIT_UNDERLINE = "_";
    
    // ownerShip
    public static final String OWNERSHIP_AUGMENTUM = "Augmentum";
    public static final String OWNERSHIP_CHARGETOCUSTOMER = "Charge to customer";
    public static final String OWNERSHIP_SHIPPEDBYCUSTOMER = "Shipped by customer";

    // Constant of CustomizeView
    public static final String OPERATOR_OR = "or";
    public static final String OPERATOR_AND = "and";

    // Constant of CustomizeViewItem
    public static final String IS = "is";
    public static final String IS_NOT = "is not";
    public static final String CONTAINS = "contains";
    public static final String NOT_CONTAINS = "not contains";
    public static final String START_WITH = "start with";
    public static final String LESS_THAN = "less than";
    public static final String GREATER_THAN = "greater than";
    public static final String LESS_OR_EQUAL = "less or equal";
    public static final String GREATER_OR_EQUAL = "greater or equal";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    
    //Constant of the Audit
    public static final String OPERATOR = "operator";
    public static final String OPERATION_TIME = "operationTime";
    public static final String FILE_NAME = "fileName";
    public static final String PERCENT_NUMBER = "percentNum";
    public static final String S_ECHO = "sEcho";
    public static final String I_DISPLAY_START = "iDisplayStart";
    public static final String I_DISPLAY_LENGTH = "iDisplayLength";
    public static final String I_TOTAL_RECORDS = "iTotalRecords";
    public static final String I_TOTAL_DISPLAY_RECORDS = "iTotalDisplayRecords";
    public static final String AA_DATA = "aaData";
    public static final String AUDIT = "audit";
    public static final String UNAUDIT = "unAudit";
    public static final String INCONSISTENT = "incons";
    public static final String SAVE = "save";
    public static final String UPDATE = "update";

    //Constant of import asset 
    public static final String CONFIG_TEMPLATES_PATH = "template/";
    public static final String CREATE = "create";
    public static final String UPDATE = "update";
}
