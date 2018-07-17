package com.voxtrail.gpstracking.fragment;

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

import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.activity.LoginActivity;
import com.voxtrail.gpstracking.fragment.setting.MyProfilefragment;
import com.voxtrail.gpstracking.fragment.setting.SettingFragment;
import com.voxtrail.gpstracking.fragmentcontroller.FragmentController;
import com.voxtrail.gpstracking.util.Pref;
import com.voxtrail.gpstracking.util.StringUtils;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MeFragment extends FragmentController{

    @BindView(R.id.btn_logout)
    Button btn_logout;
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;
    @BindView(R.id.cv_profile)
    CardView cv_profile;

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
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finishAffinity();
            }
        });

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

    }
}
