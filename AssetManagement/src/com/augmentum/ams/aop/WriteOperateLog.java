package com.augmentum.ams.aop;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.augmentum.ams.exception.AuthorityException;
import com.augmentum.ams.model.operationLog.OperationLog;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.service.operationLog.OperationLogService;
import com.augmentum.ams.util.CommonUtil;
import com.augmentum.ams.util.ErrorCodeUtil;

/**
 * @author pengjin
 * @version 2.1
 * @since 2.1
 */
@Aspect
@Component
public class WriteOperateLog {

    @Autowired
    private OperationLogService operationLogService;

    private Logger logger = Logger.getLogger(WriteOperateLog.class);

    @Before(value = "execution(* com.augmentum.ams.service..*(..))")
    public void writeLogInfo(JoinPoint joinPoint) throws Exception, IllegalAccessException {

        String temp = joinPoint.getStaticPart().toShortString();
        String longTemp = joinPoint.getStaticPart().toLongString();
        joinPoint.getStaticPart().toString();
        String classType = joinPoint.getTarget().getClass().getName();
        String methodName = temp.substring(10, temp.length() - 1);
        Class<?> className = Class.forName(classType);

        // log action
        @SuppressWarnings("rawtypes")
        Class[] args = new Class[joinPoint.getArgs().length];
        String[] sArgs = (longTemp.substring(longTemp.lastIndexOf("(") + 1, longTemp.length() - 2))
                .split(",");
        for (int i = 0; i < args.length; i++) {
            if (sArgs[i].endsWith("String[]")) {
                args[i] = Array.newInstance(Class.forName("java.lang.String"), 1).getClass();
            } else if (sArgs[i].endsWith("Long[]")) {
                args[i] = Array.newInstance(Class.forName("java.lang.Long"), 1).getClass();
            } else if (sArgs[i].indexOf(".") == -1) {
                if (sArgs[i].equals("int")) {
                    args[i] = int.class;
                } else if (sArgs[i].equals("char")) {
                    args[i] = char.class;
                } else if (sArgs[i].equals("float")) {
                    args[i] = float.class;
                } else if (sArgs[i].equals("long")) {
                    args[i] = long.class;
                }
            } else {
                args[i] = Class.forName(sArgs[i]);
            }
        }
        Method method = className.getMethod(
                methodName.substring(methodName.indexOf(".") + 1, methodName.indexOf("(")), args);

        // only intercept the methods which have OperationLogAnnotation
        if (method.isAnnotationPresent(OperationLogAnnotation.class)) {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();

            OperationLogAnnotation perationLogAnnotation = method
                    .getAnnotation(OperationLogAnnotation.class);
            String operateDescribe = perationLogAnnotation.operateDescribe();
            String operateObject = perationLogAnnotation.operateObject();
            String operateObjectId = perationLogAnnotation.operateObjectId();

            // get the parameter of the method
            String logArg = null;
            // if the method has only one parameter , replace it whit % in the
            // OperationLogAnnotation
            if (args.length == 1) {
                if (args[0].getName().equals("java.lang.Long")
                        || args[0].getName().equals("java.lang.Integer")
                        || args[0].getName().equals("java.lang.String")) {
                    logArg = String.valueOf((joinPoint.getArgs())[0]);
                } else if (args[0] == String[].class) {
                    String[] arrayArg = (String[]) (joinPoint.getArgs())[0];
                    logArg = Arrays.toString(arrayArg);
                } else if (args[0].getName().startsWith("com.augmentum.ams.model")) {
                    @SuppressWarnings("unchecked")
                    Method m = args[0].getMethod("getId");
                    logArg = String.valueOf(m.invoke(joinPoint.getArgs()[0]));
                }
                // transfer the % to id in the method
                if (!logArg.equals("null")) {
                    operateObjectId = operateObjectId.indexOf("%") != -1 ? operateObjectId.replace(
                            "%", logArg) : operateObjectId;
                }
            }
            OperationLog operationLog = null;
            User user = (User) SecurityUtils.getSubject().getPrincipal();

            if (user != null) {
                operationLog = new OperationLog(user.getUserName(), user.getUserId(),
                        operateDescribe, operateObject, operateObjectId);
                logger.info("The IP address is: " + CommonUtil.getIpAddress(request));
            } else {
                throw new AuthorityException(ErrorCodeUtil.DATA_NOT_EXIST,
                        "The current user is not in the Shiro!");
            }

            operationLogService.save(operationLog);
        }
    }
}