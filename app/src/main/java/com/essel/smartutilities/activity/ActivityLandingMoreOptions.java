package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.AppConstants;


/**
 * Created by hp on 9/10/2016.
 */
public class ActivityLandingMoreOptions extends AppCompatActivity implements View.OnClickListener {
    LinearLayout actiontips, actionaboutus,
            actioncontactus, actionfaq, actionfeedback, actionshare, actionbackmenu;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_login_more);
        mContext = this;
        initialize();
    }

    private void initialize() {

        actiontips = (LinearLayout) findViewById(R.id.action_tips);
        actionaboutus = (LinearLayout) findViewById(R.id.action_about_us);
        actioncontactus = (LinearLayout) findViewById(R.id.action_contact_us);
        actionfaq = (LinearLayout) findViewById(R.id.action_faq);
        actionfeedback = (LinearLayout) findViewById(R.id.action_feedback);
        actionshare = (LinearLayout) findViewById(R.id.action_share);
        actionbackmenu = (LinearLayout) findViewById(R.id.action_back_menu);

        actionbackmenu.setOnClickListener(this);
        actiontips.setOnClickListener(this);
        actionaboutus.setOnClickListener(this);
        actioncontactus.setOnClickListener(this);
        actionfaq.setOnClickListener(this);
        actionfeedback.setOnClickListener(this);
        actionshare.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if(view==actionbackmenu){
            Intent i = new Intent(mContext, ActivityLandingLogin.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        }
        else  if(view==actiontips){
            Intent i = new Intent(mContext, ActivityMainLogin.class);
            i.putExtra(AppConstants.SCREEN_ID, "1");
            startActivity(i);
            }
        else if(view==actionaboutus){
            Intent i = new Intent(mContext, ActivityMainLogin.class);
            i.putExtra(AppConstants.SCREEN_ID, "10");
            startActivity(i);
            }
        else if(view==actioncontactus) {
            Intent i = new Intent(mContext, ActivityMainLogin.class);
            i.putExtra(AppConstants.SCREEN_ID, "2");
            startActivity(i);
        }
        else if(view==actionfaq) {
            Intent i = new Intent(mContext, ActivityMainLogin.class);
            i.putExtra(AppConstants.SCREEN_ID, "11");
            startActivity(i);
        }
        else if(view==actionfeedback) {
            Intent i = new Intent(mContext, ActivityMainLogin.class);
            i.putExtra(AppConstants.SCREEN_ID, "12");
            startActivity(i);
        }
        else if(view==actionshare) {
            shareTextUrl();


        }

    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(mContext, ActivityLandingLogin.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);

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
