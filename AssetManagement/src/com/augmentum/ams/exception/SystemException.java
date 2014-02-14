package com.augmentum.ams.exception;

public class SystemException extends BaseException{

    private static final long serialVersionUID = 1L;

    public SystemException(Exception e, String errorcode) {
        super(e, errorcode);
    }
    
    public SystemException(String errorcode) {
        super(errorcode);
    }
    
    public SystemException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    
    public SystemException(Exception e, String errorCode, String errorMessage) {
        super(e,errorCode, errorMessage);
    }
}
