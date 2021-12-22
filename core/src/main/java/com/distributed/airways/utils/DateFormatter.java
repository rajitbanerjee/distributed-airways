package com.distributed.airways.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static final String FULL_DAY_NAME = "EEEE";

    public static String dateToDayOfWeek(String yyyyMMdd) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(yyyyMMdd);
        return new SimpleDateFormat(FULL_DAY_NAME).format(date);
    }
}
