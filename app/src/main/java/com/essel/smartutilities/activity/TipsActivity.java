package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.android.volley.toolbox.NetworkImageView;
import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.SlidingTipsAdapter;
import com.essel.smartutilities.adapter.TipsAdapter;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.fragments.TipOneFragment;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.models.Tips;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.webservice.WebRequests;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

//import static com.essel.smartutilities.R.id.indicator;

public class TipsActivity extends AppCompatActivity implements ServiceCaller {
    private ViewPager vp_tips;
    private TabLayout tabLayout;
    CirclePageIndicator circlePageIndicator;
    NetworkImageView nv;
    String imagesurl;
    // ImageView img1;

    private static final String[] IMAGES = {};
    private static final String[] TipText = {"tujhjhhioi", "fghhhjgjjk", "fghjjhjkjkjh"};
    private ArrayList<String> ImagesArray = new ArrayList<String>();
    private ArrayList<String> TipTextArray = new ArrayList<>(12);


    private TipsAdapter tipsAdapter;
    private Context mContext;
  //  private int NUM_PAGES = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
       // nv = (NetworkImageView) findViewById(R.id.img1);
        //img1=(ImageView)findViewById(R.id.img1);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        if (CommonUtils.isNetworkAvaliable(this)) {
            JsonObjectRequest request = WebRequests.getTips(this, Request.Method.GET, AppConstants.URL_GET_TIPS, AppConstants.REQEST_TIPS, this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQEST_TIPS);
        } else
        {
             Tips tip1=new Tips();
            tip1=DatabaseManager.getTips(this);
            TipTextArray.add(tip1.message);
            init();

            Toast.makeText(this.getApplicationContext(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
       // init();
       // intext();

     //    setupUI();
     //    loadData();

    }
    }

   private void init() {
        for (int i = 0; i < IMAGES.length; i++) {

          //  ImagesArray.add(imagesurl);
           // ImagesArray.add(IMAGES[i]);

        }
            vp_tips = (ViewPager) findViewById(R.id.vp_tips_pager);

           vp_tips.setAdapter(new SlidingTipsAdapter(this, ImagesArray, TipTextArray));
      // vp_tips.setAdapter(new SlidingTipsAdapter(this, ImagesArray));

        //  mPager.setPageTransformer(true, new DepthPageTransform());
          CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(vp_tips);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = IMAGES.length;


    }

    private void intext() {
        for (int i = 0; i < TipText.length; i++)
           // TipTextArray.add(TipText[i]);

          vp_tips = (ViewPager) findViewById(R.id.vp_tips_pager);


        vp_tips.setAdapter(new SlidingTipsAdapter(this, ImagesArray, TipTextArray));
       // vp_tips.setAdapter(new SlidingTipsAdapter(this, ImagesArray));

        //  mPager.setPageTransformer(true, new DepthPageTransform());
        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(vp_tips);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = TipText.length;

    }


    private void setupUI() {
        vp_tips = (ViewPager) findViewById(R.id.vp_tips_pager);
        circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);



        //tabLayout=(TabLayout)findViewById(R.id.tablayout);


    }

    private int currentPage;
    private int NUM_PAGES = 3;
    final Runnable Update = new Runnable() {
        public void run() {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            //pager.setCurrentItem(currentPage++, true);
        }
    };

    private void loadData() {
         tipsAdapter = new TipsAdapter(this,getSupportFragmentManager());
         vp_tips.setAdapter(tipsAdapter);
         vp_tips.addOnPageChangeListener(onPageChangedListener);
         circlePageIndicator.setViewPager(vp_tips);

        // tabLayout.setupWithViewPager(vp_tips);


    }


    // circlePageIndicator.OnScrollChangeListener(new ViewPager.OnPageChangeListener() {
    public ViewPager.OnPageChangeListener onPageChangedListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

              //  frag();

        }

        public void onPageSelected(int position) {
            currentPage = position;


        }


        @Override
        public void onPageScrollStateChanged(int state) {




        }
    };

    public void frag() {

        Fragment fragment = new TipOneFragment();
        FragmentManager fragmanager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmanager.beginTransaction();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();


    }


    public void onBackPressed() {

        super.onBackPressed();

    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {

        switch (label) {
            case AppConstants.REQEST_TIPS: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {

                        if(jsonResponse.tips.size()!=0) {

                            for(int i=0;i<jsonResponse.tips.size();i++) {

                                // imagesurl = jsonResponse.tips.get(0).image;
                                ImagesArray.add(jsonResponse.tips.get(i).image);
                                TipTextArray.add(jsonResponse.tips.get(i).message);
                                Tips tip=new Tips();
                                //tip.images=ImagesArray;
                                //tip.text=TipTextArray;

                               // tip.text=TipTextArray;
                                tip.message=jsonResponse.tips.get(i).message;
                                DatabaseManager.saveTips(this,tip);
                                Log.i(label, "Tipppppppppppp:" + imagesurl);
                            }

                               // nv.setImageUrl(imagesurl, this.getLoaderManager());
                               /* Picasso.with(this)
                                        .load(imagesurl)
                                        .resize(100, 100)
                                        .into(img1));*/
                            init();
                          //  intext();

                            Log.i(label, "Tipppppppppppp:" + jsonResponse.tips);


                        }
                        if (jsonResponse.authorization != null) {
                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
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
    public void onAsyncFail(String message, String label, NetworkResponse response) {

        switch (label) {
            case AppConstants.REQEST_TIPS: {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
//                Log.i(label, "Faq:" + message);
                Log.i(label, "tipppppppppp" + response);
            }
            break;
        }

    }

}









