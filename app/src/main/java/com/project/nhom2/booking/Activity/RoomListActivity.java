package com.project.nhom2.booking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

import com.project.nhom2.booking.Adapter.ViewListAdapter;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.CheckConnection;
import com.project.nhom2.booking.Util.StaticFinalString;

import java.util.ArrayList;


public class RoomListActivity extends AppCompatActivity {

    ListView lvRoom;
    FloatingActionButton btnFilter;
    ArrayList<RoomBom> arrRoom = new ArrayList<RoomBom>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            init();
            Intent intent = getIntent();
            Bundle bundle = intent.getBundleExtra("BUNDLE");
            arrRoom = bundle.getParcelableArrayList("LISTROOM");

            ViewListAdapter customAdapter
                    = new ViewListAdapter(this, R.layout.activity_room_list_row_item, arrRoom);

            lvRoom.setAdapter(customAdapter);

        } else {
            CheckConnection.showState(getApplicationContext(), StaticFinalString.INTERNET_STATE_NOTIFY);
            finish();
        }

    }

    //khởi tạo các component
    public void init() {
        btnFilter = findViewById(R.id.btn_filter);
        lvRoom = findViewById(R.id.lvRoom);
    }

    //hàm xử lý nút filter
    public void openFilter(View view) {
        super.onBackPressed();
    }

}
