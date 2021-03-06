package com.bynry.cisconsumerapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bynry.cisconsumerapp.models.AboutUs;
import com.bynry.cisconsumerapp.utility.App;
import com.bynry.cisconsumerapp.utility.CommonUtils;
import com.bynry.cisconsumerapp.webservice.WebRequests;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.callers.ServiceCaller;
import com.bynry.cisconsumerapp.db.DatabaseManager;
import com.bynry.cisconsumerapp.models.JsonResponse;
import com.bynry.cisconsumerapp.utility.AppConstants;

public class AboutUsActivity extends AppCompatActivity implements ServiceCaller {

    TextView tv_aboutus_message;
    private Context mContext;
    ProgressDialog pDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        tv_aboutus_message = (TextView) findViewById(R.id.textview_about_us);
        AboutUs aboutUs = new AboutUs();


        if (isNetworkAvailable())
        {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing())
            {
                pDialog.setMessage(" please wait..");
                pDialog.show();
            }

            JsonObjectRequest request = WebRequests.getAboutUs(this, Request.Method.GET, AppConstants.URL_GET_ABOUT_US, AppConstants.REQEST_ABOUT_US, this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQEST_ABOUT_US);

        } else
        {

            Toast.makeText(this.getApplicationContext(), "check internet connection", Toast.LENGTH_SHORT).show();
            AboutUs aboutUs2 = new AboutUs();
            aboutUs2 = DatabaseManager.getAboutUs(this);
            if (aboutUs2.about_us_msg != null) {
                tv_aboutus_message.setText(aboutUs2.about_us_msg.toString().trim());
                Log.i("Tag", "valueseaboutmsg" + aboutUs2.about_us_msg);

            } else
                Toast.makeText(this.getApplicationContext(), R.string.error_internet_not_connected, Toast.LENGTH_SHORT).show();


        }


    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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


    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {
        switch (label)
        {
            case AppConstants.REQEST_ABOUT_US:
            {
                if (jsonResponse != null)
                {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS))
                    {
//
                        Log.i(label, "hygt " + jsonResponse);
                        Log.i(label, "hyif " + jsonResponse.aboutus);
                        if (jsonResponse.aboutus == null)
                        {
                            dismissDialog();

                        }
                        if (jsonResponse.aboutus != null)
                        {
                            dismissDialog();
                            tv_aboutus_message.setText(jsonResponse.aboutus.toString().trim());
                            DatabaseManager.saveAboutUs(this, jsonResponse.aboutus);

                        }

                        if (jsonResponse.authorization != null)
                        {
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
                            dismissDialog();
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE))
                    {
                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                    }
                    break;
                }
                dismissDialog();
            }

        }
    }


    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response)
    {

        switch (label)
        {
            case AppConstants.REQEST_ABOUT_US:
            {
                Log.i(label, " " + message);
                Log.i(label, " " + response);
            }
            pDialog.dismiss();
            break;
        }

    }

}



