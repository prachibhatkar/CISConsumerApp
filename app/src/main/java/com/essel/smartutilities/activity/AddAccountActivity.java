package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

/**
 * Created by hp on 11/4/2016.
 */

public class AddAccountActivity extends AppCompatActivity implements View.OnClickListener, ServiceCaller {
    TextView tv_city;
    Button btnNext;
    EditText editTextConsumerId;
    TextInputLayout inputLayoutConsumerId;
    Context mContext;
    ProgressDialog pDialog;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);
        mContext = this;
        tv_city = (TextView) findViewById(R.id.tv_city);
        btnNext = (Button) findViewById(R.id.BTNNext);
        editTextConsumerId = (EditText) findViewById(R.id.consumerno);
        inputLayoutConsumerId = (TextInputLayout) findViewById(R.id.inputLayoutConsumerId);
        btnNext.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_city.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_CITY));
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
            case R.id.BTNNext:
                validate();
//                Intent i = new Intent(this, AddAccountActivity2.class);
//                startActivity(i);
                break;
        }
    }

    public void validate() {

        if (editTextConsumerId.getText().toString().trim().length() >= 10 &&
                editTextConsumerId.getText().toString().trim().length() <= 20) {
            callAddAccount();
        } else
            Toast.makeText(this, "Enter valid Consumer No.", Toast.LENGTH_SHORT).show();
    }

    void callAddAccount() {
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            JSONObject obj = new JSONObject();
            try {
                obj.put("consumer_no", editTextConsumerId.getText().toString());
                obj.put("city", "");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest request = WebRequests.getRequestRegister(this, Request.Method.POST, AppConstants.URL_GET_REGISTER, AppConstants.REQUEST_REGISTER, this, obj);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_REGISTER);

        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_REGISTER: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "addaccountrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        if (jsonResponse.message != null)
                            Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NO, jsonResponse.consumer_no);
                        SharedPrefManager.saveValue(this, SharedPrefManager.ID, jsonResponse.id);
                        dismissDialog();
                        Intent i = new Intent(this, AddAccountActivity2.class);
                        startActivity(i);

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
            case AppConstants.REQUEST_REGISTER: {

                Log.i(label, "responseeeeeeeeeeee:" + response);
                Log.i(label, "addaccountrequestttttttttttttttttttpass:" + message);
                dismissDialog();
                break;
            }
        }
    }

}