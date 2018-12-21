package com.project.nhom2.booking.Activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.PSFString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    ProgressBar mProgressBar;
    int mDelay = 500;
    Button btnReport;
    PieChart pieChart;
    Spinner sp_year, sp_filter;
    String link = "";

    public ArrayList<Entry> entries = new ArrayList<>();
    public ArrayList<String> label = new ArrayList<>();

    PieDataSet dataSet;
    PieData data;



    public static final int[] colors = {
            Color.rgb(192, 255, 140), Color.rgb(255, 247, 140)
            , Color.rgb(255, 208, 140), Color.rgb(140, 234, 255)
            , Color.rgb(255, 140, 157), Color.rgb(255, 102, 0)
            , Color.rgb(245, 199, 0), Color.rgb(106, 150, 31)
            , Color.rgb(179, 100, 53), Color.rgb(217, 80, 138)
            , Color.rgb(254, 149, 7), Color.rgb(254, 247, 120)
    };


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

        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(25f);
        pieChart.setHoleRadius(20f);
        pieChart.setDescription("");

        label.add("Tháng 1");
        label.add("Tháng 2");
        label.add("Tháng 3");
        label.add("Tháng 4");
        label.add("Tháng 5");
        label.add("Tháng 6");
        label.add("Tháng 7");
        label.add("Tháng 8");
        label.add("Tháng 9");
        label.add("Tháng 10");
        label.add("Tháng 11");
        label.add("Tháng 12");

        entries.add(new Entry(0,0));
        entries.add(new Entry(0,1));
        entries.add(new Entry(0,2));
        entries.add(new Entry(0,3));
        entries.add(new Entry(0,4));
        entries.add(new Entry(0,5));
        entries.add(new Entry(0,6));
        entries.add(new Entry(0,7));
        entries.add(new Entry(0,8));
        entries.add(new Entry(0,9));
        entries.add(new Entry(0,10));
        entries.add(new Entry(0,11));

        dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        data = new PieData(label,dataSet);
        data.setValueTextColor(Color.BLACK);
        pieChart.setData(data);
        pieChart.setDescription("");
        pieChart.setDrawHoleEnabled(true);
        pieChart.setTransparentCircleRadius(58f);

        pieChart.setHoleRadius(58f);
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        data.setValueTextSize(13f);
        data.setValueTextColor(Color.DKGRAY);
        pieChart.invalidate();


        ArrayAdapter<CharSequence> arr_year =
                ArrayAdapter.createFromResource(this, R.array.sp_year, R.layout.spinner_item);

        sp_year.setAdapter(arr_year);

        ArrayAdapter<CharSequence> arr_filter =
                ArrayAdapter.createFromResource(this, R.array.sp_filter, R.layout.spinner_item);

        sp_filter.setAdapter(arr_filter);

        pieChart = findViewById(R.id.piechart);

        btnReport.setOnClickListener(v -> {
            link = PSFString.REPORT
                    .concat(sp_year.getSelectedItem().toString());
            new HttpGetTask().execute();
        });

    }

    @SuppressLint("StaticFieldLeak")
    private class HttpGetTask extends AsyncTask<Void, Integer, String> {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {

            JsonArrayRequest  jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, "http://datphong12.000webhostapp.com/thongkehoadon.php?nam=2018", null
                    , response -> {
                try {
                    int[] money = new int[12];

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        money[jsonObject.getInt("Thang") - 1] += jsonObject.getInt("ThanhTien");
                    }

                    for (int m = 0; m < 12; m++) {
                        int x = 0;
                        if (money[m] != 0) {
                            entries.remove(x);
                            entries.add(new Entry(money[m], x));
                        }

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
            pieChart.setData(data);
        }

        private void sleep() {
            try {
                Thread.sleep(mDelay);
            } catch (InterruptedException e) {
                Log.e("ngủ", e.toString());
            }
        }

    }
}

