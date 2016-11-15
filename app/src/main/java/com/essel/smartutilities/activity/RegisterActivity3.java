package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.essel.smartutilities.R;

public class RegisterActivity3 extends BaseActivity implements View.OnClickListener {


    AppCompatButton buttonRegister,buttonVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Register:9595903117");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initialize();
    }

    private void initialize(){
        buttonVerify = (AppCompatButton) findViewById(R.id.btn_verify);
        buttonVerify.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if(v==buttonVerify){
            Intent i =new Intent(this,RegisterActivity4.class);
            startActivity(i);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
