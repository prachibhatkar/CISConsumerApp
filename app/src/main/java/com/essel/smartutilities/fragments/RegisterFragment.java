package com.essel.smartutilities.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.ActivityMainSL;
import com.essel.smartutilities.activity.SignupStepTwo;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements View.OnClickListener {
    private TextView btnLogin;
    private AppCompatButton btnNext;
    private EditText editTextConsumerId;
    private TextInputLayout inputLayoutConsumerId;
    private Context mContext;
    private ImageView fabNewConnection;
    private Toolbar mToolBar;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RegisterFragment.
     */
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        initialize(rootView);
        return rootView;
    }

    private void initialize(View rootView) {

        mToolBar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        // setHasOptionsMenu(true);
        mToolBar.setVisibility(View.GONE);
        btnLogin = (TextView) rootView.findViewById(R.id.txt_login);
        btnNext = (AppCompatButton) rootView.findViewById(R.id.BTNNext);
        editTextConsumerId = (EditText) rootView.findViewById(R.id.editConsumerId);
        inputLayoutConsumerId = (TextInputLayout) rootView.findViewById(R.id.inputLayoutConsumerId);

        btnNext.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        fabNewConnection = (ImageView) rootView.findViewById(R.id.fab_new_connection);
        fabNewConnection.setOnClickListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onClick(View v) {
        if (v == btnLogin) {
            Fragment fragment = new LoginFragment();
            ((ActivityMainSL) mContext).addFragment(fragment, true);
        } else if (v == btnNext) {
            Intent i = new Intent(mContext, SignupStepTwo.class);
            startActivity(i);
            ((ActivityMainSL) mContext).overridePendingTransition(R.anim.slide_no_change, R.anim.slide_up);
        } else if (v == fabNewConnection) {
            Fragment fragment = new NewConnectionFragment();
            ((ActivityMainSL) mContext).addFragment(fragment, true);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
