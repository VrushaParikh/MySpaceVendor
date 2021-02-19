package com.example.myspacevendor.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myspacevendor.R;

public class ScanTokenActivity extends AppCompatActivity {
    Button scan_btn;
    public static TextView scan_text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_token);

        scan_text=(TextView)findViewById(R.id.result);
        scan_btn=(Button)findViewById((R.id.scan));

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),SacnnerView.class));

            }
        });
    }
}
