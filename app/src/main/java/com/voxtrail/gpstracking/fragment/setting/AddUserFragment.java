package com.voxtrail.gpstracking.fragment.setting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.voxtrail.gpstracking.R;
import com.voxtrail.gpstracking.fragmentcontroller.FragmentController;
import com.voxtrail.gpstracking.util.Pref;
import com.voxtrail.gpstracking.util.StringUtils;
import com.voxtrail.gpstracking.util.TagUtils;
import com.voxtrail.gpstracking.util.ToastClass;
import com.voxtrail.gpstracking.util.UtilityFunction;
import com.voxtrail.gpstracking.webservice.WebServicesUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class AddUserFragment extends FragmentController{

    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    @BindView(R.id.et_vehicle_number)
    EditText et_vehicle_number;
    @BindView(R.id.spinner_type)
    Spinner spinner_type;
    @BindView(R.id.et_made)
    EditText et_made;
    @BindView(R.id.et_average)
    EditText et_average;
    @BindView(R.id.et_imei)
    EditText et_imei;
    @BindView(R.id.et_user_name)
    EditText et_user_name;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag_add_user,container,false);
        setUpView(getActivity(),this,view);
        return view;
    }


    public static AddUserFragment newInstance() {
        Bundle args = new Bundle();
        AddUserFragment fragment = new AddUserFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckEdits(et_password,et_confirm_password,et_email,et_average,et_imei,et_made,et_vehicle_number)){
                    saveUser();
                }else{
                    ToastClass.showShortToast(getActivity().getApplicationContext(),"Please Enter all details properly");
                }
            }
        });

    }

    public void saveUser(){
        try {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("UserName", et_user_name.getText().toString());
            jsonObject.put("Email", et_email.getText().toString());
            jsonObject.put("Password", et_password.getText().toString());
            jsonObject.put("ConfirmPassword", et_confirm_password.getText().toString());
            jsonObject.put("VehicleNumber", et_vehicle_number.getText().toString());
            jsonObject.put("VehicleType", spinner_type.getSelectedItem().toString());
            jsonObject.put("Made", et_made.getText().toString());
            jsonObject.put("Average", et_average.getText().toString());
            jsonObject.put("Imei", et_imei.getText().toString());

            Log.d(TagUtils.getTag(),"json Object:-"+jsonObject.toString());

            RequestQueue queue = Volley.newRequestQueue(getActivity());

            StringRequest getRequest = new StringRequest(Request.Method.POST, WebServicesUrls.USER_REGISTRATION,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TagUtils.getTag(),"response:-"+response.toString());
                            if(response.toString().toLowerCase().contains("success")){
                                ToastClass.showShortToast(getActivity().getApplicationContext(),"User Created Successfully");
                                onBackPressed();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TagUtils.getTag(),"error:-"+error.toString());
//                            error.printStackTrace();
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                    Log.d(TagUtils.getTag(),"obj:-"+obj.toString());

                                    JSONArray jsonArray=obj.optJSONArray("Errors");
                                    ToastClass.showShortToast(getActivity().getApplicationContext(),jsonArray.optString(0));

                                } catch (UnsupportedEncodingException e1) {
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                } catch (JSONException e2) {
                                    // returned data is not JSONObject?
                                    e2.printStackTrace();
                                }
                            }
                        }
                    }
            ) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return jsonObject.toString().getBytes();
                }
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Authorization", Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.TOKEN_TYPE, "") + " " + Pref.GetStringPref(getActivity().getApplicationContext(), StringUtils.ACCESS_TOKEN, ""));
                    headers.put("Content-Type", "application/json");
                    UtilityFunction.printAllValues(headers);
                    return headers;
                }
            };
            queue.add(getRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean CheckEdits(EditText... editTexts){
        for(EditText editText:editTexts){
            if(editText.getText().toString().length()==0){
                return false;
            }
        }
        return true;
    }



}
