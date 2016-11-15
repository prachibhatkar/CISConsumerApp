package com.essel.smartutilities.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.TextView;

import com.essel.smartutilities.R;

public class GetComplaintIdActivity extends AppCompatActivity {
TextView tv_complaintid,tv_complaintmsg;
    Button btn_submitcomplaintid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_complaint_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.complaint);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);


        tv_complaintid=(TextView)findViewById(R.id.tv_complaintid);
        tv_complaintmsg=(TextView)findViewById(R.id.tv_complaintmsg);

        btn_submitcomplaintid=(Button)findViewById(R.id.btn_submitcomplaintid);

    }
}
