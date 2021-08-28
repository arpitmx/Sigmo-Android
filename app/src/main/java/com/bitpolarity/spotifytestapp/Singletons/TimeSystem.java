package com.bitpolarity.spotifytestapp.Singletons;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeSystem {

    private static TimeSystem single_instance = null;
    String[] month = {
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};


    public static TimeSystem getInstance() {
        if (single_instance == null) single_instance = new TimeSystem();
        return single_instance;
    }

    public String getTime_format_12h(){
        Date date = new Date();
        SimpleDateFormat formatTime = new SimpleDateFormat("hh.mm aa");
        return formatTime.format(date).replace(".",":");
    }


    public String get_date_day_time_12h(){

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        SimpleDateFormat formatTime = new SimpleDateFormat("hh.mm aa");
        // "Started on 24 August at 12:30 pm, Sunday "

        Format f = new SimpleDateFormat("EEEE");
        String str = f.format(new Date());

        String result = calendar.get(Calendar.DAY_OF_MONTH) + " " + month[calendar.get(Calendar.MONTH)] + " at " + formatTime.format(date).replace(".", ":") +
                ", " + str;

        return result;
    }

}