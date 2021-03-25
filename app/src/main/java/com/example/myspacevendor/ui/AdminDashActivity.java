package com.example.myspacevendor.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.R;
import com.example.myspacevendor.databinding.ActivityAdminDashBinding;
import com.example.myspacevendor.utils.SharedPrefManager;


public class AdminDashActivity extends AppCompatActivity
{
    private ActionBarDrawerToggle toggle;
    private ActivityAdminDashBinding binding;
    private Context context = this;
    private SharedPrefManager sharedPrefManager;

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


        setSupportActionBar(binding.includedContent.includedToolbar.customToolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_toggle);

        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.includedContent.includedToolbar.customToolbar, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        binding.nav.setNavigationItemSelectedListener(this::onNavigationItemSelected);

    }

    /*--------------------------------- On Options Item Selected -----------------------------------------*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    /*--------------------------------- On Navigation Item Selected -----------------------------------------*/



    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                openActivity(DashboardActivity.class);
                return true;


            case R.id.nav_setting:
                openActivity(ChangePasswordActivity.class);
                return true;

            case R.id.nav_logout:
                sharedPrefManager.clear();
                openActivity(LoginActivity.class);
                return true;

            default:
                return false;
        }
    }


    private void clickListener() {



        binding.includedContent.block1.setOnClickListener(v -> {

            openActivity(VendorListActivity.class);

        });
        binding.includedContent.block2.setOnClickListener(v -> {
            openActivity(TermsPoliciesActivity.class);
        });
//        binding.includedContent.block3.setOnClickListener(v -> {
//            openActivity(TermsPoliciesActivity.class);
//        });



    }



    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }





}
