package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;
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

import java.util.StringTokenizer;

public class MyBillActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_billhistory;
    private ProgressDialog pDialog;
    public static int[] consum = new int[6];
    public static String[] billdate = new String[6];
    public static String[] billamount = new String[6];
    public static String[] month = new String[6];
    public static long[] billid = new long[6];
    public TextView title, duedate_date, propmtamt, currentamt, arriers, promptdate, netamt, amtafterdue;
    public String duedate1, promptamt1, currentamt1, arrears1, promptdate1, netbill, amtafterdues;
    public GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bill);
        graph = (GraphView) findViewById(R.id.graph);
        graph.setTitle("My Consumtion (Units):Last Six Months");
        propmtamt = (TextView) findViewById(R.id.promptamt);
        currentamt = (TextView) findViewById(R.id.currentamount);
        duedate_date = (TextView) findViewById(R.id.duedate_date);
        arriers = (TextView) findViewById(R.id.Arriers);
        promptdate = (TextView) findViewById(R.id.promptdate);
        amtafterdue = (TextView) findViewById(R.id.amtafterdue);
        netamt = (TextView) findViewById(R.id.netamt);
        title = (TextView) findViewById(R.id.title_bar);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO) != null)
            title.setText("My Bill " + "( " + SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NO) + " )");
        else
            title.setText("My Bill ");
        btn_billhistory = (Button) findViewById(R.id.btn_history);
        btn_billhistory.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void intialize() {
        arriers.setText(arrears1);
        netamt.setText(netbill);
        propmtamt.setText(promptamt1);
        duedate_date.setText(duedate1);
        currentamt.setText(currentamt1);
        promptdate.setText(promptdate1);
        amtafterdue.setText(amtafterdues);


        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, consum[0]),
                new DataPoint(1, consum[1]),
                new DataPoint(2, consum[2]),
                new DataPoint(3, consum[3]),
                new DataPoint(4, consum[4]),
                new DataPoint(5, consum[5])

        });

        series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
        series.setDrawBackground(false);
        graph.addSeries(series);


        BarGraphSeries<DataPoint> series1 = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, consum[0]),
                new DataPoint(1, consum[1]),
                new DataPoint(2, consum[2]),
                new DataPoint(3, consum[3]),
                new DataPoint(4, consum[4]),
                new DataPoint(5, consum[5])
        });
        graph.addSeries(series1);
        series1.setDrawValuesOnTop(true);
        series1.setColor(getResources().getColor(R.color.colorPrimaryDarkText));
        series1.setValuesOnTopColor(getResources().getColor(R.color.colorPrimaryDarkText));
        series1.setSpacing(120);

        // set manual labels on horizontal axis
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(month);
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
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
        Intent in = new Intent(this, BillHistoryActivity.class);
        startActivity(in);
    }

    class AsyncCallWS extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            Log.i("manageAccounts", "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i("manageAccounts", "doInBackground");
            getBillDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i("manageAccounts", "onPostExecute");
            Log.i("manageAccounts", "response data: ");
            dismissDialog();
            intialize();
        }
    }

    public void getBillDetails() {

        String getconsumerno = SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO);
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
            final SoapObject response = (SoapObject) soapEnvelope.getResponse();
            SoapObject responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn).getProperty("X_BILLDTLS_TBL");
//            Log.i("manageAccounts", "get : " + ((SoapObject) responceArray.getProperty(0)).getProperty("DUE_DT_CASH"));
//            Log.i("manageAccounts", "get : " + ((SoapObject) responceArray.getProperty(0)).getProperty("CURR_BILL_AMT"));
            duedate1 = ((SoapObject) responceArray.getProperty(0)).getProperty("DUE_DT_CASH").toString();
            currentamt1 = ((SoapObject) responceArray.getProperty(0)).getProperty("CURR_BILL_AMT").toString();
            promptamt1 = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE8").toString();
            promptdate1 = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE19").toString();
            netbill = ((SoapObject) responceArray.getProperty(0)).getProperty("NET_BILL_PAYABLE").toString();
            arrears1 = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE20").toString();
            amtafterdues = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE12").toString();

            for (int i = 0; i < responceArray.getPropertyCount(); i++) {
                Object obj = responceArray.getProperty(i);
                if (obj instanceof SoapObject) {
                    SoapObject obj1 = (SoapObject) obj;
                    consum[i] = Integer.parseInt(obj1.getProperty("KWH_CONSUMPTI").toString());
                    month[i] = obj1.getProperty("BILL_MONTH").toString();
                    billdate[i] = obj1.getProperty("BILL_DT").toString();
                    StringTokenizer st1 = new StringTokenizer(billdate[i], "-");
                    billdate[i] = st1.nextToken();
                    billdate[i] = billdate[i] + " " + st1.nextToken();
                    billamount[i] = obj1.getProperty("CURRENT_MONTH_BILL").toString();
                    StringTokenizer st = new StringTokenizer(month[i], "-");
                    month[i] = st.nextToken();
                    billid[i] = Long.parseLong(obj1.getProperty("BILL_ID").toString());
                }
            }

        } catch (Exception e) {
            Log.e("manageAccounts", "Error: " + e.getMessage());

        }


    }

}
