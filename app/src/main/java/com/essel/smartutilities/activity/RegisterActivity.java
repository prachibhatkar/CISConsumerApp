package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.essel.smartutilities.R;

import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView btnLogin;
    AppCompatButton btnNext;
    EditText editTextConsumerId;
    TextInputLayout inputLayoutConsumerId;
    Context mContext;
    ImageView fabNewConnection;
    Toolbar mToolBar;
    private Spinner sp_city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialize();
        return;
    }


    private void initialize(){

       /* mToolBar = (Toolbar) ((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        setHasOptionsMenu(true);

        mToolBar.setVisibility(View.VISIBLE);
       mToolBar.setBackgroundResource(R.drawable.background_toolbar_translucent);*/


        btnLogin = (TextView)findViewById(R.id.txt_login);
        btnNext = (AppCompatButton)findViewById(R.id.BTNNext);
        editTextConsumerId = (EditText)findViewById(R.id.editConsumerId);
        inputLayoutConsumerId = (TextInputLayout)findViewById(R.id.inputLayoutConsumerId);
        btnNext.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        fabNewConnection = (ImageView)findViewById(R.id.fab_new_connection);
        fabNewConnection.setOnClickListener(this);

        sp_city=(Spinner)findViewById(R.id.sp_city);
        String[] routes = this.getResources().getStringArray(R.array.City);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Arrays.asList(routes));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(dataAdapter);


    }

    @Override
    public void onClick(View v) {
        if(v==btnLogin){
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);

            // set the toolbar title
            mToolBar.setTitle(R.string.login);


        }

        else if (v==btnNext){
            Intent i = new Intent(this, SignupStepTwo.class);
            startActivity(i);


        }
        else if (v==fabNewConnection){
            Intent i = new Intent(this, SignupStepTwo.class);
            startActivity(i);


            // set the toolbar title
            mToolBar.setTitle(R.string.apply_for_new_connection);



        }



    }
}
