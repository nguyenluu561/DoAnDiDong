package com.project.nhom2.booking.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.PSFString;
import com.project.nhom2.booking.Util.StringConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class RoomOptionActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static ProgressBar mProgressBar;

    String linkGetRoom = PSFString.GET_ONE_ROOM;
    String linkRequestRoom = PSFString.REQUEST_ROOM;

    EditText etRoomID;
    TextView tvRoomID, tvStatus, tvRoom, tvBed;
    Button btnInput, btnRequest;
    FloatingActionButton btnReport;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_option);

        init();
    }

    private void init() {

        tvRoomID = findViewById(R.id.idRoom);
        tvStatus = findViewById(R.id.status);
        tvBed = findViewById(R.id.bed_type);
        tvRoom = findViewById(R.id.room_type);
        etRoomID = findViewById(R.id.input_roomId);
        btnInput = findViewById(R.id.btn_input);
        btnRequest = findViewById(R.id.btn_request);
        btnReport = findViewById(R.id.btn_report);
        mProgressBar = findViewById(R.id.progressBar);
        layout = findViewById(R.id.roomInfor);

        btnInput.setOnClickListener(v -> {
            linkGetRoom= linkGetRoom.concat(etRoomID.getText().toString());
            new HttpGetTask().execute(1);
        });

        btnRequest.setOnClickListener(v -> new HttpGetTask().execute(1, 2));

        btnReport.setOnClickListener(v -> {
            Intent intent = new Intent(RoomOptionActivity.this, ReportActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class HttpGetTask extends AsyncTask<Integer, Integer, String> {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... params) {

            if (params.length == 1) {

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                        , linkGetRoom, null, response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("LoaiPhong");
                            tvRoomID.setText(jsonObject.getString("MaPhong"));
                            tvStatus.setText(StringConfig.configStringStatus(jsonObject.getString("TrangThai")));
                            tvRoom.setText(StringConfig.configString_toSign(jsonObject1.getString("ChatLuong")));
                            tvBed.setText(StringConfig.configString_toSign(jsonObject1.getString("TenLoaiPhong")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

                requestQueue.add(jsonArrayRequest);
            } else {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                        , linkRequestRoom, null, response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            RoomBom room = RoomBom.builder()
                                    .id(jsonObject.getString("MaPhong")).build();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

                requestQueue.add(jsonArrayRequest);
            }


            for (int x = 1; x < 11; x++) {
                sleep();
                publishProgress(x * 10);
            }

            return "1";


        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {

            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            layout.setVisibility(LinearLayout.VISIBLE);

        }

        private String setTextBtnRequest (String text) {
            switch(text) {
                case "trong": return "Nhận phòng";
                default: return "Đang thuê";
            }
        }


    }

    private void sleep() {
        try {
            int mDelay = 500;
            Thread.sleep(mDelay);
        } catch (InterruptedException e) {
            Log.e("ngủ", e.toString());
        }
    }

}

