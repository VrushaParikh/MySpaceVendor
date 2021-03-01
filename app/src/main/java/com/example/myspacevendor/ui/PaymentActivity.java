package com.example.myspacevendor.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myspacevendor.R;
import com.example.myspacevendor.databinding.ActivityPaymentBinding;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    private ActivityPaymentBinding binding;
    private final Context context = this;
    private static final String TAG = "PaymentActivity";
    Button btPay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
            doActivity(amount);
        });
    }

    private void doActivity(int amount) {
        //Initialize RazorPay Checkout
        Checkout checkout=new Checkout();

        //set key id
        checkout.setKeyID("rzp_test_Cf3sVOF5OHtsqX");

        //set image
        checkout.setImage(R.drawable.razorpay);

        //initialize json object

        JSONObject object = new JSONObject();

        try {
            //put name
            object.put("name","My Space");
            //put description
            object.put("description","Test Payment");
            //put theme color
            object.put("theme.color","#0093DD");
            //put currency unit
            object.put("currency","INR");
            //put amount
            object.put("amount",amount);
            //put mobile number
            object.put("prefill.contact","9824880973");
            //put email
            object.put("prefill.email","vrushaparikh18@gmail.com");
            //open razor pay checkout activity
            checkout.open(PaymentActivity.this,object);


        } catch (JSONException e) {
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
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();

    }



}

