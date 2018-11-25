package com.project.nhom2.booking.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.project.nhom2.booking.R;

public class UserInformationActivity extends AppCompatActivity {

    TextView tv_name, tv_room, tv_userType, tv_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        init();
    }

    private void init(){
        tv_name = findViewById(R.id.tv_name);
        tv_room = findViewById(R.id.tv_room);
        tv_userType = findViewById(R.id.tv_userType);
        tv_score = findViewById(R.id.tv_score);
    }

    private void getValue() {
        
    }

}
