package com.voxgps.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.voxgps.app.R;
import com.voxgps.app.util.Pref;
import com.voxgps.app.util.StringUtils;
import com.voxgps.app.util.ToastClass;
import com.voxgps.app.util.UtilityFunction;
import com.voxgps.app.webservice.WebServiceBase;
import com.voxgps.app.webservice.WebServicesCallBack;
import com.voxgps.app.webservice.WebServicesUrls;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_user_name)
    EditText et_user_name;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
//                finish();
                if(et_user_name.getText().toString().length()>0
                        &&et_password.getText().toString().length()>0) {
                    callLoginAPI();
                }else{
                    ToastClass.showShortToast(getApplicationContext(),"Please Enter UserName and Pasword properly");
                }
            }
        });
    }

    public void callLoginAPI() {
        ArrayList<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("username", et_user_name.getText().toString()));
        nameValuePairs.add(new BasicNameValuePair("password", et_password.getText().toString()));
//        nameValuePairs.add(new BasicNameValuePair("device_token", Pref.GetDeviceToken(getApplicationContext(),"")));
        nameValuePairs.add(new BasicNameValuePair("grant_type", "password"));
        new WebServiceBase(nameValuePairs,null, this, new WebServicesCallBack() {
            @Override
            public void onGetMsg(String apicall, String response) {
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    String access_token=jsonObject.getString("access_token");
                    String token_type=jsonObject.getString("token_type");
                    String userName=jsonObject.getString("userName");
                    String Role=jsonObject.getString("Role");

                    Pref.SetStringPref(getApplicationContext(), StringUtils.ACCESS_TOKEN,access_token);
                    Pref.SetStringPref(getApplicationContext(), StringUtils.TOKEN_TYPE,token_type);
                    Pref.SetStringPref(getApplicationContext(), StringUtils.USERNAME,userName);
                    Pref.SetStringPref(getApplicationContext(), StringUtils.ROLE,Role);

                    Pref.SetBooleanPref(getApplicationContext(),StringUtils.IS_LOGIN,true);

                    UtilityFunction.setPreValuesChecked(getApplicationContext());

                    startActivity(new Intent(LoginActivity.this,HomeActivity.class));

                }catch (Exception e){
                    try{
                        JSONObject jsonObject=new JSONObject(response);
                        String error=jsonObject.optString("error");
                        String error_description=jsonObject.optString("error_description");

                        ToastClass.showShortToast(getApplicationContext(),error_description);
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                }
            }
        }, "LOGIN_API", true).execute(WebServicesUrls.TOKEN);
    }
}
