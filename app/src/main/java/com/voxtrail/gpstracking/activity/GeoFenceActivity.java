package com.voxtrail.gpstracking.activity;

import android.graphics.Rect;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.pojo.GeofencePOJO;
import com.voxtrail.gpstracking.util.Pref;
import com.voxtrail.gpstracking.util.StringUtils;
import com.voxtrail.gpstracking.util.TagUtils;
import com.voxtrail.gpstracking.util.UtilityFunction;
import com.voxtrail.gpstracking.webservice.WebServicesUrls;

import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeoFenceActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    @BindView(R.id.tv_altitude)
    TextView tv_altitude;
    @BindView(R.id.btn_get)
    Button btn_get;
    @BindView(R.id.viewPoint)
    View viewPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fence);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rect rectf = new Rect();
                viewPoint.getLocalVisibleRect(rectf);
                viewPoint.getGlobalVisibleRect(rectf);

                Log.d("WIDTH          :", String.valueOf(rectf.width()));
                Log.d("HEIGHT         :", String.valueOf(rectf.height()));
                Log.d("left           :", String.valueOf(rectf.left));
                Log.d("right          :", String.valueOf(rectf.right));
                Log.d("top            :", String.valueOf(rectf.top));
                Log.d("bottom         :", String.valueOf(rectf.bottom));
                Log.d("center x       :", String.valueOf(rectf.centerX()));
                Log.d("center y       :", String.valueOf(rectf.centerY()));

                android.graphics.Point centerPoint = new android.graphics.Point(rectf.centerX(), rectf.centerY());//center of Circle on the screen
                LatLng centerLatLang = googleMap.getProjection().fromScreenLocation(centerPoint);
                int radius = rectf.centerX();
                android.graphics.Point radiusPoint = new android.graphics.Point(centerPoint.x + radius, centerPoint.y);
                LatLng radiusLatLang = googleMap.getProjection().fromScreenLocation(radiusPoint);

                float[] results = new float[1];

                Location.distanceBetween(centerLatLang.latitude, centerLatLang.longitude, radiusLatLang.latitude, radiusLatLang.longitude, results);

                float radiusInMeters = results[0];
                Log.d(TagUtils.getTag(), "radius m:-" + radiusInMeters);

//                ToastClass.showLongToast(getApplicationContext(),"latitude:-"+centerLatLang.latitude+"\nlongitude:-"+centerLatLang.longitude+"\nradius:-"+radiusInMeters+
//                " m \ndiameter:-"+(radiusInMeters*2)+" m");
                createFence(String.valueOf(centerLatLang.latitude), String.valueOf(centerLatLang.longitude)
                        , "123456789", "73", "adcasdc", "", "", "", "");
            }
        });
    }

    public void createFence(String lat, String lng, String imei, String user_id, String address, String vehicle_num, String intime, String outtime, String duration) {

//        ArrayList<NameValuePair> nameValuePairs=new ArrayList<>();
//        nameValuePairs.add(new BasicNameValuePair("fenceID",""));
//        nameValuePairs.add(new BasicNameValuePair("X",lat));
//        nameValuePairs.add(new BasicNameValuePair("Y",lng));
//        nameValuePairs.add(new BasicNameValuePair("imei",imei));
//        nameValuePairs.add(new BasicNameValuePair("UserID",user_id));
//        nameValuePairs.add(new BasicNameValuePair("FenceAddress",address));
//        nameValuePairs.add(new BasicNameValuePair("VehicleNumber",vehicle_num));
//        nameValuePairs.add(new BasicNameValuePair("InTime",intime));
//        nameValuePairs.add(new BasicNameValuePair("OutTime",outtime));
//        nameValuePairs.add(new BasicNameValuePair("Duration",duration));
//
//        Map<String,String> headers=new HashMap<>();
//        headers.put("Authorization", Pref.GetStringPref(getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
//
//        new WebServiceBase(nameValuePairs, headers, this, new WebServicesCallBack() {
//            @Override
//            public void onGetMsg(String apicall, String response) {
//                try{
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        },"GET_HEADERS",true).execute(WebServicesUrls.RADIUS);




        final GeofencePOJO geofencePOJO=new GeofencePOJO();
//        geofencePOJO.setDuration("");
//        geofencePOJO.setFenceID("123");
//        geofencePOJO.setX(lat);
//        geofencePOJO.setY(lng);
//        geofencePOJO.setVehicleNumber("1234");
//        geofencePOJO.setFenceAddress("asdcasdc");
//        geofencePOJO.setInTime("");
//        geofencePOJO.setOutTime("");
//        geofencePOJO.setDuration("");


        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {

                try{
                    URL url = new URL(WebServicesUrls.RADIUS);
//                    URLConnection connection = url.openConnection();
                    HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
                    urlConnection.connect();
                    urlConnection.setRequestMethod("POST");
                    OutputStream outputStream=urlConnection.getOutputStream();


//                    connection.setr
//                    OutputStream outputStream=connection.getOutputStream();
//
                    ObjectOutputStream oos = new ObjectOutputStream(outputStream);
                    oos.writeObject(geofencePOJO);
                    oos.flush();
                    oos.close();


//                    PrintStream outStream = new PrintStream(connection.getOutputStream());
//                    outStream.println("string=" + stringToReverse);
//                    outStream.close();
//
//                    DataInputStream inStream = new DataInputStream(connection.getInputStream());
//                    String inputLine;
//
//                    while ((inputLine = inStream.readLine()) != null) {
//                        System.out.println(inputLine);
//                    }
//                    inStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();

//        RequestQueue queue = Volley.newRequestQueue(this);
//
//        StringRequest getRequest = new StringRequest(Request.Method.GET, WebServicesUrls.RADIUS,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Log.d(TagUtils.getTag(), "vehicle list:-" + response.toString());
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                    }
//                }
//        ) {
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String>  params = new HashMap<String, String>();
//                JSONObject object = null;
//                try{
//                    object = new JSONObject(geofencePOJO.toString());
//                }catch (Exception e){
//                }
//
//                JSONObject objectParams = new JSONObject(params);
//                params.put("", objectParams.toString());
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", Pref.GetStringPref(getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
//                UtilityFunction.printAllValues(headers);
//                return headers;
//            }
//        };
//        queue.add(getRequest);

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.e(TagUtils.getTag(), googleMap.getCameraPosition().target.toString());
            }
        });

    }
}
