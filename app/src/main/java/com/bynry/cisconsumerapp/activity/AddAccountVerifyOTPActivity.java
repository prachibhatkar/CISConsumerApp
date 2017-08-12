package com.bynry.cisconsumerapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bynry.cisconsumerapp.utility.CommonUtils;
import com.bynry.cisconsumerapp.utility.DialogCreator;
import com.bynry.cisconsumerapp.utility.SharedPrefManager;
import com.bynry.cisconsumerapp.webservice.WebRequests;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.callers.ServiceCaller;
import com.bynry.cisconsumerapp.models.JsonResponse;
import com.bynry.cisconsumerapp.utility.App;
import com.bynry.cisconsumerapp.utility.AppConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class AddAccountVerifyOTPActivity extends BaseActivity implements View.OnClickListener, ServiceCaller {

    Toolbar toolbar;
    AppCompatButton  buttonVerify;
    EditText otp;
    TextView  textViewConsumerName, textViewConsumerMobileNo, textViewConsumerAddress, textViewConsumerConnectionType;

    TextView maintitle, action_resend, msg;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_verify_otp);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        maintitle = (TextView) findViewById(R.id.title_bar);
        maintitle.setText("Add Account");
        textViewConsumerName = (TextView) findViewById(R.id.textConsumerName);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME_ADD) != null)
            textViewConsumerName.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME_ADD));
        textViewConsumerAddress = (TextView) findViewById(R.id.textConsumerAddress);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS1) != null
                && SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS2) != null
                && SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS3) != null)
            textViewConsumerAddress.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS1)
                    + " " + SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS2)
                    + " " + SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS3));
        textViewConsumerConnectionType = (TextView) findViewById(R.id.textConsumerConnectionType);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONNECTION_TYPE) != null)
            textViewConsumerConnectionType.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONNECTION_TYPE));

        textViewConsumerMobileNo = (TextView) findViewById(R.id.textConsumerMobileNo);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE) != null && !SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE).toString().equalsIgnoreCase(""))
            textViewConsumerMobileNo.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE));
        initialize();
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

    private void initialize()
    {
        buttonVerify = (AppCompatButton) findViewById(R.id.btn_verify);
        buttonVerify.setOnClickListener(this);
        otp = (EditText) findViewById(R.id.edit_otp);
        msg = (TextView) findViewById(R.id.msg);

        action_resend = (TextView) findViewById(R.id.action_resend);
        action_resend.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    public void onClick(View v)
    {

        switch (v.getId())
        {
            case R.id.btn_verify:
                if (otp.getText().toString().trim().length() == 4)
                {callAdd();
                } else
                    Toast.makeText(this, "Enter valid OTP", Toast.LENGTH_SHORT).show();
                break;

            case R.id.action_resend:
                msg.setText(R.string.title_verify_resend);
                callResend();
                break;
        }
    }

    void callAdd()
    {
        if (CommonUtils.isNetworkAvaliable(this))
        {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing())
            {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            JSONObject obj = new JSONObject();
            try {
                obj.put("consumer_no", SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO_ADD));
                obj.put("otp", otp.getText().toString());
                obj.put("id", SharedPrefManager.getStringValue(this, SharedPrefManager.ID));
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest request = WebRequests.getRequestOtpforAdd(this, Request.Method.POST, AppConstants.URL_ADD_ACCOUNT, AppConstants.REQUEST_ADD_ACCOUNT, this, obj, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_OTP);

        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    void callResend()
    {
        if (CommonUtils.isNetworkAvaliable(this))
        {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing())
            {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            JSONObject obj = new JSONObject();
            try
            {
                obj.put("consumer_no", SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO_ADD));
                obj.put("id", SharedPrefManager.getStringValue(this, SharedPrefManager.ID));
            } catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest request = WebRequests.getRequestOtpforAdd(this, Request.Method.POST, AppConstants.URL_GET_RESEND_OTP, AppConstants.REQUEST_RESEND_OTP, this, obj, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_RESEND_OTP);

        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {
        switch (label)
        {
            case AppConstants.REQUEST_ADD_ACCOUNT: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS))
                    {
                        if (jsonResponse.message != null)
                            SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NO_ADD, jsonResponse.consumer_no);
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NAME_ADD, jsonResponse.name);
                        SharedPrefManager.saveValue(this, SharedPrefManager.ADDRESS1, jsonResponse.address);
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONNECTION_TYPE, jsonResponse.connection_type);
                        SharedPrefManager.saveValue(this, SharedPrefManager.MOBILE, jsonResponse.mobile_no);
                        if (jsonResponse.authorization != null)
                            SharedPrefManager.saveValue(this, SharedPrefManager.AUTH_TOKEN.toString(), jsonResponse.authorization);
                        Intent i = new Intent(this, AddAccountCreateUserActivity.class);
                        startActivity(i);
                        dismissDialog();
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE))
                    {
                        dismissDialog();
                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
                    }
                } else
                    Toast.makeText(this, R.string.er_data_not_avaliable, Toast.LENGTH_LONG).show();
                dismissDialog();
            }
            break;
            case AppConstants.REQUEST_RESEND_OTP:
            {
                if (jsonResponse != null)
                {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS))
                    {
                        if (jsonResponse.message != null)
                            Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();

                        dismissDialog();
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE))
                    {
                        dismissDialog();
                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
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
            case AppConstants.REQUEST_ADD_ACCOUNT: {

//                Log.i(label, "responseeeeeeeeeeee:" + response);
//                Log.i(label, "addaccountrequestttttttttttttttttttttfail:" + message);
                dismissDialog();
                if(response.statusCode==401)
                {Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_LOGGED, "false");
                }
                break;
            }
            case AppConstants.REQUEST_RESEND_OTP: {

//                Log.i(label, "responseeeeeeeeeeee:" + response);
//                Log.i(label, "OTPrequestttttttttttttttttttttfail:" + message);
                dismissDialog();
                if(response.statusCode==401)
                  {Intent intent=new Intent(this,LoginActivity.class);
                    startActivity(intent);
                    SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_LOGGED, "false");
                  }
                break;
            }
        }
    }
}
