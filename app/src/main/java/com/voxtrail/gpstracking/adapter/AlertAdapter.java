package com.voxtrail.gpstracking.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.activity.VehicleStatusActivity;
import com.voxtrail.gpstracking.pojo.NotificationPOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class AlertAdapter extends RecyclerView.Adapter<AlertAdapter.ViewHolder>{
    private List<NotificationPOJO> items;
    Activity activity;
    Fragment fragment;

    public AlertAdapter(Activity activity, Fragment fragment, List<NotificationPOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_alert_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tv_name.setText(String.valueOf(items.get(position).getColVehicleNumber()));
        holder.tv_date.setText(String.valueOf(items.get(position).getColAlertDateTime()));
        holder.tv_status.setText(String.valueOf(items.get(position).getColAlert()));

        holder.ll_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, VehicleStatusActivity.class) ;
                intent.putExtra("notificationPOJO",items.get(position));
                activity.startActivity(intent);
            }
        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_status)
        TextView tv_status;
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.ll_notification)
        LinearLayout ll_notification;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
