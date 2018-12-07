package com.project.nhom2.booking.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.nhom2.booking.Bom.UserBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.CheckConnection;
import com.project.nhom2.booking.Util.StaticFinalString;

import org.json.JSONException;
import org.json.JSONObject;


public class SignInActivity extends AppCompatActivity {

    EditText et_username, et_password;
    TextView tv_signup;
    String link;
    public static UserBom userBom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            init();
        } else {
            CheckConnection.showState(getApplicationContext(), StaticFinalString.INTERNET_STATE_NOTIFY);
            finish();
        }

    }

    private void init() {
        et_username = findViewById(R.id.input_username);
        et_password = findViewById(R.id.input_password);
        tv_signup = findViewById(R.id.link_signup);
        final AppCompatButton btn_signin = findViewById(R.id.btn_login);

        btn_signin.setOnClickListener(v -> {
            if (!checkNull()) {
                getLink();
                new HttpGetTask().execute();
            } else
                Toast.makeText(getApplicationContext(), StaticFinalString.NULL_INPUT, Toast.LENGTH_LONG).show();

        });
        tv_signup.setOnClickListener(v -> signUp());
    }

    @SuppressLint("StaticFieldLeak")
    private class HttpGetTask extends AsyncTask<Void, Void, String> {
        int selection = 2;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String result = "abc";

        @Override
        protected String doInBackground(Void... params) {
            requestQueue.start();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null
                    , response -> {
                responseHandler(response);
                result = result.concat(response.toString());
            }, error -> {
            });
            requestQueue.add(jsonObjectRequest);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (selection == 1) {
                Intent intent = new Intent(SignInActivity.this, SearchActivity.class);
                startActivity(intent);
            } else if (selection == 2) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            }

        }

        private void responseHandler(JSONObject response) {
            try {
                userBom = UserBom.builder()
                        .cmnd(response.getString("Cmnd"))
                        .name(response.getString("HoTen"))
                        .phoneNumber(response.getString("SoDT"))
                        .userType(response.getInt("LoaiUser")).build();
                selection = 1;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void signUp() {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void getLink() {
        link = StaticFinalString.SIGN_IN.concat(et_username.getText().toString())
                .concat(StaticFinalString.PASSWORD_FIELD.concat(et_password.getText().toString()));
        Log.i("here is link", link);
    }

    private boolean checkNull() {
        return et_username.getText().length() == 0 || et_password.getText().length() == 0;
    }


}
