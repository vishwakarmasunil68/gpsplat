package com.voxgps.app.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.voxgps.app.R;
import com.voxgps.app.util.Constants;
import com.voxgps.app.util.TagUtils;
import com.voxgps.app.util.ToastClass;
import com.voxgps.app.util.UtilityFunction;
import com.voxgps.app.webservice.WebServiceBase;
import com.voxgps.app.webservice.WebServicesCallBack;
import com.voxgps.app.webservice.WebServicesUrls;

import net.igenius.customcheckbox.CustomCheckBox;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import top.defaults.colorpicker.ColorPickerPopup;

public class PolyGeoFenceActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    List<LatLng> latLngList = new ArrayList<>();

    @BindView(R.id.btn_reset)
    Button btn_reset;
    @BindView(R.id.btn_set)
    Button btn_set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poly_geo_fence);
        ButterKnife.bind(this);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (latLngList.size() >= 3) {
//                    String strlatlngs = "";
//                    for (int i=0;i<latLngList.size();i++) {
//                        if(latLngList.size()==(i+1)){
//                            strlatlngs+=latLngList.get(i).latitude+","+latLngList.get(i).longitude;
//                        }else{
//                            strlatlngs+=latLngList.get(i).latitude+","+latLngList.get(i).longitude+",";
//                        }
//                    }
//                    Log.d(TagUtils.getTag(),"latlongs:-"+strlatlngs);
//
                    addAddressDialog();
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please Select atleast 3 latlngs");
                }
            }
        });

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                latLngList.clear();
                googleMap.clear();
            }
        });

    }

    String hex = "FF0000";

    public void addAddressDialog() {
        final Dialog dialog1 = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog);
        dialog1.setCancelable(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_add_address);
        dialog1.show();
        dialog1.setCancelable(true);
        Window window = dialog1.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        final EditText et_name = dialog1.findViewById(R.id.et_name);
        final CustomCheckBox check_visible = dialog1.findViewById(R.id.check_visible);
        final CustomCheckBox check_name_visible = dialog1.findViewById(R.id.check_name_visible);
        final View view_color = dialog1.findViewById(R.id.view_color);
        Button btn_add = dialog1.findViewById(R.id.btn_add);

        view_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                new ColorPickerPopup.Builder(PolyGeoFenceActivity.this)
                        .initialColor(Color.RED) // Set initial color
                        .enableAlpha(true) // Enable alpha slider or not
                        .okTitle("Choose")
                        .cancelTitle("Cancel")
                        .showIndicator(true)
                        .showValue(true)
                        .build()
                        .show(view, new ColorPickerPopup.ColorPickerObserver() {
                            @Override
                            public void onColorPicked(int color) {
                                Log.d(TagUtils.getTag(), "color:-" + color);
                                hex = String.valueOf(Integer.toHexString(color));
                                view.setBackgroundColor(color);
                            }

                            @Override
                            public void onColor(int color, boolean fromUser) {
                                Log.d(TagUtils.getTag(), "color new:-" + color);
                            }
                        });
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_name.getText().toString().length() > 0) {
                    createFence(et_name.getText().toString(),check_visible.isChecked(),check_name_visible.isChecked());
                    dialog1.dismiss();
                } else {
                    ToastClass.showShortToast(getApplicationContext(), "Please Enter Address");
                }
            }
        });

    }

    public void createFence(String name,boolean zone_visible,boolean name_visible) {

        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
        nameValuePairs.add(new BasicNameValuePair("group_id", "0"));
        nameValuePairs.add(new BasicNameValuePair("zone_name", name));
        nameValuePairs.add(new BasicNameValuePair("zone_color", "#"+hex));
        nameValuePairs.add(new BasicNameValuePair("zone_visible", String.valueOf(zone_visible)));
        nameValuePairs.add(new BasicNameValuePair("zone_name_visible", String.valueOf(name_visible)));
        nameValuePairs.add(new BasicNameValuePair("zone_area", "0"));
        String latlngs="";
        for(int i=0;i<latLngList.size();i++){
            if((i+1)<latLngList.size()){
                latlngs+=latLngList.get(i).latitude+","+latLngList.get(i).longitude;
            }else{
                latlngs+=latLngList.get(i).latitude+","+latLngList.get(i).longitude+",";
            }
        }

        Log.d(TagUtils.getTag(),"lat lngs size:-"+latLngList.size());
        Log.d(TagUtils.getTag(),"lat lngs:-"+latLngList.toString());
        nameValuePairs.add(new BasicNameValuePair("zone_vertices", latlngs));

        new WebServiceBase(nameValuePairs, null, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.optString("status").equalsIgnoreCase("1")) {

                    }
                    ToastClass.showShortToast(getApplicationContext(), jsonObject.optString("message"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CREATE_POLYFENCE", false).execute(WebServicesUrls.SAVE_ZONE);

//        try {
//            final JSONObject jsonObject=new JSONObject();
//
//            final JSONArray jsonArray = new JSONArray();
//
//            for (LatLng latLng : latLngList) {
//                JSONObject latlngJSON = new JSONObject();
//                latlngJSON.put("Latitude", latLng.latitude);
//                latlngJSON.put("Longitude", latLng.longitude);
//
//                jsonArray.put(latlngJSON);
//            }
////            jsonObject.put("VehicleID", vehiclePOJO.getVehicleID());
//            jsonObject.put("listFencePloygonData", jsonArray);
//            jsonObject.put("Address", address);
//
//
//            Log.d(TagUtils.getTag(), "json Object:-" + jsonObject.toString());
//
//            RequestQueue queue = Volley.newRequestQueue(this);
//
//            StringRequest getRequest = new StringRequest(Request.Method.POST, WebServicesUrls.CreateFencePolygon,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            Log.d(TagUtils.getTag(), "response:-" + response.toString());
//                            if(response.equals("true")){
//                                ToastClass.showShortToast(getApplicationContext(),"Fence Created");
//                                onBackPressed();
//                            }
//                        }
//                    },
//                    new Response.ErrorListener() {
//                        @Override
//                        public void onErrorResponse(VolleyError error) {
//                            Log.d(TagUtils.getTag(), "error:-" + error.toString());
////                            error.printStackTrace();
//                            NetworkResponse response = error.networkResponse;
//                            if (error instanceof ServerError && response != null) {
//                                try {
//                                    String res = new String(response.data,
//                                            HttpHeaderParser.parseCharset(response.headers));
//                                    // Now you can use any deserializer to make sense of data
//                                    JSONObject obj = new JSONObject(res);
//                                    Log.d(TagUtils.getTag(), "obj:-" + obj.toString());
//
//                                    JSONArray jsonArray = obj.optJSONArray("Errors");
//                                    ToastClass.showShortToast(getApplicationContext(), jsonArray.optString(0));
//
//                                } catch (Exception e1) {
//                                    // Couldn't properly decode data to string
//                                    e1.printStackTrace();
//                                }
//                            }
//                        }
//                    }
//            ) {
//                @Override
//                public byte[] getBody() throws AuthFailureError {
//                    return jsonObject.toString().getBytes();
//                }
//
//                @Override
//                public String getBodyContentType() {
//                    return "application/json";
//                }
//
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> headers = new HashMap<>();
//                    headers.put("Authorization", Pref.GetStringPref(getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
//                    headers.put("Content-Type", "application/json");
////                    UtilityFunction.printAllValues(headers);
//                    return headers;
//                }
//            };
//            queue.add(getRequest);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d(TagUtils.getTag(), "latitude:-" + latLng.latitude + " ,long:-" + latLng.longitude);
                latLngList.add(latLng);
                setPolyMarker();
            }
        });


//        if(vehiclePOJO!=null){
//            showLocation(vehiclePOJO.getLatitude(),vehiclePOJO.getLongitude());
//        }

    }

    public void setPolyMarker() {
        if (latLngList.size() > 0) {
//            googleMap.clear();
            PolygonOptions rectOptions = new PolygonOptions();
            for (LatLng latLng : latLngList) {
                showPolyLocation(latLng.latitude, latLng.longitude);
                rectOptions.add(latLng);
            }
            rectOptions.strokeColor(Color.RED);
            rectOptions.fillColor(Color.BLUE);
            googleMap.addPolygon(rectOptions);
        }
    }

    public void checkLocation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        } else {
            double[] latlong = UtilityFunction.getLocation(getApplicationContext());
            showLocation(latlong[0], latlong[1]);
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
            showLocation(latlong[0], latlong[1]);
        }
    }

    public void showLocation(double lat, double lng) {
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("current loc");
        Marker marker = googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
    }


    public void showPolyLocation(double lat, double lng) {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_polygon_marker);

        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("").icon(icon);
        Marker marker = googleMap.addMarker(markerOptions);
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }


}
