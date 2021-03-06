package com.bynry.cisconsumerapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynry.cisconsumerapp.R;

public class ForgotSuccessActivity extends AppCompatActivity implements View.OnClickListener
{
    Button btn_goto_login;
    TextView linkmsg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_success);
        btn_goto_login=(Button)findViewById(R.id.btn_goto_login);
        btn_goto_login.setOnClickListener(this);

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
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
         linkmsg=(TextView)findViewById(R.id.tv_linkmsg);
        Intent intent = getIntent();
        String msg = intent.getExtras().getString("msg");
        linkmsg.setText(msg);


    }

    @Override
    public void onClick(View v)
    {
        if(v==btn_goto_login)
        {Intent in = new Intent(this,LoginActivity.class);
            startActivity(in);
        }

    }

    public void onBackPressed()
    {

        Intent in =new Intent(this,LoginActivity.class);
        startActivity(in);


    }
}
