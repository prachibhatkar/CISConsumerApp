package com.bynry.cisconsumerapp.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bynry.cisconsumerapp.adapter.NotificationCardAdapter;
import com.bynry.cisconsumerapp.models.NotificationCard;
import com.bynry.cisconsumerapp.R;
import com.bynry.cisconsumerapp.db.DatabaseManager;

import java.util.ArrayList;

/**
 * Created by hp on 10/10/2016.
 */
public class NotificationActivity extends Activity implements View.OnClickListener
{

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Context mContext;
    private ArrayList<NotificationCard> notification;
    private String meter_reader_id;
    private TextView title;
    private ImageView imgBack;
    private Typeface regular;
    public int j=0;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mContext=this;
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        title=(TextView)findViewById(R.id.title_bar);
        int i= DatabaseManager.getcount(this,"false");

        title.setText("Notification(" +i+")");
        title.setOnClickListener(this);

        loadRecyclerView();
    }


    private void loadRecyclerView()
    {


        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
            setnotificationArrayList();
           NotificationCardAdapter adapter = new NotificationCardAdapter(mContext, DatabaseManager.getallNotfication(this));
        recyclerView.setAdapter(adapter);
    }

    private void setnotificationArrayList()
    {
        notification =new ArrayList<>();

            for (int i=0; i <20; i++)
            {
                NotificationCard mNotificationCard = new NotificationCard();
                mNotificationCard.message="hfg dsfhojd sdfsdnj hvjhv ikgg bg dbvjbv fdnldsj sfnsld sfd ";
                mNotificationCard.date="Aug 20";
                mNotificationCard.title="Power Cut "+i;
                mNotificationCard.is_readed="false";
                notification.add(mNotificationCard);
            }
        }


    @Override
    public void onClick(View v)
    {if (v == imgBack)
        {finish();
        }
//        if (v == title)
//        {setnotificationArrayList();
//            DatabaseManager.saveNotifications(this, notification);
//
//        }
    }
}
