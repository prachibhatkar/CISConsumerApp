package com.bynry.cisconsumerapp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bynry.cisconsumerapp.adapter.SlidingTipsAdapter;
import com.bynry.cisconsumerapp.adapter.TipsAdapter;
import com.bynry.cisconsumerapp.callers.ServiceCaller;
import com.bynry.cisconsumerapp.db.DatabaseManager;
import com.bynry.cisconsumerapp.models.Tips;
import com.bynry.cisconsumerapp.utility.App;
import com.bynry.cisconsumerapp.utility.CommonUtils;
import com.bynry.cisconsumerapp.webservice.WebRequests;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.models.JsonResponse;
import com.bynry.cisconsumerapp.utility.AppConstants;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

//import static com.bynry.cisconsumerapp.smartutilities.R.id.indicator;

public class TipsActivity extends AppCompatActivity implements ServiceCaller
{
    private ViewPager vp_tips;
    CirclePageIndicator circlePageIndicator;

    private static final String[] IMAGES = {};
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private ArrayList<String> TipTextArray = new ArrayList<>(12);


    private TipsAdapter tipsAdapter;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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


        if (CommonUtils.isNetworkAvaliable(this))
        {
            JsonObjectRequest request = WebRequests.getTips(this, Request.Method.GET, AppConstants.URL_GET_TIPS, AppConstants.REQEST_TIPS, this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQEST_TIPS);
        } else {
            ArrayList<Tips> arraytips = new ArrayList<Tips>();
            arraytips = DatabaseManager.getTips(this);
            for (int i = 0; i < arraytips.size(); i++) {
                TipTextArray.add(arraytips.get(i).message);
                ImagesArray.add(arraytips.get(i).image);
                init();
                Toast.makeText(this.getApplicationContext(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void init()
    {
        for (int i = 0; i < IMAGES.length; i++)
        {



        }
        vp_tips = (ViewPager) findViewById(R.id.vp_tips_pager);

        vp_tips.setAdapter(new SlidingTipsAdapter(this, ImagesArray, TipTextArray));

        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(vp_tips);

        final float density = getResources().getDisplayMetrics().density;

        indicator.setRadius(5 * density);

        NUM_PAGES = IMAGES.length;


    }


    private int currentPage;
    private int NUM_PAGES = 3;
    final Runnable Update = new Runnable()
    {
        public void run() {
            if (currentPage == NUM_PAGES)
            {
                currentPage = 0;
            }
        }
    };


    public ViewPager.OnPageChangeListener onPageChangedListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


        }

        public void onPageSelected(int position)
        {
            currentPage = position;

        }


        @Override
        public void onPageScrollStateChanged(int state)
        {
        }
    };


    public void onBackPressed()
    {

        super.onBackPressed();

    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {

        switch (label) {
            case AppConstants.REQEST_TIPS:
            {
                if (jsonResponse != null)
                {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS))
                    {

                        if (jsonResponse.tips.size() != 0) {

                            for (int i = 0; i < jsonResponse.tips.size(); i++)
                            {
                                ImagesArray.add(jsonResponse.tips.get(i).image);
                                TipTextArray.add(jsonResponse.tips.get(i).message);
                                Tips tip = new Tips();
                                tip.message = jsonResponse.tips.get(i).message;

                            }
                            DatabaseManager.saveTips(this, jsonResponse.tips);
                            init();


                        }
                        if (jsonResponse.authorization != null)
                        {
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                    }
                    break;
                }
            }

        }

    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response)
    {

        switch (label)
        {
            case AppConstants.REQEST_TIPS:
            {
              Log.i(label, "tipppppppppp" + response);
            }
            break;
        }

    }

}









