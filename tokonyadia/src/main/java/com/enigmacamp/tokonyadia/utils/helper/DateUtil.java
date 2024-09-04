package com.enigmacamp.tokonyadia.utils.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
    public static String getCurrentDate() {
        return java.time.LocalDate.now().toString();
    }

    public static Date parseDate(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Jakarta"));
        java.util.Date tempDate;
        try {
            tempDate = sdf.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return tempDate;
    }
}
