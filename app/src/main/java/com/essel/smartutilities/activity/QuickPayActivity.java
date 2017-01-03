package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import java.util.logging.LogRecord;

/**
 * Created by hp on 11/5/2016.
 */

public class QuickPayActivity extends BaseActivity implements View.OnClickListener {

    private static EditText consumerno;
    private TextInputLayout inputLayoutconsumerno;
    private Button Submit;
    private TextView tv_city;
    String consumer_number;
    private String TAG = "responsedataaaaa";
    ProgressDialog pDialog;
    SoapObject responceArray,a11;
    SoapPrimitive responceArray1;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_pay);
        initialize();
    }



//    Timer timer = new Timer();//Initialized
//    timer.schedule(new QuickPayActivity() {
//
//        public void run(){
//            // cancel the progress dialogue after 5 seconds
//           pDialog.cancel();
//            timer.cancel();
//
//    }5000 ,5000);
//    }
//



    private void initialize() {
        consumerno = (EditText) findViewById(R.id.Consumerno);
        consumer_number = (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO)).toString();
        consumerno.setText(consumer_number);
        tv_city = (TextView) findViewById(R.id.tv_city);
        Submit = (Button) findViewById(R.id.BTNSubmit);
        Submit.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_city.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_CITY));

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTNSubmit:
                if (consumerno.getText().toString().trim().length() >= 10 &&
                        consumerno.getText().toString().trim().length() <= 20) {

                    if (CommonUtils.isNetworkAvaliable(this)) {
                        initProgressDialog();
                        if (pDialog != null && !pDialog.isShowing()) {
                            pDialog.setMessage(" please wait..");
                            pDialog.show();
                        }

                        AsyncCallWS task = new AsyncCallWS();
                        task.execute();

                        //  Intent i = new Intent(this, PayNowActivity.class);
                        //  startActivity(i);
                    } else {
                        Toast.makeText(this, " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();

                    }
                } else
                    Toast.makeText(this, "Enter valid Consumer No.", Toast.LENGTH_SHORT).show();
                break;
        }
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
    private void dialogtime(){


        final Timer timer;// Declare it above
        timer = new Timer();//Initialized
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                // cancel the progress dialogue after 5 seconds
                pDialog.dismiss();

                timer.cancel();
            }
        }, 30000 ,30000);

        Toast.makeText(this, "Enter Correct Consumer No.", Toast.LENGTH_LONG).show();

    }


    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            getBillDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            Log.i(TAG, "response data: ");
            //Toast.makeText(QuickPayActivity.this, "Response", Toast.LENGTH_LONG).show();


        }


    }


    public void getBillDetails() {

        String getconsumerno = QuickPayActivity.consumerno.getText().toString();
        String SOAP_ACTION = "http://123.63.20.164:8001/soa-infra/services/Maharashtra/EsselCCBGetBillDetails!1.0*soa_8b795420-6bdd-4416-aa61-cf0cec7e5698/EsselCCBGetBillSvc";
        String METHOD_NAME = "InputParameters";
        String NAMESPACE = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/CCBGetBillDetailsProc";
        String URL = "http://123.63.20.164:8001/soa-infra/services/Maharashtra/EsselCCBGetBillDetails!1.0*soa_8b795420-6bdd-4416-aa61-cf0cec7e5698/EsselCCBGetBillSvc";
        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            if (getconsumerno.length() == 10) {
                Request.addProperty("P_ACCT_ID", getconsumerno);
                Request.addProperty("P_BILL_ID", "");
                Request.addProperty("P_MTR_ID", "#E-NG");
            } else if (getconsumerno.length() == 12) {
                Request.addProperty("P_ACCT_ID", getconsumerno);
                Request.addProperty("P_BILL_ID", "");
                Request.addProperty("P_MTR_ID", "OLD#E-NG");
            }

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;

            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;


            androidHttpTransport.call(SOAP_ACTION, soapEnvelope);
            String msg2="anyType{}";
            final SoapObject response = (SoapObject) soapEnvelope.getResponse();
            Log.i(TAG, "get : " + response.toString().equals(msg2));
             if(response.toString().equals(msg2)){
                dismissDialog();
                 this.runOnUiThread(new Runnable() {
                     public void run() {
                         DialogCreator.showMessageDialog(QuickPayActivity.this, "Please Enter Valid Consumer No/Select Valid City");
                     }
                 });
                // DialogCreator.showMessageDialog(QuickPayActivity.this, "Please Enter Valid Consumer No/Select Valid City");

             }
           //
            responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn).getProperty("X_BILLDTLS_TBL");
          //  a11 = (SoapObject) ((SoapObject) soapEnvelope.bodyIn).getProperty("X_STATUSMESSAGE");

            Log.i(TAG, "get : " + (SoapObject) ((SoapObject) soapEnvelope.bodyIn).getProperty("X_BILLDTLS_TBL"));
           // Log.i(TAG, "statusmsggggggggg : " + ((SoapObject) responceArray.getProperty(4)).getProperty("X_STATUSMESSAGE"));

             //dialogtime();
//

                        String duedate = ((SoapObject) responceArray.getProperty(0)).getProperty("DUE_DT_CASH").toString();
                        String currentamt = ((SoapObject) responceArray.getProperty(0)).getProperty("CURR_BILL_AMT").toString();
                        String promptamt = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE8").toString();
                        String netbill = ((SoapObject) responceArray.getProperty(0)).getProperty("NET_BILL_PAYABLE").toString();
                        String arrears = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE20").toString();
                        String consumername = ((SoapObject) responceArray.getProperty(0)).getProperty("CONSUMER_NAME").toString();
                        String accid = ((SoapObject) responceArray.getProperty(0)).getProperty("ACCT_ID").toString();
                        String promptdate = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE19").toString();
                        dismissDialog();
                        Intent in = new Intent(this, PayNowActivity.class);
                        in.putExtra("date", duedate);
                        in.putExtra("amt", currentamt);
                        in.putExtra("promtamt", promptamt);
                        in.putExtra("promtdate", promptdate);
                        in.putExtra("netbill", netbill);
                        in.putExtra("arrears", arrears);
                        in.putExtra("consumername", consumername);
                        in.putExtra("accid", accid);
                        startActivity(in);



        } catch (Exception e) {
            dismissDialog();
          //  Toast.makeText(QuickPayActivity.this, "Enter Valid Consumer No.", Toast.LENGTH_LONG).show();
            Log.i(TAG, "Errorrrrrrrrrrrrrrrrrrrrrrrrrrrr: " + e.getMessage());

        }


    }
}


