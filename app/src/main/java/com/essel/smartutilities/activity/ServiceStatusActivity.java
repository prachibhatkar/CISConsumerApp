package com.essel.smartutilities.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.ContactUsAdapter;

public class ServiceStatusActivity extends AppCompatActivity implements View.OnClickListener{
      TextView tv_message,tv_service_id,tv_time_csd_center;
      Button btn_service_status_continue,btn_locate_us;
    private ContactUsAdapter contactUsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_status);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tv_message=(TextView)findViewById(R.id.tv_message);
        tv_service_id=(TextView)findViewById(R.id.tv_service_id);
        tv_time_csd_center=(TextView)findViewById(R.id.tv_time_csd_center);
        btn_service_status_continue=(Button)findViewById(R.id.btn_service_status_continue);
        btn_locate_us=(Button)findViewById(R.id.btn_locate_us);

        btn_service_status_continue.setOnClickListener(this);
        btn_locate_us.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==btn_service_status_continue){

            Intent in = new Intent(this,ActivityLoginLanding.class);
            startActivity(in);


        }
        else if(v==btn_locate_us){

            Intent in = new Intent(this,Contact_Us_Activity.class);
            startActivity(in);


        }

    }


    public void onBackPressed() {

        Intent in =new Intent(this,ActivityLoginLanding.class);
        startActivity(in);


    }




}
