package com.example.myspace2.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspace2.databinding.ActivityRegisterBinding;


public class RegistrationActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        clickListener();

    }


    private void init() {


    }

    private void clickListener() {

        binding.already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openActivity(LoginActivity.class);
            }
        });
    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }


}