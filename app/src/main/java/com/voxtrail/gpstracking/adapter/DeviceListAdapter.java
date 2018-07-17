package com.voxtrail.gpstracking.adapter;

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

import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.activity.DeviceDataActivity;
import com.voxtrail.gpstracking.pojo.VehiclePOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolder>{
    private List<VehiclePOJO> items;
    Activity activity;
    Fragment fragment;

    public DeviceListAdapter(Activity activity, Fragment fragment, List<VehiclePOJO> items) {
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
                Intent intent=new Intent(activity, DeviceDataActivity.class);
                intent.putExtra("vehiclePOJO",items.get(position));
                activity.startActivity(intent);
            }
        });
        holder.tv_vehicle_name.setText(items.get(position).getVehicleNumber());
        holder.tv_type.setText(items.get(position).getVehicleType());

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
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
