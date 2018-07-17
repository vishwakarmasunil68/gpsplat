
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.activity.HomeActivity;
import com.voxtrail.gpstracking.fragmentcontroller.FragmentController;
import com.voxtrail.gpstracking.pojo.VehiclePOJO;
import com.voxtrail.gpstracking.util.Constants;
import com.voxtrail.gpstracking.util.UtilityFunction;

import java.util.List;

public class HomeMapFragment extends FragmentController implements OnMapReadyCallback {
    GoogleMap googleMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_home_map, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }

    public static HomeMapFragment newInstance() {
        Bundle args = new Bundle();
        HomeMapFragment fragment = new HomeMapFragment();
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
        if(getActivity() instanceof HomeActivity){
            HomeActivity homeActivity= (HomeActivity) getActivity();
            homeActivity.getVehicleList();
        }
    }

    List<VehiclePOJO> vehiclePOJOS;

    public void showAllDevices(List<VehiclePOJO> vehiclePOJOS) {
        this.vehiclePOJOS = vehiclePOJOS;

        if(googleMap!=null) {
            if (vehiclePOJOS.size() > 0) {
                googleMap.clear();
                for (int i = 0; i < vehiclePOJOS.size(); i++) {
                    VehiclePOJO vehiclePOJO = vehiclePOJOS.get(i);
                    if (i == (vehiclePOJOS.size() - 1)) {
                        showLocation(vehiclePOJO.getLatitude(), vehiclePOJO.getLatitude(), vehiclePOJO.getVehicleNumber(), true);
                    } else {
                        showLocation(vehiclePOJO.getLatitude(), vehiclePOJO.getLatitude(), vehiclePOJO.getVehicleNumber(), false);
                    }
                }
            }
        }
    }


    public void checkLocation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        } else {
            double[] latlong = UtilityFunction.getLocation(getActivity().getApplicationContext());
            showLocation(latlong[0], latlong[1], "current location", true);
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
            showLocation(latlong[0], latlong[1], "current location", true);
        }
    }


    public void showLocation(double latitude, double longitude, String info, boolean zoom) {
        try {
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(info));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            if (zoom) {
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                        .target(googleMap.getCameraPosition().target)
                        .zoom(17)
                        .bearing(30)
                        .tilt(45)
                        .build()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
                                