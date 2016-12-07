package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.SharedPrefManager;

/**
 * Created by hp on 11/5/2016.
 */

public class QuickPayActivity extends BaseActivity implements View.OnClickListener {

    private EditText consumerno;
    private TextInputLayout inputLayoutconsumerno;
    private Button Submit;
    private TextView tv_city;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_pay);
        initialize();
    }

    private void initialize() {
        consumerno = (EditText) findViewById(R.id.Consumerno);
        tv_city = (TextView) findViewById(R.id.tv_city);
        Submit = (Button) findViewById(R.id.BTNSubmit);
        Submit.setOnClickListener(this);
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
            case R.id.BTNSubmit:
                if (consumerno.getText().toString().trim().length() >= 10 &&
                        consumerno.getText().toString().trim().length() <= 20) {
                    Intent i = new Intent(this, PayNowActivity.class);
                    startActivity(i);
                } else
                    Toast.makeText(this, "Enter valid Consumer No.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}


