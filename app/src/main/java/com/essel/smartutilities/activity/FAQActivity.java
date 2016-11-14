package com.essel.smartutilities.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.DialogCreator;

public class FAQActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout linearlayout_newconnection,linearlayout_changeownership,linearlayout_changeconnectiontype,linearlayout_loadextensionreduction,linearlayout_permanantdisconnection;
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

    private void initialize(){
        linearlayout_newconnection=(LinearLayout)findViewById(R.id.linear_layout_newconnection);
        linearlayout_changeconnectiontype=(LinearLayout)findViewById(R.id.linear_layout_changeconnection);
        linearlayout_changeownership=(LinearLayout)findViewById(R.id.linear_layout_changeownership);
        linearlayout_loadextensionreduction=(LinearLayout)findViewById(R.id.linear_layout_loadextensionreduction);
        linearlayout_permanantdisconnection=(LinearLayout)findViewById(R.id.linear_layout_permanantdisconnection);

        linearlayout_newconnection.setOnClickListener(this);
        linearlayout_changeconnectiontype.setOnClickListener(this);
        linearlayout_changeownership.setOnClickListener(this);
        linearlayout_loadextensionreduction.setOnClickListener(this);
        linearlayout_permanantdisconnection.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.linear_layout_newconnection:
                DialogCreator.showMessageDialog(mContext,getString(R.string.documents));
                break;
            case R.id.linear_layout_changeconnection:
                DialogCreator.showMessageDialog(mContext,getString(R.string.documents));
                break;
            case R.id.linear_layout_changeownership:
                DialogCreator.showMessageDialog(mContext,getString(R.string.documents));
                break;
            case R.id.linear_layout_loadextensionreduction:
                DialogCreator.showMessageDialog(mContext,getString(R.string.documents));
                break;
            case R.id.linear_layout_permanantdisconnection:
                DialogCreator.showMessageDialog(mContext,getString(R.string.documents));
                break;
        }

    }
}
