package com.essel.smartutilities.activity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;

public class BillHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView download1, download2, download3, download4, download5, download6, imgBack;
    DownloadManager downloadManager;
    Uri image_uri;
    String base;
    TableRow[] tbl=new TableRow[6];
    TextView month1, date1, amt1, consum1, month2, date2, amt2, consum2, month3, date3, amt3, consum3, month4, date4, amt4, consum4,
            month5, date5, amt5, consum5, month6, date6, amt6, consum6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);
        base="http://cpnagpur.sndl.in:86/getbillall.php?billnumber=";
        intialize();
        image_uri = Uri.parse(base);


    }

    private void intialize() {

        tbl[0]=(TableRow)findViewById(R.id.tbl0);
        tbl[1]=(TableRow)findViewById(R.id.tbl1);
        tbl[2]=(TableRow)findViewById(R.id.tbl2);
        tbl[3]=(TableRow)findViewById(R.id.tbl3);
        tbl[4]=(TableRow)findViewById(R.id.tbl4);
        tbl[5]=(TableRow)findViewById(R.id.tbl5);

            for(int i=0;i<MyBillActivity.month.length;i++)
                if(MyBillActivity.month[i]!=null)
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
        amt1.setText(MyBillActivity.billamount[0]);
        consum1.setText(String.valueOf(MyBillActivity.consum[0]));


        date2 = (TextView) findViewById(R.id.date2);
        amt2 = (TextView) findViewById(R.id.amount2);
        consum2 = (TextView) findViewById(R.id.units2);
        month2 = (TextView) findViewById(R.id.month2);
        month2.setText(MyBillActivity.month[1]);
        date2.setText(MyBillActivity.billdate[1]);
        amt2.setText(MyBillActivity.billamount[1]);
        consum2.setText(String.valueOf(MyBillActivity.consum[1]));

        date3 = (TextView) findViewById(R.id.date3);
        amt3 = (TextView) findViewById(R.id.amount3);
        consum3 = (TextView) findViewById(R.id.units3);
        month3 = (TextView) findViewById(R.id.month3);
        month3.setText(MyBillActivity.month[2]);
        date3.setText(MyBillActivity.billdate[2]);
        amt3.setText(MyBillActivity.billamount[2]);
        consum3.setText(String.valueOf(MyBillActivity.consum[2]));


        date4 = (TextView) findViewById(R.id.date4);
        amt4 = (TextView) findViewById(R.id.amount4);
        consum4 = (TextView) findViewById(R.id.units4);
        month4 = (TextView) findViewById(R.id.month4);
        month4.setText(MyBillActivity.month[3]);
        date4.setText(MyBillActivity.billdate[3]);
        amt4.setText(MyBillActivity.billamount[3]);
        consum4.setText(String.valueOf(MyBillActivity.consum[3]));

        date5 = (TextView) findViewById(R.id.date5);
        amt5 = (TextView) findViewById(R.id.amount5);
        consum5 = (TextView) findViewById(R.id.units5);
        month5 = (TextView) findViewById(R.id.month5);
        month5.setText(MyBillActivity.month[0]);
        date5.setText(MyBillActivity.billdate[0]);
        amt5.setText(MyBillActivity.billamount[0]);
        consum5.setText(String.valueOf(MyBillActivity.consum[0]));

        date6 = (TextView) findViewById(R.id.date6);
        amt6 = (TextView) findViewById(R.id.amount6);
        consum6 = (TextView) findViewById(R.id.units6);
        month6 = (TextView) findViewById(R.id.month6);
        month6.setText(MyBillActivity.month[0]);
        date6.setText(MyBillActivity.billdate[0]);
        amt6.setText(MyBillActivity.billamount[0]);
        consum6.setText(String.valueOf(MyBillActivity.consum[0]));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.download1:
                image_uri = Uri.parse(base +String.valueOf(MyBillActivity.billid[0]));
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                DownloadData(image_uri, download1);
                break;
            case R.id.download2:
                image_uri = Uri.parse(base + MyBillActivity.billid[1]);
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                DownloadData(image_uri, download2);
                break;
            case R.id.download3:
                image_uri = Uri.parse(base + MyBillActivity.billid[2]);
                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();
                DownloadData(image_uri, download2);
                break;
            case R.id.download4:
                image_uri = Uri.parse(base + MyBillActivity.billid[3]);
                Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();
                DownloadData(image_uri, download2);
                break;
            case R.id.download5:
                image_uri = Uri.parse(base + MyBillActivity.billid[4]);
                Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();
                DownloadData(image_uri, download2);
                break;
            case R.id.download6:
                image_uri = Uri.parse(base + MyBillActivity.billid[5]);
                Toast.makeText(this, "6", Toast.LENGTH_SHORT).show();
                DownloadData(image_uri, download2);
                break;

        }

    }

    private long DownloadData(Uri uri, View v) {

        long downloadReference;

        // Create request for android download manager
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //Setting title of request
        request.setTitle("Data Download");

        //Setting description of request
        request.setDescription(" Data download From Essel.");

        //Set the local destination for the downloaded file to a path within the application's external files directory
        if (v.getId() == R.id.download1)
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "EsselDownloadedData.html");
        else if (v.getId() == R.id.download2)
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "EsselDownloadedData.html");

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

//        Button DownloadStatus = (Button) findViewById(R.id.DownloadStatus);
//        DownloadStatus.setEnabled(true);
//        Button CancelDownload = (Button) findViewById(R.id.CancelDownload);
//        CancelDownload.setEnabled(true);

        return downloadReference;
    }
//    public void ConvertWebPageToPDF()
//    {
//        // note: be sure to copy the helper function ConvertHTMLStringToPDF() from this webpage
//
//        // a url starting with http or an HTML string
//        String value = "http://www.google.com";
//        String apiKey = "d733ba16-8d32-4efb-9b51-7fc6baba4643";
//        String apiURL = "http://api.html2pdfrocket.com/pdf";
//        HashMap<String,String> params = new HashMap<String, String>();
//        params.put("apiKey", apiKey);
//        params.put("value", value);
//
//
//        // Call the API convert to a PDF
//        InputStreamReader request = new InputStreamReader(Request.Method.POST, apiURL, new Response.Listener<byte[]>(){
//            @Override
//            public void onResponse(byte[] response)
//            {
//                try
//                {
//                    if(response != null)
//                    {
//                        File localFolder = new File(Environment.getExternalStorageDirectory(), "WebPageToPDF");
//                        if(!localFolder.exists())
//                        {
//                            localFolder.mkdirs();
//                        }
//
//                        // Write stream output to local file
//                        File pdfFile =  new File (localFolder, "MySamplePDFFile.pdf");
//                        OutputStream opStream = new FileOutputStream(pdfFile);
//                        pdfFile.setWritable(true);
//                        opStream.write(response);
//                        opStream.flush();
//                        op.close();
//                    }
//                } catch (Exception ex)
//                {
//                    Toast.makeText(this, "Error while generating PDF file!!", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
//    }
}
