package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.AppConstants;

public class LandingSkipLogin extends BaseActivity implements View.OnClickListener {

    LinearLayout actionQuickPay, actionApplyForNewConnection, actionMyTarrifs, actionTips, actionAboutUs, actionContactUs, actionFeedback, actionFAQ, actionShare;
    Context mContext;
    FrameLayout actionLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_skip_login);
        mContext = this;
        initialize();

    }

    private void initialize() {
        actionQuickPay = (LinearLayout) findViewById(R.id.action_quick_pay);
        actionApplyForNewConnection = (LinearLayout) findViewById(R.id.action_apply_new_connection);
        actionMyTarrifs = (LinearLayout) findViewById(R.id.action_my_tarrifs);
        actionTips = (LinearLayout) findViewById(R.id.action_tips);
        actionAboutUs = (LinearLayout) findViewById(R.id.action_about_us);
        actionContactUs = (LinearLayout) findViewById(R.id.action_contact_us);
        actionFeedback = (LinearLayout) findViewById(R.id.action_feedback);
        actionFAQ = (LinearLayout) findViewById(R.id.action_faq);
        actionShare = (LinearLayout) findViewById(R.id.action_share);
        actionLogin = (FrameLayout) findViewById(R.id.frame_action_login);

        actionQuickPay.setOnClickListener(this);
        actionApplyForNewConnection.setOnClickListener(this);
        actionMyTarrifs.setOnClickListener(this);
        actionTips.setOnClickListener(this);
        actionAboutUs.setOnClickListener(this);
        actionContactUs.setOnClickListener(this);
        actionFeedback.setOnClickListener(this);
        actionFAQ.setOnClickListener(this);
        actionShare.setOnClickListener(this);
        actionLogin.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == actionQuickPay) {
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "0");
            startActivity(i);
        } else if (v == actionApplyForNewConnection) {
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "3");
            startActivity(i);
        } else if (v == actionMyTarrifs) {

        } else if (v == actionTips) {
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "5");
            startActivity(i);

        } else if (v == actionAboutUs) {
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "6");
            startActivity(i);
        } else if (v == actionContactUs) {
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "7");
            startActivity(i);
        } else if (v == actionFeedback) {

        } else if (v == actionFAQ) {
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "4");
            startActivity(i);

        } else if (v == actionShare) {
            shareTextUrl();

        } else if (v == actionLogin) {
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "1");
            startActivity(i);

        } else if (v == actionMyTarrifs) {
        Intent i = new Intent(mContext, ActivityMainSL.class);
        i.putExtra(AppConstants.SCREEN_ID, "9");
        startActivity(i);
    }
    }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.codeofaninja.com");

        startActivity(Intent.createChooser(share, "Share link!"));
    }



}


