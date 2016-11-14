package com.essel.smartutilities.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.TipsAdapter;

public class TipsActivity extends AppCompatActivity {
    private ViewPager vp_tips;
    private TipsAdapter tipsAdapter;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tips");
        toolbar.setTitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        setupUI();
        loadData();
    }

    private void setupUI() {
        vp_tips = (ViewPager)findViewById(R.id.vp_tips_pager);

    }

    private void loadData() {
        tipsAdapter = new TipsAdapter(this, getSupportFragmentManager());
        vp_tips.setAdapter(tipsAdapter);
        vp_tips.addOnPageChangeListener(onPageChangedListener);


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
}
