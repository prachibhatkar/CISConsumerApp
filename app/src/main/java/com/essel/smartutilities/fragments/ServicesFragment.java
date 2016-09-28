package com.essel.smartutilities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.CustomExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ServicesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private Button btn_submit_service;
    private EditText edit_remark;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public ServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ServicesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ServicesFragment newInstance(String param1, String param2) {
        ServicesFragment fragment = new ServicesFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_services, null);

        expListView = (ExpandableListView) rootView.findViewById(R.id.expListView);

        prepareListData();

        listAdapter = new CustomExpandableListAdapter(getActivity(),listDataHeader,listDataChild);

        expListView.setAdapter(listAdapter);

        Log.i("groups", listDataHeader.toString());
        Log.i("details", listDataChild.toString());

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(getActivity().getApplicationContext(),listDataHeader.get(groupPosition) + " : " + listDataChild.get(listDataHeader.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return rootView;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("New Service Connection");
        listDataHeader.add("Change of Ownership");
        listDataHeader.add("Change of Connection Type");
        listDataHeader.add("Load Extension Reduction");
        listDataHeader.add("Permanent Disconnection");
        listDataHeader.add("Name/Address Correction");



        // Adding child data
        List<String> NewServiceConnection = new ArrayList<String>();
        NewServiceConnection.add("Subtype 1");
        NewServiceConnection.add("Subtype 2");

        List<String> ChangeOfOwnership = new ArrayList<String>();
        ChangeOfOwnership.add("Subtype 1");
        ChangeOfOwnership.add("Subtype 2");

        List<String> ChangeOfConnectionType = new ArrayList<String>();
        ChangeOfConnectionType.add("Subtype 1");
        ChangeOfConnectionType.add("Subtype 2");

        List<String> LoadExtensionReduction = new ArrayList<String>();
        LoadExtensionReduction.add("Subtype 1");
        LoadExtensionReduction.add("Subtype 2");

        List<String> PermanentDisconnection = new ArrayList<String>();
        PermanentDisconnection.add("Subtype 1");
        PermanentDisconnection.add("Subtype 2");

        List<String> Name_AddrCorrection = new ArrayList<String>();
        Name_AddrCorrection.add("Subtype 1");
        Name_AddrCorrection.add("Subtype 2");

        listDataChild.put(listDataHeader.get(0), NewServiceConnection); // Header, Child data
        listDataChild.put(listDataHeader.get(1), ChangeOfOwnership);
        listDataChild.put(listDataHeader.get(2), ChangeOfConnectionType);
        listDataChild.put(listDataHeader.get(3), LoadExtensionReduction);
        listDataChild.put(listDataHeader.get(4), PermanentDisconnection);
        listDataChild.put(listDataHeader.get(5), Name_AddrCorrection);

    }
    // TODO: Rename method, update argument and hook method into UI event


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;

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
}
