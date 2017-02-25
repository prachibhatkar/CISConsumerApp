package com.essel.smartutilities.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.models.ComplaintId;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Complaint_Status_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Complaint_Status_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Complaint_Status_Fragment extends Fragment implements View.OnClickListener,ServiceCaller {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public ArrayList<ComplaintId> temp;
    TextView tv_consumerid, tv_complainttype, tv_complaintraised, tv_complaintstatus,tv_nocomplaintfound;
    Spinner complaintid;
    String accid,status,type;
    LinearLayout linearLayout;
    private String TAG = "responsedataaaaa";

    private OnFragmentInteractionListener mListener;
    private TextView tv_complaintmsg;
    private Context mContext;
    private ArrayList<String> arraycomplaintid;
    ProgressDialog pDialog;
    String orderid,com;
    int complaintno;


    public Complaint_Status_Fragment() {

    }


    // TODO: Rename and change types and number of parameters
    public static Complaint_Status_Fragment newInstance(int param1, String param2) {
        Complaint_Status_Fragment fragment = new Complaint_Status_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(param1));
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_complaint__status, container, false);
        mContext = getActivity();
        initialize(rootView);
        arraycomplaintid = new ArrayList<>(12);
        arraycomplaintid.add(0, "Select Complaint Id");
        complaintid = (Spinner) rootView.findViewById(R.id.sp_complaintid);
        // String[] type = mContext.getResources().getStringArray(R.array.complaintid);


        if (CommonUtils.isNetworkAvaliable(getActivity()))
        {
            String consumer_no=((SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.CONSUMER_NO)).toString());

            JsonObjectRequest request = WebRequests.getComplaintID(getActivity(), Request.Method.POST, AppConstants.URL_GET_COMPLAINT_ID, AppConstants.REQUEST_GET_COMPLAINT_ID,
                    this,consumer_no,SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_COMPLAINT_ID);
        } else
        {
            Toast.makeText(getActivity(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, arraycomplaintid);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complaintid.setAdapter(dataAdapter);
        complaintid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                 orderid=complaintid.getSelectedItem().toString();
                 complaintno=complaintid.getSelectedItemPosition();



                if (CommonUtils.isNetworkAvaliable(getActivity())&&complaintno!=0)
                {
                    initProgressDialog();
                    if (pDialog != null && !pDialog.isShowing())
                    {
                        pDialog.setMessage(" please wait..");
                        pDialog.show();
                    }
                    com=complaintid.getSelectedItem().toString();
                    tv_complaintmsg.setText(temp.get(complaintid.getSelectedItemPosition()-1).remark);
                    tv_complaintraised.setText(temp.get(complaintid.getSelectedItemPosition()-1).raised_date);
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();


                } else
                {

                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }


        });

        return rootView;

    }

    private void initialize(View rootView)
    {
        tv_consumerid = (TextView) rootView.findViewById(R.id.consumerid);
        tv_complainttype = (TextView) rootView.findViewById(R.id.complainttype);
        tv_complaintstatus = (TextView) rootView.findViewById(R.id.complaintstatus);
        tv_complaintraised = (TextView) rootView.findViewById(R.id.tv_complaintraisedate);
        tv_complaintmsg = (TextView) rootView.findViewById(R.id.tv_complaintremark);
        tv_nocomplaintfound=(TextView)rootView.findViewById(R.id.tv_nocomplaintfound);
        linearLayout=(LinearLayout)rootView.findViewById(R.id.linearlayout1);


    }


    private void initProgressDialog()
    {

        if (pDialog == null)
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog()
    {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onResume()
    {
        super.onResume();
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {


        switch (label)
        {
            case AppConstants.REQUEST_GET_COMPLAINT_ID:
            {
                if (jsonResponse != null)
                {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {


                        Log.i(label, "hygt " + jsonResponse);

                        if (jsonResponse.complaints.size()== 0)
                        {
                            complaintid.setEnabled(false);
                            tv_nocomplaintfound.setText("No Complaints Found");
                        }
                        if (jsonResponse.complaints.size() != 0)
                        {

                            temp=jsonResponse.complaints;
                            for (int i = 1; i <= jsonResponse.complaints.size(); i++)
                            {

                                ComplaintId complaintid = new ComplaintId();
                                Log.i(label, "complainttype" + jsonResponse.complaints);

                                complaintid.setComplaint_no(jsonResponse.complaints.get(i-1).getComplaint_no().toString());
                                arraycomplaintid.add(i,complaintid.complaint_no);


                            }

                        }
                        if (jsonResponse.authorization != null)
                        {
                            CommonUtils.saveAuthToken(getActivity(), jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE))
                    {
                        Toast.makeText(getContext(), jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                    }
                    break;
                }

            }
        }

    }


    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response)
    {

        switch (label)
        {
            case AppConstants.REQUEST_GET_COMPLAINT_ID:
            {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
                Log.i(label, "comp id......" + message);
                Log.i(label, "locate......." + response);
            }
            break;


        }

    }


    private class AsyncCallWS extends AsyncTask<Void, Void, Void>
    {


            @Override
            protected void onPreExecute() {
                Log.i(TAG, "onPreExecute");
            }

            @Override
            protected Void doInBackground(Void... params)
            {
                Log.i(TAG, "doInBackground");
                getComplaintStatus();
                return null;
            }

            @Override
            protected void onPostExecute(Void result)
            {
                Log.i(TAG, "onPostExecute");
                Log.i(TAG, "response data: ");


                //   Toast.makeText(LandingSkipLoginActivity.this, "Response", Toast.LENGTH_LONG).show();

            }


            public void getComplaintStatus()
            {


                String SOAP_ACTION = "http://123.63.20.164:8001/soa-infra/services/FieldMobility/getConsumerCaseDetails/getconsumercasedetailsbpelprocess_client_ep";
                String METHOD_NAME = "requestElement";
                String NAMESPACE = "http://www.example.org";
                String URL = "http://123.63.20.164:8001/soa-infra/services/FieldMobility/getConsumerCaseDetails/getconsumercasedetailsbpelprocess_client_ep";

                try
                {
                    SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

                    Request.addProperty("caseOrdId", orderid);
                    Request.addProperty("city", "Nagpur");
                    Request.addProperty("service", "Electricity");


                    SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                    soapEnvelope.dotNet = true;

                    soapEnvelope.setOutputSoapObject(Request);
                    HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
                    androidHttpTransport.debug = true;


                    try
                    {
                        androidHttpTransport.call(SOAP_ACTION, soapEnvelope);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (XmlPullParserException e1) {
                        e1.printStackTrace();
                    }
                    SoapObject responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn);
                    dismissDialog();
                     accid = ((SoapObject) soapEnvelope.bodyIn).getProperty("accId").toString();
                     status = ((SoapObject) soapEnvelope.bodyIn).getProperty("status").toString();
                     type = ((SoapObject) soapEnvelope.bodyIn).getProperty("type").toString();
                     getActivity().runOnUiThread(new Runnable()
                     {
                        @Override
                        public void run() {
                            // This code will always run on the UI thread, therefore is safe to modify UI elements.
                           // tv_consumerid.setText(accid);

                            linearLayout.setVisibility(View.VISIBLE);
                            tv_consumerid.setText(com);
                            tv_complaintstatus.setText(status);
                            if(type.equalsIgnoreCase("no power complaint"))
                               tv_complainttype.setText("No Power");
                            else  if(type.equalsIgnoreCase("MANUAL BILL ADJUSTMENT"))
                                tv_complainttype.setText("Bill Complaint");
                            else  if(type.equalsIgnoreCase("Meter Testing"))
                                tv_complainttype.setText("Meter Complaint");
                            else  if(type.equalsIgnoreCase("Phase Correction"))
                                tv_complainttype.setText("Connection Problem");
                                else
                                tv_complainttype.setText(type);
                        }
                    });



//                    Log.i(TAG, "caseId" + status);
//                    Log.i(TAG, "caseId" + type);
//                    Log.i(TAG, "caseId" + accid);
//                    Log.i(TAG, "caseId" + status);
                    // final SoapObject response = (SoapObject) soapEnvelope.getResponse();
                    // SoapPrimitive response1 = (SoapPrimitive) soapEnvelope.getResponse();


                    // SoapObject responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn).getProperty("X_BILLDTLS_TBL");
                    //  Log.i(TAG, "get : " + ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE14"));

                    // String paymenthistory=  ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE14").toString();


                } catch (Exception e)
                {
                    Log.e(TAG, "Errorrrrrrrrrrrrrrrrrrrr: " + e.getMessage());

                }

            }


            }





            // TODO: Rename method, update argument and hook method into UI event
            public void onButtonPressed(Uri uri)
            {
                if (mListener != null)
                {
                    mListener.onFragmentInteraction(uri);
                }
            }

            @Override
            public void onAttach(Context context)
            {
                super.onAttach(context);

            }

            @Override
            public void onDetach()
            {
                super.onDetach();
                mListener = null;
            }

            @Override
            public void onClick(View v)
            {

            }

            /**
             * This interface must be implemented by activities that contain this
             * fragment to allow an interaction in this fragment to be communicated
             * to the activity and potentially other fragments contained in that
             * activity.
             * <p/>
             * See the Android Training lesson <a href=
             * "http://developer.android.com/training/basics/fragments/communicating.html"
             * >Communicating with Other Fragments</a> for more information.
             */
            public interface OnFragmentInteractionListener
            {
                // TODO: Update argument type and name
                void onFragmentInteraction(Uri uri);
            }
        }



