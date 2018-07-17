
package com.voxtrail.gpstracking.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.activity.DeviceDataActivity;
import com.voxtrail.gpstracking.fragmentcontroller.FragmentController;
import com.voxtrail.gpstracking.util.Constants;
import com.voxtrail.gpstracking.util.UtilityFunction;

public class DeviceMapFragment extends FragmentController implements OnMapReadyCallback {
    GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_map, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    public static DeviceMapFragment newInstance() {
        Bundle args = new Bundle();
        DeviceMapFragment fragment = new DeviceMapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
//        checkLocation();
        if (getActivity() instanceof DeviceDataActivity) {
            DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();
            if (deviceDataActivity.vehiclePOJO != null) {
                try {
                    showLocation(deviceDataActivity.vehiclePOJO.getLatitude(), deviceDataActivity.vehiclePOJO.getLongitude(), deviceDataActivity.vehiclePOJO.getVehicleNumber());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void checkLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        } else {
            double[] latlong = UtilityFunction.getLocation(getActivity().getApplicationContext());
            showLocation(latlong[0], latlong[1], "current location");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkLocationPermission() {
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    Constants.ACCESS_LOCATION);
            return;
        } else {
            double[] latlong = UtilityFunction.getLocation(getActivity().getApplicationContext());
            showLocation(latlong[0], latlong[1], "current location");
        }
    }


    public void showLocation(double latitude, double longitude, String info) {
        try {
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(info));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
                                