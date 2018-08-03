package com.voxgps.app.fragment.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.suke.widget.SwitchButton;
import com.voxgps.app.R;
import com.voxgps.app.activity.DeviceDataActivity;
import com.voxgps.app.fragment.VehicleGeoFenceFragment;
import com.voxgps.app.fragmentcontroller.FragmentController;
import com.voxgps.app.util.Pref;
import com.voxgps.app.util.StringUtils;
import com.voxgps.app.util.TagUtils;
import com.voxgps.app.util.UtilityFunction;
import com.voxgps.app.webservice.WebServicesUrls;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class DeviceSettingFragment extends FragmentController {

    @BindView(R.id.ll_geofence)
    LinearLayout ll_geofence;
    @BindView(R.id.switch_engine)
    SwitchButton switch_engine;
    @BindView(R.id.switch_speeding)
    SwitchButton switch_speeding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_device_setting, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }


    public static DeviceSettingFragment newInstance() {
        Bundle args = new Bundle();
        DeviceSettingFragment fragment = new DeviceSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ll_geofence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, new VehicleGeoFenceFragment());
            }
        });

        getRelayStatus();
        getSpeedStatus();

    }

    public void setRelayStatus(boolean status) {
        if (getActivity() instanceof DeviceDataActivity) {


            DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();

            String url = WebServicesUrls.setRelayStatus(String.valueOf(deviceDataActivity.vehiclePOJO.getVehicleID()), status);

            Log.d(TagUtils.getTag(), "url:-" + url);


            activityManager.showProgressBar();
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            activityManager.dismissProgressBar();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            activityManager.dismissProgressBar();
//                        hideWheelLoading();
                            error.printStackTrace();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
                    UtilityFunction.printAllValues(headers);
                    return headers;
                }
            };
            queue.add(getRequest);
        }
    }

    public void setSpeedStatus(boolean status) {
        if (getActivity() instanceof DeviceDataActivity) {


            DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();

            String url = WebServicesUrls.setSpeedStatus(String.valueOf(deviceDataActivity.vehiclePOJO.getVehicleID()), status);

            Log.d(TagUtils.getTag(), "url:-" + url);


            activityManager.showProgressBar();
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            activityManager.dismissProgressBar();

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            activityManager.dismissProgressBar();
//                        hideWheelLoading();
                            error.printStackTrace();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
                    UtilityFunction.printAllValues(headers);
                    return headers;
                }
            };
            queue.add(getRequest);
        }
    }

    public void getRelayStatus() {
        if (getActivity() instanceof DeviceDataActivity) {

            DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();

//            activityManager.showProgressBar();
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            StringRequest getRequest = new StringRequest(Request.Method.GET, WebServicesUrls.GET_RELAY_STATUS + deviceDataActivity.vehiclePOJO.getVehicleID(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            activityManager.dismissProgressBar();
                            Log.d(TagUtils.getTag(), "relay response:-" + response);
                            try {
                                if (response.toLowerCase().contains("on")) {
                                    switch_engine.setChecked(true);
                                } else {
                                    switch_engine.setChecked(false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            switch_engine.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                                    setRelayStatus(isChecked);
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            activityManager.dismissProgressBar();
//                        hideWheelLoading();
                            error.printStackTrace();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
                    UtilityFunction.printAllValues(headers);
                    return headers;
                }
            };
            queue.add(getRequest);
        }
    }


    public void getSpeedStatus() {
        if (getActivity() instanceof DeviceDataActivity) {

            DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();

//            activityManager.showProgressBar();
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            StringRequest getRequest = new StringRequest(Request.Method.GET, WebServicesUrls.GET_SPEED_STATUS + deviceDataActivity.vehiclePOJO.getVehicleID(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//                            activityManager.dismissProgressBar();
                            Log.d(TagUtils.getTag(), "relay response:-" + response);
                            try {
                                if (response.toLowerCase().contains("on")) {
                                    switch_speeding.setChecked(true);
                                } else {
                                    switch_speeding.setChecked(false);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            switch_speeding.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                                    setSpeedStatus(isChecked);
                                }
                            });
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            activityManager.dismissProgressBar();
//                        hideWheelLoading();
                            error.printStackTrace();
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
                    UtilityFunction.printAllValues(headers);
                    return headers;
                }
            };
            queue.add(getRequest);
        }
    }


}
