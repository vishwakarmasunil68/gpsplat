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

import butterknife.BindView;

public class MyProfilefragment extends FragmentController{

    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_my_profile,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }


    public static MyProfilefragment newInstance() {
        Bundle args = new Bundle();
        MyProfilefragment fragment = new MyProfilefragment();
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
