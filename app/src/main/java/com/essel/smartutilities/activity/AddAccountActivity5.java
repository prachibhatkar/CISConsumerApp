package com.essel.smartutilities.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;

public class AddAccountActivity5 extends BaseActivity implements View.OnClickListener {

    TextInputLayout inputLayoutEmailId, inputLayoutMobileNo;
    TextView textViewConsumerName,consumerno, textViewConsumerAddress, textViewConsumerConnectionType, textViewConsumerMobileNo, textViewActionResend;

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
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
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
        super.onBackPressed();
    }
}
