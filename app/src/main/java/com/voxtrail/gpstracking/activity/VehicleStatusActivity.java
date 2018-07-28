package com.voxtrail.gpstracking.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.pojo.NotificationPOJO;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VehicleStatusActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    NotificationPOJO notificationPOJO;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_status);
        ButterKnife.bind(this);

        notificationPOJO = (NotificationPOJO) getIntent().getSerializableExtra("notificationPOJO");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        try {
            showLocation(Double.parseDouble(notificationPOJO.getColLatitude()), Double.parseDouble(notificationPOJO.getColLongitude()), notificationPOJO.getColVehicleNumber());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showLocation(double latitude, double longitude, String info) {
        try {
            LatLng latLng = new LatLng(latitude, longitude);
            googleMap.addMarker(new MarkerOptions().position(latLng).title(info));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(googleMap.getCameraPosition().target)
                    .zoom(17)
                    .bearing(30)
                    .tilt(45)
                    .build()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
