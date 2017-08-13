package com.thundergodsoftware.todolistapp;

import android.graphics.Color;

import java.util.Date;

/**
 * Created by Marcus on 1/16/2017.
 */

public class Helpers {

    // convert milliseconds to minutes
    private static final long DATE_MAGIC = 1000*60;
    public static int DateToMinutes( Date date ) {
        long currentTime = 0L;
        if ( date != null )
        {
            currentTime = date.getTime();
            currentTime = currentTime / DATE_MAGIC;
        }
        return (int) currentTime;
    }
    public static Date MinutesToDate( int minutes ) {
        if ( minutes == 0)
        {
            return null;
        }
        else {
            long currentTime = (long) minutes * DATE_MAGIC;
            return new Date(currentTime);
        }
    }

}
