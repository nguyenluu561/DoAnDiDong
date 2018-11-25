package com.project.nhom2.booking.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.project.nhom2.booking.R;

public class SignInActivity extends AppCompatActivity {

    EditText et_username, et_password;
    TextView tv_signup;
    AppCompatButton btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

        btn_signin.setOnClickListener(this::signIn);
        tv_signup.setOnClickListener(this::signUp);
    }

    private void init(){
        et_username = findViewById(R.id.input_username);
        et_password = findViewById(R.id.input_password);
        tv_signup = findViewById(R.id.link_signup);
        btn_signin = findViewById(R.id.btn_login);
    }

    private void signIn (View v) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getLink(), response -> {
            if (response != null) {

            }
        }, Throwable::printStackTrace);
//        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(5
//                ,DefaultRetryPolicy.DEFAULT_MAX_RETRIES
//                ,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
        Intent intent = new Intent(SignInActivity.this, SearchActivity.class);
        startActivity(intent);
    }

    private void signUp (View v) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(getLink(), response -> {
            if (response != null) {

            }
        }, Throwable::printStackTrace);
//        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(5
//                ,DefaultRetryPolicy.DEFAULT_MAX_RETRIES
//                ,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private String getLink(){
        return "test";
    }


}
