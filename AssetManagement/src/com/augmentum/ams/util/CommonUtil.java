package com.augmentum.ams.util;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;

import com.augmentum.ams.model.enumeration.ProcessTypeEnum;

public class CommonUtil {
    static Logger logger = Logger.getLogger(CommonUtil.class);

    public static String stringToUTF8(String str) {
        String returnStr = "";
        try {
            returnStr = new String(str.getBytes("iso8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.info("Change encodig error!", e);
        }
        return returnStr;
    }
    
    public static String objectToString(Object object){
        if(object == null){
            return "";
        }else{
            return String.valueOf(object);
        }
    }
}
