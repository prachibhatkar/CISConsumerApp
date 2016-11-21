package com.essel.smartutilities.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.essel.smartutilities.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int CAPTURE_IMAGE = 1;
    private String mFragementName;
    private Context mContext;
    //private ViewPager profile_pager;
    ExpandableRelativeLayout expandableLayout_editProfile, expandableLayout_changepass;
    Button expandableButton_editprofile,expandableButton_changepass,save_detail,save_password;
    CircleImageView circleimage;



    private TabLayout profile_tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
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
        return;


    }

    private void setupUI() {
       // profile_pager = (ViewPager) findViewById(R.id.profile_pager);
        //profile_tabs = (TabLayout) findViewById(R.id.profile_tabs);

        circleimage=(CircleImageView)findViewById(R.id.profile_image);
        circleimage.setOnClickListener(this);

        expandableButton_editprofile=(Button)findViewById(R.id.expandableButton_editprofile);
        expandableButton_editprofile.setOnClickListener(this);
        expandableButton_changepass=(Button)findViewById(R.id.expandableButton_changepass);
        expandableButton_changepass.setOnClickListener(this);
        save_detail=(Button)findViewById(R.id.BTN_save_details);
        save_password=(Button)findViewById(R.id.BTN_save_password);

        save_detail.setOnClickListener(this);
        save_password.setOnClickListener(this);
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

        if(view==circleimage){


            Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(photoCaptureIntent, CAPTURE_IMAGE);
        }
        if(view==save_detail){


            expandableLayout_editProfile.collapse();
            expandableLayout_changepass.collapse();
        }
        if(view==save_password){


            expandableLayout_editProfile.collapse();
            expandableLayout_changepass.collapse();
        }

    }

    private void loadData() {
       /* myProfileAdapter = new MyProfileAdapter(this, getSupportFragmentManager());
        profile_pager.setAdapter(myProfileAdapter);
        profile_pager.addOnPageChangeListener(onPageChangedListener);
        profile_tabs.setupWithViewPager(profile_pager);*/
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            circleimage.setImageBitmap(photo);
            //storeCameraPhotoInSDCard(photo);
            //saveImageToStorage();
            circleimage.setVisibility(View.VISIBLE);
        }
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



    public void onBackPressed() {

        Intent in =new Intent(this,ActivityLoginLanding.class);
        startActivity(in);


    }
}


