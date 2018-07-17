package com.voxtrail.gpstracking.testing;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.SquareCap;
import com.google.gson.Gson;
import com.google.maps.android.ui.IconGenerator;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.pojo.VehicleHistoryPOJO;
import com.voxtrail.gpstracking.util.Pref;
import com.voxtrail.gpstracking.util.StringUtils;
import com.voxtrail.gpstracking.util.TagUtils;
import com.voxtrail.gpstracking.webservice.WebServicesUrls;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class MovingAPITesting extends AppCompatActivity implements OnMapReadyCallback {
    SupportMapFragment mapFragment;
    private GoogleMap mMap;
    private List<LatLng> polyLineList;
    private Marker marker;
    private float v;
    private double lat, lng;
    private Handler handler;
    private LatLng startPosition, endPosition;
    private int index, next;
    private LatLng sydney;
    private Button button;
    private Button btn_start;
    private SeekBar seekBar;
    private EditText destinationEditText;
    private String destination;
    private PolylineOptions polylineOptions, blackPolylineOptions;
    private Polyline blackPolyline, greyPolyLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moving_car);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        polyLineList = new ArrayList<>();
        button = (Button) findViewById(R.id.destination_button);
        btn_start = (Button) findViewById(R.id.btn_start);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        destinationEditText = (EditText) findViewById(R.id.edittext_place);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                destination = destinationEditText.getText().toString();
                destination = destination.replace(" ", "+");
                Log.d(TagUtils.getTag(), destination);
                mapFragment.getMapAsync(MovingAPITesting.this);
            }
        });

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_looping) {
                    is_looping = false;
                    btn_start.setText("start");
                } else {
                    is_looping = true;
                    if (handler != null && runnable != null) {
                        if(index>=(polyLineList.size()-1)){
                            index=0;
                            next=0;
                        }
                        btn_start.setText("Pause");
                        handler.postDelayed(runnable, 1000);
                    }
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                is_looping = true;
                next=seekBar.getProgress();
                index=seekBar.getProgress();
//                if (handler != null && runnable != null) {
//                    btn_start.setText("Pause");
//                    handler.postDelayed(runnable, 1000);
//                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final double latitude = 28.5281577;
        double longitude = 77.203501;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(false);
        mMap.setBuildingsEnabled(false);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Home and move the camera
        sydney = new LatLng(28.5281577, 77.203501);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Home"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                .target(googleMap.getCameraPosition().target)
                .zoom(17)
                .bearing(30)
                .tilt(45)
                .build()));

        setUpData();

    }

    boolean is_looping = true;
    Runnable runnable;

    public void setUpData() {
        try {
            String url = WebServicesUrls.getVehicleHistoryURL(String.valueOf("73"), "07/12/2018 00:00", "07/13/2018 23:59", "HistoryBetween");
            Log.d(TagUtils.getTag(), "url:-" + url);
            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TagUtils.getTag(), "vehicle list:-" + response.toString());
                            try {
                                mMap.clear();
                                is_looping = true;
                                handler = null;
                                runnable = null;
                                btn_start.setText("Pause");
                                JSONArray jsonArray = new JSONArray(response.toString());
                                List<VehicleHistoryPOJO> vehicleHistoryPOJOS = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    vehicleHistoryPOJOS.add(new Gson().fromJson(jsonArray.optJSONObject(i).toString(), VehicleHistoryPOJO.class));
                                }
                                List<LatLng> latLngs = new ArrayList<>();
                                for (VehicleHistoryPOJO vehicleHistoryPOJO : vehicleHistoryPOJOS) {
                                    latLngs.add(new LatLng(vehicleHistoryPOJO.getLat(), vehicleHistoryPOJO.getLng()));
                                }

                                polyLineList = latLngs;
                                seekBar.setMax(polyLineList.size());
                                if (latLngs.size() > 0) {
//                                JSONArray jsonArray = response.getJSONArray("routes");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject route = jsonArray.getJSONObject(i);
//                                    JSONObject poly = route.getJSONObject("overview_polyline");
//                                    String polyline = poly.getString("points");
//                                    polyLineList = decodePoly(polyline);
//                                    Log.d(TagUtils.getTag(), polyLineList + "");
//                                }
                                    //Adjusting bounds
                                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                                    for (LatLng latLng : polyLineList) {
                                        builder.include(latLng);
                                    }
                                    LatLngBounds bounds = builder.build();
                                    CameraUpdate mCameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 2);
                                    mMap.animateCamera(mCameraUpdate);

                                    polylineOptions = new PolylineOptions();
                                    polylineOptions.color(Color.GRAY);
                                    polylineOptions.width(10);
                                    polylineOptions.startCap(new SquareCap());
                                    polylineOptions.endCap(new SquareCap());
                                    polylineOptions.jointType(ROUND);
                                    polylineOptions.addAll(polyLineList);
                                    greyPolyLine = mMap.addPolyline(polylineOptions);

                                    blackPolylineOptions = new PolylineOptions();
                                    blackPolylineOptions.width(5);
                                    blackPolylineOptions.color(Color.BLACK);
                                    blackPolylineOptions.startCap(new SquareCap());
                                    blackPolylineOptions.endCap(new SquareCap());
                                    blackPolylineOptions.jointType(ROUND);
                                    blackPolyline = mMap.addPolyline(blackPolylineOptions);

                                    mMap.addMarker(new MarkerOptions()
                                            .position(polyLineList.get(polyLineList.size() - 1)));

                                    ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 100);
                                    polylineAnimator.setDuration(2000);
                                    polylineAnimator.setInterpolator(new LinearInterpolator());
                                    polylineAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                        @Override
                                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                            List<LatLng> points = greyPolyLine.getPoints();
                                            int percentValue = (int) valueAnimator.getAnimatedValue();
                                            int size = points.size();
                                            int newPoints = (int) (size * (percentValue / 100.0f));
                                            List<LatLng> p = points.subList(0, newPoints);
                                            blackPolyline.setPoints(p);
                                        }
                                    });
                                    polylineAnimator.start();
                                    marker = mMap.addMarker(new MarkerOptions().position(sydney)
                                            .flat(true)
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_truck)));
                                    handler = new Handler();
                                    index = -1;
                                    next = 1;

                                    runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            if (index == (polyLineList.size() - 1)) {
                                                is_looping = false;
                                                btn_start.setText("Play");
                                            }
                                            if (index < polyLineList.size() - 1) {
                                                index++;
                                                next = index + 1;
                                            }
                                            if (index < polyLineList.size() - 1) {
                                                startPosition = polyLineList.get(index);
                                                endPosition = polyLineList.get(next);
                                            }
                                            seekBar.setProgress(next);
                                            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 1);
                                            valueAnimator.setDuration(1000);
                                            valueAnimator.setInterpolator(new LinearInterpolator());
                                            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                                @Override
                                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                                    v = valueAnimator.getAnimatedFraction();
                                                    lng = v * endPosition.longitude + (1 - v)
                                                            * startPosition.longitude;
                                                    lat = v * endPosition.latitude + (1 - v)
                                                            * startPosition.latitude;
                                                    LatLng newPos = new LatLng(lat, lng);

//                                                TextView text = new TextView(getApplicationContext());

                                                    LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                    View view = inflater.inflate(R.layout.window_info, null);

                                                    ImageView iv_truck = view.findViewById(R.id.iv_truck);
                                                    iv_truck.setRotation(getBearing(startPosition, newPos));


//                                                text.setText(String.valueOf(lat)+" "+String.valueOf(lng));
//                                                text.setTextColor(Color.parseColor("#FFFFFF"));
                                                    IconGenerator generator = new IconGenerator(getApplicationContext());
                                                    generator.setBackground(getResources().getDrawable(R.drawable.window_transparent));
                                                    generator.setContentView(view);
                                                    Bitmap icon = generator.makeIcon();

//                                                MarkerOptions tp = new MarkerOptions().position(newPos).anchor(0.5f,0.5f).rotation(getBearing(startPosition,newPos)).icon(BitmapDescriptorFactory.fromBitmap(icon));

                                                    marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));

                                                    marker.setPosition(newPos);
                                                    marker.setAnchor(0.5f, 0.5f);
//                                                marker.setRotation(getBearing(startPosition, newPos));


                                                    mMap.moveCamera(CameraUpdateFactory
                                                            .newCameraPosition
                                                                    (new CameraPosition.Builder()
                                                                            .target(newPos)
                                                                            .zoom(20f)
                                                                            .build()));
                                                }
                                            });
                                            valueAnimator.start();
                                            if (is_looping) {
                                                handler.postDelayed(this, 1000);
                                            }
                                        }
                                    };

                                    handler.postDelayed(runnable, 1000);

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", Pref.GetStringPref(getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " Mwv_ya_cB6vPhZI8dPvKLSuceBV-HO1AgvS1F96H70KtDfdGlyhay3YSVYMyISKgB4Mcg7mlhMdNDEgiMCqUB-fTA5PmNLdUEC1CsJ7vN28lHH95egMQVwy6QFuBRHUNYnpwShg3wgt0W-EZIBf51v_XVrGpRCnPUzqQWJyuVShMS5X0QTv1B-n-PK--JZVQdB402aUkXkIg6LpXMJ3dnb90UyyT-6qIWi2deQXAp5xHz0sWmSuWcgEafGjZkezBUtbrDfZKIRMnWcjO0VubIVnCrDmu9eBRBUwQ4Faap2LiLLmDyTcgUhb_1g4QYpPicHR-qUIAtALqTZDOW2e2xsyGXg1QSMPhfj_fb8A1J-uIjh6lr1guEmttycRZnMGpd_sipiHJ_n96_iTyZ7ASWdnJoF-PF95Pu8vzbtqpCk_5k2GWjnrpDSuMu-27rEWz7C3SUpyf38paAbNaXu9O9IYCmRSH96XnskzqS_GESJVTd5catMTPEseCiG8xGrE-N2ioQjN2hPVWvCm-tQDEzQ");

                    return headers;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(getRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<LatLng> decodePoly(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    private float getBearing(LatLng begin, LatLng end) {
        double lat = Math.abs(begin.latitude - end.latitude);
        double lng = Math.abs(begin.longitude - end.longitude);

        if (begin.latitude < end.latitude && begin.longitude < end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)));
        else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90);
        else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
            return (float) (Math.toDegrees(Math.atan(lng / lat)) + 180);
        else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
            return (float) ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270);
        return -1;
    }

}
