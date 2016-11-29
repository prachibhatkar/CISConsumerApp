package com.essel.smartutilities.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.webservice.WebRequests;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 11/5/2016.
 */

public class NewConnectionActivity extends BaseActivity implements View.OnClickListener, ServiceCaller {
    private Context mContext;
    private EditText editTextFullName,editTextpoleno, editTextAddress1, editTextAddress2, editTextAddress3, editTextPhone, editTextConsumerId, editTextEmailId;
    private TextInputLayout inputLayoutFullName, inputLayoutAddress1, inputLayoutAddress2, inputLayoutAddress3, inputLayoutPhone, inputLayoutConsumerId, inputLayoutEmailId;
    private Button btnActionSubmit;
    private TextView actionLogin;
    Spinner connectiontype;
    private ArrayList<String> mytype;
    private ProgressDialog pDialog;
    Intent i;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_connection);
        mContext = this;

        initialize();
    }

    private void initialize() {
        mytype = new ArrayList<>(12);
        mytype.add(0, "Select Connection Type");
        if (CommonUtils.isNetworkAvaliable(this)) {
            JsonObjectRequest request = WebRequests.getConnectionType(this, Request.Method.GET, AppConstants.URL_GET_CONNECTION_TYPE, AppConstants.REQUEST_GET_CONNECTION_TYPE, this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_CONNECTION_TYPE);
        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_SHORT).show();

        editTextFullName = (EditText) findViewById(R.id.editFullName);
        editTextAddress1 = (EditText) findViewById(R.id.editAddressLine1);
        editTextAddress2 = (EditText) findViewById(R.id.editAddressLine2);
        editTextAddress3 = (EditText) findViewById(R.id.editAddressLine3);
        editTextPhone = (EditText) findViewById(R.id.editPhone);
        editTextConsumerId = (EditText) findViewById(R.id.editConsumerId);
        editTextpoleno = (EditText) findViewById(R.id.editpoleno);
        editTextEmailId = (EditText) findViewById(R.id.editEmailId);
        inputLayoutFullName = (TextInputLayout) findViewById(R.id.inputLayoutFullName);
        inputLayoutAddress1 = (TextInputLayout) findViewById(R.id.inputLayoutAddressLine1);
        inputLayoutAddress2 = (TextInputLayout) findViewById(R.id.inputLayoutAddressLine2);
        inputLayoutPhone = (TextInputLayout) findViewById(R.id.inputLayoutPhone);
        inputLayoutEmailId = (TextInputLayout) findViewById(R.id.inputLayoutEmailId);
        btnActionSubmit = (Button) findViewById(R.id.action_submit);
        btnActionSubmit.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        connectiontype = (Spinner) findViewById(R.id.sp_connectiontype);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, mytype);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        connectiontype.setAdapter(dataAdapter);
    }


    public boolean validate() {
        Boolean flag = false;
        String fullname = editTextFullName.getText().toString().trim();
        String address1 = editTextAddress1.getText().toString().trim();
        String address2 = editTextAddress2.getText().toString().trim();
        String phoneno = editTextPhone.getText().toString().trim();
        String email = editTextEmailId.getText().toString().trim();
        if (!fullname.isEmpty()) {
            if (!address1.isEmpty()) {
                if (!address2.isEmpty()) {
                    if (phoneno.length() == 10 || phoneno.length() == 12) {
                        if (email.length() == 0 || emailValidator(email)) {
                            if (connectiontype.getSelectedItemPosition() != 0) {
                                flag = true;
                            } else
                                Toast.makeText(this, "Select valid Connection Type", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(this, "Enter vaild Emailid", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(this, "Enter vaild Phone no", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(this, "Enter vaild Address", Toast.LENGTH_LONG).show();

            } else
                Toast.makeText(this, "Enter vaild Address", Toast.LENGTH_LONG).show();

        } else
            Toast.makeText(this, "Enter vaild Name", Toast.LENGTH_LONG).show();


        return flag;

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
            case R.id.action_submit:
                if (validate()) {
                    submitData();
//                    i = new Intent(this, NewConnectionActivity2.class);
//                    startActivity(i);
                }
                break;

        }
    }

    private void submitData() {
        initProgressDialog();
        if (pDialog != null && !pDialog.isShowing()) {
            pDialog.setMessage("Requesting, please wait..");
            pDialog.show();
        }
        JSONObject obj = new JSONObject();
        try {
            obj.put("consumer_name", editTextFullName.getText().toString());
            obj.put("email_id", editTextEmailId.getText().toString());
            obj.put("address_line1", editTextAddress1.getText().toString());
            obj.put("address_line2", editTextAddress2.getText().toString());
            obj.put("address_line3", editTextAddress2.getText().toString());
            obj.put("mobile_no", editTextPhone.getText().toString());
            obj.put("nearest_consumer_no", editTextConsumerId.getText().toString());
            obj.put("nearest_pole_no", editTextpoleno.getText().toString());
            obj.put("connection_type", connectiontype.getSelectedItemPosition() - 1);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (CommonUtils.isNetworkAvaliable(this)) {
            JsonObjectRequest request = WebRequests.addNewConnectionRequest(this, Request.Method.POST, AppConstants.URL_NEW_CONNECTION, AppConstants.REQUEST_NEW_CONNECTION, this, obj);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_NEW_CONNECTION);
        } else
            Toast.makeText(this, R.string.error_internet_not_connected, Toast.LENGTH_SHORT).show();
    }


    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_NEW_CONNECTION: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "newconnectionrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        dismissDialog();
                        i = new Intent(this, NewConnectionActivity2.class);
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
            case AppConstants.REQUEST_GET_CONNECTION_TYPE: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        if (jsonResponse.connectiontype.size() != 0) {
                            for (int i = 1; i <= jsonResponse.connectiontype.size(); i++) {
                                mytype.add(i, jsonResponse.connectiontype.get(i - 1).type);
                            }
                            dismissDialog();

                            Log.i(label, "connectionTyperequestttttttttttttttttttttpass: " + jsonResponse.connectiontype.get(0).id);
                            Log.i(label, "connectionTyperequestttttttttttttttttttttpass:  " + jsonResponse.connectiontype.get(0).type.toString());

                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();

                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
                        dismissDialog();
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
            case AppConstants.REQUEST_NEW_CONNECTION: {
                Log.i(label, "responseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee:" + response);
                Log.i(label, "newconnectionrequestttttttttttttttttttttfail:" + message);
                dismissDialog();

            }
            break;
            case AppConstants.REQUEST_GET_CONNECTION_TYPE: {
                Log.i(label, "responseeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee:" + response);
                Log.i(label, "connectiontyperequestttttttttttttttttttttfail:" + message);
                dismissDialog();

            }
            break;
        }
    }


}