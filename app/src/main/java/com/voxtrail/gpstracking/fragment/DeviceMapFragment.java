
package com.voxtrail.gpstracking.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.activity.DeviceDataActivity;
import com.voxtrail.gpstracking.activity.HomeActivity;
import com.voxtrail.gpstracking.fragmentcontroller.FragmentController;
import com.voxtrail.gpstracking.pojo.VehiclePOJO;
import com.voxtrail.gpstracking.util.Constants;
import com.voxtrail.gpstracking.util.Pref;
import com.voxtrail.gpstracking.util.StringUtils;
import com.voxtrail.gpstracking.util.TagUtils;
import com.voxtrail.gpstracking.util.ToastClass;
import com.voxtrail.gpstracking.util.UtilityFunction;
import com.voxtrail.gpstracking.webservice.WebServicesUrls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class DeviceMapFragment extends FragmentController implements OnMapReadyCallback {
    GoogleMap googleMap;
    @BindView(R.id.ll_prev)
    LinearLayout ll_prev;
    @BindView(R.id.ll_next)
    LinearLayout ll_next;

    @BindView(R.id.iv_map_type)
    ImageView iv_map_type;
    @BindView(R.id.iv_traffic)
    ImageView iv_traffic;

    boolean is_satellite_view = false;
    boolean is_traffic_view = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_map, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    public static DeviceMapFragment newInstance() {
        Bundle args = new Bundle();
        DeviceMapFragment fragment = new DeviceMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ll_next.setVisibility(View.GONE);
        ll_prev.setVisibility(View.GONE);


        iv_map_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMapView();
                if (is_satellite_view) {
                    iv_map_type.setImageResource(R.drawable.ic_satellite_view);
                } else {
                    iv_map_type.setImageResource(R.drawable.ic_normal_map);
                }
            }
        });
        iv_traffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTrafficView();
                if (is_traffic_view) {
                    iv_traffic.setImageResource(R.drawable.ic_enable_traffic);
                } else {
                    iv_traffic.setImageResource(R.drawable.ic_disable_traffic);
                }
            }
        });
    }

    public void setMapView() {
        if (googleMap != null) {
            if (is_satellite_view) {
                is_satellite_view = false;
                googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
            } else {
                is_satellite_view = true;
                googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE);
            }
        }
    }


    public void setTrafficView() {
        if (googleMap != null) {
            if (is_traffic_view) {
                is_traffic_view = false;
                googleMap.setTrafficEnabled(is_traffic_view);
            } else {
                is_traffic_view = true;
                googleMap.setTrafficEnabled(is_traffic_view);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
//        checkLocation();
        if (getActivity() instanceof DeviceDataActivity) {
//            DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();
//            if (deviceDataActivity.vehiclePOJO != null) {
//                try {
//                    showLocation(deviceDataActivity.vehiclePOJO.getLatitude(), deviceDataActivity.vehiclePOJO.getLongitude(), deviceDataActivity.vehiclePOJO.getVehicleNumber());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
            getDeviceLastLocation();
        }

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        setcurrentlocationcontrol();

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (getActivity() instanceof DeviceDataActivity) {
                    DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();
                    try {
                        deviceDataActivity.getCompleteAddress(vehiclePOJO);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }


    public void setcurrentlocationcontrol() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }


    VehiclePOJO vehiclePOJO;

    public void getDeviceLastLocation() {
        if (getActivity() instanceof DeviceDataActivity) {
            final DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();

            RequestQueue queue = Volley.newRequestQueue(getActivity());
            activityManager.showProgressBar();
            StringRequest getRequest = new StringRequest(Request.Method.GET, WebServicesUrls.VEHICLE_CURRENT_STATUS + deviceDataActivity.vehiclePOJO.getVehicleID(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            activityManager.dismissProgressBar();
                            Log.d(TagUtils.getTag(), "response:-" + response.toString());
                            try {
                                vehiclePOJO = new Gson().fromJson(response, VehiclePOJO.class);
                                deviceDataActivity.tv_vehicle_name.setText(vehiclePOJO.getVehicleNumber());
                                showLocation(vehiclePOJO);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            activityManager.dismissProgressBar();
                            Log.d(TagUtils.getTag(), "error:-" + error.toString());
//                            error.printStackTrace();
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                    Log.d(TagUtils.getTag(), "obj:-" + obj.toString());

                                    JSONArray jsonArray = obj.optJSONArray("Errors");
                                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonArray.optString(0));

                                } catch (Exception e1) {
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                }
                            }
                        }
                    }
            ) {


                @Override
                public String getBodyContentType() {
                    return "application/json";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
                    return headers;
                }
            };
            queue.add(getRequest);
        }
    }


    public void checkLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        } else {
            double[] latlong = UtilityFunction.getLocation(getActivity().getApplicationContext());
//            showLocation(latlong[0], latlong[1], "current location");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkLocationPermission() {
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.ACCESS_LOCATION);
            return;
        } else {
            double[] latlong = UtilityFunction.getLocation(getActivity().getApplicationContext());
//            showLocation(latlong[0], latlong[1], "current location");
        }
    }


    public void showLocation(VehiclePOJO vehiclePOJO) {
        try {
            LatLng latLng = new LatLng(vehiclePOJO.getLatitude(), vehiclePOJO.getLongitude());
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_car_icon);
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(vehiclePOJO.getVehicleNumber()).icon(icon);
            googleMap.addMarker(markerOptions);
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
                                