package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener,ServiceCaller {

    EditText consumerIdEditText;
    Dialog dialogSucccess;
    AppCompatButton actionSubmit;
    Button actionok;
    ProgressDialog pDialog;
    String consno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        consumerIdEditText = (EditText) findViewById(R.id.edit_consumer_id);
        consno=consumerIdEditText.toString().trim();
      //  consumerno=String.valueOf( consumerIdEditText.getText());
        actionSubmit = (AppCompatButton) findViewById(R.id.BTNSubmit);
        actionSubmit.setOnClickListener(this);




    }


    @Override
    public void onClick(View v) {
        if (v == actionSubmit) {
            String consumerno =String.valueOf( consumerIdEditText.getText());
            if (consumerIdEditText.equals("") || consumerIdEditText.length() < 10 || consumerIdEditText.length() > 20) {
                Toast.makeText(this.getApplicationContext(), "Enter correct consumer id", Toast.LENGTH_SHORT).show();
            } else {
                initProgressDialog();
                if (pDialog != null && !pDialog.isShowing()) {
                    pDialog.setMessage(" please wait..");
                    pDialog.show();
                }

              // String consumerno=String.valueOf( consumerIdEditText.getText());
                JsonObjectRequest request = WebRequests.forgotpassword(this, Request.Method.POST, AppConstants.URL_POST_FORGOT_PASSWORD, AppConstants.REQUEST_FORGOT_PASSWORD, this, consumerno);
                App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_FORGOT_PASSWORD);


            }



        }


    if(v==actionok)

    {
        dialogSucccess.hide();
    }

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

    public void onBackPressed() {

        super.onBackPressed();


    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_FORGOT_PASSWORD: {
                if (jsonResponse != null) {

                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        dismissDialog();

                        Intent in = new Intent(this, ForgotActivity2.class);
                        startActivity(in);
                        Log.i(label, "hygt " + jsonResponse);

                        if(jsonResponse.forgotpasswordmessage!= null) {
                            dismissDialog();

                        }

                        if (jsonResponse.authorization != null) {
                            dismissDialog();

                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Log.i(label, "failllllllllll " + jsonResponse);
                        dismissDialog();


                    }

                    break;
                }
                dismissDialog();
            }

        }

    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {

        switch (label) {
            case AppConstants.REQUEST_FORGOT_PASSWORD: {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
                Log.i(label, "gjjkfhdkh " + message);
                 Log.i(label, "jhjkghfkh " + response);
                dismissDialog();
            }
            break;
        }


    }
}
