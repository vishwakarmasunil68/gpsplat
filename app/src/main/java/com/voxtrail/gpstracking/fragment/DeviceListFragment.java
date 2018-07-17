package com.voxtrail.gpstracking.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.adapter.DeviceListAdapter;
import com.voxtrail.gpstracking.fragmentcontroller.FragmentController;
import com.voxtrail.gpstracking.pojo.VehiclePOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class DeviceListFragment extends FragmentController{

    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_device_list,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public static DeviceListFragment newInstance() {
        Bundle args = new Bundle();
        DeviceListFragment fragment = new DeviceListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    DeviceListAdapter deviceListAdapter;

    public void attachAdapter(List<VehiclePOJO> vehiclePOJOS) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rv_list.setHasFixedSize(true);
        rv_list.setLayoutManager(linearLayoutManager);
        deviceListAdapter = new DeviceListAdapter(getActivity(),null,vehiclePOJOS);
        rv_list.setAdapter(deviceListAdapter);
        rv_list.setNestedScrollingEnabled(false);
        rv_list.setItemAnimator(new DefaultItemAnimator());
    }
}
