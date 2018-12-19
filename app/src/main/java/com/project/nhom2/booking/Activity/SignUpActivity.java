package com.project.nhom2.booking.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.PSFString;

public class SignUpActivity extends AppCompatActivity {

    EditText etPhoneNumber, etPassword, etName, etId;
    Button btnSignUP;
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

            init();
            btnSignUP.setOnClickListener(v -> {
                getLink();
                Log.i("here is link",link);
                new HttpGetTask().execute();
            });

    }

    private void init () {
        etPhoneNumber = findViewById(R.id.input_sdt);
        etPassword = findViewById(R.id.input_password);
        etName = findViewById(R.id.input_name);
        etId = findViewById(R.id.input_cmnd);
        btnSignUP = findViewById(R.id.btn_login);
    }

    public void getLink () {
        link = PSFString.SIGN_UP.concat(etId.getText().toString())
                .concat(PSFString.NAME_FIELD.concat(etName.getText().toString()))
                .concat(PSFString.PHONENUMBER_FIELD.concat(etPhoneNumber.getText().toString()))
                .concat(PSFString.PASSWORD_FIELD.concat(etPassword.getText().toString()));
    }

    @SuppressLint("StaticFieldLeak")
    private class HttpGetTask extends AsyncTask<Void, Void, String> {
        String result = null;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        @Override
        protected String doInBackground(Void... params) {
            requestQueue.add(new JsonObjectRequest(Request.Method.GET, link, null
                            , response -> {
                        result = response.toString();

                    }, error -> {
                    })
            );
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(),PSFString.SUCCESS_RESULT,Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        }
    }
}
