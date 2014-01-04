package com.augmentum.ams.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.augmentum.ams.service.BaseCaseTest;

public class UTCTimeUtilTest extends BaseCaseTest{

    // test Time_Zone = +8:00
    @Test
    public void testUTCToLocalTime() throws ParseException {
        String clientTimeOffset = "8,0";
        Date date = null;
        Date utcTime = UTCTimeUtil.formatStringToDate("2013-11-14 10:00:00", Constant.TIME_SECOND_PATTERN);
        date = UTCTimeUtil.utcToLocalTime(utcTime, clientTimeOffset);
        String localTime = UTCTimeUtil.formatDateToString(date, Constant.TIME_SECOND_PATTERN);
        Assert.assertEquals("2013-11-14 18:00:00", localTime);
    }
    
    // test Time_Zone = -5:30
    @Test
    public void testUTCToLocalTime1() throws ParseException {
        String clientTimeOffset = "-5,-30";
        Date date = null;
        Date utcTime = UTCTimeUtil.formatStringToDate("2013-11-14 10:00:00", Constant.TIME_SECOND_PATTERN);
        date = UTCTimeUtil.utcToLocalTime(utcTime, clientTimeOffset);
        String localTime = UTCTimeUtil.formatDateToString(date, Constant.TIME_SECOND_PATTERN);
        Assert.assertEquals("2013-11-14 04:30:00", localTime);
    }
    
    @Test
    public void testCurrentTimeToUTC() {
        
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.set(Calendar.HOUR_OF_DAY, hour - 8);
        Assert.assertEquals(0, calendar.getTime().compareTo(UTCTimeUtil.localDateToUTC()));
    }
    
    @Test
    public void testLocalDateToUTC() {
        
        // date pattern is 'yyyy-MM-dd'
        Calendar calendar = Calendar.getInstance();
        calendar.set(2013, 11, 12, 0, 0, 0);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 8);
        Assert.assertTrue(calendar.getTime().toString().equals(UTCTimeUtil.localDateToUTC("2013-12-12").toString()));
    }
}
