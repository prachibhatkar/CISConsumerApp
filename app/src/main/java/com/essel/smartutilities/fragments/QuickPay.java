package com.essel.smartutilities.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.essel.smartutilities.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuickPay.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuickPay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class QuickPay extends Fragment  implements View.OnClickListener{


    AppCompatButton actionProceed,actionProceedtoPay;
    Context mContext;
    CardView proceedToPayView;
    Toolbar mToolBar;
    public QuickPay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment QuickPay.
     */
    // TODO: Rename and change types and number of parameters
    public static QuickPay newInstance(String param1, String param2) {
        QuickPay fragment = new QuickPay();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_quick_pay, container, false);
        initialize(rootView);
        return rootView;
    }

    private void initialize(View rootView){
        mToolBar = (Toolbar) ((AppCompatActivity) getActivity()).findViewById(R.id.toolbar);
        setHasOptionsMenu(true);



        actionProceed =(AppCompatButton)rootView.findViewById(R.id.BTNProceed);
        actionProceedtoPay = (AppCompatButton)rootView.findViewById(R.id.BTNProceedtoPay);
        proceedToPayView = (CardView)rootView.findViewById(R.id.proceed_to_pay_dialog);

        actionProceed.setOnClickListener(this);
        actionProceedtoPay.setOnClickListener(this);

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
    public void onClick(View v) {
        if(v==actionProceed){
            proceedToPayView.setVisibility(View.VISIBLE);
        }
        else if(v==actionProceedtoPay){

        }
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
