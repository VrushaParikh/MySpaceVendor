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
import com.example.myspacevendor.databinding.ActivityChangePasswordBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangePasswordActivity extends AppCompatActivity {



    private ActivityChangePasswordBinding binding;
    private Context context = this;
    private String email,old_pwd,new_pwd;



    private static final String TAG = "ChangePasswordActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);

        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        handleToolbar();
        init();


    }

    private void init()
    {

        binding.change.setOnClickListener(view -> {

            email = binding.edtEmail.getText().toString().trim();
            old_pwd = binding.edtOldpwd.getText().toString().trim();
            new_pwd = binding.edtNewPwd.getText().toString().trim();

            Log.d(TAG, "init: " + email + "----" + old_pwd + "----"+ new_pwd);


            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(old_pwd) || TextUtils.isEmpty(new_pwd)) {
                if (TextUtils.isEmpty(email)) {
                    binding.edtEmail.setError("Email Address Required!!");
                }
                if (TextUtils.isEmpty(old_pwd)) {
                    binding.edtOldpwd.setError("Enter old password!!");
                }
                if (TextUtils.isEmpty(new_pwd)) {
                    binding.edtNewPwd.setError("Enter new password!!");
                }
                return;
            }

            doChange(email, old_pwd,new_pwd);
        });

    }

    /*---------------------------------------- Do Change Password ----------------------------------------------------------------*/

    private void doChange(String email, String old_pwd, String new_pwd ) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.change(email, old_pwd, new_pwd);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {


                if (response.body() != null) {

                    ServerResponse serverResponse = response.body();

                    if (!serverResponse.getError()) {
                        Config.showToast(context, serverResponse.getMessage());
                        openActivity(DashboardActivity.class);
                        finish();

                    } else {
                        Config.showToast(context, serverResponse.getMessage());

                    }
                }


            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    /*--------------------------------- Handle Toolbar --------------------------------*/

    private void handleToolbar() {

        binding.includedToolbar.title.setText("Change Password");
        binding.includedToolbar.backBtn.setOnClickListener(v -> finish());
    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);

    }


}
