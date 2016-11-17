package com.essel.smartutilities.activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.adapter.NotificationCardAdapter;
import com.essel.smartutilities.models.NotificationCard;

import java.util.ArrayList;

/**
 * Created by hp on 10/10/2016.
 */
public class NotificationActivity extends Activity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Context mContext;
    private ArrayList<NotificationCard> notification;
    private String meter_reader_id;
    private TextView title;
    private ImageView imgBack;
    private Typeface regular;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mContext=this;
        imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(this);
        title=(TextView)findViewById(R.id.title_bar);
        title.setTypeface(regular);

        loadRecyclerView();
    }


    private void loadRecyclerView() {

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
       setnotificationArrayList();

        NotificationCardAdapter adapter = new NotificationCardAdapter(mContext,notification);
        recyclerView.setAdapter(adapter);
    }

    private void setnotificationArrayList() {

        notification=new ArrayList<>();
            NotificationCard mNotificationCard = new NotificationCard();
            for (int i = 0; i < 5; i++) {
                mNotificationCard.message="hfg dsfhojd sdfsdnj hvjhv ikgg bg dbvjbv fdnldsj sfnsld sfd ";
                mNotificationCard.date="Aug 20";
                mNotificationCard.title="Power Cut";
                notification.add(mNotificationCard);
            }
        }


    @Override
    public void onClick(View v) {
        if (v == imgBack) {
            finish();
        }

    }
}
