package com.voxtrail.gpstracking.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

    public static void setPreValuesChecked(Context context){
        Pref.SetBooleanPref(context,StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION,true);
        Pref.SetBooleanPref(context,StringUtils.NOTIFICATION_VIBRATION,true);
        Pref.SetBooleanPref(context,StringUtils.NOTIFICATION_ALL_DAY,true);
        Pref.SetBooleanPref(context,StringUtils.ALERT_SOS,true);
        Pref.SetBooleanPref(context,StringUtils.ALERT_LOW_BATTERY,true);
        Pref.SetBooleanPref(context,StringUtils.ALERT_EXTERNAL_POWER_DISCONNECT,true);
        Pref.SetBooleanPref(context,StringUtils.ALERT_VIBRATION,true);
        Pref.SetBooleanPref(context,StringUtils.ALERT_GEOFENCE_IN,true);
        Pref.SetBooleanPref(context,StringUtils.ALERT_GEOFENCE_OUT,true);
        Pref.SetBooleanPref(context,StringUtils.ALERT_SPEEDING,true);
        Pref.SetBooleanPref(context,StringUtils.ALERT_TOWING,true);
        Pref.SetBooleanPref(context,StringUtils.ALERT_ENGINE_ON,true);
        Pref.SetBooleanPref(context,StringUtils.ALERT_ENGINE_OFF,true);
        Pref.SetStringPref(context,StringUtils.MONITOR_REFRESH_RATE,StringUtils.REF_10_SEC);
        Pref.SetStringPref(context,StringUtils.TRACKING_REFRESH_RATE,StringUtils.REF_10_SEC);
        Pref.SetStringPref(context,StringUtils.DISTANCE_METRICES,StringUtils.KILOMETER);
        Pref.SetStringPref(context,StringUtils.TEMPERATURE_METRICES,StringUtils.CELCIUS);
    }


    public static String getJSONString(Context context)
    {
        String str = "";
        try
        {
            AssetManager assetManager = context.getAssets();
            InputStream in = assetManager.open("json.txt");
            InputStreamReader isr = new InputStreamReader(in);
            char [] inputBuffer = new char[100];

            int charRead;
            while((charRead = isr.read(inputBuffer))>0)
            {
                String readString = String.copyValueOf(inputBuffer,0,charRead);
                str += readString;
            }
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace();
        }

        return str;
    }

    public static void writeToFile(String data,Context context) {
        try {
            String base_path=Environment.getExternalStorageDirectory()+File.separator+"output";
            File f=new File(base_path);
            f.mkdirs();

            String file_path=base_path+File.separator+System.currentTimeMillis()+"_oup.txt";

            FileWriter out = new FileWriter(new File(file_path));
            out.write(data);
            out.close();

            Log.d(TagUtils.getTag(),"file written to :-"+file_path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String vehicle_list="[{\"VehicleID\":81,\"VehicleActive\":false,\"VehicleMade\":null,\"VehicleType\":\"Bike\",\"Latitude\":28.168121,\"Longitude\":77.151561,\"Altitude\":null,\"Time\":\"0001-01-01T00:00:00\",\"Speed\":0,\"BatteryLevel\":0,\"FuelLevel\":0,\"Direction\":null,\"Signal\":null,\"DriverName\":null,\"ServiceDueDate\":\"0001-01-01T00:00:00\",\"LastServiceDate\":\"0001-01-01T00:00:00\",\"VehicleNumber\":\"DL3SDC5507\",\"VehicleModel\":null,\"Address\":null,\"Mileage\":55.0,\"TodaysMileage\":0.0,\"ExpiryDate\":\"0001-01-01T00:00:00\",\"MaxSpeed\":0,\"AccidentSensor\":false,\"Fast\":false,\"FuelSensor\":false,\"GeoOnlineFence\":false,\"OfflineGeoFence\":false,\"IgnitionWire\":false,\"MovementAlerts\":false,\"PanicButton\":false,\"Relay\":false,\"SpeedLimit\":0,\"SpeedWarnings\":false,\"StopLimit\":0,\"StopTimeGapMinutes\":0,\"TemperatureSensor\":false,\"Owner\":null,\"DeviceID\":null}]";

}
