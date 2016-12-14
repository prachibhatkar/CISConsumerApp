package com.essel.smartutilities.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int CAPTURE_IMAGE = 1;
    private String mFragementName;
    private Context mContext;
    private static final int SELECT_IMAGE = 2;
    //private ViewPager profile_pager;
    ExpandableRelativeLayout expandableLayout_editProfile, expandableLayout_changepass;
    Button expandableButton_editprofile,expandableButton_changepass,save_detail,save_password;
    CircleImageView circleimage;
    private TabLayout profile_tabs;
    EditText contactno,emailid,old_pass,new_pass,confirm_pass;

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

        expandableLayout_changepass = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_changepass);
        expandableLayout_editProfile = (ExpandableRelativeLayout)findViewById(R.id.expandableLayout_editprofile);

            contactno=(EditText)findViewById(R.id.editcontactno);
            emailid=(EditText)findViewById(R.id.editEmailId);
            old_pass=(EditText)findViewById(R.id.editOldPassword);
            new_pass=(EditText)findViewById(R.id.editNewPassword);
            confirm_pass=(EditText)findViewById(R.id.editConfirmPassword);



       // String contactno = String.valueOf(conta.getText());


        save_detail.setOnClickListener(this);
        save_password.setOnClickListener(this);
    }


    public void onClick(View view) {
        if (view == expandableButton_editprofile) {

            expandableLayout_editProfile.toggle();
            expandableLayout_changepass.collapse();

            // toggle expand and collapse


        }
        if (view == expandableButton_changepass) {

            expandableLayout_changepass.toggle();
            expandableLayout_editProfile.collapse();// toggle expand and collapse
        }

        if (view == circleimage) {

            showImageOptionsDialog();
          //  Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
           // startActivityForResult(photoCaptureIntent, CAPTURE_IMAGE);
        }
        if (view == save_detail) {

                expandableLayout_editProfile.collapse();
                expandableLayout_changepass.collapse();

        }
        if (view == save_password) {
            String oldpass = String.valueOf(old_pass.getText());
            String newpass = String.valueOf(new_pass.getText());
            String confirmpass = String.valueOf(confirm_pass.getText());

            if ((oldpass.equals(" ")) || (newpass.equals("")) || (confirmpass.equals(""))) {
                Toast.makeText(this, "Please fill all fields ", Toast.LENGTH_LONG).show();
                expandableLayout_changepass.expand();
            }
                expandableLayout_editProfile.collapse();
                expandableLayout_changepass.collapse();


        }
    }


    private void showImageOptionsDialog() {
        final String CHOOSE_GALLERY = "Choose Gallery", USE_CAMERA = "Use Camera", UPLOAD_VIDEO = "upload video";
        ArrayList<String> list = new ArrayList<String>();
        final CharSequence items[];
        list.add(CHOOSE_GALLERY);
        list.add(USE_CAMERA);
        //list.add(UPLOAD_VIDEO);

        items = list.toArray(new CharSequence[list.size()]);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" Set Profile Image ");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // mRemoveImage = false;

                //PermissionUtility permissionUtility = new PermissionUtility(AdvertiseWithUsActivity.this);

                if (items[item].equals(USE_CAMERA)) {

                    Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(photoCaptureIntent, CAPTURE_IMAGE);
                } else if (items[item].equals(CHOOSE_GALLERY)) {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , SELECT_IMAGE);


                }
            }

        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            circleimage.setImageBitmap(photo);
            //storeCameraPhotoInSDCard(photo);
            //saveImageToStorage();
            circleimage.setVisibility(View.VISIBLE);
        }

        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            // circleimage.setImageBitmap(photo);
            //circleimage.setVisibility(View.VISIBLE);

            Uri selectedImage = data.getData();
            circleimage.setImageURI(selectedImage);
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



    public void onBackPressed() {

       super.onBackPressed();

    }
}


