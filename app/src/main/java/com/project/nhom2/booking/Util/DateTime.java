package com.project.nhom2.booking.Util;

import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateTime {

    // chuyển mốc thời gian đặt phòng thành miliseconds
    public static long dateToMiliseconds(int day, int month, int year) {

        GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gc.clear();
        gc.set(year, month - 1, day);
        return gc.getTimeInMillis();
    }

    public static int configMonth(int x) {
        return x < 12 ? (x + 1) : x;
    }

}
