package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.SharedPrefManager;

public class RegisterActivity4 extends BaseActivity implements View.OnClickListener {

    EditText editTextEmailId, editTextMobileNo, editTextPassword, editTextRetypePassword, editTextOTPCode;
    TextInputLayout inputLayoutEmailId, inputLayoutMobileNo, inputLayoutPassword, inputLayoutRetypePassword;
    AppCompatButton buttonRegister, buttonVerify;
    TextView textViewConsumerName,consumernotext, consumerno,mobile, textViewConsumerAddress, maintitle, textViewConsumerConnectionType, textViewConsumerMobileNo, textViewActionResend;

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

        initialize();
    }

    private void initialize() {
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
            i = new Intent(this, AddAccountActivity.class);
            startActivity(i);
        } else if (v.getId() == R.id.btn_continue) {
//            callLogin();
            i = new Intent(this, ActivityLoginLanding.class);
            startActivity(i);
        }
    }

//    private void callLogin() {
//        if (CommonUtils.isNetworkAvaliable(this) == true) {
//            initProgressDialog();
//            if (pDialog != null && !pDialog.isShowing()) {
//                pDialog.setMessage("Logging in, please wait..");
//                pDialog.show();
//            }
//            JsonObjectRequest request = WebRequests.loginRequest(this, Request.Method.POST, AppConstants.URL_LOGIN, AppConstants.REQUEST_LOGIN, this,
//                    SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO), SharedPrefManager.getStringValue(this, SharedPrefManager.PASSWORD));
//            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_LOGIN);
//
//        } else
//            DialogCreator.showMessageDialog(this, getString(R.string.error_internet_not_connected));
//    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, LoginActivity.class);
        startActivity(i);
    }


//    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
//        switch (label) {
//            case AppConstants.REQUEST_LOGIN: {
//                if (jsonResponse != null) {
//                    System.out.println("Login Response " + jsonResponse.result);
//                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
//                        if (jsonResponse.user_info != null) {
//                            Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
//                            Log.i(label, "loginnnnnnnnnnnnnnn:" + jsonResponse.user_info);
//                            dismissDialog();
//                            CommonUtils.saveDetails(this, jsonResponse.user_info.consumer_no, jsonResponse.user_info.consumer_name,
//                                    jsonResponse.user_info.city);
//                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
//                            Intent i = new Intent(this, ActivityLoginLanding.class);
//                            startActivity(i);
//                            DatabaseManager.saveLoginDetails(this, jsonResponse.user_info);
//                        }
//                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
//                        dismissDialog();
//                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
//                        // Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null), Toast.LENGTH_LONG).show();
//                    }
//                } else
//                    Toast.makeText(this, R.string.er_data_not_avaliable, Toast.LENGTH_LONG).show();
//                dismissDialog();
//            }
//            break;
//
//        }
//
//    }
//
//    public void onAsyncFail(String messages, String label, NetworkResponse response) {
//        switch (label) {
//            case AppConstants.REQUEST_LOGIN: {
//                try {
//                    String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
//                    if (res != null) {
//                        Gson gson = new Gson();
//                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
//                        if (jsonResponse.error_code.equals("101")) {
//                            DialogCreator.showMessageDialog(this, getString(R.string.login_error_101));
//                        } else if (jsonResponse.error_code.equals("102")) {
//                            DialogCreator.showMessageDialog(this, getString(R.string.login_error_102));
//                        }
//                    }
//                } catch (Exception e) {
//                    DialogCreator.showMessageDialog(this, getString(R.string.login_error_null));
//                    e.printStackTrace();
//                }
//                pDialog.dismiss();
//            }
//        }
//    }

}
