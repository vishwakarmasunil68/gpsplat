package com.voxgps.app.fragment.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.voxgps.app.R;
import com.voxgps.app.adapter.LanguageSettingAdapter;
import com.voxgps.app.fragmentcontroller.FragmentController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TimeZoneFragment extends FragmentController {

    @BindView(R.id.rv_language)
    RecyclerView rv_language;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_time_zone, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }


    public static TimeZoneFragment newInstance() {
        Bundle args = new Bundle();
        TimeZoneFragment fragment = new TimeZoneFragment();
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
        attachAdapter();

    }

    LanguageSettingAdapter languageSettingAdapter;
    List<String> deviceStrings = new ArrayList<>();

    public void attachAdapter() {

        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");
        deviceStrings.add("UTC-30");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_language.setHasFixedSize(true);
        rv_language.setLayoutManager(linearLayoutManager);
        languageSettingAdapter = new LanguageSettingAdapter(getActivity(), null, deviceStrings);
        rv_language.setAdapter(languageSettingAdapter);
        rv_language.setNestedScrollingEnabled(false);
        rv_language.setItemAnimator(new DefaultItemAnimator());
    }
}
