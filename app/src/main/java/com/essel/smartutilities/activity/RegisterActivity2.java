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

public class RegisterActivity2 extends BaseActivity implements View.OnClickListener {

    Toolbar toolbar;
    AppCompatButton buttonRegister, buttonVerify;
    EditText otp;
    TextView maintitle, msg,resend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        maintitle = (TextView) findViewById(R.id.title_bar);
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO).isEmpty())
            maintitle.setText("Register : " + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
        initialize();
    }

    private void initialize() {
        buttonVerify = (AppCompatButton) findViewById(R.id.btn_verify);
        msg = (TextView) findViewById(R.id.msg);
        resend = (TextView) findViewById(R.id.action_resend);
        resend.setOnClickListener(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verify: {
                if (otp.getText().toString().trim().length() == 6) {
                    Intent i = new Intent(this, RegisterActivity3.class);
                    startActivity(i);
                } else
                    Toast.makeText(this, "Enter valid OTP", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.action_resend:
                msg.setText(R.string.title_verify_resend);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
