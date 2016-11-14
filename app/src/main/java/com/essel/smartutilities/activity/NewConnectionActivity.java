package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;

public class NewConnectionActivity extends AppCompatActivity implements View.OnClickListener{
    private Context mContext;
    private EditText editTextFullName,editTextAddress1,editTextAddress2,editTextAddress3,editTextPhone,editTextConsumerId,editTextEmailId;
    private TextInputLayout inputLayoutFullName,inputLayoutAddress1,inputLayoutAddress2,inputLayoutAddress3,inputLayoutPhone,inputLayoutConsumerId,inputLayoutEmailId;
    private AppCompatButton btnActionSubmit;
    private TextView actionLogin;
    private Toolbar mToolBar;
    private LinearLayout toolbarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_connection);
       // mToolBar = (Toolbar)findViewById(R.id.toolbar);
        // mToolBar.setVisibility(VISIBLE);
       // setSupportActionBar(mToolBar);
       // getSupportActionBar().setTitle("New Service Connection");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("New Service Connection");

        initialize();
        return;

    }

    private void initialize(){


        editTextFullName = (EditText)findViewById(R.id.editFullName);
        editTextAddress1 = (EditText)findViewById(R.id.editAddressLine1);
        editTextAddress2 = (EditText)findViewById(R.id.editAddressLine2);
        editTextAddress3 = (EditText)findViewById(R.id.editAddressLine3);
        editTextPhone = (EditText)findViewById(R.id.editPhone);
        editTextConsumerId = (EditText)findViewById(R.id.editConsumerId);
        editTextEmailId = (EditText)findViewById(R.id.editEmailId);
        inputLayoutFullName = (TextInputLayout)findViewById(R.id.inputLayoutFullName);
        inputLayoutAddress1 = (TextInputLayout)findViewById(R.id.inputLayoutAddressLine1);
        inputLayoutAddress2 = (TextInputLayout)findViewById(R.id.inputLayoutAddressLine2);
        inputLayoutAddress3 = (TextInputLayout)findViewById(R.id.inputLayoutAddressLine3);
        inputLayoutPhone = (TextInputLayout)findViewById(R.id.inputLayoutPhone);
        inputLayoutConsumerId = (TextInputLayout)findViewById(R.id.inputLayoutConsumerId);
        inputLayoutEmailId = (TextInputLayout)findViewById(R.id.inputLayoutEmailId);
        btnActionSubmit = (AppCompatButton)findViewById(R.id.action_submit);
        actionLogin = (TextView)findViewById(R.id.action_login);
        btnActionSubmit.setOnClickListener(this);
        actionLogin.setOnClickListener(this);
    }


    public void validate() {
        String fullname = editTextFullName.getText().toString().trim();
        String address1 = editTextAddress1.getText().toString().trim();
        String address2 = editTextAddress2.getText().toString().trim();
        String address3 = editTextAddress3.getText().toString().trim();
        String phoneno = editTextPhone.getText().toString().trim();
        String emailid = editTextEmailId.getText().toString().trim();
        String consumerid = editTextConsumerId.getText().toString().trim();
        if (fullname.equals("") || address1.equals("") || address2.equals("") || address3.equals("") || phoneno.equals("") || emailid.equals("") || consumerid.equals("")) {
            Toast.makeText(mContext.getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onClick(View v) {

        if(v==btnActionSubmit){
            //  int selection = gridViewConnectionTypes.getCheckedItemPosition();
            //  Log.d("",""+selection);

            validate();
        }
        else if(v==actionLogin){
            Intent i = new Intent(this,LoginActivity.class);
            startActivity(i);
        }
    }

    }

