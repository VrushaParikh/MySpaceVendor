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
import com.example.myspacevendor.databinding.ActivityMslotBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;

import java.time.Duration;
import java.time.LocalTime;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MslotActivity extends AppCompatActivity {

    private ActivityMslotBinding binding;
    private final Context context = this;
    private int sduration;
    private int stotal;

    private static final String TAG = "Mslot Activity";

    private int shopId;

    private String shopStartTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMslotBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        shopId = intent.getIntExtra("shop_id", 0);


        init();
        getShopDetails(shopId);

    }


    private void init() {
        binding.slotSubmit.setOnClickListener(view -> {
            sduration = Integer.parseInt((String) binding.spinDura.getSelectedItem());
            stotal = Integer.parseInt((String) binding.spinno.getSelectedItem());
            Log.d(TAG, "init: " + sduration + "----" + stotal);


//            doManageSlot(sduration, stotal);


            handleTimeLoop(sduration, stotal);

        });

    }


    /*------------------- Handle Time Loop -----------------------*/
    private void handleTimeLoop(int duration, int total) {


        if (TextUtils.isEmpty(shopStartTime)) {
            Config.showToast(context, "Start time is empty");
            return;
        }

        String[] t2 = shopStartTime.split(":", 2);

        Duration interval = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            interval = Duration.ofMinutes(duration);

            LocalTime time = LocalTime.of(Integer.parseInt(String.valueOf(t2[0])), 0);
            for (int i = 0; i < total; i++) {
                time = time.plus(interval);

                insertSlotToDB(String.valueOf(time));

            }

            Config.showToast(context, "Slot Inserted");
            finish();
        }


    }


    private void insertSlotToDB(String time) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.InsertSlot(shopId, time, time);
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


    private void doManageSlot(int sduration, int stotal) {
        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.ManageSlot(shopId, sduration, stotal);
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



    /*------------------------------- Get Shop Details ---------------------*/


    private void getShopDetails(int shopId) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.getShopDetails(String.valueOf(shopId));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                if (response.body() != null) {
                    ServerResponse serverResponse = response.body();
                    shopStartTime = serverResponse.getShop().getShopOpenTime();
                }


            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Config.showToast(context, t.getMessage());
            }
        });
    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }


}

