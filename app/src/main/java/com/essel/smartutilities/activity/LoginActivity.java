package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ServiceCaller {
    private AppCompatButton btnLogin;
    private EditText editTextUsername, editTextPassword;
    private TextInputLayout inputLayoutUsername, inputLayoutPassword;
    private TextView actionRegister, actioncontinueasguest, actionnewconnection, actionForgot;
    private ImageView fabNewConnection;
    private Toolbar mToolBar;
    private Pattern pattern;
    private Matcher matcher;
    ProgressDialog pDialog;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        return;
    }

    private void initialize() {
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        //  mToolBar.setVisibility(GONE);
        //  mToolBar.setBackgroundResource(R.drawable.background_toolbar_translucent);


        btnLogin = (AppCompatButton) findViewById(R.id.BTNLogin);
        editTextUsername = (EditText) findViewById(R.id.editConsumerId);
        editTextPassword = (EditText) findViewById(R.id.editPassword);
        inputLayoutUsername = (TextInputLayout) findViewById(R.id.inputLayoutConsumerId);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.inputLayoutPassword);
        actionRegister = (TextView) findViewById(R.id.action_register);
        actioncontinueasguest = (TextView) findViewById(R.id.action_continue_as_guest);
        actionnewconnection = (TextView) findViewById(R.id.action_apply_new_connection);
        actionForgot = (TextView) findViewById(R.id.action_forgot);
        btnLogin.setOnClickListener(this);
        actionRegister.setOnClickListener(this);
        actioncontinueasguest.setOnClickListener(this);
        actionnewconnection.setOnClickListener(this);
        actionForgot.setOnClickListener(this);

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

    @Override
    public void onClick(View v) {


        if (v == btnLogin) {
            performLogin();
            // Intent i = new Intent(this, ActivityLoginLanding.class);
            //  startActivity(i);
//            ArrayList<Consumer> consumers = Consumer.createConsumersList(10);
//            DatabaseManager.saveLoginDetails(this,consumers.get(3));

        } else if (v == actionRegister) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);

        } else if (v == actionForgot) {
            Intent i = new Intent(this, ForgotActivity.class);
            startActivity(i);
        } else if (v == actionRegister) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        } else if (v == actionnewconnection) {
            Intent in = new Intent(this, NewConnectionActivity.class);
            startActivity(in);
        } else if (v == actioncontinueasguest) {
            Intent in = new Intent(this, LandingSkipLoginActivity.class);
            startActivity(in);

        }

    }


    private boolean isBlankInput() {
        boolean b = true;
        String username = String.valueOf(editTextUsername.getText());
        if (username.equals("") || username.length() < 10 || username.length() > 20) {
            inputLayoutUsername.setError(getString(R.string.error_empty_consumer_id));
            b = false;
        } else {
            inputLayoutUsername.setError(null);
        }
        return b;

    }

    private boolean isValidPassword() {
        boolean b = true;
        String password = String.valueOf(editTextPassword.getText());
        if (password.equals("") || password.length() < 6 || password.length() > 20) {
            inputLayoutPassword.setError(getString(R.string.error_empty_password));
            b = false;
        } else {
            inputLayoutPassword.setError(null);
        }
        return b;
    }


    @Override
    public void onBackPressed() {
        DialogCreator.showExitDialog(this, "Exit App?", "Do you want to exit?");
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_LOGIN: {
                if (jsonResponse != null) {
                    System.out.println("Login Response " + jsonResponse.result);
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        if (jsonResponse.user_info != null) {
                            Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                            Log.i(label, "loginnnnnnnnnnnnnnn:" + jsonResponse.user_info);
                            dismissDialog();
                            CommonUtils.saveDetails(this, jsonResponse.user_info.consumer_no, jsonResponse.user_info.consumer_name,
                                    jsonResponse.user_info.city);
                            SharedPrefManager.saveValue(this, SharedPrefManager.ADDRESS1, jsonResponse.user_info.address);
                            SharedPrefManager.saveValue(this, SharedPrefManager.EMAIL_ID, jsonResponse.user_info.alternet_email_id);
                            SharedPrefManager.saveValue(this, SharedPrefManager.MOBILE, jsonResponse.user_info.contact_no);

                            CommonUtils.saveAuthToken(this, jsonResponse.authorization);
                            SharedPrefManager.saveValue(this, SharedPrefManager.CONSUMER_LOGGED, "true");
                            Intent i = new Intent(this, ActivityLoginLanding.class);
                            startActivity(i);
//                            if(jsonResponse.user_info.profile_img!=null) {
////                                bitmap = StringToBitMap(jsonResponse.user_info.profile_img);
////                                jsonResponse.user_info.profile_img = bitmap.toString();
//                                URL imageUrl = null;
//                                try {
//                                    imageUrl = new URL(jsonResponse.user_info.profile_img.toString());
//                                } catch (MalformedURLException e) {
//                                    e.printStackTrace();
//                                }
//                                URLConnection ucon = null;
//                                try {
//                                    ucon = imageUrl.openConnection();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                InputStream is = null;
//                                try {
//                                    is = ucon.getInputStream();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                                BufferedInputStream bis = new BufferedInputStream(is);
//
//                                ByteArrayBuffer baf = new ByteArrayBuffer(500);
//                                int current = 0;
//                                try {
//                                    while ((current = bis.read()) != -1) {
//         /* This approach slowdown the process*/
//                                        baf.append((byte) current);
//                                    }
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//
//                                byte[] img_ary= baf.toByteArray();
//                                ByteArrayInputStream imageStream = new ByteArrayInputStream(
//                                        img_ary);
//                                Bitmap theImage = BitmapFactory.decodeStream(imageStream);
//                                String img=BitMapToString(theImage);
//                                jsonResponse.user_info.profile_img=img;
//                            }
                            DatabaseManager.saveLoginDetails(this, jsonResponse.user_info);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();
                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
                        // Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, R.string.er_data_not_avaliable, Toast.LENGTH_LONG).show();
            }
            break;

        }
        dismissDialog();
    }

    public void onAsyncFail(String messages, String label, NetworkResponse response) {
        switch (label) {
            case AppConstants.REQUEST_LOGIN: {
                try {
                    String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                    if (res != null) {
                        Gson gson = new Gson();
                        JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                        if (jsonResponse.message != null)
                            DialogCreator.showMessageDialog(this, jsonResponse.message);
                    }
                } catch (Exception e) {
                    DialogCreator.showMessageDialog(this, getString(R.string.login_error_null));
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }
    }

    private void performLogin() {

        String password = editTextPassword.getText().toString();
        String consumer_id = editTextUsername.getText().toString();
        // Check for a valid password, if the user entered one.
        if (CommonUtils.isNetworkAvaliable(this) == true) {
            if (isBlankInput()) {
                inputLayoutUsername.setError("");
                if (isValidPassword()) {
                    inputLayoutPassword.setError("");
                    initProgressDialog();
                    if (pDialog != null && !pDialog.isShowing()) {
                        pDialog.setMessage("Logging in, please wait..");
                        pDialog.show();
                    }
                    JsonObjectRequest request = WebRequests.loginRequest(this, Request.Method.POST, AppConstants.URL_LOGIN, AppConstants.REQUEST_LOGIN, this, consumer_id, password);
                    App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_LOGIN);
                } else
                    Log.i("login", "Enter Valid Password");
//                    Toast.makeText(this, "Enter Valid Password", Toast.LENGTH_SHORT).show();
            } else
                Log.i("login", "Enter Valid User Name");
//                Toast.makeText(this, "Enter Valid User Name", Toast.LENGTH_SHORT).show();
        } else
            DialogCreator.showMessageDialog(this, getString(R.string.error_internet_not_connected));


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

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
}
