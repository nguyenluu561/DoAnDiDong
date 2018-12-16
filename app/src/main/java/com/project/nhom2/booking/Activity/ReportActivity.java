package com.project.nhom2.booking.Activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.project.nhom2.booking.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    static String link1 = "http://datphong12.000webhostapp.com/thongkehoadon.php?nam=2018&TenLoaiPhong=don";
    static String link2 = "http://datphong12.000webhostapp.com/thongkehoadon.php?nam=2018&ChatLuong=vip";
    static String link3 = "http://datphong12.000webhostapp.com/thongkehoadon.php?nam=2018";


    ProgressBar mProgressBar;
    int mDelay = 500;
    Button btnExe;
    PieChart pieChart;
    ArrayList<String> xValsBedType = new ArrayList<>();
    ArrayList<String> xValsQualityType = new ArrayList<>();
    ArrayList<String> xValsRoomType = new ArrayList<>();
    ArrayList<Integer> yVals = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        init();
    }

    private void init() {

        mProgressBar = findViewById(R.id.progressBar);

        btnExe = findViewById(R.id.btn_execute);


        pieChart = findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);

        xValsBedType.add("Đơn");
        xValsBedType.add("Đôi");

        xValsRoomType.add("Thường");
        xValsRoomType.add("VIP");

        xValsQualityType.add("Thường - Đơn");
        xValsQualityType.add("Thường - Đôi");
        xValsQualityType.add("VIP - Đơn");
        xValsQualityType.add("VIP - Đôi");

        btnExe.setOnClickListener(v -> new HttpGetTask().execute());


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

            requestQueue.start();
            JsonArrayRequest jsonObjectRequest1 = new JsonArrayRequest(Request.Method.GET, link1, null
                    , response -> {
                try {
                    int total = 0;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        total += jsonObject.getInt("ThanhTien");
                    }
                    yVals.add(total);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
            });

            JsonArrayRequest jsonObjectRequest2 = new JsonArrayRequest(Request.Method.GET, link2, null
                    , response -> {
                try {
                    int total = 0;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        total += jsonObject.getInt("ThanhTien");
                    }
                    yVals.add(total);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
            });

            JsonArrayRequest jsonObjectRequest3 = new JsonArrayRequest(Request.Method.GET, link3, null
                    , response -> {
                try {
                    int total = 0;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        total += jsonObject.getInt("ThanhTien");
                    }
                    yVals.add(total);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
            });
            requestQueue.add(jsonObjectRequest1);
            requestQueue.add(jsonObjectRequest2);
            requestQueue.add(jsonObjectRequest3);

            for (int x = 1; x < 11; x++) {
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
            //PieData data = new PieData(yVals, );
            //data.setValueFormatter(new PercentFormatter());
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

