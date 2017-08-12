package com.bynry.cisconsumerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.bynry.cisconsumerapp.utility.SharedPrefManager;
import com.bynry.cisconsumerapp.R;

public class SplashScreenActivity extends BaseActivity
{
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                moveNext();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void moveNext()
    {
        Intent intent = null;
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_LOGGED).equals("true"))
            intent = new Intent(SplashScreenActivity.this, ActivityLoginLanding.class);
        else
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        SplashScreenActivity.this.startActivity(intent);
        SplashScreenActivity.this.finish();
    }
}
