package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.Faq;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class FAQActivity extends AppCompatActivity implements View.OnClickListener, ServiceCaller {
    LinearLayout linearlayout_newconnection, linearlayout_changeownership, linearlayout_changeconnectiontype, linearlayout_loadextensionreduction, linearlayout_permanantdisconnection;
    ExpandableRelativeLayout expandableLayout_newserviceconnection, expandableLayout_changeofownership, expandableLayout_changeofconnectiontype, expandableLayout_loadextensionreduction, expandableLayout_permanantdisconnect;
    Button expandablebutton_newserviceconnection, expandablebutton_changeofownership, expandablebutton_changeofconnectiontype, expandablebutton_loadextensionreduction, expandablebutton_permanantdisconnec;
    TextView tv_1,tv_2;
    Context mContext;
    Dialog dialog_faq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

         initialize();
          Faq faq=new Faq();
        DatabaseManager.saveFAQ(this, faq);
    }




    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_faq, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_detail_view) {
            expandableLayout_newserviceconnection.collapse();
            expandableLayout_changeofownership.collapse();
            expandableLayout_changeofconnectiontype.collapse();
            expandableLayout_loadextensionreduction.collapse();
            expandableLayout_permanantdisconnect.collapse();
            return true;

        }


        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        expandablebutton_newserviceconnection = (Button) findViewById(R.id.expandableButton_newconnection);
        expandablebutton_changeofownership = (Button) findViewById(R.id.expandableButton_changeofowner);
        expandablebutton_changeofconnectiontype = (Button) findViewById(R.id.expandableButton_changeofconnection);
        expandablebutton_loadextensionreduction = (Button) findViewById(R.id.expandableButton_loadextensionreduction);
        expandablebutton_permanantdisconnec = (Button) findViewById(R.id.expandableButton_permanantdisconnect);

        expandablebutton_newserviceconnection.setOnClickListener(this);
        expandablebutton_changeofownership.setOnClickListener(this);
        expandablebutton_changeofconnectiontype.setOnClickListener(this);
        expandablebutton_loadextensionreduction.setOnClickListener(this);
        expandablebutton_permanantdisconnec.setOnClickListener(this);

        expandableLayout_newserviceconnection = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_newconnection);
        tv_1=(TextView)findViewById(R.id.tv_newconnection);
        tv_2=(TextView)findViewById(R.id.tv_changeofownership);
        expandableLayout_changeofownership = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_changeofownership);
        expandableLayout_loadextensionreduction = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_loadextensionreduction);
        expandableLayout_permanantdisconnect = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_permanantdisconnect);
        expandableLayout_changeofconnectiontype = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_changeofconnection);
        JsonObjectRequest request = WebRequests.getFaq(this, Request.Method.GET, AppConstants.URL_GET_FAQ, AppConstants.REQUEST_FAQ,
                this,SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
          App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_FAQ);
    }


    @Override
    public void onClick(View v) {


        if (v == expandablebutton_newserviceconnection) {

               expandableLayout_newserviceconnection.toggle();
           }

        if (v == expandablebutton_changeofownership) {

            expandableLayout_changeofownership.toggle();

        }
        if (v == expandablebutton_changeofconnectiontype) {

            expandableLayout_changeofconnectiontype.toggle();

        }
        if (v == expandablebutton_loadextensionreduction) {

            expandableLayout_loadextensionreduction.toggle();
        }
        if (v == expandablebutton_permanantdisconnec) {

            expandableLayout_permanantdisconnect.toggle();

        }

    }

    public void onBackPressed() {

        Intent in = new Intent(this, ActivityLoginLanding.class);
        startActivity(in);


    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_FAQ: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
//                            DatabaseManager.saveJobCards(mContext, jsonResponse.responsedata.jobcards);
//                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();
//                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
//                        Log.i(label, "Faqqqqqqqqqqqqqqqqq:" + jsonResponse.faqs);
                        if(jsonResponse.faqs.size()!=0)
                        {
                            tv_1.setText(jsonResponse.faqs.get(0).answer.toString().trim());
                            expandablebutton_newserviceconnection .setText(jsonResponse.faqs.get(0).question);
                            tv_2.setText(jsonResponse.faqs.get(0).answer.toString().trim());
                            expandablebutton_changeofownership  .setText(jsonResponse.faqs.get(1).question);
                        }
                        if (jsonResponse.authorization != null) {
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                    }
                    break;
                }
            }

        }
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {
        switch (label) {
            case AppConstants.REQUEST_FAQ: {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
//                Log.i(label, "Faq:" + message);
//                Log.i(label, "Faq:" + response);
            }
            break;
        }

    }
}