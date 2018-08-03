package com.voxgps.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voxgps.app.R;
import com.voxgps.app.adapter.ViewPagerWithTitleAdapter;
import com.voxgps.app.fragmentcontroller.FragmentController;
import com.voxgps.app.view.CustomViewPager;

import butterknife.BindView;

public class DeviceFragment extends FragmentController{

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    CustomViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_device,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }


    public static DeviceFragment newInstance() {
        Bundle args = new Bundle();
        DeviceFragment fragment = new DeviceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager.setPagingEnabled(true);

        setUpPager();

    }


    public void setUpPager(){
        ViewPagerWithTitleAdapter adapter = new ViewPagerWithTitleAdapter(getChildFragmentManager());
        adapter.addFrag(new DeviceListFragment(), "All");
        adapter.addFrag(new DeviceListFragment(), "Online");
        adapter.addFrag(new DeviceListFragment(), "Offline");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());

        tabs.setupWithViewPager(viewPager);
    }
}
