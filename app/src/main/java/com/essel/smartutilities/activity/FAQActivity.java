package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.Faq;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.webservice.WebRequests;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

public class FAQActivity extends AppCompatActivity implements View.OnClickListener, ServiceCaller
{
    LinearLayout linearlayout_newconnection, linearlayout_changeownership, linearlayout_changeconnectiontype, linearlayout_loadextensionreduction, linearlayout_permanantdisconnection;
    ExpandableRelativeLayout expandableLayout_newserviceconnection, expandableLayout_changeofownership, expandableLayout_changeofconnectiontype, expandableLayout_loadextensionreduction, expandableLayout_permanantdisconnect,expandablelayout6,expandablelayout7,expandablelayout8, expandablelayout9,expandablelayout10;

    Button expandablebutton_newserviceconnection, expandablebutton_changeofownership, expandablebutton_changeofconnectiontype, expandablebutton_loadextensionreduction, expandablebutton_permanantdisconnec,btn6,btn7,btn8,btn9,btn10;
    TextView tv_1, tv_2, tv_3, tv_4, tv_5, tv_6, tv_7, tv_8, tv_9, tv_10;
    Context mContext;
    Dialog dialog_faq;
    ProgressDialog pDialog;
    ArrayList<Button>btnarray=new ArrayList<Button>();
    ArrayList<TextView>tvarray=new ArrayList<TextView>();
    ArrayList<ExpandableRelativeLayout> explayoutarray=new ArrayList<ExpandableRelativeLayout>();
    ArrayList<String>tvarray1=new ArrayList<String>();
    ArrayList<String>btnarray1=new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
//          Faq faq=new Faq();
//        DatabaseManager.saveFAQ(this, faq);
    }


    public boolean onCreateOptionsMenu(Menu menu)
    {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_faq, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_detail_view)
        {
//            expandableLayout_newserviceconnection.collapse();
            tv_1.setVisibility(View.GONE);
            tv_2.setVisibility(View.GONE);
            tv_3.setVisibility(View.GONE);
            tv_4.setVisibility(View.GONE);
            tv_5.setVisibility(View.GONE);
            tv_6.setVisibility(View.GONE);
            tv_7.setVisibility(View.GONE);
            tv_8.setVisibility(View.GONE);
            tv_9.setVisibility(View.GONE);
            tv_10.setVisibility(View.GONE);

            return true;

        }


        return super.onOptionsItemSelected(item);
    }

    private void initialize()
    {
        expandablebutton_newserviceconnection = (Button) findViewById(R.id.expandableButton_newconnection);
        expandablebutton_changeofownership = (Button) findViewById(R.id.expandableButton_changeofowner);
        expandablebutton_changeofconnectiontype = (Button) findViewById(R.id.expandableButton_changeofconnection);
        expandablebutton_loadextensionreduction = (Button) findViewById(R.id.expandableButton_loadextensionreduction);
        expandablebutton_permanantdisconnec = (Button) findViewById(R.id.expandableButton_permanantdisconnect);
        btn6=(Button)findViewById(R.id.btn6);
        btn7=(Button)findViewById(R.id.btn7);
        btn8=(Button)findViewById(R.id.btn8);
        btn9=(Button)findViewById(R.id.btn9);
        btn10=(Button)findViewById(R.id.btn10);


        btnarray.add(0, expandablebutton_newserviceconnection);
        btnarray.add(1, expandablebutton_changeofownership);
        btnarray.add(2, expandablebutton_changeofconnectiontype);
        btnarray.add(3, expandablebutton_loadextensionreduction);
        btnarray.add(4, expandablebutton_permanantdisconnec);
        btnarray.add(5, btn6);
        btnarray.add(6, btn7);
        btnarray.add(7, btn8);
        btnarray.add(8, btn9);
        btnarray.add(9, btn10);

        expandablebutton_newserviceconnection.setOnClickListener(this);
        expandablebutton_changeofownership.setOnClickListener(this);
        expandablebutton_changeofconnectiontype.setOnClickListener(this);
        expandablebutton_loadextensionreduction.setOnClickListener(this);
        expandablebutton_permanantdisconnec.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);



        tv_1=(TextView)findViewById(R.id.tv_newconnection);
        tv_2=(TextView)findViewById(R.id.tv_changeofownership);
        tv_3=(TextView)findViewById(R.id.tv_changeofconnectiontype);
        tv_4=(TextView)findViewById(R.id.tv_loadextensionreduction);
        tv_5=(TextView)findViewById(R.id.tv_permanantdisconnect);
        tv_6=(TextView)findViewById(R.id.tv_6);
        tv_7=(TextView)findViewById(R.id.tv_7);
        tv_8=(TextView)findViewById(R.id.tv_8);
        tv_9=(TextView)findViewById(R.id.tv_9);
        tv_10=(TextView)findViewById(R.id.tv_10);

       // tv_1.setMovementMethod(LinkMovementMethod.getInstance());

        tvarray.add(0,tv_1);
        tvarray.add(1,tv_2);
        tvarray.add(2,tv_3);
        tvarray.add(3,tv_4);
        tvarray.add(4,tv_5);
        tvarray.add(5,tv_6);
        tvarray.add(6,tv_7);
        tvarray.add(7,tv_8);
        tvarray.add(8,tv_9);
        tvarray.add(9,tv_10);


//        //expandableLayout_newserviceconnection = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_newconnection);
//        expandableLayout_changeofownership = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_changeofownership);
//        expandableLayout_loadextensionreduction = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_loadextensionreduction);
//        expandableLayout_permanantdisconnect = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_permanantdisconnect);
//        expandableLayout_changeofconnectiontype = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_changeofconnection);
//
//        expandablelayout6 = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout6);
//        expandablelayout7 = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout7);
//        expandablelayout8 = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout8);
//        expandablelayout9 = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout9);
//        expandablelayout10 = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout10);

//        explayoutarray.add(0,expandableLayout_changeofownership);
//        explayoutarray.add(1,expandableLayout_loadextensionreduction);
//        explayoutarray.add(2,expandableLayout_permanantdisconnect);
//        explayoutarray.add(3,expandableLayout_changeofconnectiontype);
//        explayoutarray.add(4,expandablelayout6);
//        explayoutarray.add(5,expandablelayout7);
//        explayoutarray.add(6,expandablelayout8);
//        explayoutarray.add(7,expandablelayout9);
//        explayoutarray.add(8,expandablelayout10);
//        explayoutarray.add(9,expandablelayout6);


        if( CommonUtils.isNetworkAvaliable(this))
        {

            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing())
            {
                pDialog.setMessage(" please wait..");
                pDialog.show();
            }
            JsonObjectRequest request = WebRequests.getFaq(this, Request.Method.GET, AppConstants.URL_GET_FAQ, AppConstants.REQUEST_FAQ, this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_FAQ);
        }else

        {
              Faq faq2 = new Faq();
             ArrayList<Faq>arrayfaq=new ArrayList<Faq>();
            arrayfaq=DatabaseManager.getFaq(this);
             for(int i=0;i<arrayfaq.size();i++) {
                   btnarray.get(i).setVisibility(View.VISIBLE);

                   tvarray.get(i).setText(arrayfaq.get(i).answer);
                   btnarray.get(i).setText(arrayfaq.get(i).question);
               // expandablebutton_newserviceconnection.setText(faq2.answer);
            }
//
            Toast.makeText(this.getApplicationContext(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }


    }


    private void initProgressDialog()
    {

        if (pDialog == null) {
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


    @Override
    public void onClick(View v)
    {


        if (v == expandablebutton_newserviceconnection)
        {
           if(tv_1.getVisibility()==View.VISIBLE)
               tv_1.setVisibility(View.GONE);
            else
               tv_1.setVisibility(View.VISIBLE);
//            expandableLayout_newserviceconnection.toggle();
        }

        if (v == expandablebutton_changeofownership)
        {

            if(tv_2.getVisibility()==View.VISIBLE)
                tv_2.setVisibility(View.GONE);
            else
                tv_2.setVisibility(View.VISIBLE);

        }
        if (v == expandablebutton_changeofconnectiontype)
        {

            if(tv_3.getVisibility()==View.VISIBLE)
                tv_3.setVisibility(View.GONE);
            else
                tv_3.setVisibility(View.VISIBLE);

        }
        if (v == expandablebutton_loadextensionreduction)
        {

            if(tv_4.getVisibility()==View.VISIBLE)
                tv_4.setVisibility(View.GONE);
            else
                tv_4.setVisibility(View.VISIBLE);
        }
        if (v == expandablebutton_permanantdisconnec)
        {

            if(tv_5.getVisibility()==View.VISIBLE)
                tv_5.setVisibility(View.GONE);
            else
                tv_5.setVisibility(View.VISIBLE);

        }
        if (v == btn6)
        {

            if(tv_6.getVisibility()==View.VISIBLE)
                tv_6.setVisibility(View.GONE);
            else
                tv_6.setVisibility(View.VISIBLE);

        }
        if (v == btn7)
        {

            if(tv_7.getVisibility()==View.VISIBLE)
                tv_7.setVisibility(View.GONE);
            else
                tv_7.setVisibility(View.VISIBLE);

        }
        if (v == btn8)
        {

            if(tv_8.getVisibility()==View.VISIBLE)
                tv_8.setVisibility(View.GONE);
            else
                tv_8.setVisibility(View.VISIBLE);

        }
        if (v == btn9)
        {if(tv_9.getVisibility()==View.VISIBLE)
            tv_9.setVisibility(View.GONE);
        else
            tv_9.setVisibility(View.VISIBLE);
        }
        if (v == btn10)
        {

            if(tv_10.getVisibility()==View.VISIBLE)
                tv_10.setVisibility(View.GONE);
            else
                tv_10.setVisibility(View.VISIBLE);
        }

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
            case AppConstants.REQUEST_FAQ:
            {
                if (jsonResponse != null)
                {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {

                        if (jsonResponse.faqs.size() == 0)
                        {
                            dismissDialog();

                        }
                        if (jsonResponse.faqs.size() != 0)
                        {
                            dismissDialog();
                            for (int i = 0; i <jsonResponse.faqs.size(); i++)
                            {
                                btnarray.get(i).setVisibility(View.VISIBLE);
                                tvarray.get(i).setText(jsonResponse.faqs.get(i).answer.toString().trim());
                                btnarray.get(i).setText(jsonResponse.faqs.get(i).question.toString());

                            }
                            for(int i=0;i<jsonResponse.faqs.size();i++)
                            {

                                tvarray1.add(jsonResponse.faqs.get(i).answer.toString().trim());
                                btnarray1.add(jsonResponse.faqs.get(i).question.toString());
                            }
                            DatabaseManager.saveFAQ(this,jsonResponse.faqs);



                        }

                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

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
            case AppConstants.REQUEST_FAQ:
            {

                Log.i(label, "Faq:" + message);
                Log.i(label, "Faq:" + response);
            }
            break;
        }

    }
}