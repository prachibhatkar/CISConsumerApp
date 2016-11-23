package com.essel.smartutilities.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.GetComplaintIdActivity;
import com.essel.smartutilities.activity.ServiceStatusActivity;

import java.util.Arrays;
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

    private static final int CAPTURE_IMAGE=1;
    private int count = 0;
    ImageView iv;
    Spinner complainttype;
    Button btn_submitcomplaint;
    EditText complaint_remark;
    @Override
    public void onClick(View v) {

        if(v==iv){

        Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(photoCaptureIntent, CAPTURE_IMAGE);

    }

        if(v==btn_submitcomplaint) {
            if (isBlankInput()) {

                Intent i = new Intent(getActivity(),GetComplaintIdActivity.class);
                startActivity(i);


            }

        }
    }

    private boolean isBlankInput() {
        boolean b = true;
        String complaintremark = String.valueOf(complaint_remark.getText());
        if (complaintremark.equals("")){
            Toast.makeText(getContext(), "Enter Remark", Toast.LENGTH_LONG).show();

            b = false;
        }


        if (complainttype.getSelectedItemPosition()== 0) {
            Toast.makeText(getContext(), "Select complaint type", Toast.LENGTH_LONG).show();
            b = false;
        }
        return b;

    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
      /*  super.onActivityResult(requestCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        iv.setImageBitmap(bp);
        iv.setVisibility(View.VISIBLE);*/

       /* if (requestCode == CAPTURE_IMAGE) {
            if (resultCode == RESULT_OK) {
                Intent i = new Intent(Intent.ACTION_VIEW);

                i.setDataAndType(Uri.fromFile(output), "image/jpeg");
                startActivity(i);
                 Bitmap bp = (Bitmap) data.getExtras().get("data");
                iv.setImageBitmap(bp);
                iv.setVisibility(View.VISIBLE);

            }
        }*/


        if (requestCode == CAPTURE_IMAGE && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(photo);
            //storeCameraPhotoInSDCard(photo);
            //saveImageToStorage();
            iv.setVisibility(View.VISIBLE);
        }

        /*if (this.CAPTURE_IMAGE == requestCode && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");

            String partFilename = currentDateFormat();
            storeCameraPhotoInSDCard(bitmap, partFilename);

            // display the image from SD Card to ImageView Control
            String storeFilename = "photo_" + partFilename + ".jpg";
            Bitmap mBitmap = getImageFileFromSDCard(storeFilename);
            iv.setImageBitmap(mBitmap);

        }*/
    }




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

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

       // expListView = (ExpandableListView) rootView.findViewById(R.id.expListView);
       // linear_layout_imageview=(LinearLayout)rootView.findViewById(R.id.linear_layout_imageview);
        //img=(ImageView)rootView.findViewById(R.id.imgv_camera);
        iv = (ImageView)rootView.findViewById(R.id.iv_captured_image);
        iv.setOnClickListener(this);

        //img.setOnClickListener(this);
        btn_submitcomplaint=(Button)rootView.findViewById(R.id.btn_submitcomplaint);
        btn_submitcomplaint.setOnClickListener(this);

        complaint_remark=(EditText)rootView.findViewById(R.id.editremark);

        complainttype = (Spinner)rootView.findViewById(R.id.sp_complainttype);
        String[] type = mContext.getResources().getStringArray(R.array.complaints);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, Arrays.asList(type));
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        complainttype.setAdapter(dataAdapter);

        return rootView;
    }





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
