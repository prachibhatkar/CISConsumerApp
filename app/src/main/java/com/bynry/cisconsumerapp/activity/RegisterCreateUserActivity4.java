package com.bynry.cisconsumerapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bynry.cisconsumerapp.models.Consumer;
import com.bynry.cisconsumerapp.utility.App;
import com.bynry.cisconsumerapp.utility.CommonUtils;
import com.bynry.cisconsumerapp.utility.DialogCreator;
import com.bynry.cisconsumerapp.utility.SharedPrefManager;
import com.bynry.cisconsumerapp.webservice.WebRequests;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.callers.ServiceCaller;
import com.bynry.cisconsumerapp.models.JsonResponse;
import com.bynry.cisconsumerapp.utility.AppConstants;

import java.util.ArrayList;

public class RegisterCreateUserActivity4 extends BaseActivity implements View.OnClickListener, ServiceCaller
{

    EditText editTextEmailId, editTextMobileNo, editTextPassword, editTextRetypePassword, editTextOTPCode;
    TextInputLayout inputLayoutEmailId, inputLayoutMobileNo, inputLayoutPassword, inputLayoutRetypePassword;
    AppCompatButton buttonRegister, buttonVerify;
    TextView textViewConsumerName, consumernotext, consumerno, mobile, textViewConsumerAddress, maintitle, textViewConsumerConnectionType, textViewConsumerMobileNo, textViewActionResend;

    Context mContext;
    ProgressDialog pDialog;
    ArrayList<Consumer> consumerArray;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_create_user);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        maintitle = (TextView) findViewById(R.id.title_bar);
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO).isEmpty())
            maintitle.setText("Register : " + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));

        initialize();
    }

    private void initialize()
    {
        Button con = (Button) findViewById(R.id.btn_continue);

        Button add = (Button) findViewById(R.id.btn_addmore);
        textViewConsumerName = (TextView) findViewById(R.id.textConsumerName);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME) != null)
            textViewConsumerName.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME));

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
        mobile = (TextView) findViewById(R.id.ConsumerMobileNo);
        mobile.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE));
        textViewConsumerMobileNo = (TextView) findViewById(R.id.textConsumerMobileNo);
        consumernotext = (TextView) findViewById(R.id.consumerno);
        consumernotext.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE) != null && !SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE).toString().equalsIgnoreCase(""))
            textViewConsumerMobileNo.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE));
        add.setOnClickListener(this);
        con.setOnClickListener(this);
        if (CommonUtils.isNetworkAvaliable(this))
        {
            JsonObjectRequest request = WebRequests.getAccounts(this, Request.Method.GET, AppConstants.URL_GET_ACCOUNTS, AppConstants.REQUEST_GET_ACCOUNTS, this, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_ACCOUNTS);
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
        } else
            Toast.makeText(this.getApplicationContext(), R.string.error_internet_not_connected, Toast.LENGTH_SHORT).show();

        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                onBackPressed();
                finish();
            }
        });
    }

    private void initProgressDialog()
    {

        if (pDialog == null) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btn_addmore)
        {
            if (consumerArray.size() <= 9 && consumerArray != null) {
                i = new Intent(this, AddAccountGetUserActivity.class);
                startActivity(i);
            } else
                DialogCreator.showMessageDialog(this, " Only 10 Accounts can be Added ");
        } else if (v.getId() == R.id.btn_continue) {
            i = new Intent(this, ActivityLoginLanding.class);
            startActivity(i);
        }
    }


    @Override
    public void onBackPressed() {
        if (CommonUtils.isNetworkAvaliable(this)) {

            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage(" please wait..");
                pDialog.show();
            }
            JsonObjectRequest request = WebRequests.getLogOut(this, Request.Method.GET, AppConstants.URL_LOGOUT, AppConstants.REQUEST_LOGOUT, this, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_LOGOUT);
        } else {
            Toast.makeText(this.getApplicationContext(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();

        }

        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_LOGOUT: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        if (jsonResponse.message != null) {
                            SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_LOGGED, "false");
                            SharedPrefManager.saveValue(this, SharedPrefManager.AUTH_TOKEN, "no");

                            dismissDialog();
                            Intent in = new Intent(this, LoginActivity.class);
                            startActivity(in);
                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.message);
                        }
                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();
                        dismissDialog();
                    }
                    break;
                }

                dismissDialog();
            }
            case AppConstants.REQUEST_GET_ACCOUNTS: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {

                        if (jsonResponse.consumers != null && jsonResponse.consumers.size() > 0) {
                            consumerArray = jsonResponse.consumers;
                            dismissDialog();
                        }
                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();
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
            case AppConstants.REQUEST_GET_ACCOUNTS: {
                Log.i(label, AppConstants.REQUEST_GET_ACCOUNTS + message);
                Log.i(label, AppConstants.REQUEST_GET_ACCOUNTS + response);
                dismissDialog();
            }
            break;


            case AppConstants.REQUEST_LOGOUT: {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
//                Log.i(label, "Faq:" + message);
//                Log.i(label, "Faq:" + response);
                dismissDialog();
            }

            break;
        }
    }


}
