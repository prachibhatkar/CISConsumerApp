package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.CustomExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext;
    private Button btn_submit_service;
    private EditText edit_remark;
   ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Service Request");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        btn_submit_service = (Button) findViewById(R.id.btn_submit_service);
        btn_submit_service.setOnClickListener(this);

      // expListView = (ExpandableListView)findViewById(R.id.expListView);

       // prepareListData();
//
       // listAdapter = new CustomExpandableListAdapter(this,listDataHeader,listDataChild);

       // expListView.setAdapter(listAdapter);

        //Log.i("groups", listDataHeader.toString());
       // Log.i("details", listDataChild.toString());

        // Listview Group click listener
       // expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            /*@Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/

        // Listview Group expanded listener
        /*expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });*/

        // Listview Group collasped listener
    /*  expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });*/

        // Listview on child click listener
      /*  expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(),listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
        return;
    }

    /*private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("New Service Connection");
        listDataHeader.add("Change of Ownership");
        listDataHeader.add("Change of Connection Type");
        listDataHeader.add("Load Extension Reduction");
        listDataHeader.add("Permanent Disconnection");
        listDataHeader.add("Name/Address Correction");



        // Adding child data
        List<String> NewServiceConnection = new ArrayList<String>();
        NewServiceConnection.add("Subtype 1");
        NewServiceConnection.add("Subtype 2");

        List<String> ChangeOfOwnership = new ArrayList<String>();
        ChangeOfOwnership.add("Subtype 1");
        ChangeOfOwnership.add("Subtype 2");

        List<String> ChangeOfConnectionType = new ArrayList<String>();
        ChangeOfConnectionType.add("Subtype 1");
        ChangeOfConnectionType.add("Subtype 2");

        List<String> LoadExtensionReduction = new ArrayList<String>();
        LoadExtensionReduction.add("Subtype 1");
        LoadExtensionReduction.add("Subtype 2");

        List<String> PermanentDisconnection = new ArrayList<String>();
        PermanentDisconnection.add("Subtype 1");
        PermanentDisconnection.add("Subtype 2");

        List<String> Name_AddrCorrection = new ArrayList<String>();
        Name_AddrCorrection.add("Subtype 1");
        Name_AddrCorrection.add("Subtype 2");

        listDataChild.put(listDataHeader.get(0), NewServiceConnection); // Header, Child data
        listDataChild.put(listDataHeader.get(1), ChangeOfOwnership);
        listDataChild.put(listDataHeader.get(2), ChangeOfConnectionType);
        listDataChild.put(listDataHeader.get(3), LoadExtensionReduction);
        listDataChild.put(listDataHeader.get(4), PermanentDisconnection);
        listDataChild.put(listDataHeader.get(5), Name_AddrCorrection);

    }*/


        @Override
        public void onClick (View v){
            if (v == btn_submit_service) {
                Intent in = new Intent(this, ServiceStatusActivity.class);
                startActivity(in);

            }

        }
    }
