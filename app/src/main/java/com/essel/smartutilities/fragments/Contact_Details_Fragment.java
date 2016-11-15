package com.essel.smartutilities.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.essel.smartutilities.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 *
 * to handle interaction events.
 * Use the {@link Contact_Details_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Contact_Details_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    TextView tv_helpline,tv_antiberibery,tv_onlinecomplaint,tv_igrcemail,tv_consumerportal,tv_helplineno,tv_antiberiberyno,tv_onlinecomplaintno,tv_electricitythefthelp,tv_electricitytheftno,tv_igrc,tv_igrcno;

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


    private void initialize(View rootView){
        tv_helpline=(TextView)rootView.findViewById(R.id.tv_helpline);
        tv_antiberibery=(TextView)rootView.findViewById(R.id.tv_antiberihelp);
        tv_onlinecomplaint=(TextView)rootView.findViewById(R.id.tv_onlinehelp);
        tv_igrcemail=(TextView)rootView.findViewById(R.id.tv_igrcemail);
        tv_electricitythefthelp=(TextView)rootView.findViewById(R.id.tv_electricitythefthelp);
        tv_electricitytheftno=(TextView)rootView.findViewById(R.id.tv_electricitytheftno);
        tv_helplineno=(TextView)rootView.findViewById(R.id.tv_helplineno);
        tv_antiberiberyno=(TextView)rootView.findViewById(R.id.tv_antiberiberyno);
        tv_onlinecomplaintno=(TextView)rootView.findViewById(R.id.tv_onlineemail);

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
