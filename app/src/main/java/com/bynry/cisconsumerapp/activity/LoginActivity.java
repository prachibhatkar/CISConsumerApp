package com.bynry.cisconsumerapp.activity;

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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.bynry.cisconsumerapp.callers.ServiceCaller;
import com.bynry.cisconsumerapp.utility.DialogCreator;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.models.JsonResponse;
import com.bynry.cisconsumerapp.utility.AppConstants;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, ServiceCaller
{
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
    protected void onCreate(Bundle savedInstanceState)
    {
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

    private void initProgressDialog()
    {

        if (pDialog == null)
        {
            pDialog = new ProgressDialog(this);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog()
    {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onClick(View v)
    {

        if (v == btnLogin)
        {
            Intent i = new Intent(this, ActivityLoginLanding.class);
            startActivity(i);

        } else if (v == actionRegister)
        {
            Intent i = new Intent(this, RegisterGetUserActivity.class);
            startActivity(i);

        } else if (v == actionForgot)
        {
            Intent i = new Intent(this, ForgotRequestActivity.class);
            startActivity(i);
        } else if (v == actionRegister)
        {
            Intent i = new Intent(this, RegisterGetUserActivity.class);
            startActivity(i);
        } else if (v == actionnewconnection)
        {
            Intent in = new Intent(this, NewConnectionRequestActivity.class);
            startActivity(in);
        } else if (v == actioncontinueasguest)
        {
            Intent in = new Intent(this, LandingSkipLoginActivity.class);
            startActivity(in);

        }

    }


    private boolean isBlankInput()
    {
        inputLayoutPassword.setError("");
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

    private boolean isValidPassword()
    {
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
    public void onBackPressed()
    {
        DialogCreator.showExitDialog(this, "Exit App?", "Do you want to exit?");
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {
        switch (label) {
            case AppConstants.REQUEST_LOGIN: {

            }
            break;

        }

    }

    public void onAsyncFail(String messages, String label, NetworkResponse response)
    {
        switch (label) {

        }
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
