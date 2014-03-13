package com.augmentum.ams.util;

import org.apache.log4j.Logger;

public class CommonUtil {
    static Logger logger = Logger.getLogger(CommonUtil.class);

    public static String objectToString(Object object) {
        if (object == null) {
            return "";
        } else {
            return String.valueOf(object);
        }
    }
}
