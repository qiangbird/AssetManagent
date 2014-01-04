package com.augmentum.ams.exception;

/**
 * @author Rudy.Gao
 * @time Sep 27, 2013 2:50:07 PM
 */
public class ParameterException extends BaseException{

    private static final long serialVersionUID = -5488824506686142281L;

    public ParameterException(Exception e, String errorcode) {
	    super(e, errorcode);
    }
    
    public ParameterException(String errorcode) {
	    super(errorcode);
    }
    
    public ParameterException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	public ParameterException(Exception e, String errorCode, String errorMessage) {
		super(e,errorCode, errorMessage);
	}

}
