package com.essel.smartutilities.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class BillHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView download1, download2, download3, download4, download5, download6, imgBack;
    DownloadManager downloadManager;
    Uri image_uri;
    String strApiKey;
    String fileName;
    File root;
    ProgressDialog pDialog;
    String strValue;
    String webUrl = null;
    File gpxfile = null;
    String base;
    TableRow[] tbl = new TableRow[6];
    TextView month1, date1, amt1, consum1, month2, date2, amt2, consum2, month3, date3, amt3, consum3, month4, date4, amt4, consum4,
            month5, date5, amt5, consum5, month6, date6, amt6, consum6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);
        base = "http://cpnagpur.sndl.in:86/getbillall.php?billnumber=";
        intialize();
        image_uri = Uri.parse(base);


    }

    private void intialize() {

        tbl[0] = (TableRow) findViewById(R.id.tbl0);
        tbl[1] = (TableRow) findViewById(R.id.tbl1);
        tbl[2] = (TableRow) findViewById(R.id.tbl2);
        tbl[3] = (TableRow) findViewById(R.id.tbl3);
        tbl[4] = (TableRow) findViewById(R.id.tbl4);
        tbl[5] = (TableRow) findViewById(R.id.tbl5);

        for (int i = 0; i < MyBillActivity.month.length; i++)
            if (MyBillActivity.month[i] != null)
                tbl[i].setVisibility(View.VISIBLE);


        download1 = (ImageView) findViewById(R.id.download1);
        download1.setOnClickListener(this);
        download2 = (ImageView) findViewById(R.id.download2);
        download2.setOnClickListener(this);
        download3 = (ImageView) findViewById(R.id.download3);
        download3.setOnClickListener(this);
        download4 = (ImageView) findViewById(R.id.download4);
        download4.setOnClickListener(this);
        download5 = (ImageView) findViewById(R.id.download5);
        download5.setOnClickListener(this);
        download6 = (ImageView) findViewById(R.id.download6);
        download6.setOnClickListener(this);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setvalues();
    }

    private void setvalues() {
        month1 = (TextView) findViewById(R.id.month1);
        date1 = (TextView) findViewById(R.id.date1);
        amt1 = (TextView) findViewById(R.id.amount1);
        consum1 = (TextView) findViewById(R.id.units1);
        month1.setText(MyBillActivity.month[0]);
        date1.setText(MyBillActivity.billdate[0]);
        amt1.setText("Rs " + MyBillActivity.billamount[0]);
        consum1.setText(String.valueOf(MyBillActivity.consum[0]));


        date2 = (TextView) findViewById(R.id.date2);
        amt2 = (TextView) findViewById(R.id.amount2);
        consum2 = (TextView) findViewById(R.id.units2);
        month2 = (TextView) findViewById(R.id.month2);
        month2.setText(MyBillActivity.month[1]);
        date2.setText(MyBillActivity.billdate[1]);
        amt2.setText("Rs " + MyBillActivity.billamount[1]);
        consum2.setText(String.valueOf(MyBillActivity.consum[1]));

        date3 = (TextView) findViewById(R.id.date3);
        amt3 = (TextView) findViewById(R.id.amount3);
        consum3 = (TextView) findViewById(R.id.units3);
        month3 = (TextView) findViewById(R.id.month3);
        month3.setText(MyBillActivity.month[2]);
        date3.setText(MyBillActivity.billdate[2]);
        amt3.setText("Rs " + MyBillActivity.billamount[2]);
        consum3.setText(String.valueOf(MyBillActivity.consum[2]));


        date4 = (TextView) findViewById(R.id.date4);
        amt4 = (TextView) findViewById(R.id.amount4);
        consum4 = (TextView) findViewById(R.id.units4);
        month4 = (TextView) findViewById(R.id.month4);
        month4.setText(MyBillActivity.month[3]);
        date4.setText(MyBillActivity.billdate[3]);
        amt4.setText("Rs " + MyBillActivity.billamount[3]);
        consum4.setText(String.valueOf(MyBillActivity.consum[3]));

        date5 = (TextView) findViewById(R.id.date5);
        amt5 = (TextView) findViewById(R.id.amount5);
        consum5 = (TextView) findViewById(R.id.units5);
        month5 = (TextView) findViewById(R.id.month5);
        month5.setText(MyBillActivity.month[4]);
        date5.setText(MyBillActivity.billdate[4]);
        amt5.setText("Rs " + MyBillActivity.billamount[4]);
        consum5.setText(String.valueOf(MyBillActivity.consum[4]));

        date6 = (TextView) findViewById(R.id.date6);
        amt6 = (TextView) findViewById(R.id.amount6);
        consum6 = (TextView) findViewById(R.id.units6);
        month6 = (TextView) findViewById(R.id.month6);
        month6.setText(MyBillActivity.month[5]);
        date6.setText(MyBillActivity.billdate[5]);
        amt6.setText("Rs " + MyBillActivity.billamount[5]);
        consum6.setText(String.valueOf(MyBillActivity.consum[5]));


    }

    @Override
    public void onClick(View view) {
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Downloding, please wait..");
                pDialog.show();
            }
            switch (view.getId()) {
                case R.id.download1:
                    fileName = "MYbill" + "(" + SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[0] + ".pdf";
                    getPdf(String.valueOf(MyBillActivity.billid[0]), fileName);
                    break;
                case R.id.download2:
                    fileName = "MYbill" + "(" + SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[1] + ".pdf";
                    getPdf(String.valueOf(MyBillActivity.billid[1]), fileName);
                    break;
                case R.id.download3:
                    fileName = "MYbill" + "(" + SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[2] + ".pdf";
                    getPdf(String.valueOf(MyBillActivity.billid[2]), fileName);
                    break;
                case R.id.download4:
                    fileName = "MYbill" + "(" + SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[3] + ".pdf";
                    getPdf(String.valueOf(MyBillActivity.billid[3]), fileName);
                    break;
                case R.id.download5:
                    fileName = "MYbill" + "(" + SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[4] + ".pdf";
                    getPdf(String.valueOf(MyBillActivity.billid[4]), fileName);
                    break;
                case R.id.download6:
                    fileName = "MYbill" + "(" + SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[5] + ".pdf";
                    getPdf(String.valueOf(MyBillActivity.billid[5]), fileName);
                    break;
            }
        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();

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

    public void getPdf(String ss, final String dd) {
        strApiKey = "d733ba16-8d32-4efb-9b51-7fc6baba4643";//Web api Parameter
        strValue = base + ss; //Web api Parameter

        webUrl = "http://api.html2pdfrocket.com/pdf";//Web Api Url
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("apiKey", strApiKey);
        params.put("value", strValue);

        webUrl = "http://api.html2pdfrocket.com/pdf";
        String mUrl = webUrl;
        InputStreamReader request = new InputStreamReader(Request.Method.POST, webUrl,
                new Response.Listener<byte[]>() {
                    @Override
                    public void onResponse(byte[] response) {
                        // TODO handle the response
                        try {
                            if (response != null) {
//                                Random r = new Random();
//                                int i1 = r.nextInt(80 - 65) + 65;
                                fileName=dd;
                                root = new File(Environment.getExternalStorageDirectory(), "Essel Bills");
                                if (!root.exists()) {
                                    root.mkdirs();
                                }
                                if (root.exists()) {

                                    if (gpxfile == null || !gpxfile.exists()) {
                                        gpxfile = new File(root, dd);
                                        OutputStream op = new FileOutputStream(gpxfile);
                                        gpxfile.setWritable(true);
                                        op.write(response);
                                        op.flush();
                                        op.close();
                                    } else {

                                        if (gpxfile.exists()) {
                                            OutputStream op = new FileOutputStream(gpxfile, true);
                                            op.write(response);
                                            op.flush();
                                            op.close();
                                        }
                                    }
                                }
                                dismissDialog();
                                Toast.makeText(getBaseContext(), "File Save in Essel Folder", Toast.LENGTH_LONG).show();
                                System.out.print("Response ----------------------" + response.toString());

                            }
                        } catch (Exception e) {
                            Log.d("KEY_ERROR", "UNABLE TO DOWNLOAD FILE");
                            Toast.makeText(BillHistoryActivity.this, "KEY_ERROR UNABLE TO DOWNLOAD FILE", Toast.LENGTH_LONG).show();
                            dismissDialog();
                            e.printStackTrace();
                        }
                    }
                }
                , new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BillHistoryActivity.this, "Error", Toast.LENGTH_LONG).show();
                dismissDialog();
                error.printStackTrace();
            }
        }, params);
        request.setRetryPolicy(new DefaultRetryPolicy(
                5000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext(), new HurlStack());
        mRequestQueue.add(request);
    }
}


class InputStreamReader extends Request<byte[]> {
    private final Response.Listener<byte[]> mListener;
    private Map<String, String> mParams;
    public Map<String, String> responseHeaders;

    public InputStreamReader(int method, String mUrl, Response.Listener<byte[]> listener,
                             Response.ErrorListener errorListener, HashMap<String, String> params) {
        // TODO Auto-generated constructor stub

        super(method, mUrl, errorListener);
        //super(post,mUrl,errorListener);
        // this request would never use cache.
        setShouldCache(false);
        mListener = listener;
        mParams = params;
    }

    @Override
    protected Map<String, String> getParams()
            throws com.android.volley.AuthFailureError {
        return mParams;
    }

    ;

    @Override
    protected void deliverResponse(byte[] response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        //Initialise local responseHeaders map with response headers received
        responseHeaders = response.headers;

        //Pass the response data here
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}