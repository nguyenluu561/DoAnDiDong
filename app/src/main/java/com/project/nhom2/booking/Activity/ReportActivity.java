package com.project.nhom2.booking.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.T;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    PieChart pieChart;
    ArrayList<String> xValsBedType = new ArrayList<String>();
    ArrayList<String> xValsQualityType = new ArrayList<String>();
    ArrayList<String> xValsRoomType = new ArrayList<String>();
    ArrayList<T> yVals = new ArrayList<T>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        init();
    }

    private void init () {
        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        xValsBedType.add("Đơn");
        xValsBedType.add("Đôi");

        xValsQualityType.add("Thường");
        xValsQualityType.add("VIP");

        xValsRoomType.add("Thường - Đơn");
        xValsRoomType.add("Thường - Đôi");
        xValsRoomType.add("VIP - Đơn");
        xValsRoomType.add("VIP - Đôi");
    }


}
