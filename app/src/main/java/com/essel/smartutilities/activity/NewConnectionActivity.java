package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;

import java.util.Arrays;

/**
 * Created by hp on 11/5/2016.
 */

public class NewConnectionActivity extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private EditText editTextFullName,editTextAddress1,editTextAddress2,editTextAddress3,editTextPhone,editTextConsumerId,editTextEmailId;
    private TextInputLayout inputLayoutFullName,inputLayoutAddress1,inputLayoutAddress2,inputLayoutAddress3,inputLayoutPhone,inputLayoutConsumerId,inputLayoutEmailId;
    private Button btnActionSubmit;
    private TextView actionLogin;
    Spinner connectiontype;

protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_connection);
        mContext = this;

    initialize();
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
        btnActionSubmit = (Button)findViewById(R.id.action_submit);
        btnActionSubmit.setOnClickListener(this);
//        actionLogin = (TextView)findViewById(R.id.action_login);
//        actionLogin.setOnClickListener(this);


         connectiontype = (Spinner) findViewById(R.id.sp_connectiontype);
        String[] type = mContext.getResources().getStringArray(R.array.type);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, Arrays.asList(type));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        connectiontype.setAdapter(dataAdapter);
    }

    public void validate() {
        String fullname = editTextFullName.getText().toString().trim();
        String address1 = editTextAddress1.getText().toString().trim();
        String address2 = editTextAddress2.getText().toString().trim();
        String address3 = editTextAddress3.getText().toString().trim();
        String phoneno = editTextPhone.getText().toString().trim();
        String emailid = editTextEmailId.getText().toString().trim();
        String consumerid = editTextConsumerId.getText().toString().trim();
        if (fullname.equals("") || address1.equals("") || address2.equals("") || address3.equals("")
                || phoneno.equals("") || emailid.equals("") || consumerid.equals(""))
        {
            Toast.makeText(mContext.getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
        }

        Intent i=new Intent(this,NewConnectionActivity2.class);
        startActivity(i);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.txt_login:
//                break;
            case R.id.action_submit:
                validate();
                break;

            }
        }
 }


