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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.fragments.LoginLandingFragment;
import com.essel.smartutilities.utility.DialogCreator;

public class LandingSkipLoginActivity extends AppCompatActivity implements View.OnClickListener {
    Button Submit;
    LinearLayout  action_about_us, action_tips, action_my_traiff, action_faq, action_contactus, action_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_skip_login);
        initialize();
    }

    private void initialize() {
        EditText consumerno = (EditText) findViewById(R.id.consumer_id);
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
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
        Intent in;
        switch (v.getId()) {
            case R.id.BTNSubmit:
                 in = new Intent(this, PayNowActivity.class);
                startActivity(in);
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
    private void shareTextUrl() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.codeofaninja.com");

        startActivity(Intent.createChooser(share, "Share link!"));
    }
}
