package com.bynry.cisconsumerapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class AddAccountGetOTPActivity extends AppCompatActivity implements View.OnClickListener, ServiceCaller {
    EditText editTextEmailId, editTextMobileNo;
    TextInputLayout inputLayoutEmailId, inputLayoutMobileNo;
    AppCompatButton buttonRegister;
    TextView textViewConsumerName, textViewConsumerAddress, maintitle, textViewConsumerConnectionType, textViewConsumerMobileNo;
    Context mContext;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_get_otp);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        maintitle = (TextView) findViewById(R.id.title_bar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initialize();
    }

    private void initialize()
    {
        editTextEmailId = (EditText) findViewById(R.id.editEmailId);
        editTextMobileNo = (EditText) findViewById(R.id.editMobileNo);
        inputLayoutEmailId = (TextInputLayout) findViewById(R.id.inputLayoutEmailId);
        inputLayoutMobileNo = (TextInputLayout) findViewById(R.id.inputLayoutMobileNumber);
        textViewConsumerName = (TextView) findViewById(R.id.textConsumerName);
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
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE).toString().equalsIgnoreCase("NA")) {
            textViewConsumerMobileNo.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE));
        } else
        {
            noMobileDialog();
        }
        buttonRegister = (AppCompatButton) findViewById(R.id.btn_register);
        buttonRegister.setOnClickListener(this);

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

    @Override
    public void onClick(View v)
    {
        if (v == buttonRegister)
        {
            if (validate())
                callRegisteruser();
        }

    }

    private void noMobileDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Mobile No. is Not Registered. Please Register Mobile No in Your Nearest CSD Center.");
        builder.setCancelable(false);

        builder.setNegativeButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    private boolean validate()
    {
        Boolean flag = false;
        if (editTextEmailId.getText().length() == 0 || CommonUtils.emailValidator(editTextEmailId.getText().toString()))
        {
            if (editTextMobileNo.getText().length() == 10 || editTextMobileNo.getText().length() == 12 || editTextMobileNo.getText().length() == 0)
            {

                flag = true;

            } else
                Toast.makeText(this, "Retype valid Mobile", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();
        return flag;
    }


    void callRegisteruser()
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
                obj.put("person_name", SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME_ADD));
                obj.put("consumer_no", SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO_ADD));
                obj.put("account_no", SharedPrefManager.getStringValue(this, SharedPrefManager.CON_NO));
                obj.put("address_line1", SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS1));
                obj.put("address_line2", SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS2));
                obj.put("address_line3", SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS3));
                obj.put("mobile_no", SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE));
                obj.put("city", SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_CITY));
                obj.put("postal", SharedPrefManager.getStringValue(this, SharedPrefManager.POSTAL));
                obj.put("connection_type", SharedPrefManager.getStringValue(this, SharedPrefManager.CONNECTION_TYPE));
                obj.put("password", "");
                obj.put("reasone", "add");
                obj.put("alternet_email_id", editTextEmailId.getText().toString() == null ? "" : editTextEmailId.getText().toString());
                obj.put("alternet_mobile_no", editTextMobileNo.getText().toString() == null ? "" : editTextMobileNo.getText().toString());
            } catch (JSONException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest request = WebRequests.getRequestUser(this, Request.Method.POST, AppConstants.URL_REGISTER, AppConstants.REQUEST_CREATE, this, obj);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_CREATE);

        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {
        switch (label)
        {
            case AppConstants.REQUEST_CREATE:
            {
                if (jsonResponse != null)
                {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS))
                    {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "newrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        if (jsonResponse.message != null)
                            SharedPrefManager.saveValue(this, SharedPrefManager.ID, jsonResponse.id);
                        Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();
                        dismissDialog();
                        Intent i = new Intent(this, AddAccountVerifyOTPActivity.class);
                        startActivity(i);
                        CommonUtils.saveAuthToken(this, jsonResponse.authorization);

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
    public void onAsyncFail(String message, String label, NetworkResponse response)
    {
        switch (label)
        {
            case AppConstants.REQUEST_CREATE:
            {

                Log.i(label, "responseeeeeeeeeeee:" + response);
                Log.i(label, "requestttttttttttttttttttttfail:" + message);
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
