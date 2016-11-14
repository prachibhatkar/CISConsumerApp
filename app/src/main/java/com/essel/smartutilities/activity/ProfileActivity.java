package com.essel.smartutilities.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.essel.smartutilities.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private String mFragementName;
    private Context mContext;
    //private ViewPager profile_pager;
    ExpandableRelativeLayout expandableLayout_editProfile, expandableLayout_changepass;
    Button expandableButton_editprofile,expandableButton_changepass;



    private TabLayout profile_tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.my_profile);
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setupUI();
        loadData();
        return;


    }

    private void setupUI() {
       // profile_pager = (ViewPager) findViewById(R.id.profile_pager);
        //profile_tabs = (TabLayout) findViewById(R.id.profile_tabs);

        expandableButton_editprofile=(Button)findViewById(R.id.expandableButton_editprofile);
        expandableButton_editprofile.setOnClickListener(this);
        expandableButton_changepass=(Button)findViewById(R.id.expandableButton_changepass);
        expandableButton_changepass.setOnClickListener(this);
    }


    public void onClick(View view) {
        if(view==expandableButton_editprofile){
            expandableLayout_editProfile = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout_editprofile);
            expandableLayout_editProfile.toggle(); // toggle expand and collapse


        }
        if(view==expandableButton_changepass){


            expandableLayout_changepass = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_changepass);
            expandableLayout_changepass.toggle(); // toggle expand and collapse
        }

    }

    private void loadData() {
       /* myProfileAdapter = new MyProfileAdapter(this, getSupportFragmentManager());
        profile_pager.setAdapter(myProfileAdapter);
        profile_pager.addOnPageChangeListener(onPageChangedListener);
        profile_tabs.setupWithViewPager(profile_pager);*/
    }

  /*  ViewPager.OnPageChangeListener onPageChangedListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };*/
}


