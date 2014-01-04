package com.augmentum.ams.exception;

/**
 * @author Rudy.Gao
 * @time Sep 27, 2013 2:49:47 PM
 */
public class DataException extends BaseException{

    private static final long serialVersionUID = 1L;

    public DataException(Exception e, String errorcode) {
	    super(e, errorcode);
    }
    
    public DataException(String errorcode) {
	    super(errorcode);
    }
	
	public DataException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	public DataException(Exception e, String errorCode, String errorMessage) {
		super(e,errorCode, errorMessage);
	}
}
