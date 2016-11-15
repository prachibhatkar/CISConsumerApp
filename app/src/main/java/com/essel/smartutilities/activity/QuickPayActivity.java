package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.essel.smartutilities.R;

/**
 * Created by hp on 11/5/2016.
 */

public class QuickPayActivity extends BaseActivity implements View.OnClickListener {

    private EditText consumerno;
    private TextInputLayout inputLayoutconsumerno;
    private Button Submit;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_pay);
        initialize();
    }

    private void initialize() {
        consumerno = (EditText) findViewById(R.id.consumer_id);
        Submit = (Button) findViewById(R.id.BTNSubmit);
        Submit.setOnClickListener(this);
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
            case R.id.BTNSubmit:
                Intent in = new Intent(this, PayNowActivity.class);
                startActivity(in);
                break;
        }
    }
}


