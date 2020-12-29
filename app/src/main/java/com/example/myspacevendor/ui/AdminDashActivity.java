package com.example.myspacevendor.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspace2.databinding.ActivityAdminDashBinding;

public class AdminDashActivity extends AppCompatActivity
{

    private ActivityAdminDashBinding binding;
    private Context context = this;

    private static final String TAG = "AdminDashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminDashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        clickListener();

    }


    private void init() {

    }

    private void clickListener() {

        binding.mngTnp.setOnClickListener(v -> openActivity(TermsPoliciesActivity.class));
        binding.img3.setOnClickListener(v -> openActivity(TermsPoliciesActivity.class));



    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }





}
