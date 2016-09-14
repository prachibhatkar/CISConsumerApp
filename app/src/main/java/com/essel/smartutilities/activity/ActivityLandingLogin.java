package com.essel.smartutilities.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.DialogCreator;


public class ActivityLandingLogin extends Activity implements View.OnClickListener {
    LinearLayout actionmybill, actionservices, actioncomplaints, actionpaymenthistory, actionmyprofile, actionquickpay, actionmanageaccount,
            actionmytariff,actionmore;Button btnLogout;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login);
        mContext = this;
        initialize();


    }

    private void initialize() {

        actionmybill = (LinearLayout) findViewById(R.id.action_my_bill);
        actionservices = (LinearLayout) findViewById(R.id.action_services);
        actioncomplaints = (LinearLayout) findViewById(R.id.action_complaints);
        actionpaymenthistory = (LinearLayout) findViewById(R.id.action_payment_history);
        actionmyprofile = (LinearLayout) findViewById(R.id.action_my_profile);
        actionquickpay = (LinearLayout) findViewById(R.id.action_quick_pay);
        actionmanageaccount = (LinearLayout) findViewById(R.id.action_manage_accounts);
        actionmytariff = (LinearLayout) findViewById(R.id.action_my_tarrifs);
        actionmore = (LinearLayout) findViewById(R.id.action_more_menu);

        btnLogout=(Button)findViewById(R.id.BTNLogout);

        btnLogout.setOnClickListener(this);
        actionmyprofile.setOnClickListener(this);
        actionmore.setOnClickListener(this);
        actionmytariff.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        if(view==actionmyprofile){
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "8");
            startActivity(i);
        }
        else if(view==actionmore){
            Intent i = new Intent(mContext, ActivityLandingMoreOptions.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_in,R.anim.slide_out);

        }
        else if(view==actionmytariff){
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "9");
            startActivity(i);
        }
        else if(view==btnLogout){
            DialogCreator.showLogoutDialog(this,"Logout","Are you sure you want to logout?");
        }
    }

    @Override
    public void onBackPressed() {
        DialogCreator.showExitDialog(this,getString(R.string.exit),getString(R.string.exit_message));
    }
}

