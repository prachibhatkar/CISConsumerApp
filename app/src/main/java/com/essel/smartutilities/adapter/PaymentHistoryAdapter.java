package com.essel.smartutilities.adapter;

/**
 * Created by hp on 9/14/2016.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.models.Consumer;

import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder>  {

    private List<Consumer> mConsumers;
    // Store the context for easy access
    private Context mContext;
    private OnRecycleItemClickListener mListener;

    // Pass in the contact array into the constructor
    public PaymentHistoryAdapter(Context context, List<Consumer> consumers, OnRecycleItemClickListener listener) {
        mConsumers = consumers;
        mContext = context;
        mListener=listener;
    }

    @Override
    public PaymentHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_paymenthistory, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PaymentHistoryAdapter.ViewHolder viewHolder, int position) {
        viewHolder.bind(mConsumers.get(position), mListener);
    }

    @Override
    public int getItemCount() {
        return mConsumers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tv_transactionid,tv_amount,tv_paymentmode;
        private final CardView cardView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.cv);
            tv_transactionid = (TextView) itemView.findViewById(R.id.tv_transactionid);
            tv_paymentmode = (TextView) itemView.findViewById(R.id.tv_paymentmode);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
        }

        public void bind(final Consumer consumer, final OnRecycleItemClickListener listener) {
            tv_transactionid.setText(consumer.transaction_id);
            tv_paymentmode.setText(consumer.payment_mode);
            tv_amount.setText(consumer.amount);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(consumer);
                }
            });
        }
    }

    public interface OnRecycleItemClickListener {
        void onItemClick(Consumer consumer);
    }
}

