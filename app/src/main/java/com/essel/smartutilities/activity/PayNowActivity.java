package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.DialogCreator;

/**
 * Created by hp on 11/5/2016.
 */

public class PayNowActivity extends BaseActivity implements View.OnClickListener {

    private TextView consumerno, consumername, propmtamt, currentamt, duedate_date, arriers, netamt, promptdate;
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
        currentamt = (TextView) findViewById(R.id.currentamount);
        duedate_date = (TextView) findViewById(R.id.duedate_date);
        arriers = (TextView) findViewById(R.id.Arriers);
        promptdate = (TextView) findViewById(R.id.promptdate);
        netamt = (TextView) findViewById(R.id.netamt);
        amtpay = (EditText) findViewById(R.id.amt_paying);
        consumerno = (TextView) findViewById(R.id.consumerno);
        // if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO) != null)
        //  consumerno.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NO));
        consumername = (TextView) findViewById(R.id.consumer_name);
        //if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME) != null)
        //  consumername.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME));
        Submit = (Button) findViewById(R.id.BTNSubmit);
        Submit.setOnClickListener(this);
        Intent intent = getIntent();
        String amount = intent.getExtras().getString("amt");
        String duedate = intent.getExtras().getString("date");
        String arrears = intent.getExtras().getString("arrears");
        String promptamt = intent.getExtras().getString("promtamt");
        String promptdat = intent.getExtras().getString("promtdate");
        String netamount = intent.getExtras().getString("netbill");
        String consumernam = intent.getExtras().getString("consumername");
        String accid = intent.getExtras().getString("accid");
        currentamt.setText(amount);
        duedate_date.setText(duedate);
        arriers.setText(arrears);
        propmtamt.setText(promptamt);
        netamt.setText(netamount);
        consumername.setText(consumernam);
        consumerno.setText(accid);
        promptdate.setText(promptdat);
        amtpay.setText(propmtamt.getText().toString().trim());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.BTNSubmit:
                if (!amtpay.getText().toString().equalsIgnoreCase("")) {
                    if (Float.parseFloat(amtpay.getText().toString()) >= Float.parseFloat(propmtamt.getText().toString()) || (Float.parseFloat(amtpay.getText().toString()) >= 1))
                        DialogCreator.showMessageDialog(this, "yesss");
                    else
                        DialogCreator.showMessageDialog(this, "Noooo");
                } else
                    DialogCreator.showMessageDialog(this, "Noooo");
                break;
        }
    }
}


