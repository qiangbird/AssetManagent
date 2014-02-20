package com.augmentum.ams.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
 * @author John Li 
 * @time 2014.02.19 
 */  
@Retention(RetentionPolicy.RUNTIME)  
@Target(ElementType.METHOD)  
public @interface OperationLogAnnotation {  
    
    String operateDescribe();  
    String operateObject();
    String operateObjectId();
}  
