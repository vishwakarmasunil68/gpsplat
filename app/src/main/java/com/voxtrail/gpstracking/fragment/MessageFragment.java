package com.voxtrail.gpstracking.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.activity.DeviceDataActivity;
import com.voxtrail.gpstracking.fragmentcontroller.FragmentController;

import butterknife.BindView;

public class MessageFragment extends FragmentController {

    @BindView(R.id.tv_number)
    TextView tv_number;
    @BindView(R.id.tv_imei)
    TextView tv_imei;
    @BindView(R.id.tv_sim_card)
    TextView tv_sim_card;
    @BindView(R.id.tv_model)
    TextView tv_model;
    @BindView(R.id.tv_activation_time)
    TextView tv_activation_time;
    @BindView(R.id.tv_expiration_date)
    TextView tv_expiration_date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_message, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }


    public static MessageFragment newInstance() {
        Bundle args = new Bundle();
        MessageFragment fragment = new MessageFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            if (getActivity() instanceof DeviceDataActivity) {
                DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();
                tv_number.setText(deviceDataActivity.vehiclePOJO.getVehicleNumber());
//                tv_imei.setText(deviceDataActivity.vehiclePOJO.getVehicleNumber());
//                tv_sim_card.setText(deviceDataActivity.vehiclePOJO.getVehicleNumber());
                tv_model.setText(String.valueOf(deviceDataActivity.vehiclePOJO.getVehicleModel()));
//                tv_activation_time.setText(deviceDataActivity.vehiclePOJO.getVehicleNumber());
//                tv_expiration_date.setText(deviceDataActivity.vehiclePOJO.getVehicleNumber());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
