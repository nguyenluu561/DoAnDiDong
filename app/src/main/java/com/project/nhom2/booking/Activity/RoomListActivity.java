package com.project.nhom2.booking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.project.nhom2.booking.Adapter.RecyclerViewAdapter;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.CheckConnection;
import com.project.nhom2.booking.Util.StaticFinalString;

import java.util.ArrayList;


public class RoomListActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerViewAdapter mRcvAdapter;
    FloatingActionButton btnFilter;
    ArrayList<RoomBom> arrRoom = new ArrayList<RoomBom>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            init();
            getResult();
            createItemList();

        } else {
            CheckConnection.showState(getApplicationContext(), StaticFinalString.INTERNET_STATE_NOTIFY);
            finish();
        }

    }

    //khởi tạo các component
    public void init() {
        btnFilter = findViewById(R.id.btn_filter);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvRoom);
    }

    //hàm xử lý nút filter
    public void openFilter(View view) {
        super.onBackPressed();
    }

    //hàm nhận dữ liệu từ màn hình filter
    public void getResult() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        arrRoom = bundle.getParcelableArrayList("LISTROOM");
    }

    //hàm xử lý dự liệu gắn lên item list
    public void createItemList () {
        mRcvAdapter = new RecyclerViewAdapter(arrRoom);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mRcvAdapter);
    }

}
