package com.essel.smartutilities.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;

public class GetComplaintIdActivity extends AppCompatActivity implements View.OnClickListener {
TextView tv_complaintid,tv_complaintmsg;
    Button btn_submitcomplaintid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_complaint_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        tv_complaintid=(TextView)findViewById(R.id.tv_complaintid);
        tv_complaintmsg=(TextView)findViewById(R.id.tv_complaintmsg);

        btn_submitcomplaintid=(Button)findViewById(R.id.btn_submitcomplaintid);
        btn_submitcomplaintid.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v==btn_submitcomplaintid){

            Intent in = new Intent(this,ComplaintActivity.class);
            startActivity(in);


        }

    }

    public void onBackPressed() {

        Intent in =new Intent(this,ComplaintActivity.class);
        startActivity(in);


    }
}
