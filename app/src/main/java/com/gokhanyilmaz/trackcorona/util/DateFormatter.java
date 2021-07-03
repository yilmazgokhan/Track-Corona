package com.gokhanyilmaz.trackcorona.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateFormatter {

    public String getFormattedDate(String date) {
        try {
            String serverDateFormat = "yyyy-MM-dd HH:mm:ss.SSSSSSSZ";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(serverDateFormat);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String formattedDate = DateFormat.getDateInstance(DateFormat.LONG).format(simpleDateFormat.parse(date));
            return formattedDate;
        } catch (Exception e) {
            return date;
        }
    }
}
