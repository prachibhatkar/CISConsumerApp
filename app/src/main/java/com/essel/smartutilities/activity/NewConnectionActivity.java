package com.essel.smartutilities.activity;

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

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 11/5/2016.
 */

public class NewConnectionActivity extends BaseActivity implements View.OnClickListener, ServiceCaller {
    private Context mContext;
    private EditText editTextFullName, editTextAddress1, editTextAddress2, editTextAddress3, editTextPhone, editTextConsumerId, editTextEmailId;
    private TextInputLayout inputLayoutFullName, inputLayoutAddress1, inputLayoutAddress2, inputLayoutAddress3, inputLayoutPhone, inputLayoutConsumerId, inputLayoutEmailId;
    private Button btnActionSubmit;
    private TextView actionLogin;
    Spinner connectiontype;
    Intent i;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_connection);
        mContext = this;

        initialize();
    }

    private void initialize() {

        editTextFullName = (EditText) findViewById(R.id.editFullName);
        editTextAddress1 = (EditText) findViewById(R.id.editAddressLine1);
        editTextAddress2 = (EditText) findViewById(R.id.editAddressLine2);
        editTextAddress3 = (EditText) findViewById(R.id.editAddressLine3);
        editTextPhone = (EditText) findViewById(R.id.editPhone);
        editTextConsumerId = (EditText) findViewById(R.id.editConsumerId);
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
        String[] type = mContext.getResources().getStringArray(R.array.type);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, Arrays.asList(type));
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
        JSONObject obj = new JSONObject();
        try {
            obj.put("consumer_name", editTextFullName.getText().toString());
            obj.put("email_id", editTextEmailId.getText().toString());
            obj.put("address_line1", editTextAddress1.getText().toString());
            obj.put("address_line2", editTextAddress2.getText().toString());
            obj.put("address_line3", editTextAddress2.getText().toString());
            obj.put("mobile_no", editTextPhone.getText().toString());
            obj.put("nearest_consumer_no", editTextConsumerId.getText().toString());
            obj.put("nearest_pole_no", editTextConsumerId.getText().toString());
            obj.put("connection_type", "2");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        JsonObjectRequest request = WebRequests.addNewConnectionRequest(this, Request.Method.POST, AppConstants.URL_NEW_CONNECTION, AppConstants.REQUEST_NEW_CONNECTION, this, obj);
        App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_NEW_CONNECTION);

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
                        i = new Intent(this, NewConnectionActivity2.class);
                        startActivity(i);

                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {

                        DialogCreator.showMessageDialog(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null));
                        // Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(this, R.string.er_data_not_avaliable, Toast.LENGTH_LONG).show();
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

            }
        }
    }


}


