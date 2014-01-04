package com.augmentum.ams.exception;

/**
 * @author Rudy.Gao
 * @time Sep 27, 2013 2:50:24 PM
 */
public class AuthorityException extends BaseException{

    private static final long serialVersionUID = -4529873012715523511L;

    public AuthorityException(Exception e, String errorcode) {
	    super(e, errorcode);
    }

    public AuthorityException(String errorcode) {
	    super(errorcode);
    }
    
    public AuthorityException(String errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}
	
	public AuthorityException(Exception e, String errorCode, String errorMessage) {
		super(e,errorCode, errorMessage);
	}
}
