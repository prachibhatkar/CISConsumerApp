package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
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
import com.essel.smartutilities.models.ServiceType;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

import org.json.JSONException;
import org.json.JSONObject;

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
    private ArrayList<String> services;
    String editremark;
    String service_type,consumer_remark;
    static Boolean flag=false;
    ProgressDialog pDialog;
    HashMap<String, List<String>> listDataChild;
   // ServiceType service1=new ServiceType();

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


        ((EditText)findViewById(R.id.edit_remark)).setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(200)
        });

        services = new ArrayList<>(12);
        services.add(0, "Select Service Type");

        // expListView = (ExpandableListView)findViewById(R.id.expListView);


       /* initProgressDialog();
        if (pDialog != null && !pDialog.isShowing()) {
            pDialog.setMessage(" please wait..");
            pDialog.show();*/

            if (CommonUtils.isNetworkAvaliable(this)) {

                JsonObjectRequest request = WebRequests.getServiceType(this, Request.Method.GET, AppConstants.URL_GET_SERVICE_TYPE, AppConstants.REQUEST_SERVICE_TYPE,
                        this, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
                App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_SERVICE_TYPE);
            } else
                Toast.makeText(this.getApplicationContext(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();


            servicetype = (Spinner) findViewById(R.id.sp_sevicetype);
            // String[] type = this.getResources().getStringArray(R.array.service);
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, services);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            servicetype.setAdapter(dataAdapter);


        }


    private void initProgressDialog() {

        if (pDialog == null) {
            pDialog = new ProgressDialog(this);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }




        @Override
    public void onClick(View v) {
        if(v==btn_submit_service){
          if (isBlankInput()) {

              editremark = edit_remark.getText().toString().trim();
              JSONObject obj = new JSONObject();
              try {
                  obj.put("service_type", servicetype.getSelectedItemPosition());
                  obj.put("consumer_remark", editremark);
              } catch (JSONException e) {
                  e.printStackTrace();
              }

              if( CommonUtils.isNetworkAvaliable(this)) {

                  initProgressDialog();
                  if (pDialog != null && !pDialog.isShowing()) {
                      pDialog.setMessage(" please wait..");
                      pDialog.show();
                  }

                   JsonObjectRequest request = WebRequests.serviceRequest(this, Request.Method.POST, AppConstants.URL_POST_SERVICE_REQUEST, AppConstants.REQUEST_POST_SERVICE_REQUEST,
                           this,obj,SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
                   App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_POST_SERVICE_REQUEST);
               }else
                   Toast.makeText(this.getApplicationContext(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();





           }
        }
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

                        {

                            for(int i = 1 ; i <= jsonResponse.type.size(); i++) {

                             //   services.add(i, jsonResponse.type.get(i-1).type);
                                dismissDialog();
                               ServiceType service1=new ServiceType();

                               // services.set(0,jsonResponse.type.get(0).type.toString().trim());
                                Log.i(label, "servicetype" + jsonResponse.type);

                                service1.setType(jsonResponse.type.get(i-1).type);
                                service1.setId(jsonResponse.type.get(i-1).id.toString().trim());
                                services.add(i,service1.getType());
                                Log.i(label, "servicetype22" + jsonResponse.type);



                               // services.add(jsonResponse.type.get(0).type.toString().trim());

                            }


                        }
                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                    }
                    break;
                }
            }

            case AppConstants.REQUEST_POST_SERVICE_REQUEST: {
                if (jsonResponse != null) {
                    Log.i(label, "hyif " + jsonResponse);

                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        dismissDialog();

                        Log.i(label, "hyif " + jsonResponse.result);
                        Intent i = new Intent(this, ServiceStatusActivity.class);
                        startActivity(i);

                    }
                     if(jsonResponse.servicerequestmessage!= null) {
                         dismissDialog();


                    }
                        if (jsonResponse.authorization != null) {
                            dismissDialog();
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



    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {

        switch (label) {
            case AppConstants.REQUEST_SERVICE_TYPE: {
//
               Log.i(label, "servicetype" + message);
               Log.i(label, "servicetype" + response);
            }
            break;
        }

        switch (label) {
            case AppConstants.REQUEST_POST_SERVICE_REQUEST: {
//
                Log.i(label, "servicerequest" + message);
                Log.i(label, "servicerequest" + response);
            }
            break;
        }


    }
}
