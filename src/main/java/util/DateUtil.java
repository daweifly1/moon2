package util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil
        extends DateUtils {
    public static final long ONE_DAY_SECONDS = 86400L;
    public static final String shortFormat = "yyyyMMdd";
    public static final String longFormat = "yyyyMMddHHmmss";
    public static final String webFormat = "yyyy-MM-dd";
    public static final String timeFormat = "HHmmss";
    public static final String monthFormat = "yyyyMM";
    public static final String chineseDtFormat = "yyyy年MM月dd日";
    public static final String newFormat = "yyyy-MM-dd HH:mm:ss";
    public static final String newFormat2 = "yyyy-MM-dd HH:mm";
    public static final String newFormat3 = "yyyy-MM-dd HH";
    public static final String FULL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static long ONE_DAY_MILL_SECONDS = 86400000L;

    public static Date getNowDate() {
        return new Date();
    }

    public static Date getDate(long millsecord) {
        return new Date(millsecord);
    }

    public static DateFormat getNewDateFormat(String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);

        df.setLenient(false);
        return df;
    }

    public static Date formatDate(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        ParsePosition pos = new ParsePosition(0);
        Date newDate = formatter.parse(dateString, pos);
        return newDate;
    }

    public static String format(Date date, String format) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat(format).format(date);
    }

    public static String formatByLong(long date, String format) {
        return new SimpleDateFormat(format).format(new Date(date));
    }

    public static String formatByString(String date, String format) {
        if (StringUtils.isNotBlank(date)) {
            return new SimpleDateFormat(format).format(new Date(
                    NumberUtils.toLong(date)));
        }
        return "";
    }

    public static String formatShortFormat(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("yyyyMMdd").format(date);
    }

    public static Date parseDateNoTime(String sDate)
            throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        if ((sDate == null) || (sDate.length() < "yyyyMMdd".length())) {
            throw new ParseException("length too little", 0);
        }
        if (!StringUtils.isNumeric(sDate)) {
            throw new ParseException("not all digit", 0);
        }
        return dateFormat.parse(sDate);
    }

    public static Date parseDateNoTime(String sDate, String format)
            throws ParseException {
        if (StringUtils.isBlank(format)) {
            throw new ParseException("Null format. ", 0);
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        if ((sDate == null) || (sDate.length() < format.length())) {
            throw new ParseException("length too little", 0);
        }
        return dateFormat.parse(sDate);
    }

    public static Date parseDateNoTimeWithDelimit(String sDate, String delimit)
            throws ParseException {
        sDate = sDate.replaceAll(delimit, "");

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        if ((sDate == null) || (sDate.length() != "yyyyMMdd".length())) {
            throw new ParseException("length not match", 0);
        }
        return dateFormat.parse(sDate);
    }

    public static Date parseDateLongFormat(String sDate) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date d = null;
        if ((sDate != null) && (sDate.length() == "yyyyMMddHHmmss".length())) {
            try {
                d = dateFormat.parse(sDate);
            } catch (ParseException ex) {
                return null;
            }
        }
        return d;
    }

    public static Date parseDateNewFormat(String sDate) {
        Date d = parseDateHelp(sDate, "yyyy-MM-dd HH:mm:ss");
        if (d != null) {
            return d;
        }
        d = parseDateHelp(sDate, "yyyy-MM-dd HH:mm");
        if (d != null) {
            return d;
        }
        d = parseDateHelp(sDate, "yyyy-MM-dd HH");
        if (d != null) {
            return d;
        }
        d = parseDateHelp(sDate, "yyyy-MM-dd");
        if (d != null) {
            return d;
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormat.parse(sDate);
        } catch (ParseException ex) {
        }
        return null;
    }

    private static Date parseDateHelp(String sDate, String format) {
        if ((sDate != null) && (sDate.length() == format.length())) {
            try {
                DateFormat dateFormat = new SimpleDateFormat(format);
                return dateFormat.parse(sDate);
            } catch (ParseException ex) {
                return null;
            }
        }
        return null;
    }

    public static Date addHours(Date date, long hours) {
        return addMinutes(date, hours * 60L);
    }

    public static Date addMinutes(Date date, long minutes) {
        return addSeconds(date, minutes * 60L);
    }

    public static Date addSeconds(Date date1, long secs) {
        return new Date(date1.getTime() + secs * 1000L);
    }

    public static boolean isValidHour(String hourStr) {
        if ((!StringUtils.isEmpty(hourStr)) && (StringUtils.isNumeric(hourStr))) {
            int hour = new Integer(hourStr).intValue();
            if ((hour >= 0) && (hour <= 23)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidMinuteOrSecond(String str) {
        if ((!StringUtils.isEmpty(str)) && (StringUtils.isNumeric(str))) {
            int hour = new Integer(str).intValue();
            if ((hour >= 0) && (hour <= 59)) {
                return true;
            }
        }
        return false;
    }

    public static Date addDays(Date date1, long days) {
        return addSeconds(date1, days * 86400L);
    }

    public static Date addDaysFromNow(long days) {
        return addSeconds(new Date(System.currentTimeMillis()), days *
                86400L);
    }

    public static String getTomorrowDateString(String sDate)
            throws ParseException {
        Date aDate = parseDateNoTime(sDate);

        aDate = addSeconds(aDate, 86400L);

        return getDateString(aDate);
    }

    public static String getLongDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

        return getDateString(date, dateFormat);
    }

    public static String getNewFormatDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return getDateString(date, dateFormat);
    }

    public static String getFullDateFormat(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return getDateString(date, dateFormat);
    }

    public static String getDateString(Date date, DateFormat dateFormat) {
        if ((date == null) || (dateFormat == null)) {
            return null;
        }
        return dateFormat.format(date);
    }

    public static String getYesterDayDateString(String sDate)
            throws ParseException {
        Date aDate = parseDateNoTime(sDate);

        aDate = addSeconds(aDate, -86400L);

        return getDateString(aDate);
    }

    public static String getDateString(Date date) {
        DateFormat df = getNewDateFormat("yyyyMMdd");

        return df.format(date);
    }

    public static String getWebDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat("yyyy-MM-dd");

        return getDateString(date, dateFormat);
    }

    public static String getChineseDateString(Date date) {
        DateFormat dateFormat = getNewDateFormat("yyyy年MM月dd日");

        return getDateString(date, dateFormat);
    }

    public static String getTodayString() {
        DateFormat dateFormat = getNewDateFormat("yyyyMMdd");

        return getDateString(new Date(), dateFormat);
    }

    public static String getTimeString(Date date) {
        DateFormat dateFormat = getNewDateFormat("HHmmss");

        return getDateString(date, dateFormat);
    }

    public static String getBeforeDayString(int days) {
        Date date = new Date(System.currentTimeMillis() -
                ONE_DAY_MILL_SECONDS * days);
        DateFormat dateFormat = getNewDateFormat("yyyyMMdd");

        return getDateString(date, dateFormat);
    }

    public static long getDiffMilliseconds(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();

        sysDate.setTime(one);

        Calendar failDate = new GregorianCalendar();

        failDate.setTime(two);
        return sysDate.getTimeInMillis() - failDate.getTimeInMillis();
    }

    public static long getDiffSeconds(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();

        sysDate.setTime(one);

        Calendar failDate = new GregorianCalendar();

        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) / 1000L;
    }

    public static long getDiffMinutes(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();

        sysDate.setTime(one);

        Calendar failDate = new GregorianCalendar();

        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) /
                60000L;
    }

    public static long getDiffDays(Date one, Date two) {
        Calendar sysDate = new GregorianCalendar();

        sysDate.setTime(one);

        Calendar failDate = new GregorianCalendar();

        failDate.setTime(two);
        return (sysDate.getTimeInMillis() - failDate.getTimeInMillis()) /
                86400000L;
    }

    public static String getBeforeDayString(String dateString, int days) {
        Date date = null;
        DateFormat df = getNewDateFormat("yyyyMMdd");
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {

            date = new Date();
        }
        date = new Date(date.getTime() - ONE_DAY_MILL_SECONDS * days);
        date = new Date(date.getTime() - ONE_DAY_MILL_SECONDS * days);
        return df.format(date);
    }

    public static boolean isValidShortDateFormat(String strDate) {
        if (strDate.length() != "yyyyMMdd".length()) {
            return false;
        }
        try {
            Integer.parseInt(strDate);
        } catch (Exception NumberFormatException) {
            return false;
        }
        DateFormat df = getNewDateFormat("yyyyMMdd");
        try {
            df.parse(strDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidShortDateFormat(String strDate, String delimiter) {
        String temp = strDate.replaceAll(delimiter, "");

        return isValidShortDateFormat(temp);
    }

    public static boolean isValidLongDateFormat(String strDate) {
        if (strDate.length() != "yyyyMMddHHmmss".length()) {
            return false;
        }
        try {
            Long.parseLong(strDate);
        } catch (Exception NumberFormatException) {
            return false;
        }
        DateFormat df = getNewDateFormat("yyyyMMddHHmmss");
        try {
            df.parse(strDate);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    public static boolean isValidLongDateFormat(String strDate, String delimiter) {
        String temp = strDate.replaceAll(delimiter, "");

        return isValidLongDateFormat(temp);
    }

    public static String getShortDateString(String strDate) {
        return getShortDateString(strDate, "-|/");
    }

    public static String getShortDateString(String strDate, String delimiter) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        String temp = strDate.replaceAll(delimiter, "");
        if (isValidShortDateFormat(temp)) {
            return temp;
        }
        return null;
    }

    public static String getShortFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();

        cal.setTime(dt);
        cal.set(5, 1);

        DateFormat df = getNewDateFormat("yyyyMMdd");

        return df.format(cal.getTime());
    }

    public static String getWebTodayString() {
        DateFormat df = getNewDateFormat("yyyy-MM-dd");

        return df.format(new Date());
    }

    public static String getWebFirstDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        Date dt = new Date();

        cal.setTime(dt);
        cal.set(5, 1);

        DateFormat df = getNewDateFormat("yyyy-MM-dd");

        return df.format(cal.getTime());
    }

    public static String convert(String dateString, DateFormat formatIn, DateFormat formatOut) {
        try {
            Date date = formatIn.parse(dateString);

            return formatOut.format(date);
        } catch (ParseException e) {
        }
        return "";
    }

    public static String convert2WebFormat(String dateString) {
        DateFormat df1 = getNewDateFormat("yyyyMMdd");
        DateFormat df2 = getNewDateFormat("yyyy-MM-dd");

        return convert(dateString, df1, df2);
    }

    public static String convert2ChineseDtFormat(String dateString) {
        DateFormat df1 = getNewDateFormat("yyyyMMdd");
        DateFormat df2 = getNewDateFormat("yyyy年MM月dd日");

        return convert(dateString, df1, df2);
    }

    public static String convertFromWebFormat(String dateString) {
        DateFormat df1 = getNewDateFormat("yyyyMMdd");
        DateFormat df2 = getNewDateFormat("yyyy-MM-dd");

        return convert(dateString, df2, df1);
    }

    public static boolean webDateNotLessThan(String date1, String date2) {
        DateFormat df = getNewDateFormat("yyyy-MM-dd");

        return dateNotLessThan(date1, date2, df);
    }

    public static boolean dateNotLessThan(String date1, String date2, DateFormat format) {
        try {
            Date d1 = format.parse(date1);
            Date d2 = format.parse(date2);
            if (d1.before(d2)) {
                return false;
            }
            return true;
        } catch (ParseException e) {
        }
        return false;
    }

    public static String getEmailDate(Date today) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");

        String todayStr = sdf.format(today);
        return todayStr;
    }

    public static String getSmsDate(Date today) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日HH:mm");

        String todayStr = sdf.format(today);
        return todayStr;
    }

    public static String formatTimeRange(Date startDate, Date endDate, String format) {
        if ((endDate == null) || (startDate == null)) {
            return null;
        }
        String rt = null;
        long range = endDate.getTime() - startDate.getTime();
        long day = range /
                86400000L;
        long hour = range % 86400000L /
                3600000L;
        long minute = range % 3600000L /
                60000L;
        if (range < 0L) {
            day = 0L;
            hour = 0L;
            minute = 0L;
        }
        rt = format.replaceAll("dd", String.valueOf(day));
        rt = rt.replaceAll("hh", String.valueOf(hour));
        rt = rt.replaceAll("mm", String.valueOf(minute));

        return rt;
    }

    public static String formatMonth(Date date) {
        if (date == null) {
            return null;
        }
        return new SimpleDateFormat("yyyyMM").format(date);
    }

    public static Date getBeforeDate() {
        Date date = new Date();

        return new Date(date.getTime() - ONE_DAY_MILL_SECONDS);
    }

    public static String currentTime(String format) {
        if (StringUtils.isBlank(format)) {
            return format(new Date(), "yyyy-MM-dd HH:mm:ss");
        }
        return format(new Date(), format);
    }

    public static boolean isValidDateRange(Date startDate, Date endDate, boolean equalOK) {
        if ((startDate == null) || (endDate == null)) {
            return false;
        }
        if (equalOK) {
            if (startDate.equals(endDate)) {
                return true;
            }
        }
        if (endDate.after(startDate)) {
            return true;
        }
        return false;
    }

    public static boolean isToday(long time) {
        return isSameDay(new Date(time), new Date());
    }

    public static boolean isYesterday(long time) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(System.currentTimeMillis());
        if ((cal1.get(0) == cal2.get(0)) &&
                (cal1.get(1) == cal2.get(1))) {
            if (cal1.get(6) + 1 == cal2.get(6)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTomorrow(long time) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTimeInMillis(time);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTimeInMillis(System.currentTimeMillis());
        if ((cal1.get(0) == cal2.get(0)) &&
                (cal1.get(1) == cal2.get(1))) {
            if (cal1.get(6) - 1 == cal2.get(6)) {
                return true;
            }
        }
        return false;
    }

    public static boolean compareWithNow(long time, long interval) {
        if (System.currentTimeMillis() - time > interval) {
            return true;
        }
        return false;
    }

    public static long getDiffDaysWithNow(long target) {
        long t1 = target - System.currentTimeMillis();
        if (t1 < 0L) {
            return -1L;
        }
        return t1 / 86400000L;
    }

    public static long getPastDaysWithNow(long target) {
        long t1 = System.currentTimeMillis() - target;
        if (t1 < 0L) {
            return -1L;
        }
        return t1 / 86400000L;
    }

    public static String getDynamicLeftTime(long target) {
        long t1 = target - System.currentTimeMillis();
        if (t1 < 0L) {
            return "0";
        }
        long days = t1 / 86400000L;
        if (days > 0L) {
            return days + "天";
        }
        long hours = t1 / 3600000L;
        if (hours > 0L) {
            return hours + "小时";
        }
        long minutes = t1 / 60000L;
        if (minutes > 0L) {
            return minutes + "分钟";
        }
        long seconds = t1 / 1000L;
        if (seconds > 0L) {
            return seconds + "秒";
        }
        return "0";
    }

    public static String getDynamicPassTime(long target) {
        String meaningfulTimeStr = null;
        long curTime = System.currentTimeMillis();
        long timeGap = (curTime - target) / 1000L;
        if (timeGap > 172800L) {
            DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
            Date targetDate = new Date(target);
            meaningfulTimeStr = formater.format(targetDate);
        } else if ((timeGap > 86400L) && (timeGap <= 172800L)) {
            meaningfulTimeStr = "昨天";
        } else if ((timeGap > 3600L) && (timeGap <= 86400L)) {
            Integer hourNum = Integer.valueOf((int) (timeGap / 3600L));
            meaningfulTimeStr = hourNum + "小时前";
        } else if ((timeGap > 300L) && (timeGap <= 3600L)) {
            Integer minNum = Integer.valueOf((int) (timeGap / 60L));
            meaningfulTimeStr = minNum + "分钟前";
        } else if (timeGap <= 300L) {
            meaningfulTimeStr = "刚刚";
        }
        return meaningfulTimeStr;
    }

    public static Date getBeforeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, -1);
        date = calendar.getTime();
        return date;
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, -1);
        date = calendar.getTime();
        return date;
    }
}
