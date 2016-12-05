package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.CommonUtils;

public class SplashScreenActivity extends BaseActivity  {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                moveNext();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private void moveNext() {
        Intent intent = null;
        if(CommonUtils.isLoggedIn(SplashScreenActivity.this))
            intent = new Intent(SplashScreenActivity.this,ActivityLoginLanding.class);
        else
            intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
        SplashScreenActivity.this.startActivity(intent);
        SplashScreenActivity.this.finish();
//        overridePendingTransition(R.anim.slide_no_change, R.anim.slide_up);
    }
}
