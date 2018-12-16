package com.project.nhom2.booking.Activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.project.nhom2.booking.Adapter.RoomListAdapter;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.StaticFinalString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RoomOptionActivity extends AppCompatActivity {
    private ListView lvRoom;
    public static ProgressBar mProgressBar;
    RoomListAdapter roomListAdapter;
    ArrayList<RoomBom> arrRoom;
    String link1 = "";
    String link2 = "";
    Button btnOpt1, btnOpt2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_option);

        init();
    }

    private void init() {
        arrRoom = new ArrayList<>();
        roomListAdapter = new RoomListAdapter(this, R.layout.activity_room_list_row_item2, arrRoom);
        roomListAdapter.notifyDataSetChanged();
        lvRoom = findViewById(R.id.lv_Room);
        btnOpt1 = findViewById(R.id.btn_opt1);
        btnOpt2 = findViewById(R.id.btn_opt2);
        mProgressBar = findViewById(R.id.progressBar);

        btnOpt1.setOnClickListener(v -> new HttpGetTask().execute(1));
        btnOpt2.setOnClickListener(v -> new HttpGetTask().execute(1, 2));
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
            arrRoom.clear();

            if (params.length == 1) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                        , link1, null, response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            RoomBom room = RoomBom.builder()
                                    .id(jsonObject.getString("MaPhong")).build();
                            arrRoom.add(room);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);

                requestQueue.add(jsonArrayRequest);
            }

            else {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                        , link2, null, response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            RoomBom room = RoomBom.builder()
                                    .id(jsonObject.getString("MaPhong")).build();
                            arrRoom.add(room);
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
            if (arrRoom.size() != 0) {
                lvRoom.setAdapter(roomListAdapter);
            } else
                Toast.makeText(getApplicationContext(), StaticFinalString.FAILURE_RESULT, Toast.LENGTH_LONG).show();
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

