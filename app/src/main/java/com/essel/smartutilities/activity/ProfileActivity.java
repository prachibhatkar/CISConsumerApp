package com.essel.smartutilities.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.GetInfo;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener, ServiceCaller {
    private static final int CAPTURE_IMAGE = 1;
    private String mFragementName;
    private Context mContext;
    private static final int SELECT_IMAGE = 2;
    //private ViewPager profile_pager;
    ExpandableRelativeLayout expandableLayout_editProfile, expandableLayout_changepass;
    Button expandableButton_editprofile, expandableButton_changepass, save_detail, save_password;
    CircleImageView circleimage;
    private TabLayout profile_tabs;
    ProgressDialog pDialog;
    EditText contactno, emailid, old_pass, new_pass, confirm_pass;
    TextView consemer_name, consumer_number, consumer_add, consumer_add1;
    String profileimage;
    Bitmap bitmap;
    Consumer con1 = new Consumer();
    String img1;

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
        circleimage = (CircleImageView) findViewById(R.id.profile_image);
        circleimage.setOnClickListener(this);
        Consumer con = new Consumer();
        con = DatabaseManager.getImage(this);
        if (con.profile_img != null && !con.profile_img.equals("") && con.profile_img.startsWith("http://")) {


//             bitmap=StringToBitMap(con.profile_img);
//             circleimage.setImageBitmap(bitmap);
            Picasso.with(this)
                    .load(con.profile_img)

                    .into(circleimage);

        } else if (con.profile_img != null && !con.profile_img.equals("")) {

            bitmap = StringToBitMap(con.profile_img);
            circleimage.setImageBitmap(bitmap);

        }

        expandableButton_editprofile = (Button) findViewById(R.id.expandableButton_editprofile);
        expandableButton_editprofile.setOnClickListener(this);
        expandableButton_changepass = (Button) findViewById(R.id.expandableButton_changepass);
        expandableButton_changepass.setOnClickListener(this);
        save_detail = (Button) findViewById(R.id.BTN_save_details);
        save_password = (Button) findViewById(R.id.BTN_save_password);

        expandableLayout_changepass = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_changepass);
        expandableLayout_editProfile = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout_editprofile);


        GetInfo get = new GetInfo();
        String isprimary = "true";
        get = DatabaseManager.getProfileinfo(this, isprimary);
        String consumerno = get.consumerno;
        String consumername = get.consumername;
        String consumeraddress = get.consumeraddress;
        String consumermobno = get.mobileno;

        contactno = (EditText) findViewById(R.id.editcontactno);
        contactno.setText(consumermobno);
        emailid = (EditText) findViewById(R.id.editconsumeremailid);
        emailid.setText((SharedPrefManager.getStringValue(this, SharedPrefManager.EMAIL_ID)).toString());
        old_pass = (EditText) findViewById(R.id.editOldPassword);
        new_pass = (EditText) findViewById(R.id.editNewPassword);
        confirm_pass = (EditText) findViewById(R.id.editConfirmPassword);


        consemer_name = (TextView) findViewById(R.id.textConsumerName);
        consemer_name.setText(consumername);

        consumer_number = (TextView) findViewById(R.id.textConsumerno);
        consumer_number.setText(consumerno);

        consumer_add = (TextView) findViewById(R.id.textConsumerAddress);
        consumer_add.setText(consumeraddress);
        consumer_add1 = (TextView) findViewById(R.id.textConsumerAddress1);
        //consumer_add1.setText(consumeraddress);


        save_detail.setOnClickListener(this);
        save_password.setOnClickListener(this);


    }

    public Bitmap StringToBitMap(String img1) {
        try {
            byte[] encodeByte = Base64.decode(img1, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

    }

    private void initProgressDialog() {

        if (pDialog == null) {
            pDialog = new ProgressDialog(this);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onClick(View view) {
        if (view == expandableButton_editprofile) {

            expandableLayout_editProfile.toggle();
            expandableLayout_changepass.collapse();


        }
        if (view == expandableButton_changepass) {

            expandableLayout_changepass.toggle();
            expandableLayout_editProfile.collapse();
        }

        if (view == circleimage) {
            showImageOptionsDialog();
        }
        if (view == save_detail) {


            if (contactno.equals("") || contactno.length() != 10) {

                Toast.makeText(this, "Please fill all fields ", Toast.LENGTH_LONG).show();

            } else if (emailid.getText().length() == 0 || !CommonUtils.emailValidator(emailid.getText().toString())) {
                Toast.makeText(this, "Enter valid Email", Toast.LENGTH_SHORT).show();

            } else
                saveDetails();


//            expandableLayout_editProfile.collapse();
//            expandableLayout_changepass.collapse();
        }
        if (view == save_password) {

            if (validate()) {
                callchangepass();

            }
          /*  String oldpass = String.valueOf(old_pass.getText());
            String newpass = String.valueOf(new_pass.getText());
            String confirmpass = String.valueOf(confirm_pass.getText());

            if ((oldpass.equals(" ")) || (newpass.equals("")) || (confirmpass.equals(""))) {
                Toast.makeText(this, "Please fill correct passsword ", Toast.LENGTH_LONG).show();
                expandableLayout_changepass.expand();
            }

           else if(oldpass.length()<6||oldpass.length()>20||newpass.length()<6||newpass.length()>20) {


                Toast.makeText(this, " password should have atleast 6 characters", Toast.LENGTH_LONG).show();

            }
          //  else if(confirmpass.toString().trim().compareTo(newpass.toString().trim())!= 0){

            else {
                 callchangepass();
//                expandableLayout_editProfile.collapse();
//                expandableLayout_changepass.collapse();
            }
            }*/
        }
    }

    private void saveDetails() {
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            JSONObject obj = new JSONObject();
            try {
                obj.put("alternate_mobile", contactno.getText().toString() == null ? "" : contactno.getText().toString());
                obj.put("alternate_email", emailid.getText().toString() == null ? "" : emailid.getText().toString());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest request = WebRequests.getRequestOtpforAdd(this, Request.Method.POST, AppConstants.URL_CONTACT_INFO, AppConstants.REQUEST_CONTACT_INFO, this, obj, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_CONTACT_INFO);

        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    private void callchangepass() {
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            JSONObject obj = new JSONObject();
            try {
                obj.put("old_password", old_pass.getText().toString() == null ? "" : old_pass.getText().toString());
                obj.put("new_password", new_pass.getText().toString() == null ? "" : new_pass.getText().toString());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest request = WebRequests.getRequestOtpforAdd(this, Request.Method.POST, AppConstants.URL_CHANGE_PASS, AppConstants.REQUEST_CHANGE_PASS, this, obj, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_CHANGE_PASS);

        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    private void showImageOptionsDialog() {
        final String CHOOSE_GALLERY = "Choose Gallery", USE_CAMERA = "Use Camera", UPLOAD_VIDEO = "upload video";
        ArrayList<String> list = new ArrayList<String>();
        final CharSequence items[];
        list.add(CHOOSE_GALLERY);
        list.add(USE_CAMERA);

        items = list.toArray(new CharSequence[list.size()]);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(" Set Profile Image ");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals(USE_CAMERA)) {

                    Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(photoCaptureIntent, CAPTURE_IMAGE);
                } else if (items[item].equals(CHOOSE_GALLERY)) {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, SELECT_IMAGE);


                }
            }

        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            circleimage.setImageBitmap(photo);
            profileimage = CommonUtils.getBitmapEncodedString(photo);
            //storeCameraPhotoInSDCard(photo);
            //saveImageToStorage();
            circleimage.setVisibility(View.VISIBLE);
            if (CommonUtils.isNetworkAvaliable(this)) {
                initProgressDialog();
                if (pDialog != null && !pDialog.isShowing()) {
                    pDialog.setMessage(" please wait..");
                    pDialog.show();
                }
                JSONObject obj = new JSONObject();
                try {
                    obj.put("consumer_id", consumer_number.getText().toString() == null ? "" : consumer_number.getText().toString());
                    obj.put("profile_pic", profileimage.toString() == null ? "" : profileimage.toString());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JsonObjectRequest request = WebRequests.profileimg(this, Request.Method.POST, AppConstants.URL_POST_PROFILE_IMG, AppConstants.REQUEST_POST_PROFILE_IMG, this, obj, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
                App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_POST_PROFILE_IMG);

            } else
                Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();

        }

        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK) {

            Uri selectedImage = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            circleimage.setImageURI(selectedImage);

            profileimage = CommonUtils.getBitmapEncodedString(bitmap);
            if (CommonUtils.isNetworkAvaliable(this)) {
                initProgressDialog();
                if (pDialog != null && !pDialog.isShowing()) {
                    pDialog.setMessage(" please wait..");
                    pDialog.show();
                }
                JSONObject obj = new JSONObject();
                try {
                    obj.put("image_name", consumer_number.getText().toString() == null ? "" : consumer_number.getText().toString().concat(".JPEG"));
                    obj.put("profile_pic", profileimage.toString() == null ? "" : profileimage.toString());
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                JsonObjectRequest request = WebRequests.profileimg(this, Request.Method.POST, AppConstants.URL_POST_PROFILE_IMG, AppConstants.REQUEST_POST_PROFILE_IMG, this, obj, SharedPrefManager.getStringValue(this, SharedPrefManager.AUTH_TOKEN));
                App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_POST_PROFILE_IMG);

            }
        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }


    private void loadData() {

    }

    public void onBackPressed() {

        super.onBackPressed();

    }

    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_CONTACT_INFO: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "newrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        if (jsonResponse.message != null)
                            Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();
                        SharedPrefManager.saveValue(this, SharedPrefManager.EMAIL_ID, emailid.getText().toString());
                        SharedPrefManager.saveValue(this, SharedPrefManager.ALTERNATE_MOB_NO, contactno.getText().toString());

                        GetInfo get = new GetInfo();
                        get.mobileno = contactno.getText().toString();
                        DatabaseManager.updateProfile(this, get);
                        // contactno.setText(get.mobileno);
                        expandableLayout_editProfile.collapse();
                        dismissDialog();

                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();
                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
                        // Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, R.string.er_data_not_avaliable, Toast.LENGTH_LONG).show();
                dismissDialog();
            }
            break;
            case AppConstants.REQUEST_CHANGE_PASS: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "newrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        if (jsonResponse.message != null)
                            Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();
                        dismissDialog();
                        expandableLayout_changepass.collapse();

                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();
                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
                        Toast.makeText(this, "Please check old Password", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, R.string.er_data_not_avaliable, Toast.LENGTH_LONG).show();
                dismissDialog();
            }

            case AppConstants.REQUEST_POST_PROFILE_IMG: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "newrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        Consumer consumer = new Consumer();
                        consumer.profile_img = CommonUtils.getBitmapEncodedString(bitmap);
                        // consumer.profile_img=circleimage.toString();
                        DatabaseManager.saveImage(this, consumer);
                        if (jsonResponse.message != null)
                            Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();
                        dismissDialog();


                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();
                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
                        Toast.makeText(this, "Fill Correct Data", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, R.string.er_data_not_avaliable, Toast.LENGTH_LONG).show();
                dismissDialog();
            }
        }
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {
        switch (label) {
            case AppConstants.REQUEST_CONTACT_INFO: {

                Log.i(label, "responseeeeeeeeeeee:" + response);
                Log.i(label, "requestttttttttttttttttttttfail:" + message);
                dismissDialog();
                break;
            }
            case AppConstants.REQUEST_CHANGE_PASS: {

                Log.i(label, "responseeeeeeeeeeee:" + response);
                Log.i(label, "requestttttttttttttttttttttfail:" + message);
                Toast.makeText(this, "Fill Correct Data", Toast.LENGTH_SHORT).show();
                dismissDialog();
                break;
            }
            case AppConstants.REQUEST_POST_PROFILE_IMG: {

                Log.i(label, "responseeeeeeeeeeee:" + response);
                Log.i(label, "requestttttttttttttttttttttfail:" + message);
                dismissDialog();
                break;
            }
        }
    }


    private boolean validate() {
        Boolean flag = false;
        if (emailid.getText().length() == 0 || CommonUtils.emailValidator(emailid.getText().toString())) {
            if (contactno.getText().length() == 10 || contactno.getText().length() == 12 || contactno.getText().length() == 0) {
                if (old_pass.getText().toString().trim().length() >= 6 && old_pass.getText().toString().trim().length() <= 20) {
                    if (new_pass.getText().toString().trim().length() >= 6 && new_pass.getText().toString().trim().length() <= 20) {
                        if (confirm_pass.getText().length()==0 ||confirm_pass.getText().length()>=6 ||confirm_pass.getText().length()<=20) {
                            if (confirm_pass.getText().toString().trim().compareTo(new_pass.getText().toString().trim()) == 0) {
                                flag = true;
                            } else
                                Toast.makeText(this, "Password Does not Match", Toast.LENGTH_SHORT).show();
                            } else
                                Toast.makeText(this, "Please Enter all Fields", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(this, "Please Enter all Fields", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(this, "Please Enter all Fields", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Retype valid Mobile", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Please Enter valid Email", Toast.LENGTH_SHORT).show();
            return flag;
        }
    }



