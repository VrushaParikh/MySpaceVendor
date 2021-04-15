package com.example.myspacevendor.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.adapters.ViewHistoryAdapter;
import com.example.myspacevendor.data.Booking;
import com.example.myspacevendor.databinding.ActivityViewTokenBinding;
import com.example.myspacevendor.model.ServerResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.myspacevendor.ui.LoginActivity.id;
import static com.example.myspacevendor.ui.ViewShopActivity.shopId;

public class ViewTokenActivity extends AppCompatActivity implements ViewHistoryAdapter.BookingInterface {


    private ActivityViewTokenBinding binding;
    private final Context context = this;
    private ViewHistoryAdapter viewHistoryAdapter;
    private final List<Booking> bookingList = new ArrayList<>();


    private static final String TAG = "ViewTokenActivtiy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityViewTokenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleToolbar();
        init();
        fetchBookingData();


    }

    /*--------------------------------- Handle Toolbar --------------------------------*/

    private void handleToolbar() {

        binding.includedToolbar.title.setText("View Token");
        binding.includedToolbar.backBtn.setOnClickListener(v -> finish());
    }



    /*--------------------------------- Init --------------------------------*/

    private void init() {
        viewHistoryAdapter = new ViewHistoryAdapter(bookingList,this);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(viewHistoryAdapter);

    }


    /*--------------------------------- Fetch Booking Data --------------------------------*/

    private void fetchBookingData() {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);
        Log.d(TAG, "ID: " + id);
        Log.d(TAG, "ID: " + shopId);


        Call<ServerResponse> call = service.getTokenView(id,shopId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {


                if (response.body() != null) {

                    ServerResponse response1 = response.body();

                    bookingList.clear();
                    bookingList.addAll(response1.getBookingList());
                    viewHistoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    /*----------------------------------- Open Activity -----------------------------------------*/

    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);

    }


    @Override
    public void onClick(Booking booking) {

    }


}
