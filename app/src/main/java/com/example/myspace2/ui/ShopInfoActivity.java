package com.example.myspace2.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myspace2.Network.Api;
import com.example.myspace2.Network.AppConfig;
import com.example.myspace2.databinding.ActivityShopInfoBinding;
import com.example.myspace2.model.ServerResponse;
import com.example.myspace2.utils.Config;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopInfoActivity extends AppCompatActivity {
    private ActivityShopInfoBinding binding;
    private Context context = this;

    private String shname, shadd, shcategory, sharea, shcity, shstate, shpin, shmail, shgst, shpan, shbankname, shbranch;
    private String shaccn, shlic, shsqft, shtiming;

    private String start, mstart, sampm, end, mend, eampm;

    private static final String TAG = "ShopInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShopInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();

    }


    private void init()
    {
        binding.shReg.setOnClickListener(view -> {
            shname = binding.edtSname.getText().toString().trim();
            shcategory = binding.spinCat.getSelectedItem().toString().trim();
            shadd = binding.edtAdd.getText().toString().trim();
            sharea = binding.edtArea.getText().toString().trim();
            shcity = binding.edtCity.getText().toString().trim();
            shstate = binding.edtState.getText().toString().trim();
            shmail = binding.edtMail.getText().toString().trim();
            shpin =binding.edtPincode.getText().toString().trim();

            shbankname = binding.edtBankna.getText().toString().trim();
            shbranch = binding.edtBankbranch.getText().toString().trim();
            shaccn = binding.edtBankacc.getText().toString().trim();

            shgst = binding.edtSgst.getText().toString().trim();
            shpan = binding.edtSpan.getText().toString().trim();
            shsqft = binding.edtSsqft.getText().toString().trim();
            shlic = binding.edtLic.getText().toString().trim();

            start = binding.spinStart.getSelectedItem().toString().trim();
            mstart = binding.spinStartMin.getSelectedItem().toString().trim();
            sampm = binding.spinStartAmPm.getSelectedItem().toString().trim();

            end = binding.spinEnd.getSelectedItem().toString().trim();
            mend = binding.spinEndMin.getSelectedItem().toString().trim();
            eampm = binding.spinEndAmPm.getSelectedItem().toString().trim();

            shtiming = start+":"+mstart+sampm+"-"+end+":"+mend+eampm;

            Log.d(TAG, "init: " + shname + "----" + start + "----" + mstart + "----" + sampm + "----" + end + "----" + mend + "----" + eampm);

            if (TextUtils.isEmpty(shname) || TextUtils.isEmpty(shcategory) || TextUtils.isEmpty(shadd) || TextUtils.isEmpty(shcity) || TextUtils.isEmpty(shstate) || TextUtils.isEmpty(shmail) || TextUtils.isEmpty(shpin)
                    || TextUtils.isEmpty(shbankname) || TextUtils.isEmpty(shbranch) || TextUtils.isEmpty(shaccn)
                    || TextUtils.isEmpty(shgst) || TextUtils.isEmpty(shpan) || TextUtils.isEmpty(shsqft)
                    || TextUtils.isEmpty(shlic) || TextUtils.isEmpty(sharea))
            {
                if (TextUtils.isEmpty(shname)) {
                    binding.edtSname.setError("Shop Name Required!!");
                }

                if (TextUtils.isEmpty(shadd)) {
                    binding.edtAdd.setError("Address Required!!");
                }

                if (TextUtils.isEmpty(sharea)) {
                    binding.edtArea.setError("Area Required!!");
                }

                if (TextUtils.isEmpty(shcity)) {
                    binding.edtCity.setError("City Required!!");
                }

                if (TextUtils.isEmpty(shstate)) {
                    binding.edtState.setError("State Required!!");
                }

                if (TextUtils.isEmpty(shmail)) {
                    binding.edtMail.setError("Email Required!!");
                }

                if (TextUtils.isEmpty(shpin)) {
                    binding.edtPincode.setError("Pincode Required!!");
                }

                if (TextUtils.isEmpty(shbankname)) {
                    binding.edtBankna.setError("Bank Name Required!!");
                }

                if (TextUtils.isEmpty(shbranch)) {
                    binding.edtBankbranch.setError("Branch Name Required!!");
                }

                if (TextUtils.isEmpty(shaccn)) {
                    binding.edtBankacc.setError("Account Number Required!!");
                }

                if (TextUtils.isEmpty(shgst)) {
                    binding.edtSgst.setError("GST Number Required!!");
                }

                if (TextUtils.isEmpty(shpan)) {
                    binding.edtSpan.setError("PAN Number Required!!");
                }

                if (TextUtils.isEmpty(shlic)) {
                    binding.edtLic.setError("License Number Required!!");
                }

                if (TextUtils.isEmpty(shsqft)) {
                    binding.edtSsqft.setError("Shop Area Required!!");
                }

            }

            doShopRegister(shname, shadd, shcategory, sharea, shcity, shstate, shpin, shmail, shgst, shpan, shbankname, shbranch, shaccn, shsqft, shlic, shtiming);

        });


    }


    private void doShopRegister(String shname, String shadd, String shcategory, String sharea, String shcity, String shstate, String shpin,
                                String shmail, String shgst, String shpan, String shbankname, String shbranch, String shaccn, String shsqft,
                                String shlic, String shtiming)
    {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.ShopRegister(shname, shadd, shcategory, sharea, shcity, shstate, shpin, shmail, shgst, shpan, shbankname, shbranch, shaccn, shsqft, shlic, shtiming);
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


}

