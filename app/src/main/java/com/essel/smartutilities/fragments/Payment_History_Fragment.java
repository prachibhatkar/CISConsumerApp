package com.essel.smartutilities.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.PaymentHistoryAdapter;
import com.essel.smartutilities.models.Consumer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Payment_History_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Payment_History_Fragment extends Fragment implements PaymentHistoryAdapter.OnRecycleItemClickListener {
    private static final String ARG_PAGE_NO = "page_no";
    private static final String ARG_PAGE_TITLE = "page_title";

    private int mPageNo;
    private String mPageTitle;
    private RecyclerView rv_consumers;
    private Context mContext;


    public Payment_History_Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page_no    Page Number.
     * @param page_title Page Title.
     * @return A new instance of fragment AssignedConsumerFragment.
     */
    public static Payment_History_Fragment newInstance(int page_no, String page_title) {
        Payment_History_Fragment fragment = new Payment_History_Fragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NO, page_no);
        args.putString(ARG_PAGE_TITLE, page_title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPageNo = getArguments().getInt(ARG_PAGE_NO);
            mPageTitle = getArguments().getString(ARG_PAGE_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_payment__history, container, false);
        setupUI(layout);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    private void loadData() {
        // Initialize contacts
        ArrayList<Consumer> consumers = Consumer.createConsumersList(10);
        // Create adapter passing in the sample user data
        PaymentHistoryAdapter adapter = new PaymentHistoryAdapter(mContext, consumers, this);
        // Attach the adapter to the recyclerview to populate items
        rv_consumers.setAdapter(adapter);
        // Set layout manager to position the items
        rv_consumers.setLayoutManager(new LinearLayoutManager(mContext));
        // That's all!
    }

    private void setupUI(View layout) {
        rv_consumers = (RecyclerView) layout.findViewById(R.id.rv_consumers);
    }

    public void onItemClick(Consumer consumer) {

    }
}


    //public void onItemClick(Consumer consumer) {
      //  DialogCreator.showConsumerDetailsDialog(mContext,consumer);
    //}
//}
