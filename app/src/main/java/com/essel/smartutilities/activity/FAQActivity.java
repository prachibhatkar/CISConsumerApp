package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.DialogCreator;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class FAQActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout linearlayout_newconnection,linearlayout_changeownership,linearlayout_changeconnectiontype,linearlayout_loadextensionreduction,linearlayout_permanantdisconnection;
    ExpandableRelativeLayout expandableLayout_newserviceconnection, expandableLayout_changeofownership,expandableLayout_changeofconnectiontype,expandableLayout_loadextensionreduction,expandableLayout_permanantdisconnect;
    Button expandablebutton_newserviceconnection, expandablebutton_changeofownership,expandablebutton_changeofconnectiontype,expandablebutton_loadextensionreduction,expandablebutton_permanantdisconnec;

    Context mContext;
    Dialog dialog_faq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("FAQ");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        initialize();
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

    private void initialize(){
        expandablebutton_newserviceconnection=(Button)findViewById(R.id.expandableButton_newconnection);
        expandablebutton_changeofownership=(Button)findViewById(R.id.expandableButton_changeofowner);
        expandablebutton_changeofconnectiontype=(Button)findViewById(R.id.expandableButton_changeofconnection);
        expandablebutton_loadextensionreduction=(Button)findViewById(R.id.expandableButton_loadextensionreduction);
        expandablebutton_permanantdisconnec=(Button)findViewById(R.id.expandableButton_permanantdisconnect);

        expandablebutton_newserviceconnection.setOnClickListener(this);
        expandablebutton_changeofownership.setOnClickListener(this);
        expandablebutton_changeofconnectiontype.setOnClickListener(this);
        expandablebutton_loadextensionreduction.setOnClickListener(this);
        expandablebutton_permanantdisconnec.setOnClickListener(this);

        expandableLayout_newserviceconnection = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_newconnection);
        expandableLayout_changeofownership = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_changeofownership);
        expandableLayout_loadextensionreduction = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_loadextensionreduction);
        expandableLayout_permanantdisconnect = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout_permanantdisconnect);
        expandableLayout_changeofconnectiontype = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_changeofconnection);

    }


    @Override
    public void onClick(View v) {


           if(v==expandablebutton_newserviceconnection) {

               expandableLayout_newserviceconnection.toggle();
           }

        if(v==expandablebutton_changeofownership) {

            expandableLayout_changeofownership.toggle();

        }
        if(v==expandablebutton_changeofconnectiontype) {

            expandableLayout_changeofconnectiontype.toggle();

        }
        if(v==expandablebutton_loadextensionreduction) {

             expandableLayout_loadextensionreduction.toggle();
        }
        if(v==expandablebutton_permanantdisconnec) {

                expandableLayout_permanantdisconnect.toggle();

        }

    }
}
