package com.example.myspace2.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspace2.databinding.ActivityDashboardBinding;


public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private Context context = this;

    private static final String TAG = "DashboardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        clickListener();

    }


    private void init() {

    }

    private void clickListener() {

        binding.shopInfo.setOnClickListener(v -> openActivity(ShopInfoActivity.class));
        binding.img1.setOnClickListener(v -> openActivity(ShopInfoActivity.class));

    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }


}