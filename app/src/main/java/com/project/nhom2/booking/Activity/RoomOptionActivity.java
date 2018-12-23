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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.PSFString;
import com.project.nhom2.booking.Util.StringConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class RoomOptionActivity extends AppCompatActivity {
    @SuppressLint("StaticFieldLeak")
    public static ProgressBar mProgressBar;

    String linkGetRoom;
    String linkRequestRoom;

    EditText etRoomID, etCost;
    TextView tvRoomID, tvStatus, tvRoom, tvBed, tvPrice;
    Button btnInput, btnRequest;
    FloatingActionButton btnReport, btnBack, btnLogOut;
    LinearLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_option);

        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {

        tvRoomID = findViewById(R.id.idRoom);
        tvStatus = findViewById(R.id.status);
        tvBed = findViewById(R.id.bed_type);
        tvRoom = findViewById(R.id.room_type);
        tvPrice = findViewById(R.id.price);
        etRoomID = findViewById(R.id.input_roomId);
        etCost = findViewById(R.id.input_cost);
        btnInput = findViewById(R.id.btn_input);
        btnRequest = findViewById(R.id.btn_request);
        btnReport = findViewById(R.id.menu_report);
        btnLogOut = findViewById(R.id.menu_logOut);
        btnBack = findViewById(R.id.menu_back);
        mProgressBar = findViewById(R.id.progressBar);
        layout = findViewById(R.id.roomInfor);

        btnInput.setOnClickListener(v -> {
            btnRequest.setClickable(true);
            btnRequest.setFocusable(true);
            btnRequest.setEnabled(true);
            linkGetRoom = PSFString.GET_ONE_ROOM.concat(etRoomID.getText().toString());
            new HttpGetTask().execute(1);
        });

        btnRequest.setOnClickListener(v -> {

            if (btnRequest.getText().equals("Thanh toán")) {
                if (etRoomID.getText().toString().length() != 0) {
                    linkRequestRoom = PSFString.REQUEST_ROOM1
                            .concat(etRoomID.getText().toString())
                            .concat(PSFString.COST_FIELD)
                            .concat(etCost.getText().toString());
                } else
                    Toast.makeText(getApplicationContext(), PSFString.NULL_INPUT, Toast.LENGTH_LONG).show();

            } else {
                linkRequestRoom = PSFString.REQUEST_ROOM2
                        .concat(etRoomID.getText().toString());
            }

            new HttpGetTask().execute(1, 2);
        });

        btnReport.setOnClickListener(v -> {
            Intent intent = new Intent(RoomOptionActivity.this, ReportActivity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(RoomOptionActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        btnLogOut.setOnClickListener(v -> {
            Intent intent = new Intent(RoomOptionActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }

    @SuppressLint("StaticFieldLeak")
    private class HttpGetTask extends AsyncTask<Integer, Integer, String> {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest;
        int temp = 0;

        @Override
        protected void onPreExecute() {

            mProgressBar.setVisibility(ProgressBar.VISIBLE);
            layout.setVisibility(LinearLayout.GONE);
            tvPrice.setVisibility(TextView.GONE);
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected String doInBackground(Integer... params) {

            if (params.length == 1) {

                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                        , linkGetRoom, null, response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("LoaiPhong");
                            tvRoomID.setText(jsonObject.getString("MaPhong"));
                            tvStatus.setText(StringConfig.configStringStatus(jsonObject.getString("TrangThaiDatPhong")));
                            tvRoom.setText(StringConfig.configString_toSign(jsonObject1.getString("ChatLuong")));
                            tvBed.setText(StringConfig.configString_toSign(jsonObject1.getString("TenLoaiPhong")));

                            switch (jsonObject.getString("TrangThaiDatPhong")) {
                                case "da nhan":
                                    btnRequest.setText("Thanh toán");
                                    etCost.setVisibility(EditText.VISIBLE);
                                    break;
                                case "chua nhan":
                                    btnRequest.setText("Nhận phòng");
                                    break;
                                default:
                                    btnRequest.setText("Chưa đặt");
                                    btnRequest.setClickable(false);
                                    btnRequest.setFocusable(false);
                                    btnRequest.setEnabled(false);
                                    break;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

                temp = 1;

            } else {
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                        , linkRequestRoom, null, response -> {
                    try {
                        tvPrice.setText(String.valueOf(response.getInt(3)));
                        temp = 2;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

            }
            requestQueue.add(jsonArrayRequest);

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

            if (temp == 1) {
                layout.setVisibility(LinearLayout.VISIBLE);
                tvPrice.setVisibility(TextView.VISIBLE);
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

