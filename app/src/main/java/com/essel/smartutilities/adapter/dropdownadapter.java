package com.essel.smartutilities.adapter;

/**
 * Created by hp on 9/14/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.ActivityLoginLanding;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.utility.App;
import com.essel.smartutilities.utility.CommonUtils;
import com.essel.smartutilities.utility.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;


public class dropdownadapter extends RecyclerView.Adapter<dropdownadapter.ViewHolder> {

    private List<Consumer> mConsumers;
    // Store the context for easy access
    private Context mContext;
    private OnRecycleItemClickListener mListener;


    public dropdownadapter(Context context, ArrayList<Consumer> consumers) {
        mConsumers = consumers;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.cell_dropdown_account, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.bind(mContext, mConsumers.get(position), mListener);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (v.getId() == R.id.cv) {
                    String temp = "Account of  " + mConsumers.get(position).consumer_name + "\n" +
                            "Consumer No. " + mConsumers.get(position).consumer_no + "  is  Selected ";
                    Toast.makeText(mContext, temp, Toast.LENGTH_LONG).show();
                    App.dropdown = true;
                    CommonUtils.saveDetails(mContext, mConsumers.get(position).consumer_no, mConsumers.get(position).consumer_name,
                            mConsumers.get(position).city);
                    mContext.startActivity(new Intent(mContext, ActivityLoginLanding.class));

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mConsumers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView name, id, address, acctype;
        private CardView cardView;
        private ImageView ic_dete;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            id = (TextView) itemView.findViewById(R.id.tv_consumerid);
            address = (TextView) itemView.findViewById(R.id.tv_address);
            ic_dete = (ImageView) itemView.findViewById(R.id.iv_pry);
            acctype = (TextView) itemView.findViewById(R.id.acctype);
        }

        public void bind(final Context context, final Consumer consumer, final OnRecycleItemClickListener listener) {

            name.setText(consumer.consumer_name);
            id.setText(consumer.consumer_no);
            address.setText(consumer.address);
            if (!SharedPrefManager.getStringValue(context, SharedPrefManager.CONSUMER_NO).isEmpty()) {
                if (consumer.consumer_no.equals(SharedPrefManager.getStringValue(context, SharedPrefManager.CONSUMER_NO)))
                    ic_dete.setVisibility(View.VISIBLE);
                else
                    ic_dete.setVisibility(View.GONE);
            }
            if (consumer.is_primary.equals("true")) {

                acctype.setText("(Primary)");
            } else {
                acctype.setText("");
            }

        }
    }

    public interface OnRecycleItemClickListener {
        void onItemClick(Consumer consumer);
    }
}

