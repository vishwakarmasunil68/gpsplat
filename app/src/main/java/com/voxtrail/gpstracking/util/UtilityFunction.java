package com.voxtrail.gpstracking.util;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class UtilityFunction {
    public static double[] getLocation(Context context) {
        GPSTracker gps;
        gps = new GPSTracker(context);
        double latitude = 0.00;
        double longitude = 0.00;
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Log.d(TagUtils.getTag(), "location:-latitude:-" + latitude);
            Log.d(TagUtils.getTag(), "location:-longitude:-" + longitude);

            Pref.SetStringPref(context, StringUtils.CURRENT_LATITUDE, String.valueOf(latitude));
            Pref.SetStringPref(context, StringUtils.CURRENT_LONGITUDE, String.valueOf(longitude));
        } else {
//            gps.showSettingsAlert();
        }

        double[] loc = new double[]{latitude, longitude};
        return loc;
    }

    public static double convertStringToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public static void printAllValues(Map<String, String> map) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // do stuff
            Log.d(TagUtils.getTag(), key + " : " + value);
        }
    }


    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String formatted_date = sdf.format(d);
        return formatted_date;
    }

    public static String getMMddYYYY() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        String formatted_date = sdf.format(d);
        return formatted_date;
    }


    public static String getCurrentDateTime() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formatted_date = sdf.format(d);
        return formatted_date;
    }

    public static String getMMddyyyyDT() {
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        String formatted_date = sdf.format(d);
        return formatted_date;
    }

    public static String converttoserverDateTime(String datetime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date givenDate = sdf.parse(datetime);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            return simpleDateFormat.format(givenDate);
        } catch (Exception e) {
            e.printStackTrace();
            return datetime;
        }
    }
    public static boolean checkValidDateTime(String datetime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
            Date givenDate = sdf.parse(datetime);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
