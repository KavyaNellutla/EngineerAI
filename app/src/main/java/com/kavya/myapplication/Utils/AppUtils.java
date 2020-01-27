package com.kavya.myapplication.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppUtils {
    public static String getFormattedDate(String actualDate){
        String convertedDate="";
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'.'SSS'Z'", Locale.getDefault());
        DateFormat convertTo = new SimpleDateFormat("dd-MM-yyyy hh:mm a",Locale.getDefault());
        Date date = null;
        try {
            date= dateFormat.parse(actualDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            convertedDate= convertTo.format(date);
        }
        return convertedDate;
    }
}
