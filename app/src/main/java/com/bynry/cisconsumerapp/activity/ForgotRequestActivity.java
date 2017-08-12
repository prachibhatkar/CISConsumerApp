package com.bynry.cisconsumerapp.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.bynry.cisconsumerapp.utility.CommonUtils;
import com.bynry.cisconsumerapp.utility.DialogCreator;
import com.bynry.cisconsumerapp.webservice.WebRequests;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.callers.ServiceCaller;
import com.bynry.cisconsumerapp.models.JsonResponse;
import com.bynry.cisconsumerapp.utility.App;
import com.bynry.cisconsumerapp.utility.AppConstants;

public class ForgotRequestActivity extends AppCompatActivity implements View.OnClickListener, ServiceCaller
{

    EditText consumerIdEditText;
    Dialog dialogSucccess;
    AppCompatButton actionSubmit;
    Button actionok;
    ProgressDialog pDialog;
    String consno;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_request);

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
        consno = consumerIdEditText.toString().trim();
        actionSubmit = (AppCompatButton) findViewById(R.id.BTNSubmit);
        actionSubmit.setOnClickListener(this);
    }


    @Override
    public void onClick(View v)
    {
        if (v == actionSubmit)
        {
            String consumerno = String.valueOf(consumerIdEditText.getText());
            if (consumerIdEditText.equals("") || consumerIdEditText.length() < 10 || consumerIdEditText.length() > 20) {
                Toast.makeText(this.getApplicationContext(), "Enter correct consumer id", Toast.LENGTH_SHORT).show();
            } else
            {

                if (CommonUtils.isNetworkAvaliable(this))
                {
                    initProgressDialog();
                    if (pDialog != null && !pDialog.isShowing())
                    {
                        pDialog.setMessage(" please wait..");
                        pDialog.show();
                    }
                    JsonObjectRequest request = WebRequests.forgotpassword(this, Request.Method.POST, AppConstants.URL_POST_FORGOT_PASSWORD, AppConstants.REQUEST_FORGOT_PASSWORD, this, consumerno);
                    App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_FORGOT_PASSWORD);
                } else
                {

                    Toast.makeText(this, " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
                }
            }
        }


        if (v == actionok)

        {
            dialogSucccess.hide();
        }
    }

    private void initProgressDialog()
    {

        if (pDialog == null)
        {
            pDialog = new ProgressDialog(this);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog()
    {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onBackPressed()
    {

        super.onBackPressed();


    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {
        switch (label)
        {
            case AppConstants.REQUEST_FORGOT_PASSWORD:
            {
                if (jsonResponse != null) {

                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS))
                    {
                        dismissDialog();
                        String msg = jsonResponse.message;
                        Intent in = new Intent(this, ForgotSuccessActivity.class);
                        in.putExtra("msg", msg);
                        startActivity(in);
                        Log.i(label, "hygt " + jsonResponse);

                        if (jsonResponse.forgotpasswordmessage != null)
                        {
                            dismissDialog();
                        }

                        if (jsonResponse.authorization != null)
                        {
                            dismissDialog();

                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE))
                    {
                        Log.i(label, "failllllllllll " + jsonResponse);
                        dismissDialog();
                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : "You don't have regersted email id, please contact to your nearest CSD center.");

                    }

                    break;
                }
                dismissDialog();
            }

        }

    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response)
    {

        switch (label)
        {
            case AppConstants.REQUEST_FORGOT_PASSWORD:
            {
                toast();
                Log.i(label, "gjjkfhdkh " + message);
                Log.i(label, "jhjkghfkh " + response);
                dismissDialog();
            }
            break;
        }


    }

    public void toast()
    {

        Toast.makeText(this, "User Doest Not exists, with this consumer_no", Toast.LENGTH_LONG).show();
    }
}
