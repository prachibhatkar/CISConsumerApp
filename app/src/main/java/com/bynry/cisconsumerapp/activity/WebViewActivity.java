package com.bynry.cisconsumerapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.bynry.cisconsumerapp.utility.DialogCreator;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.utility.RSAUtility;
import com.bynry.cisconsumerapp.utility.ServiceHandler;
import com.bynry.cisconsumerapp.utility.ServiceUtility;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 04-01-2017.
 */

public class WebViewActivity extends BaseActivity
{
    private ProgressDialog dialog;
    String html, encVal;
    Intent mainIntent;
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
        setContentView(R.layout.activity_webview);
        mainIntent = getIntent();

        // Calling async task to get display content
        new RenderView().execute();

    }
    private class RenderView extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            dialog = new ProgressDialog(WebViewActivity.this);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();

            // Making a request to url and getting response
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(WebViewActivity.
                    ACCESS_CODE, mainIntent.getStringExtra(WebViewActivity.ACCESS_CODE)));
            params.add(new BasicNameValuePair(WebViewActivity.ORDER_ID, mainIntent.getStringExtra(PayNowActivity.ORDER_ID)));

            String vResponse = sh.makeServiceCall(mainIntent.getStringExtra(WebViewActivity.RSA_KEY_URL), ServiceHandler.POST, params);
            Log.i("Web View payment","Resp"+vResponse);
            if(!vResponse.equals("")&&vResponse.indexOf("ERROR")==-1){
                StringBuffer vEncVal = new StringBuffer("");
                vEncVal.append(ServiceUtility.addToPostParams(WebViewActivity.AMOUNT, mainIntent.getStringExtra(WebViewActivity.AMOUNT)));
                vEncVal.append(ServiceUtility.addToPostParams(WebViewActivity.CURRENCY, mainIntent.getStringExtra(WebViewActivity.CURRENCY)));
                encVal = RSAUtility.encrypt(vEncVal.substring(0,vEncVal.length()-1), vResponse);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (dialog.isShowing())
                dialog.dismiss();

            @SuppressWarnings("unused")
            class MyJavaScriptInterface
            {
                @JavascriptInterface
                public void processHTML(String html)
                {
                    // process the html as needed by the app
                    String status = null;
                    if(html.indexOf("Failure")!=-1){
                        status = "Transaction Declined!";
                    }else if(html.indexOf("Success")!=-1){
                        status = "Transaction Successful!";
                    }else if(html.indexOf("Aborted")!=-1){
                        status = "Transaction Cancelled!";
                    }else{
                        status = "Status Not Known!";
                    }
                    Toast.makeText(getApplicationContext(), status, Toast.LENGTH_SHORT).show();
                    DialogCreator.showMessageDialog(getApplicationContext(),status);
                }
            }

            final WebView webview = (WebView) findViewById(R.id.webview);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");
            webview.setWebViewClient(new WebViewClient()
            {
                @Override
                public void onPageFinished(WebView view, String url)
                {
                    super.onPageFinished(webview, url);
                    if(url.indexOf("/ccavResponseHandler.php")!=-1)
                    {
                        webview.loadUrl("javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
                    }
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    Toast.makeText(getApplicationContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                }
            });

			/* An instance of this class will be registered as a JavaScript interface */
            StringBuffer params = new StringBuffer();
            params.append(ServiceUtility.addToPostParams(WebViewActivity.ACCESS_CODE,mainIntent.getStringExtra(WebViewActivity.ACCESS_CODE)));
            params.append(ServiceUtility.addToPostParams(WebViewActivity.MERCHANT_ID,mainIntent.getStringExtra(WebViewActivity.MERCHANT_ID)));
            params.append(ServiceUtility.addToPostParams(WebViewActivity.ORDER_ID,mainIntent.getStringExtra(WebViewActivity.ORDER_ID)));
            params.append(ServiceUtility.addToPostParams(WebViewActivity.REDIRECT_URL,mainIntent.getStringExtra(WebViewActivity.REDIRECT_URL)));
             params.append(ServiceUtility.addToPostParams(WebViewActivity.CANCEL_URL,mainIntent.getStringExtra(WebViewActivity.CANCEL_URL)));
            try
            {
                params.append(ServiceUtility.addToPostParams(WebViewActivity.ENC_VAL, URLEncoder.encode(encVal,"UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            String vPostParams = params.substring(0,params.length()-1);
            try
            {
                webview.postUrl(ServiceUtility.TRANS_URL, EncodingUtils.getBytes(vPostParams, "UTF-8"));
            } catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"Exception occured while opening webview.",Toast.LENGTH_SHORT).show();
            }
        }
    }
    }
