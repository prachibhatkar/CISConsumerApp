package com.bynry.cisconsumerapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.bynry.cisconsumerapp.models.FixedEnergyCharge;
import com.bynry.cisconsumerapp.models.TarifCatagory;
import com.bynry.cisconsumerapp.models.TariffEnergyCharge;
import com.bynry.cisconsumerapp.utility.CommonUtils;
import com.bynry.cisconsumerapp.webservice.WebRequests;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.callers.ServiceCaller;
import com.bynry.cisconsumerapp.db.DatabaseManager;
import com.bynry.cisconsumerapp.models.JsonResponse;
import com.bynry.cisconsumerapp.models.Tariff;
import com.bynry.cisconsumerapp.utility.App;
import com.bynry.cisconsumerapp.utility.AppConstants;

import java.util.ArrayList;

public class TraiffActivity extends AppCompatActivity implements ServiceCaller
{

    public ArrayList<String>tariffEnergyCharge;
    TextView tv_bplcatagory1,tv_bplcatagory2,tv_fixedcharge,tv_energycharge,tv_100units,tv_300unoits,tv_500units,tv_1000units,tv_singlephase,tv_threephase,tv_energycharge1,tv_energycharge2,tv_energycharge3,tv_energycharge4,tv_singlephase_rs,tv_threephase_rs;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
        tv_100units=(TextView)findViewById(R.id.tv_100units);
        tv_300unoits=(TextView)findViewById(R.id.tv_300units);
        tv_500units=(TextView)findViewById(R.id.tv_500units);
        tv_1000units=(TextView)findViewById(R.id.tv_1000units);
        tv_singlephase=(TextView)findViewById(R.id.single_phase);
        tv_threephase=(TextView)findViewById(R.id.three_phase);
        tv_energycharge1=(TextView)findViewById(R.id.tv_energycharge1);
        tv_energycharge2=(TextView)findViewById(R.id.tv_energycharge2);
        tv_energycharge3=(TextView)findViewById(R.id.tv_energycharge3);
        tv_energycharge4=(TextView)findViewById(R.id.tv_energycharge4);
        tv_singlephase_rs=(TextView)findViewById(R.id.single_phase_rs);
        tv_threephase_rs=(TextView)findViewById(R.id.three_phase_rs);





        if( CommonUtils.isNetworkAvaliable(this))
        {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing())
            {
                pDialog.setMessage(" please wait..");
                pDialog.show();
            }
            JsonObjectRequest request = WebRequests.getTariff(this, Request.Method.GET, AppConstants.URL_GET_TARIFF, AppConstants.REQUEST_TARIFF,
                    this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_TARIFF);
        }else
        {

            TarifCatagory tariffcata=new TarifCatagory();
            ArrayList<TarifCatagory>arraytraiffcata=new ArrayList<TarifCatagory>();
            arraytraiffcata = DatabaseManager.getTariffCatagory(this);
            for(int i=0;i< arraytraiffcata.size();i++) {

                tv_bplcatagory1.setText(arraytraiffcata.get(0).charge);
                tv_fixedcharge.setText(arraytraiffcata.get(0).slab);

                tv_bplcatagory2.setText(arraytraiffcata.get(1).charge);
                tv_energycharge.setText(arraytraiffcata.get(1).slab);

            }

            TariffEnergyCharge traiffenergycharge=new TariffEnergyCharge();
            ArrayList<TariffEnergyCharge>traiffenergy=new ArrayList<TariffEnergyCharge>();
            traiffenergy=DatabaseManager.getTariffEnergyCharge(this);
            for(int i=0;i< traiffenergy.size();i++) {
                tv_energycharge1.setText(traiffenergy.get(0).charge);
                tv_100units.setText(traiffenergy.get(0).slab);

                tv_energycharge2.setText(traiffenergy.get(1).charge);
                tv_300unoits.setText(traiffenergy.get(1).slab);


                tv_energycharge3.setText(traiffenergy.get(2).charge);
                tv_500units.setText(traiffenergy.get(2).slab);


                tv_energycharge4.setText(traiffenergy.get(3).charge);
                tv_1000units.setText(traiffenergy.get(3).slab);
            }

           FixedEnergyCharge fixedenergycharge=new FixedEnergyCharge();
            ArrayList<FixedEnergyCharge>fixedcharge=new ArrayList<FixedEnergyCharge>();
            fixedcharge=DatabaseManager.getTariffFixedEnergyCharge(this);
            for(int i=0;i< fixedcharge.size();i++)
            {
                tv_singlephase.setText(fixedcharge.get(0).slab);
                tv_singlephase_rs.setText(fixedcharge.get(0).charge);

                tv_threephase.setText(fixedcharge.get(1).slab);
                tv_threephase_rs.setText(fixedcharge.get(1).charge);
            }
            Toast.makeText(this.getApplicationContext(), " Please Check Internet Connection", Toast.LENGTH_SHORT).show();
        }



    }

    private void initProgressDialog()
    {

        if (pDialog == null)
        {
            pDialog = new ProgressDialog(this);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog()
    {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }


    public void onBackPressed()
    {

       super.onBackPressed();

    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {
        switch (label)
        {
            case AppConstants.REQUEST_TARIFF:
            {
                if (jsonResponse != null)
                {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        dismissDialog();
//
                        if(jsonResponse.tariff!=null)
                        {
                            Log.i(label, "Tariffsucccccc:" + jsonResponse.tariff);
                            Tariff gettariff = new Tariff();

                            for (int i = 1; i <=jsonResponse.tariff.tariffCategory.size(); i++) {

                                tv_bplcatagory1.setText(jsonResponse.tariff.tariffCategory.get(0).charge.toString().trim());
                                tv_fixedcharge.setText(jsonResponse.tariff.tariffCategory.get(0).slab.toString().trim());

                                tv_bplcatagory2.setText(jsonResponse.tariff.tariffCategory.get(1).charge.toString().trim());
                                tv_energycharge.setText(jsonResponse.tariff.tariffCategory.get(1).slab.toString().trim());
                                Log.i(label, "Tariffsucccccc:" + jsonResponse.tariffCategory);

                                TarifCatagory tariff1=new TarifCatagory();



//                                tariff1.charge=jsonResponse.tariff.tariffCategory.get(0).charge.toString().trim();
//                                tariff1.slab=jsonResponse.tariff.tariffCategory.get(0).slab.toString().trim();
//
//                                tariff1.charge=jsonResponse.tariff.tariffCategory.get(1).charge.toString().trim();
//                                tariff1.slab=jsonResponse.tariff.tariffCategory.get(1).slab.toString().trim();



                            }

                            DatabaseManager.saveTariffCatagory(this,jsonResponse.tariff.tariffCategory);

                           for (int i = 1; i <=jsonResponse.tariff.tariffEnergyCharge.size(); i++) {

                                tv_energycharge1.setText(jsonResponse.tariff.tariffEnergyCharge.get(0).charge.toString().trim());
                                tv_100units.setText(jsonResponse.tariff.tariffEnergyCharge.get(0).slab.toString().trim());

                                tv_energycharge2.setText(jsonResponse.tariff.tariffEnergyCharge.get(1).charge.toString().trim());
                                tv_300unoits.setText(jsonResponse.tariff.tariffEnergyCharge.get(1).slab.toString().trim());


                                tv_energycharge3.setText(jsonResponse.tariff.tariffEnergyCharge.get(2).charge.toString().trim());
                                tv_500units.setText(jsonResponse.tariff.tariffEnergyCharge.get(2).slab.toString().trim());


                                tv_energycharge4.setText(jsonResponse.tariff.tariffEnergyCharge.get(3).charge.toString().trim());
                                tv_1000units.setText(jsonResponse.tariff.tariffEnergyCharge.get(3).slab.toString().trim());
                                Log.i(label, "Tariffsucccccc:" + jsonResponse.tariffEnergyCharge);

                                  TariffEnergyCharge tariffenergycharge= new TariffEnergyCharge();

//                               tariffenergycharge.charge=jsonResponse.tariff.tariffEnergyCharge.get(0).charge.toString().trim();
//                               tariffenergycharge.slab=jsonResponse.tariff.tariffEnergyCharge.get(0).slab.toString().trim();
//
//                               tariffenergycharge.charge1=jsonResponse.tariff.tariffEnergyCharge.get(1).charge.toString().trim();
//                               tariffenergycharge.slab1=jsonResponse.tariff.tariffEnergyCharge.get(1).slab.toString().trim();
//
//                               tariffenergycharge.charge2=jsonResponse.tariff.tariffEnergyCharge.get(2).charge.toString().trim();
//                               tariffenergycharge.slab2=jsonResponse.tariff.tariffEnergyCharge.get(2).slab.toString().trim();
//
//                               tariffenergycharge.charge3=jsonResponse.tariff.tariffEnergyCharge.get(3).charge.toString().trim();
//                               tariffenergycharge.slab3=jsonResponse.tariff.tariffEnergyCharge.get(3).slab.toString().trim();



                           }
                            DatabaseManager.saveTariffEnergyCharge(this,jsonResponse.tariff.tariffEnergyCharge);

                            for (int i = 1; i <=jsonResponse.tariff.fixedEnergyCharge.size(); i++) {

                                tv_singlephase.setText(jsonResponse.tariff.fixedEnergyCharge.get(0).slab.toString().trim());
                                tv_singlephase_rs.setText(jsonResponse.tariff.fixedEnergyCharge.get(0).charge.toString().trim());
                               // tv_100units.setText(jsonResponse.tariff.tariffEnergyCharge.get(i).slab.toString().trim());

                                tv_threephase.setText(jsonResponse.tariff.fixedEnergyCharge.get(1).slab.toString().trim());
                                tv_threephase_rs.setText(jsonResponse.tariff.fixedEnergyCharge.get(1).charge.toString().trim());
                              //  tv_300unoits.setText(jsonResponse.tariff.tariffEnergyCharge.get(i).slab.toString().trim());

                                Log.i(label, "Tariffsucccccc:" + jsonResponse.fixedEnergyCharge);

                            }

                            DatabaseManager.saveFixedEnergyCharge(this,jsonResponse.tariff.fixedEnergyCharge);

                        }
                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();

                        Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                    }
                    break;
                }
                dismissDialog();
            }

        }




    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response)
    {

        switch (label)
        {
            case AppConstants.REQUEST_TARIFF:
            {Log.i(label, "tarifffffffff" + response);
                dismissDialog();
            }
            break;
        }

    }
}
