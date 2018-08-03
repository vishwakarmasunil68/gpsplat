package com.voxgps.app.fragment;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.voxgps.app.R;
import com.voxgps.app.adapter.AlertAdapter;
import com.voxgps.app.adapter.DeviceListAdapter;
import com.voxgps.app.fragmentcontroller.FragmentController;
import com.voxgps.app.pojo.NotificationPOJO;
import com.voxgps.app.util.Pref;
import com.voxgps.app.util.StringUtils;
import com.voxgps.app.util.TagUtils;
import com.voxgps.app.util.UtilityFunction;
import com.voxgps.app.webservice.WebServicesUrls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class AlertFragment extends FragmentController{

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_device_list,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }


    public static AlertFragment newInstance() {
        Bundle args = new Bundle();
        AlertFragment fragment = new AlertFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        attachAdapter();

    }

    boolean is_initialize=false;

    public void initialize(){
        if(!is_initialize){
            is_initialize=true;
            callNotificationAPI();
        }
    }

    public void callNotificationAPI(){
        String url = WebServicesUrls.GET_NOTIFICATIONS_BY_USERID;
        Log.d(TagUtils.getTag(), "url:-" + url);
        activityManager.showProgressBar();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        activityManager.dismissProgressBar();
                        Log.d(TagUtils.getTag(), "response:-" + response.toString());
                        try{
                            JSONArray jsonArray=new JSONArray(response);

                            List<NotificationPOJO> notificationPOJOS=new ArrayList<>();

                            for(int i=0;i<jsonArray.length();i++){
                                NotificationPOJO notificationPOJO=new Gson().fromJson(jsonArray.optJSONObject(i).toString(), NotificationPOJO.class);
                                notificationPOJOS.add(notificationPOJO);
                            }

                            if(notificationPOJOS.size()>0){
                                deviceStrings.clear();
                                deviceStrings.addAll(notificationPOJOS);
                                deviceListAdapter.notifyDataSetChanged();
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        activityManager.dismissProgressBar();
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


    AlertAdapter deviceListAdapter;
    List<NotificationPOJO> deviceStrings= new ArrayList<>();

    public void attachAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(linearLayoutManager);
        deviceListAdapter = new AlertAdapter(getActivity(),null,deviceStrings);
        rv_list.setAdapter(deviceListAdapter);
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setItemAnimator(new DefaultItemAnimator());
    }
}
