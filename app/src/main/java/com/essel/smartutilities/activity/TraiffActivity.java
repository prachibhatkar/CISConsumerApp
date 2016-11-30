package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.models.Tariff;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

import java.util.ArrayList;

public class TraiffActivity extends AppCompatActivity implements ServiceCaller {

    public ArrayList<String>tariffEnergyCharge;
    TextView tv_bplcatagory1,tv_bplcatagory2,tv_fixedcharge,tv_energycharge;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traiff);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Context mContext;
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tv_bplcatagory1=(TextView)findViewById(R.id.tv_bplcatagory1);
        tv_bplcatagory2=(TextView)findViewById(R.id.tv_bplcatagory2);
        tv_fixedcharge=(TextView)findViewById(R.id.tv_fixeddemandcharge);
        tv_energycharge=(TextView)findViewById(R.id.tv_energycharge);




        if( CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage(" please wait..");
                pDialog.show();
            }
            JsonObjectRequest request = WebRequests.getTariff(this, Request.Method.GET, AppConstants.URL_GET_TARIFF, AppConstants.REQUEST_TARIFF,
                    this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_TARIFF);
        }else
            Toast.makeText(this.getApplicationContext(), " Please Connection Internet ", Toast.LENGTH_SHORT).show();




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


    public void onBackPressed() {

       super.onBackPressed();

    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_TARIFF: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        dismissDialog();
//
                        if(jsonResponse.tariff!=null) {
                            Log.i(label, "Tariffsucccccc:" + jsonResponse.tariff);
                            Tariff gettariff = new Tariff();

                            for (int i = 1; i <=jsonResponse.tariff.tariffCategory.size(); i++) {

                                tv_bplcatagory1.setText(jsonResponse.tariff.tariffCategory.get(i).charge.toString().trim());
                                tv_fixedcharge.setText(jsonResponse.tariff.tariffCategory.get(i).slab.toString().trim());

                                tv_bplcatagory2.setText(jsonResponse.tariff.tariffCategory.get(i).charge.toString().trim());
                                tv_energycharge.setText(jsonResponse.tariff.tariffCategory.get(i).slab.toString().trim());
                                Log.i(label, "Tariffsucccccc:" + jsonResponse.tariffCategory);

                            }
                        }
                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {

                        Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

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
            case AppConstants.REQUEST_TARIFF: {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
//                Log.i(label, "Faq:" + message);
                Log.i(label, "tarifffffffff" + response);
            }
            break;
        }

    }
}
