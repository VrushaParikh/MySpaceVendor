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
import com.example.myspacevendor.data.slot.Slot;
import com.example.myspacevendor.databinding.ActivityMslotBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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

    private List<Slot> slotList = new ArrayList<>();


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
            for (int i = 0; i < (total + 1); i++) {
                handleSlotList(String.valueOf(time));
                time = time.plus(interval);

            }

            fetchLoop();

        }


    }



    /*----------------------------- Handle Slot List ------------------------------*/

    private void handleSlotList(String myTime) {


        if (!slotList.isEmpty()) {

            Slot slot = slotList.get(slotList.size() - 1);
            slot.setEndSlot(myTime);
            slotList.set(slotList.size() - 1, slot);
        }

        Slot slot = new Slot(myTime, myTime);
        slotList.add(slot);
    }



    /*----------------------------- Fetch Loop ------------------------------*/

    private void fetchLoop() {

        for (Slot slot : slotList) {

            if (slotList.indexOf(slot) == 6) {
                break;
            }

            Log.d(TAG, "fetchLoop: INDEX " + slotList.indexOf(slot));


            insertSlotToDB(slot.getStartSlot(), slot.getEndSlot());

            Log.d(TAG, "Start Time : " + slot.getStartSlot() + " ---  End Time : " + slot.getEndSlot());
        }


        Config.showToast(context, "Slot Inserted");
        finish();

    }

    /*----------------------------- Insert Slot To DB ------------------------------*/

    private void insertSlotToDB(String startSlot, String endSlot) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.InsertSlot(shopId, startSlot, endSlot);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
//                Config.showToast(context, response.body().getMessage());
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

