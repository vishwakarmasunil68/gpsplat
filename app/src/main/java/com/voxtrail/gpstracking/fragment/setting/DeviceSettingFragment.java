package com.voxtrail.gpstracking.fragment.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.activity.GeoFenceActivity;
import com.voxtrail.gpstracking.fragmentcontroller.FragmentController;

import butterknife.BindView;

public class DeviceSettingFragment extends FragmentController{

    @BindView(R.id.ll_geofence)
    LinearLayout ll_geofence;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_device_setting,container,false);
        setUpView(getActivity(),this,view);
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
                Intent intent=new Intent(getActivity(), GeoFenceActivity.class);
                startActivity(intent);
            }
        });
    }
}
