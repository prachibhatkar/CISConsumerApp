package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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
import com.eftimoff.viewpagertransformers.AccordionTransformer;
import com.eftimoff.viewpagertransformers.CubeInTransformer;
import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.eftimoff.viewpagertransformers.DrawFromBackTransformer;
import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.DepthPageTransform;
import com.essel.smartutilities.adapter.SlidingImageAdapter;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.fragments.LoginDropDownFragment;
import com.essel.smartutilities.fragments.LoginLandingFragment;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class ActivityLoginLanding extends AppCompatActivity implements View.OnClickListener, ServiceCaller {
    TextView maintitle;
    ProgressDialog pDialog;
    LinearLayout img, button, table;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.esselgroup, R.drawable.logo,
            R.drawable.infrastruc};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_landing);
        init();
        img = (LinearLayout) findViewById(R.id.linear_img);
        button = (LinearLayout) findViewById(R.id.linear_lay_button);
        table = (LinearLayout) findViewById(R.id.container);
        ImageView drop = (ImageView) findViewById(R.id.img_drowdown);
        drop.setOnClickListener(this);
        maintitle = (TextView) findViewById(R.id.title_bar);
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO).isEmpty())
            maintitle.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
        maintitle.setOnClickListener(this);
        TextView subtitle = (TextView) findViewById(R.id.subtitle_bar);
        if (!SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME).isEmpty())
            subtitle.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME));
        subtitle.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button bill = (Button) findViewById(R.id.btn_mybill);
        bill.setOnClickListener(this);
        Button pay = (Button) findViewById(R.id.btn_paynow);
        pay.setOnClickListener(this);
        // toolbar.setOnMenuItemClickListener(ActionBar.DISPLAY_SHOW_HOME);
        Fragment fragment = new LoginLandingFragment();
        FragmentManager fragmanager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

        Boolean flag = FeedBackActivity.getflag();
        Log.i("Tag", "valuelogin" + flag);
        if (flag) {
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

        if (CommonUtils.isNetworkAvaliable(this)) {
            JsonObjectRequest request = WebRequests.getAccounts(this, Request.Method.GET, AppConstants.URL_GET_ACCOUNTS, AppConstants.REQUEST_GET_ACCOUNTS, this, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_ACCOUNTS);
        } else
            Toast.makeText(this.getApplicationContext(), R.string.error_internet_not_connected, Toast.LENGTH_SHORT).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("You Want to Logout");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Click yes to continue!")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {

                             onClickDialog();

                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
             AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

    }
    return true;
}

        //  DialogCreator.showLogoutDialog(this, "Logout", "Are you sure you want to logout?");
           // return true;


     private void onClickDialog(){

         if (CommonUtils.isNetworkAvaliable(this)) {

             initProgressDialog();
             if (pDialog != null && !pDialog.isShowing()) {
                 pDialog.setMessage(" please wait..");
                 pDialog.show();
             }
             JsonObjectRequest request = WebRequests.getLogOut(this, Request.Method.GET, AppConstants.URL_LOGOUT, AppConstants.REQUEST_LOGOUT, this, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
             App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_LOGOUT);
         } else {
             Toast.makeText(this.getApplicationContext(), " Please Connection Internet ", Toast.LENGTH_SHORT).show();

         }



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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mybill:
                Intent i = new Intent(this, MyBillActivity.class);
                startActivity(i);
                break;
            case R.id.btn_paynow:
                Intent in = new Intent(this, PayNowActivity.class);
                startActivity(in);
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

    void callDropDown() {
        if (App.getdropdown()) {
            table.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            img.setVisibility(View.GONE);
            Fragment fragment = new LoginDropDownFragment();
            FragmentManager fragmanager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
            fragmentTransaction.replace(R.id.big_container, fragment);
            fragmentTransaction.commit();
            App.dropdown = false;
        } else {
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

    /*public static boolean snackBarMethod() {
    /* Boolean flag =FeedBackActivity.getflag();
    if(flag) {

        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Thanks for your valuable feedback", Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
        snack.setActionTextColor(Color.WHITE);
        FeedBackActivity.flag = false;

    }
    }*/

        /*Fragment fragment = new LoginLandingFragment();
        FragmentManager fragmanager = BIND_AUTO_CREATE

                .getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();
        return true;
    }*/

    public void onBackPressed() {
        if (App.dropdown == false) {
            table.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
            img.setVisibility(View.VISIBLE);
            if (getSupportFragmentManager().findFragmentById(R.id.big_container) != null) {
                getSupportFragmentManager()
                        .beginTransaction().
                        remove(getSupportFragmentManager().findFragmentById(R.id.big_container)).commit();
            } else {
                DialogCreator.showExitDialog(this, "Exit App?", "Do you want to exit?");
            }
            App.dropdown = true;
        }
    }


    private void init() {

        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImageAdapter(ActivityLoginLanding.this, ImagesArray));

        mPager.setPageTransformer(true, new DrawFromBackTransformer());
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler() {
            @Override
            public void close() {

            }

            @Override
            public void flush() {

            }

            @Override
            public void publish(LogRecord logRecord) {

            }
        };
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
//                handler.post(Update);
            }
        }, 1000, 1000);

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }


    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_LOGOUT: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        if (jsonResponse.message != null) {
                            Intent in = new Intent(this, LoginActivity.class);
                            startActivity(in);
                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.message);
                            dismissDialog();

                        }
                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                            //  CommonUtils.saveAuthToken(this, jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();
                        dismissDialog();
                    }
                    break;
                }

                dismissDialog();
            }

            case AppConstants.REQUEST_GET_ACCOUNTS: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {

                        if (jsonResponse.consumers != null && jsonResponse.consumers.size() > 0) {
                            DatabaseManager.saveManageAccounts(this, jsonResponse.consumers);

                            dismissDialog();
                        }
                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();
                        dismissDialog();
                    }
                    break;
                }

                dismissDialog();
            }


        }
    }


    public void onAsyncFail(String message, String label, NetworkResponse response) {

        switch (label) {
            case AppConstants.REQUEST_LOGOUT: {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
//                Log.i(label, "Faq:" + message);
//                Log.i(label, "Faq:" + response);
            }
            break;
            case AppConstants.REQUEST_GET_ACCOUNTS: {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
                Log.i(label, AppConstants.REQUEST_GET_ACCOUNTS + message);
                Log.i(label, AppConstants.REQUEST_GET_ACCOUNTS + response);
            }
            break;
        }



    }
}