package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.ManageAccountAdapter;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.Consumer;

import java.util.ArrayList;

public class ManageAccountsActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rv_consumers;
    ImageView add, imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);
        add = (ImageView) findViewById(R.id.add_new);
        add.setOnClickListener(this);
        rv_consumers = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        ArrayList<Consumer> consumers = Consumer.createConsumersList(10);
        ArrayList<Consumer> consumers =  DatabaseManager.getAllManageAccounts(this);
        ManageAccountAdapter adapter = new ManageAccountAdapter(this, consumers);
        rv_consumers.setAdapter(adapter);
        rv_consumers.setLayoutManager(layoutManager);
//        DatabaseManager.saveManageAccounts(this, consumers);
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
                Intent i = new Intent(this, AddAccountActivity.class);
                startActivity(i);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this, ActivityLoginLanding.class);
        startActivity(i);
    }
}