package com.essel.smartutilities.fragments;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.DropDownAdapter;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.utility.App;

import java.util.ArrayList;


public class LoginDropDownFragment extends Fragment {

    private Context mContext;
    private OnFragmentInteractionListener mListener;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;


    public LoginDropDownFragment() {

    }

    public static LoginDropDownFragment newInstance() {
        LoginDropDownFragment fragment = new LoginDropDownFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        ArrayList<Consumer> consumers = Consumer.createConsumersList(1);
        DropDownAdapter adapter = new DropDownAdapter(mContext, consumers);
        View rootView = inflater.inflate(R.layout.fragment_login_dropdown, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

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


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
