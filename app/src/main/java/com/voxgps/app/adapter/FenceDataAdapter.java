package com.voxgps.app.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.voxgps.app.R;
import com.voxgps.app.activity.PolyGeoFenceShowActivity;
import com.voxgps.app.activity.VehicleStatusActivity;
import com.voxgps.app.pojo.NotificationPOJO;
import com.voxgps.app.pojo.fence.FencePOJO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class FenceDataAdapter extends RecyclerView.Adapter<FenceDataAdapter.ViewHolder>{
    private List<FencePOJO> items;
    Activity activity;
    Fragment fragment;

    public FenceDataAdapter(Activity activity, Fragment fragment, List<FencePOJO> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_fence_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if(items.get(position).getAddress()!=null) {
            holder.tv_name.setText(String.valueOf(items.get(position).getAddress()));
        }else{
            holder.tv_name.setText("");
        }

        holder.ll_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity, PolyGeoFenceShowActivity.class);
                intent.putExtra("fences",items.get(position));
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
        @BindView(R.id.ll_data)
        LinearLayout ll_data;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
