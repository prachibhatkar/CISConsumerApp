package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by hp on 11/4/2016.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, ServiceCaller {
    TextView btnLogin;
    Button btnNext;
    EditText editTextConsumerId;
    TextInputLayout inputLayoutConsumerId;
    Context mContext;
    RelativeLayout rl;
    ProgressDialog pDialog;
    private Spinner sp_city;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        rl = (RelativeLayout) findViewById(R.id.relative);
        btnNext = (Button) findViewById(R.id.BTNNext);
        editTextConsumerId = (EditText) findViewById(R.id.consumerno);
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

    private void initProgressDialog() {

        if (pDialog == null) {
            pDialog = new ProgressDialog(this);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
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
        if (sp_city.getSelectedItemPosition() != 0) {
            if (editTextConsumerId.getText().toString().trim().length() >= 10 &&
                    editTextConsumerId.getText().toString().trim().length() <= 20) {
                callRegister();
            } else
                Toast.makeText(this, "Enter valid Consumer No.", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Select valid city", Toast.LENGTH_SHORT).show();

    }

    void callRegister() {
        if (CommonUtils.isNetworkAvaliable(this)) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage("Requesting, please wait..");
                pDialog.show();
            }
            JSONObject obj = new JSONObject();
            try {
                obj.put("consumer_no", editTextConsumerId.getText().toString());
                obj.put("city", sp_city.getSelectedItem().toString());
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            JsonObjectRequest request = WebRequests.getRequestRegister(this, Request.Method.POST, AppConstants.URL_GET_REGISTER, AppConstants.REQUEST_REGISTER, this, obj);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_REGISTER);

        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_REGISTER: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "newconnectionrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        if(jsonResponse.message!=null)
                        Toast.makeText(this, jsonResponse.message.toString(), Toast.LENGTH_SHORT).show();
                        CommonUtils.saveDetails(this, editTextConsumerId.getText().toString().trim(), "", String.valueOf(sp_city.getSelectedItem()));
                        SharedPrefManager.saveValue(this, SharedPrefManager.ID, jsonResponse.id);
                        dismissDialog();
                        Intent i = new Intent(this, RegisterActivity2.class);
                        startActivity(i);

                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();
                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
                        // Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, R.string.er_data_not_avaliable, Toast.LENGTH_LONG).show();
                dismissDialog();
            }
            break;
        }
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {
        switch (label) {
            case AppConstants.REQUEST_REGISTER: {

                Log.i(label, "responseeeeeeeeeeee:" + response);
                Log.i(label, "newconnectionrequestttttttttttttttttttttpass:" + message);
                dismissDialog();
                break;
            }
        }
    }
}