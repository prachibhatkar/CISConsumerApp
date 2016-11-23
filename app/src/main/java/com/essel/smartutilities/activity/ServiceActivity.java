package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.CommonUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.essel.smartutilities.activity.FeedBackActivity.flag;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Context Context;
    private Button btn_submit_service;
    private EditText edit_remark;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    String editremark;
    static Boolean flag=false;
    HashMap<String, List<String>> listDataChild;

    Spinner servicetype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        btn_submit_service = (Button) findViewById(R.id.btn_submit_service);
        btn_submit_service.setOnClickListener(this);

        edit_remark = (EditText) findViewById(R.id.edit_remark);
      //  editremark = edit_remark.toString().trim();


        servicetype = (Spinner) findViewById(R.id.sp_sevicetype);
        String[] type = this.getResources().getStringArray(R.array.service);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Arrays.asList(type));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        servicetype.setAdapter(dataAdapter);

        // expListView = (ExpandableListView)findViewById(R.id.expListView);

        return;
    }

    @Override
    public void onClick(View v) {
        if(v==btn_submit_service){
           if (isBlankInput()) {

                Intent i = new Intent(this, ServiceStatusActivity.class);
                startActivity(i);
            //   flag=true;
           //  Intent i = new Intent(this, ActivityLoginLanding.class);
          //  startActivity(i);



            }
        }
    }

    public static Boolean getflag(){

        return flag;


    }


   /* public boolean validate() {
        Boolean flag = false;

        String editremark = edit_remark.getText().toString().trim();
        if (!editremark.isEmpty()) {
            if (servicetype.getSelectedItemPosition()== 0) {
                Toast.makeText(this, "Select service type", Toast.LENGTH_LONG).show();
                            flag = true;
                        }

                    } else
                        Toast.makeText(this, "Enter Remark", Toast.LENGTH_LONG).show();



        return flag;

    }*/

    private boolean isBlankInput() {
        boolean b = true;
        String editremark = String.valueOf(edit_remark.getText());
        if (editremark.equals("")){
            Toast.makeText(this, "Enter Remark", Toast.LENGTH_LONG).show();

            b = false;
        }


        if (servicetype.getSelectedItemPosition()== 0) {
            Toast.makeText(this, "Select service type", Toast.LENGTH_LONG).show();
            b = false;
        }
        return b;

    }




    public void onBackPressed() {

        Intent in =new Intent(this,ActivityLoginLanding.class);
        startActivity(in);


    }
    }
