package com.essel.smartutilities.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.models.NotificationCard;

import java.util.ArrayList;

/**
 * Created by hp on 10/10/2016.
 */
public class NotificationCardAdapter  extends RecyclerView.Adapter<NotificationCardAdapter.NotificationCardHolder> {


    public Context mcontext;
    private ArrayList<NotificationCard> mNotificationCard;
    public NotificationCardAdapter() {
    }

    public NotificationCardAdapter(Context context, ArrayList<NotificationCard> NotificationCards)
    {
        this.mcontext = context;
        this.mNotificationCard = NotificationCards;

    }

    public NotificationCardAdapter(Context context) {
        this.mcontext = context;
    }

    @Override
    public NotificationCardHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_notification_card, null);
        NotificationCardHolder viewHolder = new NotificationCardHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NotificationCardHolder holder, final int position)
    {
        final NotificationCard item = mNotificationCard.get(position);
        holder.date.setText(String.valueOf(item.date));
        holder.msg.setText(String.valueOf(item.message));
            }

    @Override
    public int getItemCount()
    {
        if(mNotificationCard != null && mNotificationCard.size() > 0)
            return mNotificationCard.size();
        else
            return 0;
    }

    public void setJobCard(ArrayList<NotificationCard> NotificationCards) {
        mNotificationCard=NotificationCards;
        notifyDataSetChanged();
    }



    public  class NotificationCardHolder extends RecyclerView.ViewHolder
    {
        public RelativeLayout rl_notification_card;
        public TextView msg,date;

        public NotificationCardHolder(View itemView)
        {
            super(itemView);
            rl_notification_card=(RelativeLayout)itemView.findViewById(R.id.rl_notification_card);
            msg = (TextView) itemView.findViewById(R.id.tv_address);
            date=(TextView)itemView.findViewById(R.id.tv_name);
        }
    }
}



