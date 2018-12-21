package com.project.nhom2.booking.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.PSFString;
import com.project.nhom2.booking.Util.StringConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReportActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    int mDelay = 500;
    @SuppressLint("StaticFieldLeak")
    static Button btnReport;
    static PieChart pieChart;
    @SuppressLint("StaticFieldLeak")
    static Spinner sp_year, sp_filter;
    private static String link = "";
    public static List<PieEntry> entries = new ArrayList<>();
    static PieDataSet dataSet;
    static PieData data;
    static ArrayList<Integer> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        init();
    }

    private void init() {

        mProgressBar = findViewById(R.id.progressBar);
        sp_year = findViewById(R.id.sp_year);
        sp_filter = findViewById(R.id.sp_filter);
        btnReport = findViewById(R.id.btn_report);
        pieChart = findViewById(R.id.piechart);

        ArrayAdapter<CharSequence> arr_year =
                ArrayAdapter.createFromResource(this, R.array.sp_year, R.layout.spinner_item);

        sp_year.setAdapter(arr_year);

        ArrayAdapter<CharSequence> arr_filter =
                ArrayAdapter.createFromResource(this, R.array.sp_filter, R.layout.spinner_item);

        sp_filter.setAdapter(arr_filter);

        btnReport.setOnClickListener(v -> {
            link = PSFString.REPORT
                    .concat(sp_year.getSelectedItem().toString());
            if (!StringConfig
                    .configString_toSign_Report(sp_filter.getSelectedItem().toString()).equals("tong")) {
                link = link
                        .concat(StringConfig.configString_toSign_Report(sp_filter.getSelectedItem().toString()));
            }
            Log.i("link report", link);
            new HttpGetTask().execute();
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class HttpGetTask extends AsyncTask<Void, Integer, String> {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
            pieChart.setVisibility(PieChart.GONE);
        }

        @Override
        protected String doInBackground(Void... params) {

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, link
                    , null, response -> {
                try {
                    int[] money = new int[12];
                    entries.clear();

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        money[jsonObject.getInt("Thang") - 1] += jsonObject.getInt("ThanhTien");
                    }

                    for (int m = 0; m < 12; m++) {
                        if (money[m] != 0)
                            entries.add(new PieEntry(money[m], configMonth(m)));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
            });

            requestQueue.add(jsonArrayRequest);

            for (int x = 0; x < 11; x++) {
                sleep();
                publishProgress(x * 10);
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            setUpData();
            setUpPieChart();
            pieChart.setVisibility(PieChart.VISIBLE);
        }

        private void sleep() {
            try {
                Thread.sleep(mDelay);
            } catch (InterruptedException e) {
                Log.e("ngủ", e.toString());
            }
        }

    }


    private void setUpPieChart() {

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        pieChart.setData(data);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(20f);

    }

    private void setUpData() {
        dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);


        data = new PieData(dataSet);
        data.setValueTextSize(20f);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);

        pieChart.highlightValues(null);

        pieChart.invalidate();

    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    private String configMonth(int x) {
        switch (x) {
            case 0:
                return "Tháng 1";
            case 1:
                return "Tháng 2";
            case 2:
                return "Tháng 3";
            case 3:
                return "Tháng 4";
            case 4:
                return "Tháng 5";
            case 5:
                return "Tháng 6";
            case 6:
                return "Tháng 7";
            case 7:
                return "Tháng 8";
            case 8:
                return "Tháng 9";
            case 9:
                return "Tháng 10";
            case 10:
                return "Tháng 11";
            default:
                return "Tháng 12";
        }
    }
}

