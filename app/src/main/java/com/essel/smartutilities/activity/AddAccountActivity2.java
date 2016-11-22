package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.SharedPrefManager;

public class AddAccountActivity2 extends BaseActivity implements View.OnClickListener {

    Toolbar toolbar;
    AppCompatButton buttonRegister, buttonVerify;
    EditText otp;
    TextView maintitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account2);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        maintitle = (TextView) findViewById(R.id.title_bar);
        maintitle.setText("Add Account");
        initialize();
    }

    private void initialize() {
        buttonVerify = (AppCompatButton) findViewById(R.id.btn_verify);
        buttonVerify.setOnClickListener(this);
        otp = (EditText) findViewById(R.id.edit_otp);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }



    @Override
    public void onClick(View v) {
        if (v == buttonVerify) {
            if (otp.getText().toString().trim().length() == 4) {
                Intent i = new Intent(this, AddAccountActivity3.class);
                startActivity(i);
            } else
                Toast.makeText(this, "Enter valid OTP", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
