package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by hp on 11/4/2016.
 */

public class AddAccountActivity3 extends AppCompatActivity implements View.OnClickListener,ServiceCaller {

    Button btnNo, btnyes;
    ProgressDialog pDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account3);
        btnNo = (Button) findViewById(R.id.btn_no);
        btnyes = (Button) findViewById(R.id.btn_yes);
        btnyes.setOnClickListener(this);
        btnNo.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_no:
              callYes();
                break;
            case R.id.btn_yes:
                Intent in = new Intent(this, AddAccountActivity4.class);
                startActivity(in);
                break;
        }


    }

    private void callYes() {
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            JSONObject obj = new JSONObject();
            try {
                obj.put("consumer_no", SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
                obj.put("id", SharedPrefManager.getStringValue(this, SharedPrefManager.ID));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest request = WebRequests.getRequestOtp(this, Request.Method.POST, AppConstants.URL_GET_OTP, AppConstants.REQUEST_OTP, this, obj);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_OTP);

        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_OTP: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "addaccountrequesttttttttttttttttttttpass:" + jsonResponse.message);
                        if (jsonResponse.message != null)
                            Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NO, jsonResponse.consumer_no);
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NAME, jsonResponse.name);
                        SharedPrefManager.saveValue(this, SharedPrefManager.ADDRESS, jsonResponse.address);
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONNECTION_TYPE, jsonResponse.connection_type);
                        SharedPrefManager.saveValue(this, SharedPrefManager.MOBILE, jsonResponse.mobile_no);
                        Intent i = new Intent(this, RegisterActivity3.class);
                        startActivity(i);
                        dismissDialog();
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();
                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
                        // Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, R.string.er_data_not_avaliable, Toast.LENGTH_LONG).show();
                dismissDialog();
            }
            break;
        }
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {
        switch (label) {
            case AppConstants.REQUEST_OTP: {

                Log.i(label, "responseeeeeeeeeeee:" + response);
                Log.i(label, "addaccountrequestttttttttttttttttttttfail:" + message);
                dismissDialog();
                break;
            }
        }
    }
}