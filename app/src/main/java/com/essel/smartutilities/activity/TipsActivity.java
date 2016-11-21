package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.TipsAdapter;

import com.viewpagerindicator.CirclePageIndicator;

//import static com.essel.smartutilities.R.id.indicator;

public class TipsActivity extends AppCompatActivity {
    private ViewPager vp_tips;
    private TabLayout tabLayout;
    CirclePageIndicator circlePageIndicator;



    private TipsAdapter tipsAdapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
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
    }

    private void setupUI() {
        vp_tips = (ViewPager)findViewById(R.id.vp_tips_pager);
        circlePageIndicator=(CirclePageIndicator)findViewById(R.id.indicator);

       //tabLayout=(TabLayout)findViewById(R.id.tablayout);


    }

    private int currentPage;
    private int NUM_PAGES=3;
    final Runnable Update = new Runnable() {
        public void run() {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            //pager.setCurrentItem(currentPage++, true);
        }
    };

    private void loadData() {
        tipsAdapter = new TipsAdapter(this, getSupportFragmentManager());
        vp_tips.setAdapter(tipsAdapter);
        vp_tips.addOnPageChangeListener(onPageChangedListener);
        circlePageIndicator.setViewPager(vp_tips);

        // tabLayout.setupWithViewPager(vp_tips);


    }


   // circlePageIndicator.OnScrollChangeListener(new ViewPager.OnPageChangeListener() {
    ViewPager.OnPageChangeListener onPageChangedListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {





        }

        public void onPageSelected(int position) {
            currentPage=position;


        }


        @Override
        public void onPageScrollStateChanged(int state) {


        }
    };

    public void onBackPressed() {

        Intent in =new Intent(this,ActivityLoginLanding.class);
        startActivity(in);


    }
}
