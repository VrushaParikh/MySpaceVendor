package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.R;
import com.example.myspacevendor.databinding.ActivityMslotBinding;
import com.example.myspacevendor.databinding.ActivityScanTokenBinding;
import com.example.myspacevendor.databinding.LayoutSuccessDialogBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ScanTokenActivity extends AppCompatActivity {
    Button scan_btn;
    public static TextView scan_text;
    private ActivityScanTokenBinding binding;
    private Context context = this;


    private static final String TAG = "ScanTokenActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityScanTokenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleToolbar();
        init();
    }

    private void init() {
        scan_text = binding.result;
        scan_btn = binding.scan;

        scan_btn.setOnClickListener(view -> {

            Intent intent = new Intent(context, SacnnerView.class);
            startActivityForResult(intent, 1001);
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1001 && resultCode == RESULT_OK) {

            if (data != null) {
                String tokenID = data.getStringExtra("tokenId");
                verifyBooking(tokenID);
            } else {
                Config.showToast(context, "Data is NULL");

            }
        } else {
            Config.showToast(context, "Token not scanned");

        }

    }

    /*--------------------------------- Handle Toolbar --------------------------------*/

    private void handleToolbar() {

        binding.includedToolbar.title.setText("Scan Token");
        binding.includedToolbar.backBtn.setOnClickListener(v -> finish());
    }








    /*----------------------------- Verify Booking  ---------------------------------*/

    private void verifyBooking(String tokenID) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.scanToken(tokenID);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {


                if (response.body() != null) {

                    ServerResponse response1 = response.body();
                    displaySuccessResult(response1.getError(), response1.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }



    /*----------------------------- Display QR Dialog  ---------------------------------*/

    private void displaySuccessResult(boolean isWrong, String msg) {


        // Create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("QR Code");

        LayoutSuccessDialogBinding qrDialogBinding = LayoutSuccessDialogBinding.inflate(getLayoutInflater(), null, false);
        builder.setView(qrDialogBinding.getRoot());


        if (!isWrong) {
            qrDialogBinding.imageQR.setImageResource(R.drawable.ic_approve);
        } else {
            qrDialogBinding.imageQR.setImageResource(R.drawable.ic_rejected);
        }
        qrDialogBinding.tvStatus.setText(msg);

        // add a button
        builder.setPositiveButton(
                "OK",
                (dialog, which) -> {

                    dialog.dismiss();
                });


        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
