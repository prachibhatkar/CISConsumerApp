package com.essel.smartutilities.activity;

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
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;
import com.google.gson.Gson;

public class RegisterActivity4 extends BaseActivity implements View.OnClickListener, ServiceCaller {

    EditText editTextEmailId, editTextMobileNo, editTextPassword, editTextRetypePassword, editTextOTPCode;
    TextInputLayout inputLayoutEmailId, inputLayoutMobileNo, inputLayoutPassword, inputLayoutRetypePassword;
    AppCompatButton buttonRegister, buttonVerify;
    TextView textViewConsumerName, consumerno, textViewConsumerAddress, maintitle, textViewConsumerConnectionType, textViewConsumerMobileNo, textViewActionResend;

    Context mContext;
    ProgressDialog pDialog;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register4);
        mContext = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        maintitle = (TextView) findViewById(R.id.title_bar);
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO).isEmpty())
            maintitle.setText("Register : " + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
    }

    private void initialize() {
        Button con = (Button) findViewById(R.id.btn_continue);
        Button add = (Button) findViewById(R.id.btn_addmore);
        inputLayoutEmailId = (TextInputLayout) findViewById(R.id.inputLayoutEmailId);
        inputLayoutMobileNo = (TextInputLayout) findViewById(R.id.inputLayoutMobileNumber);
        textViewConsumerName = (TextView) findViewById(R.id.textConsumerName);
        consumerno = (TextView) findViewById(R.id.consumerno);
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO).isEmpty())
            consumerno.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
        textViewConsumerAddress = (TextView) findViewById(R.id.textConsumerAddress);
        textViewConsumerConnectionType = (TextView) findViewById(R.id.textConsumerConnectionType);
        textViewConsumerMobileNo = (TextView) findViewById(R.id.textConsumerMobileNo);
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE).isEmpty())
            consumerno.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE));
        add.setOnClickListener(this);
        con.setOnClickListener(this);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_addmore) {
            i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        } else if (v.getId() == R.id.btn_continue) {
            callLogin();
        }
    }

    private void callLogin() {
        if (CommonUtils.isNetworkAvaliable(this) == true) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Logging in, please wait..");
                pDialog.show();
            }
            JsonObjectRequest request = WebRequests.loginRequest(this, Request.Method.POST, AppConstants.URL_LOGIN, AppConstants.REQUEST_LOGIN, this,
                    SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO), SharedPrefManager.getStringValue(this, SharedPrefManager.PASSWORD));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_LOGIN);

        } else
            DialogCreator.showMessageDialog(this, getString(R.string.error_internet_not_connected));
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }


    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_LOGIN: {
                if (jsonResponse != null) {
                    System.out.println("Login Response " + jsonResponse.result);
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        if (jsonResponse.user_info != null) {
                            Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                            Log.i(label, "loginnnnnnnnnnnnnnn:" + jsonResponse.user_info);
                            dismissDialog();
                            CommonUtils.saveDetails(this, jsonResponse.user_info.consumer_no, jsonResponse.user_info.consumer_name,
                                    jsonResponse.user_info.city);
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
                            Intent i = new Intent(this, ActivityLoginLanding.class);
                            startActivity(i);
                            DatabaseManager.saveLoginDetails(this, jsonResponse.user_info);
                        }
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

    public void onAsyncFail(String messages, String label, NetworkResponse response) {
        switch (label) {
            case AppConstants.REQUEST_LOGIN: {
                try {
                    String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    if (res != null) {
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        if (jsonResponse.error_code.equals("101")) {
                            DialogCreator.showMessageDialog(this, getString(R.string.login_error_101));
                        } else if (jsonResponse.error_code.equals("102")) {
                            DialogCreator.showMessageDialog(this, getString(R.string.login_error_102));
                        }
                    }
                } catch (Exception e) {
                    DialogCreator.showMessageDialog(this, getString(R.string.login_error_null));
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }
    }

}
