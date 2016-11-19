package com.app_cpslab.android.evinond;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Simon on 8/14/2016.
 */
public class Utils {

    Calendar calander;
    SimpleDateFormat simpledateformat;

    public static String getDeviceUuid(Context context) {
        String deviceID;


        // Get Device ID
        deviceID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return deviceID;


    }


    public static String printStandardDate(Context date) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());

        return currentDateandTime;

    }
}