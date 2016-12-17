package com.essel.smartutilities.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.GetComplaintIdActivity;
import com.essel.smartutilities.activity.ServiceStatusActivity;
import com.essel.smartutilities.adapter.PaymentHistoryAdapter;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.Complaints;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.models.PaymentHistory;
import com.essel.smartutilities.models.ServiceType;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link Raise_Complaint_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Raise_Complaint_Fragment extends Fragment implements View.OnClickListener,ServiceCaller {

    private static final int CAPTURE_IMAGE=1;
    private int count = 0;
    ImageView iv;
    Spinner complainttype;
    Button btn_submitcomplaint;
    String complaintremark;
    EditText complaint_remark;
    String image;
    private String TAG = "responsedataaaaa";
    private ArrayList<String> complaints;
    Complaints complaint = new Complaints();
    @Override
    public void onClick(View v) {

        if(v==iv){

        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoCaptureIntent, CAPTURE_IMAGE);

    }

        if(v==btn_submitcomplaint) {
         //  if (isBlankInput()) {



                JSONObject obj = new JSONObject();
                try {
                    obj.put("complainttype", complainttype.getSelectedItemPosition());
                    obj.put("consumer_remark", complaintremark);
                    obj.put("complaint_img", image);

                } catch (JSONException e) {
                    e.printStackTrace();
               }

                if( CommonUtils.isNetworkAvaliable(getActivity())) {
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();
                   // JsonObjectRequest request = WebRequests.addComplaint(getActivity(), Request.Method.POST, AppConstants.URL_POST_ADD_COMPLAINT, AppConstants.REQUEST_POST_ADD_COMPLAINT,
                       //     this,obj,SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.AUTH_TOKEN));

                 //   App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_POST_ADD_COMPLAINT);
                }else
                    Toast.makeText(getActivity(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();




                //  Intent i = new Intent(getActivity(),GetComplaintIdActivity.class);
               // startActivity(i);


           // }

        }
    }

    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            Log.i(TAG, "doInBackground");
            getComplaintId();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            Log.i(TAG, "response data: ");

            //   Toast.makeText(LandingSkipLoginActivity.this, "Response", Toast.LENGTH_LONG).show();

        }
    }


    public void getComplaintId() {

        String getconsumerno = (SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.CONSUMER_NO)).toString();
        String getconsumercity = (SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.CONSUMER_CITY)).toString();
        String getconsumermobileno = (SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.MOBILE)).toString();
        String remark =  String.valueOf(complaint_remark.getText());
        String SOAP_ACTION = "http://123.63.20.164:8001/soa-infra/services/FieldMobility/CreateComplaint!2.0*soa_5bb8bf43-cb4e-4110-9180-3fda9cacc35e/createcomplaintbpelprocess_client_ep";
        String METHOD_NAME = "requestElement";
        String NAMESPACE = "http://www.example.org";
        String URL = "http://123.63.20.164:8001/soa-infra/services/FieldMobility/CreateComplaint!2.0*soa_5bb8bf43-cb4e-4110-9180-3fda9cacc35e/createcomplaintbpelprocess_client_ep";

        try {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            // if(getconsumerno.length()==10) {
            Request.addProperty("accId", "1000039175");
            Request.addProperty("city", "Nagpur");
            Request.addProperty("service", "Electricity");
            Request.addProperty("caseType", "Meter Burnt Complaint");
            Request.addProperty("mobile", "9766325977");
            Request.addProperty("remark", "Bill Adjustment");
            ///  }
           /* else{
                Request.addProperty("P_ACCT_ID", getconsumerno);
                Request.addProperty("P_BILL_ID", "");
                Request.addProperty("P_MTR_ID", "OLD#E-NG");
            }*/

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;

            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;


            androidHttpTransport.call(SOAP_ACTION, soapEnvelope);
            SoapObject responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn);

            String caseid =((SoapObject) soapEnvelope.bodyIn).getProperty("caseId").toString();
            String message =((SoapObject) soapEnvelope.bodyIn).getProperty("message").toString();
            Log.i(TAG, "caseId" +caseid);
            Log.i(TAG, "caseId" +message);

            DatabaseManager.saveComplaint(getActivity(),complaint);
            Intent in = new Intent(getActivity(), GetComplaintIdActivity.class);
            in.putExtra("caseid", caseid);
            in.putExtra("message", message);
            startActivity(in);

           // final SoapObject response = (SoapObject) soapEnvelope.getResponse();
           // SoapPrimitive response1 = (SoapPrimitive) soapEnvelope.getResponse();



           // SoapObject responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn).getProperty("X_BILLDTLS_TBL");
          //  Log.i(TAG, "get : " + ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE14"));

           // String paymenthistory=  ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE14").toString();







        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());

        }


    }





    private boolean isBlankInput() {
        boolean b = true;
        complaintremark = String.valueOf(complaint_remark.getText());
        if (complaintremark.equals("")){
            Toast.makeText(getContext(), "Enter Remark", Toast.LENGTH_LONG).show();

            b = false;
        }


        if (complainttype.getSelectedItemPosition()== 0) {
            Toast.makeText(getContext(), "Select complaint type", Toast.LENGTH_LONG).show();
            b = false;
        }
        return b;

    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(photo);
            //storeCameraPhotoInSDCard(photo);
            //saveImageToStorage();
            iv.setVisibility(View.VISIBLE);
            image=CommonUtils.getBitmapEncodedString(photo);


          //  ByteArrayOutputStream baos = new ByteArrayOutputStream();
           /* bm.compress(Bitmap.CompressFormat.JPEG, 100,baos);
            byte[] b = baos.toByteArray();
            byte[] encodedImage = Base64.encode(b, Base64.DEFAULT);
            image=encodedImage.toString().trim();*/
        }


    }




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ImageView imageView ;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    LinearLayout  linear_layout_imageview;
    ImageView img;
    Context mContext;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Raise_Complaint_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Raise_Complaint_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Raise_Complaint_Fragment newInstance(int param1, String param2) {
        Raise_Complaint_Fragment fragment = new Raise_Complaint_Fragment();
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
        mContext = getActivity();




        View rootView = inflater.inflate(R.layout.fragment_raise__complaint, null);

       // expListView = (ExpandableListView) rootView.findViewById(R.id.expListView);
       // linear_layout_imageview=(LinearLayout)rootView.findViewById(R.id.linear_layout_imageview);
        //img=(ImageView)rootView.findViewById(R.id.imgv_camera);
        iv = (ImageView)rootView.findViewById(R.id.iv_captured_image);
        iv.setOnClickListener(this);



        //img.setOnClickListener(this);
        btn_submitcomplaint=(Button)rootView.findViewById(R.id.btn_submitcomplaint);
        btn_submitcomplaint.setOnClickListener(this);

        complaint_remark=(EditText)rootView.findViewById(R.id.editremark);

        complaints = new ArrayList<>(12);
        complaints.add(0,"Select Complaint Type");

        if (CommonUtils.isNetworkAvaliable(getActivity())) {
            JsonObjectRequest request = WebRequests.getComplaintType(getActivity(), Request.Method.GET, AppConstants.URL_GET_COMPLAINT_TYPE, AppConstants.REQUEST_GET_COMPLAINT_TYPE,
                    this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_COMPLAINT_TYPE);
        } else

            Toast.makeText(getActivity(), " Please Check  Internet Connection ", Toast.LENGTH_SHORT).show();




        complainttype = (Spinner)rootView.findViewById(R.id.sp_complainttype);
       // String[] type = mContext.getResources().getStringArray(R.array.complaints);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, complaints);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complainttype.setAdapter(dataAdapter);

        return rootView;
    }





    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_GET_COMPLAINT_TYPE: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
//
                        Log.i(label, "hygt " + jsonResponse);
                       // Log.i(label, "hyif " + jsonResponse.complaint_type);
                        if(jsonResponse.complaint_type.size()!=0)
                        {

                            for(int i = 1 ; i <= jsonResponse.complaint_type.size(); i++) {

                                ServiceType complaint1=new ServiceType();

                                Log.i(label, "complainttype" + jsonResponse.complaint_type);
                                complaint1.setType(jsonResponse.complaint_type.get(i-1).type);
                                complaint1.setId(jsonResponse.complaint_type.get(i-1).id.toString().trim());
                                complaints.add(i,complaint1.getType());
                                Log.i(label, "complainttype" + jsonResponse.complaint_type);



                            }


                        }
                        if (jsonResponse.authorization != null) {
                            CommonUtils.saveAuthToken(getActivity(), jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        Toast.makeText(getContext(), jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                    }
                    break;
                }
            }
            case AppConstants.REQUEST_POST_ADD_COMPLAINT: {
                if (jsonResponse != null) {
                    Log.i(label, "hyif " + jsonResponse);

                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {

                        Log.i(label, "hyif " + jsonResponse.result);
                        Intent i = new Intent(getActivity(),GetComplaintIdActivity.class);
                        startActivity(i);

                    }
                    if(jsonResponse.message!= null) {


                    }
                    if (jsonResponse.authorization != null) {
                        CommonUtils.saveAuthToken(getActivity(), jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                    }
                } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                    Toast.makeText(getActivity(), jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                }
                break;
            }
            }

        }



    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {
        switch (label) {
            case AppConstants.REQUEST_GET_COMPLAINT_TYPE: {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
               Log.i(label, "Complainttype" + message);
                Log.i(label, "Complainttype" + response);
            }
            break;
            case AppConstants.REQUEST_POST_ADD_COMPLAINT: {
//
                    Log.i(label, "addcomplaint" + message);
                    Log.i(label, "addcomplaint" + response);
                }
                break;
            }
        }




    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
