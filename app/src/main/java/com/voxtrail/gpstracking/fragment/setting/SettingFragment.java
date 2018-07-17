package com.voxtrail.gpstracking.fragment.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.fragmentcontroller.FragmentController;

import butterknife.BindView;

public class SettingFragment extends FragmentController{

    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.ll_notification)
    LinearLayout ll_notification;
    @BindView(R.id.ll_language)
    LinearLayout ll_language;
    @BindView(R.id.ll_time_zone)
    LinearLayout ll_time_zone;
    @BindView(R.id.ll_monitor)
    LinearLayout ll_monitor;
    @BindView(R.id.ll_tracking)
    LinearLayout ll_tracking;
    @BindView(R.id.ll_distance)
    LinearLayout ll_distance;
    @BindView(R.id.ll_pressure)
    LinearLayout ll_pressure;
    @BindView(R.id.ll_temperature)
    LinearLayout ll_temperature;
    @BindView(R.id.ll_volume)
    LinearLayout ll_volume;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_settings,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }


    public static SettingFragment newInstance() {
        Bundle args = new Bundle();
        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ll_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new NotificationSettingFragment());
            }
        });
        ll_time_zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new TimeZoneFragment());
            }
        });

        ll_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new MonitorRefreshRateFragment());
            }
        });
        ll_distance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new DistanceMetricesFragment());
            }
        });
        ll_pressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new PressureMetricesFragment());
            }
        });

        ll_temperature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new TemperatureMetricesFragment());
            }
        });
        ll_volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new VolumeMetricesFragment());
            }
        });

        ll_tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new TrackingRefreshRateFragment());
            }
        });

        ll_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new LanguageSettingFragment());
            }
        });

    }


}
