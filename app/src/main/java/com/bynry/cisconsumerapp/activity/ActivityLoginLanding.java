package com.bynry.cisconsumerapp.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
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

import com.appdynamics.eumagent.runtime.Instrumentation;
import com.eftimoff.viewpagertransformers.CubeOutTransformer;
import com.bynry.cisconsumerapp.fragments.LoginLandingFragment;
import com.bynry.cisconsumerapp.models.BrandingImages;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.adapter.SlidingImageAdapter;
import com.bynry.cisconsumerapp.db.DatabaseManager;
import com.bynry.cisconsumerapp.fragments.LoginDropDownFragment;
import com.bynry.cisconsumerapp.models.Consumer;
import com.bynry.cisconsumerapp.utility.App;
import com.bynry.cisconsumerapp.utility.CommonUtils;
import com.bynry.cisconsumerapp.utility.DialogCreator;
import com.bynry.cisconsumerapp.utility.SharedPrefManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ActivityLoginLanding extends AppCompatActivity implements View.OnClickListener {
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
    public Menu Mymenu;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_landing);
        Instrumentation.start("AD-AAB-AAD-EHP", getApplicationContext());
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
        drop.
                setOnClickListener(this);
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
    {ArrayList<Consumer> consumers = DatabaseManager.getAllManageAccounts(this);
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
            Consumer get = new Consumer();
            get = DatabaseManager.getProfileinfo(this,"true");
            SharedPrefManager.saveValue(this,SharedPrefManager.CONSUMER_NO,get.consumer_no);
            SharedPrefManager.saveValue(this,SharedPrefManager.CONSUMER_NAME,get.consumer_name);
            maintitle.setText(get.consumer_no);
            subtitle.setText(get.consumer_name);
        }

        maintitle.setOnClickListener(this);
        subtitle.setOnClickListener(this);
    }


    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        Drawable dre= ContextCompat.getDrawable(this,R.drawable.ic_action_notification);
        int count=DatabaseManager.getcount(this,"false");
        if (count > 0)
            ActionItemBadge.update(this, menu.findItem(R.id.action_notifications),dre, ActionItemBadge.BadgeStyles.DARK_GREY, count);
        Mymenu=menu;
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
            fragmentTransaction.add(R.id.big_container, fragment);
            fragmentTransaction.addToBackStack(null);
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
        final int time=6000;

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, time, time);

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

    @Override
    protected void onResume()
    {        super.onResume();
        this.invalidateOptionsMenu();
        if (CommonUtils.isNetworkAvaliable(this))
        {
            if (pDialog != null && !pDialog.isShowing())
            {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
        } else
            Toast.makeText(this.getApplicationContext(), R.string.error_internet_not_connected, Toast.LENGTH_SHORT).show();
        setTitle();

    }



}