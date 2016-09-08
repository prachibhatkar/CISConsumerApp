package com.essel.smartutilities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.ActivityMainSL;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewConnectionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewConnectionFragment extends Fragment implements View.OnClickListener
{
    private Context mContext;
    private EditText editTextFullName,editTextAddress1,editTextAddress2,editTextAddress3,editTextPhone,editTextConsumerId,editTextEmailId;
    private TextInputLayout inputLayoutFullName,inputLayoutAddress1,inputLayoutAddress2,inputLayoutAddress3,inputLayoutPhone,inputLayoutConsumerId,inputLayoutEmailId;
    private AppCompatButton btnActionSubmit;
    private TextView actionLogin;
    private Toolbar mToolBar;
    private LinearLayout toolbarContainer;

    public NewConnectionFragment() {
        // Required empty public constructor
    }

    public static NewConnectionFragment newInstance() {
        NewConnectionFragment fragment = new NewConnectionFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_new_connection, container, false);
        initialize(rootView);
        return rootView;
    }

    private void initialize(View rootView){
        mToolBar = (Toolbar)  getActivity().findViewById(R.id.toolbar);
        mToolBar.setVisibility(View.VISIBLE);
        editTextFullName = (EditText)rootView.findViewById(R.id.editFullName);
        editTextAddress1 = (EditText)rootView.findViewById(R.id.editAddressLine1);
        editTextAddress2 = (EditText)rootView.findViewById(R.id.editAddressLine2);
        editTextAddress3 = (EditText)rootView.findViewById(R.id.editAddressLine3);
        editTextPhone = (EditText)rootView.findViewById(R.id.editPhone);
        editTextConsumerId = (EditText)rootView.findViewById(R.id.editConsumerId);
        editTextEmailId = (EditText)rootView.findViewById(R.id.editEmailId);
        inputLayoutFullName = (TextInputLayout)rootView.findViewById(R.id.inputLayoutFullName);
        inputLayoutAddress1 = (TextInputLayout)rootView.findViewById(R.id.inputLayoutAddressLine1);
        inputLayoutAddress2 = (TextInputLayout)rootView.findViewById(R.id.inputLayoutAddressLine2);
        inputLayoutAddress3 = (TextInputLayout)rootView.findViewById(R.id.inputLayoutAddressLine3);
        inputLayoutPhone = (TextInputLayout)rootView.findViewById(R.id.inputLayoutPhone);
        inputLayoutConsumerId = (TextInputLayout)rootView.findViewById(R.id.inputLayoutConsumerId);
        inputLayoutEmailId = (TextInputLayout)rootView.findViewById(R.id.inputLayoutEmailId);
        btnActionSubmit = (AppCompatButton)rootView.findViewById(R.id.action_submit);
        actionLogin = (TextView)rootView.findViewById(R.id.action_login);
        btnActionSubmit.setOnClickListener(this);
        actionLogin.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       mContext=context;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.top_right_menu, menu);
    }

    @Override
    public void onClick(View v) {
        if(v==btnActionSubmit){
          //  int selection = gridViewConnectionTypes.getCheckedItemPosition();
          //  Log.d("",""+selection);
        }
        else if(v==actionLogin){
            Fragment fragment = new LoginFragment();
            ((ActivityMainSL)mContext).addFragment(fragment,true);
        }
    }
}
