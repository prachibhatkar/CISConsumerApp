package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;

import java.util.Arrays;

/**
 * Created by hp on 11/4/2016.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    TextView btnLogin;
    Button btnNext;
    EditText editTextConsumerId;
    TextInputLayout inputLayoutConsumerId;
    Context mContext;
    RelativeLayout rl;
    ImageView fabNewConnection;
    private Spinner sp_city;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        rl = (RelativeLayout) findViewById(R.id.relative);
//        btnLogin = (TextView) findViewById(R.id.txt_login);
        btnNext = (Button) findViewById(R.id.BTNNext);
        editTextConsumerId = (EditText) findViewById(R.id.editPassword);
        inputLayoutConsumerId = (TextInputLayout) findViewById(R.id.inputLayoutConsumerId);
        btnNext.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sp_city = (Spinner) findViewById(R.id.sp_city);
        String[] routes = mContext.getResources().getStringArray(R.array.City);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, Arrays.asList(routes));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.txt_login:
//                break;
            case R.id.BTNNext:
                validate();
                break;
//            case R.id.fab_new_connection: {
//                Snackbar snack = Snackbar.make(rl, "fhbvvbdbvbdhbvdb", Snackbar.LENGTH_LONG);
//                view = snack.getView();
//                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
//                params.gravity = Gravity.TOP;
//                view.setLayoutParams(params);
//                snack.show();
//            }
        }


    }

    public void validate() {
        if (sp_city.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select valid city", Toast.LENGTH_SHORT).show();
            if (editTextConsumerId.getText().toString().length() == 0)
            { Toast.makeText(this, "Enter valid Consumer ID", Toast.LENGTH_SHORT).show();}

            Intent i = new Intent(mContext, RegisterActivity2.class);
            startActivity(i);

        }
    }
}