package com.voxtrail.gpstracking.activity;

import android.graphics.Rect;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.util.TagUtils;
import com.voxtrail.gpstracking.util.ToastClass;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GeoFenceActivity extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap googleMap;
    @BindView(R.id.tv_altitude)
    TextView tv_altitude;
    @BindView(R.id.btn_get)
    Button btn_get;
    @BindView(R.id.viewPoint)
    View viewPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fence);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Rect rectf = new Rect();
                viewPoint.getLocalVisibleRect(rectf);
                viewPoint.getGlobalVisibleRect(rectf);

                Log.d("WIDTH          :", String.valueOf(rectf.width()));
                Log.d("HEIGHT         :", String.valueOf(rectf.height()));
                Log.d("left           :", String.valueOf(rectf.left));
                Log.d("right          :", String.valueOf(rectf.right));
                Log.d("top            :", String.valueOf(rectf.top));
                Log.d("bottom         :", String.valueOf(rectf.bottom));
                Log.d("center x       :", String.valueOf(rectf.centerX()));
                Log.d("center y       :", String.valueOf(rectf.centerY()));

                android.graphics.Point centerPoint = new android.graphics.Point(rectf.centerX(), rectf.centerY());//center of Circle on the screen
                LatLng centerLatLang = googleMap.getProjection().fromScreenLocation(centerPoint);
                int radius =rectf.centerX();
                android.graphics.Point radiusPoint = new android.graphics.Point(centerPoint.x+radius, centerPoint.y);
                LatLng radiusLatLang = googleMap.getProjection().fromScreenLocation(radiusPoint);

                float[] results = new float[1];

                Location.distanceBetween(centerLatLang.latitude, centerLatLang.longitude, radiusLatLang.latitude, radiusLatLang.longitude, results);

                float radiusInMeters = results[0];
                Log.d(TagUtils.getTag(),"radius m:-"+radiusInMeters);

                ToastClass.showLongToast(getApplicationContext(),"latitude:-"+centerLatLang.latitude+"\nlongitude:-"+centerLatLang.longitude+"\nradius:-"+radiusInMeters+
                " m \ndiameter:-"+(radiusInMeters*2)+" m");
            }
        });



    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        this.googleMap=googleMap;

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                Log.e(TagUtils.getTag(), googleMap.getCameraPosition().target.toString());
            }
        });

    }
}
