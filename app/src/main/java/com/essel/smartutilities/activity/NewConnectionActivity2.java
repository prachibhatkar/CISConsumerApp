package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.essel.smartutilities.R;

/**
 * Created by hp on 11/5/2016.
 */

public class NewConnectionActivity2 extends BaseActivity implements View.OnClickListener {

    Intent i;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_connection2);
        Button login = (Button) findViewById(R.id.btn_gotologin);
        Button guest = (Button) findViewById(R.id.btn_continuasguest);
        login.setOnClickListener(this);
        guest.setOnClickListener(this);
//        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gotologin:
                i = new Intent(this, LoginActivity.class);
                startActivity(i);
                break;
            case R.id.btn_continuasguest:
                i = new Intent(this, LandingSkipLoginActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }
}