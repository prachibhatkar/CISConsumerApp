package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.essel.smartutilities.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Arrays;

public class LandingSkipLoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button Submit;
    ImageView imgBack;
    LinearLayout action_about_us, action_tips, action_my_traiff, action_faq, action_contactus, action_share;
    static EditText consumerno;
    Spinner sp_city;
    private String TAG = "responsedataaaaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_skip_login);
        initialize();
    }

    private void initialize() {
        consumerno = (EditText) findViewById(R.id.consumer_id);
        Submit = (Button) findViewById(R.id.BTNSubmit);
        Submit.setOnClickListener(this);
        action_about_us = (LinearLayout) findViewById(R.id.action_about_us);
        action_tips = (LinearLayout) findViewById(R.id.action_tips);
        action_my_traiff = (LinearLayout) findViewById(R.id.action_my_traiff);
        action_faq = (LinearLayout) findViewById(R.id.action_faq);
        action_contactus = (LinearLayout) findViewById(R.id.action_contact_us);
        action_share = (LinearLayout) findViewById(R.id.action_share);

        action_about_us.setOnClickListener(this);
        action_tips.setOnClickListener(this);
        action_my_traiff.setOnClickListener(this);
        action_faq.setOnClickListener(this);
        action_contactus.setOnClickListener(this);
        action_share.setOnClickListener(this);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sp_city = (Spinner) findViewById(R.id.sp_city);
        String[] routes = this.getResources().getStringArray(R.array.City);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Arrays.asList(routes));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(dataAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            // DialogCreator.showLogoutDialog(this, "Logout", "Are you sure you want to logout?");
            return true;
        }
        if (id == R.id.action_notifications) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validate() {
        Boolean flag = false;

        if (consumerno.getText().toString().trim().length() != 0 && (consumerno.getText().toString().trim().length() >= 10
                && consumerno.getText().toString().trim().length() <= 20)) {
            flag = true;
        } else
            Toast.makeText(this, "Enter valid Consumer Number", Toast.LENGTH_LONG).show();
        return flag;
    }

    @Override
    public void onClick(View v) {
        Intent in;
        switch (v.getId()) {
            case R.id.BTNSubmit:
                if (validate()) {
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();
                    //  in = new Intent(this, PayNowActivity.class);
                    //  startActivity(in);
                }
                break;
            case R.id.action_about_us:
                in = new Intent(this, AboutUsActivity.class);
                startActivity(in);
                break;
            case R.id.action_contact_us:
                in = new Intent(this, Contact_Us_Activity.class);
                startActivity(in);
                break;
            case R.id.action_my_traiff:
                in = new Intent(this, TraiffActivity.class);
                startActivity(in);
                break;
            case R.id.action_faq:
                in = new Intent(this, FAQActivity.class);
                startActivity(in);
                break;
            case R.id.action_tips:
                in = new Intent(this, TipsActivity.class);
                startActivity(in);
                break;
            case R.id.action_share:
                shareTextUrl();
                break;
        }
    }

    private void shareTextUrl() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.EsselSmartUtilities.com");

        startActivity(Intent.createChooser(share, "Share link!"));
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
         //   Toast.makeText(LandingSkipLoginActivity.this, "Response", Toast.LENGTH_LONG).show();


        }


    }


    public void getBillDetails() {

        String getconsumerno = LandingSkipLoginActivity.consumerno.getText().toString();
        String SOAP_ACTION = "http://123.63.20.164:8001/soa-infra/services/Maharashtra/EsselCCBGetBillDetails!1.0*soa_8b795420-6bdd-4416-aa61-cf0cec7e5698/EsselCCBGetBillSvc";
        String METHOD_NAME = "InputParameters";
        String NAMESPACE = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/CCBGetBillDetailsProc";
        String URL = "http://123.63.20.164:8001/soa-infra/services/Maharashtra/EsselCCBGetBillDetails!1.0*soa_8b795420-6bdd-4416-aa61-cf0cec7e5698/EsselCCBGetBillSvc";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            if(getconsumerno.length()==10) {
                Request.addProperty("P_ACCT_ID", getconsumerno);
                Request.addProperty("P_BILL_ID", "");
                Request.addProperty("P_MTR_ID", "#E-NG");
            }
            else{
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

            final SoapObject response = (SoapObject) soapEnvelope.getResponse();




//            Log.i(TAG, "get responce: " + ((SoapObject) soapEnvelope.getResponse()).getProperty(0));
//            Log.i(TAG, "get bodyin response: " + response);

//            Log.i(TAG, "get bodyin //City: " + ((Object) soapEnvelope.bodyIn).getProperty("X_STATUSMESSAGE"));
//            Log.i(TAG, "get bodyin //Name: " + ((SoapObject) soapEnvelope.bodyIn).getProperty("X_BILLDTLS_TBL"));
            SoapObject responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn).getProperty("X_BILLDTLS_TBL");
            Log.i(TAG, "get : " + ((SoapObject)responceArray.getProperty(0)).getProperty("DUE_DT_CASH"));
            Log.i(TAG, "get : " +((SoapObject) responceArray.getProperty(0)).getProperty("CURR_BILL_AMT"));

            String duedate=  ((SoapObject)responceArray.getProperty(0)).getProperty("DUE_DT_CASH").toString();
            String currentamt=  ((SoapObject)responceArray.getProperty(0)).getProperty("CURR_BILL_AMT").toString();
            String promptamt=  ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE8").toString();
            String netbill=  ((SoapObject)responceArray.getProperty(0)).getProperty("NET_BILL_PAYABLE").toString();
            String arrears=  ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE20").toString();
            String consumername =  ((SoapObject)responceArray.getProperty(0)).getProperty("CONSUMER_NAME").toString();
            String accid =  ((SoapObject)responceArray.getProperty(0)).getProperty("ACCT_ID").toString();
            String promptdate =  ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE19").toString();

             Intent in = new Intent(this, PayNowActivity.class);
             in.putExtra("date", duedate);
             in.putExtra("amt", currentamt);
             in.putExtra("promtamt", promptamt);
             in.putExtra("promtdate", promptdate);
             in.putExtra("netbill", netbill);
             in.putExtra("arrears", arrears);
             in.putExtra("consumername", consumername);
             in.putExtra("accid",accid);
             startActivity(in);



            Log.i(TAG, "get : " +((SoapObject) responceArray.getProperty(0)).getProperty(" PROMPT_PAYMENT_INCENTIVE"));
            Log.i(TAG, "get : " +((SoapObject) responceArray.getProperty(0)).getProperty(" NET_BILL_PAYABLE"));
            Log.i(TAG, "get : " +((SoapObject) responceArray.getProperty(0)).getProperty(" ARREARS_INCL_CUMM_SURCH"));


            //Intent in = new Intent(this, PayNowActivity.class);
            //in.putExtra("epuzzle", duedate);
           // in.putExtra("epuzzle", duedate);
           // startActivity(in);


           /* for (int i = 0; i < responceArray.getPropertyCount(); i++) {
                Object obj = responceArray.getProperty(i);
                if (obj instanceof SoapObject) {
                    SoapObject obj1 = (SoapObject) obj;
//                    obj1.getProperty("BILL_MONTH").toString();
//                    obj1.getProperty("BILL_YEAR").toString();

                    Log.i(TAG, "Index =  " + i + ", month = " + obj1.getProperty("BILL_MONTH").toString() + " , year = " +
                            obj1.getProperty("BILL_YEAR").toString());

                    Log.i(TAG, "Index =  " + i + ", currentamt = " + obj1.getProperty("CURR_BILL_AMT").toString() + " , duedate = " +
                            obj1.getProperty("DUE_DT_CASH").toString());

                }
            }*/


        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());

        }


    }
}
