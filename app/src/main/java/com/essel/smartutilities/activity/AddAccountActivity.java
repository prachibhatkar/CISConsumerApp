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

public class AddAccountActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_city;
    Button btnNext;
    EditText editTextConsumerId;
    TextInputLayout inputLayoutConsumerId;
    Context mContext;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        mContext = this;
        tv_city = (TextView) findViewById(R.id.tv_city);
        btnNext = (Button) findViewById(R.id.BTNNext);
        editTextConsumerId = (EditText) findViewById(R.id.consumerno);
        inputLayoutConsumerId = (TextInputLayout) findViewById(R.id.inputLayoutConsumerId);
        btnNext.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_city.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_CITY));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTNNext:
                validate();
                break;

        }


    }

    public void validate() {

        if (editTextConsumerId.getText().toString().trim().length() >= 10 &&
                editTextConsumerId.getText().toString().trim().length() <= 20) {
            Intent i = new Intent(mContext, AddAccountActivity2.class);
            startActivity(i);
        } else
            Toast.makeText(this, "Enter valid Consumer No.", Toast.LENGTH_SHORT).show();

    }


}