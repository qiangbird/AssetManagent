package com.augmentum.ams.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.augmentum.ams.constants.SystemConstants;

public class UTCTimeUtil {

    private static Logger logger = Logger.getLogger(UTCTimeUtil.class);

    /**
     * Format string to date, date pattern is 'yyyy-MM-dd'
     * 
     * @param str
     * @return
     */
    public static Date formatStringToDate(String str) {

        Date date = null;

        if (null != str && !"".equals(str)) {
            SimpleDateFormat sdf = new SimpleDateFormat(
                    SystemConstants.DATE_DAY_PATTERN);

            try {
                date = sdf.parse(str);
            } catch (ParseException e) {
                logger.error("format string to date pattern error", e);
                return null;
            }
        }
        return date;
    }

    /**
     * Format string to date based on given pattern
     * 
     * @param str
     * @param pattern
     * @return
     */
    public static Date formatStringToDate(String str, String pattern) {

        Date date = null;

        if (null != str && !"".equals(str)) {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);

            try {
                date = sdf.parse(str);
            } catch (ParseException e) {
                logger.error("format string to date error", e);
                return null;
            }
        }
        return date;
    }

    /**
     * Format date to string, string pattern is 'yyyy-MM-dd'
     * 
     * @param date
     * @return
     */
    public static String formatDateToString(Date date) {

        if (null == date) {
            return "";
        }
        DateFormat df = new SimpleDateFormat(SystemConstants.DATE_DAY_PATTERN);
        return df.format(date);
    }

    /**
     * Format date to string based on given pattern
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDateToString(Date date, String pattern) {

        if (null == date) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    /**
     * transfer UTC time to local time, return date type
     * 
     * @param date
     * @param clientTimeOffset
     * @return date
     */
    public static Date utcToLocalTime(Date date, String clientTimeOffset) {

        if (null == date) {
            return null;
        }

        String[] timeOffset = clientTimeOffset
                .split(SystemConstants.SPLIT_COMMA);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int clientHour = calendar.get(Calendar.HOUR_OF_DAY)
                + Integer.parseInt(timeOffset[0]);
        int clientMinute = calendar.get(Calendar.MINUTE)
                + Integer.parseInt(timeOffset[1]);

        calendar.set(Calendar.HOUR_OF_DAY, clientHour);
        calendar.set(Calendar.MINUTE, clientMinute);

        return calendar.getTime();
    }

    /**
     * transfer UTC time to local time, format date to string based on given
     * pattern return string type
     * 
     * @param date
     * @param clientTimeOffset
     * @param pattern
     * @return string
     */
    public static String utcToLocalTime(Date date, String clientTimeOffset,
            String pattern) {

        if (null == date) {
            return "";
        }
        String[] timeOffset = clientTimeOffset
                .split(SystemConstants.SPLIT_COMMA);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int clientHour = calendar.get(Calendar.HOUR_OF_DAY)
                + Integer.parseInt(timeOffset[0]);
        int clientMinute = calendar.get(Calendar.MINUTE)
                + Integer.parseInt(timeOffset[1]);

        calendar.set(Calendar.HOUR_OF_DAY, clientHour);
        calendar.set(Calendar.MINUTE, clientMinute);

        return formatDateToString(calendar.getTime(), pattern);

    }

    /**
     * transfer given date to UTC time, date type is 'yyyy-MM-dd'
     * 
     * @param localDate
     * @return
     */
    public static Date localDateToUTC(String localDate) {
        if (StringUtils.isBlank(localDate)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatStringToDate(localDate,
                SystemConstants.DATE_DAY_PATTERN));
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return calendar.getTime();

    }

    public static Date localDateToUTCForSearchIndex(String localDate) {

        if (StringUtils.isBlank(localDate)) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatStringToDate(localDate,
                SystemConstants.TIME_SECOND_PATTERN));
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return calendar.getTime();

    }

    /**
     * transfer current time to UTC time
     * 
     * @return
     */
    public static Date localDateToUTC() {
        Calendar calendar = Calendar.getInstance();
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return calendar.getTime();
    }
    
    public static String formatMaxUTCTimeForSearch(String localDate) {
        
        if (StringUtils.isBlank(localDate)) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatStringToDate(localDate,
                SystemConstants.TIME_SECOND_PATTERN));
        
        int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);
        int dstOffset = calendar.get(Calendar.DST_OFFSET);
        calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        Date date = calendar.getTime();
        
        return formatDateToString(date, SystemConstants.FILTER_TIME_PATTERN);
    }
    
    // transfer fromTime to string 'yyyyMMddHHmmss', it's lucene date index
    // type.
    // used for search condition
    public static String formatFilterTime(String filterTime) {

        if (StringUtils.isBlank(filterTime)) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        Date firstFormatDate = UTCTimeUtil.localDateToUTC(filterTime);
        
        String secondTimeString = UTCTimeUtil.formatDateToString(
                firstFormatDate, SystemConstants.TIME_SECOND_PATTERN);
        Date secondFormatDate = UTCTimeUtil
                .localDateToUTCForSearchIndex(secondTimeString);
        String newFilterTime = UTCTimeUtil.formatDateToString(secondFormatDate,
                SystemConstants.FILTER_TIME_PATTERN);
        stringBuilder.append(newFilterTime);
        return stringBuilder.toString();

    }

    // transfer fromTime to string 'yyyyMMddHHmmss', it's lucene date index
    // type.
    // used for search condition
    public static String formatFromFilterTime(String filterTime) {

        if (StringUtils.isBlank(filterTime)) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        Date originDate = UTCTimeUtil.formatStringToDate(filterTime, SystemConstants.DATE_DAY_PATTERN);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(originDate);
        cal.add(Calendar.DAY_OF_MONTH, 0);
        Date date = cal.getTime();
        
        String time = UTCTimeUtil.formatDateToString(date, SystemConstants.DATE_DAY_PATTERN);
        
        Date firstFormatDate = UTCTimeUtil.localDateToUTC(time);
        
        String secondTimeString = UTCTimeUtil.formatDateToString(
                firstFormatDate, SystemConstants.TIME_SECOND_PATTERN);
        Date secondFormatDate = UTCTimeUtil
                .localDateToUTCForSearchIndex(secondTimeString);
        String newFilterTime = UTCTimeUtil.formatDateToString(secondFormatDate,
                SystemConstants.FILTER_TIME_PATTERN);
        stringBuilder.append(newFilterTime);
        return stringBuilder.toString();

    }
    
    // transfer toTime to string 'yyyyMMddHHmmss', it's lucene date index
    // type.
    // used for search condition
    public static String formatToFilterTime(String filterTime) {

        if (StringUtils.isBlank(filterTime)) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();
        Date originDate = UTCTimeUtil.formatStringToDate(filterTime, SystemConstants.DATE_DAY_PATTERN);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(originDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        Date date = cal.getTime();
        
        String time = UTCTimeUtil.formatDateToString(date, SystemConstants.DATE_DAY_PATTERN);
        
        Date firstFormatDate = UTCTimeUtil.localDateToUTC(time);
        
        String secondTimeString = UTCTimeUtil.formatDateToString(
                firstFormatDate, SystemConstants.TIME_SECOND_PATTERN);
        Date secondFormatDate = UTCTimeUtil
                .localDateToUTCForSearchIndex(secondTimeString);
        String newFilterTime = UTCTimeUtil.formatDateToString(secondFormatDate,
                SystemConstants.FILTER_TIME_PATTERN);
        stringBuilder.append(newFilterTime);
        return stringBuilder.toString();

    }

    /**
     * 
     * @description convert date from database to UTC String
     * @author Jay.He
     * @time Dec 25, 2013 11:09:23 AM
     * @param date
     * @param timeOffset
     * @return
     */
    public static String formatUTCToLocalString(Date date, String timeOffset) {
        if (null != date) {
            return UTCTimeUtil.formatDateToString(
                    UTCTimeUtil.utcToLocalTime(date, timeOffset),
                    SystemConstants.DATE_DAY_PATTERN);
        } else {
            return null;
        }
    }

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    public static String formatUTCStringToLocalString(String utcString,
            String timeOffset, String pattern) {

        if (StringUtils.isBlank(utcString)) {
            return "";
        }
        Date utcTime = formatStringToDate(utcString,
                SystemConstants.TIME_SECOND_PATTERN);
        Date localTime = utcToLocalTime(utcTime, timeOffset);
        return formatDateToString(localTime, pattern);
    }

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    public static String getAssetExpiredTimeForFilterTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, SystemConstants.EXPIRED_TIME_SETTING);
        String s = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1)
                + "-" + cal.get(Calendar.DAY_OF_MONTH);
        return formatFilterTime(s);
    }

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    public static String formatCurrentTimeForFilterTime() {
        Calendar cal = Calendar.getInstance();
        String s = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1)
                + "-" + cal.get(Calendar.DAY_OF_MONTH);
        return formatFilterTime(s);
    }

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    public static Date getAssetExpiredTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, SystemConstants.EXPIRED_TIME_SETTING);
        String s = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1)
                + "-" + (cal.get(Calendar.DAY_OF_MONTH));
        return localDateToUTC(s);
    }

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    public static Date formatCurrentDateToUTC() {
        Calendar cal = Calendar.getInstance();
        String s = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1)
                + "-" + (cal.get(Calendar.DAY_OF_MONTH));
        return localDateToUTC(s);
    }

}
