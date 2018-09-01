package com.voxgps.app.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxgps.app.R;
import com.voxgps.app.activity.LoginActivity;
import com.voxgps.app.fragment.setting.AddUserFragment;
import com.voxgps.app.fragment.setting.MyProfilefragment;
import com.voxgps.app.fragment.setting.SettingFragment;
import com.voxgps.app.fragmentcontroller.FragmentController;
import com.voxgps.app.util.Constants;
import com.voxgps.app.util.Pref;
import com.voxgps.app.util.StringUtils;
import com.voxgps.app.util.ToastClass;
import com.voxgps.app.webservice.WebServiceBase;
import com.voxgps.app.webservice.WebServicesCallBack;
import com.voxgps.app.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

public class MeFragment extends FragmentController {

    @BindView(R.id.btn_logout)
    Button btn_logout;
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;
    @BindView(R.id.cv_profile)
    CardView cv_profile;
    @BindView(R.id.ll_user)
    LinearLayout ll_user;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.ll_add_device)
    LinearLayout ll_add_device;
    @BindView(R.id.ll_geofence)
    LinearLayout ll_geofence;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_me, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    public static MeFragment newInstance() {
        Bundle args = new Bundle();
        MeFragment fragment = new MeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.IS_LOGIN, false);
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.TOKEN_UPDATED, false);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finishAffinity();
            }
        });

//        tv_user_name.setText(Pref.GetStringPref(getActivity().getApplicationContext(),StringUtils.USERNAME,""));
        tv_user_name.setText(Constants.userDetail.getUsername());

        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, new SettingFragment());
            }
        });

        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, MyProfilefragment.newInstance());
            }
        });

        if (Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.ROLE, "").equalsIgnoreCase("admin")) {
            ll_user.setVisibility(View.VISIBLE);
        } else {
            ll_user.setVisibility(View.GONE);
        }

        ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, AddUserFragment.newInstance());
            }
        });

        ll_geofence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new VehicleGeoFenceFragment());
            }
        });

        ll_add_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog1 = new Dialog(getActivity(), android.R.style.Theme_DeviceDefault_Light_Dialog);
                dialog1.setCancelable(true);
                dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog1.setContentView(R.layout.dialog_add_device);
                dialog1.show();
                dialog1.setCancelable(true);
                Window window = dialog1.getWindow();
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                Button btn_save = dialog1.findViewById(R.id.btn_save);
                final EditText et_imei = dialog1.findViewById(R.id.et_imei);
                final EditText et_name = dialog1.findViewById(R.id.et_name);
                btn_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
                        nameValuePairs.add(new BasicNameValuePair("imei", et_imei.getText().toString()));
                        nameValuePairs.add(new BasicNameValuePair("user_id", Constants.userDetail.getId()));
                        nameValuePairs.add(new BasicNameValuePair("name", et_name.getText().toString()));

                        new WebServiceBase(nameValuePairs, null, getActivity(), new WebServicesCallBack() {
                            @Override
                            public void onGetMsg(String apicall, String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.optString("status").equalsIgnoreCase("1")) {
                                        dialog1.dismiss();
                                    }
                                    ToastClass.showShortToast(getActivity().getApplicationContext(), jsonObject.optString("message"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }, "SAVE_OBJECT", true).execute(WebServicesUrls.SAVE_OBJECT);
                    }
                });
            }
        });
    }
}
