package com.voxgps.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.voxgps.app.R;
import com.voxgps.app.activity.DeviceDataActivity;
import com.voxgps.app.activity.PolyGeoFenceActivity;
import com.voxgps.app.adapter.FenceDataAdapter;
import com.voxgps.app.fragmentcontroller.FragmentController;
import com.voxgps.app.pojo.fence.FencePOJO;
import com.voxgps.app.util.Pref;
import com.voxgps.app.util.StringUtils;
import com.voxgps.app.util.TagUtils;
import com.voxgps.app.util.UtilityFunction;
import com.voxgps.app.webservice.WebServicesUrls;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class VehicleGeoFenceFragment extends FragmentController {

    @BindView(R.id.ic_add)
    ImageView ic_add;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.rv_fences)
    RecyclerView rv_fences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_geo_fence, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    public static VehicleGeoFenceFragment newInstance() {
        Bundle args = new Bundle();
        VehicleGeoFenceFragment fragment = new VehicleGeoFenceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        attachAdapter();
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ic_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof DeviceDataActivity) {
                    DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();
                    Intent intent = new Intent(getActivity(), PolyGeoFenceActivity.class);
                    intent.putExtra("vehiclePOJO", deviceDataActivity.vehiclePOJO);
                    startActivity(intent);
                }
            }
        });

        getFenceData();
    }

    public void getFenceData() {
        if (getActivity() instanceof DeviceDataActivity) {
            DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();
            activityManager.showProgressBar();
            RequestQueue queue = Volley.newRequestQueue(getActivity());

            StringRequest getRequest = new StringRequest(Request.Method.GET, WebServicesUrls.VEHICLE_FENCES + deviceDataActivity.vehiclePOJO.getVehicleID(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            activityManager.dismissProgressBar();
//                        hideWheelLoading();
                            Log.d(TagUtils.getTag(), "vehicle list:-" + response.toString());
                            try {
                                JSONArray jsonArray = new JSONArray(response.toString());
                                deviceStrings.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    deviceStrings.add(new Gson().fromJson(jsonArray.optJSONObject(i).toString(), FencePOJO.class));
                                }
//                                if (deviceStrings.size()>0) {
                                deviceListAdapter.notifyDataSetChanged();
//                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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


    FenceDataAdapter deviceListAdapter;
    List<FencePOJO> deviceStrings = new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_fences.setHasFixedSize(true);
        rv_fences.setLayoutManager(linearLayoutManager);
        deviceListAdapter = new FenceDataAdapter(getActivity(), null, deviceStrings);
        rv_fences.setAdapter(deviceListAdapter);
        rv_fences.setNestedScrollingEnabled(false);
        rv_fences.setItemAnimator(new DefaultItemAnimator());
    }
}