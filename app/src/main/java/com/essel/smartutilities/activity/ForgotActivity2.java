package com.essel.smartutilities.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.essel.smartutilities.R;

public class ForgotActivity2 extends AppCompatActivity implements View.OnClickListener{
    Button btn_goto_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot2);
        btn_goto_login=(Button)findViewById(R.id.btn_goto_login);
        btn_goto_login.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

    }

    @Override
    public void onClick(View v) {
        if(v==btn_goto_login){

            Intent in = new Intent(this,LoginActivity.class);
            startActivity(in);
        }

    }

    public void onBackPressed() {

        Intent in =new Intent(this,LoginActivity.class);
        startActivity(in);


    }
}
