package com.essel.smartutilities.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.ExpandablelistAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Raise_Complaint_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Raise_Complaint_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Raise_Complaint_Fragment extends Fragment implements View.OnClickListener {
    @Override
    public void onClick(View v) { if(v==img){

        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }
    }




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    ExpandablelistAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ImageView imageView ;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
//    LinearLayout  linear_layout_imageview;
    ImageView img;
    Context mContext;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Raise_Complaint_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Raise_Complaint_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Raise_Complaint_Fragment newInstance(int param1, String param2) {
        Raise_Complaint_Fragment fragment = new Raise_Complaint_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, String.valueOf(param1));
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
        mContext = getActivity();


        View rootView = inflater.inflate(R.layout.fragment_raise__complaint, null);

        expListView = (ExpandableListView) rootView.findViewById(R.id.expListView);
       // linear_layout_imageview=(LinearLayout)rootView.findViewById(R.id.linear_layout_imageview);
        img=(ImageView)rootView.findViewById(R.id.imgv_camera);
        img.setOnClickListener(this);

        prepareListData();

        listAdapter = new ExpandablelistAdapter(getActivity(), listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);

        Log.i("groups", listDataHeader.toString());
        Log.i("details", listDataChild.toString());

       // linear_layout_imageview.setOnClickListener(this);

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
                //Toast.makeText(getActivity().getApplicationContext(),listDataHeader.get(groupPosition) + " Expanded",Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                //Toast.makeText(getActivity().getApplicationContext(),listDataHeader.get(groupPosition) + " Collapsed",Toast.LENGTH_SHORT).show();
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

        
        listDataHeader.add("No Power Complaints");
        listDataHeader.add("Meter Complaints");
        listDataHeader.add("Bill Complaints");
        listDataHeader.add("Reconnection/Disconnection");

        // Adding child data
        List<String> NoPowerComplaints = new ArrayList<String>();
        NoPowerComplaints.add("Subtype one");
        NoPowerComplaints.add("Subtype two");
        NoPowerComplaints.add("Subtype three");
        NoPowerComplaints.add("Subtype four");
        List<String> MeterComplaints = new ArrayList<String>();
        MeterComplaints.add("Subtype one");
        MeterComplaints.add("Subtype two");
        MeterComplaints.add("Subtype three");
        MeterComplaints.add("Subtype four");

        List<String> BillComplaints = new ArrayList<String>();
        BillComplaints.add("Subtype one");
        BillComplaints.add("Subtype two");
        BillComplaints.add("Subtype three");
        BillComplaints.add("Subtype four");


        List<String> Reconnection = new ArrayList<String>();
        Reconnection.add("Subtype one");
        Reconnection.add("Subtype two");
        Reconnection.add("Subtype three");
        Reconnection.add("Subtype four");

        listDataChild.put(listDataHeader.get(0), NoPowerComplaints); // Header, Child data
        listDataChild.put(listDataHeader.get(1), MeterComplaints);
        listDataChild.put(listDataHeader.get(2), BillComplaints);
        listDataChild.put(listDataHeader.get(3), Reconnection);

    }

    // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_raise__complaint, container, false);


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
