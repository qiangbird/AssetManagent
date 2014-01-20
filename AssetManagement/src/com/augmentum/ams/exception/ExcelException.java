package com.augmentum.ams.exception;

public class ExcelException extends BaseException{

    private static final long serialVersionUID = 1L;

    public ExcelException(Exception e, String errorcode) {
        super(e, errorcode);
    }
    
    public ExcelException(String errorcode) {
        super(errorcode);
    }
    
    public ExcelException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    
    public ExcelException(Exception e, String errorCode, String errorMessage) {
        super(e,errorCode, errorMessage);
    }
}
