package com.augmentum.ams.util;

import java.util.Map;

public class ExceptionHelper extends Exception{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private Map<String, ExceptionHelper> errorCodes;
    
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Map<String, ExceptionHelper> getErrorCodes() {
		return errorCodes;
	}

	public void setErrorCodes(Map<String, ExceptionHelper> errorCodes) {
		this.errorCodes = errorCodes;
	}
	
	// Construction method

	public ExceptionHelper(String errorCode) {
        this.errorCode = errorCode;
    }
    
    public ExceptionHelper(Map<String, ExceptionHelper> errorCodes) {
    	this.errorCodes = errorCodes;
    }
}
