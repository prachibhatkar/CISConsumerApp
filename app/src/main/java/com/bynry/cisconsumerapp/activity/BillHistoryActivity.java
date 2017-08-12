package com.bynry.cisconsumerapp.activity;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.bynry.cisconsumerapp.utility.CommonUtils;
import com.bynry.cisconsumerapp.utility.SharedPrefManager;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.utility.AppConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BillHistoryActivity extends Activity implements View.OnClickListener {
    ImageView download1, download2, download3, download4, download5, download6, imgBack;
    DownloadManager downloadManager;
    Uri image_uri;
    String fileName;
    ProgressDialog pDialog;
    String base;
    public static Context con;
    TableRow[] tbl = new TableRow[6];
    TextView month1, date1, amt1, consum1, month2, date2, amt2, consum2, month3, date3, amt3, consum3, month4, date4, amt4, consum4,
            month5, date5, amt5, consum5, month6, date6, amt6, consum6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);
//        base = "http://192.168.0.5:8000/mobileapi/get-bill-pdf/?";
        base = AppConstants.bill + "city=" + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_CITY);
        intialize();
        image_uri = Uri.parse(base);
        con=this;
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
    public void onBackPressed() {
        super.onBackPressed();
        dismissDialog();
    }

    @Override
    public void onClick(View view) {
        if (CommonUtils.isNetworkAvaliable(this))
        {  /* long freeBytesInternal = new File(BillHistoryActivity.con.getFilesDir().getAbsoluteFile().toString()).getFreeSpace();
            long freeBytesExternal = new File(getExternalFilesDir(null).toString()).getFreeSpace();*/
//            if(freeBytesInternal>1000)
//            {
                initProgressDialog();
                if (pDialog != null && !pDialog.isShowing())
                {   pDialog.setMessage("Downloding, please wait..");
                    pDialog.show();
                }
                String b;
                switch (view.getId())
                {
                case R.id.download1:
                    fileName = "MY Bill" + "(" + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[0] + ".pdf";
                     b=base+"&&bill_id=" + MyBillActivity.billid[0];
                    new DownloadFile().execute(b, fileName);
                    break;
                case R.id.download2:
                    fileName = "MY Bill" + "(" + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[1] + ".pdf";
                     b=base+"&&bill_id=" + MyBillActivity.billid[1];
                    new DownloadFile().execute(b, fileName);
                    break;
                case R.id.download3:
                    fileName = "MY Bill" + "(" + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[2] + ".pdf";
                    b=base+"&&bill_id=" + MyBillActivity.billid[2];
                    new DownloadFile().execute(b, fileName);
                    break;
                case R.id.download4:
                    fileName = "MY Bill" + "(" + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[3] + ".pdf";
                    b=base+"&&bill_id=" + MyBillActivity.billid[3];
                    new DownloadFile().execute(b, fileName);
                    break;
                case R.id.download5:
                    fileName = "MY Bill" + "(" + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[4] + ".pdf";
                    b=base+"&&bill_id=" + MyBillActivity.billid[4];
                    new DownloadFile().execute(b, fileName);
                    break;
                case R.id.download6:
                    fileName = "MY Bill" + "(" + SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO) + ")" + MyBillActivity.month[5] + ".pdf";
                    b=base+"&&bill_id=" + MyBillActivity.billid[5];
                    new DownloadFile().execute(b, fileName);
                    break;
                }
//            }else
//                Toast.makeText(this, "Please Make Sure You Have Free Space To Save Downloaded Bills", Toast.LENGTH_LONG).show();

        }   else
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

    class DownloadFile extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute()
        {
            Log.i("Bill History", "onPreExecute");
        }



        @Override
        protected void onPostExecute(Void result)
        {   /*Log.i("Bill History", "onPostExecute");*/
//            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/Essel bills/" + fileName);  // -> filename = maven.pdf
//            Uri path = Uri.fromFile(pdfFile);
//            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//            pdfIntent.setDataAndType(path, "application/pdf");
//            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            try{
//                startActivity(pdfIntent);
//            }catch(Exception e){
//                e.printStackTrace();
//                Toast.makeText(BillHistoryActivity.con, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
//            }
            dismissDialog();
            Toast.makeText(BillHistoryActivity.con,"Bill is Saved in "+" Essel bills "+" Folder",Toast.LENGTH_LONG).show();

        }
        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];
            String fileName = strings[1];

            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "Essel Bills ");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }



}
 class FileDownloader {
    private static final int  MEGABYTE = 1024 * 1024;

    public static void downloadFile(String fileUrl, File directory){
        try {

            URL url = new URL(fileUrl);
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(directory);
            int totalSize = urlConnection.getContentLength();

            byte[] buffer = new byte[MEGABYTE];
            int bufferLength = 0;
            while((bufferLength = inputStream.read(buffer))>0 ){
                fileOutputStream.write(buffer, 0, bufferLength);
            }
            fileOutputStream.close();



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}