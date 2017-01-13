package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

import java.util.ArrayList;

public class AddAccountCreateUserActivity extends AppCompatActivity implements View.OnClickListener, ServiceCaller
{

    TextView textViewConsumerName, textViewConsumerAddress, textViewConsumerConnectionType, textViewConsumerMobileNo;
    public ArrayList<Consumer> consumerArray;
    Context mContext;
    public ProgressDialog pDialog;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account_create_user);
        mContext = this;
        initialize();
    }

    private void initialize()
    {
        Button con = (Button) findViewById(R.id.btn_continue);
        Button add = (Button) findViewById(R.id.btn_addmore);
        add.setOnClickListener(this);
        con.setOnClickListener(this);
        textViewConsumerName = (TextView) findViewById(R.id.textConsumerName);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME) != null)
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
        if (CommonUtils.isNetworkAvaliable(this))
        {
            JsonObjectRequest request = WebRequests.getAccounts(this, Request.Method.GET, AppConstants.URL_GET_ACCOUNTS, AppConstants.REQUEST_GET_ACCOUNTS, this, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_ACCOUNTS);
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing())
            {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
        } else
            Toast.makeText(this.getApplicationContext(), R.string.error_internet_not_connected, Toast.LENGTH_SHORT).show();

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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case android.R.id.home:
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
            if (consumerArray.size() <= 9 && consumerArray != null)
            {
                i = new Intent(this, AddAccountGetUserActivity.class);
                startActivity(i);
            } else
                DialogCreator.showMessageDialog(this, " Only 10 Accounts can be Added ");
        } else if (v.getId() == R.id.btn_continue)
        {
            i = new Intent(this, ActivityLoginLanding.class);
            App.dropdown = true;
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed()
    {
        i = new Intent(this, AddAccountGetUserActivity.class);
        startActivity(i);
        App.dropdown=true;
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {
        switch (label)
        {
            case AppConstants.REQUEST_GET_ACCOUNTS:
            {
                if (jsonResponse != null)
                {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS))
                    {

                        if (jsonResponse.consumers != null && jsonResponse.consumers.size() > 0) {
                            consumerArray = jsonResponse.consumers;
                            dismissDialog();
                        }
                        if (jsonResponse.authorization != null)
                        {
                            dismissDialog();
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE))
                    {
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
    public void onAsyncFail(String message, String label, NetworkResponse response)
    {
        switch (label)
        {
            case AppConstants.REQUEST_GET_ACCOUNTS:
            {
                Log.i(label, AppConstants.REQUEST_GET_ACCOUNTS + message);
                Log.i(label, AppConstants.REQUEST_GET_ACCOUNTS + response);
                dismissDialog();
            }
            break;
        }
    }
}
