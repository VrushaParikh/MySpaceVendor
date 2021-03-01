package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.databinding.ActivityMslotBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MslotActivity extends AppCompatActivity {

    private ActivityMslotBinding binding;
    private Context context = this;
    private int sduration;
    private int stotal;
    private int vendor_id = 3;
    private int shop_id = 4;

    private static final String TAG = "Mslot Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMslotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();




    }



    private void init() {
        binding.slotSubmit.setOnClickListener(view -> {
            sduration =Integer.parseInt((String) binding.spinDura.getSelectedItem());
            stotal = Integer.parseInt((String) binding.spinno.getSelectedItem());
            Log.d(TAG, "init: " + sduration + "----" + stotal);
            openActivity(DashboardActivity.class);

            doManageSlot(vendor_id, shop_id, sduration, stotal);

        });

    }



    private void doManageSlot(int vendor_id, int shop_id, int sduration, int stotal) {
        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.ManageSlot(vendor_id, shop_id, sduration, stotal);
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


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }


}

