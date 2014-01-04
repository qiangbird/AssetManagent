package com.augmentum.ams.model.enumeration;


/**
 * @author John.Li
 * @timme Oct 31, 2013 9:38:30 AM
 */
public enum OperatorsEnum {
    
    AND("and"),
    
    OR("or");
    
    private String operator;
    
    private OperatorsEnum(String operator) {
        this.operator = operator;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public static OperatorsEnum getOperatorsEnum(String operator) {
        
        OperatorsEnum operatorsEnum = null;
        for(OperatorsEnum cvo : OperatorsEnum.values()) {
            if (cvo.getOperator().equalsIgnoreCase(operator)) {
                operatorsEnum = cvo;
                break;
            }
        }
        return operatorsEnum;
    }
    
}
