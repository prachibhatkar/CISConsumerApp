package com.essel.smartutilities.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.activity.NotificationActivity;
import com.essel.smartutilities.models.Consumer;
import com.essel.smartutilities.models.NotificationCard;

import java.util.ArrayList;

/**
 * Created by hp on 10/10/2016.
 */
public class NotificationCardAdapter extends RecyclerView.Adapter<NotificationCardAdapter.NotificationCardHolder> {


    public Context mcontext;
    private ArrayList<NotificationCard> mNotificationCard;
    private ManageAccountAdapter.OnRecycleItemClickListener listener;
    public NotificationCardAdapter() {
    }

    public NotificationCardAdapter(Context context, ArrayList<NotificationCard> NotificationCards) {
        this.mcontext = context;
        this.mNotificationCard = NotificationCards;

    }


    @Override
    public NotificationCardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_notification_card, null);
        NotificationCardHolder viewHolder = new NotificationCardHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final NotificationCardHolder holder, final int position) {
        holder.bind(mcontext, mNotificationCard.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (v.getId() == R.id.delete) {
                    mNotificationCard.remove(holder.getAdapterPosition());
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mNotificationCard != null && mNotificationCard.size() > 0)
            return mNotificationCard.size();
        else
            return 0;
    }




    public class NotificationCardHolder extends RecyclerView.ViewHolder {
        public RelativeLayout rl_notification_card;
        public TextView msg, date, title;
        public ImageView delete;

        public NotificationCardHolder(View itemView) {
            super(itemView);
            rl_notification_card = (RelativeLayout) itemView.findViewById(R.id.rl_notification_card);
            msg = (TextView) itemView.findViewById(R.id.tv_message);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            delete = (ImageView) itemView.findViewById(R.id.delete);
            title = (TextView) itemView.findViewById(R.id.tv_title);
        }

        public void bind(final Context context, final NotificationCard notificationCard) {
            msg.setText(notificationCard.message);
            date.setText(notificationCard.date);
            title.setText(notificationCard.title);

            rl_notification_card.setOnClickListener(new View.OnClickListener() {
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




