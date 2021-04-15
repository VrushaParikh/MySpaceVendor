package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.data.Shop;

import com.example.myspacevendor.databinding.ActivityOdViewShopBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.myspacevendor.ui.LoginActivity.id;
import static com.example.myspacevendor.ui.ViewShopActivity.shopId;

public class OdViewShopActivity extends AppCompatActivity {


    public static int shopId = 0;
    public static int id=0;
    private Context context = this;
    private ActivityOdViewShopBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOdViewShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleToolbar();
        init();
    }

    private void init() {
        Intent intent = getIntent();
        shopId = intent.getIntExtra("shop_id", 0);


        Config.showToast(context, "" + shopId);

        fetchShops(String.valueOf(shopId));
        clickListener();

    }

    /*--------------------------------- Handle Toolbar --------------------------------*/

    private void handleToolbar() {

        binding.includedToolbar.title.setText(" Offer Deals: Shop Information");
        binding.includedToolbar.backBtn.setOnClickListener(v -> finish());
    }

    private void clickListener() {

        binding.od.setOnClickListener(v -> {
            Intent intent = new Intent(context, OffersDealsActivity.class);
            intent.putExtra("shop_id", shopId);
            intent.putExtra("vid",id);
            startActivity(intent);
        });
    }


    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);
    }


    private void fetchShops(String shopId) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.getShopDetails(shopId);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

                ServerResponse serverResponse = response.body();
                handleShopData(serverResponse.getShop());

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Config.showToast(context, t.getMessage());
            }
        });

    }

    private void handleShopData(Shop shop) {

        binding.ipShname.setText(shop.getShopName());
        binding.ipAdd.setText(shop.getShopAdd());
        binding.ipPincode.setText(shop.getShopPincode());
        binding.ipMail.setText(shop.getShopEmail());
        binding.ipBankna.setText(shop.getShopBankName());
        binding.ipBankifsc.setText(shop.getShopIfsc());
        binding.ipBankacc.setText(shop.getShopAccNo());
        binding.ipSgst.setText(shop.getShopGst());
        binding.ipSpan.setText(shop.getShopPan());
        binding.ipSsqft.setText(shop.getShopSqft());
        binding.ipLic.setText(shop.getShopLicNo());
        binding.ipTime.setText(shop.getShopTiming());

    }
}
