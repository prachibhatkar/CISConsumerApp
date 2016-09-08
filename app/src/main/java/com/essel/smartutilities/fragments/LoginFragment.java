package com.essel.smartutilities.fragments;

/**
 * Created by imac on 25/04/16.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.ActivityMainSL;
import com.essel.smartutilities.activity.ForgotActivity;
import com.essel.smartutilities.activity.LandingLoginActivity;
import com.essel.smartutilities.utility.CommonUtils;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton btnLogin;
    private EditText editTextUsername, editTextPassword;
    private TextInputLayout inputLayoutUsername, inputLayoutPassword;
    private TextView actionRegister;
    private Context mContext;
    private ImageView fabNewConnection, actionForgot;
    private Toolbar mToolBar;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initialize(rootView);
        return rootView;
    }


    private void initialize(View rootView) {
        mToolBar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        mToolBar.setVisibility(View.GONE);
        //  mToolBar.setBackgroundResource(R.drawable.background_toolbar_translucent);


        btnLogin = (AppCompatButton) rootView.findViewById(R.id.BTNLogin);
        editTextUsername = (EditText) rootView.findViewById(R.id.editConsumerId);
        editTextPassword = (EditText) rootView.findViewById(R.id.editPassword);
        inputLayoutUsername = (TextInputLayout) rootView.findViewById(R.id.inputLayoutConsumerId);
        inputLayoutPassword = (TextInputLayout) rootView.findViewById(R.id.inputLayoutPassword);
        actionRegister = (TextView) rootView.findViewById(R.id.action_register);
        fabNewConnection = (ImageView) rootView.findViewById(R.id.fab_new_connection);
        actionForgot = (ImageView) rootView.findViewById(R.id.action_forgot);

        btnLogin.setOnClickListener(this);
        actionRegister.setOnClickListener(this);
        fabNewConnection.setOnClickListener(this);
        actionForgot.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            if (isBlankInput()) {
                if (isValidPassword()) {
                    String email=editTextUsername.getText().toString();
                    String password=editTextPassword.getText().toString();

                    CommonUtils.hideKeyBoard(getActivity());
                    CommonUtils.saveCredentials(getActivity(),email,password);

                    Intent i = new Intent(mContext, LandingLoginActivity.class);
                    startActivity(i);

                }
            }
        } else if (v == actionRegister) {
            Fragment fragment = new RegisterFragment();
            ((ActivityMainSL) mContext).addFragment(fragment, true);
        } else if (v == fabNewConnection) {
            Fragment fragment = new NewConnectionFragment();
            ((ActivityMainSL) mContext).addFragment(fragment, true);
        } else if (v == actionForgot) {
            Intent i = new Intent(mContext, ForgotActivity.class);
            startActivity(i);
        }

    }


    private boolean isBlankInput() {
        boolean b = true;
        String username = String.valueOf(editTextUsername.getText());
        if (TextUtils.isEmpty(username)) {
            inputLayoutUsername.setError(getString(R.string.error_empty_consumer_id));

            b = false;
        } else {
            inputLayoutUsername.setError(null);
        }

        String password = String.valueOf(editTextPassword.getText());
        if (TextUtils.isEmpty(password)) {
            inputLayoutPassword.setError(getString(R.string.error_empty_password));
            b = false;
        } else {
            inputLayoutPassword.setError(null);
        }
        return b;

    }

    private boolean isValidPassword() {
        boolean p = false;

        final EditText Password = (EditText) inputLayoutPassword.findViewById(R.id.editPassword);

        int PLength = Password.length();

        if (PLength >= 6 && PLength <= 10) {
            p = true;
        } else {
            Toast.makeText(mContext.getApplicationContext(), "Password should have 6-10 characters", Toast.LENGTH_SHORT).show();
        }
        return p;
    }




    @Override
    public void onDestroyView() {
        mToolBar.setBackgroundResource(R.color.colorPrimary);
        super.onDestroyView();
    }
}
