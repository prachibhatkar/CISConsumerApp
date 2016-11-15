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
 * {@link Complaint_Status_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Complaint_Status_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Complaint_Status_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView tv_consumerid,tv_complainttype,tv_complaintraised,tv_complaintstatus;

    private OnFragmentInteractionListener mListener;
    private TextView tv_complaintmsg;

    public Complaint_Status_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Complaint_Status_Fragment.
     */
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
        initialize(rootView);
        return rootView;




    }

    private void initialize(View rootView){
        tv_consumerid=(TextView)rootView.findViewById(R.id.tv_consumerid);
        tv_complainttype=(TextView)rootView.findViewById(R.id.tv_complainttype);
        tv_complaintstatus=(TextView)rootView.findViewById(R.id.tv_complaintstatus);
        tv_complaintraised=(TextView)rootView.findViewById(R.id.tv_complaintraised);
        tv_complaintmsg=(TextView)rootView.findViewById(R.id.tv_complaintmsg);






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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
