/*
 * @(#) AuditHandleException 1.0 12/03/23
 * Copyright 2012 Augmentum, Inc. All rights reserved.
 * Training Project : Assets Management System
 */
package com.augmentum.ams.exception;

/**
 * <p>Title: AuditHandleException</p>
 * <p>Description: the class about the exception of Audit Handle</p>
 * <p>Date: 12/02/07</p>
 * <p>Company: Augmentum</p> 
 * @author Lilian.Feng
 * @version 1.0
 *
 */
public class AuditHandleException extends Exception {

    private static final long serialVersionUID = -4666238025902144329L;

    public AuditHandleException() {
        super();
    }

    public AuditHandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuditHandleException(String message) {
        super(message);
    }

    public AuditHandleException(Throwable cause) {
        super(cause);
    }

}
