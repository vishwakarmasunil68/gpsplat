package com.voxgps.app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.voxgps.app.R;
import com.voxgps.app.activity.DeviceDataActivity;
import com.voxgps.app.fragmentcontroller.FragmentController;

import butterknife.BindView;

public class DeviceEditFragment extends FragmentController {

    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_imer)
    EditText et_imer;
    @BindView(R.id.et_sim_card)
    EditText et_sim_card;
    @BindView(R.id.et_vehicle_number)
    EditText et_vehicle_number;
    @BindView(R.id.et_model)
    EditText et_model;
    @BindView(R.id.iv_back)
    ImageView iv_back;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_device_edit, container, false);
        setUpView(getActivity(), this, view);
        return view;
    }


    public static DeviceEditFragment newInstance() {
        Bundle args = new Bundle();
        DeviceEditFragment fragment = new DeviceEditFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if (getActivity() instanceof DeviceDataActivity) {
            DeviceDataActivity deviceDataActivity = (DeviceDataActivity) getActivity();
            et_name.setText(deviceDataActivity.vehiclePOJO.getVehicleNumber());
            et_imer.setText("1654845125");
            ;
            et_sim_card.setText("9876543210");
            ;
            et_vehicle_number.setText(deviceDataActivity.vehiclePOJO.getVehicleNumber());
            if (deviceDataActivity.vehiclePOJO.getVehicleModel() != null) {
                et_model.setText(deviceDataActivity.vehiclePOJO.getVehicleModel().toString());
            }
        }
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
