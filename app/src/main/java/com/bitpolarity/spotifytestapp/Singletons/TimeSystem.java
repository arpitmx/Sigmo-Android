package com.bitpolarity.spotifytestapp.Singletons;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSystem {

    private static TimeSystem single_instance = null;



    public static TimeSystem getInstance() {
        if (single_instance == null) single_instance = new TimeSystem();
        return single_instance;
    }

    public String getTime_format_12h(){
        Date date = new Date();
        SimpleDateFormat formatTime = new SimpleDateFormat("hh.mm aa");
        return formatTime.format(date).replace(".",":");
    }

}