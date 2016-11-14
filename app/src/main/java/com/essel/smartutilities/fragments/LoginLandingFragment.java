package com.essel.smartutilities.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.ComplaintActivity;
import com.essel.smartutilities.activity.Contact_Us_Activity;
import com.essel.smartutilities.activity.ManageAccountsActivity;
import com.essel.smartutilities.activity.ProfileActivity;
import com.essel.smartutilities.activity.QuickPayActivity;
import com.essel.smartutilities.activity.ServiceActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginLandingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginLandingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginLandingFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LinearLayout actionservices, actioncomplaints, actionpaymenthistory, actionmyprofile, actionquickpay, actionmanageaccount,
            actioncontactus,actionmore;
    Button btnLogout;
    Context mContext;
    Toolbar mToolbar;;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public LoginLandingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LandingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginLandingFragment newInstance(String param1, String param2) {
        LoginLandingFragment fragment = new LoginLandingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
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
        View layout = inflater.inflate(R.layout.fragment_login_landing, container, false);
        setupUI(layout);
        return layout;

    }

    private void setupUI(View layout) {
        actionmore=(LinearLayout)layout.findViewById(R.id.action_more_menu);
        actionservices = (LinearLayout)layout.findViewById(R.id.action_services);
        actioncomplaints = (LinearLayout)layout.findViewById(R.id.action_complaints);
        actionpaymenthistory = (LinearLayout)layout.findViewById(R.id.action_payment_history);
        actionquickpay = (LinearLayout)layout.findViewById(R.id.action_quick_pay);
        actionmanageaccount = (LinearLayout)layout.findViewById(R.id.action_manage_accounts);
        actionmyprofile = (LinearLayout)layout.findViewById(R.id.action_My_Profile);
        actioncontactus = (LinearLayout)layout.findViewById(R.id.action_contact_us);
        actionmore.setOnClickListener(this);
        actionservices.setOnClickListener(this);
        actioncomplaints.setOnClickListener(this);
        actionpaymenthistory.setOnClickListener(this);
        actionquickpay.setOnClickListener(this);
        actionmanageaccount.setOnClickListener(this);
        actionmyprofile.setOnClickListener(this);
        actioncontactus.setOnClickListener(this);

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
    public void onClick(View view) {

        if(view==actionmore) {
            Fragment fragment = new LoginLandingmoreFragment();
            addFragment(fragment, true);
            getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        }

        else if(view==actionservices){
            Intent i = new Intent(getActivity(), ServiceActivity.class);
            startActivity(i);
        }
        else if(view==actioncomplaints){
            Intent i = new Intent(getActivity(), ComplaintActivity.class);
            startActivity(i);

        }
        else if(view==actionmanageaccount){
            Intent i = new Intent(getActivity(), ManageAccountsActivity.class);
            startActivity(i);


        }
        else if(view==actionquickpay){
            Intent i = new Intent(getActivity(), QuickPayActivity.class);
            startActivity(i);

        }

        else if(view==actionpaymenthistory){
//            Intent i = new Intent(getActivity(), ComplaintActivity.class);
//            startActivity(i);
        }
        else if(view==actioncontactus){
            Intent i = new Intent(getActivity(), Contact_Us_Activity.class);
            startActivity(i);

        }
        else if(view==actionmyprofile){
            Intent i = new Intent(getActivity(), ProfileActivity.class);
            startActivity(i);

        }

    }

    public void addFragment(Fragment fragment, boolean withAnimation) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getFragmentManager();
        int entryCount = manager.getBackStackEntryCount();

        String fragName = "";
        if (entryCount > 0)
            fragName = manager.getBackStackEntryAt(entryCount - 1).getName();

        if (!backStateName.equals(fragName)) {
            boolean poped = manager.popBackStackImmediate(backStateName, 0);//int index= getBackStackEntryIndex(backStateName);
            if (!poped && manager.findFragmentByTag(backStateName) == null) {
                FragmentTransaction ft = manager.beginTransaction();
                if (withAnimation) {
                    ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_enter, R.anim.slide_exit);

                }
                ft.replace(R.id.container, fragment, backStateName);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(backStateName);
                ft.commit();
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
