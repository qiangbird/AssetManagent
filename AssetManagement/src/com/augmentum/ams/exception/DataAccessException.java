/*
 * @(#) DataAccessException.java 1.0 12/02/07
 * Copyright 2012 Augmentum, Inc. All rights reserved.
 * Training Project : Assets Management System
 */
package com.augmentum.ams.exception;

/**
 * <p>Title: DataAccessException</p>
 * <p>Description: the class about the exception of Data Access</p>
 * <p>Date: 12/02/07</p>
 * <p>Company: Augmentum</p> 
 * @author Linus.Wang
 * @version 1.0
 *
 */
public class DataAccessException extends Exception {
    private static final long serialVersionUID = -6552930849911381468L;

    public DataAccessException() {
        super();
    }

    public DataAccessException(String message, Throwable cause) {
        super(message, cause);

    }
    public DataAccessException(String message) {
        super(message);
    }

    public DataAccessException(Throwable cause) {
        super(cause);
    }

}

