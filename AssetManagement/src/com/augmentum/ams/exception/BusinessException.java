package com.augmentum.ams.exception;

public class BusinessException extends BaseException{

    private static final long serialVersionUID = 1L;

    public BusinessException(Exception e, String errorcode) {
        super(e, errorcode);
    }
    
    public BusinessException(String errorcode) {
        super(errorcode);
    }
    
    public BusinessException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    
    public BusinessException(Exception e, String errorCode, String errorMessage) {
        super(e,errorCode, errorMessage);
    }
}
