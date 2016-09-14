package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.DialogCreator;


public class StartActivity extends BaseActivity implements View.OnClickListener{

    Button btnLogin,btnSignup;
    FrameLayout btnNewConnection;
    LinearLayout btnSkipLogin;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mContext = this;

        btnLogin = (Button)findViewById(R.id.BTNLogin);
        btnSignup = (Button)findViewById(R.id.BTNSignup);
        btnNewConnection = (FrameLayout)findViewById(R.id.FrameNewconnection);
        btnSkipLogin = (LinearLayout)findViewById(R.id.LinearSkip);

        btnLogin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnNewConnection.setOnClickListener(this);
        btnSkipLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==btnLogin){
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "1");
            startActivity(i);
        }
        else if(v==btnSignup){
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "2");
            startActivity(i);
        }
        else if(v==btnNewConnection){
            Intent i = new Intent(mContext, ActivityMainSL.class);
            i.putExtra(AppConstants.SCREEN_ID, "3");
            startActivity(i);

        }
        else if(v==btnSkipLogin){
            Intent i = new Intent(mContext, LandingSkipLogin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        DialogCreator.showExitDialog(this,getString(R.string.exit),getString(R.string.exit_message));
    }
}
