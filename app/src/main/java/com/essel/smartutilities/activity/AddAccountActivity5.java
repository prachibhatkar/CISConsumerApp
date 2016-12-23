package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.SharedPrefManager;

public class AddAccountActivity5 extends BaseActivity implements View.OnClickListener {

    TextInputLayout inputLayoutEmailId, inputLayoutMobileNo;
    TextView textViewConsumerName, consumerno, textViewConsumerAddress, textViewConsumerConnectionType, textViewConsumerMobileNo, textViewActionResend;

    Context mContext;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account5);
        mContext = this;
        initialize();
    }

    private void initialize() {
        Button con = (Button) findViewById(R.id.btn_continue);
        Button add = (Button) findViewById(R.id.btn_addmore);
        add.setOnClickListener(this);
        con.setOnClickListener(this);
        textViewConsumerName = (TextView) findViewById(R.id.textConsumerName);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME) != null)
            textViewConsumerName.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONSUMER_NAME));

        textViewConsumerAddress = (TextView) findViewById(R.id.textConsumerAddress);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS1) != null
                && SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS2) != null
                && SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS3) != null)
            textViewConsumerAddress.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS1)
                    + " " + SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS2)
                    + " " + SharedPrefManager.getStringValue(this, SharedPrefManager.ADDRESS3));
        textViewConsumerConnectionType = (TextView) findViewById(R.id.textConsumerConnectionType);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.CONNECTION_TYPE) != null)
            textViewConsumerConnectionType.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.CONNECTION_TYPE));

        textViewConsumerMobileNo = (TextView) findViewById(R.id.textConsumerMobileNo);
        if (SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE) != null && !SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE).toString().equalsIgnoreCase(""))
            textViewConsumerMobileNo.setText(SharedPrefManager.getStringValue(this, SharedPrefManager.MOBILE));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_addmore) {
            i = new Intent(this, AddAccountActivity.class);
            startActivity(i);
        } else if (v.getId() == R.id.btn_continue) {
            i = new Intent(this, ActivityLoginLanding.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        i = new Intent(this, AddAccountActivity.class);
        startActivity(i);

    }
}
