package com.bynry.cisconsumerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynry.cisconsumerapp.R;

public class GetComplaintIdActivity extends AppCompatActivity implements View.OnClickListener
{
TextView tv_complaintid,tv_complaintmsg;
    Button btn_submitcomplaintid;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_complaint_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         imgBack = (ImageView) findViewById(R.id.img_back);
         imgBack.setOnClickListener(this);




        tv_complaintid=(TextView)findViewById(R.id.tv_complaintid);
        tv_complaintmsg=(TextView)findViewById(R.id.tv_complaintmsg);

        Intent intent = getIntent();
        String caseid = intent.getExtras().getString("caseid");
        String message=intent.getExtras().getString("message");
       String msg="Please select Correct values";

        if(msg.equals(message))
        {
            tv_complaintid.setText("");
            tv_complaintmsg.setText(message);
        }
        else
        {
            tv_complaintid.setText(caseid);
            tv_complaintmsg.setText(message);
        }
        btn_submitcomplaintid=(Button)findViewById(R.id.btn_submitcomplaintid);
        btn_submitcomplaintid.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        if(v==btn_submitcomplaintid)
        {

            Intent in = new Intent(this,ComplaintActivity.class);
            startActivity(in);


        }
        if(v==imgBack)
        {

            Intent in = new Intent(this,ActivityLoginLanding.class);
            startActivity(in);


        }

    }

}
