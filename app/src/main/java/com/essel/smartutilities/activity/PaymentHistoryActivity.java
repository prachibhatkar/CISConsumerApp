package com.essel.smartutilities.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.ManageAccountAdapter;
import com.essel.smartutilities.adapter.PaymentHistoryAdapter;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.NotificationCard;
import com.essel.smartutilities.models.PaymentHistory;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class PaymentHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rv_consumers;
    ImageView add;
    private String TAG = "responsedataaaaa";
   private ArrayList<PaymentHistory> paymenthis;
    private Context mContext;
    String date1,date,amount;
    private  ArrayList<String> NewPaymentlist=new ArrayList<String>();
    private  ArrayList<String> NewPaymentdates=new ArrayList<String>();
    private  ArrayList<String> NewPaymentreceiptno=new ArrayList<String>();
    LinearLayoutManager layoutManager ;
    PaymentHistoryAdapter adapter;
    ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
//
            mContext=this;
        rv_consumers = (RecyclerView) findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(mContext);
        rv_consumers.setLayoutManager(layoutManager);
        ArrayList<Consumer> consumer = Consumer.createConsumersList(10);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage(" please wait..");
                pDialog.show();
            }

            AsyncCallWS task = new AsyncCallWS();
            task.execute();

        }

        else
            Toast.makeText(this, " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();


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



    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            getPaymentHistory();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            Log.i(TAG, "response data: ");
             dismissDialog();
            //   Toast.makeText(LandingSkipLoginActivity.this, "Response", Toast.LENGTH_LONG).show();
          //  adapter = new PaymentHistoryAdapter(mContext, NewPaymentlist,NewPaymentdates,NewPaymentreceiptno);
            adapter = new PaymentHistoryAdapter(mContext, NewPaymentlist,NewPaymentdates);
           // adapter = new PaymentHistoryAdapter(mContext, NewPaymentlist);
            rv_consumers.setAdapter(adapter);
            rv_consumers.setLayoutManager(layoutManager);


        }


    }




    public void getPaymentHistory() {

        String getconsumerno = (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO)).toString();
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
            else
             if(getconsumerno.length()==12)
             {
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


            SoapObject responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn).getProperty("X_BILLDTLS_TBL");
            Log.i(TAG, "get : " + ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE14"));

            String paymenthistory=  ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE14").toString();

                StringTokenizer st = new StringTokenizer(paymenthistory, "~");
                date1 = st.nextToken();
                String date2 = st.nextToken();
                String date3 = st.nextToken();
                String date4 = st.nextToken();
                String date5 = st.nextToken();
                String date6 = st.nextToken();

              StringTokenizer str = new StringTokenizer(date1, "#");
                String date = str.nextToken();
                String amount = str.nextToken();
               // String receiptno = str.nextToken();

            StringTokenizer str1 = new StringTokenizer(date2, "#");
            String date11 = str1.nextToken();
            String amount11 = str1.nextToken();
           // String receiptno11 = str1.nextToken();

            StringTokenizer str2 = new StringTokenizer(date3, "#");
            String date22 = str2.nextToken();
            String amount22 = str2.nextToken();
           // String receiptno22 = str2.nextToken();

            StringTokenizer str3 = new StringTokenizer(date4, "#");
            String date33 = str3.nextToken();
            String amount33 = str3.nextToken();
           // String receiptno33 = str3.nextToken();

            StringTokenizer str4 = new StringTokenizer(date5, "#");
            String date44 = str4.nextToken();
            String amount44 = str4.nextToken();
           // String receiptno44 = str4.nextToken();

            StringTokenizer str5 = new StringTokenizer(date6, "#");
            String date55 = str5.nextToken();
            String amount55 = str5.nextToken();
           // String receiptno55 = str5.nextToken();


            ArrayList<String>Amount=new ArrayList<>();
            Amount.add(0,amount);
            Amount.add(1,amount11);
            Amount.add(2,amount22);
            Amount.add(3,amount33);
            Amount.add(4,amount44);
            Amount.add(5,amount55);

            ArrayList<String>Date=new ArrayList<>();
            Date.add(0,date);
            Date.add(1,date11);
            Date.add(2,date22);
            Date.add(3,date33);
            Date.add(4,date44);
            Date.add(5,date55);

         /*   ArrayList<String>Receiptno=new ArrayList<>();
            Receiptno.add(0,receiptno);
            Receiptno.add(1,receiptno11);
            Receiptno.add(2,receiptno22);
            Receiptno.add(3,receiptno33);
            Receiptno.add(4,receiptno44);
            Receiptno.add(5,receiptno55);
               paymenthis=new ArrayList<>();*/


           for (int i = 0; i < 6; i++) {
               PaymentHistory paymenthistory1 = new PaymentHistory();

                 NewPaymentlist.add(Amount.get(i));
                 NewPaymentdates.add(Date.get(i));
                // NewPaymentreceiptno.add(Receiptno.get(i));

                //paymenthis.add(paymenthistory1);
            }
            /*PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(mContext, NewPaymentlist);
            rv_consumers.setAdapter(adapter);
            rv_consumers.setLayoutManager(layoutManager);*/

            /*adapter = new PaymentHistoryAdapter(mContext, NewPaymentlist,NewPaymentdates,NewPaymentreceiptno);

            rv_consumers.setAdapter(adapter);
            rv_consumers.setLayoutManager(layoutManager);*/



        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());

        }


    }





    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }





    @Override
    public void onClick(View view) {


    }

   /* @Override
    public void onItemClick(Consumer consumer) {

    }*/
}