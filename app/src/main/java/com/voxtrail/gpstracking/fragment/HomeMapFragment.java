package com.voxtrail.gpstracking.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.activity.HomeActivity;
import com.voxtrail.gpstracking.fragmentcontroller.FragmentController;
import com.voxtrail.gpstracking.pojo.VehiclePOJO;
import com.voxtrail.gpstracking.util.Constants;
import com.voxtrail.gpstracking.util.Pref;
import com.voxtrail.gpstracking.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeMapFragment extends FragmentController implements OnMapReadyCallback {
    GoogleMap googleMap;
    @BindView(R.id.ll_prev)
    LinearLayout ll_prev;
    @BindView(R.id.ll_next)
    LinearLayout ll_next;
    @BindView(R.id.iv_map_type)
    ImageView iv_map_type;
    @BindView(R.id.iv_traffic)
    ImageView iv_traffic;

    boolean is_satellite_view = false;
    boolean is_traffic_view = false;

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

        checkLocation();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        iv_map_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setMapView();
                if (is_satellite_view) {
                    iv_map_type.setImageResource(R.drawable.ic_satellite_view);
                } else {
                    iv_map_type.setImageResource(R.drawable.ic_normal_map);
                }
            }
        });
        iv_traffic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTrafficView();
                if (is_traffic_view) {
                    iv_traffic.setImageResource(R.drawable.ic_enable_traffic);
                } else {
                    iv_traffic.setImageResource(R.drawable.ic_disable_traffic);
                }
            }
        });
    }


    public void setTrafficView() {
        if (googleMap != null) {
            if (is_traffic_view) {
                is_traffic_view = false;
                googleMap.setTrafficEnabled(is_traffic_view);
            } else {
                is_traffic_view = true;
                googleMap.setTrafficEnabled(is_traffic_view);
            }
        }
    }

    public void setMapView() {
        if (googleMap != null) {
            if (is_satellite_view) {
                is_satellite_view = false;
                googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
            } else {
                is_satellite_view = true;
                googleMap.setMapType(googleMap.MAP_TYPE_SATELLITE);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        setcurrentlocationcontrol();

        if (getActivity() instanceof HomeActivity) {
            final HomeActivity homeActivity = (HomeActivity) getActivity();
            homeActivity.getVehicleList();


        }

        ll_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextVehicle();
            }
        });

        ll_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevVehicle();
            }
        });

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (getActivity() instanceof HomeActivity) {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    try {
                        homeActivity.getCompleteAddress(vehiclePOJOS.get(Integer.parseInt(marker.getTag().toString())));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return false;
            }
        });
    }

    public void setcurrentlocationcontrol() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    int time = -1;

    public void setTimer() {


        switch (Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.MONITOR_REFRESH_RATE, StringUtils.REF_10_SEC)) {
            case StringUtils.REF_DISABLE_AUTO_REFRESH:
                time = -1;
                break;
            case StringUtils.REF_10_SEC:
                time = 10000;
                break;
            case StringUtils.REF_30_SEC:
                time = 30000;
                break;
            case StringUtils.REF_1_MIN:
                time = 60000;
                break;
            case StringUtils.REF_3_MIN:
                time = 180000;
                break;
            case StringUtils.REF_5_MIN:
                time = 300000;
                break;
        }

        if (time != -1) {
            final Handler mHandler = new Handler();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    while (true) {
                        try {
                            Thread.sleep(time);
                            mHandler.post(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    // Write your code here to update the UI.
//                                    homeActivity.getVehicleList();
//                                    Log.d(TagUtils.getTag(),"call on 10 sec");
                                }
                            });
                        } catch (Exception e) {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    public void nextVehicle() {
        if (currentmarkerPosition == (vehiclePOJOS.size() - 1)) {
            makeZoomtoMarker(0);
        } else {
            makeZoomtoMarker(currentmarkerPosition + 1);
        }
    }

    public void prevVehicle() {
        if (currentmarkerPosition == 0) {
            makeZoomtoMarker(vehiclePOJOS.size() - 1);
        } else {
            makeZoomtoMarker(currentmarkerPosition - 1);
        }
    }

    List<VehiclePOJO> vehiclePOJOS;

    public void showAllDevices(List<VehiclePOJO> vehiclePOJOS) {
        this.vehiclePOJOS = vehiclePOJOS;

        if (googleMap != null) {
            if (vehiclePOJOS.size() > 0) {
                googleMap.clear();
                for (int i = 0; i < vehiclePOJOS.size(); i++) {
                    VehiclePOJO vehiclePOJO = vehiclePOJOS.get(i);
//                    if (i == (vehiclePOJOS.size() - 1)) {
//                        showLocation(vehiclePOJO.getLatitude(), vehiclePOJO.getLatitude(), vehiclePOJO.getVehicleNumber(), true);
//                    } else {
                    try {
                        showLocation(vehiclePOJO,i);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    }
                }

                makeZoomtoMarker(0);
            }
        }
    }

    public void checkLocation() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        } else {
//            double[] latlong = UtilityFunction.getLocation(getActivity().getApplicationContext());
//            showLocation(latlong[0], latlong[1], "current location", true);
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
//            double[] latlong = UtilityFunction.getLocation(getActivity().getApplicationContext());
//            showLocation(latlong[0], latlong[1], "current location", true);
        }
    }

    List<MarkerOptions> markers = new ArrayList<>();
    int currentmarkerPosition = 0;
    public void showLocation(VehiclePOJO vehiclePOJO,int position) {
        try {
            if(vehiclePOJO.getLatitude()!=null&&vehiclePOJO.getLongitude()!=null) {
                LatLng latLng = new LatLng(vehiclePOJO.getLatitude(), vehiclePOJO.getLongitude());
                BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_car_icon);
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(vehiclePOJO.getVehicleNumber()).icon(icon);
                markers.add(markerOptions);
                Marker marker = googleMap.addMarker(markerOptions);
                marker.setTag(String.valueOf(position));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeZoomtoMarker(int position) {
        try {
            if (googleMap != null) {
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markers.get(position).getPosition(), 17));
                currentmarkerPosition = position;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
                                