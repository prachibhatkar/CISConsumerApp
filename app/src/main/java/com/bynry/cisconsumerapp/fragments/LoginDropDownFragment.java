package com.bynry.cisconsumerapp.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.activity.ManageAccountsActivity;
import com.bynry.cisconsumerapp.adapter.dropdownadapter;
import com.bynry.cisconsumerapp.db.DatabaseManager;
import com.bynry.cisconsumerapp.models.Consumer;

import java.util.ArrayList;


public class LoginDropDownFragment extends DialogFragment implements View.OnClickListener
{

    private Context mContext;
    private OnFragmentInteractionListener mListener;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private View rootView;

    public LoginDropDownFragment()
    {
    }

    public static LoginDropDownFragment newInstance()
    {
        LoginDropDownFragment fragment = new LoginDropDownFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();

         rootView = inflater.inflate(R.layout.fragment_login_dropdown, container, false);
        TextView tv = (TextView) rootView.findViewById(R.id.tv_title);
        tv.setOnClickListener(this);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        getData();
        return rootView;
    }

    public void getData()
    {
        ArrayList<Consumer> consumers = DatabaseManager.getAllManageAccounts(getActivity());
        dropdownadapter adapter = new dropdownadapter(mContext, consumers);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
    public void onResume() {
        super.onResume();
        getData();

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
        if (view.getId() == R.id.tv_title) {
            onDestroy();

            Intent in = new Intent(getActivity(), ManageAccountsActivity.class);
            startActivity(in);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}
