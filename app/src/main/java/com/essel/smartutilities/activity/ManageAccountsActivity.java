package com.essel.smartutilities.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.ManageAccountAdapter;
import com.essel.smartutilities.adapter.PaymentHistoryAdapter;
import com.essel.smartutilities.models.Consumer;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class ManageAccountsActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rv_consumers;
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("Manage Accounts");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        add = (ImageView) findViewById(R.id.add_new);
        add.setOnClickListener(this);
        rv_consumers = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ArrayList<Consumer> consumers = Consumer.createConsumersList(10);
        ManageAccountAdapter adapter = new ManageAccountAdapter(this, consumers);
        rv_consumers.setAdapter(adapter);
        rv_consumers.setLayoutManager(layoutManager);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new: {
//                Intent i = new Intent(this, ActivityRegister.class);
//                startActivity(i);
                break;
            }
        }
    }
}