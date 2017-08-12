package com.bynry.cisconsumerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynry.cisconsumerapp.adapter.ContactUsAdapter;
import com.bynry.cisconsumerapp.R;

public class ServiceStatusActivity extends AppCompatActivity implements View.OnClickListener{
      TextView tv_message,tv_service_id,tv_time_csd_center;
      Button btn_service_status_continue,btn_locate_us;
      static Boolean flag=false;
    private ContactUsAdapter contactUsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_status);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        }); 


        tv_message=(TextView)findViewById(R.id.tv_message);
        tv_service_id=(TextView)findViewById(R.id.lbl_tv_service_id);
        tv_time_csd_center=(TextView)findViewById(R.id.tv_time_csd_center);
        btn_service_status_continue=(Button)findViewById(R.id.btn_service_status_continue);
        btn_locate_us=(Button)findViewById(R.id.btn_locate_us);
        Intent intent = getIntent();
         String msg = intent.getExtras().getString("msg");
         String id = intent.getExtras().getString("id");
         tv_message.setText(msg);
         tv_service_id.setText(id);
        btn_service_status_continue.setOnClickListener(this);
        btn_locate_us.setOnClickListener(this);





    }

    @Override
    public void onClick(View v)
    {
        if(v==btn_service_status_continue)
        {

            Intent in = new Intent(this,ActivityLoginLanding.class);
            startActivity(in);


        }
        else if(v==btn_locate_us)
        {

            flag=true;
//            Contact_Us_Activity.get();

             Intent in = new Intent(this,Contact_Us_Activity.class);
             in.putExtra("value",1);
             startActivity(in);
             finish();


        }

    }


    public void onBackPressed()
    {

        Intent in =new Intent(this,ActivityLoginLanding.class);
        startActivity(in);


    }




}
