package com.essel.smartutilities.adapter;

/**
 * Created by hp on 9/14/2016.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.models.Consumer;

import java.util.ArrayList;
import java.util.List;

public class ManageAccountAdapter extends RecyclerView.Adapter<ManageAccountAdapter.ViewHolder> {

    private List<Consumer> mConsumers;
    // Store the context for easy access
    private Context mContext;
    private OnRecycleItemClickListener mListener;


    public ManageAccountAdapter(Context context, ArrayList<Consumer> consumers) {
        mConsumers = consumers;
        mContext = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.item_manage_account, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        viewHolder.bind(mContext, mConsumers.get(position), mListener);
        viewHolder.ic_dete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (v.getId() == R.id.ic_delete) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
                    builder1.setMessage("Are you sure you want to delete account"+mConsumers.get(position).consumer_id+"-"+
                    mConsumers.get(position).consumer_name);
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    mConsumers.remove(viewHolder.getAdapterPosition());
                                    notifyDataSetChanged();
                                    dialog.cancel();
                                    Snackbar snack = Snackbar.make(v, "Account Deleted", Snackbar.LENGTH_LONG);
                                    snack.show();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                    notifyDataSetChanged();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mConsumers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView name, id, address, acctype, netamt, date;
        private CardView cardView;
        private ImageView ic_dete;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.consumer_name);
            id = (TextView) itemView.findViewById(R.id.consumerid);
            address = (TextView) itemView.findViewById(R.id.address);
            acctype = (TextView) itemView.findViewById(R.id.acctype);
            netamt = (TextView) itemView.findViewById(R.id.netamt);
            date = (TextView) itemView.findViewById(R.id.duedate_date);
            ic_dete = (ImageView) itemView.findViewById(R.id.ic_delete);
        }

        public void bind(final Context context, final Consumer consumer, final OnRecycleItemClickListener listener) {

            name.setText(consumer.consumer_name);
            id.setText(consumer.consumer_id);
            address.setText(consumer.address);
            acctype.setText(consumer.acctype);
            netamt.setText(consumer.netamt);
            date.setText(consumer.duedate);
            if (consumer.acctype.equals("primary"))
                ic_dete.setVisibility(View.GONE);
            else
                ic_dete.setVisibility(View.VISIBLE);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public interface OnRecycleItemClickListener {
        void onItemClick(Consumer consumer);
    }
}

