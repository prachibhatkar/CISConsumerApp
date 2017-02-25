package com.essel.smartutilities.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
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
import com.essel.smartutilities.db.DatabaseManager;
import com.essel.smartutilities.models.ContactUs;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.DialogCreator;
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
public class Contact_Details_Fragment extends Fragment implements ServiceCaller,View.OnClickListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ProgressDialog pDialog;
    Toolbar toolbar;


    TextView tv_portalsite, tv_antiberibery, tv_onlinecomplaint, tv_igrcemail, tv_consumerportal, tv_helplineno, tv_antiberiberyno, tv_onlinecomplaintno, tv_electricitytheftno, tv_igrc, tv_igrcno;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Contact_Details_Fragment()
    {
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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_contact__details, container, false);

        initialize(rootView);
//        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
//        // setSupportActionBar(toolbar);
//        ImageView imgBack = (ImageView) rootView.findViewById(R.id.img_back);
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().finish();
//            }
//        });
        return rootView;

    }


    private void initialize(View rootView)
    {
        tv_portalsite = (TextView) rootView.findViewById(R.id.tv_portalsite);
        tv_antiberibery = (TextView) rootView.findViewById(R.id.tv_antiberihelp);
        tv_onlinecomplaint = (TextView) rootView.findViewById(R.id.tv_onlinehelp);
        tv_igrcemail = (TextView) rootView.findViewById(R.id.tv_igrcemailid);
        tv_igrcno = (TextView) rootView.findViewById(R.id.tv_igrcno);
        tv_electricitytheftno = (TextView) rootView.findViewById(R.id.tv_electricitytheftno);
        tv_helplineno = (TextView) rootView.findViewById(R.id.tv_helplineno);
        tv_antiberiberyno = (TextView) rootView.findViewById(R.id.tv_antiberiberyno);
        tv_onlinecomplaintno = (TextView) rootView.findViewById(R.id.tv_onlineemail);

        tv_helplineno.setOnClickListener(this);
        tv_antiberiberyno.setOnClickListener(this);
        tv_onlinecomplaintno.setOnClickListener(this);
        tv_igrcno.setOnClickListener(this);
        tv_portalsite.setOnClickListener(this);
        tv_electricitytheftno.setOnClickListener(this);
        tv_igrcemail.setOnClickListener(this);



        if (CommonUtils.isNetworkAvaliable(getActivity()))
        {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing())
            {
                pDialog.setMessage(" please wait..");
                pDialog.show();
            }
            JsonObjectRequest request = WebRequests.getContactDetails(getActivity(), Request.Method.GET, AppConstants.URL_GET_CONTACT_DETAILS, AppConstants.REQUEST_GET_CONTACT_DETAILS,
                    this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_CONTACT_DETAILS);
        } else {
            ContactUs cont=new ContactUs();
            cont=DatabaseManager.getContactDetail(getActivity());
            tv_helplineno.setText(cont.helpline_number);
            tv_antiberiberyno.setText(cont.anti_bribery_help);
            tv_portalsite.setText(cont.customer_portal);
            tv_electricitytheftno.setText(cont.electricity_theft_help_no);
            tv_igrcno.setText(cont.igrc_no);
            tv_igrcemail.setText(cont.igrc_email);
            tv_onlinecomplaintno.setText(cont.online_complaint);
            Toast.makeText(getActivity(), " Please Connection Internet ", Toast.LENGTH_SHORT).show();

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



    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);

    }

    @Override
    public void onDetach()
    {
        super.onDetach();

    }

    public void onBackPressed()
    {

        dismissDialog();
    }



    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label)
    {
        switch (label)
        {
            case AppConstants.REQUEST_GET_CONTACT_DETAILS:
            {
                if (jsonResponse != null)
                {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {
//                            DatabaseManager.saveJobCards(mContext, jsonResponse.responsedata.jobcards);
//                        Toast.makeText(mContext, jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();
//                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
//                        Log.i(label, "Contactus:" + jsonResponse.contactus);
                        if (jsonResponse.contactus == null)
                        {
                            dismissDialog();
                        }

                        if (jsonResponse.contactus != null)
                        {
                            dismissDialog();
                            tv_helplineno.setText(jsonResponse.contactus.helpline_number);
                            tv_antiberiberyno.setText(jsonResponse.contactus.anti_bribery_help);
                            tv_portalsite.setText(jsonResponse.contactus.customer_portal);
                            tv_electricitytheftno.setText(jsonResponse.contactus.electricity_theft_help_no);
                            tv_igrcno.setText(jsonResponse.contactus.igrc_no);
                            tv_igrcemail.setText(jsonResponse.contactus.igrc_email);
                            tv_onlinecomplaintno.setText(jsonResponse.contactus.online_complaint);
                            SharedPrefManager.saveValue(getActivity(),SharedPrefManager.HELPLINENO,tv_helplineno.getText().toString());
                            ContactUs contactus=new ContactUs();
                            contactus.helpline_number=jsonResponse.contactus.helpline_number;
                            contactus.anti_bribery_help=jsonResponse.contactus.anti_bribery_help;
                            contactus.customer_portal=jsonResponse.contactus.customer_portal;
                            contactus.electricity_theft_help_no=jsonResponse.contactus.electricity_theft_help_no;
                            contactus.igrc_no=jsonResponse.contactus.igrc_no;
                            contactus.igrc_email=jsonResponse.contactus.igrc_email;
                            contactus.online_complaint=jsonResponse.contactus.online_complaint;
                            DatabaseManager.saveContactDetail(getActivity(),contactus);
                        }
                        if (jsonResponse.authorization != null)
                        {
                            dismissDialog();
                            CommonUtils.saveAuthToken(getActivity(), jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
//                        Log.i(label, "responseeeeeeeeeeee:" + jsonResponse);
                    }
                    break;
                }
                dismissDialog();
            }

        }
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response)
    {
        switch (label)
        {
            case AppConstants.REQUEST_GET_CONTACT_DETAILS:
            {
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
//                Log.i(label, "Contactus" + message);
//                Log.i(label, "Contactus" + response);
            }
            break;
        }
    }


    @Override
    public void onClick(View v)
    {
        if(v==tv_helplineno)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + tv_helplineno.getText().toString()));
            startActivity(intent);

        }
        if(v==tv_antiberiberyno)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + tv_antiberiberyno.getText().toString()));
            startActivity(intent);


        }

        if(v==tv_igrcemail)
        {
            Intent send = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode(tv_igrcemail.getText().toString()) +
                    "?subject=" + Uri.encode("") +
                    "&body=" + Uri.encode("");
            Uri uri = Uri.parse(uriText);
            send.setData(uri);
            startActivity(Intent.createChooser(send, "Send mail..."));

            if (send.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(send);

            }else
                DialogCreator.showMessageDialog(getActivity(), "No Apps Can Perform This Action ");
        }

        if(v==tv_igrcno)
        {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + tv_igrcno.getText().toString()));
            startActivity(intent);


        }

        if(v==tv_portalsite)
        {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(tv_portalsite.getText().toString()));
            startActivity(i);
        }

        if(v==tv_onlinecomplaintno)
        {
//            Intent send = new Intent(Intent.ACTION_SENDTO);
//            String uriText = "mailto:" + Uri.encode(tv_onlinecomplaintno.getText().toString()) +
//                    "?subject=" + Uri.encode("") +
//                    "&body=" + Html.fromHtml("<!DOCTYPE html>" +
//                    "<html>" +
//                    "<head>" +
//                    "<title>HTML Table Header</title>" +
//                    "</head>" +
//                    "<body>" +
//                    "<table border="+1+">" +
//                    "<tr>" +
//                    "<th>Name</th>" +
//                    "<th>Salary</th>" +
//                    "</tr>" +
//                    "<tr>" +
//                    "<td>Ramesh Raman</td>" +
//                    "<td>5000</td>" +
//                    "</tr>" +
//                    "<tr>" +
//                    "<td>Shabbir Hussein</td>" +
//                    "<td>7000</td>" +
//                    "</tr>" +
//                    "</table>" +
//                    "</body>" +
//                    "</html>");
//            Uri uri = Uri.parse(uriText);
//            send.setData(uri);
//            startActivity(Intent.createChooser(send, "Send mail..."));
//
//            if (send.resolveActivity(getActivity().getPackageManager()) != null) {
//                startActivity(send);
//
//            }else
//                DialogCreator.showMessageDialog(getActivity(), "No Apps Can Perform This Action ");
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("text/html");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]
                    {"[EMAIL PROTECTED]"});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                    "Subject");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                    Html.fromHtml("<html><body><h1><img width=\"100\" src=\"http://cdn2-www.dogtime.com/assets/uploads/gallery/30-impossibly-cute-puppies/impossibly-cute-puppy-8.jpg\"> Hello World </h1><table><tr><td>aaaa</td><td>bbbb</td><td>cccc</td></tr></table></body></html>"));
            this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }

        if(v==tv_electricitytheftno)
        {

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + tv_electricitytheftno.getText().toString()));
            startActivity(intent);


        }


    }
}
