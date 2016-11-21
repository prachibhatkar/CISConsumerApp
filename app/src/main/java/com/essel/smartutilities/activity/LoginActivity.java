package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton btnLogin;
    private EditText editTextUsername, editTextPassword;
    private TextInputLayout inputLayoutUsername, inputLayoutPassword;
    private TextView actionRegister,actioncontinueasguest,actionnewconnection,actionForgot;
    private Context mContext;
    private ImageView fabNewConnection ;
    private Toolbar mToolBar;
    private Pattern pattern;
    private Matcher matcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        return;

    }


    private void initialize() {
        mToolBar = (Toolbar)findViewById(R.id.toolbar);
      //  mToolBar.setVisibility(GONE);
        //  mToolBar.setBackgroundResource(R.drawable.background_toolbar_translucent);


        btnLogin = (AppCompatButton)findViewById(R.id.BTNLogin);
        editTextUsername = (EditText)findViewById(R.id.editConsumerId);
        editTextPassword = (EditText)findViewById(R.id.editPassword);
        inputLayoutUsername = (TextInputLayout)findViewById(R.id.inputLayoutConsumerId);
        inputLayoutPassword = (TextInputLayout)findViewById(R.id.inputLayoutPassword);
        actionRegister = (TextView)findViewById(R.id.action_register);
        actioncontinueasguest = (TextView)findViewById(R.id.action_continue_as_guest);
        actionnewconnection = (TextView)findViewById(R.id.action_apply_new_connection);
        actionForgot = (TextView)findViewById(R.id.action_forgot);
        btnLogin.setOnClickListener(this);
        actionRegister.setOnClickListener(this);
        actioncontinueasguest.setOnClickListener(this);
        actionnewconnection.setOnClickListener(this);
        actionForgot.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if (v == btnLogin) {
            if (isBlankInput()) {
                String password=editTextPassword.getText().toString();
                if (isValidPassword(password)) {
                    String consumer_id=editTextUsername.getText().toString();
                    CommonUtils.hideKeyBoard(this);
//                    CommonUtils.saveCredentials(this,consumer_id,password);
                    Intent i = new Intent(this, ActivityLoginLanding.class);
                    startActivity(i);
                String password = editTextPassword.getText().toString();
                String consumer_id = editTextUsername.getText().toString();
                CommonUtils.hideKeyBoard(this);
                CommonUtils.saveCredentials(this, consumer_id, password);
                Intent i = new Intent(this, ActivityLoginLanding.class);
                startActivity(i);

            }
                else{
                 //Toast.makeText(this.getApplicationContext(), "Enter correct password", Toast.LENGTH_SHORT).show();



                }

        } else if (v == actionRegister) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);

        }

         else if (v == actionForgot) {
            Intent i = new Intent(this, ForgotActivity.class);
            startActivity(i);
        }
        else if (v == actionRegister) {
            Intent i = new Intent(this, RegisterActivity.class);
            startActivity(i);
        }
        else if (v == actionnewconnection) {
            Intent in = new Intent(this, NewConnectionActivity.class);
            startActivity(in);
        }

        else if (v == actioncontinueasguest) {
            Intent in = new Intent(this, LandingSkipLoginActivity.class);
            startActivity(in);

        }

    }


    private boolean isBlankInput() {
        boolean b = true;
       String username = String.valueOf(editTextUsername.getText());
       if (username.equals("")||username.length()<10||username.length()>20){

           inputLayoutUsername.setError(getString(R.string.error_empty_consumer_id));
            b = false;
       }


       else {
            inputLayoutUsername.setError(null);
       }

        String password = String.valueOf(editTextPassword.getText());
        if (password.equals("")||password.length()<6||password.length()>20) {
           inputLayoutPassword.setError(getString(R.string.error_empty_password));
            b = false;
        } else {
            inputLayoutPassword.setError(null);
        }
        return b;

    }

   /* private boolean isValidPassword(final String password) {


      String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        boolean p = false;

       final EditText Password = (EditText) inputLayoutPassword.findViewById(R.id.editPassword);
        matcher = pattern.matcher(password);
       return matcher.matches();


    }*/


    public void onBackPressed() {
         DialogCreator.showExitDialog(this,getString(R.string.exit),getString(R.string.exit_message));
        }

}
