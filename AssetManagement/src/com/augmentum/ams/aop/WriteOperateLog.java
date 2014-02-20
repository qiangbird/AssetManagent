package com.augmentum.ams.aop;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

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

        // 日志动作
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
        // BaseController thisController = (BaseController) joinPoint
        // .getTarget();

        // 如果该方法写了注解才做操作
        if (method.isAnnotationPresent(OperationLogAnnotation.class)) {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();

            OperationLogAnnotation perationLogAnnotation = method
                    .getAnnotation(OperationLogAnnotation.class);
            String operateDescribe = perationLogAnnotation.operateDescribe();
            String operateObject = perationLogAnnotation.operateObject();
            String operateObjectId = perationLogAnnotation.operateObjectId();
            List<String> logArgs = null;

            // 多个参数
            /*
             * if (operateDescribe.indexOf("#") != -1) { logArgs = new
             * ArrayList<String>(); int startIndex =
             * operateDescribe.indexOf("#", 0); int endIndex =
             * operateDescribe.indexOf("#", startIndex + 1); while (startIndex
             * != -1) { String tempArg = operateDescribe.substring(startIndex +
             * 1, endIndex); startIndex = operateDescribe.indexOf("#", endIndex
             * + 1); endIndex = operateDescribe.indexOf("#", startIndex + 1);
             * logArgs.add(tempArg); } }
             */

            // 获取被注解方法的参数，实现动态注解
            String logArg = null;
            // 被注解方法只有一个参数的情况可用%替代要传入的参数
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
                    // 包含了两个操作的日志内容如save方法中包含了增加和修改操作，根据是否存在参数来判断是什么操作
                    if (operateDescribe.indexOf("|") != -1) {
                        if (!logArg.equals("null")) {
                            operateDescribe = operateDescribe.substring(operateDescribe
                                    .indexOf("|") + 1);
                        } else {
                            operateDescribe = operateDescribe.substring(0,
                                    operateDescribe.indexOf("|"));
                        }
                    }
                }
                // 将注解中%转换为被拦截方法参数中的id
                if (!logArg.equals("null")) {
                    operateObjectId = operateObjectId.indexOf("%") != -1 ? operateObjectId.replace(
                            "%", logArg) : operateObjectId;
                }
            } else {
                Object obj[] = joinPoint.getArgs();
                for (int k = 0; k < logArgs.size(); k++) {
                    for (int j = k; j < obj.length; j++) {
                        // 如果是实体
                        if (logArgs.get(k).startsWith("@")) {
                            if (args[j].getName().startsWith("com.augmentum.ams.model")) {
                                @SuppressWarnings("unchecked")
                                Method m = args[j].getMethod("getId");
                                logArg = String.valueOf(m.invoke(joinPoint.getArgs()[j]));
                                // 包含了两个操作的日志内容如save方法中包含了增加和修改操作，根据是否存在参数来判断是什么操作
                                if (!logArg.equals("null")) {
                                    operateDescribe = operateDescribe.substring(operateDescribe
                                            .indexOf("|") + 1);
                                } else {
                                    operateDescribe = operateDescribe.substring(0,
                                            operateDescribe.indexOf("|"));
                                }
                            } else {
                                continue;
                            }
                            // 数组
                        } else if (logArgs.get(k).startsWith("{1}")) {
                            String[] arrayArg = request.getParameterValues(logArgs.get(k)
                                    .substring(1));
                            logArg = Arrays.toString(arrayArg);
                            // String
                        } else {
                            logArg = request.getParameter(logArgs.get(k));

                            /*
                             * if (logArgs.get(k).equals("bsId") && logArg ==
                             * null) { logArg =
                             * SpringSecurityUtils.getCurrentAdmin()
                             * .getNwOfficeId().toString(); }
                             */
                            if (operateDescribe.indexOf("|") != -1) {
                                if (!logArg.equals("null")) {
                                    operateDescribe = operateDescribe.substring(operateDescribe
                                            .indexOf("|") + 1);
                                } else {
                                    operateDescribe = operateDescribe.substring(0,
                                            operateDescribe.indexOf("|"));
                                }
                            }
                        }
                        if (logArg == null || logArg.equals("null")) {
                            logArg = "";
                        }
                        operateDescribe = operateDescribe.replace("#" + logArgs.get(k) + "#",
                                logArg);
                        break;
                    }
                }
            }
            OperationLog operationLog = null;
            User user = (User) SecurityUtils.getSubject().getPrincipal();

            if (user != null) {
                operationLog = new OperationLog(user.getUserName(), user.getUserId(),
                        operateDescribe, operateObject, operateObjectId);
                logger.info("The IP address is: " + getIpAddr(request));
            } else {
                throw new AuthorityException(ErrorCodeUtil.DATA_NOT_EXIST,
                        "The current user is not in the Shiro!");
            }

            operationLogService.save(operationLog);
        }
    }

    public static String getIpAddr(HttpServletRequest request) {
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
        return ip;
    }
}