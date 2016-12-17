package com.essel.smartutilities.adapter;

/**
 * Created by hp on 9/14/2016.
 */

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;

import java.util.ArrayList;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.Paymenthistoryholder> {

    public Context mcontext;
    private ArrayList<String> mPaymenthistory;
    private ArrayList<String> mPaymenthistorydates;
    private ArrayList<String> mPaymenthistoryreceiptno;
    private ManageAccountAdapter.OnRecycleItemClickListener listener;

    public PaymentHistoryAdapter() {
    }

    public PaymentHistoryAdapter(Context context, ArrayList<String> paymenthistory,ArrayList<String> paymenthistorydates) {
        this.mcontext = context;
        this.mPaymenthistory = paymenthistory;
        this.mPaymenthistorydates = paymenthistorydates;
       // this.mPaymenthistoryreceiptno = paymenthistoryreceiptno;
        Log.d("historyyyy",""+mPaymenthistory);
    }
   /* public PaymentHistoryAdapter(Context context, ArrayList<String> paymenthistory) {
        this.mcontext = context;
        this.mPaymenthistory = paymenthistory;

        Log.d("historyyyy",""+mPaymenthistory);
    }*/


   @Override
    public Paymenthistoryholder onCreateViewHolder(ViewGroup parent, int viewType) {
       Context context = parent.getContext();
       LayoutInflater inflater = LayoutInflater.from(context);
       View contactView = inflater.inflate(R.layout.item_paymenthistory, parent, false);
       Paymenthistoryholder viewHolder = new Paymenthistoryholder(contactView);

       return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PaymentHistoryAdapter.Paymenthistoryholder holder, final int position) {
        holder.tv_amount.setText(mPaymenthistory.get(position));
        holder.date.setText(mPaymenthistorydates.get(position));
//        holder.transactionid.setText(mPaymenthistoryreceiptno.get(position));



        // holder.tv_amount.setText(mPaymenthistory.get(position));
        // holder.date.setText(mPaymenthistorydates.get(position));
       // holder.date.setText(mPaymenthistory.get(position));
    }

    @Override
    public int getItemCount() {
        /*if (mPaymenthistory != null && mPaymenthistory.size() > 0)
            return mPaymenthistory.size();
        else
            return 0;*/
        return mPaymenthistory.size();
    }


    public class Paymenthistoryholder extends RecyclerView.ViewHolder {
        public TextView month, tv_transactionid, paytime, transactionid, tv_amount, date, paymode;
        private final CardView cardView;
        private ImageView icpaymode;

        public Paymenthistoryholder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv1);
            tv_transactionid = (TextView) itemView.findViewById(R.id.lbl_transationid);
            transactionid = (TextView) itemView.findViewById(R.id.transationid);
            tv_amount = (TextView) itemView.findViewById(R.id.amountpaid);
            date = (TextView) itemView.findViewById(R.id.tv_month);
        }

       /* public void bind(final PaymentHistoryAdapter.Paymenthistoryholder holder, final int position) {
            holder.tv_amount.setText(mPaymenthistory.get(position));
            holder.date.setText(mPaymenthistorydates.get(position));


        }*/
    }




}

