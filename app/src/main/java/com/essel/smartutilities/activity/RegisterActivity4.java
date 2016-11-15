package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essel.smartutilities.R;

public class RegisterActivity4 extends BaseActivity implements View.OnClickListener {

    EditText editTextEmailId, editTextMobileNo, editTextPassword, editTextRetypePassword, editTextOTPCode;
    TextInputLayout inputLayoutEmailId, inputLayoutMobileNo, inputLayoutPassword, inputLayoutRetypePassword;
    AppCompatButton buttonRegister, buttonVerify;
    TextView textViewConsumerName, textViewConsumerAddress, textViewConsumerConnectionType, textViewConsumerMobileNo, textViewActionResend;
    LinearLayout linearActionCancel;
    Context mContext;
    Dialog dialogVerify, dialogSucccess;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);
        mContext = this;
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Register:9595903117");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
    }

    private void initialize() {
        Button con = (Button) findViewById(R.id.btn_continue);
        Button add = (Button) findViewById(R.id.btn_addmore);
        inputLayoutEmailId = (TextInputLayout) findViewById(R.id.inputLayoutEmailId);
        inputLayoutMobileNo = (TextInputLayout) findViewById(R.id.inputLayoutMobileNumber);
        textViewConsumerName = (TextView) findViewById(R.id.textConsumerName);
        textViewConsumerAddress = (TextView) findViewById(R.id.textConsumerAddress);
        textViewConsumerConnectionType = (TextView) findViewById(R.id.textConsumerConnectionType);
        textViewConsumerMobileNo = (TextView) findViewById(R.id.textConsumerMobileNo);
        add.setOnClickListener(this);
        con.setOnClickListener(this);
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
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_addmore) {
            i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        } else if (v.getId() == R.id.btn_continue) {
            i = new Intent(this, LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
