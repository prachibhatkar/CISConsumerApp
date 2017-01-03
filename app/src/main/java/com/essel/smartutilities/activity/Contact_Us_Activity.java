package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.ContactUsAdapter;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;

public class Contact_Us_Activity extends AppCompatActivity {
    private static boolean flag1;
    private static ViewPager vp_contact_pager;
    private static TabLayout tablayout;
    private ContactUsAdapter contactUsAdapter;
    private Context mContext;
    static int i,i1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__us_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setupUI();

        loadData();

        if(ServiceStatusActivity.flag==true) {
            Intent intent = getIntent();
            i = intent.getExtras().getInt("value");
            vp_contact_pager.setCurrentItem(1);
            ServiceStatusActivity.flag=false;

        }

        }

//
//    public static void get(){
//
//        vp_contact_pager.setCurrentItem(1);
//
//    }


    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.top_right_call, menu);
//        if(i1==1){
//
//            getMenuInflater().inflate(R.menu.top_right_faq, menu);
//        }
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_call) {

            String phnno=(SharedPrefManager.getStringValue(this, SharedPrefManager.HELPLINENO)).toString();
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phnno));
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupUI() {
        vp_contact_pager = (ViewPager)findViewById(R.id.vp_contact_pager);
        tablayout = (TabLayout)findViewById(R.id.tabs);
    }

    private void loadData() {
         contactUsAdapter = new ContactUsAdapter(this, getSupportFragmentManager());
         contactUsAdapter.getItem(1);
         vp_contact_pager.setAdapter(contactUsAdapter);
         vp_contact_pager.addOnPageChangeListener(onPageChangedListener);
         tablayout.setupWithViewPager(vp_contact_pager);
         //i1=tablayout.getSelectedTabPosition();



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

        super.onBackPressed();


    }



}
