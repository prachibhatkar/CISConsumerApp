package com.essel.smartutilities.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.ContactUsAdapter;

public class Contact_Us_Activity extends AppCompatActivity {
    private static boolean flag1;
    private ViewPager vp_contact_pager;
    private static TabLayout tablayout;
    private ContactUsAdapter contactUsAdapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__us_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contact Us");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setupUI();
        loadData();
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
public static void changeposition(){






}



}