package com.example.myspace2.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspace2.Network.Api;
import com.example.myspace2.Network.AppConfig;
import com.example.myspace2.databinding.ActivityLoginBinding;
import com.example.myspace2.model.ServerResponse;
import com.example.myspace2.utils.Config;

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



        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = binding.edtEmail.getText().toString().trim();
                password = binding.edtPwd.getText().toString().trim();

                Log.d(TAG, "init: " + binding.edtEmail.getText().toString().trim() + "----" + binding.edtPwd.getText().toString().trim());



                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    binding.edtPwd.setError("All Fields are Required!!");
                    return;
                }

                doLogin(email, password);
            }
        });


    }

    private void doLogin(String email, String password) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.login(email, password);
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

        binding.signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity(RegistrationActivity.class);
            }
        });
    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }
//da82092aa93166b007c074cc7da738a27b81e4d7
}
