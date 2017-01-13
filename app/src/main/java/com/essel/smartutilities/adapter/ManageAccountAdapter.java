package com.essel.smartutilities.adapter;

/**
 * Created by hp on 9/14/2016.
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.PayNowActivity;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ManageAccountAdapter extends RecyclerView.Adapter<ManageAccountAdapter.ViewHolder> {

    private List<Consumer> mConsumers;
    // Store the context for easy access
    private Context mContext;
    private OnRecycleItemClickListener mListener;
    public ProgressDialog pDialog;

    public ManageAccountAdapter(Context context, ArrayList<Consumer> consumers) {
        mConsumers = consumers;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_manage_account, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.bind(mContext, mConsumers.get(position), mListener);
        viewHolder.ic_dete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (v.getId() == R.id.ic_delete) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                    if (SharedPrefManager.getStringValue(mContext, SharedPrefManager.CONSUMER_NO).equalsIgnoreCase(mConsumers.get(position).consumer_no))
                        builder1.setMessage("This Account is Selected.Are you sure you want to delete Account of " + mConsumers.get(position).consumer_name + "  -  " +
                                mConsumers.get(position).consumer_no);
                    else
                        builder1.setMessage("Are you sure you want to delete Account of " + mConsumers.get(position).consumer_name + "  -  " +
                                mConsumers.get(position).consumer_no);

                    builder1.setCancelable(true);
                    final int p = position;
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    if (CommonUtils.isNetworkAvaliable(mContext)) {
                                        initProgressDialog();
                                        if (pDialog != null && !pDialog.isShowing()) {
                                            pDialog.setMessage("Requesting, please wait..");
                                            pDialog.show();
                                            JSONObject obj = new JSONObject();
                                            try {
                                                obj.put("consumer_no", mConsumers.get(position).consumer_no);
                                            } catch (JSONException e) {
                                                // TODO Auto-generated catch block
                                                e.printStackTrace();
                                            }
//                                            JsonObjectRequest request = WebRequests.deleteAccount(mContext, Request.Method.POST,
//                                                    AppConstants.URL_DELETE_ACCOUNT, AppConstants.REQUEST_DELETE_ACCOUNT, mContext, obj, SharedPrefManager.getStringValue(mContext, SharedPrefManager.AUTH_TOKEN));
                                            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST, AppConstants.URL_DELETE_ACCOUNT, obj, new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response) {
                                                    Log.d(AppConstants.REQUEST_DELETE_ACCOUNT, response.toString());
                                                    Gson gson = new Gson();
                                                    JsonResponse jsonResponse = gson.fromJson(response.toString(), JsonResponse.class);
                                                    DialogCreator.showMessageDialog(mContext, jsonResponse.message);
                                                    mConsumers.remove(viewHolder.getAdapterPosition());
                                                    notifyDataSetChanged();
                                                    //DatabaseManager.deleteAccount(mContext, mConsumers.get(p).consumer_no);
                                                    Snackbar snack = Snackbar.make(v, "Account Deleted", Snackbar.LENGTH_LONG);
                                                    snack.show();
                                                    dismissDialog();
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    NetworkResponse response = error.networkResponse;
                                                    if (error instanceof ServerError && response != null) {
                                                        try {
                                                            String res = new String(response.data, HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                                            VolleyLog.d(AppConstants.REQUEST_DELETE_ACCOUNT, "Error: " + res);
                                                            Gson gson = new Gson();
                                                            JsonResponse jsonResponse = gson.fromJson(res, JsonResponse.class);
                                                            DialogCreator.showMessageDialog(mContext, jsonResponse.message + "  " + jsonResponse.result);
                                                            dismissDialog();

                                                        } catch (UnsupportedEncodingException | JsonSyntaxException e1) {
                                                            DialogCreator.showMessageDialog(mContext, "ERROR");
                                                            dismissDialog();
                                                        }
                                                    } else
                                                        dismissDialog();
                                                    DialogCreator.showMessageDialog(mContext, "ERROR");
                                                }
                                            }) {
                                                @Override
                                                public Map<String, String> getHeaders() throws AuthFailureError {
                                                    HashMap<String, String> params = new HashMap<>();
                                                    params.put("Content-Type", "application/json");
                                                    params.put("Authorization", SharedPrefManager.getStringValue(mContext, SharedPrefManager.AUTH_TOKEN));
                                                    return params;
                                                }
                                            };

                                            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                            App.getInstance().addToRequestQueue(jsonObjReq, AppConstants.REQUEST_DELETE_ACCOUNT);


                                        }
                                    } else
                                        Toast.makeText(mContext, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();

                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    notifyDataSetChanged();

                }
            }
        });
        viewHolder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (v.getId() == R.id.btn_paynow) {
                    if (CommonUtils.isNetworkAvaliable(mContext)) {
                        initProgressDialog();
                        if (pDialog != null && !pDialog.isShowing()) {
                            pDialog.setMessage("Requesting, please wait..");
                            pDialog.show();
                        }
                        AsyncCallWS task = new AsyncCallWS();
                        task.execute(new String[]{mConsumers.get(position).consumer_no});
                    } else
                        Toast.makeText(mContext, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initProgressDialog() {

        if (pDialog == null) {
            pDialog = new ProgressDialog(mContext);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public int getItemCount() {
        return mConsumers.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView name, id, address, acctype, netamt, date;
        private CardView cardView;
        private ImageView ic_dete;
        private Button pay;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.consumer_name);
            id = (TextView) itemView.findViewById(R.id.consumerid);
            address = (TextView) itemView.findViewById(R.id.address);
            acctype = (TextView) itemView.findViewById(R.id.acctype);
            ic_dete = (ImageView) itemView.findViewById(R.id.ic_delete);
            pay = (Button) itemView.findViewById(R.id.btn_paynow);
        }

        public void bind(final Context context, final Consumer consumer, final OnRecycleItemClickListener listener) {

            name.setText(consumer.consumer_name);
            id.setText(consumer.consumer_no);
            address.setText(consumer.address);
            if (consumer.is_primary.equals("true")) {
                ic_dete.setVisibility(View.GONE);
                acctype.setText("(Primary)");
            } else {
                ic_dete.setVisibility(View.VISIBLE);
                acctype.setText("");
            }
        }
    }

    public interface OnRecycleItemClickListener {
        void onItemClick(Consumer consumer);
    }

    class AsyncCallWS extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... strings) {
            Log.i("manageAccounts", "doInBackground");

            getBillDetails(mContext, strings[0]);
            return null;
        }

        @Override
        protected void onPreExecute() {

            Log.i("manageAccounts", "onPreExecute");
        }

        protected void onPostExecute(Void result) {
            dismissDialog();
            Log.i("manageAccounts", "onPostExecute");
            Log.i("manageAccounts", "response data: ");

        }
    }

    public void getBillDetails(Context mContext, String con) {

        String getconsumerno = con;
        String SOAP_ACTION = "http://123.63.20.164:8001/soa-infra/services/Maharashtra/EsselCCBGetBillDetails!1.0*soa_8b795420-6bdd-4416-aa61-cf0cec7e5698/EsselCCBGetBillSvc";
        String METHOD_NAME = "InputParameters";
        String NAMESPACE = "http://xmlns.oracle.com/pcbpel/adapter/db/sp/CCBGetBillDetailsProc";
        String URL = "http://123.63.20.164:8001/soa-infra/services/Maharashtra/EsselCCBGetBillDetails!1.0*soa_8b795420-6bdd-4416-aa61-cf0cec7e5698/EsselCCBGetBillSvc";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            if (getconsumerno.length() == 10) {
                Request.addProperty("P_ACCT_ID", getconsumerno);
                Request.addProperty("P_BILL_ID", "");
                Request.addProperty("P_MTR_ID", "#E-NG");
            } else if (getconsumerno.length() == 12) {
                Request.addProperty("P_ACCT_ID", getconsumerno);
                Request.addProperty("P_BILL_ID", "");
                Request.addProperty("P_MTR_ID", "OLD#E-NG");
            }
            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            androidHttpTransport.call(SOAP_ACTION, soapEnvelope);
            final SoapObject response = (SoapObject) soapEnvelope.getResponse();
            SoapObject responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn).getProperty("X_BILLDTLS_TBL");
            String duedate = ((SoapObject) responceArray.getProperty(0)).getProperty("DUE_DT_CASH").toString();
            String currentamt = ((SoapObject) responceArray.getProperty(0)).getProperty("CURR_BILL_AMT").toString();
            String promptamt = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE8").toString();
            String promptdate = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE19").toString();
            String netbill = ((SoapObject) responceArray.getProperty(0)).getProperty("NET_BILL_PAYABLE").toString();
            String arrears = ((SoapObject) responceArray.getProperty(0)).getProperty("ATTRIBUTE20").toString();
            String consumername = ((SoapObject) responceArray.getProperty(0)).getProperty("CONSUMER_NAME").toString();
            String accid = ((SoapObject) responceArray.getProperty(0)).getProperty("ACCT_ID").toString();
            Log.i("manageAccounts", "date: " + duedate);
            Log.i("manageAccounts", "amt: " + currentamt);
            dismissDialog();
            Intent in = new Intent(mContext, PayNowActivity.class);
            in.putExtra("date", duedate);
            in.putExtra("amt", currentamt);
            in.putExtra("promtamt", promptamt);
            in.putExtra("netbill", netbill);
            in.putExtra("arrears", arrears);
            in.putExtra("consumername", consumername);
            in.putExtra("accid", accid);
            in.putExtra("promtdate", promptdate);
            mContext.startActivity(in);

        } catch (Exception e) {
            Log.e("manageAccounts", "Error: " + e.getMessage());

        }
    }

}