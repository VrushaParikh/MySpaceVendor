package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspace2.Network.Api;
import com.example.myspace2.Network.AppConfig;
import com.example.myspace2.databinding.ActivityRegisterBinding;
import com.example.myspace2.model.ServerResponse;
import com.example.myspace2.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private Context context = this;

    private String fname, username, email, password, category;
    private String phno, ad_no, yy, mm, dd, dob;

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
            fname = binding.edtFname.getText().toString().trim();
            username = binding.edtUsername.getText().toString().trim();
            email = binding.edtEmail.getText().toString().trim();
            category = binding.spinCat.getSelectedItem().toString().trim();

            yy = String.valueOf(binding.datePicker.getYear());
            mm = String.valueOf(binding.datePicker.getMonth() + 1);
            dd = String.valueOf(binding.datePicker.getDayOfMonth());


            dob = yy + "-" + mm + "-" + dd;


            password = binding.edtPwd.getText().toString().trim();
            phno = binding.edtPh.getText().toString().trim();
            ad_no = binding.edtAdno.getText().toString().trim();


//            Log.d(TAG, "init: " + fname + "----" + email + "----" + category + "----" + dob + "----" + password + "----" + ad_no + "----" + phno);


            if (TextUtils.isEmpty(fname) || TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(category) || TextUtils.isEmpty(dob) || TextUtils.isEmpty(password) || TextUtils.isEmpty(phno) || TextUtils.isEmpty(ad_no)) {
                if (TextUtils.isEmpty(fname)) {
                    binding.edtFname.setError("Full Name Required!!");
                }

                if (TextUtils.isEmpty(username)) {
                    binding.edtFname.setError("User Name Required!!");
                }

                if (TextUtils.isEmpty(email)) {
                    binding.edtEmail.setError("Email Required!!");
                }

                if (TextUtils.isEmpty(password)) {
                    binding.edtPwd.setError("Password Required!!");
                }

                if (TextUtils.isEmpty(phno)) {
                    binding.edtPh.setError("Phone Number Required!!");
                }

                if (TextUtils.isEmpty(ad_no)) {
                    binding.edtAdno.setError("Aadhaar Number Required!!");
                }

                return;
            }
            doRegister(fname, username, email, category, dob, phno, password, ad_no);

        });


    }

    private void doRegister(String fname, String username, String email, String category, String dob, String phno, String pwd, String ad_no) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.register(fname, username, email, category, dob, phno, pwd, ad_no);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Config.showToast(context, response.body().getMessage());
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