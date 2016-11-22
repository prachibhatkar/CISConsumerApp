package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

    /*private void showSuccess(){

        dialogSucccess = new Dialog(this, R.style.verify_dialog);
        dialogSucccess.setContentView(R.layout.dialog_success);
        dialogSucccess.setCancelable(true);
        dialogSucccess.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogSucccess.show();
    }*/

    @Override
    public void onClick(View v) {
        if(v==actionSubmit) {
            if (consumerIdEditText.equals("") || consumerIdEditText.length() < 10 || consumerIdEditText.length() > 20) {

                Toast.makeText(this.getApplicationContext(), "Enter correct consumer id", Toast.LENGTH_SHORT).show();


            } else {
                Intent in = new Intent(this, ForgotActivity2.class);
                startActivity(in);
                //showSuccess();
            }
        }
        if(v==actionok){
            dialogSucccess.hide();
        }
    }

    public void onBackPressed() {

        Intent in =new Intent(this,ActivityLoginLanding.class);
        startActivity(in);


    }
}
