package com.augmentum.ams.util;

/**
 * @author Geoffrey.Zhao
 *
 */
public class LogHelper {

    public static String getLogInfo(String logMessage, Object... parameters) {
        StringBuilder logInfo = new StringBuilder();
        logInfo.append(logMessage);
        logInfo.append(" : ");
        
        for (Object object : parameters) {
            logInfo.append(object.toString());
            logInfo.append(", ");
        }
        return logInfo.toString().substring(0, logInfo.length() - 2);
    }
    
    public static void getLogWarn(String userId, String ipAddress, String logMessage) {
        
    }
    
    public static void getLogDebug() {
        
    }
    
    public static void getLogError() {
        
    }
    
}
