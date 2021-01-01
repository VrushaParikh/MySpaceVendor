package com.example.myspacevendor.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.Network.Api;
import com.example.myspacevendor.Network.AppConfig;
import com.example.myspacevendor.R;

import com.example.myspacevendor.databinding.ActivityTermsPoliciesBinding;
import com.example.myspacevendor.model.ServerResponse;
import com.example.myspacevendor.model.TermsAndConditions;
import com.example.myspacevendor.utils.Config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TermsPoliciesActivity extends AppCompatActivity {

    private ActivityTermsPoliciesBinding binding;
    private Context context = this;
    private int i = 0;
    private int hint = 2;

    private String tp, desc;

    private static final String TAG = "TermsPoliciesActivity";

    private final List<TermsAndConditions> termsList = new ArrayList<>();


    private int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTermsPoliciesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        parentLayout = binding.relative;


        init();
        clickListeners();


    }


    /*---------------------------------------------- Init ---------------------------------------------------*/

    private void init() {

        /*binding.submitTp.setOnClickListener(view -> {
            tp = binding.tp1.getText().toString().trim();
            desc = binding.desc1.getText().toString().trim();

            if (TextUtils.isEmpty(tp) || TextUtils.isEmpty(desc)) {
                if (TextUtils.isEmpty(tp)) {
                    binding.tp1.setError("Title Required!!");
                }

                if (TextUtils.isEmpty(desc)) {
                    binding.desc1.setError("Description Required!!");
                }

                return;
            }

            doTermsPolicyRegister(tp, desc);

        });*/
    }


    /*---------------------------------------------- click Listeners ---------------------------------------------------*/

    private void clickListeners() {

        binding.fab.setOnClickListener(view -> {

            manageLinearContainer();

        });


        binding.submitTp.setOnClickListener(view -> {


            readDynamicIds();

        });
    }



    /*---------------------------------------------- Read Dynamic Ids---------------------------------------------------*/


    private void readDynamicIds() {


        int count = binding.termsContainer.getChildCount();
        Log.d(TAG, "Count is : " + count);


        for (int position = 0; position < count; position++) {

            View view = binding.termsContainer.getChildAt(position);

            EditText title = view.findViewWithTag("title" + position);
            EditText desc = view.findViewWithTag("desc" + position);


            String val = title.getText().toString();
            String val2 = desc.getText().toString();

            Log.d(TAG, "Title is : " + val + " -- --- -- Desc is : " + val2);

            termsList.add(new TermsAndConditions(val, val2));

        }


        for (TermsAndConditions termsAndConditions : termsList) {

            sendDataToServer(termsAndConditions.getTitle(), termsAndConditions.getDescription());

        }


    }




    /*---------------------------------------------- Send Data To Server ---------------------------------------------------*/


    private void sendDataToServer(String title, String description) {

        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.TermsPolicies(title, description);
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





    /*---------------------------------------------- Manage Linear Container---------------------------------------------------*/

    private void manageLinearContainer() {


        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        EditText edt = new EditText(context,null,R.style.MyEditText);
//        EditText edt = new EditText(context);
        edt.setLayoutParams(lparams);
        edt.setHint("Enter Title");
        edt.setTag("title" + position);
        edt.setPadding(8, 8, 8, 8);
        edt.setTextSize(14f);
        edt.setFocusableInTouchMode(true);
        edt.requestFocus();



        EditText edt2 = new EditText(context,null,R.style.MyEditText);
        edt2.setLayoutParams(lparams);
        edt2.setHint("Enter Description");
        edt2.setTag("desc" + position);
        edt2.setPadding(8, 8, 8, 8);
        edt2.setTextSize(14f);
        edt2.setFocusableInTouchMode(true);
        edt2.requestFocus();


        LinearLayout myLayout = new LinearLayout(context);
        myLayout.setLayoutParams(lparams);
        myLayout.setOrientation(LinearLayout.VERTICAL);
        myLayout.setPadding(8, 8, 8, 8);
        myLayout.addView(edt);
        myLayout.addView(edt2);
        binding.termsContainer.addView(myLayout);


/*        binding.termsContainer.addView(tv);


        binding.termsContainer.addView(edt);*/

        position++;
    }


    /*---------------------------------------------- Do Terms Policy Register ---------------------------------------------------*/


    private void doTermsPolicyRegister(String tp, String desc) {
        Retrofit retrofit = AppConfig.getRetrofit();
        Api service = retrofit.create(Api.class);

        Call<ServerResponse> call = service.TermsPolicies(tp, desc);
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



    /*---------------------------------------------- createEditTextView ---------------------------------------------------*/


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    protected void createEditTextView() {
        /*RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        EditText edittTxt = new EditText(this);
        int maxLength = 20;

        edittTxt.setHint("Title of a term/policy");
        edittTxt.setId(hint);
        hint++;

        params.addRule(RelativeLayout.BELOW, binding.desc1.getId());

//        if (i==0)
//        {
//            params.addRule(RelativeLayout.BELOW, R.id.desc1);
//            i++;
//        }
//        else
//        {
//
//            params.addRule(RelativeLayout.BELOW, R.id.desc1);
//        }

        edittTxt.setLayoutParams(params);

        edittTxt.setBackgroundColor(Color.WHITE);
        edittTxt.setHintTextColor(R.color.grey);
        edittTxt.setTextAppearance(R.style.TextAppearance_AppCompat_Body2);
        edittTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        edittTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        edittTxt.setBackgroundResource(androidx.appcompat.R.drawable.abc_edit_text_material);


        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        edittTxt.setFilters(fArray);

        binding.linear.addView(edittTxt);*/

    }

}