package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button btn_submit_service;
    private EditText edit_remark;
   ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Service Request");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        btn_submit_service = (Button) findViewById(R.id.btn_submit_service);
        btn_submit_service.setOnClickListener(this);

      // expListView = (ExpandableListView)findViewById(R.id.expListView);

        return;
    }




        @Override
        public void onClick (View v){
            if (v == btn_submit_service) {
                Intent in = new Intent(this, ServiceStatusActivity.class);
                startActivity(in);

            }

        }
    }
