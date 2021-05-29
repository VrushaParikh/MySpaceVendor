package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;

import com.example.myspacevendor.databinding.ActivityShopInfoBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.myspacevendor.ui.LoginActivity.id;

public class ShopInfoActivity extends AppCompatActivity {
    private ActivityShopInfoBinding binding;
    private Context context = this;

    private String  shop_name, shop_add, shop_pincode, shop_email, shop_gst, shop_pan, shop_bank_name, shop_ifsc,
            shop_acc_no, shop_sqft, shop_lic_no, shop_timing;

    private String start, mstart, sampm, end, mend, eampm;

    private static final String TAG = "ShopInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShopInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleToolbar();
        init();


    }


    private void init()
    {
        binding.shReg.setOnClickListener(view -> {
            shop_name = binding.edtSname.getText().toString().trim();
            shop_add = binding.edtAdd.getText().toString().trim();
            shop_pincode =binding.edtPincode.getText().toString().trim();
            shop_email = binding.edtMail.getText().toString().trim();
            shop_gst = binding.edtSgst.getText().toString().trim();
            shop_pan = binding.edtSpan.getText().toString().trim();
            shop_bank_name = binding.edtBankna.getText().toString().trim();
            shop_ifsc = binding.edtBankifsc.getText().toString().trim();
            shop_acc_no = binding.edtBankacc.getText().toString().trim();
            shop_sqft = binding.edtSsqft.getText().toString().trim();
            shop_lic_no = binding.edtLic.getText().toString().trim();

            start = binding.spinStart.getSelectedItem().toString().trim();
            mstart = binding.spinStartMin.getSelectedItem().toString().trim();
            sampm = binding.spinStartAmPm.getSelectedItem().toString().trim();

            end = binding.spinEnd.getSelectedItem().toString().trim();
            mend = binding.spinEndMin.getSelectedItem().toString().trim();
            eampm = binding.spinEndAmPm.getSelectedItem().toString().trim();

            shop_timing = start+":"+mstart+sampm+"-"+end+":"+mend+eampm;

//            Log.d(TAG, "init: " + shop_name + "----" + start + "----" + mstart + "----" + sampm + "----" + end + "----" + mend + "----" + eampm);
            Log.d(TAG, "init: " + shop_name + "----" + shop_timing+ "----" + shop_lic_no);

            if (TextUtils.isEmpty(shop_name) || TextUtils.isEmpty(shop_add) || TextUtils.isEmpty(shop_email) || TextUtils.isEmpty(shop_pincode)
                    || TextUtils.isEmpty(shop_bank_name) || TextUtils.isEmpty(shop_ifsc) || TextUtils.isEmpty(shop_acc_no)
                    || TextUtils.isEmpty(shop_gst) || TextUtils.isEmpty(shop_pan) || TextUtils.isEmpty(shop_sqft)
                    || TextUtils.isEmpty(shop_lic_no))
            {
                if (TextUtils.isEmpty(shop_name)) {
                    binding.edtSname.setError("Shop Name Required!!");
                }

                if (TextUtils.isEmpty(shop_add)) {
                    binding.edtAdd.setError("Address Required!!");
                }


                if (TextUtils.isEmpty(shop_email)) {
                    binding.edtMail.setError("Email Required!!");
                }

                if (TextUtils.isEmpty(shop_pincode)) {
                    binding.edtPincode.setError("Pincode Required!!");
                }

                if (TextUtils.isEmpty(shop_bank_name)) {
                    binding.edtBankna.setError("Bank Name Required!!");
                }

                if (TextUtils.isEmpty(shop_ifsc)) {
                    binding.edtBankifsc.setError("IFSC Number Required!!");
                }

                if (TextUtils.isEmpty(shop_acc_no)) {
                    binding.edtBankacc.setError("Account Number Required!!");
                }

                if (TextUtils.isEmpty(shop_gst)) {
                    binding.edtSgst.setError("GST Number Required!!");
                }

                if (TextUtils.isEmpty(shop_pan)) {
                    binding.edtSpan.setError("PAN Number Required!!");
                }

                if (TextUtils.isEmpty(shop_lic_no)) {
                    binding.edtLic.setError("License Number Required!!");
                }

                if (TextUtils.isEmpty(shop_sqft)) {
                    binding.edtSsqft.setError("Shop Area Required!!");
                }

            }

            doShopRegister(String.valueOf(Config.user_id) ,shop_name, shop_add, shop_pincode, shop_email, shop_gst, shop_pan, shop_bank_name, shop_ifsc,
                    shop_acc_no, shop_sqft, shop_lic_no, shop_timing);

        });


    }



    /*--------------------------------- Handle Toolbar --------------------------------*/

    private void handleToolbar() {

        binding.includedToolbar.title.setText("Add Shop Information");
        binding.includedToolbar.backBtn.setOnClickListener(v -> finish());
    }




    private void doShopRegister( String id , String shop_name, String shop_add, String shop_pincode, String shop_email,
                                String shop_gst, String shop_pan, String shop_bank_name, String shop_ifsc,
                                String shop_acc_no, String shop_sqft, String shop_lic_no, String shop_timing)
    {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.ShopRegister(id, shop_name, shop_add, shop_pincode, shop_email, shop_gst, shop_pan, shop_bank_name, shop_ifsc, shop_acc_no, shop_sqft, shop_lic_no, shop_timing);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                Config.showToast(context, response.body().getMessage());
                Intent intent = new Intent(ShopInfoActivity.this, DashboardActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });

    }


}

