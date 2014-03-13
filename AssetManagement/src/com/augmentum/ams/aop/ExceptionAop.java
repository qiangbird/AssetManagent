package com.augmentum.ams.aop;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.user.User;

/**
 * This class is to make aop for system
 * 
 */
@Aspect
@Component
public class ExceptionAop {

    private static Logger logger = Logger.getLogger(ExceptionAop.class);

    @Before(value = "execution(* com.augmentum.ams..*(..)) && !within(ExceptionAop)")
    public void testDoLog(JoinPoint point) {

        StringBuilder logInfo = getMethodAndParams(point);
        logInfo.append(" Start...");

        logger.info(logInfo);
    }

    @AfterReturning(value = "execution(* com.augmentum.ams..*(..)) && !within(ExceptionAop)")
    public void doSystemLog(JoinPoint point) {

        logger.info(point.getSignature().getName() + "()  End...");
    }

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

        setUserAndIP();

        if ((e instanceof BaseException)) {
            BaseException baseException = (BaseException) e;
            logger.error("Error code: " + ((BaseException) e).getErrorCode()
                    + ",\t Error message: " + baseException.getErrorMessage() + "\n"
                    + "Origianl exception: " + baseException.getException().getStackTrace() + "\n"
                    + baseException);
            logger.error(e.getMessage(), e);
        } else {
            logger.error(e.getMessage(), e);
        }

    }

    private void setUserAndIP() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String ip = getIpAddress(request);

        User user = (User) SecurityUtils.getSubject().getPrincipal();
        String loginName;

        if (user != null) {
            loginName = user.getUserName();
        } else {
            loginName = "anonymousUser";
        }
        logger.error("Login user: " + loginName + ",\t" + "IP address: " + ip);
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

        // Get execute class and its method
        logInfo.append("Execute method : " + joinPoint.getTarget().getClass() + "."
                + joinPoint.getSignature().getName() + "()");

        // Get execute method's args value
        logInfo.append(" and the arg is: ");
        for (Object arg : joinPoint.getArgs()) {
            logInfo.append(String.valueOf(arg) + "\t");
        }
        return logInfo;
    }

    public String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("http_client_ip");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip != null && ip.indexOf(",") != -1) {
            ip = ip.substring(ip.lastIndexOf(",") + 1, ip.length()).trim();
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
