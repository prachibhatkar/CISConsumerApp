package com.essel.smartutilities.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.essel.smartutilities.utility.SharedPrefManager;
import com.essel.smartutilities.webservice.WebRequests;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * <p>
 * to handle interaction events.
 * Use the {@link Contact_Details_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contact_Details_Fragment extends Fragment implements ServiceCaller {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    TextView tv_portalsite, tv_antiberibery, tv_onlinecomplaint, tv_igrcemail, tv_consumerportal, tv_helplineno, tv_antiberiberyno, tv_onlinecomplaintno, tv_electricitytheftno, tv_igrc, tv_igrcno;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Contact_Details_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Contact_Details_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Contact_Details_Fragment newInstance(int param1, String param2) {
        Contact_Details_Fragment fragment = new Contact_Details_Fragment();
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
        View rootView = inflater.inflate(R.layout.fragment_contact__details, container, false);
        initialize(rootView);
        return rootView;

    }


    private void initialize(View rootView) {
        tv_portalsite = (TextView) rootView.findViewById(R.id.tv_portalsite);
        tv_antiberibery = (TextView) rootView.findViewById(R.id.tv_antiberihelp);
        tv_onlinecomplaint = (TextView) rootView.findViewById(R.id.tv_onlinehelp);
        tv_igrcemail = (TextView) rootView.findViewById(R.id.tv_igrcemailid);
        tv_igrcno = (TextView) rootView.findViewById(R.id.tv_igrcno);
        tv_electricitytheftno = (TextView) rootView.findViewById(R.id.tv_electricitytheftno);
        tv_helplineno = (TextView) rootView.findViewById(R.id.tv_helplineno);
        tv_antiberiberyno = (TextView) rootView.findViewById(R.id.tv_antiberiberyno);
        tv_onlinecomplaintno = (TextView) rootView.findViewById(R.id.tv_onlineemail);
        if (CommonUtils.isNetworkAvaliable(getActivity())) {
            JsonObjectRequest request = WebRequests.getContactDetails(getActivity(), Request.Method.GET, AppConstants.URL_GET_CONTACT_DETAILS, AppConstants.REQUEST_GET_CONTACT_DETAILS,
                    this, SharedPrefManager.getStringValue(getActivity(), SharedPrefManager.AUTH_TOKEN));
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_CONTACT_DETAILS);
        } else
            Toast.makeText(getActivity(), " Please Connection Internet ", Toast.LENGTH_SHORT).show();


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_GET_CONTACT_DETAILS: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
//                            DatabaseManager.saveJobCards(mContext, jsonResponse.responsedata.jobcards);
//                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();
//                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
//                        Log.i(label, "Contactus:" + jsonResponse.contactus);
                        if (jsonResponse.contactus != null) {
                            tv_helplineno.setText(jsonResponse.contactus.helpline_number);
                            tv_antiberiberyno.setText(jsonResponse.contactus.anti_bribery_help);
                            tv_portalsite.setText(jsonResponse.contactus.customer_portal);
                            tv_electricitytheftno.setText(jsonResponse.contactus.electricity_theft_help_no);
                            tv_igrcno.setText(jsonResponse.contactus.igrc_no);
                            tv_igrcemail.setText(jsonResponse.contactus.igrc_email);
                            tv_onlinecomplaintno.setText(jsonResponse.contactus.online_complaint);
                        }
                        if (jsonResponse.authorization != null) {
                            CommonUtils.saveAuthToken(getActivity(), jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
//                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                    }
                    break;
                }
            }

        }
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {
        switch (label) {
            case AppConstants.REQUEST_GET_CONTACT_DETAILS: {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
//                Log.i(label, "Contactus" + message);
//                Log.i(label, "Contactus" + response);
            }
            break;
        }
    }
}
