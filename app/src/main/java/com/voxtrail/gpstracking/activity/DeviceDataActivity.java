package com.voxtrail.gpstracking.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.adapter.ViewPagerAdapter;
import com.voxtrail.gpstracking.fragment.DeviceMapFragment;
import com.voxtrail.gpstracking.fragment.MessageFragment;
import com.voxtrail.gpstracking.fragment.PlayBackFragment;
import com.voxtrail.gpstracking.fragment.setting.DeviceSettingFragment;
import com.voxtrail.gpstracking.fragmentcontroller.ActivityManager;
import com.voxtrail.gpstracking.pojo.VehiclePOJO;

import java.util.ArrayList;
import java.util.List;

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

    }

    PlayBackFragment playBackFragment;
    private void setupViewPager(ViewPager viewPager) {

        DeviceMapFragment homeFragment1 = DeviceMapFragment.newInstance();
        playBackFragment = PlayBackFragment.newInstance();
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
                iv_message.setImageResource(R.drawable.ic_message_sel);
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
        iv_message.setImageResource(R.drawable.ic_message_unsel);
        iv_setting.setImageResource(R.drawable.ic_setting_black);
    }


}
