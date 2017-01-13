package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.SlidingImageAdapter;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.fragments.LoginDropDownFragment;
import com.essel.smartutilities.fragments.LoginLandingFragment;
import com.essel.smartutilities.models.BrandingImages;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.GetInfo;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.viewpagerindicator.CirclePageIndicator;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityLoginLanding extends AppCompatActivity implements View.OnClickListener, ServiceCaller {
    private TextView maintitle,subtitle;
    private ProgressDialog pDialog;
    private LinearLayout img, button, table;
    private static ViewPager mPager;
    private AdView mAdView;
    private static int currentPage = 0;
    private int NUM_PAGES = 0;
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private ArrayList<BrandingImages> ImagesArray1 = new ArrayList<BrandingImages>();
    private String TAG = "Landingscreennnnnnnnn";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_landing);
        callGetAccounts();
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        img = (LinearLayout) findViewById(R.id.linear_img);
        button = (LinearLayout) findViewById(R.id.linear_lay_button);
        table = (LinearLayout) findViewById(R.id.container);
        ImageView drop = (ImageView) findViewById(R.id.img_drowdown);
        drop.setOnClickListener(this);
        maintitle = (TextView) findViewById(R.id.title_bar);
         subtitle = (TextView) findViewById(R.id.subtitle_bar);
        setTitle();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button bill = (Button) findViewById(R.id.btn_mybill);
        bill.setOnClickListener(this);
        Button pay = (Button) findViewById(R.id.btn_paynow);
        pay.setOnClickListener(this);
        Fragment fragment = new LoginLandingFragment();
        FragmentManager fragmanager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

        Boolean flag = FeedBackActivity.getflag();
        Log.i("Tag", "valuelogin" + flag);
        if (flag)
        {
            Log.i("Tag", "valueinif" + flag);

            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Thanks for your valuable feedback", Snackbar.LENGTH_LONG);
            View view = snack.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
            snack.setActionTextColor(Color.WHITE);
            FeedBackActivity.flag = false;

        }


    }

    public void setTitle()
    {
        ArrayList<Consumer> consumers = DatabaseManager.getAllManageAccounts(this);
        int i=0;
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO).isEmpty())
            if (!consumers.isEmpty() && consumers.size() != 0) {
                for (Consumer con : consumers)
                    if (con.consumer_no.equalsIgnoreCase(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO))) {
                        maintitle.setText(con.consumer_no);
                        subtitle.setText(con.consumer_name);
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NO, con.consumer_no);
                        SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_NAME, con.consumer_name);
                        i = 1;
                    }
            }else
            { maintitle.setText(SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NO));

                subtitle.setText(SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NAME));
                i=1;
            }
        if(i==0)
        {
            GetInfo get = new GetInfo();
            get = DatabaseManager.getProfileinfo(this,"true");
            SharedPrefManager.saveValue(this,SharedPrefManager.CONSUMER_NO,get.consumerno);
            SharedPrefManager.saveValue(this,SharedPrefManager.CONSUMER_NAME,get.consumername);
            maintitle.setText(get.consumerno);
            subtitle.setText(get.consumername);
        }

        maintitle.setOnClickListener(this);
//        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME).isEmpty())
//            subtitle.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME));
        subtitle.setOnClickListener(this);
    }
    public void callGetAccounts()
    {
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            JsonObjectRequest request = WebRequests.getAccounts(this, Request.Method.GET, AppConstants.URL_GET_ACCOUNTS, AppConstants.REQUEST_GET_ACCOUNTS, this, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_ACCOUNTS);

            JsonObjectRequest request2 = WebRequests.getBrandingImages(this, Request.Method.GET, AppConstants.URL_BRANDING_IMAGES, AppConstants.REQUEST_BRANDING_IMAGES, this);
            App.getInstance().addToRequestQueue(request2, AppConstants.REQUEST_BRANDING_IMAGES);

        } else
            Toast.makeText(this.getApplicationContext(), R.string.error_internet_not_connected, Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("You Want to Logout");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to continue!")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            onClickDialog();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();


        } else if (id == R.id.action_notifications)
        {
            Intent in = new Intent(this, NotificationActivity.class);
            startActivity(in);
        }
        return true;
    }


    private void onClickDialog()
    {

        if (CommonUtils.isNetworkAvaliable(this))
        {

            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing())
            {
                pDialog.setMessage(" please wait..");
                pDialog.show();
            }
            JsonObjectRequest request = WebRequests.getLogOut(this, Request.Method.GET, AppConstants.URL_LOGOUT, AppConstants.REQUEST_LOGOUT, this, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_LOGOUT);
        } else
        {
            Toast.makeText(this.getApplicationContext(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();

        }

    }

    private void initProgressDialog()
    {

        if (pDialog == null) {
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
        switch (v.getId())
        {
            case R.id.btn_mybill:
                if (CommonUtils.isNetworkAvaliable(this))
                {
                    Intent i = new Intent(this, MyBillActivity.class);
                    startActivity(i);
                } else
                    Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();

                break;
            case R.id.btn_paynow:
                callPayNow();
                break;
            case R.id.img_drowdown:
                callDropDown();
                break;
            case R.id.subtitle_bar:
                callDropDown();
                break;
            case R.id.title_bar:
                callDropDown();
                break;
        }
    }

    private void callPayNow()
    {
        if (CommonUtils.isNetworkAvaliable(this))
        {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            AsyncCallWS task = new AsyncCallWS();
            task.execute();
        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    void callDropDown()
    {
        if (App.getdropdown())
        {
            table.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            img.setVisibility(View.GONE);
            Fragment fragment = new LoginDropDownFragment();
            FragmentManager fragmanager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
            fragmentTransaction.replace(R.id.big_container, fragment);
            fragmentTransaction.commit();
            App.dropdown = false;
        } else
        {
            table.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            if (getSupportFragmentManager().findFragmentById(R.id.big_container) != null) {
                getSupportFragmentManager()
                        .beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.big_container)).commit();
                App.dropdown = true;
            }
        }
    }


    public void onBackPressed()
    {
        if (App.dropdown == false)
        {
            table.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            if (getSupportFragmentManager().findFragmentById(R.id.big_container) != null) {
                getSupportFragmentManager()
                        .beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.big_container)).commit();
            } else
            {
                DialogCreator.showExitDialog(this, "Exit App?", "Do you want to exit?");
            }
            App.dropdown = true;
        } else
        {
            DialogCreator.showExitDialog(this, "Exit App?", "Do you want to exit?");
        }
        dismissDialog();

    }


    private void init()
    {
        NUM_PAGES = ImagesArray1.size();
        for (int i = 0; i < NUM_PAGES; i++)
            ImagesArray.add(ImagesArray1.get(i).image);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImageAdapter(ActivityLoginLanding.this, ImagesArray));

        mPager.setPageTransformer(true, new CubeOutTransformer());
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);


        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(4 * density);

        final android.os.Handler handler = new android.os.Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 6000, 6000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {

            @Override
            public void onPageSelected(int position)
            {
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2)
            {
                currentPage = pos;

            }

            @Override
            public void onPageScrollStateChanged(int pos)
            {
            }
        });
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
            Log.i(TAG, "doInBackground");
            getBillDetails();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i(TAG, "onPostExecute");
            Log.i(TAG, "response data: ");
            dismissDialog();
        }
    }

    public void getBillDetails()
    {

        String getconsumerno = SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO);
        String SOAP_ACTION = "http://123.63.20.164:8001/soa-infra/services/Maharashtra/EsselCCBGetBillDetails!1.0*soa_8b795420-6bdd-4416-aa61-cf0cec7e5698/EsselCCBGetBillSvc";
        String METHOD_NAME = "InputParameters";
        String NAMESPACE = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/CCBGetBillDetailsProc";
        String URL = "http://123.63.20.164:8001/soa-infra/services/Maharashtra/EsselCCBGetBillDetails!1.0*soa_8b795420-6bdd-4416-aa61-cf0cec7e5698/EsselCCBGetBillSvc";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            if (getconsumerno.length() == 10)
            {
                Request.addProperty("P_ACCT_ID", getconsumerno);
                Request.addProperty("P_BILL_ID", "");
                Request.addProperty("P_MTR_ID", "#E-NG");
            } else if (getconsumerno.length() == 12)
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
            String duedate = ((SoapObject) responceArray.getProperty(0)).getProperty("DUE_DT_CASH").toString();
            String currentamt = ((SoapObject) responceArray.getProperty(0)).getProperty("CURR_BILL_AMT").toString();
            String promptamt = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE8").toString();
            String netbill = ((SoapObject) responceArray.getProperty(0)).getProperty("NET_BILL_PAYABLE").toString();
            String arrears = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE20").toString();
            String consumername = ((SoapObject) responceArray.getProperty(0)).getProperty("CONSUMER_NAME").toString();
            String accid = ((SoapObject) responceArray.getProperty(0)).getProperty("ACCT_ID").toString();
            String promptdate = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE19").toString();
            dismissDialog();
            Intent in = new Intent(this, PayNowActivity.class);
            in.putExtra("date", duedate);
            in.putExtra("amt", currentamt);
            in.putExtra("promtamt", promptamt);
            in.putExtra("netbill", netbill);
            in.putExtra("arrears", arrears);
            in.putExtra("consumername", consumername);
            in.putExtra("accid", accid);
            in.putExtra("promtdate", promptdate);
            startActivity(in);

        } catch (Exception e)
        {
            Log.e(TAG, "Error: " + e.getMessage());

        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (CommonUtils.isNetworkAvaliable(this))
        {
            JsonObjectRequest request = WebRequests.getAccounts(this, Request.Method.GET, AppConstants.URL_GET_ACCOUNTS, AppConstants.REQUEST_GET_ACCOUNTS, this, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_ACCOUNTS);
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing())
            {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
        } else
            Toast.makeText(this.getApplicationContext(), R.string.error_internet_not_connected, Toast.LENGTH_SHORT).show();
        setTitle();

    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {
        switch (label) {
            case AppConstants.REQUEST_LOGOUT:
            {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        if (jsonResponse.message != null)
                        {
                            SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_LOGGED, "false");
                            SharedPrefManager.saveValue(this, SharedPrefManager.AUTH_TOKEN, "no");

                            dismissDialog();
                            Intent in = new Intent(this, LoginActivity.class);
                            startActivity(in);
                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.message);
                        }
                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();
                        dismissDialog();
                    }
                    break;
                }

                dismissDialog();
            }

            case AppConstants.REQUEST_GET_ACCOUNTS:
            {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {

                        if (jsonResponse.consumers != null && jsonResponse.consumers.size() > 0) {
                            Collections.reverse(jsonResponse.consumers);
                            DatabaseManager.saveManageAccounts(this, jsonResponse.consumers);

                            dismissDialog();
                        }
                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();
                        dismissDialog();
                    }
                    break;
                }

                dismissDialog();
            }
            break;
            case AppConstants.REQUEST_BRANDING_IMAGES:
            {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {

                        if (jsonResponse.images != null && jsonResponse.images.size() >= 0) {
                            ImagesArray1 = jsonResponse.images;
                            init();
                            dismissDialog();
                            Log.i(label, "Imagesssssssssssssssssssssssss:" + jsonResponse.result);
                        }

                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();
                        Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();
                    }
                    break;
                }

            }
        }
    }


    public void onAsyncFail(String message, String label, NetworkResponse response)
    {

        switch (label)
        {
            case AppConstants.REQUEST_LOGOUT:
            {
                Log.i(label, "REQUEST_LOGOUT " + response);
                dismissDialog();
            }
            break;
            case AppConstants.REQUEST_GET_ACCOUNTS:
            {
                Log.i(label, AppConstants.REQUEST_GET_ACCOUNTS + message);
                Log.i(label, AppConstants.REQUEST_GET_ACCOUNTS + response);
                dismissDialog();
            }
            break;
            case AppConstants.REQUEST_BRANDING_IMAGES:
            {
                Log.i(label, AppConstants.REQUEST_BRANDING_IMAGES + message);
                Log.i(label, AppConstants.REQUEST_BRANDING_IMAGES + response);
                dismissDialog();
            }
            break;
        }


    }
}