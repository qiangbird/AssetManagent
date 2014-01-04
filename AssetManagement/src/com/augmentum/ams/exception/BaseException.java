package com.augmentum.ams.exception;

/**
 * BaseException is the superclass of those exceptions that can be thrown during 
 * the normal operation of the Java Virtual Machine.
 * A method is not required to declare in its throws clause any subclasses of
 * RuntimeException that might be thrown during the execution of the method but
 * not caught.
 * 
 * @author Rudy.Gao
 * @time Sep 27, 2013 2:54:50 PM
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = -4368304810297242836L;

	private String errorCode;

	private Exception exception;
	
	private String errorMessage;

	/**
	 * @param e which cause exception
	 * @param errorCode which is defined in @ErrorCodeUtil
	 */
	public BaseException(Exception e, String errorCode) {
		this.errorCode = errorCode;
		this.exception = e;
	}
	
	public BaseException(Exception e) {
		this.exception = e;
	}

	public BaseException(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public BaseException(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public BaseException(Exception e, String errorCode, String errorMessage) {
		this.exception = e;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	/**
	 * @description errorCode which is defined in @ErrorCodeUtil
	 * @author Rudy.Gao
	 * @time Sep 28, 2013 8:34:15 AM
	 * @return String
	 */
	public String getErrorCode() {
		return errorCode;
	}

	public Exception getException() {
		return exception;
	}

	public String getErrorMessage() {
    	return errorMessage;
    }
	
}
