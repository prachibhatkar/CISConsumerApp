package com.bynry.cisconsumerapp.activity;

import android.app.ProgressDialog;
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

import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.utility.CommonUtils;

import java.util.Arrays;

public class LandingSkipLoginActivity extends AppCompatActivity implements View.OnClickListener
{
    Button Submit;
    ImageView imgBack;
    LinearLayout action_about_us, action_tips, action_my_traiff, action_faq, action_contactus, action_share;
    static EditText consumerno;
    Spinner sp_city;
    private String TAG = "responsedataaaaa";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_skip_login);
        initialize();
    }

    private void initialize()
    {
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
        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });
        sp_city = (Spinner) findViewById(R.id.sp_city);
        String[] city = this.getResources().getStringArray(R.array.City);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Arrays.asList(city));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(dataAdapter);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_logout)
        {
            return true;
        }
        if (id == R.id.action_notifications)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validate()
    {
        Boolean flag = false;
        if (sp_city.getSelectedItemPosition()!=0)
        {
            if (consumerno.getText().toString().trim().length() != 0 && (consumerno.getText().toString().trim().length() >= 10
                    && consumerno.getText().toString().trim().length() <= 20))
            {
                flag = true;
            } else
                Toast.makeText(this, "Enter valid Consumer Number", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this, "Select valid City ", Toast.LENGTH_LONG).show();

        return flag;
    }


    private void initProgressDialog()
    {

        if (pDialog == null)
        {
            pDialog = new ProgressDialog(this);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog()
    {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View v)
    {
        Intent in;
        switch (v.getId())
        {
            case R.id.BTNSubmit:
                if (validate())
                {
                    if (CommonUtils.isNetworkAvaliable(this))
                    {

                        initProgressDialog();
                        if (pDialog != null && !pDialog.isShowing())
                        {
                            pDialog.setMessage(" please wait..");
                            pDialog.show();
                        }


                        }
                    else
                        Toast.makeText(this, " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();

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

    private void shareTextUrl()
    {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.EsselSmartUtilities.com");

        startActivity(Intent.createChooser(share, "Share link!"));
    }


    private class AsyncCallWS extends AsyncTask<Void, Void, Void>
    {


        @Override
        protected void onPreExecute()
        {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params)
        {
//            Log.i(TAG, "doInBackground");

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
//            Log.i(TAG, "onPostExecute");
//            Log.i(TAG, "response data: ");
            dismissDialog();
        }


    }


    @Override
    public void onBackPressed()
    {
        Intent in = new Intent(LandingSkipLoginActivity.this, LoginActivity.class);
        startActivity(in);
    }


}
