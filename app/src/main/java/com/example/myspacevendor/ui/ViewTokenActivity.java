package com.example.myspacevendor.ui;


import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.databinding.ActivityViewTokenBinding;

public class ViewTokenActivity extends AppCompatActivity  {


    private ActivityViewTokenBinding binding;
    private final Context context = this;


    private static final String TAG = "ViewTokenActivtiy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityViewTokenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleToolbar();
        init();

    }

    /*--------------------------------- Handle Toolbar --------------------------------*/

    private void handleToolbar() {

        binding.includedToolbar.title.setText("View Token");
        binding.includedToolbar.backBtn.setOnClickListener(v -> finish());
    }




    /*--------------------------------- Init --------------------------------*/

    private void init() {


    }

}
