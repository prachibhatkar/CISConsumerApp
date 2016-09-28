package com.essel.smartutilities.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.essel.smartutilities.R;

public class ActivityMyBill extends AppCompatActivity {
    Button btn_paynow,btn_billhistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bill);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Bill");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        intialize();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_call) {
            return true;
        }

        if(id == R.id.action_notifications){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void intialize(){

        btn_paynow = (Button)findViewById(R.id.btn_paynow);
        btn_billhistory = (Button)findViewById(R.id.btn_billhistory);


    }
}
