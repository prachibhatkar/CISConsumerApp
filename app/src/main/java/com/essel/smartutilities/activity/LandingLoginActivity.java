package com.essel.smartutilities.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.DialogCreator;


public class LandingLoginActivity extends Activity implements View.OnClickListener {
    LinearLayout actionmybill, actionapplyfornewconnection, actionmyprofile, actiontips, actionaboutus,
            actioncontactus, actionfaq, actionshare; Button btnLogout;
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
        actionapplyfornewconnection = (LinearLayout) findViewById(R.id.action_apply_new_connection);
        actionmyprofile = (LinearLayout) findViewById(R.id.action_my_profile);
        actiontips = (LinearLayout) findViewById(R.id.action_tips);
        actionaboutus = (LinearLayout) findViewById(R.id.action_about_us);
        actioncontactus = (LinearLayout) findViewById(R.id.action_contact_us);
        actionfaq = (LinearLayout) findViewById(R.id.action_faq);
        actionshare = (LinearLayout) findViewById(R.id.action_share);
        btnLogout=(Button)findViewById(R.id.BTNLogout);

        btnLogout.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

        if(view==btnLogout){
            DialogCreator.showLogoutDialog(this,"Logout","Are you sure you want to logout?");
        }
    }

    @Override
    public void onBackPressed() {
        DialogCreator.showExitDialog(this,getString(R.string.exit),getString(R.string.exit_message));
    }
}

