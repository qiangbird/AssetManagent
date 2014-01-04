package com.augmentum.ams.util;

public class ExceptionHelper extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String errorCode;
    
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


    public ExceptionHelper(String errorCode) {
        this.errorCode = errorCode;
    }
}
