package com.voxgps.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxgps.app.R;
import com.voxgps.app.activity.LoginActivity;
import com.voxgps.app.fragment.setting.AddUserFragment;
import com.voxgps.app.fragment.setting.MyProfilefragment;
import com.voxgps.app.fragment.setting.SettingFragment;
import com.voxgps.app.fragmentcontroller.FragmentController;
import com.voxgps.app.util.Pref;
import com.voxgps.app.util.StringUtils;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MeFragment extends FragmentController{

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_me,container,false);
        setUpView(getActivity(),this,view);
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
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.IS_LOGIN,false);
                Pref.SetBooleanPref(getActivity().getApplicationContext(), StringUtils.TOKEN_UPDATED,false);
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finishAffinity();
            }
        });

        tv_user_name.setText(Pref.GetStringPref(getActivity().getApplicationContext(),StringUtils.USERNAME,""));

        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home,new SettingFragment());
            }
        });

        cv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, MyProfilefragment.newInstance());
            }
        });

        if(Pref.GetStringPref(getActivity().getApplicationContext(),StringUtils.ROLE,"").equalsIgnoreCase("admin")){
            ll_user.setVisibility(View.VISIBLE);
        }else{
            ll_user.setVisibility(View.GONE);
        }

        ll_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityManager.startFragment(R.id.frame_home, AddUserFragment.newInstance());
            }
        });

    }
}
