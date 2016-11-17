package com.essel.smartutilities.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.NotificationCard;

import java.util.ArrayList;

/**
 * Created by hp on 10/10/2016.
 */
public class DropDownAdapter extends RecyclerView.Adapter<DropDownAdapter.DropDownHolder> {


    public Context mcontext;
    private ArrayList<Consumer> mConsumer;

    public DropDownAdapter() {
    }

    public DropDownAdapter(Context context, ArrayList<Consumer> consumerArrayList) {
        this.mcontext = context;
        this.mConsumer = consumerArrayList;

    }

    public DropDownAdapter(Context context) {
        this.mcontext = context;
    }

    @Override
    public DropDownHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_dropdown_account, null);
        DropDownHolder viewHolder = new DropDownHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DropDownHolder holder, int position) {
    }


    @Override
    public int getItemCount() {
        if (mConsumer != null && mConsumer.size() > 0)
            return mConsumer.size();
        else
            return 0;
    }

    public void setJobCard(ArrayList<Consumer> Consumer) {
        mConsumer = Consumer;
        notifyDataSetChanged();
    }


    public class DropDownHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl_dropdown_card;
        public TextView msg, date;

        public DropDownHolder(View itemView) {
            super(itemView);
            rl_dropdown_card = (RelativeLayout) itemView.findViewById(R.id.rl_dropdown_card);
            msg = (TextView) itemView.findViewById(R.id.tv_address);
            date = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}



