package com.essel.smartutilities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.ActivityMainSL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public void validate() {
        String fullname = editTextFullName.getText().toString().trim();
        String address1 = editTextAddress1.getText().toString().trim();
        String address2 = editTextAddress2.getText().toString().trim();
        String address3 = editTextAddress3.getText().toString().trim();
        String phoneno = editTextPhone.getText().toString().trim();
        String emailid = editTextEmailId.getText().toString().trim();
        String consumerid = editTextConsumerId.getText().toString().trim();
        if (fullname.equals("") || address1.equals("") || address2.equals("") || address3.equals("") || phoneno.equals("") || emailid.equals("") || consumerid.equals("")) {
            Toast.makeText(mContext.getApplicationContext(), "Please fill all fields", Toast.LENGTH_LONG).show();
            return;
        }
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

            validate();
        }
        else if(v==actionLogin){
            Fragment fragment = new LoginFragment();
            ((ActivityMainSL)mContext).addFragment(fragment,true);
        }
    }
}
