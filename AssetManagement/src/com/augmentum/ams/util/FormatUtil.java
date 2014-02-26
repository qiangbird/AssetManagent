/**
 * 
 */
package com.augmentum.ams.util;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.augmentum.ams.constants.SystemConstants;

/**
 * @author Grylls.Xu
 * @time Sep 25, 2013 7:52:46 PM
 */
public class FormatUtil {

    public static String formatKeyword(String keyword) {

        keyword = keyword.trim().toLowerCase();
        String flags[] = { "\\", "+", "|", "!", "{", "}", "[", "]", "~", "^",
                ":", "\"", "AND", "OR", "NOT", "_", "-", "&", "#"};
        for (String flag : flags) {
            keyword = keyword.replace(flag, "\\" + flag);
        }
        return keyword;
    }

    public static String[] splitString(String param, String mark) {

        if (null == param || "".equals(param) || null == mark
                || "".equals(mark)) {
            return null;
        } else {
            return param.split(mark);
        }
    }

    public static String generateFileName(List<String> fileNameList) {

        StringBuilder newFileName = new StringBuilder();
        if (null == fileNameList || 0 == fileNameList.size()) {
            String prefix = UTCTimeUtil.formatDateToString(new Date());
            newFileName.append(prefix).append(SystemConstants.SPLIT_UNDERLINE)
                    .append("01");
        } else {

            String prefix = Collections.max(fileNameList).split(
                    SystemConstants.SPLIT_UNDERLINE)[0];
            String suffix = Collections.max(fileNameList).split(
                    SystemConstants.SPLIT_UNDERLINE)[1];
            Integer sequence = Integer.valueOf(suffix) + 1;

            if (sequence < 10) {
                suffix = "0" + sequence.toString();
            } else {
                suffix = sequence.toString();
            }
            newFileName.append(prefix).append(SystemConstants.SPLIT_UNDERLINE)
                    .append(suffix);
        }
        return newFileName.toString();
    }
}
