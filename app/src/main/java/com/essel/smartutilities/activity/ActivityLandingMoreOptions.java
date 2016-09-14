package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;


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

    }

    @Override
    public void onClick(View view) {

        if(view==actionbackmenu){
            Intent i = new Intent(mContext, ActivityLandingLogin.class);
            startActivity(i);
            overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);
        }
    }
    @Override
    public void onBackPressed() {
        Intent i = new Intent(mContext, ActivityLandingLogin.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_enter, R.anim.slide_exit);

    }
}
