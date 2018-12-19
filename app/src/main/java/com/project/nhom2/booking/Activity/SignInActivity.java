package com.project.nhom2.booking.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.nhom2.booking.Bom.UserBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.PSFString;

import org.json.JSONException;
import org.json.JSONObject;


public class SignInActivity extends AppCompatActivity {

    EditText et_username, et_password;
    TextView tv_signup;
    String link;
    public static UserBom userBom;
    static int selection = 2;
    @SuppressLint("StaticFieldLeak")
    public static ProgressBar mProgressBar;
    public static int mDelay = 500;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();

    }

    private void init() {
        et_username = findViewById(R.id.input_username);
        et_password = findViewById(R.id.input_password);
        tv_signup = findViewById(R.id.link_signup);
        mProgressBar = findViewById(R.id.progressBar);

        final AppCompatButton btn_signin = findViewById(R.id.btn_login);

        userBom = new UserBom("a","b","c",1,0);

        btn_signin.setOnClickListener(v -> {
            if (!checkNull()) {
                getLink();
                new HttpGetTask().execute();
            } else
                Toast.makeText(getApplicationContext(), PSFString.NULL_INPUT, Toast.LENGTH_LONG).show();

        });
        tv_signup.setOnClickListener(v -> signUp());
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
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, link, null
                    , response -> {
                try {
                    responseHandler(response);
                    selection = 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }, error -> {
            });
            requestQueue.add(jsonObjectRequest);

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

            if (selection == 1) {
                Intent intent = new Intent(SignInActivity.this, SearchActivity.class);
                startActivity(intent);
            } else if (selection == 2) {
                Toast.makeText(getApplicationContext(), PSFString.FAILURE_RESULT, Toast.LENGTH_LONG).show();
            }

        }

        private void sleep() {
            try {
                Thread.sleep(mDelay);
            } catch (InterruptedException e) {
                Log.e("ngủ", e.toString());
            }
        }

        private void responseHandler(JSONObject response) throws JSONException {
            selection = 1;
            userBom.setCmnd(response.getString("Cmnd"));
            userBom.setName(response.getString("HoTen"));
            userBom.setPhoneNumber(response.getString("SoDT"));
            userBom.setUserType(response.getInt("LoaiUser"));

        }
    }


    private void signUp() {
        Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    private void getLink() {
        link = PSFString.SIGN_IN.concat(et_username.getText().toString())
                .concat(PSFString.PASSWORD_FIELD.concat(et_password.getText().toString()));
        Log.i("here is link", link);
    }

    private boolean checkNull() {
        return et_username.getText().length() == 0 || et_password.getText().length() == 0;
    }


}
