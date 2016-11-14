package com.essel.smartutilities.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.essel.smartutilities.R;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_remark_feedback;
    Button  btn_submit_feedback;
     static Boolean flag =true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feedback");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        edit_remark_feedback=(EditText)findViewById(R.id.edit_remark_feedback);
        btn_submit_feedback=(Button)findViewById(R.id.btn_submit_feedback);
        btn_submit_feedback.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v==btn_submit_feedback){
            Intent in =new Intent(this,ActivityLoginLanding.class);
            startActivity(in);
           // ActivityLoginLanding.snackBarMethod();
            flag=true;

           /* Snackbar snack = Snackbar.make(findViewById(android.R.id.content), "Thanks for your valuable feedback", Snackbar.LENGTH_INDEFINITE);
            View view = snack.getView();
            FrameLayout.LayoutParams params =(FrameLayout.LayoutParams)view.getLayoutParams();
            params.gravity = Gravity.TOP;
            view.setLayoutParams(params);
            snack.show();
            snack.setActionTextColor(Color.WHITE);*/

        }



    }

    public static Boolean getflag(){

        return flag;


    }
}
