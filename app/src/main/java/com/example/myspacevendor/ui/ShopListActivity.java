package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.adapters.ShopAdapter;
import com.example.myspacevendor.data.Shop;
import com.example.myspacevendor.databinding.ActivityAdminShopListBinding;
import com.example.myspacevendor.databinding.ActivityShopListBinding;

import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;
import com.example.myspacevendor.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.myspacevendor.ui.LoginActivity.vendor_email;
import static com.example.myspacevendor.ui.LoginActivity.vendor_pwd;
import static com.example.myspacevendor.ui.VendorListActivity.vid;
import static com.example.myspacevendor.utils.Config.user_id;


public class ShopListActivity extends AppCompatActivity implements ShopAdapter.RestaurantInterface {

    private ActivityShopListBinding binding;

    private Context context = this;

    private List<Shop> shopArrayList = new ArrayList<>();

    private static final String TAG = "ShopListActivity";

    private ShopAdapter shopAdapter;
    private SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShopListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleToolbar();
        init();
        fetchShops();
//        clickListener();


    }


    private void init() {

        sharedPrefManager = new SharedPrefManager(context);


        shopAdapter = new ShopAdapter(shopArrayList, this);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(shopAdapter);
        binding.addShop.setOnClickListener(view -> openActivity(ShopInfoActivity.class));


    }

//    private void clickListener() {
//
//        binding..setOnClickListener(v -> openActivity(ShopListActivity.class));
//
//    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }


    /*--------------------------------- Handle Toolbar --------------------------------*/

    private void handleToolbar() {

        binding.includedToolbar.title.setText("Shop List");
        binding.includedToolbar.backBtn.setOnClickListener(v -> finish());
    }


    /*----------------------------- Get Shop Data From Server ----------------------------*/


    private void fetchShops() {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.getAllShop(String.valueOf(user_id));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse serverResponse = response.body();
                shopArrayList.clear();
                shopArrayList.addAll(serverResponse.getShopList());
                shopAdapter.notifyDataSetChanged();

                binding.loadingSpinner.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Config.showToast(context, t.getMessage());
            }
        });

    }

    @Override
    public void onClick(Shop shop) {


        Intent intent = new Intent(context,ViewShopActivity.class);
        intent.putExtra("shop_id",shop.getShopId());
        startActivity(intent);
    }

}






