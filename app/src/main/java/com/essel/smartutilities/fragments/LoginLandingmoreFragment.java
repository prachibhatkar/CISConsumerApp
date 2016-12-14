package com.essel.smartutilities.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.AboutUsActivity;
import com.essel.smartutilities.activity.FAQActivity;
import com.essel.smartutilities.activity.FeedBackActivity;
import com.essel.smartutilities.activity.TipsActivity;
import com.essel.smartutilities.activity.TraiffActivity;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link LoginLandingmoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginLandingmoreFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    LinearLayout action_back_menu,action_about_us,action_tips,action_my_traiff,action_faq,action_feedback,action_share;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    public LoginLandingmoreFragment() {
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
    public static LoginLandingmoreFragment newInstance(String param1, String param2) {
        LoginLandingmoreFragment fragment = new LoginLandingmoreFragment();
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
        View layout = inflater.inflate(R.layout.fragment_login_landingmore, container, false);
        setupUI(layout);
        return layout;

    }

    private void setupUI(View layout) {
        action_back_menu=(LinearLayout)layout.findViewById(R.id.action_back_menu);
        action_about_us=(LinearLayout)layout.findViewById(R.id.action_about_us);
        action_tips=(LinearLayout)layout.findViewById(R.id.action_tips);
        action_my_traiff=(LinearLayout)layout.findViewById(R.id.action_my_traiff);
        action_faq=(LinearLayout)layout.findViewById(R.id.action_faq);
        action_feedback=(LinearLayout)layout.findViewById(R.id.action_feedback);
        action_share=(LinearLayout)layout.findViewById(R.id.action_share);


        action_back_menu.setOnClickListener(this);
        action_about_us.setOnClickListener(this);
        action_tips.setOnClickListener(this);
        action_my_traiff.setOnClickListener(this);
        action_faq.setOnClickListener(this);
        action_feedback.setOnClickListener(this);
        action_share.setOnClickListener(this);


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

        if(view==action_back_menu) {
            Fragment fragment = new LoginLandingFragment();
            addFragment(fragment, true);

        }
        else  if(view==action_about_us){
            Intent i = new Intent(getActivity(), AboutUsActivity.class);
            startActivity(i);


        }
        else if(view==action_tips){
            Intent i = new Intent(getActivity(), TipsActivity.class);
            startActivity(i);

        }
        else if(view==action_my_traiff) {
            Intent i = new Intent(getActivity(), TraiffActivity.class);
            startActivity(i);

        }
        else if(view==action_faq) {
            Intent i = new Intent(getActivity(), FAQActivity.class);
            startActivity(i);

        }
        else if(view==action_feedback) {
            Intent i = new Intent(getActivity(), FeedBackActivity.class);
            startActivity(i);


        }
        else if(view==action_share) {
            shareTextUrl();


        }



    }

    private void shareTextUrl() {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        share.putExtra(Intent.EXTRA_TEXT, "http://www.EsselSmartUtilies.com");

        startActivity(Intent.createChooser(share, "Share link!"));
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
                    ft.setCustomAnimations(R.anim.slide_enter, R.anim.slide_exit);
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