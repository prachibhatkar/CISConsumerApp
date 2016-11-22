package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;

import java.util.Arrays;

/**
 * Created by hp on 11/4/2016.
 */

public class AddAccountActivity3 extends AppCompatActivity implements View.OnClickListener {

    Button btnNo, btnyes;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account3);
        btnNo = (Button) findViewById(R.id.btn_no);
        btnyes = (Button) findViewById(R.id.btn_yes);
        btnyes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_no:
                Intent i = new Intent(this, AddAccountActivity.class);
                startActivity(i);
                break;
            case R.id.btn_yes:
                Intent in = new Intent(this, AddAccountActivity4.class);
                startActivity(in);
                break;
        }


    }

}