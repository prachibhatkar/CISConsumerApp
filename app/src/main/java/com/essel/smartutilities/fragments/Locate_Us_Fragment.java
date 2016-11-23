package com.essel.smartutilities.fragments;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.CommonUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.logging.Handler;

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
    TextView tv_address,tv_address2,tv_address3;
    Geocoder geocoder;
    //List<Address> addresses;
    private List<Address> addresses;
    private List<Address> addresses1;
    private List<Address> addresses2;
    private List<Address> addresses3;
    String address="",city="",country="";

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

        tv_address=(TextView)rootView.findViewById(R.id.tv_address1);
        tv_address2=(TextView)rootView.findViewById(R.id.tv_address2);
        tv_address3=(TextView)rootView.findViewById(R.id.tv_address3);



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
        switch(position) {



                case 0:
                    if (CommonUtils.checkConnectivity(getContext())) {
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

                        tv_address.setText("");
                    }
                    else{
                        Toast.makeText(getContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();


                    }
                    break;

                case 1:
                    if (CommonUtils.checkConnectivity(getContext())) {
                        LatLng amgola_road = new LatLng(26.1114, 85.3897);
                        googleMap.addMarker(new MarkerOptions().position(amgola_road).title("Amgola Road").snippet("Marker Description"));

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition3 = new CameraPosition.Builder().target(amgola_road).zoom(15).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition3));

                        Geocoder geocoder4 = new Geocoder(getContext(), Locale.getDefault());


                        try {
                            addresses = geocoder4.getFromLocation(26.1114, 85.3897, 1);
                            // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                        tv_address.setText(address + " \n" + city + " \n" + state + "\n " + country + "\n " + postalCode);
                    }
                    else {

                        Toast.makeText(getContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                    }




                    break;

                case 2:
                    if (CommonUtils.checkConnectivity(getContext())) {
                        LatLng saraiganj = new LatLng(26.1221, 85.3659);
                        googleMap.addMarker(new MarkerOptions().position(saraiganj).title("Saraiganj").snippet("Marker Description"));

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition4 = new CameraPosition.Builder().target(saraiganj).zoom(15).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition4));

                        Geocoder geocoder1 = new Geocoder(getContext(), Locale.getDefault());

                        try {
                            addresses = geocoder1.getFromLocation(26.1221, 85.3659, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String address1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city1 = addresses.get(0).getLocality();
                        String state1 = addresses.get(0).getAdminArea();
                        String country1 = addresses.get(0).getCountryName();
                        String postalCode1 = addresses.get(0).getPostalCode();
                        String knownName1 = addresses.get(0).getFeatureName(); // Only if available else return NULL
                        tv_address.setText(address1 + " \n" + city1 + "\n " + state1 + "\n " + country1 + " \n" + postalCode1);

                    }
                    else{
                        Toast.makeText(getContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                    }


                    break;

                case 3:
                    if (CommonUtils.checkConnectivity(mContext)) {
                        LatLng kachhi_sarai = new LatLng(26.1175964, 85.3977566);
                        googleMap.addMarker(new MarkerOptions().position(kachhi_sarai).title("Kachhi Sarai").snippet("Marker Description"));

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition5 = new CameraPosition.Builder().target(kachhi_sarai).zoom(15).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition5));

                        Geocoder geocoder2 = new Geocoder(getContext(), Locale.getDefault());

                        try {
                            addresses = geocoder2.getFromLocation(26.1175964, 85.3977566, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String address2 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city2 = addresses.get(0).getLocality();
                        String state2 = addresses.get(0).getAdminArea();
                        String country2 = addresses.get(0).getCountryName();
                        String postalCode2 = addresses.get(0).getPostalCode();
                        String knownName2 = addresses.get(0).getFeatureName(); // Only if available else return NULL
                        tv_address.setText(address2 + " \n" + city2 + "\n " + state2 + "\n " + country2 + "\n " + postalCode2);

                    }
                    else {

                        Toast.makeText(getContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                    }

                    break;

                case 4: if (CommonUtils.checkConnectivity(mContext)) {
                    LatLng pakki_sarai = new LatLng(26.1247527, 85.3994252);
                    googleMap.addMarker(new MarkerOptions().position(pakki_sarai).title("Pakki Sarai").snippet("Marker Description"));

                    // For zooming automatically to the location of the marker
                    CameraPosition cameraPosition6 = new CameraPosition.Builder().target(pakki_sarai).zoom(15).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition6));
                    Geocoder geocoder3 = new Geocoder(getContext(), Locale.getDefault());

                    try {
                        addresses = geocoder3.getFromLocation(26.1247527, 85.3994252, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    String address3 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    String city3 = addresses.get(0).getLocality();
                    String state3 = addresses.get(0).getAdminArea();
                    String country3 = addresses.get(0).getCountryName();
                    String postalCode3 = addresses.get(0).getPostalCode();
                    String knownName3 = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    tv_address.setText(address3 + " \n" + city3 + "\n" + state3 + " \n" + country3 + " \n" + postalCode3);
                }

                  else {
                    Toast.makeText(getContext(), "Please check Internet Connection", Toast.LENGTH_LONG).show();

                }

                    break;

            }

        }






    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}
