package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.databinding.ActivityRegisterBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private Context context = this;

    private String vendor_name, vendor_email, vendor_pwd, vendor_mobile, vendor_aadhar;

    private static final String TAG = "RegistrationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        clickListener();

    }


    private void init() {

        binding.register.setOnClickListener(view -> {
            vendor_name = binding.edtFname.getText().toString().trim();
            vendor_email = binding.edtEmail.getText().toString().trim();
            vendor_pwd = binding.edtPwd.getText().toString().trim();
            vendor_mobile = binding.edtPh.getText().toString().trim();
            vendor_aadhar = binding.edtAdno.getText().toString().trim();


//            Log.d(TAG, "init: " + vendor_name + "----" + vendor_email + "----" + vendor_pwd + "----" + vendor_aadhar + "----" + vendor_mobile);


            if (TextUtils.isEmpty(vendor_name) || TextUtils.isEmpty(vendor_email)  || TextUtils.isEmpty(vendor_pwd) || TextUtils.isEmpty(vendor_mobile) || TextUtils.isEmpty(vendor_aadhar)) {
                if (TextUtils.isEmpty(vendor_name)) {
                    binding.edtFname.setError("Full Name Required!!");
                }

                if (TextUtils.isEmpty(vendor_email)) {
                    binding.edtEmail.setError("Email Required!!");
                }

                if (TextUtils.isEmpty(vendor_pwd)) {
                    binding.edtPwd.setError("Password Required!!");
                }

                if (TextUtils.isEmpty(vendor_mobile)) {
                    binding.edtPh.setError("Phone Number Required!!");
                }

                if (TextUtils.isEmpty(vendor_aadhar)) {
                    binding.edtAdno.setError("Aadhaar Number Required!!");
                }

                return;
            }

            doRegister(vendor_name, vendor_email, vendor_pwd, vendor_mobile, vendor_aadhar);

        });


    }

    private void doRegister(String vendor_name, String vendor_email, String vendor_pwd, String vendor_mobile, String vendor_aadhar) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.Register(vendor_name, vendor_email, vendor_pwd, vendor_mobile, vendor_aadhar);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Config.showToast(context, response.body().getMessage());
                Intent intent = new Intent(RegistrationActivity.this, VendorDashboardActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void clickListener() {

        binding.already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity(LoginActivity.class);
            }
        });
    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }


}