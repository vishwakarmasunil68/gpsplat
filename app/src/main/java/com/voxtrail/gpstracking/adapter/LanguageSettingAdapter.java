package com.voxtrail.gpstracking.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.voxtrail.gpstracking.R;

import net.igenius.customcheckbox.CustomCheckBox;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by sunil on 03-11-2017.
 */

public class LanguageSettingAdapter extends RecyclerView.Adapter<LanguageSettingAdapter.ViewHolder> {
    private List<String> items;
    Activity activity;
    Fragment fragment;

    List<CustomCheckBox> customCheckBoxes = new ArrayList<>();

    public LanguageSettingAdapter(Activity activity, Fragment fragment, List<String> items) {
        this.items = items;
        this.activity = activity;
        this.fragment = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inflate_language_setting_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (!customCheckBoxes.contains(holder.check)) {
            customCheckBoxes.add(holder.check);
        }

        holder.tv_language.setText(items.get(position));

        holder.check.setOnCheckedChangeListener(new CustomCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CustomCheckBox checkBox, boolean isChecked) {
                if (isChecked) {
                    for (CustomCheckBox customCheckBox : customCheckBoxes) {
                        if (!customCheckBox.equals(holder.check)) {
                            customCheckBox.setChecked(false);
                        }
                    }
//                    holder.check.setChecked(true);
                }
            }
        });
//        holder.check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.check.setChecked(true);
//            }
//        });

        holder.itemView.setTag(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.check)
        CustomCheckBox check;
        @BindView(R.id.tv_language)
        TextView tv_language;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
