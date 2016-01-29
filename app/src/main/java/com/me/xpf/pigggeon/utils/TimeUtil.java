package com.me.xpf.pigggeon.utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pengfeixie on 16/1/24.
 */
@SuppressWarnings("UnusedDeclaration")
public class TimeUtil {
    public static SimpleDateFormat sYMDFormat = new SimpleDateFormat("yyyy年MM月dd日");

    public static SimpleDateFormat sYMDHMSFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

    public static SimpleDateFormat sMDHMFormat = new SimpleDateFormat("MM月dd日 HH:mm");

    public static SimpleDateFormat sHMFormat = new SimpleDateFormat("HH:mm");

    public static String getCurrentDate() {
        if (sYMDFormat == null) {
            sYMDFormat = new SimpleDateFormat("yyyy年MM月dd日");
        }

        Date date = new Date(System.currentTimeMillis());
        return sYMDFormat.format(date);
    }

    public static String getSpecificDate(Date date) {
        if (sYMDFormat == null) {
            sYMDFormat = new SimpleDateFormat("yyyy年MM月dd日");
        }
        return sYMDFormat.format(date);
    }

    public static long getCurrentStamp() {
        return System.currentTimeMillis();
    }

    public static String getDateFromStamp(long stamptime) {
        String string = null;
        Timestamp timestamp = new Timestamp(stamptime);
        try {
            string = sMDHMFormat.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string;
    }

    public static boolean isLongEnough(long stamptime, long prestamptime) {
        if (stamptime - prestamptime > (20 * 60 * 1000)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return 2 明天
     */
    public static int whichDay(String time) {
        Calendar msgCalendar = Calendar.getInstance();
        Calendar nowCalendar = Calendar.getInstance();
        Date date = new Date();
        long now = System.currentTimeMillis();
        try {
            date = sYMDFormat.parse(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        msgCalendar.setTime(date);

        int nowDay = nowCalendar.get(Calendar.DAY_OF_YEAR);
        int msgDay = msgCalendar.get(Calendar.DAY_OF_YEAR);
        if (nowDay == msgDay) {
            return 1;
        } else if ((nowDay - msgDay) == 1) {
            return 0;
        } else if ((msgDay - nowDay) == 1) {
            return 2;
        }
        return -1;
    }
}
