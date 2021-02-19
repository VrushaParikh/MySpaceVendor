package com.example.myspacevendor.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myspacevendor.R;
import com.example.myspacevendor.databinding.ActivityDashboardBinding;
import com.example.myspacevendor.utils.Config;
import com.google.android.material.navigation.NavigationView;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    private ActivityDashboardBinding binding;
    private final Context context = this;
    private final Activity activity = this;
    private static final String TAG = "DashboardActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        init();
        clickListener();
   }


    private void init()
    {

        toggle = new ActionBarDrawerToggle(activity, binding.drawerLayout, R.string.open, R.string.close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setSupportActionBar(binding.includedContent.includedToolbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        binding.nav.setNavigationItemSelectedListener(this);


    }
    /*--------------------------------- On Options Item Selected -----------------------------------------*/


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        if (toggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    /*--------------------------------- On Navigation Item Selected -----------------------------------------*/


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {

        switch (item.getItemId())
        {
            case R.id.nav_home:
                Config.showToast(activity, "Home");
                return true;

            case R.id.nav_gallery:
                Config.showToast(activity, "Edit Profile");
                return true;

            case R.id.nav_slideshow:
                Config.showToast(activity, "Settings");
                return true;

            default:
                return false;
        }
    }


    private void clickListener()
    {

//        binding.block1.setOnClickListener(v -> openActivity(ShopInfoActivity.class));

        binding.includedContent.block1.setOnClickListener(v -> {openActivity(ShopListActivity.class);});
        binding.includedContent.block2.setOnClickListener(v -> {openActivity(OffersDealsActivity.class);});
        binding.includedContent.block4.setOnClickListener(v -> {openActivity(ScanTokenActivity.class);});
        binding.includedContent.block6.setOnClickListener(v -> {openActivity(MslotActivity.class);});

    }

    private void openActivity(Class aclass)
    {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }


}