package com.voxgps.app.fragment.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.suke.widget.SwitchButton;
import com.voxgps.app.R;
import com.voxgps.app.fragmentcontroller.FragmentController;
import com.voxgps.app.util.Pref;
import com.voxgps.app.util.StringUtils;

import butterknife.BindView;

public class NotificationSettingFragment extends FragmentController {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.ll_alert)
    LinearLayout ll_alert;
    @BindView(R.id.switch_receive_notification)
    SwitchButton switch_receive_notification;
    @BindView(R.id.switch_vibration)
    SwitchButton switch_vibration;
    @BindView(R.id.switch_all_day)
    SwitchButton switch_all_day;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_notification_settings, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }


    public static NotificationSettingFragment newInstance() {
        Bundle args = new Bundle();
        NotificationSettingFragment fragment = new NotificationSettingFragment();
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
        ll_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, AlertOptionSettingFragment.newInstance());
            }
        });

        switch_receive_notification.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION, isChecked);
            }
        });

        switch_vibration.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.NOTIFICATION_VIBRATION, isChecked);
            }
        });

        switch_all_day.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.NOTIFICATION_ALL_DAY, isChecked);
            }
        });


        switch_receive_notification.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.NOTIFICATION_RECEIVE_NOTIFICATION, false));
        switch_vibration.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.NOTIFICATION_VIBRATION, false));
        switch_all_day.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.NOTIFICATION_ALL_DAY, false));


    }


}
