package com.augmentum.ams.exception;

public class ValidatorException extends BaseException{

    private static final long serialVersionUID = 1L;

    public ValidatorException(Exception e, String errorcode) {
        super(e, errorcode);
    }
    
    public ValidatorException(String errorcode) {
        super(errorcode);
    }
    
    public ValidatorException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    
    public ValidatorException(Exception e, String errorCode, String errorMessage) {
        super(e,errorCode, errorMessage);
    }
}
