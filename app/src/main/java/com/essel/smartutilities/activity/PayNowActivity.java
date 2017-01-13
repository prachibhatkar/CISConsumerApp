package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;

/**
 * Created by hp on 11/5/2016.
 */

public class PayNowActivity extends BaseActivity implements View.OnClickListener
{

    private TextView consumerno, consumername, propmtamt, currentamt, duedate_date, arriers, netamt, promptdate;
    private TextInputLayout inputLayoutconsumerno;
    private Button Submit;
    private EditText amtpay;
    public static final String COMMAND = "command";
    public static final String ACCESS_CODE = "access_code";
    public static final String MERCHANT_ID = "merchant_id";
    public static final String ORDER_ID = "order_id";
    public static final String AMOUNT = "amount";
    public static final String CURRENCY = "currency";
    public static final String ENC_VAL = "enc_val";
    public static final String REDIRECT_URL = "redirect_url";
    public static final String CANCEL_URL = "cancel_url";
    public static final String RSA_KEY_URL = "rsa_key_url";

    protected void onCreate(Bundle savedInstanceState)
    {
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

    private void initialize()
    {
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
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.BTNSubmit:
                if (CommonUtils.isNetworkAvaliable(this))
                {
                    if (!amtpay.getText().toString().equalsIgnoreCase("") && !amtpay.getText().toString().equalsIgnoreCase("."))
                    {
                        if (Float.parseFloat(amtpay.getText().toString()) >= Float.parseFloat(propmtamt.getText().toString()) && (Float.parseFloat(amtpay.getText().toString()) >= 1)) {
//                            DialogCreator.showMessageDialog(this, "Can go to Payment Gateway");
                            callwebview();
                        } else
                            DialogCreator.showMessageDialog(this, "You can Pay Equal/More than Prompt Amount");
                    } else
                        DialogCreator.showMessageDialog(this, "You can Pay Equal/More than Prompt Amount");
                    break;
                }else
                    Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
        }
    }

    private void callwebview()
    {
        String vAccessCode = "4YRUXLSRO20O8NIH";//"AVVT64DC39AU91TVUA";4YRUXLSRO20O8NIH
        String vMerchantId = "2";
        String vCurrency = "INR";
        String vAmount = amtpay.getText().toString().trim();
        String redirect = "http://122.182.6.216/merchant/ccavResponseHandler.jsp";
        String cancel = "http://122.182.6.216/merchant/ccavResponseHandler.jsp";
        String rsakey = "http://122.182.6.216/merchant/GetRSA.jsp";
        Integer orderid = CommonUtils.randInt(0, 9999999);
        String od = orderid.toString();
        if (!vAccessCode.equals("") && !vMerchantId.equals("") && !vCurrency.equals("") && !vAmount.equals(""))
        {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(PayNowActivity.ACCESS_CODE, vAccessCode);
            intent.putExtra(PayNowActivity.MERCHANT_ID, vMerchantId);
            intent.putExtra(PayNowActivity.ORDER_ID, od);
            intent.putExtra(PayNowActivity.CURRENCY, vCurrency);
            intent.putExtra(PayNowActivity.AMOUNT, vAmount);
            intent.putExtra(PayNowActivity.REDIRECT_URL, redirect);
            intent.putExtra(PayNowActivity.CANCEL_URL, cancel);
            intent.putExtra(PayNowActivity.RSA_KEY_URL, rsakey);

            startActivity(intent);

        }
    }
}
