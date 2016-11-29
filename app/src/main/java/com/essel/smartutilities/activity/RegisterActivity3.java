package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity3 extends BaseActivity implements View.OnClickListener, ServiceCaller {

    EditText editTextEmailId, editTextMobileNo, editTextPassword, editTextRetypePassword, editTextOTPCode;
    TextInputLayout inputLayoutEmailId, inputLayoutMobileNo, inputLayoutPassword, inputLayoutRetypePassword;

    AppCompatButton buttonRegister, buttonVerify;
    TextView textViewConsumerName, textViewConsumerAddress, maintitle, textViewConsumerConnectionType, textViewConsumerMobileNo, textViewActionResend;
    LinearLayout linearActionCancel;
    Context mContext;
    Dialog dialogVerify, dialogSucccess;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        maintitle = (TextView) findViewById(R.id.title_bar);
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO).isEmpty())
            maintitle.setText("Register : " + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initialize();
    }

    private void initialize() {
        editTextEmailId = (EditText) findViewById(R.id.editEmailId);
        editTextMobileNo = (EditText) findViewById(R.id.editMobileNo);
        editTextPassword = (EditText) findViewById(R.id.editPassword);
        editTextRetypePassword = (EditText) findViewById(R.id.editRetypePassword);
        SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME);

        inputLayoutEmailId = (TextInputLayout) findViewById(R.id.inputLayoutEmailId);
        inputLayoutMobileNo = (TextInputLayout) findViewById(R.id.inputLayoutMobileNumber);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        inputLayoutRetypePassword = (TextInputLayout) findViewById(R.id.inputLayoutRetypePassword);

        textViewConsumerName = (TextView) findViewById(R.id.textConsumerName);
        textViewConsumerName.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME));
        textViewConsumerAddress = (TextView) findViewById(R.id.textConsumerAddress);
        textViewConsumerConnectionType = (TextView) findViewById(R.id.textConsumerConnectionType);
        textViewConsumerMobileNo = (TextView) findViewById(R.id.textConsumerMobileNo);
        textViewConsumerMobileNo.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));

        buttonRegister = (AppCompatButton) findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(this);


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
        if (v == buttonRegister) {
            if (validate())
                callRegisteruser();
        }

    }

    private boolean validate() {
        Boolean flag = false;
        if (editTextEmailId.getText().length() == 0 || emailValidator(editTextEmailId.getText().toString())) {
            if (editTextMobileNo.getText().length() == 10 || editTextMobileNo.getText().length() == 12 || editTextMobileNo.getText().length() == 0) {
                if (editTextPassword.getText().toString().trim().length() >= 6 && editTextPassword.getText().toString().trim().length() <= 20) {
                    if (editTextRetypePassword.getText().toString().trim().length() >= 6 && editTextRetypePassword.getText().toString().trim().length() <= 20) {
                        if (editTextRetypePassword.getText().toString().trim().compareTo(editTextPassword.getText().toString().trim()) == 0) {
                            flag = true;
                        } else
                            Toast.makeText(this, "Password Does not Match", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, "Retype valid Password", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Enter valid Password", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Retype valid Mobile", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
        return flag;
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    void callRegisteruser() {
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            JSONObject obj = new JSONObject();
            try {
                obj.put("consumer_no", SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
                obj.put("alternate_email", editTextEmailId.getText().toString() != null ? "" : editTextEmailId.getText().toString());
                obj.put("alternate_mobile", editTextMobileNo.getText().toString() != null ? "" : editTextMobileNo.getText().toString());
                obj.put("password", editTextPassword.getText().toString());
                obj.put("id", SharedPrefManager.getStringValue(this, SharedPrefManager.ID));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest request = WebRequests.getRequestUser(this, Request.Method.POST, AppConstants.URL_REGISTER, AppConstants.REQUEST_CREATE, this, obj);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_CREATE);

        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_CREATE: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "newrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        if (jsonResponse.message != null)
                            SharedPrefManager.saveValue(this, SharedPrefManager.PASSWORD, editTextPassword.getText().toString());
                        Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();
                        dismissDialog();
                        Intent i = new Intent(this, RegisterActivity4.class);
                        startActivity(i);
                        CommonUtils.saveAuthToken(this, jsonResponse.authorization);

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
            case AppConstants.REQUEST_CREATE: {

                Log.i(label, "responseeeeeeeeeeee:" + response);
                Log.i(label, "requestttttttttttttttttttttfail:" + message);
                dismissDialog();
                break;
            }
        }
    }
}
