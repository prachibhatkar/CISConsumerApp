package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.fragments.LoginLandingFragment;
import com.essel.smartutilities.utility.DialogCreator;

public class ActivityLoginLanding extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_landing);
        boolean flag1 = true;
        Context context;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("10201458201");
        getSupportActionBar().setSubtitle("Ashu Singh");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        Button bill = (Button) findViewById(R.id.btn_mybill);
        bill.setOnClickListener(this);
        Button pay = (Button) findViewById(R.id.btn_paynow);
        pay.setOnClickListener(this);
        // toolbar.setOnMenuItemClickListener(ActionBar.DISPLAY_SHOW_HOME);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        Fragment fragment = new LoginLandingFragment();
        FragmentManager fragmanager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

       /* Boolean flag = FeedBackActivity.getflag();
        if(flag){

            Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Thanks for your valuable feedback", Snackbar.LENGTH_LONG);
            View view = snack.getView();
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
            snack.setActionTextColor(Color.WHITE);
            FeedBackActivity.flag=false;

        }*/


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
            DialogCreator.showLogoutDialog(this, "Logout", "Are you sure you want to logout?");
            return true;
        }

        if (id == R.id.action_notifications) {

            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
