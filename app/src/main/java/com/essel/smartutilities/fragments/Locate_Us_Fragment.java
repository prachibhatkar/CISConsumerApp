package com.essel.smartutilities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.essel.smartutilities.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.

 * to handle interaction events.
 * Use the {@link Locate_Us_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Locate_Us_Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mMapView;
    private GoogleMap googleMap;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner sp_location;
    Context mContext;



    public Locate_Us_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Locate_Us_Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Locate_Us_Fragment newInstance(int param1, String param2) {
        Locate_Us_Fragment fragment = new Locate_Us_Fragment();
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

    //final FragmentController mFragments = FragmentController.createController(new HostCallbacks());


    private FragmentManager getSupportFragmentManager() {
        FragmentActivity mFragments = new FragmentActivity();
        return mFragments.getSupportFragmentManager();
    }


   /* public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng muzaffarpur = new LatLng(26.121473, 85.368752);
        mMap.addMarker(new MarkerOptions().position(muzaffarpur).title("Marker in Muzaffarpur"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(muzaffarpur));
    }*/
    // public void onActivityCreated(Bundle savedInstanceState) {
    //   super.onActivityCreated(savedInstanceState);
    //   FragmentManager fm = getChildFragmentManager();
    ///  SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    //  mapFragment.getMapAsync(this);
    // }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext=getActivity();

        View rootView = inflater.inflate(R.layout.fragment_locate__us, container, false);

        sp_location = (Spinner) rootView.findViewById(R.id.sp_location);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);


        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }


        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng all_csd = new LatLng(26.1114, 85.3897);
                googleMap.addMarker(new MarkerOptions().position(all_csd).title("Amgola Road").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(all_csd).zoom(10).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            }

        });

        String[] locations = mContext.getResources().getStringArray(R.array.CSD_Centers);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, Arrays.asList(locations));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_location.setAdapter(dataAdapter);

        sp_location.setOnItemSelectedListener(this);
        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch(position){

            case 0:
                LatLng all_csd = new LatLng(26.1114, 85.3897);
                googleMap.addMarker(new MarkerOptions().position(all_csd));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(all_csd).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                LatLng all_csd0 = new LatLng(26.1221, 85.3659);
                googleMap.addMarker(new MarkerOptions().position(all_csd0));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition0 = new CameraPosition.Builder().target(all_csd0).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition0));

                LatLng all_csd1 = new LatLng(26.1175964, 85.3977566);
                googleMap.addMarker(new MarkerOptions().position(all_csd1));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition1 = new CameraPosition.Builder().target(all_csd1).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition1));

                LatLng all_csd2 = new LatLng(26.1247527, 85.3994252);
                googleMap.addMarker(new MarkerOptions().position(all_csd1));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition2 = new CameraPosition.Builder().target(all_csd2).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition2));
                break;

            case 1:
                LatLng amgola_road = new LatLng(26.1114, 85.3897);
                googleMap.addMarker(new MarkerOptions().position(amgola_road).title("Amgola Road").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition3 = new CameraPosition.Builder().target(amgola_road).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition3));
                break;

            case 2:
                LatLng saraiganj = new LatLng(26.1221, 85.3659);
                googleMap.addMarker(new MarkerOptions().position(saraiganj).title("Saraiganj").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition4 = new CameraPosition.Builder().target(saraiganj).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition4));
                break;

            case 3:
                LatLng kachhi_sarai = new LatLng(26.1175964, 85.3977566);
                googleMap.addMarker(new MarkerOptions().position(kachhi_sarai).title("Kachhi Sarai").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition5 = new CameraPosition.Builder().target(kachhi_sarai).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition5));
                break;

            case 4:
                LatLng pakki_sarai = new LatLng(26.1247527, 85.3994252);
                googleMap.addMarker(new MarkerOptions().position(pakki_sarai).title("Pakki Sarai").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition6 = new CameraPosition.Builder().target(pakki_sarai).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition6));
                break;

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
