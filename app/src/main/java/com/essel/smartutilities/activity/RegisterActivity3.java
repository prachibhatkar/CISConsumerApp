package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.SharedPrefManager;

public class RegisterActivity3 extends BaseActivity implements View.OnClickListener {

    EditText editTextEmailId, editTextMobileNo, editTextPassword, editTextRetypePassword, editTextOTPCode;
    TextInputLayout inputLayoutEmailId, inputLayoutMobileNo, inputLayoutPassword, inputLayoutRetypePassword;

    AppCompatButton buttonRegister, buttonVerify;
    TextView textViewConsumerName, textViewConsumerAddress, maintitle, textViewConsumerConnectionType, textViewConsumerMobileNo, textViewActionResend;
    LinearLayout linearActionCancel;
    Context mContext;
    Dialog dialogVerify, dialogSucccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        maintitle = (TextView) findViewById(R.id.title_bar);
        if(!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO).isEmpty())
            maintitle.setText("Register : "+SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initialize();
    }

    private void initialize() {
        editTextEmailId = (EditText) findViewById(R.id.editEmailId);
        editTextMobileNo = (EditText) findViewById(R.id.editMobileNo);
        editTextPassword = (EditText) findViewById(R.id.editPassword);
        editTextRetypePassword = (EditText) findViewById(R.id.editRetypePassword);

        inputLayoutEmailId = (TextInputLayout) findViewById(R.id.inputLayoutEmailId);
        inputLayoutMobileNo = (TextInputLayout) findViewById(R.id.inputLayoutMobileNumber);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        inputLayoutRetypePassword = (TextInputLayout) findViewById(R.id.inputLayoutRetypePassword);

        textViewConsumerName = (TextView) findViewById(R.id.textConsumerName);
        textViewConsumerAddress = (TextView) findViewById(R.id.textConsumerAddress);
        textViewConsumerConnectionType = (TextView) findViewById(R.id.textConsumerConnectionType);
        textViewConsumerMobileNo = (TextView) findViewById(R.id.textConsumerMobileNo);

        buttonRegister = (AppCompatButton) findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            if (validate()) {
                Intent i = new Intent(this, RegisterActivity4.class);
                startActivity(i);
            }
        }

    }

    private boolean validate() {
        Boolean flag = false;
        if (editTextPassword.getText().toString().trim().length() >= 6 && editTextPassword.getText().toString().trim().length() <= 20) {
            if (editTextRetypePassword.getText().toString().trim().length() >= 6 && editTextRetypePassword.getText().toString().trim().length() <= 20) {
                if (editTextRetypePassword.getText().toString().trim().compareTo(editTextPassword.getText().toString().trim()) == 0) {
                    flag = true;
                } else
                    Toast.makeText(this, "Password Does not Match", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Retype valid Password", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Enter valid Password", Toast.LENGTH_SHORT).show();
        return flag;
    }

    private void showVerifyDialog() {
        dialogVerify = new Dialog(this, R.style.verify_dialog);
        dialogVerify.setContentView(R.layout.dialog_verify_number);
        dialogVerify.setCancelable(true);
        dialogVerify.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        editTextOTPCode = (EditText) dialogVerify.findViewById(R.id.edit_otp);

        textViewActionResend = (TextView) dialogVerify.findViewById(R.id.action_resend);
        linearActionCancel = (LinearLayout) dialogVerify.findViewById(R.id.action_cancel);
        buttonVerify = (AppCompatButton) dialogVerify.findViewById(R.id.btn_verify);

        textViewActionResend.setOnClickListener(this);
        linearActionCancel.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        buttonVerify.setOnClickListener(this);
        dialogVerify.show();

    }

    private void showSuccess() {
        dialogVerify.hide();
        dialogSucccess = new Dialog(this, R.style.verify_dialog);
        dialogSucccess.setContentView(R.layout.dialog_registration_success);
        dialogSucccess.setCancelable(true);
        dialogSucccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSucccess.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
