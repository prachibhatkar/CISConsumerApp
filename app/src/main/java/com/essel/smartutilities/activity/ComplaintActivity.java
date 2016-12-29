package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.ComplaintAdapter;

public class ComplaintActivity extends AppCompatActivity {
    private ViewPager vp_complaint_pager;
    private TabLayout tablayout;
    private ComplaintAdapter complaintAdapter;
    private Context mContext;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               Intent in=new Intent(this,ActivityLoginLanding.class);
//                startActivity(in);
            }
        });
        setupUI();
        loadData();


    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if(id == R.id.action_notifications){

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
       // mToolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(mToolbar);
      //  getSupportActionBar().setTitle("Complaint");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  ActionBar actionBar = getSupportActionBar();
       // if (actionBar != null) {
         // actionBar.setDisplayShowHomeEnabled(true);
            //String title = getString(R.string.complaint);
            //getSupportActionBar().setTitle(title);
      //  }


        vp_complaint_pager = (ViewPager)findViewById(R.id.vp_complaint_pager);
        tablayout = (TabLayout)findViewById(R.id.tabs);
    }

    private void loadData() {
        complaintAdapter = new ComplaintAdapter(this,getSupportFragmentManager());
        vp_complaint_pager.setAdapter(complaintAdapter);
        vp_complaint_pager.addOnPageChangeListener(onPageChangedListener);
        tablayout.setupWithViewPager(vp_complaint_pager);

    }
    ViewPager.OnPageChangeListener onPageChangedListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        public void onPageSelected(int position) {

        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void onBackPressed() {

        Intent in = new Intent(this, ActivityLoginLanding.class);
        startActivity(in);


    }

}
