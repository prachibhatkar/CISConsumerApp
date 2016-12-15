package com.essel.smartutilities.activity;

import android.app.Activity;
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

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.ManageAccountAdapter;
import com.essel.smartutilities.adapter.PaymentHistoryAdapter;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.NotificationCard;
import com.essel.smartutilities.models.PaymentHistory;
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

public class PaymentHistoryActivity extends AppCompatActivity implements View.OnClickListener, PaymentHistoryAdapter.OnRecycleItemClickListener {
    RecyclerView rv_consumers;
    ImageView add;
    private String TAG = "responsedataaaaa";
   private ArrayList<PaymentHistory> paymenthis;
    private Context mContext;
    String date1,date,amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Manage Accounts");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rv_consumers = (RecyclerView) findViewById(R.id.recycler_view);
       // LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ArrayList<Consumer> consumer = Consumer.createConsumersList(10);
       // PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(mContext,paymenthis);
        //rv_consumers.setAdapter(adapter);
       // rv_consumers.setLayoutManager(layoutManager);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        AsyncCallWS task = new AsyncCallWS();
        task.execute();



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
            //   Toast.makeText(LandingSkipLoginActivity.this, "Response", Toast.LENGTH_LONG).show();


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
            // if(getconsumerno.length()==10) {
                Request.addProperty("P_ACCT_ID", "1000039175");
                Request.addProperty("P_BILL_ID", "");
                Request.addProperty("P_MTR_ID", "#E-NG");
          ///  }
           /* else{
                Request.addProperty("P_ACCT_ID", getconsumerno);
                Request.addProperty("P_BILL_ID", "");
                Request.addProperty("P_MTR_ID", "OLD#E-NG");
            }*/

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

            StringTokenizer str1 = new StringTokenizer(date2, "#");
            String date11 = str1.nextToken();
            String amount11 = str1.nextToken();

            StringTokenizer str2 = new StringTokenizer(date3, "#");
            String date22 = str2.nextToken();
            String amount22 = str2.nextToken();

            StringTokenizer str3 = new StringTokenizer(date4, "#");
            String date33 = str3.nextToken();
            String amount33 = str3.nextToken();

            StringTokenizer str4 = new StringTokenizer(date5, "#");
            String date44 = str4.nextToken();
            String amount44 = str4.nextToken();

            StringTokenizer str5 = new StringTokenizer(date6, "#");
            String date55 = str5.nextToken();
            String amount55 = str5.nextToken();

          //  paymenthistory();

             paymenthis=new ArrayList<>();
             PaymentHistory paymenthistory1 = new PaymentHistory();
             LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            for (int i = 0; i < 6; i++) {
                paymenthistory1.amount = amount;
                paymenthistory1.date = date;
                paymenthistory1.receiptno = "dgfgh";
                paymenthis.add(i,paymenthistory1);
            }
            PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(mContext,paymenthis);
            rv_consumers.setAdapter(adapter);
            rv_consumers.setLayoutManager(layoutManager);


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

    @Override
    public void onItemClick(Consumer consumer) {

    }
}