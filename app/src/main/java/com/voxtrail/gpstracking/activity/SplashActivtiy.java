package com.voxtrail.gpstracking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.util.Pref;
import com.voxtrail.gpstracking.util.StringUtils;

public class SplashActivtiy extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_activtiy);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Pref.GetBooleanPref(getApplicationContext(), StringUtils.IS_LOGIN, false)) {
                    Intent intent = new Intent(SplashActivtiy.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivtiy.this, LoginActivity.class);
                    startActivity(intent);
                }
//                startActivity(new Intent(SplashActivtiy.this, GeoFenceActivity.class));
            }
        }, 2000);
    }
}
