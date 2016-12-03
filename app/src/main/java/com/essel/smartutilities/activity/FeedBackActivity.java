package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

public class FeedBackActivity extends AppCompatActivity implements View.OnClickListener,ServiceCaller {
     EditText edit_remark_feedback;
     Button  btn_submit_feedback;
     public static Boolean flag=false;
     String remark;
     TextView tv_rate;
     String count;
     ImageView image1,image2,image3,image4,image5;
    ProgressDialog pDialog;
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
        remark=edit_remark_feedback.toString().trim();

        tv_rate=(TextView)findViewById(R.id.tv_rate);

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

    private void initProgressDialog() {

        if (pDialog == null) {
            pDialog = new ProgressDialog(this);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View v) {
        if(v==btn_submit_feedback){
            String feedbackremark  = String.valueOf(edit_remark_feedback.getText());

            if(feedbackremark.equals("")){

                Toast.makeText(this, "Please fill all fields ", Toast.LENGTH_LONG).show();

            }

           // ActivityLoginLanding.snackBarMethod();
           // flag=true;
        else {
                initProgressDialog();
                if (pDialog != null && !pDialog.isShowing()) {
                    pDialog.setMessage(" please wait..");
                    pDialog.show();
                }
                JsonObjectRequest request = WebRequests.feedbackrequest(this, Request.Method.POST, AppConstants.URL_POST_FEEDBACK, AppConstants.REQUEST_FEEDBACK, this, count, remark,SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN) );
                App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_FEEDBACK);
               // flag=true;


            }



        }else if(v==image1){
            count="5";
            tv_rate.setText("bad");
           // Toast.makeText(this.getApplicationContext(), "you have rated 5", Toast.LENGTH_SHORT).show();



        }
        else if(v==image2){
            count="4";
            tv_rate.setText("ok");
           // Toast.makeText(this.getApplicationContext(), " you have rated 4", Toast.LENGTH_SHORT).show();



        }
        else if(v==image3){
            count="3";
            tv_rate.setText("like it");
          //Toast.makeText(this.getApplicationContext(), "you have rated 3", Toast.LENGTH_SHORT).show();



        }
        else if(v==image4){
            count="2";
            tv_rate.setText("Good");
           //Toast.makeText(this.getApplicationContext(), "you have rated 2", Toast.LENGTH_SHORT).show();



        }
        else if(v==image5){
            count="1";
            tv_rate.setText("loved it");
            //Toast.makeText(this.getApplicationContext(), "you have rated 1", Toast.LENGTH_SHORT).show();

        }



    }




    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_FEEDBACK: {
                if (jsonResponse != null) {
                    Log.i("Tag","valueresponse"+flag);
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        dismissDialog();
                        flag=true;
                        Intent in = new Intent(this, ActivityLoginLanding.class);
                        startActivity(in);
                        Log.i(label, "hygt " + jsonResponse);
                        Log.i(label, "hyif " + jsonResponse.feedbackmessage);
                        if(jsonResponse.feedbackmessage!= null) {
                            dismissDialog();

                        }


                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();


                    }

                    break;
                }
                dismissDialog();
            }

        }
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {

        switch (label) {
            case AppConstants.REQUEST_FEEDBACK: {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
                Log.i(label, "gjjkfhdkh " + message);
                Log.i(label, "jhjkghfkh " + response);
                dismissDialog();
            }
            break;
        }




    }
    public static Boolean getflag(){

        Log.i("Tag","valuegetflg" +flag);
        return flag;



    }

}
