package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.NetworkResponse;
import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.ManageAccountAdapter;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.DialogCreator;

import java.util.ArrayList;

public class ManageAccountsActivity extends AppCompatActivity implements View.OnClickListener,ServiceCaller {
    RecyclerView rv_consumers;
    ImageView add, imgBack;
    ArrayList<Consumer> consumers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accounts);
        add = (ImageView) findViewById(R.id.add_new);
        add.setOnClickListener(this);
        rv_consumers = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        ArrayList<Consumer> consumers = Consumer.createConsumersList(10);
         consumers =  DatabaseManager.getAllManageAccounts(this);
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
                if(consumers.size()<=9) {
                    Intent i = new Intent(this, AddAccountActivity.class);
                    startActivity(i);
                }else
                    DialogCreator.showMessageDialog(this," Only 10 Accounts can be Added ");
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(this, ActivityLoginLanding.class);
        startActivity(i);
        App.dropdown=true;
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {

    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {

    }
}