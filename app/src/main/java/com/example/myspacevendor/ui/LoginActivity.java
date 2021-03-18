package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.data.user.User;
import com.example.myspacevendor.databinding.ActivityLoginBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;
import com.example.myspacevendor.utils.SharedPrefManager;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private final Context context = this;

    public static String vendor_email, vendor_pwd;
    public static String id;

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

            vendor_email = binding.edtEmail.getText().toString().trim();
            vendor_pwd = binding.edtPwd.getText().toString().trim();

            Log.d(TAG, "init: " + binding.edtEmail.getText().toString().trim() + "----" + binding.edtPwd.getText().toString().trim());


            if (TextUtils.isEmpty(vendor_email) || TextUtils.isEmpty(vendor_pwd)) {
                if (TextUtils.isEmpty(vendor_email)) {
                    binding.edtEmail.setError("Email Required!!");
                }

                if (TextUtils.isEmpty(vendor_pwd)) {
                    binding.edtPwd.setError("Password Required!!");
                }

                return;
            }

            doLogin(vendor_email, vendor_pwd);
        });


    }

    private void doLogin(String vendor_email, String vendor_pwd) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);


        Call<ServerResponse> call = service.login(vendor_email, vendor_pwd);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(@NotNull Call<ServerResponse> call, @NotNull Response<ServerResponse> response) {


                if (response.body() != null) {

                }


                ServerResponse response1 = response.body();
                Config.showToast(context, response1.getMessage());


                if (!response1.getError()) {
                    sendUserData(response1.getUser());
                }
            }


            @Override
            public void onFailure(@NotNull Call<ServerResponse> call, @NotNull Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);

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


    /*----------------------------------------------- Save User Data -----------------------------------------------*/

    private void sendUserData(User user) {

        SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        sharedPrefManager.setInt("id", Integer.parseInt(user.getVendorId()));
        sharedPrefManager.setString("name", user.getVendorName());

        sharedPref();
    }


    @Override
    protected void onStart() {
        super.onStart();

        sharedPref();

    }

    private void sharedPref() {
        SharedPrefManager sharedPrefManager = new SharedPrefManager(context);
        Config.user_id = sharedPrefManager.getInt("id");

        if (Config.user_id != -1) {

            openActivity(DashboardActivity.class);
            finish();
        }

    }

}
