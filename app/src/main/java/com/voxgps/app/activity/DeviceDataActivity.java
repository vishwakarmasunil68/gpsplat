package com.voxgps.app.activity;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.voxgps.app.R;
import com.voxgps.app.adapter.ViewPagerAdapter;
import com.voxgps.app.fragment.DeviceEditFragment;
import com.voxgps.app.fragment.DeviceMapFragment;
import com.voxgps.app.fragment.MessageFragment;
import com.voxgps.app.fragment.NewPlayBackFragment;
import com.voxgps.app.fragment.setting.DeviceSettingFragment;
import com.voxgps.app.fragmentcontroller.ActivityManager;
import com.voxgps.app.pojo.VehiclePOJO;
import com.voxgps.app.util.TagUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceDataActivity extends ActivityManager {

    @BindView(R.id.ll_track)
    LinearLayout ll_track;
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.iv_track)
    ImageView iv_track;
    @BindView(R.id.tv_track)
    TextView tv_track;
    @BindView(R.id.iv_playback)
    ImageView iv_playback;
    @BindView(R.id.ll_playback)
    LinearLayout ll_playback;
    @BindView(R.id.tv_playback)
    TextView tv_playback;
    @BindView(R.id.ll_message)
    LinearLayout ll_message;
    @BindView(R.id.iv_message)
    ImageView iv_message;
    @BindView(R.id.tv_message)
    TextView tv_message;
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;
    @BindView(R.id.iv_setting)
    ImageView iv_setting;
    @BindView(R.id.tv_setting)
    TextView tv_setting;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_select)
    ImageView iv_select;
    @BindView(R.id.tv_edit)
    TextView tv_edit;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;
    @BindView(R.id.tv_latitude)
    TextView tv_latitude;
    @BindView(R.id.tv_longitude)
    TextView tv_longitude;
    @BindView(R.id.tv_speed)
    TextView tv_speed;
    @BindView(R.id.tv_mileage)
    TextView tv_mileage;
    @BindView(R.id.tv_battery)
    TextView tv_battery;
    @BindView(R.id.tv_avg_mileage)
    TextView tv_avg_mileage;
    @BindView(R.id.tv_vehicle_name)
    public TextView tv_vehicle_name;
    @BindView(R.id.iv_close)
    public ImageView iv_close;
    @BindView(R.id.tv_address)
    TextView tv_address;

    public VehiclePOJO vehiclePOJO;

    List<TextView> textViewList = new ArrayList<>();
    List<ImageView> imageViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_data);
        ButterKnife.bind(this);

        vehiclePOJO = (VehiclePOJO) getIntent().getSerializableExtra("vehiclePOJO");

        textViewList.add(tv_track);
        textViewList.add(tv_playback);
        textViewList.add(tv_message);
        textViewList.add(tv_setting);

        imageViewList.add(iv_track);
        imageViewList.add(iv_playback);
        imageViewList.add(iv_message);
        imageViewList.add(iv_setting);

        setupViewPager(viewPager);

        ll_track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        ll_playback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        ll_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        iv_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playBackFragment.showScheduleDialog();
            }
        });

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sliding_layout != null &&
                        (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
                    sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                } else {
                    sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }

            }
        });

        tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(R.id.frame_home,new DeviceEditFragment());
            }
        });

    }

    NewPlayBackFragment playBackFragment;

    private void setupViewPager(ViewPager viewPager) {

        DeviceMapFragment homeFragment1 = DeviceMapFragment.newInstance();
        playBackFragment = NewPlayBackFragment.newInstance();
        MessageFragment messageFragment = MessageFragment.newInstance();
        DeviceSettingFragment homeFragment4 = DeviceSettingFragment.newInstance();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(homeFragment1, "Monitor");
        adapter.addFrag(playBackFragment, "Device");
        adapter.addFrag(messageFragment, "Alert");
        adapter.addFrag(homeFragment4, "Me");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(adapter.getCount());

        changePagerIconsandcolor(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changePagerIconsandcolor(position);
                if (position == 1) {
                    playBackFragment.initialize();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void changePagerIconsandcolor(int position) {
        for (TextView textView : textViewList) {
            textView.setTextColor(Color.parseColor("#757575"));
        }
        setDefaultImages();

        textViewList.get(position).setTextColor(Color.parseColor("#59C3FF"));

        iv_select.setVisibility(View.GONE);
        tv_edit.setVisibility(View.GONE);

        switch (position) {
            case 0:
                iv_track.setImageResource(R.drawable.ic_track_sel);
                break;
            case 1:
                iv_playback.setImageResource(R.drawable.ic_play_sel);
                iv_select.setVisibility(View.VISIBLE);
                break;
            case 2:
                iv_message.setImageResource(R.drawable.ic_info_sel);
                tv_edit.setVisibility(View.VISIBLE);
                break;
            case 3:
                iv_setting.setImageResource(R.drawable.ic_setting_sel);
                break;
        }


//        iv_track.setVisibility(View.GONE);
//        iv_playback.setVisibility(View.GONE);
//        iv_message.setVisibility(View.GONE);
//        iv_setting.setVisibility(View.GONE);

    }

    public void setDefaultImages() {
        iv_track.setImageResource(R.drawable.ic_track_unsel);
        iv_playback.setImageResource(R.drawable.ic_play_unsel);
        iv_message.setImageResource(R.drawable.ic_info);
        iv_setting.setImageResource(R.drawable.ic_setting_black);
    }

    public void slidingLogic(VehiclePOJO vehiclePOJO, String address) {
        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }

        try {
            tv_latitude.setText(String.valueOf(vehiclePOJO.getLatitude()));
            tv_longitude.setText(String.valueOf(vehiclePOJO.getLongitude()));
            tv_speed.setText(String.valueOf(vehiclePOJO.getSpeed() + " km/h"));
            tv_mileage.setText(String.valueOf(vehiclePOJO.getMileage() + " km/l"));
            tv_battery.setText(String.valueOf(vehiclePOJO.getBatteryLevel() + " %"));
            tv_avg_mileage.setText(String.valueOf(vehiclePOJO.getTodaysMileage() + " km/l"));
            tv_address.setText(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getCompleteAddress(final VehiclePOJO vehiclePOJO) {
        try {
            showProgressBar();
            String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(vehiclePOJO.getLatitude()) + "," + String.valueOf(vehiclePOJO.getLongitude()) + "&sensor=true";

            StringRequest req = new StringRequest(url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dismissProgressBar();
                            try {
                                Log.d(TagUtils.getTag(), "address response:-" + response);
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.optJSONArray("results");
                                JSONObject jsonObject1 = jsonArray.optJSONObject(0);
                                String formatted_address = jsonObject1.optString("formatted_address");
                                slidingLogic(vehiclePOJO,formatted_address);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dismissProgressBar();
                    Log.d(TagUtils.getTag(), "api error:-" + error.toString());
                }
            });
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            queue.add(req);
        }catch (Exception e){
            dismissProgressBar();
            e.printStackTrace();
        }

//        try {
//            showProgressBar();
//            String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(vehiclePOJO.getLatitude()) + "," + String.valueOf(vehiclePOJO.getLongitude()) + "&sensor=true";
//
//            StringRequest req = new StringRequest(url,
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//                            dismissProgressBar();
//                            try {
//                                Log.d(TagUtils.getTag(), "address response:-" + response);
//                                JSONObject jsonObject = new JSONObject(response);
//                                JSONArray jsonArray = jsonObject.optJSONArray("results");
//                                JSONObject jsonObject1 = jsonArray.optJSONObject(0);
//                                String formatted_address = jsonObject1.optString("formatted_address");
//                                slidingLogic(vehiclePOJO, formatted_address);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    dismissProgressBar();
//                    Log.d(TagUtils.getTag(), "api error:-" + error.toString());
//                }
//            });
//            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//            queue.add(req);
//        } catch (Exception e) {
//            dismissProgressBar();
//            e.printStackTrace();
//        }
//        try {
//            Geocoder geocoder;
//            List<Address> addresses;
//            geocoder = new Geocoder(this, Locale.getDefault());
//
//            addresses = geocoder.getFromLocation(vehiclePOJO.getLatitude(), vehiclePOJO.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
//
////            String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//            StringBuilder strReturnedAddress = new StringBuilder("");
//            Address returnedAddress = addresses.get(0);
//            for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
//                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
//            }
//            String strAdd = "";
//            strAdd = strReturnedAddress.toString();
//            String city = addresses.get(0).getLocality();
//            String state = addresses.get(0).getAdminArea();
//            String country = addresses.get(0).getCountryName();
//            String postalCode = addresses.get(0).getPostalCode();
//            String knownName = addresses.get(0).getFeatureName();
//
//            Log.d(TagUtils.getTag(), "address:-" + strAdd);
//            Log.d(TagUtils.getTag(), "city:-" + city);
//            Log.d(TagUtils.getTag(), "state:-" + state);
//            Log.d(TagUtils.getTag(), "country:-" + country);
//            Log.d(TagUtils.getTag(), "postalCode:-" + postalCode);
//            Log.d(TagUtils.getTag(), "knownName:-" + knownName);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
