package com.bitpolarity.spotifytestapp.Singletons;

import java.text.SimpleDateFormat;
import java.util.Date;

class TimeSystem {

    private static TimeSystem single_instance = null;
    Date date = new Date();
    SimpleDateFormat formatTime = new SimpleDateFormat("hh.mm aa");


    public static TimeSystem getInstance() {
        if (single_instance == null) single_instance = new TimeSystem();
        return single_instance;
    }

    String getTime_format_12h(){
        return formatTime.format(date);
    }

}