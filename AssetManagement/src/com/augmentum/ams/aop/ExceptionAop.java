package com.augmentum.ams.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.augmentum.ams.exception.BaseException;


/**
 * This class is to make aop for system
 * 
 */
@Aspect
@Component
public class ExceptionAop {

    private static Logger logger = Logger.getLogger(ExceptionAop.class);

    /**
     * This method to catch system throw exception, and encapsulates it to
     * BaseException then throw it, so that system just need to catch
     * BaseException
     * 
     * @param joinPoint
     *            which is aop joinPoint message
     * @param e
     *            which causes exception
     * @throws BaseException 
     */
    @AfterThrowing(value = "execution(* com.augmentum.ams..*(..)) && !within(ExceptionAop)", throwing = "e")
    public void catchException(JoinPoint joinPoint, Exception e) {
    	
    	StringBuilder logInfo = getMethodAndParams(joinPoint);
        if((e instanceof BaseException)) {
        	BaseException baseException = (BaseException) e;
            logger.error(logInfo+"\n"+ baseException.getErrorMessage()+"\n"+baseException);
            logger.error(e.getMessage(), e);
            //Encapsulate primitive exception to BaseException then throw it
        }
        else {
        	logger.error(logInfo+"\n"+e);
        }
        
    }

	/**
     * @description get JoinPoint method and its parameters
     * @author Rudy.Gao
     * @time Sep 28, 2013 4:04:34 PM
     * @param joinPoint
     * @return
     */
    private StringBuilder getMethodAndParams(JoinPoint joinPoint) {
	    StringBuilder logInfo = new StringBuilder();

	    //Get execute class and its method 
	    logInfo.append("Execute method : " + joinPoint.getTarget().getClass()
	            + "." + joinPoint.getSignature().getName() + "()");
	    
	    //Get execute method's args value 
	    logInfo.append(" and the arg is: ");
	    for(Object arg : joinPoint.getArgs()) {
	        logInfo.append(String.valueOf(arg)+"\t");
	    }
	    return logInfo;
    }
}
