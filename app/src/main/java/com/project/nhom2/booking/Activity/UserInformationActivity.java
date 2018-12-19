package com.project.nhom2.booking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.project.nhom2.booking.Bom.UserBom;
import com.project.nhom2.booking.R;

public class UserInformationActivity extends AppCompatActivity {

    TextView tv_name, tv_userType, tv_score, tv_phonenumber;
    UserBom userBom;
    FloatingActionButton btnBack, btnLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        init();
    }

    private void init(){
        tv_name = findViewById(R.id.tv_name);
        tv_userType = findViewById(R.id.tv_userType);
        tv_score = findViewById(R.id.tv_score);
        tv_phonenumber = findViewById(R.id.tv_phonenumber);

        btnLogOut = findViewById(R.id.menu_logOut);
        btnBack = findViewById(R.id.menu_back);

        userBom = SignInActivity.userBom;

        tv_name.setText(userBom.getName());
        tv_userType.setText(getUser(userBom.getUserType()));
        tv_score.setText(String.valueOf(userBom.getHistory()));
        tv_phonenumber.setText(userBom.getPhoneNumber());

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(UserInformationActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        btnLogOut.setOnClickListener(v -> {
            Intent intent = new Intent(UserInformationActivity.this, SignInActivity.class);
            startActivity(intent);
        });
    }

    private String getUser(int x) {
        switch (x) {
            case 1: return "Thường";
            case 2: return "VIP";
            default: return "Nhân viên";
        }
    }

}
