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

import com.android.volley.NetworkResponse;
import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.PayNowActivity;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;


public class ManageAccountAdapter extends RecyclerView.Adapter<ManageAccountAdapter.ViewHolder> implements ServiceCaller {

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
                    builder1.setMessage("Are you sure you want to delete Account Of  \n  " + mConsumers.get(position).consumer_name + "  -  " +
                            mConsumers.get(position).consumer_no);
                    builder1.setCancelable(true);

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
//                                                    AppConstants.URL_NEW_CONNECTION, AppConstants.REQUEST_NEW_CONNECTION, mContext, obj, SharedPrefManager.getStringValue(mContext, SharedPrefManager.AUTH_TOKEN));
//                                            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_NEW_CONNECTION);
                                        }
                                    } else
                                        Toast.makeText(mContext, R.string.error_internet_not_connected, Toast.LENGTH_LONG).show();
                                    mConsumers.remove(viewHolder.getAdapterPosition());
                                    notifyDataSetChanged();
                                    dialog.cancel();
                                    Snackbar snack = Snackbar.make(v, "Account Deleted", Snackbar.LENGTH_LONG);
                                    snack.show();
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

    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_DELETE_ACCOUNT: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                        Log.i(label, "newrequestttttttttttttttttttttpass:" + jsonResponse.message);
                        if (jsonResponse.message != null)
                            DialogCreator.showMessageDialog(mContext,jsonResponse.message);

                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();
                        DialogCreator.showMessageDialog(mContext, jsonResponse.message != null ? jsonResponse.message :"error");
                        // Toast.makeText(this, jsonResponse.message != null ? jsonResponse.message : getString(R.string.login_error_null), Toast.LENGTH_LONG).show();
                    }
                } else
                    Toast.makeText(mContext, R.string.er_data_not_avaliable, Toast.LENGTH_LONG).show();
                dismissDialog();
            }
            break;
        }
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {
        switch (label) {
            case AppConstants.REQUEST_DELETE_ACCOUNT: {

                Log.i(label, "responseeeeeeeeeeee:" + response);
                Log.i(label, "requestttttttttttttttttttttfail:" + message);
                dismissDialog();
                break;
            }
        }
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
            String promptamt = ((SoapObject) responceArray.getProperty(0)).getProperty("PROMPT_PAYMENT_INCENTIVE").toString();
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