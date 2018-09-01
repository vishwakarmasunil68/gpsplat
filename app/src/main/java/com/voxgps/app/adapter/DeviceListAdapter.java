package com.voxgps.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.voxgps.app.R;
import com.voxgps.app.activity.DeviceDataActivity;
import com.voxgps.app.pojo.VehiclePOJO;
import com.voxgps.app.pojo.device.DevicePOJO;
import com.voxgps.app.webservice.WebServicesUrls;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder> {
    private List<DevicePOJO> items;
    Activity activity;
    Fragment fragment;

    public DeviceListAdapter(Activity activity, Fragment fragment, List<DevicePOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_device_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.ll_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DeviceDataActivity.class);
                intent.putExtra("devicePOJO", items.get(position));
                activity.startActivity(intent);
            }
        });
        holder.tv_vehicle_name.setText(items.get(position).getDeviceDetailPOJO().getName());

//        Glide.with(activity.getApplicationContext())
//                .load(WebServicesUrls.IMAGE_BASE_URL+items.get(position).getDeviceDetailPOJO().getIcon())
//                .into(holder.iv_vehicle);

        holder.tv_type.setText("Moving ("+items.get(position).getDeviceDetailPOJO().getSpeed()+"km/h)");

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_device)
        LinearLayout ll_device;
        @BindView(R.id.tv_vehicle_name)
        TextView tv_vehicle_name;
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.iv_vehicle)
        ImageView iv_vehicle;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
