package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.adapters.VendorNameListAdapter;
import com.example.myspacevendor.data.Vendor_Name_List;
import com.example.myspacevendor.databinding.ActivityVendorListBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;
import com.example.myspacevendor.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class VendorListActivity extends AppCompatActivity implements VendorNameListAdapter.VendorNameInterface {

    private ActivityVendorListBinding binding;
    private Context context = this;

    private List<Vendor_Name_List> vendor_name_listArrayList = new ArrayList<>();

    private static final String TAG = "VendorList Activity";

    private VendorNameListAdapter vendorNameListAdapter;
    private SharedPrefManager sharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityVendorListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleToolbar();
        init();
        fetchVendors();
//        clickListener();


    }


    private void init() {

        sharedPrefManager = new SharedPrefManager(context);

        vendorNameListAdapter = new VendorNameListAdapter(vendor_name_listArrayList, this);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(vendorNameListAdapter);

    }

//    private void clickListener() {
//
//        binding..setOnClickListener(v -> openActivity(ShopListActivity.class));
//
//
//
//
//    }
//
//
//    private void openActivity(Class aclass) {
//        Intent intent = new Intent(context, aclass);
//        startActivity(intent);
//    }


    /*--------------------------------- Handle Toolbar --------------------------------*/

    private void handleToolbar() {

        binding.includedToolbar.title.setText("Manage Timeslot");
        binding.includedToolbar.backBtn.setOnClickListener(v -> finish());
    }

    /*----------------------------- Get Shop Data From Server ----------------------------*/


    private void fetchVendors() {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.vendorList();
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                if (response.body() != null) {
                    ServerResponse serverResponse = response.body();

                    Config.showToast(context, serverResponse.getMessage());


                    vendor_name_listArrayList.clear();
                    vendor_name_listArrayList.addAll(serverResponse.getVendor_name_lists());
                    vendorNameListAdapter.notifyDataSetChanged();
                    binding.loadingSpinner.setVisibility(View.GONE);
                } else {
                    Config.showToast(context, "Response Body Is Null");
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Config.showToast(context, t.getMessage());
            }
        });

    }


    @Override
    public void onClick(Vendor_Name_List vendorNameList) {

        Intent intent = new Intent(context, VendorListActivity.class);
        intent.putExtra("vendor_id", vendorNameList.getVendorId());
        startActivity(intent);
    }
}
