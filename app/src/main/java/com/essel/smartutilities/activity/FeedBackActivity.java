package com.essel.smartutilities.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.essel.smartutilities.R;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edit_remark_feedback;
    Button  btn_submit_feedback;
     static Boolean flag=false;
     ImageView image1,image2,image3,image4,image5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        edit_remark_feedback=(EditText)findViewById(R.id.edit_remark_feedback);
        btn_submit_feedback=(Button)findViewById(R.id.btn_submit_feedback);

        image1=(ImageView)findViewById(R.id.image1);
        image2=(ImageView)findViewById(R.id.image2);
        image3=(ImageView)findViewById(R.id.image3);
        image4=(ImageView)findViewById(R.id.image4);
        image5=(ImageView)findViewById(R.id.image5);

        btn_submit_feedback.setOnClickListener(this);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
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

        }else if(v==image1){

           Toast.makeText(this.getApplicationContext(), "you have rated 5", Toast.LENGTH_SHORT).show();



        }
        else if(v==image2){
            Toast.makeText(this.getApplicationContext(), " you have rated 4", Toast.LENGTH_SHORT).show();



        }
        else if(v==image3){
          Toast.makeText(this.getApplicationContext(), "you have rated 3", Toast.LENGTH_SHORT).show();



        }
        else if(v==image4){
          Toast.makeText(this.getApplicationContext(), "you have rated 2", Toast.LENGTH_SHORT).show();



        }
        else if(v==image5){

           Toast.makeText(this.getApplicationContext(), "you have rated 1", Toast.LENGTH_SHORT).show();



        }



    }

    public static Boolean getflag(){

        return flag;


    }


    public void onBackPressed() {

        Intent in =new Intent(this,ActivityLoginLanding.class);
        startActivity(in);


    }
}
