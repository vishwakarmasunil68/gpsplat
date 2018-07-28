package com.voxtrail.gpstracking.testing;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.util.TagUtils;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GetLocationActivity extends AppCompatActivity {

    @BindView(R.id.btn_start)
    Button btn_start;
    @BindView(R.id.btn_stop)
    Button btn_stop;
    MySQLiteHelper db = new MySQLiteHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        ButterKnife.bind(this);

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (!isMyServiceRunning(LocationService.class)) {
//                    startService(new Intent(GetLocationActivity.this, LocationService.class));
//                }
//                getJSONFile(db.getAllLocations());
                exportDB();
//initiatePicker();

            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.getAllLocations();
                try {
                    stopService(new Intent(GetLocationActivity.this,LocationService.class));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void exportDB(){
        File sd = Environment.getExternalStorageDirectory();
        File data = Environment.getDataDirectory();
        FileChannel source=null;
        FileChannel destination=null;
        String currentDBPath = "/data/"+ "com.voxtrail.gpstracking" +"/databases/locDB";
        String backupDBPath = "locDB";
        File currentDB = new File(data, currentDBPath);
        File backupDB = new File(sd, backupDBPath);
        try {
            source = new FileInputStream(currentDB).getChannel();
            destination = new FileOutputStream(backupDB).getChannel();
            destination.transferFrom(source, 0, source.size());
            source.close();
            destination.close();
            Toast.makeText(this, "DB Exported!", Toast.LENGTH_LONG).show();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void initiatePicker() {
        new ImagePicker.Builder(this)
                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }


    public void getJSONFile(List<LocPOJO> locPOJOS){
        try {
            JSONObject jsonObject = new JSONObject();
            JSONArray jsonArray=new JSONArray();
            for(LocPOJO locPOJO:locPOJOS){
                JSONObject locJSON=new JSONObject();
                locJSON.put("id",locPOJO.getId());
                locJSON.put("lat",locPOJO.getLat());
                locJSON.put("lng",locPOJO.getLng());
                locJSON.put("alt",locPOJO.getAlt());
                locJSON.put("speed",locPOJO.getSpeed());
                locJSON.put("dt",locPOJO.getDt());
                jsonArray.put(locJSON);
            }

            jsonObject.put("locations",jsonArray);

            Log.d(TagUtils.getTag(),"locationstring:-"+jsonObject.toString());
            writeToFile(jsonArray.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void writeToFile(String data){
        try{
            File file = new File(Environment.getExternalStorageDirectory()+File.separator+"location.txt");
            FileWriter writer = new FileWriter(file);
            writer.append(data);
            writer.flush();
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}

//        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        LocationListener ll = new Mylocationlistener();
//
//
//        // ---Get the status of GPS---
//        boolean isGPS = lm
//                .isProviderEnabled(LocationManager.GPS_PROVIDER);
//
//        // If GPS is not enable then it will be on
//        if (!isGPS) {
//            Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
//            intent.putExtra("enabled", true);
//            sendBroadcast(intent);
//
//
//        }
//
//        //<--registers the current activity to be notified periodically by the named provider. Periodically,
//        //the supplied LocationListener will be called with the current Location or with status updates.-->
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
//    }
//
//    /**
//     *Mylocationlistener class will give the current GPS location
//     *with the help of Location Listener interface
//     */
//    private class Mylocationlistener implements LocationListener {
//        @Override
//        public void onLocationChanged(Location location) {
//            if (location != null) {
//                // ---Get current location latitude, longitude, altitude & speed ---
//
//                Log.d("LOCATION CHANGED", location.getLatitude() + "");
//                Log.d("LOCATION CHANGED", location.getLongitude() + "");
//                float speed = location.getSpeed();
//                double altitude = location.getAltitude();
////                Toast.makeText(GetLocationActivity.this,"Latitude = "+
////                                location.getLatitude() + "" +"Longitude = "+ location.getLongitude()+"Altitude = "+altitude+"Speed = "+speed,
////                        Toast.LENGTH_LONG).show();
//            }
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//        }
//    }
//}