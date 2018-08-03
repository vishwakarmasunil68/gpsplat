package com.voxgps.app.fragment.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.voxgps.app.R;
import com.voxgps.app.fragmentcontroller.FragmentController;
import com.voxgps.app.util.Pref;
import com.voxgps.app.util.StringUtils;

import net.igenius.customcheckbox.CustomCheckBox;

import butterknife.BindView;

public class AlertOptionSettingFragment extends FragmentController {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.ll_sos)
    LinearLayout ll_sos;
    @BindView(R.id.check_sos)
    CustomCheckBox check_sos;
    @BindView(R.id.ll_low_battery)
    LinearLayout ll_low_battery;
    @BindView(R.id.check_low_battery)
    CustomCheckBox check_low_battery;
    @BindView(R.id.ll_external_power)
    LinearLayout ll_external_power;
    @BindView(R.id.check_external_power_disconnect)
    CustomCheckBox check_external_power_disconnect;
    @BindView(R.id.ll_vibration)
    LinearLayout ll_vibration;
    @BindView(R.id.check_vibration)
    CustomCheckBox check_vibration;
    @BindView(R.id.ll_geofence_in)
    LinearLayout ll_geofence_in;
    @BindView(R.id.check_geofence_in)
    CustomCheckBox check_geofence_in;
    @BindView(R.id.ll_geofence_out)
    LinearLayout ll_geofence_out;
    @BindView(R.id.check_geofence_out)
    CustomCheckBox check_geofence_out;
    @BindView(R.id.ll_speeding)
    LinearLayout ll_speeding;
    @BindView(R.id.check_speeding)
    CustomCheckBox check_speeding;
    @BindView(R.id.ll_towing)
    LinearLayout ll_towing;
    @BindView(R.id.check_towing)
    CustomCheckBox check_towing;
    @BindView(R.id.ll_engine_on)
    LinearLayout ll_engine_on;
    @BindView(R.id.check_engine_on)
    CustomCheckBox check_engine_on;
    @BindView(R.id.ll_engine_off)
    LinearLayout ll_engine_off;
    @BindView(R.id.check_engine_off)
    CustomCheckBox check_engine_off;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_alert_options, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }


    public static AlertOptionSettingFragment newInstance() {
        Bundle args = new Bundle();
        AlertOptionSettingFragment fragment = new AlertOptionSettingFragment();
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


        ll_sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_sos.callOnClick();
            }
        });
        ll_low_battery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_low_battery.callOnClick();
            }
        });
        ll_external_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_external_power_disconnect.callOnClick();
            }
        });
        ll_vibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_vibration.callOnClick();
            }
        });
        ll_geofence_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_geofence_in.callOnClick();
            }
        });
        ll_geofence_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_geofence_out.callOnClick();
            }
        });
        ll_speeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_speeding.callOnClick();
            }
        });
        ll_towing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_towing.callOnClick();
            }
        });
        ll_engine_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_engine_on.callOnClick();
            }
        });
        ll_engine_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check_engine_off.callOnClick();
            }
        });
        check_sos.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_SOS, isChecked);
            }
        });
        check_low_battery.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_LOW_BATTERY, isChecked);
            }
        });
        check_external_power_disconnect.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_EXTERNAL_POWER_DISCONNECT, isChecked);
            }
        });
        check_vibration.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_VIBRATION, isChecked);
            }
        });
        check_geofence_in.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_GEOFENCE_IN, isChecked);
            }
        });
        check_geofence_out.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_GEOFENCE_OUT, isChecked);
            }
        });
        check_speeding.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_SPEEDING, isChecked);
            }
        });
        check_towing.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_TOWING, isChecked);
            }
        });
        check_engine_on.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_ENGINE_ON, isChecked);
            }
        });
        check_engine_off.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_ENGINE_OFF, isChecked);
            }
        });


        check_sos.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_SOS, false));
        check_low_battery.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_LOW_BATTERY, false));
        check_external_power_disconnect.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_EXTERNAL_POWER_DISCONNECT, false));
        check_vibration.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_VIBRATION, false));
        check_geofence_in.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_GEOFENCE_IN, false));
        check_geofence_out.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_GEOFENCE_OUT, false));
        check_speeding.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_SPEEDING, false));
        check_towing.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_TOWING, false));
        check_engine_on.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_ENGINE_ON, false));
        check_engine_off.setChecked(Pref.GetBooleanPref(getActivity().getApplicationContext(), StringUtils.ALERT_ENGINE_OFF, false));
    }
}
