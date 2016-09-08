package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.essel.smartutilities.R;

public class ForgotActivity extends BaseActivity implements View.OnClickListener {

    EditText consumerIdEditText;
    Dialog dialogSucccess;
    AppCompatButton actionSubmit;
    Button actionok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        initialize();

    }

    private void initialize(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Forgot Password");

        consumerIdEditText = (EditText)findViewById(R.id.edit_consumer_id);
        actionSubmit = (AppCompatButton)findViewById(R.id.BTNSubmit);
        actionSubmit.setOnClickListener(this);

    }

    private void showSuccess(){

        dialogSucccess = new Dialog(this, R.style.verify_dialog);
        dialogSucccess.setContentView(R.layout.dialog_success);
        dialogSucccess.setCancelable(true);
        dialogSucccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSucccess.show();
    }

    @Override
    public void onClick(View v) {
        if(v==actionSubmit) {
            showSuccess();
        }
        else if(v==actionok){
            dialogSucccess.hide();
        }
    }
}
