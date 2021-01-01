package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.databinding.ActivityDashboardBinding;


public class VendorDashboardActivity extends AppCompatActivity {

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

        binding.addOffer.setOnClickListener(v -> openActivity(OffersDealsActivity.class));
        binding.img2.setOnClickListener(v -> openActivity(OffersDealsActivity.class));

        binding.mngTime.setOnClickListener(v -> openActivity(MslotActivity.class));
        binding.img6.setOnClickListener(v -> openActivity(MslotActivity.class));

    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }


}