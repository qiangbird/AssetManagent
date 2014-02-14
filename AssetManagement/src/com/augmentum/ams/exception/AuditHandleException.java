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
public class AuditHandleException extends BaseException {

    private static final long serialVersionUID = -4666238025902144329L;

    public AuditHandleException(Exception e, String errorcode) {
        super(e, errorcode);
    }
    
    public AuditHandleException(String errorcode) {
        super(errorcode);
    }
    
    public AuditHandleException(String errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    
    public AuditHandleException(Exception e, String errorCode, String errorMessage) {
        super(e,errorCode, errorMessage);
    }

}
