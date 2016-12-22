package com.essel.smartutilities.fragments;

import android.app.ProgressDialog;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.essel.smartutilities.R;
import com.essel.smartutilities.callers.ServiceCaller;
import com.essel.smartutilities.models.JsonResponse;
import com.essel.smartutilities.models.LocateCsd;
import com.essel.smartutilities.models.LocateUs;
import com.essel.smartutilities.models.ServiceType;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.AppConstants;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.webservice.WebRequests;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
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
public class Locate_Us_Fragment extends Fragment implements AdapterView.OnItemSelectedListener,ServiceCaller {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MapView mMapView;
    private GoogleMap googleMap;
    TextView tv_address,tv_address2,tv_address3,tv_address4;
   Double lat,lon;
    ArrayList<String>address1=new ArrayList<String>();
    ArrayList<String>city1=new ArrayList<String>();
    ArrayList<String>starttime1=new ArrayList<String>();
    ArrayList<String>endtime1=new ArrayList<String>();
    ArrayList<String>lat1=new ArrayList<String>();
    ArrayList<String>lon1=new ArrayList<String>();
    Geocoder geocoder;
    ProgressDialog pDialog;
    //List<Address> addresses;
    private List<Address> addresses;
    private List<Address> addresses1;
    private List<Address> addresses2;
    private List<Address> addresses3;
    String address="",city="",country="";
    private ArrayList<String> csdcenters;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner sp_location;
    Context mContext;
    LocateUs locateus1 = new LocateUs();



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


    private void initProgressDialog() {

        if (pDialog == null) {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
        }
    }

    private void dismissDialog() {
        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
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

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        tv_address=(TextView)rootView.findViewById(R.id.tv_address1);
        tv_address2=(TextView)rootView.findViewById(R.id.tv_address2);
        tv_address3=(TextView)rootView.findViewById(R.id.starttime);
        tv_address4=(TextView)rootView.findViewById(R.id.endtime);



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
                /* LatLng all_csd = new LatLng(20.5937, 78.9629);
                 googleMap.addMarker(new MarkerOptions().position(all_csd).title("Amgola Road").snippet("Marker Description"));

                 //For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(all_csd).zoom(10).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/

            }

        });

        csdcenters = new ArrayList<>(12);
      //  csdcenters.add(0,"Select CSD Centers");


        if (CommonUtils.isNetworkAvaliable(getActivity())) {
            initProgressDialog();
            if (pDialog != null && !pDialog.isShowing()) {
                pDialog.setMessage(" please wait..");
                pDialog.show();
            }
            JsonObjectRequest request = WebRequests.getLocateUs(getActivity(), Request.Method.GET, AppConstants.URL_GET_LOCATE_US, AppConstants.REQUEST_GET_LOCATE_US, this);
            App.getInstance().addToRequestQueue(request, AppConstants.REQUEST_GET_LOCATE_US);
        } else

            Toast.makeText(getActivity(), " Please Check  Internet Connection ", Toast.LENGTH_SHORT).show();




        sp_location = (Spinner) rootView.findViewById(R.id.sp_location);
       /* ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,csdcenters);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_location.setAdapter(dataAdapter);
        sp_location.setOnItemSelectedListener(this);*/


        //  String[] locations = mContext.getResources().getStringArray(R.array.CSD_Centers);

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
       // if(position!=0) {
          lat= Double.valueOf(lat1.get(position));
          lon= Double.valueOf(lon1.get(position));
        LatLng csd1 = new LatLng(lat,lon);
       // googleMap.addMarker(new MarkerOptions().position(csd1).title("").snippet("Marker Description"));
        googleMap.addMarker(new MarkerOptions().position(csd1)
                .title("")
                .snippet("")
                .draggable(true));

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition3 = new CameraPosition.Builder().target(csd1).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition3));
       // googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), 14));



            tv_address.setText(address1.get(position));
            tv_address2.setText(city1.get(position));
            tv_address3.setText(starttime1.get(position));
            tv_address4.setText(endtime1.get(position));

     //   }
        /*else{


            LatLng csd1 = new LatLng(20.5937, 76.9629);
            googleMap.addMarker(new MarkerOptions().position(csd1).title("").snippet("Marker Description"));

            // For zooming automatically to the location of the marker
            CameraPosition cameraPosition3 = new CameraPosition.Builder().target(csd1).zoom(10).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition3));






        }*/
        //Geocoder geocoder4 = new Geocoder(getContext(), Locale.getDefault());


     /*   try {
            addresses = geocoder4.getFromLocation(lat,lon, 1);
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

      /*  switch(position) {



                case 0:
                   if (CommonUtils.isNetworkAvaliable(getContext())) {
                        LatLng all_csd = new LatLng(lat,lon);
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
                    if (CommonUtils.isNetworkAvaliable(getContext())) {
                       LatLng csd1 = new LatLng(lat,lon);
                        googleMap.addMarker(new MarkerOptions().position(csd1).title("").snippet("Marker Description"));

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition3 = new CameraPosition.Builder().target(csd1).zoom(15).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition3));

                        Geocoder geocoder4 = new Geocoder(getContext(), Locale.getDefault());


                        try {
                            addresses = geocoder4.getFromLocation(lat,lon, 1);
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
                    if (CommonUtils.isNetworkAvaliable(getContext())) {
                        LatLng csd2 = new LatLng(lat,lon);
                        googleMap.addMarker(new MarkerOptions().position(csd2).title("").snippet("Marker Description"));

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition4 = new CameraPosition.Builder().target(csd2).zoom(15).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition4));

                        Geocoder geocoder1 = new Geocoder(getContext(), Locale.getDefault());

                        try {
                            addresses = geocoder1.getFromLocation(lat,lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
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
                    if (CommonUtils.isNetworkAvaliable(mContext)) {
                        LatLng csd3 = new LatLng(lat,lon);
                        googleMap.addMarker(new MarkerOptions().position(csd3).title("Kachhi Sarai").snippet("Marker Description"));

                        // For zooming automatically to the location of the marker
                        CameraPosition cameraPosition5 = new CameraPosition.Builder().target(csd3).zoom(15).build();
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

                case 4: if (CommonUtils.isNetworkAvaliable(mContext)) {
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

            }*/

        }






    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public void onAsyncSuccess(JsonResponse jsonResponse, String label) {
        switch (label) {
            case AppConstants.REQUEST_GET_LOCATE_US: {
                if (jsonResponse != null) {
                    if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.SUCCESS)) {


                        Log.i(label, "hygt " + jsonResponse);
                        // Log.i(label, "hyif " + jsonResponse.complaint_type);
                        if(jsonResponse.locateus.size()!=0)
                        {

                            for(int i = 1 ; i <= jsonResponse.locateus.size(); i++) {

                               /* if(jsonResponse.locateus.get(i-1).center_name==null){
                                    locateus1.setCenter_name(" \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\]");
                                    locateus1.setLatitude(jsonResponse.locateus.get(i-1).latitude.toString());
                                    locateus1.setLongitude(jsonResponse.locateus.get(i-1).longitude.toString());
                                    locateus1.setAddress(jsonResponse.locateus.get(i-1).address.toString());
                                    locateus1.setCity(jsonResponse.locateus.get(i-1).city.toString());
                                    locateus1.setStart_time(jsonResponse.locateus.get(i-1).start_time.toString());
                                    locateus1.setEnd_time(jsonResponse.locateus.get(i-1).end_time.toString());
                                    lat= Double.valueOf(locateus1.getLatitude());
                                    lon= Double.valueOf(locateus1.getLongitude());
                                } else
                                if(jsonResponse.locateus.get(i-1).address==null){
                                    locateus1.setAddress(" ");
                                    locateus1.setCenter_name(jsonResponse.locateus.get(i-1).center_name.toString());
                                    locateus1.setLatitude(jsonResponse.locateus.get(i-1).latitude.toString());
                                    locateus1.setLongitude(jsonResponse.locateus.get(i-1).longitude.toString());
                                   // locateus1.setAddress(jsonResponse.locateus.get(i-1).address.toString());
                                    locateus1.setCity(jsonResponse.locateus.get(i-1).city.toString());
                                    locateus1.setStart_time(jsonResponse.locateus.get(i-1).start_time.toString());
                                    locateus1.setEnd_time(jsonResponse.locateus.get(i-1).end_time.toString());
                                    lat= Double.valueOf(locateus1.getLatitude());
                                    lon= Double.valueOf(locateus1.getLongitude());
                                } else
                                if(jsonResponse.locateus.get(i-1).city==null){
                                    locateus1.setCity(" ");
                                    locateus1.setCenter_name(jsonResponse.locateus.get(i-1).center_name.toString());
                                    locateus1.setLatitude(jsonResponse.locateus.get(i-1).latitude.toString());
                                    locateus1.setLongitude(jsonResponse.locateus.get(i-1).longitude.toString());
                                    locateus1.setAddress(jsonResponse.locateus.get(i-1).address.toString());
                                    //locateus1.setCity(jsonResponse.locateus.get(i-1).city.toString());
                                    locateus1.setStart_time(jsonResponse.locateus.get(i-1).start_time.toString());
                                    locateus1.setEnd_time(jsonResponse.locateus.get(i-1).end_time.toString());
                                    lat= Double.valueOf(locateus1.getLatitude());
                                    lon= Double.valueOf(locateus1.getLongitude());
                                } else
                                if(jsonResponse.locateus.get(i-1).start_time==null){
                                    locateus1.setStart_time(" ");
                                    locateus1.setCenter_name(jsonResponse.locateus.get(i-1).center_name.toString());
                                    locateus1.setLatitude(jsonResponse.locateus.get(i-1).latitude.toString());
                                    locateus1.setLongitude(jsonResponse.locateus.get(i-1).longitude.toString());
                                    locateus1.setAddress(jsonResponse.locateus.get(i-1).address.toString());
                                    locateus1.setCity(jsonResponse.locateus.get(i-1).city.toString());
                                    //locateus1.setStart_time(jsonResponse.locateus.get(i-1).start_time.toString());
                                    locateus1.setEnd_time(jsonResponse.locateus.get(i-1).end_time.toString());
                                    lat= Double.valueOf(locateus1.getLatitude());
                                    lon= Double.valueOf(locateus1.getLongitude());
                                }else
                                if(jsonResponse.locateus.get(i-1).end_time==null){
                                    locateus1.setEnd_time(" ");
                                    locateus1.setCenter_name(jsonResponse.locateus.get(i-1).center_name.toString());
                                    locateus1.setLatitude(jsonResponse.locateus.get(i-1).latitude.toString());
                                    locateus1.setLongitude(jsonResponse.locateus.get(i-1).longitude.toString());
                                    locateus1.setAddress(jsonResponse.locateus.get(i-1).address.toString());
                                    locateus1.setCity(jsonResponse.locateus.get(i-1).city.toString());
                                    locateus1.setStart_time(jsonResponse.locateus.get(i-1).start_time.toString());
                                   // locateus1.setEnd_time(jsonResponse.locateus.get(i-1).end_time.toString());
                                    lat= Double.valueOf(locateus1.getLatitude());
                                    lon= Double.valueOf(locateus1.getLongitude());
                                 } else
                                if(jsonResponse.locateus.get(i-1).latitude==null){
                                    locateus1.setLatitude(" ");
                                    locateus1.setCenter_name(jsonResponse.locateus.get(i-1).center_name.toString());
                                   // locateus1.setLatitude(jsonResponse.locateus.get(i-1).latitude.toString());
                                    locateus1.setLongitude(jsonResponse.locateus.get(i-1).longitude.toString());
                                    locateus1.setAddress(jsonResponse.locateus.get(i-1).address.toString());
                                    locateus1.setCity(jsonResponse.locateus.get(i-1).city.toString());
                                    locateus1.setStart_time(jsonResponse.locateus.get(i-1).start_time.toString());
                                    locateus1.setEnd_time(jsonResponse.locateus.get(i-1).end_time.toString());
                                    lat= Double.valueOf(locateus1.getLatitude());
                                    lon= Double.valueOf(locateus1.getLongitude());
                                } else
                                if(jsonResponse.locateus.get(i-1).longitude==null){
                                    locateus1.setLongitude(" ");
                                    locateus1.setCenter_name(jsonResponse.locateus.get(i-1).center_name.toString());
                                    locateus1.setLatitude(jsonResponse.locateus.get(i-1).latitude.toString());
                                   // locateus1.setLongitude(jsonResponse.locateus.get(i-1).longitude.toString());
                                    locateus1.setAddress(jsonResponse.locateus.get(i-1).address.toString());
                                    locateus1.setCity(jsonResponse.locateus.get(i-1).city.toString());
                                    locateus1.setStart_time(jsonResponse.locateus.get(i-1).start_time.toString());
                                    locateus1.setEnd_time(jsonResponse.locateus.get(i-1).end_time.toString());
                                    lat= Double.valueOf(locateus1.getLatitude());
                                    lon= Double.valueOf(locateus1.getLongitude());
                                }*/

                                dismissDialog();
                               Log.i(label, "complainttype" + jsonResponse.locateus);
                                locateus1.setCenter_name(jsonResponse.locateus.get(i-1).center_name.toString()!=null?jsonResponse.locateus.get(i-1).center_name.toString():" ");
                                locateus1.setLatitude(jsonResponse.locateus.get(i-1).latitude.toString()!=null?jsonResponse.locateus.get(i-1).latitude.toString():" ");
                                locateus1.setLongitude(jsonResponse.locateus.get(i-1).longitude.toString()!=null?jsonResponse.locateus.get(i-1).longitude.toString(): " ");
                                locateus1.setAddress(jsonResponse.locateus.get(i-1).address.toString()!=null?jsonResponse.locateus.get(i-1).address.toString():" ");
                                locateus1.setCity(jsonResponse.locateus.get(i-1).city.toString()!=null?jsonResponse.locateus.get(i-1).city.toString():" ");
                                locateus1.setStart_time(jsonResponse.locateus.get(i-1).start_time.toString()!=null?jsonResponse.locateus.get(i-1).start_time.toString():" 00.00");
                                locateus1.setEnd_time(jsonResponse.locateus.get(i-1).end_time.toString()!=null?jsonResponse.locateus.get(i-1).end_time.toString():"00.00");
                                lat= Double.valueOf(locateus1.getLatitude());
                                lon= Double.valueOf(locateus1.getLongitude());
                              //  locateus.setCity(jsonResponse.locateus.get(i-1).getCity().toString().trim());
                             //   locateus.setLatitude(jsonResponse.locateus.get(i-1).getLatitude().toString());
                              //  locateus.setLongitude(jsonResponse.locateus.get(i-1).getLongitude().toString());
                                   address1.add(locateus1.address);
                                   city1.add(locateus1.city);
                                   starttime1.add(locateus1.start_time);
                                  endtime1.add(locateus1.end_time);
                                  lat1.add(locateus1.latitude);
                                  lon1.add(locateus1.longitude);
                                 csdcenters.add(i-1,locateus1.getCenter_name());
                                Log.i(label, "complainttype" + jsonResponse.locateus);

                            }

                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item,csdcenters);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_location.setAdapter(dataAdapter);
                            sp_location.setOnItemSelectedListener(this);




                        }


                        if (jsonResponse.authorization != null) {
                            dismissDialog();
                            CommonUtils.saveAuthToken(getActivity(), jsonResponse.authorization);
//                            Log.i(label, "Authorrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr:" + jsonResponse.authorization);
                        }
                    } else if (jsonResponse.result != null && jsonResponse.result.equals(JsonResponse.FAILURE)) {
                        dismissDialog();
                        Toast.makeText(getContext(), jsonResponse.message != null ? jsonResponse.message : "", Toast.LENGTH_LONG).show();

                    }
                    break;
                }
            }

        }

    }


    public void onBackPressed() {

        dismissDialog();
    }

    @Override
    public void onAsyncFail(String message, String label, NetworkResponse response) {
        switch (label) {
            case AppConstants.REQUEST_GET_LOCATE_US: {
                 dismissDialog();
//                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
//                Toast.makeText(mContext, ""+ response, Toast.LENGTH_LONG).show();
                Log.i(label, "locate" + message);
                Log.i(label, "locate......." + response);
            }
            break;


        }

    }
}
