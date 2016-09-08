package com.essel.smartutilities.webservice;


import android.os.AsyncTask;
import android.util.Log;


import com.essel.smartutilities.widget.MMException;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public abstract class BaseHttpHelper extends AsyncTask<Void, Void, BaseResponse>  {
    private static final String TAG = "BaseHttpHelper";
    private String mApi="";
    protected String mJsonInput;
    public ResponseHelper mResponseHelper;
    public String mApiType;

    @Override
    protected BaseResponse doInBackground(Void... pParams) {

        String lResult = sendRequest();
        if (lResult == null)
            return null;
        Log.v(TAG, "Response" + lResult);

        try {
            return retriveResponse(lResult);
        } catch (MMException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected BaseHttpHelper(String Type,String ApiName,String inputParameter,ResponseHelper responseHelper) {
        mApiType = Type;
        mApi=ApiName;
        mJsonInput=inputParameter;
        mResponseHelper = responseHelper;
    }

    /**
     * @return
     */
    public String sendRequest() {

        /*
        try {


            URL mUrl = new URL(mApi);

            HttpURLConnection lHttpUrlConnection = (HttpURLConnection) mUrl.openConnection();
            lHttpUrlConnection.setDoOutput(true);
            lHttpUrlConnection.setDoInput(true);
            lHttpUrlConnection.setRequestMethod("POST");
            lHttpUrlConnection.setRequestProperty("Content-Type", "application/json");
            lHttpUrlConnection.setRequestProperty("Accept", "application/json");


            Log.v(TAG, "Api:" + mApi);
            Log.v(TAG, "Request:" + mJsonInput);

            OutputStream os = lHttpUrlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF8"));
            writer.write(mJsonInput);
            writer.flush();
            writer.close();
            os.close();


            if (lHttpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (lHttpUrlConnection.getInputStream())));

            String output;
            StringBuilder lResponse = new StringBuilder();

            while ((output = br.readLine()) != null) {
                lResponse.append(output);
                lResponse.append('\r');
            }
            Log.v(TAG, "" + lResponse);
            lHttpUrlConnection.disconnect();

            return lResponse.toString();

        } catch (IOException e) {

            e.printStackTrace();

        } */


        try {

            URL mUrl = new URL(mApi);
            HttpURLConnection lHttpUrlConnection = (HttpURLConnection) mUrl.openConnection();

            lHttpUrlConnection.setRequestProperty("Content-Type", "application/json");
            lHttpUrlConnection.setRequestProperty("Accept", "application/json");
            lHttpUrlConnection.setRequestProperty("app-platform", "android");
            lHttpUrlConnection.setRequestProperty("App-version", "1.0");
            //  lHttpUrlConnection.setRequestProperty("auth-key", "25");
            lHttpUrlConnection.setDoInput(true);

            Log.d(TAG, "Api:" + mApi);
            Log.d(TAG, "Request:" + mJsonInput);

            lHttpUrlConnection.setRequestMethod("POST");

            if (mApiType.equalsIgnoreCase(ApiConstants.GET)){
                lHttpUrlConnection.setRequestMethod("GET");
                lHttpUrlConnection.connect();
             }else{

                lHttpUrlConnection.setDoOutput(true);
                OutputStream lOutputStream = lHttpUrlConnection.getOutputStream();
                lOutputStream.write(mJsonInput.getBytes());
                lOutputStream.flush();
            }

            int code = lHttpUrlConnection.getResponseCode();

            if (lHttpUrlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.i("HTTP CHECK", "Response Code=" + lHttpUrlConnection.getResponseCode());
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (lHttpUrlConnection.getInputStream())));

            String output;
            StringBuilder lResponse = new StringBuilder();

            Log.d(TAG, "Response Start");

            while ((output = br.readLine()) != null) {

                lResponse.append(output);
                lResponse.append('\r');
            }
            Log.d(TAG, "Response:" + lResponse);
            lHttpUrlConnection.disconnect();
            return lResponse.toString();

        } catch (IOException e) {

            e.printStackTrace();

        }

            return null;
    }


    private List<NameValuePair> getInputParameters(String jsonString){

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        try {
            JSONObject obj= new JSONObject(jsonString);
            Iterator<String> iter = obj.keys();
            while (iter.hasNext()) {
                String key = iter.next();
                String value = obj.getString(key);
                params.add(new BasicNameValuePair(key, value));
            }
        }catch (Exception e){

        }
        return params;
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    /**
     * @author spotonsoft
     */
    public interface ResponseHelper {
        public void onSuccess(BaseResponse pResponse);
        public void onFail(BaseResponse pBaseResponse);
    }

    /**
     *
     */
    protected abstract void invokeAPI();

     /**
     * Parse response to respective object
     *
     * @param pResponse
     * @return
     */
    private BaseResponse retriveResponse(String pResponse) throws MMException {
        BaseResponse lBaseResponse = null;

        if (mApi.equalsIgnoreCase(ApiConstants.LOGIN_URL))
            return lBaseResponse;

        return lBaseResponse;
    }

    @Override
    protected void onPostExecute(BaseResponse pResult) {
        super.onPostExecute(pResult);
        if (pResult == null) {
            mResponseHelper.onFail(null);
            return;
        }
        if (pResult.ismStatus()) {
            mResponseHelper.onFail(pResult);
            return;
        }
        mResponseHelper.onSuccess(pResult);
        return;


    }
}