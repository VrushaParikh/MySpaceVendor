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
import com.example.myspacevendor.databinding.ActivityLoginBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private final Context context = this;

    private String email, password;

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        clickListener();

    }

    private void init() {


        binding.login.setOnClickListener(view -> {

            email = binding.edtUsername.getText().toString().trim();
            password = binding.edtPwd.getText().toString().trim();

            Log.d(TAG, "init: " + binding.edtUsername.getText().toString().trim() + "----" + binding.edtPwd.getText().toString().trim());


            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                binding.edtPwd.setError("All Fields are Required!!");
                return;
            }

            doLogin(email, password);
        });


    }

    private void doLogin(String email, String password) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.login(email, password);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                Config.showToast(context, response.body().getMessage());
                Intent intent = new Intent(LoginActivity.this, CustDashActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    private void clickListener() {

        binding.signup.setOnClickListener(v -> openActivity(RegistrationActivity.class));
    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }

}
