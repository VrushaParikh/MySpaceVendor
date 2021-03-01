package com.example.myspacevendor.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.R;
import com.example.myspacevendor.databinding.ActivityDashboardBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.myspacevendor.ui.LoginActivity.vendor_email;
import static com.example.myspacevendor.ui.LoginActivity.vendor_pwd;
import static java.lang.Integer.parseInt;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle toggle;
    private ActivityDashboardBinding binding;
    private final Context context = this;
    private final Activity activity = this;
    private static String name;

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
        getName(vendor_email, vendor_pwd);


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

        binding.includedContent.block1.setOnClickListener(v -> {ShopProfile(vendor_email,vendor_pwd);});
        binding.includedContent.block2.setOnClickListener(v -> {openActivity(OffersDealsActivity.class);});
        binding.includedContent.block4.setOnClickListener(v -> {openActivity(ScanTokenActivity.class);});
        binding.includedContent.block6.setOnClickListener(v -> {openActivity(MslotActivity.class);});
        binding.includedContent.block5.setOnClickListener(v -> {openActivity(PaymentActivity.class);});


    }

    private void openActivity(Class aclass)
    {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }


    private void ShopProfile(String vendor_email, String vendor_pwd)
    {

        Retrofit retrofit;
        retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.ShopProfile(vendor_email, vendor_pwd);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Log.d(TAG, "onSuccess: " + response.body().getMessage());
                int c = parseInt(response.body().getMessage());
                if(c>0)
                {
                    Intent intent = new Intent(DashboardActivity.this, ShopListActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(DashboardActivity.this, ShopInfoActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    //get name

    private void getName(String vendor_email, String vendor_pwd)
    {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.getVdName(vendor_email, vendor_pwd);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                name = response.body().getMessage();

                Log.d(TAG, "name fetched: " + name);
                Name(name);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });



    }

    private void Name(String na)
    {
        Log.d(TAG, "hello " + na);

        RelativeLayout rl= binding.includedContent.includedContent;


        final TextView textView;
        textView = new TextView(this);

        textView.setText("Welcome "+na);
        textView.setTextSize(18f);
        textView.setPadding(400, 300, 10, 8);
        // textView.setTextColor(rnd.nextInt() | 0xff000000);

        textView.setTextColor(Color.BLACK);
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        int curTextViewId = 777;
        textView.setId(curTextViewId);
        final RelativeLayout.LayoutParams params =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);


        textView.setLayoutParams(params);


        rl.addView(textView, params);

    }


}//main class closing