package com.essel.smartutilities.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.essel.smartutilities.R;
import com.essel.smartutilities.utility.SharedPrefManager;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MyBillActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_billhistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_bill);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NO)!=null)
        getSupportActionBar().setTitle("My Bill "+ SharedPrefManager.getStringValue(this,SharedPrefManager.CONSUMER_NO));
        else
        getSupportActionBar().setTitle("My Bill");
        intialize();
    }

    public void intialize() {
        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.setTitle("My Consumtion (Units):Last Six Months");
        btn_billhistory = (Button) findViewById(R.id.btn_history);
        btn_billhistory.setOnClickListener(this);
        ImageView imgBack = (ImageView) findViewById(R.id.img_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 125),
                new DataPoint(1, 220),
                new DataPoint(2, 300),
                new DataPoint(3, 200),
                new DataPoint(4, 255),
                new DataPoint(5, 333)

        });

        series.setDrawDataPoints(true);
        series.setDataPointsRadius(8);
        series.setDrawBackground(false);
        graph.addSeries(series);


        BarGraphSeries<DataPoint> series1 = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 125),
                new DataPoint(1, 220),
                new DataPoint(2, 300),
                new DataPoint(3, 200),
                new DataPoint(4, 255),
                new DataPoint(5, 333)
        });
        graph.addSeries(series1);
        series1.setDrawValuesOnTop(true);
        series1.setColor(getResources().getColor(R.color.colorPrimaryDarkText));
        series1.setValuesOnTopColor(getResources().getColor(R.color.colorPrimaryDarkText));
        series1.setSpacing(120);

        // set manual labels on horizontal axis
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[]{"Jun", "Jul", "Aug", "Sep", "Oct", "Nov"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
        graph.getGridLabelRenderer().setVerticalLabelsVisible(false);
    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(this, BillHistoryActivity.class);
        startActivity(in);

    }
}
