package com.essel.smartutilities.activity;

import android.app.DownloadManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.essel.smartutilities.R;

public class BillHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView download1, download2, download3, download4, download5, download6, imgBack;
    DownloadManager downloadManager;
    Uri image_uri, music_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_history);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("My Bill");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intialize();
        image_uri = Uri.parse("http://192.168.10.114:8000/sitemedia/Tips-Images/4.png");
        music_uri = Uri.parse("http://www.androidtutorialpoint.com/wp-content/uploads/2016/09/AndroidDownloadManager.mp3");

    }

    private void intialize() {

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.download1:
                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                DownloadData(image_uri, download1);
                break;
            case R.id.download2:
                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
                DownloadData(music_uri, download2);
                break;
            case R.id.download3:
                Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();

                break;
            case R.id.download4:
                Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();

                break;
            case R.id.download5:
                Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();

                break;
            case R.id.download6:
                Toast.makeText(this, "6", Toast.LENGTH_SHORT).show();

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
        request.setDescription("Android Data download using DownloadManager.");

        //Set the local destination for the downloaded file to a path within the application's external files directory
        if (v.getId() == R.id.download1)
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "AndroidTutorialPoint.mp3");
        else if (v.getId() == R.id.download2)
            request.setDestinationInExternalFilesDir(this, Environment.DIRECTORY_DOWNLOADS, "AndroidTutorialPoint.jpg");

        //Enqueue download and save into referenceId
        downloadReference = downloadManager.enqueue(request);

//        Button DownloadStatus = (Button) findViewById(R.id.DownloadStatus);
//        DownloadStatus.setEnabled(true);
//        Button CancelDownload = (Button) findViewById(R.id.CancelDownload);
//        CancelDownload.setEnabled(true);

        return downloadReference;
    }
}
