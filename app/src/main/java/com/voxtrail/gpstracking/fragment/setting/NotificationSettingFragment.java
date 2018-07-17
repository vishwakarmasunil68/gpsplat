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

public class NotificationSettingFragment extends FragmentController{

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_notification_settings,container,false);
        setUpView(getActivity(),this,view);
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



    }


}
