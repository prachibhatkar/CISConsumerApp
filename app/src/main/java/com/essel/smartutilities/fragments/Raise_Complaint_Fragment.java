package com.essel.smartutilities.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.GetComplaintIdActivity;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.Complaints;
import com.essel.smartutilities.models.GetInfo;
import com.essel.smartutilities.models.JsonResponse;
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
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link Raise_Complaint_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Raise_Complaint_Fragment extends Fragment implements View.OnClickListener,ServiceCaller
{

    private static final int CAPTURE_IMAGE=1,SELECT_IMAGE=2;
    private int count = 0;
    ImageView iv;
    Spinner complainttype;
    Button btn_submitcomplaint;
    String complaintremark;
    EditText complaint_remark;
    String image,image1;
    Boolean flag=true;
    String caseid;
    ProgressDialog pDialog;
    Bitmap photo;
    Bitmap bitmap;
    String consumerno;
    String selectcomplainttype,casetype,msg,msg1;

    private String TAG = "responsedataaaaa";
    private ArrayList<String> complaints;
    private ArrayList<String> complaintcode = new ArrayList<String>();
    private Object fileUri;

    @Override
    public void onClick(View v)
    {

        if(v==iv){
            //showImageOptionsDialog();

        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoCaptureIntent, CAPTURE_IMAGE);

    }

        if(v==btn_submitcomplaint)
        {
          if (isBlankInput())
          {



//                JSONObject obj = new JSONObject();
//                try {
//                    obj.put("complainttype", complainttype.getSelectedItemPosition());
//                    obj.put("consumer_remark", complaintremark);
//                    obj.put("complaint_img", image);
//                    obj.put("complaint_id", caseid);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//               }

                if( CommonUtils.isNetworkAvaliable(getActivity()))
                {

                    initProgressDialog();
                    if (pDialog != null && !pDialog.isShowing())
                    {
                        pDialog.setMessage(" please wait..");
                        pDialog.show();
                    }
                    AsyncCallWS task = new AsyncCallWS();
                    task.execute();




                   // JsonObjectRequest request = WebRequests.addComplaint(getActivity(), Request.Method.POST, AppConstants.URL_POST_ADD_COMPLAINT, AppConstants.REQUEST_POST_ADD_COMPLAINT,
                       //     this,obj,SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.AUTH_TOKEN));

                 //   App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_POST_ADD_COMPLAINT);
                }else
                    Toast.makeText(getActivity(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();


//              if(flag=true){
//
//                  callApi();
//
//
//              }
            }



        }
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

    private class AsyncCallWS extends AsyncTask<Void, Void, Void>
    {


        @Override
        protected void onPreExecute()
        {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            Log.i(TAG, "doInBackground");
            getComplaintId();
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            Log.i(TAG, "onPostExecute");

            dismissDialog();



        }



    }


    public void callApi()
    {
         consumerno=((SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.CONSUMER_NO)).toString());

         // selectcomplainttype=complainttype.getSelectedItem().toString();
          JSONObject obj = new JSONObject();
        try
        {
            obj.put("complaint_type", complainttype.getSelectedItemPosition());
            obj.put("consumer_remark", complaintremark);
            obj.put("complaint_img", image == null ? " " : image.toString());
            obj.put("complaint_id", caseid);
            obj.put("consumer_no", consumerno);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }

        if( CommonUtils.isNetworkAvaliable(getActivity()))
        {

            JsonObjectRequest request = WebRequests.addComplaint(getActivity(), Request.Method.POST, AppConstants.URL_POST_ADD_COMPLAINT, AppConstants.REQUEST_POST_ADD_COMPLAINT,
                    this,obj,SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.AUTH_TOKEN));

            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_POST_ADD_COMPLAINT);
            flag=false;
        }
        else
            Toast.makeText(getActivity(), " Please Check Internet Connection ", Toast.LENGTH_SHORT).show();

    }


    public void getComplaintId()
    {
        GetInfo get =new GetInfo();
        String consumerno=(SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.CONSUMER_NO)).toString();
        get = DatabaseManager.getinfo(getActivity(),consumerno);

        String mbnno=get.mobileno;

        String getconsumerno = (SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.CONSUMER_NO)).toString();
        String getconsumercity = "Nagpur";
        String getconsumermobileno = (SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.MOBILE)).toString();
        String remark =  String.valueOf(complaint_remark.getText());
        String complainttype1 = String.valueOf(complainttype.getSelectedItemPosition());
        String SOAP_ACTION = "http://123.63.20.164:8001/soa-infra/services/FieldMobility/CreateComplaint!2.0*soa_5bb8bf43-cb4e-4110-9180-3fda9cacc35e/createcomplaintbpelprocess_client_ep";
        String METHOD_NAME = "requestElement";
        String NAMESPACE = "http://www.example.org";
        String URL = "http://123.63.20.164:8001/soa-infra/services/FieldMobility/CreateComplaint!2.0*soa_5bb8bf43-cb4e-4110-9180-3fda9cacc35e/createcomplaintbpelprocess_client_ep";

        try
        {
            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            Request.addProperty("accId", getconsumerno);
            Request.addProperty("city", getconsumercity);
            Request.addProperty("service", "Electricity");
            Request.addProperty("caseType", casetype);
            Request.addProperty("mobile", mbnno);
            Request.addProperty("remark",complaint_remark.length()!=0?complaint_remark.getText().toString():"" );

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;

            soapEnvelope.setOutputSoapObject(Request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;


            androidHttpTransport.call(SOAP_ACTION, soapEnvelope);
            SoapObject responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn);

             caseid =((SoapObject) soapEnvelope.bodyIn).getProperty("caseId").toString();
            String message =((SoapObject) soapEnvelope.bodyIn).getProperty("message").toString();
             msg="Please select Correct values";
             msg1="This Type Of Case Already Open";


            Log.i(TAG, "caseId" +caseid);
            Log.i(TAG, "caseId" +message);
            Complaints complaint = new Complaints();
            complaint.img=image;
            complaint.type=complainttype1;
            complaint.complaintid=caseid;
            complaint.remark=remark;

            //DatabaseManager.saveComplaint(getActivity(),complaint);
            DatabaseManager.saveComplaint(getActivity(),complaint);


            dismissDialog();
            Intent in = new Intent(getActivity(), GetComplaintIdActivity.class);
            in.putExtra("caseid", caseid);
            in.putExtra("message", message);
            startActivity(in);


            //||msg1.equals(message)
            if(msg.equals(message)||msg1.equals(message)){
               // Toast.makeText(getContext(), "jghjgjgj", Toast.LENGTH_LONG).show();
                flag=false;

            }
            else
            {

                //flag=true;
                callApi();

            }



           // final SoapObject response = (SoapObject) soapEnvelope.getResponse();
           // SoapPrimitive response1 = (SoapPrimitive) soapEnvelope.getResponse();



           // SoapObject responceArray = (SoapObject) ((SoapObject) soapEnvelope.bodyIn).getProperty("X_BILLDTLS_TBL");
          //  Log.i(TAG, "get : " + ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE14"));

           // String paymenthistory=  ((SoapObject)responceArray.getProperty(0)).getProperty("ATTRIBUTE14").toString();







        } catch (Exception e)
        {
            Log.e(TAG, "Error: " + e.getMessage());

        }


    }





    private boolean isBlankInput()
    {
        boolean b = true;
        complaintremark = String.valueOf(complaint_remark.getText());
       /* if (complaintremark.equals("")){
            Toast.makeText(getContext(), "Enter Remark", Toast.LENGTH_LONG).show();

            b = false;
        }*/
       /* if(complaintremark.length()>200){
            Toast.makeText(getContext(), "remark should be 200 char", Toast.LENGTH_LONG).show();
            b = false;
        }*/

        if (complainttype.getSelectedItemPosition()== 0)
        {
            Toast.makeText(getContext(), "Select complaint type", Toast.LENGTH_LONG).show();
            b = false;
        }
        return b;

    }



    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub

        if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK)
        {
            photo = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(photo);

            //storeCameraPhotoInSDCard(photo);
            //saveImageToStorage();
            iv.setVisibility(View.VISIBLE);

            image=CommonUtils.getBitmapEncodedString(photo);
            Log.i(TAG, "hygt " +  image);



          //  ByteArrayOutputStream baos = new ByteArrayOutputStream();
           /* bm.compress(Bitmap.CompressFormat.JPEG, 100,baos);
            byte[] b = baos.toByteArray();
            byte[] encodedImage = Base64.encode(b, Base64.DEFAULT);
            image=encodedImage.toString().trim();*/
        }
//        if (requestCode == SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
//
//            Uri selectedImage = data.getData();
//
//            try {
//               bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            iv.setImageURI(selectedImage);
//            image=CommonUtils.getBitmapEncodedString(bitmap);
//
//            }



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

    public Raise_Complaint_Fragment()
    {
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
    public static Raise_Complaint_Fragment newInstance(int param1, String param2)
    {
        Raise_Complaint_Fragment fragment = new Raise_Complaint_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(param1));
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        mContext = getActivity();




        View rootView = inflater.inflate(R.layout.fragment_raise__complaint, null);

        iv = (ImageView)rootView.findViewById(R.id.iv_captured_image);
        iv.setOnClickListener(this);



        //img.setOnClickListener(this);
        btn_submitcomplaint=(Button)rootView.findViewById(R.id.btn_submitcomplaint);
        btn_submitcomplaint.setOnClickListener(this);
        complainttype = (Spinner)rootView.findViewById(R.id.sp_complainttype);
        complaint_remark=(EditText)rootView.findViewById(R.id.editremark);

        ((EditText)rootView.findViewById(R.id.editremark)).setFilters(new InputFilter[]
                {
                new InputFilter.LengthFilter(200)
        });

        complaints = new ArrayList<>(12);
        complaints.add(0,"Select Complaint Type");

        if (CommonUtils.isNetworkAvaliable(getActivity()))
        {

            JsonObjectRequest request = WebRequests.getComplaintType(getActivity(), Request.Method.GET, AppConstants.URL_GET_COMPLAINT_TYPE, AppConstants.REQUEST_GET_COMPLAINT_TYPE,
                    this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_COMPLAINT_TYPE);
        } else

            Toast.makeText(getActivity(), " Please Check  Internet Connection ", Toast.LENGTH_SHORT).show();

       // String[] type = mContext.getResources().getStringArray(R.array.complaints);
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, complaints);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        complainttype.setAdapter(dataAdapter);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, complaints);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complainttype.setAdapter(dataAdapter);

        complainttype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
//                 if(complainttype.getSelectedItem().toString()=="Bill Complaint"){
//                     casetype="Mannual Bill Adjustment";
//                 }
//                if(complainttype.getSelectedItem().toString()=="No Power"){
//                    casetype="No Power Complaints";
//                }
//                if(complainttype.getSelectedItem().toString()=="Meter Complaint"){
//                    casetype="Meter Testing";
//                }
//                if(complainttype.getSelectedItem().toString()=="Bill Complaint"){
//                    casetype="Phase Correction";
//                }
                if(position==1)
                {
                     casetype=complaintcode.get(0).toString();
                 }
                if(position==2)
                {
                    casetype=complaintcode.get(1).toString();
                }
                if(position==3)
                {
                    casetype=complaintcode.get(2).toString();
                }
                if(position==4){
                    casetype=complaintcode.get(3).toString();
                }

//
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }


        });


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
        switch (label)
        {
            case AppConstants.REQUEST_GET_COMPLAINT_TYPE:
            {
                if (jsonResponse != null)
                {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
                        Log.i(label, "hygt " + jsonResponse);
                        if(jsonResponse.complaint_type.size()!=0)
                        {
                            for(int i = 1 ; i <= jsonResponse.complaint_type.size(); i++) {

                                ServiceType complaint1=new ServiceType();

                                Log.i(label, "complainttype" + jsonResponse.complaint_type);
                                complaint1.setType(jsonResponse.complaint_type.get(i-1).type);
                                complaint1.setId(jsonResponse.complaint_type.get(i-1).id.toString().trim());
                                complaints.add(i,complaint1.getType());
                                complaint1.setCode(jsonResponse.complaint_type.get(i-1).code.toString());
                                complaintcode.add(i-1,complaint1.getCode());

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
                        Log.i(label, "hyif " + jsonResponse.result);
                      //  Intent i = new Intent(getActivity(),GetComplaintIdActivity.class);
                       // startActivity(i);

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


    private void showImageOptionsDialog() {
        final String CHOOSE_GALLERY = "Choose Gallery", USE_CAMERA = "Use Camera", UPLOAD_VIDEO = "upload video";
        ArrayList<String> list = new ArrayList<String>();
        final CharSequence items[];
        list.add(CHOOSE_GALLERY);
        list.add(USE_CAMERA);

        items = list.toArray(new CharSequence[list.size()]);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(" Set Profile Image ");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {


                if (items[item].equals(USE_CAMERA)) {

                    Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(photoCaptureIntent, CAPTURE_IMAGE);
                } else if (items[item].equals(CHOOSE_GALLERY)) {

                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, SELECT_IMAGE);


                }
            }

        });
        builder.show();
    }



}
