package com.voxtrail.gpstracking.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tt.whorlviewlibrary.WhorlView;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.adapter.ViewPagerAdapter;
import com.voxtrail.gpstracking.fragment.AlertFragment;
import com.voxtrail.gpstracking.fragment.DeviceListFragment;
import com.voxtrail.gpstracking.fragment.HomeMapFragment;
import com.voxtrail.gpstracking.fragment.MeFragment;
import com.voxtrail.gpstracking.fragment.setting.NotificationSettingFragment;
import com.voxtrail.gpstracking.fragmentcontroller.ActivityManager;
import com.voxtrail.gpstracking.pojo.VehiclePOJO;
import com.voxtrail.gpstracking.util.Pref;
import com.voxtrail.gpstracking.util.StringUtils;
import com.voxtrail.gpstracking.util.TagUtils;
import com.voxtrail.gpstracking.util.UtilityFunction;
import com.voxtrail.gpstracking.webservice.WebServicesUrls;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends ActivityManager {

    @BindView(R.id.ll_monitor)
    LinearLayout ll_monitor;
    @BindView(R.id.ll_device)
    LinearLayout ll_device;
    @BindView(R.id.ll_alert)
    LinearLayout ll_alert;
    @BindView(R.id.ll_me)
    LinearLayout ll_me;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.iv_refresh)
    ImageView iv_refresh;
    @BindView(R.id.tv_monitor)
    TextView tv_monitor;
    @BindView(R.id.tv_device)
    TextView tv_device;
    @BindView(R.id.tv_alert)
    TextView tv_alert;
    @BindView(R.id.tv_me)
    TextView tv_me;
    @BindView(R.id.iv_me)
    ImageView iv_me;
    @BindView(R.id.iv_alert)
    ImageView iv_alert;
    @BindView(R.id.iv_device)
    ImageView iv_device;
    @BindView(R.id.iv_monitor)
    ImageView iv_monitor;
    @BindView(R.id.iv_setting)
    ImageView iv_setting;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.iv_menu)
    ImageView iv_menu;
    @BindView(R.id.wheelLoading)
    WhorlView wheelLoading;
    @BindView(R.id.ll_wheel)
    LinearLayout ll_wheel;

    List<TextView> textViewList = new ArrayList<>();
    List<ImageView> imageViewList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        textViewList.add(tv_monitor);
        textViewList.add(tv_device);
        textViewList.add(tv_alert);
        textViewList.add(tv_me);

        imageViewList.add(iv_monitor);
        imageViewList.add(iv_device);
        imageViewList.add(iv_alert);
        imageViewList.add(iv_me);

        setupViewPager(viewPager);

        ll_monitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });
        ll_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
        ll_alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2);
            }
        });
        ll_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3);
            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu menu = new PopupMenu(HomeActivity.this, view);

                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuitem) {
                        switch (menuitem.getItemId()) {
                            case R.id.popup_read_all:
                                break;
                            case R.id.popup_delete_all:
                                break;
                        }
                        return false;
                    }
                });
                menu.inflate(R.menu.menu_alert);
                menu.show();
            }
        });

        iv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFragment(R.id.frame_home, NotificationSettingFragment.newInstance());
            }
        });

        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getVehicleList();
            }
        });

//        getVehicleList();
    }

    public List<VehiclePOJO> vehiclePOJOS = new ArrayList<>();
    public void showWheelLoading(){
        ll_wheel.setVisibility(View.VISIBLE);
        wheelLoading.start();
    }

    public void hideWheelLoading(){
        ll_wheel.setVisibility(View.GONE);
        wheelLoading.stop();
    }
    public void getVehicleList() {
        showWheelLoading();
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest getRequest = new StringRequest(Request.Method.GET, WebServicesUrls.VEHICLE_LIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        hideWheelLoading();
                        Log.d(TagUtils.getTag(), "vehicle list:-" + response.toString());
                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());
                            vehiclePOJOS.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                vehiclePOJOS.add(new Gson().fromJson(jsonArray.optJSONObject(i).toString(), VehiclePOJO.class));
                            }
                            if (deviceListFragment != null) {
                                homeMapFragment.showAllDevices(vehiclePOJOS);
                                deviceListFragment.attachAdapter(vehiclePOJOS);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hideWheelLoading();
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", Pref.GetStringPref(getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
                UtilityFunction.printAllValues(headers);
                return headers;
            }
        };
        queue.add(getRequest);


    }

    DeviceListFragment deviceListFragment;
    HomeMapFragment homeMapFragment;

    private void setupViewPager(ViewPager viewPager) {

        homeMapFragment = HomeMapFragment.newInstance();
        deviceListFragment = DeviceListFragment.newInstance();
        AlertFragment alertFragment = AlertFragment.newInstance();
        MeFragment homeFragment4 = MeFragment.newInstance();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(homeMapFragment, "Monitor");
        adapter.addFrag(deviceListFragment, "Device");
        adapter.addFrag(alertFragment, "Alert");
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

        switch (position) {
            case 0:
                iv_monitor.setImageResource(R.drawable.ic_globe_selected);
                break;
            case 1:
                iv_device.setImageResource(R.drawable.ic_list_sel);
                break;
            case 2:
                iv_alert.setImageResource(R.drawable.ic_alert_selected);
                break;
            case 3:
                iv_me.setImageResource(R.drawable.ic_user_sel);
                break;
        }

        iv_search.setVisibility(View.GONE);
        iv_setting.setVisibility(View.GONE);
        iv_menu.setVisibility(View.GONE);
        iv_refresh.setVisibility(View.GONE);

        switch (position) {
            case 0:
                iv_refresh.setVisibility(View.VISIBLE);
                break;
            case 1:
                iv_search.setVisibility(View.VISIBLE);
                break;
            case 2:
                iv_setting.setVisibility(View.VISIBLE);
                iv_menu.setVisibility(View.VISIBLE);
                break;
            case 3:
                break;
        }

    }

    public void setDefaultImages() {
        iv_monitor.setImageResource(R.drawable.ic_globe_unsel);
        iv_device.setImageResource(R.drawable.ic_list_unsel);
        iv_alert.setImageResource(R.drawable.ic_alert_unsel);
        iv_me.setImageResource(R.drawable.ic_user_unsel);
    }
}
