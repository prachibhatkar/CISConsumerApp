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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.essel.smartutilities.activity.FeedBackActivity.flag;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener,ServiceCaller {
    private Context Context;
    private Button btn_submit_service;
    private EditText edit_remark;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    List<String> services;
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


        if( CommonUtils.isNetworkAvaliable(this)) {
            JsonObjectRequest request = WebRequests.getServiceType(this, Request.Method.GET, AppConstants.URL_GET_SERVICE_TYPE, AppConstants.REQUEST_SERVICE_TYPE,
                    this, "Token c686681877b60f7189965137e2d57857c0a07099");
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_SERVICE_TYPE);
        }else
            Toast.makeText(this.getApplicationContext(), " Please Connection Internet ", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {


        switch (label) {
            case AppConstants.REQUEST_SERVICE_TYPE: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
//
                        Log.i(label, "hygt " + jsonResponse);
                        Log.i(label, "hyif " + jsonResponse.type);
                        if(jsonResponse.type.size()!=0)
                            Log.i(label, "servicetype" +jsonResponse.type );
                        {
                     //       services.add(jsonResponse.type.get(0).type.toString().trim());



                        }
                        if (jsonResponse.authorization != null) {
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                    }
                    break;
                }
            }

        }

    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {

        switch (label) {
            case AppConstants.REQUEST_FAQ: {
//
               Log.i(label, "servicetype" + message);
               Log.i(label, "servicetype" + response);
            }
            break;
        }


    }
}
