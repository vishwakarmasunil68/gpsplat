package com.voxtrail.gpstracking.testing;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.util.TagUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public class MapTestingActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap googleMap;
    ImageView central_marker;
    int initial_flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_testing);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap mMap) {
        this.googleMap = mMap;

//        LatLng origin = new LatLng(28.5281577, 77.203501);
//        LatLng destination = new LatLng(28.4908437, 77.0713523);
//
//        String url = getDirectionsUrl(origin, destination);
//
//        DownloadTask downloadTask = new DownloadTask();
//
//        // Start downloading json data from Google Directions API
//        downloadTask.execute(url);

        central_marker = (ImageView)findViewById(R.id.central_marker);
        final int[] init_loc = {0};
        final int final_loc = -300;

        final CountDownTimer timer = new CountDownTimer(300,300) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                init_loc[0] = 0;
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(central_marker, "translationY", final_loc, init_loc[0]);
                objectAnimatorY.setDuration(200);
                objectAnimatorY.start();
            }
        };


        mMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
            @Override
            public void onCameraMoveStarted(int i) {

                System.out.println("Camera started moving worked");
                timer.cancel();
                ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(central_marker, "translationY", init_loc[0], final_loc);
                objectAnimatorY.setDuration(200);
                objectAnimatorY.start();
                init_loc[0] = -300;

            }
        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                System.out.println("Camera idle worked");
                if(initial_flag!=0)
                {
                    System.out.println("Camera Setting timer now");
                    timer.cancel();
                    timer.start();
                }
                initial_flag++;
                System.out.println("Camera Value of initial_flag ="+initial_flag);
            }
        });

    }

    private class DownloadTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();


            parserTask.execute(result);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<LatLng>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<LatLng> doInBackground(String... jsonData) {

            JSONObject jObject;

            try {

                Log.d(TagUtils.getTag(), "jsondata:-" + jsonData[0]);

                jObject = new JSONObject(jsonData[0]);

                JSONArray jsonArray = jObject.optJSONArray("routes");
                JSONArray stepsArray = jsonArray.optJSONObject(0).optJSONArray("legs").optJSONObject(0).optJSONArray("steps");
                List<LatLng> latLngs=new ArrayList<>();
                for (int i = 0; i < stepsArray.length(); i++) {
                    JSONObject startingLatLong = stepsArray.optJSONObject(i).optJSONObject("start_location");
                    JSONObject endingLatLong = stepsArray.optJSONObject(i).optJSONObject("end_location");
                    LatLng latLng=new LatLng(Double.parseDouble(startingLatLong.optString("lat")),
                            Double.parseDouble(startingLatLong.optString("lng")));
                    LatLng endinglatLng=new LatLng(Double.parseDouble(endingLatLong.optString("lat")),
                            Double.parseDouble(endingLatLong.optString("lng")));
                    latLngs.add(latLng);
                    latLngs.add(endinglatLng);
                }

                return latLngs;
//                DirectionsJSONParser parser = new DirectionsJSONParser();
//
//                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<LatLng> result) {
            if (result != null) {
                ArrayList points = null;
                PolylineOptions lineOptions = null;
                MarkerOptions markerOptions = new MarkerOptions();

                points = new ArrayList();
                lineOptions = new PolylineOptions();


                for (int j = 0; j < result.size(); j++) {
                    points.add(result.get(j));
                }

                lineOptions.addAll(points);
                lineOptions.width(12);
                lineOptions.color(Color.RED);
                lineOptions.geodesic(true);


// Drawing polyline in the Google Map for the i-th route
                googleMap.addPolyline(lineOptions);

            }


        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";

        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;
        Log.d(TagUtils.getTag(), "directional url:-" + url);

        return url;
    }

    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

}
