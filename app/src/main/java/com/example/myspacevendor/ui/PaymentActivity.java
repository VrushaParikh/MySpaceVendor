package com.example.myspacevendor.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.R;
import com.example.myspacevendor.data.user.User;
import com.example.myspacevendor.databinding.ActivityPaymentBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.utils.Config;
import com.example.myspacevendor.utils.SharedPrefManager;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.example.myspacevendor.ui.LoginActivity.u1;
import static com.example.myspacevendor.ui.LoginActivity.vendor_email;
import static com.example.myspacevendor.ui.LoginActivity.vendor_pwd;
import static java.lang.Integer.parseInt;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private ActivityPaymentBinding binding;
    private final Context context = this;
    private SharedPrefManager sharedPrefManager;
    public static User u1;
    private static final String TAG = "PaymentActivity";

    Button btPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        handleToolbar();
        init();
        clickListener();

    }
    private void init() {
        btPay=binding.btPay;


    }

    private void clickListener() {
        binding.btPay.setOnClickListener(view -> {
            String sAmount = "5000";
            int amount = Math.round(Float.parseFloat(sAmount) * 100);
            doActivity(amount,u1);
        });
    }


    /*--------------------------------- Handle Toolbar --------------------------------*/

    private void handleToolbar() {

        binding.includedToolbar.title.setText("Make Payment");
        binding.includedToolbar.backBtn.setOnClickListener(v -> finish());
    }

    private void doActivity(int amount, User u1) {


                //Initialize RazorPay Checkout
                Checkout checkout=new Checkout();

                //set key id
                checkout.setKeyID("rzp_test_Cf3sVOF5OHtsqX");

                //set image
                checkout.setImage(R.drawable.razorpay);

                //initialize json object

                JSONObject object = new JSONObject();



                try {
//                    sharedPrefManager = new SharedPrefManager(context);
//
//                    String phn=sharedPrefManager.getString(u1.getVendorMobile());
//
//                    Log.d(TAG, "onFailure: " +phn);

                    //put name
                    object.put("name","My Space");
                    //put description
                    object.put("description","Shop Payment");
                    //put theme color
                    object.put("theme.color","#0093DD");
                    //put currency unit
                    object.put("currency","INR");
                    //put amount
                    object.put("amount",amount);
                    //put mobile number
//                    object.put("prefill.contact", "9824552365");
//                    //put email
//                    object.put("prefill.email","u1.getVendorEmail()");

                    Log.d(TAG, "onFailure: Try Again!" );

                    //open razor pay checkout activity
                    checkout.open(PaymentActivity.this,object);


                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }




    @Override
    public void onPaymentSuccess(String s) {

        //Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Set title
        builder.setTitle("Payment ID");

        //Set message
        builder.setMessage(s);

        //Show alert dialog
        builder.show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        //Display Toast
        Config.showToast(context, "Payment not successful");
//        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

    }



}

