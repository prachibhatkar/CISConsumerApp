package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by hp on 11/4/2016.
 */

public class AddAccountActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tv_city;
    Button btnNext;
    EditText editTextConsumerId;
    TextInputLayout inputLayoutConsumerId;
    Context mContext;
    ProgressDialog pDialog;
    public String TAG = "Add Accounttttttttttttt";


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
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, ManageAccountsActivity.class);
        startActivity(i);
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            consumerdetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            dismissDialog();
        }

    }

    public void consumerdetails() {
        String SOAP_ACTION = "http://123.63.20.164:8001/soa-infra/services/FieldMobility/getConsumerDetails/getconsumerdetailsbpelprocess_client_ep";
        String METHOD_NAME = "requestElement";
        String NAMESPACE = "http://www.example.org";
        String URL = "http://123.63.20.164:8001/soa-infra/services/FieldMobility/getConsumerDetails/getconsumerdetailsbpelprocess_client_ep";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("acctConsNo", editTextConsumerId.getText().toString());
            Request.addProperty("city", "Nagpur");//SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_CITY));
            Request.addProperty("serviceType", "Electricity");

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;

            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;


            androidHttpTransport.call(SOAP_ACTION, soapEnvelope);
            if (((SoapObject) soapEnvelope.bodyIn) != null) {
                Log.i(TAG, "get bodyin: " + (SoapObject) soapEnvelope.bodyIn);
                if (((SoapObject) soapEnvelope.bodyIn).getProperty("message") != null)
                    if ((((SoapObject) soapEnvelope.bodyIn).getProperty("message").toString()).equalsIgnoreCase("Please Select Correct Values")) {
                        this.runOnUiThread(new Runnable() {
                            public void run() {
                                DialogCreator.showMessageDialog(AddAccountActivity.this, "Please Enter Valid Consumer No");
                            }
                        });
                    } else {

                        Intent i = new Intent(this, AddAccountActivity2.class);
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NO_ADD, ((SoapObject) soapEnvelope.bodyIn).getProperty("accId").toString().equalsIgnoreCase("anytype{}")?" " :((SoapObject) soapEnvelope.bodyIn).getProperty("accId").toString());
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NAME_ADD, ((SoapObject) soapEnvelope.bodyIn).getProperty("perNam").toString());
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_CITY, ((SoapObject) soapEnvelope.bodyIn).getProperty("city").toString());
                        SharedPrefManager.saveValue(this, SharedPrefManager.ADDRESS1, ((SoapObject) soapEnvelope.bodyIn).getProperty("addr1").toString().equalsIgnoreCase("anytype{}")?" " :((SoapObject) soapEnvelope.bodyIn).getProperty("addr1").toString());
                        SharedPrefManager.saveValue(this, SharedPrefManager.ADDRESS2, ((SoapObject) soapEnvelope.bodyIn).getProperty("addr2").toString().equalsIgnoreCase("anytype{}")?" " :((SoapObject) soapEnvelope.bodyIn).getProperty("addr2").toString());
                        SharedPrefManager.saveValue(this, SharedPrefManager.ADDRESS3, ((SoapObject) soapEnvelope.bodyIn).getProperty("addr3").toString().equalsIgnoreCase("anytype{}")?" " :((SoapObject) soapEnvelope.bodyIn).getProperty("addr3").toString());
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONNECTION_TYPE, ((SoapObject) soapEnvelope.bodyIn).getProperty("conType").toString());
                        SharedPrefManager.saveValue(this, SharedPrefManager.MOBILE, ((SoapObject) soapEnvelope.bodyIn).getProperty("mobile").toString());
                        SharedPrefManager.saveValue(this, SharedPrefManager.CON_NO, ((SoapObject) soapEnvelope.bodyIn).getProperty("consNo").toString());
                        SharedPrefManager.saveValue(this, SharedPrefManager.POSTAL, ((SoapObject) soapEnvelope.bodyIn).getProperty("postal").toString());
                        startActivity(i);
                        dismissDialog();
                    }
            }
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}