package com.essel.smartutilities.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.MyProfileAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MyProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MyProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyProfileFragment extends Fragment {

    public static final String FRAG_NAME=MyProfileFragment.class.getName();

    private String mFragementName;
    private Context mContext;
    private ViewPager profile_pager;

    private MyProfileAdapter myProfileAdapter;
    private TabLayout profile_tabs;






    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public MyProfileFragment() {

        // Required empty public constructor

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFragementName = getArguments().getString(FRAG_NAME);
        }
        setHasOptionsMenu(true);


        /*profile_tabs.addTab(profile_tabs.newTab().setText("Change Details"));
        profile_tabs.addTab(profile_tabs.newTab().setText("Change Password"));

        profile_tabs.setTabGravity(TabLayout.GRAVITY_FILL);*/

    }

    /*private void setupViewPager(ViewPager viewPager) {
        ViewPager adapter = new ViewPager(mContext,null);
        adapter.addFragment(new My_Profile_Change_Details(), "ONE");
        adapter.addFragment(new My_Profile_Change_Password(), "TWO");
        viewPager.setAdapter(adapter);
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_my_profile, container, false);

        setupUI(layout);

        return layout;

    }

    private void setupUI(View layout) {
        profile_pager = (ViewPager) layout.findViewById(R.id.profile_pager);
        profile_tabs = (TabLayout) layout.findViewById(R.id.profile_tabs);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
   /*     ActionBar actionBar;
        actionBar = ((ActivityLandingLogin)mContext).getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(mContext.getString(R.string.app_name));*/
        loadData();
    }

    private void loadData() {
        myProfileAdapter = new MyProfileAdapter(mContext,getChildFragmentManager());
        profile_pager.setAdapter(myProfileAdapter);
        profile_pager.addOnPageChangeListener(onPageChangedListener);
        profile_tabs.setupWithViewPager(profile_pager);
    }

    ViewPager.OnPageChangeListener onPageChangedListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }


        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };

            /**
             * Use this factory method to create a new instance of
             * this fragment using the provided parameters.
             *
             * @param param1 Parameter 1.
             * @param param2 Parameter 2.
             * @return A new instance of fragment MyProfileFragment1.
             */
    // TODO: Rename and change types and number of parameters
    public static MyProfileFragment newInstance(String param1, String param2) {
        MyProfileFragment fragment = new MyProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    // TODO: Rename method, update argument and hook method into UI event




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
