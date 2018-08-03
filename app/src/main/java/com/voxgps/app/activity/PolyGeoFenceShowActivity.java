package com.voxgps.app.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.voxgps.app.R;
import com.voxgps.app.fragmentcontroller.ActivityManager;
import com.voxgps.app.pojo.fence.FencePOJO;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PolyGeoFenceShowActivity extends ActivityManager implements OnMapReadyCallback {

    @BindView(R.id.iv_back)
    ImageView iv_back;
    GoogleMap googleMap;
    FencePOJO fencePOJO;
    String data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poly_geo_fence_show);
        ButterKnife.bind(this);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fencePOJO = (FencePOJO) getIntent().getSerializableExtra("fences");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    public List<LatLng> splitData(String data) {
        String[] stringLatLongs = data.split(",");
        int count = stringLatLongs.length;
        int halfcount = count / 2;
        List<LatLng> latLngs = new ArrayList<>();
        for (int i = 0; i < halfcount; i++) {
            LatLng latLng = new LatLng(parseDouble(stringLatLongs[i * 2]), parseDouble(stringLatLongs[(i * 2) + 1]));
            latLngs.add(latLng);
        }

        return latLngs;

    }

    public double parseDouble(String value) {
        double db = 0.0;
        try {
            db = Double.parseDouble(value);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return db;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (fencePOJO != null) {
            showLocation(splitData(fencePOJO.getFencePloygonData()));
        }
    }

    public void showLocation(List<LatLng> latLngs) {
        PolygonOptions rectOptions = new PolygonOptions();
        for (int i = 0; i < latLngs.size(); i++) {
            LatLng latLng = latLngs.get(i);
            if (i == 0) {
                showPolyLocation(latLng.latitude, latLng.longitude, true);
            } else {
                showPolyLocation(latLng.latitude, latLng.longitude, false);
            }

            rectOptions.add(latLng);
        }
        rectOptions.strokeColor(Color.RED);
        rectOptions.fillColor(Color.BLUE);
        googleMap.addPolygon(rectOptions);
    }


    public void showPolyLocation(double lat, double lng, boolean zoom) {

        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_polygon_marker);

        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("").icon(icon);
        Marker marker = googleMap.addMarker(markerOptions);
        if (zoom) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }

    }


}
