package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.R;

import com.example.myspacevendor.databinding.ActivityOffersDealsBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.myspacevendor.ui.LoginActivity.id;
import static com.example.myspacevendor.ui.OdViewShopActivity.shopId;


public class OffersDealsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 101;
    private ActivityOffersDealsBinding binding;

    private Context context = this;


    private String offer_name, offer_desc, offer_start, offer_end, sdd, smm, syy, edd, emm, eyy;


    private static final String TAG = "OffersDealsActivity";

    //Bitmap to get image from gallery
    private Bitmap bitmap;

    //Uri to store the image uri
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers_deals);
        binding = ActivityOffersDealsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleToolbar();
        init();
        clickListener();

//        buttonUpload = (Button) findViewById(R.id.od_submit);


    }

    private void init() {
        Intent intent = getIntent();
        shopId = intent.getIntExtra("shop_id", 0);
        id = String.valueOf(intent.getIntExtra("vid", 0));

        binding.submit.setOnClickListener(view -> {
            offer_name = binding.edtOffername.getText().toString().trim();
            offer_desc = binding.edtOfferdesc.getText().toString().trim();

            syy = String.valueOf(binding.edtOfferstart.getYear());
            smm = String.valueOf(binding.edtOfferstart.getMonth() + 1);
            sdd = String.valueOf(binding.edtOfferstart.getDayOfMonth());

            offer_start = syy + "-" + smm + "-" + sdd;

            eyy = String.valueOf(binding.edtOfferend.getYear());
            emm = String.valueOf(binding.edtOfferend.getMonth() + 1);
            edd = String.valueOf(binding.edtOfferend.getDayOfMonth());

            offer_end = eyy + "-" + emm + "-" + edd;


            Log.d(TAG, "init: " + offer_name + "----" + offer_start + "----" + offer_end + "----" + offer_desc);

            if (TextUtils.isEmpty(offer_name) || TextUtils.isEmpty(offer_desc)) {
                if (TextUtils.isEmpty(offer_name)) {
                    binding.edtOffername.setError("Offer Name Required!!");
                }

                if (TextUtils.isEmpty(offer_desc)) {
                    binding.edtOfferdesc.setError("Offer Description Required!!");
                }

                return;
            }

            Log.d(TAG, "IDS: " + id+ "-------" +shopId );
            doOffersDealsRegister(id,String.valueOf(shopId) ,offer_name, offer_desc, offer_start, offer_end, bitmap);

        });


    }

    /*--------------------------------- Handle Toolbar --------------------------------*/

    private void handleToolbar() {

        binding.includedToolbar.title.setText("Offers and Deal");
        binding.includedToolbar.backBtn.setOnClickListener(v -> finish());
    }


    private void doOffersDealsRegister(String vendor_id,String shop_id,String offname, String offdesc, String offsdate, String offedate, Bitmap bitmap) {
        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.OffersDeals(vendor_id,shop_id,offname, offdesc, offsdate, offedate, getImageString(bitmap));
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Config.showToast(context, response.body().getMessage());
                openActivity(DashboardActivity.class);
                finish();


            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });


    }

    private void clickListener() {

        binding.odSubmit.setOnClickListener(view -> showFileChooser());
    }


    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }


    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                binding.imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /*----------------------- Bitmap to Image String -------------------------------*/

    public String getImageString(Bitmap bitmap) {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();

        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    private void openActivity(Class aclass) {
        Intent intent = new Intent(context, aclass);
        startActivity(intent);

    }

}