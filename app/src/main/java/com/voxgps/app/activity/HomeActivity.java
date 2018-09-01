package com.voxgps.app.activity;

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
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.tt.whorlviewlibrary.WhorlView;
import com.voxgps.app.R;
import com.voxgps.app.adapter.ViewPagerAdapter;
import com.voxgps.app.fragment.AlertFragment;
import com.voxgps.app.fragment.DeviceListFragment;
import com.voxgps.app.fragment.HomeMapFragment;
import com.voxgps.app.fragment.MeFragment;
import com.voxgps.app.fragment.setting.NotificationSettingFragment;
import com.voxgps.app.fragmentcontroller.ActivityManager;
import com.voxgps.app.pojo.VehiclePOJO;
import com.voxgps.app.pojo.device.DeviceDetailPOJO;
import com.voxgps.app.util.Constants;
import com.voxgps.app.util.Pref;
import com.voxgps.app.util.StringUtils;
import com.voxgps.app.util.TagUtils;
import com.voxgps.app.util.UtilityFunction;
import com.voxgps.app.webservice.WebServicesUrls;

import org.json.JSONArray;
import org.json.JSONObject;

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
    @BindView(R.id.graph)
    GraphView graph;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;
    @BindView(R.id.tv_vehicle_name)
    TextView tv_vehicle_name;
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
    @BindView(R.id.iv_close)
    ImageView iv_close;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    List<TextView> textViewList = new ArrayList<>();
    List<ImageView> imageViewList = new ArrayList<>();

    int[] colors = new int[]{Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.RED, Color.GRAY, Color.MAGENTA, Color.CYAN};
    String[] hexColors = new String[]{"#0000FF", "#008000", "#FFFF00", "#000000", "#FF0000", "#808080", "#EE00EE", "##00FFFF"};

//    TextView textView;

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
//                sendRawData();
                homeMapFragment.getVehicleList(false);
            }
        });
//        updateDeviceToken();

        showGraph();


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
//        tv_user_name.setText(Pref.GetStringPref(getApplicationContext(), StringUtils.USERNAME, ""));
        tv_user_name.setText(Constants.userDetail.getUsername());
    }

    public void slidingLogic(DeviceDetailPOJO deviceDetailPOJO, String address) {
        if (sliding_layout != null &&
                (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
        }

        try {
            tv_vehicle_name.setText(String.valueOf(deviceDetailPOJO.getPlateNumber()) + " Data");
            tv_latitude.setText(String.valueOf(deviceDetailPOJO.getLng()));
            tv_longitude.setText(String.valueOf(deviceDetailPOJO.getLng()));
            tv_speed.setText(String.valueOf(deviceDetailPOJO.getSpeed() + " km/h"));
            tv_mileage.setText(String.valueOf(deviceDetailPOJO.getSpeed() + " km/l"));
            tv_battery.setText(String.valueOf(deviceDetailPOJO.getOdometer() + " %"));
            tv_avg_mileage.setText(String.valueOf(deviceDetailPOJO.getSpeed() + " km/l"));
            tv_address.setText(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showGraph() {
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 6),
                new DataPoint(4, 2)
        });
        graph.addSeries(series);
    }

    public List<VehiclePOJO> vehiclePOJOS = new ArrayList<>();

    public void showWheelLoading() {
        ll_wheel.setVisibility(View.VISIBLE);
        wheelLoading.start();
    }

    public void hideWheelLoading() {
        ll_wheel.setVisibility(View.GONE);
        wheelLoading.stop();
    }

    public void updateDeviceToken() {

        if (!Pref.GetBooleanPref(getApplicationContext(), StringUtils.TOKEN_UPDATED, false)) {
            String url = WebServicesUrls.updateDeviceToken(Pref.GetDeviceToken(getApplicationContext(), ""));
            Log.d(TagUtils.getTag(), "url:-" + url);
            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.toString().toLowerCase().contains("success")) {
                                Pref.SetBooleanPref(getApplicationContext(), StringUtils.TOKEN_UPDATED, true);
                            } else {
                                Pref.SetBooleanPref(getApplicationContext(), StringUtils.TOKEN_UPDATED, false);
                            }
                            Log.d(TagUtils.getTag(), "response:-" + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
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
    }

    public DeviceListFragment deviceListFragment;
    public HomeMapFragment homeMapFragment;

    private void setupViewPager(ViewPager viewPager) {

        homeMapFragment = HomeMapFragment.newInstance();
        deviceListFragment = DeviceListFragment.newInstance();
        final AlertFragment alertFragment = AlertFragment.newInstance();
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
                if (position == 2) {
                    alertFragment.initialize();
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
//                iv_search.setVisibility(View.VISIBLE);
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

    public void getCompleteAddress(final DeviceDetailPOJO deviceDetailPOJO) {
        try {
            showProgressBar();
            String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng=" + String.valueOf(deviceDetailPOJO.getLat()) + "," + String.valueOf(deviceDetailPOJO.getLng()) + "&sensor=true";

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
                                slidingLogic(deviceDetailPOJO, formatted_address);
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
        } catch (Exception e) {
            dismissProgressBar();
            e.printStackTrace();
        }
    }

}
