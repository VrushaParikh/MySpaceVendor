package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspace2.Network.Api;
import com.example.myspace2.Network.AppConfig;
import com.example.myspace2.R;
import com.example.myspace2.databinding.ActivityOffersDealsBinding;
import com.example.myspace2.model.ServerResponse;
import com.example.myspace2.utils.Config;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OffersDealsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 101;
    private ActivityOffersDealsBinding binding;
    private Context context = this;

    private String offname, offdesc, offsdate, offedate, sdd, smm, syy, edd, emm, eyy;

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


        init();
        clickListener();

//        buttonUpload = (Button) findViewById(R.id.od_submit);


    }

    private void init() {

        binding.submit.setOnClickListener(view -> {
           offname = binding.edtOffername.getText().toString().trim();
           offdesc = binding.edtOfferdesc.getText().toString().trim();

            syy = String.valueOf(binding.edtOfferstart.getYear());
            smm = String.valueOf(binding.edtOfferstart.getMonth() + 1);
            sdd = String.valueOf(binding.edtOfferstart.getDayOfMonth());

            offsdate = syy + "-" + smm + "-" + sdd;

            eyy = String.valueOf(binding.edtOfferend.getYear());
            emm = String.valueOf(binding.edtOfferend.getMonth() + 1);
            edd = String.valueOf(binding.edtOfferend.getDayOfMonth());

            offedate = eyy + "-" + emm + "-" + edd;


          Log.d(TAG, "init: " + offname + "----" + offsdate + "----" + offedate + "----" + offdesc);

            if (TextUtils.isEmpty(offname) || TextUtils.isEmpty(offdesc))
            {
                if (TextUtils.isEmpty(offname)) {
                    binding.edtOffername.setError("Offer Name Required!!");
                }

                if (TextUtils.isEmpty(offdesc)) {
                    binding.edtOfferdesc.setError("Offer Description Required!!");
                }

                return;
            }

            doOffersDealsRegister(offname, offdesc, offsdate, offedate, bitmap);

        });


    }

    private void doOffersDealsRegister(String offname, String offdesc, String offsdate, String offedate, Bitmap bitmap)
    {
        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.OffersDeals(offname, offdesc, offsdate, offedate, bitmap);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Config.showToast(context, response.body().getMessage());
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


}