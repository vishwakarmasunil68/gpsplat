package com.voxtrail.gpstracking.testing;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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
import com.voxtrail.gpstracking.util.Constants;
import com.voxtrail.gpstracking.util.TagUtils;
import com.voxtrail.gpstracking.util.UtilityFunction;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.google.android.gms.maps.model.JointType.ROUND;

public class PlayBackTesting extends AppCompatActivity implements OnMapReadyCallback, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    GoogleMap googleMap;
    private Marker marker;
    private PolylineOptions polylineOptions, blackPolylineOptions;
    private float v;
    private double lat, lng;
    private Handler handler;
    private LatLng startPosition, endPosition;
    private int index, next;
    private Button button;
    private EditText destinationEditText;
    private String destination;
    private Polyline blackPolyline, greyPolyLine;

    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.iv_play)
    ImageView iv_play;

    private static final int PLAYBACK_MILLIS = 3000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_back_testing);
        ButterKnife.bind(this);
        Log.d(TagUtils.getTag(), "play back testing");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (is_looping) {
                    is_looping = false;
                    iv_play.setImageResource(R.drawable.ic_play);
                } else {
                    is_looping = true;
                    if (handler != null && runnable != null) {
                        if (index >= (polyLineList.size() - 1)) {
                            index = 0;
                            next = 0;
                        }
                        iv_play.setImageResource(R.drawable.ic_pause);
                        handler.postDelayed(runnable, PLAYBACK_MILLIS);
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
                next = seekBar.getProgress();
                index = seekBar.getProgress();
//                if (handler != null && runnable != null) {
//                    btn_start.setText("Pause");
//                    handler.postDelayed(runnable, 2000);
//                }
            }
        });
    }

    String startdatetime = "";
    String enddatetime = "";
    TextView tv_date_time;
    boolean is_custom_DT = false;

    public void showScheduleDialog() {

        final String start_date_time = "";
        String end_date_time = "";


        final Dialog dialog1 = new Dialog(PlayBackTesting.this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_date_time_selector);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final CheckBox check_today = dialog1.findViewById(R.id.check_today);
        final CheckBox check_yesterday = dialog1.findViewById(R.id.check_yesterday);
        final CheckBox check_hour = dialog1.findViewById(R.id.check_hour);
        final CheckBox check_user_defined = dialog1.findViewById(R.id.check_user_defined);
        final LinearLayout ll_custom = dialog1.findViewById(R.id.ll_custom);
        final TextView tv_start_datetime = dialog1.findViewById(R.id.tv_start_datetime);
        LinearLayout ll_start_datetime = dialog1.findViewById(R.id.ll_start_datetime);
        LinearLayout ll_end_datetime = dialog1.findViewById(R.id.ll_end_datetime);
        final TextView tv_end_datetime = dialog1.findViewById(R.id.tv_end_datetime);

        final List<CheckBox> checkBoxes = new ArrayList<>();
        checkBoxes.add(check_today);
        checkBoxes.add(check_yesterday);
        checkBoxes.add(check_hour);
        checkBoxes.add(check_user_defined);

        check_user_defined.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_custom.setVisibility(View.VISIBLE);

                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_user_defined)) {
                            checkBox.setChecked(false);
                        }
                    }

                } else {
                    ll_custom.setVisibility(View.GONE);
                }
            }
        });

        check_hour.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_custom.setVisibility(View.GONE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_hour)) {
                            checkBox.setChecked(false);
                        }
                        getHourAgo();
                    }
                }
            }
        });

        check_today.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_custom.setVisibility(View.GONE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_today)) {
                            checkBox.setChecked(false);
                        }
                    }
                    getTodayRange();
                }
            }
        });

        check_yesterday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    ll_custom.setVisibility(View.GONE);
                    for (CheckBox checkBox : checkBoxes) {
                        if (!checkBox.equals(check_yesterday)) {
                            checkBox.setChecked(false);
                        }
                    }
                    getYesterdayRange();
                }
            }
        });

        check_today.setChecked(true);

        ll_start_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_date_time = tv_start_datetime;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        PlayBackTesting.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        ll_end_datetime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_date_time = tv_end_datetime;
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        PlayBackTesting.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
        check_today.setChecked(true);
        Button btn_cancel = dialog1.findViewById(R.id.btn_cancel);
        Button btn_ok = dialog1.findViewById(R.id.btn_ok);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog1.dismiss();
            }
        });

    }

    public void getTodayRange() {
        is_custom_DT = false;
        startdatetime = UtilityFunction.getMMddYYYY() + " 00:00";
        enddatetime = UtilityFunction.getMMddYYYY() + " 23:59";
    }

    public void getYesterdayRange() {
        is_custom_DT = false;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        startdatetime = dateFormat.format(cal.getTime()) + " 00:00";
        enddatetime = UtilityFunction.getMMddYYYY() + " 23:59";
    }

    public void getHourAgo() {
        is_custom_DT = false;
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -1);
        startdatetime = dateFormat.format(cal.getTime());
        enddatetime = UtilityFunction.getMMddyyyyDT();
    }


    private List<LatLng> polyLineList;

    public void drawRoute(String response) {
        try {
            googleMap.clear();
            is_looping = true;
            handler = null;
            runnable = null;
            iv_play.setImageResource(R.drawable.ic_pause);
            JSONArray jsonArray = new JSONArray(response.toString());
            final List<VehicleHistoryPOJO> vehicleHistoryPOJOS = new ArrayList<>();
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
                googleMap.animateCamera(mCameraUpdate);

                polylineOptions = new PolylineOptions();
                polylineOptions.color(Color.GRAY);
                polylineOptions.width(10);
                polylineOptions.startCap(new SquareCap());
                polylineOptions.endCap(new SquareCap());
                polylineOptions.jointType(ROUND);
                polylineOptions.addAll(polyLineList);
                greyPolyLine = googleMap.addPolyline(polylineOptions);

                blackPolylineOptions = new PolylineOptions();
                blackPolylineOptions.width(5);
                blackPolylineOptions.color(Color.BLACK);
                blackPolylineOptions.startCap(new SquareCap());
                blackPolylineOptions.endCap(new SquareCap());
                blackPolylineOptions.jointType(ROUND);
                blackPolyline = googleMap.addPolyline(blackPolylineOptions);

                googleMap.addMarker(new MarkerOptions()
                        .position(polyLineList.get(polyLineList.size() - 1)));

                ValueAnimator polylineAnimator = ValueAnimator.ofInt(0, 100);
                polylineAnimator.setDuration(PLAYBACK_MILLIS);
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
                marker = googleMap.addMarker(new MarkerOptions().position(latLngs.get(0))
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
                            iv_play.setImageResource(R.drawable.ic_play);
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
                        valueAnimator.setDuration(PLAYBACK_MILLIS);
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

                                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                View view = inflater.inflate(R.layout.window_info, null);

                                ImageView iv_truck = view.findViewById(R.id.iv_truck);
                                iv_truck.setRotation(getBearing(startPosition, newPos));

                                TextView tv_speed = view.findViewById(R.id.tv_speed);
                                TextView tv_time = view.findViewById(R.id.tv_time);
                                TextView tv_mileage = view.findViewById(R.id.tv_mileage);

                                tv_time.setText(String.valueOf(vehicleHistoryPOJOS.get(index).getTime()));
                                tv_speed.setText(String.valueOf(vehicleHistoryPOJOS.get(index).getSpeed()));
                                tv_mileage.setText(String.valueOf(vehicleHistoryPOJOS.get(index).getTotalMileage()));

//                                                text.setText(String.valueOf(lat)+" "+String.valueOf(lng));
//                                                text.setTextColor(Color.parseColor("#FFFFFF"));
                                IconGenerator generator = new IconGenerator(PlayBackTesting.this);
                                generator.setBackground(getResources().getDrawable(R.drawable.window_transparent));
                                generator.setContentView(view);
                                Bitmap icon = generator.makeIcon();

//                                                MarkerOptions tp = new MarkerOptions().position(newPos).anchor(0.5f,0.5f).rotation(getBearing(startPosition,newPos)).icon(BitmapDescriptorFactory.fromBitmap(icon));

                                marker.setIcon(BitmapDescriptorFactory.fromBitmap(icon));

                                marker.setPosition(newPos);
                                marker.setAnchor(0.5f, 0.5f);
//                                                marker.setRotation(getBearing(startPosition, newPos));


                                googleMap.moveCamera(CameraUpdateFactory
                                        .newCameraPosition
                                                (new CameraPosition.Builder()
                                                        .target(newPos)
                                                        .zoom(20f)
                                                        .build()));
                            }
                        });
                        valueAnimator.start();
                        if (is_looping) {
                            handler.postDelayed(this, PLAYBACK_MILLIS);
                        }
                    }
                };

                handler.postDelayed(runnable, PLAYBACK_MILLIS);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void drawRouteOnMap(GoogleMap map, List<LatLng> positions) {
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        options.addAll(positions);
        Polyline polyline = map.addPolyline(options);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(positions.get(1).latitude, positions.get(1).longitude))
                .zoom(17)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


    boolean is_looping = true;
    Runnable runnable;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
//        initiatePicker();
        Log.d(TagUtils.getTag(), "map ready");
//        String data=getJsonString();
//        Log.d(TagUtils.getTag(), "play back:-" + data);
//
//        drawRoute(data);

//        checkLocation();
//        initialize();
//        drawRoute(UtilityFunction.getJSONString(this));
    }


//    public String getJsonString() {
//        String aBuffer = "";
//        try {
//            File myFile = new File(Environment.getExternalStorageDirectory()+File.separator+"json.txt");
//            FileInputStream fIn = new FileInputStream(myFile);
//            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));
//            String aDataRow = "";
//            while ((aDataRow = myReader.readLine()) != null) {
//                aBuffer += aDataRow;
//            }
//            myReader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return aBuffer;
//    }


    public void initiatePicker() {
        new ImagePicker.Builder(this)
                .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                .directory(ImagePicker.Directory.DEFAULT)
                .allowMultipleImages(false)
                .enableDebuggingMode(true)
                .build();
    }



    public void checkLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        } else {
            double[] latlong = UtilityFunction.getLocation(getApplicationContext());
            showLocation(latlong[0], latlong[1], "current location");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkLocationPermission() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.ACCESS_LOCATION);
            return;
        } else {
            double[] latlong = UtilityFunction.getLocation(getApplicationContext());
            showLocation(latlong[0], latlong[1], "current location");
        }
    }


    public void showLocation(double latitude, double longitude, String info) {
        try {
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(info));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
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

    String selected_date = "";

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month = "";
        String day = "";
        if ((monthOfYear + 1) < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }

        if (dayOfMonth < 10) {
            day = "0" + dayOfMonth;
        } else {
            day = String.valueOf(dayOfMonth);
        }

        String date = day + "-" + month + "-" + year;
        selected_date = date;
        openTimePicker();
    }

    public void openTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                PlayBackTesting.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        tpd.show(getFragmentManager(), "Select Time");
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        is_custom_DT = true;
        String hourString = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteString = minute < 10 ? "0" + minute : "" + minute;
        String secondString = second < 10 ? "0" + second : "" + second;
        String time = hourString + ":" + minuteString;
        tv_date_time.setText(selected_date + " " + time);
    }
}
