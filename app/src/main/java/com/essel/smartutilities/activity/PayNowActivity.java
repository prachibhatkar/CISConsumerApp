package com.essel.smartutilities.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;

/**
 * Created by hp on 11/5/2016.
 */

public class PayNowActivity extends BaseActivity implements View.OnClickListener {

    private TextView consumerno,consumername,propmtamt;
    private TextInputLayout inputLayoutconsumerno;
    private Button Submit;
    private EditText amtpay;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_now);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initialize();
    }

    private void initialize() {
        propmtamt = (TextView) findViewById(R.id.promptamt);
        amtpay = (EditText) findViewById(R.id.amt_paying);
        amtpay.setText(propmtamt.getText().toString().trim());
        consumerno = (TextView) findViewById(R.id.consumerno);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO) != null)
            consumerno.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
        consumername = (TextView) findViewById(R.id.consumer_name);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME) != null)
            consumername.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME));
        Submit = (Button) findViewById(R.id.BTNSubmit);
        Submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTNSubmit:
                        if(Integer.parseInt(amtpay.getText().toString())>=Integer.parseInt(propmtamt.getText().toString()))
                            DialogCreator.showMessageDialog(this,"yesss");
                else
                            DialogCreator.showMessageDialog(this,"Noooo");

                break;
        }
    }
}


