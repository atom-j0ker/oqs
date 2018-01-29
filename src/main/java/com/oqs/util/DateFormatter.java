package com.oqs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static final String OLD_FORMAT = "dd-MM-yyyy";
    private static final String NEW_FORMAT = "yyyy-MM-dd";

    public static java.sql.Date format(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date date = sdf.parse(dateString);
        sdf.applyPattern(NEW_FORMAT);
        return new java.sql.Date(date.getTime());
    }

}
