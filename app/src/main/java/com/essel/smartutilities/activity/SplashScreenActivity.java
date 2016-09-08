package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.essel.smartutilities.R;

public class SplashScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        callNextScreen(false);

    }

    private void callNextScreen(Boolean isLogin) {

        if (isLogin) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);

        } else {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent intent = new Intent(SplashScreenActivity.this, StartActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, 2000);
        }
    }
}
