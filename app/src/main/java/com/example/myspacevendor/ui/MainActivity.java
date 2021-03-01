package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static int splash_timeout = 3000;

    private ActivityMainBinding binding;
    private Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(context, LoginActivity.class);

                startActivity(i);

                finish();

            }
        }, splash_timeout);
    }
}
