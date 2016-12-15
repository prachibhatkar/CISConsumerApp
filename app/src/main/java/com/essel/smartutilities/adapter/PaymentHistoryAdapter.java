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
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.PaymentHistoryActivity;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.PaymentHistory;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.ViewHolder> {

    private List<Consumer> mConsumers;
    private List<PaymentHistory> paymenthistory;
    // Store the context for easy access
    private Context mContext;
    private OnRecycleItemClickListener mListener;

    // Pass in the contact array into the constructor
    public PaymentHistoryAdapter(Context context, List<Consumer> consumers, OnRecycleItemClickListener listener) {
        mConsumers = consumers;
        mContext = context;
        mListener = listener;
    }

    public PaymentHistoryAdapter(Context mContext, ArrayList<PaymentHistory> paymenthis) {

        paymenthistory=paymenthis;
        mContext=mContext;



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
        viewHolder.bind(paymenthistory.get(position), mListener);
    }

    @Override
    public int getItemCount() {

        /*if (paymenthistory != null && paymenthistory.size() > 0)
            return paymenthistory.size();
        else
            return 0;*/

        return paymenthistory == null ? 0 : paymenthistory.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView month, tv_transactionid,paytime, transactionid, tv_amount, date, paymode;
        private final CardView cardView;
        private ImageView icpaymode;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
              cardView = (CardView) itemView.findViewById(R.id.cv);
              tv_transactionid = (TextView) itemView.findViewById(R.id.lbl_transationid);
              transactionid = (TextView) itemView.findViewById(R.id.transationid);
              tv_amount = (TextView) itemView.findViewById(R.id.amountpaid);
              date = (TextView) itemView.findViewById(R.id.tv_month);

//            paytime = (TextView) itemView.findViewById(R.id.tv_paymenttime);
//            paymode = (TextView) itemView.findViewById(R.id.lbl_paymentmode);
//            icpaymode = (ImageView) itemView.findViewById(R.id.iv_paymentmode);

        }

        public void bind(final PaymentHistory payment, final OnRecycleItemClickListener listener) {
             transactionid.setText(payment.receiptno);
             tv_amount.setText(payment.amount);
             date.setText(payment.date);
//            paymode.setText(consumer.payment_mode);
//            paytime.setText(consumer.payment_time);
//            if (consumer.payment_mode.equals("cash"))
//                icpaymode.setBackgroundResource(R.drawable.camera);
//            else
//                icpaymode.setBackgroundResource(R.drawable.phone);


        }
    }

    public interface OnRecycleItemClickListener {
        void onItemClick(Consumer consumer);
    }
}

