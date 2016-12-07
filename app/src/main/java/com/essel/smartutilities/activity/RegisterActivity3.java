package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

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

public class RegisterActivity3 extends BaseActivity implements View.OnClickListener, ServiceCaller {

    Toolbar toolbar;
    AppCompatButton buttonRegister, buttonVerify;
    EditText otp;
    TextView maintitle, msg, resend,textViewConsumerName,textViewConsumerMobileNo,textViewConsumerAddress,textViewConsumerConnectionType;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        maintitle = (TextView) findViewById(R.id.title_bar);
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO).isEmpty())
            maintitle.setText("Register : " + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
        initialize();
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

    private void initialize() {
        textViewConsumerName = (TextView) findViewById(R.id.textConsumerName);
        textViewConsumerName.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME));
        textViewConsumerAddress = (TextView) findViewById(R.id.textConsumerAddress);
        textViewConsumerConnectionType = (TextView) findViewById(R.id.textConsumerConnectionType);
        textViewConsumerMobileNo = (TextView) findViewById(R.id.textConsumerMobileNo);
        textViewConsumerMobileNo.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
        buttonVerify = (AppCompatButton) findViewById(R.id.btn_verify);
        msg = (TextView) findViewById(R.id.msg);
        resend = (TextView) findViewById(R.id.action_resend);
        resend.setOnClickListener(this);
        buttonVerify.setOnClickListener(this);
        otp = (EditText) findViewById(R.id.edit_otp);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_verify: {
                if (otp.getText().toString().trim().length() == 4) {
                    callRegister();
                } else
                    Toast.makeText(this, "Enter valid OTP", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(this, RegisterActivity4.class);
//                startActivity(i);
            }
            break;
            case R.id.action_resend:
                msg.setText(R.string.title_verify_resend);
                callResend();

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    void callRegister() {
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            JSONObject obj = new JSONObject();
            try {
                obj.put("consumer_no", SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
                obj.put("otp", otp.getText().toString());
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

    void callResend() {
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
            JsonObjectRequest request = WebRequests.getRequestOtp(this, Request.Method.POST, AppConstants.URL_GET_RESEND_OTP, AppConstants.REQUEST_RESEND_OTP, this, obj);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_RESEND_OTP);

        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_OTP: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "OPTsendrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        if (jsonResponse.message != null)
                            Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NO, jsonResponse.consumer_no);
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NAME, jsonResponse.name);
                        SharedPrefManager.saveValue(this, SharedPrefManager.ADDRESS, jsonResponse.address);
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONNECTION_TYPE, jsonResponse.connection_type);
                        SharedPrefManager.saveValue(this, SharedPrefManager.MOBILE, jsonResponse.mobile_no);
                        Intent i = new Intent(this, RegisterActivity4.class);
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
            case AppConstants.REQUEST_RESEND_OTP: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "OTPrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        if (jsonResponse.message != null)
                            Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();

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
                Log.i(label, "OTPsendquestttttttttttttttttttttfail:" + message);
                dismissDialog();
                break;
            }
            case AppConstants.REQUEST_RESEND_OTP: {

                Log.i(label, "responseeeeeeeeeeee:" + response);
                Log.i(label, "OTPrequestttttttttttttttttttttfail:" + message);
                dismissDialog();
                break;
            }
        }
    }
}
