package com.project.nhom2.booking.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.project.nhom2.booking.Adapter.RoomListAdapterSearchActivity;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.DateTime;
import com.project.nhom2.booking.Util.PSFString;
import com.project.nhom2.booking.Util.StringConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public static ProgressBar mProgressBar;
    public static int mDelay = 500;

    TextView CheckInDateDisplay, CheckOutDateDisplay;
    Spinner sp_room_type, sp_bed_type;
    ListView lvRoom;

    public static ArrayList<RoomBom> arrRoom = new ArrayList<>();

    @SuppressLint("StaticFieldLeak")
    public static RoomListAdapterSearchActivity customAdapter;

    FloatingActionButton btnUser, btnBill, btnLogOut;

    Button btn_search;

    public static String getCheckInDate() {
        return checkInDate;
    }

    public static String getCheckOutDate() {
        return checkOutDate;
    }

    private static String checkInDate;
    private static String checkOutDate;

    private int checkInYear;
    private int checkInMonth;
    private int checkInDay;

    private int checkOutYear;
    private int checkOutMonth;
    private int checkOutDay;

    //ngày nhỏ nhất và khoảng thời gian dài nhất có thể đặt phòng
    private long checkInMinDate = System.currentTimeMillis();
    //cho phép đặt phòng sớm nhất là trước 3 tháng tính từ thời điểm hiện tại
    private long checkInMaxDate = System.currentTimeMillis() + 7889238000L;

    //ngày nhỏ nhất có thể trả phòng
    long checkOutMinDate;

    static final int DATE_DIALOG_ID_FROM = 0;
    static final int DATE_DIALOG_ID_TO = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();

    }

    //Khởi tạo các component
    private void init() {

        // Gán các component trên giao diện
        CheckInDateDisplay = findViewById(R.id.check_in_date_picker);
        CheckOutDateDisplay = findViewById(R.id.check_out_date_picker);

        sp_room_type = findViewById(R.id.sp_room_type);
        sp_bed_type = findViewById(R.id.sp_bed_type);

        btn_search = findViewById(R.id.btn_search);
        btnUser = findViewById(R.id.menu_user);
        btnBill = findViewById(R.id.menu_bill);
        btnLogOut = findViewById(R.id.menu_logOut);

        lvRoom = findViewById(R.id.lvRoom);

        mProgressBar = findViewById(R.id.progressBar);

        //Khởi tạo các mảng dữ liệu
        ArrayAdapter<CharSequence> arr_room_type =
                ArrayAdapter.createFromResource(this, R.array.sp_room_type, R.layout.spinner_item);
        ArrayAdapter<CharSequence> arr_bed_type =
                ArrayAdapter.createFromResource(this, R.array.sp_bed_type, R.layout.spinner_item);

        //Gắn dữ liệu cho các spinner
        sp_room_type.setAdapter(arr_room_type);
        sp_bed_type.setAdapter(arr_bed_type);

        //Lấy ngày hiện tại
        final Calendar c = Calendar.getInstance();
        checkInYear = c.get(Calendar.YEAR);
        checkInMonth = c.get(Calendar.MONTH);
        checkInDay = c.get(Calendar.DAY_OF_MONTH);

        // HIển thị ngày hiện tại khi bắt đầu màn hình
        updateDisplay(CheckInDateDisplay, checkInDay, checkInMonth, checkInYear);
        updateDisplay(CheckOutDateDisplay, checkInDay, checkInMonth, checkInYear);

        customAdapter
                = new RoomListAdapterSearchActivity(this, R.layout.activity_room_list_row_item, arrRoom);
        customAdapter.notifyDataSetChanged();

        if (SignInActivity.userBom.getUserType() != 10) {
            btnBill.setEnabled(false);
            btnBill.setClickable(false);
            btnBill.setFocusable(false);
        } else {
            btnUser.setEnabled(false);
            btnUser.setClickable(false);
            btnUser.setFocusable(false);
        }

        btn_search.setOnClickListener(v -> new HttpGetTask().execute(1));

        btnBill.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, RoomOptionActivity.class);
            startActivity(intent);
        });

        btnUser.setOnClickListener(v -> new HttpGetTask().execute(1, 2));

        btnLogOut.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, SignInActivity.class);
            startActivity(intent);
        });


    }


    //Biến nhận xử lý sự kiện  khi ấn vào ngày đặt phòng
    private DatePickerDialog.OnDateSetListener checkInDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            checkInYear = year;
            checkInMonth = monthOfYear;
            checkInDay = dayOfMonth;
            setCheckOutMinDate();
            updateDisplay(CheckInDateDisplay, checkInDay, checkInMonth, checkInYear);
        }
    };


    //Biến nhận xử lý sự kiện khi ấn vào ngày trả phòng
    private DatePickerDialog.OnDateSetListener checkOutDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            checkOutYear = year;
            checkOutMonth = monthOfYear;
            checkOutDay = dayOfMonth;
            updateDisplay(CheckOutDateDisplay, checkOutDay, checkOutMonth, checkOutYear);
        }
    };

    //Hàm xử lý sự kiện khi nhấn ngày đặt phòng, được chỉ định ở file xml tương ứng
    @SuppressWarnings("deprecation")
    public void onClickCheckInDate(View v) {
        showDialog(DATE_DIALOG_ID_FROM);
    }

    //Hàm xử lý sự kiện khi nhấn ngày trả phòng, được chỉ định ở file xml tương ứng
    @SuppressWarnings("deprecation")
    public void onClickCheckOutDate(View v) {
        showDialog(DATE_DIALOG_ID_TO);
    }


    // Hiển thị ngày đã chọn lên textview tương ứng
    private void updateDisplay(TextView tv, int day, int month, int year) {
        tv.setText(new StringBuilder()
                .append(day).append("-").append(month + 1).append("-")
                .append(year).append(" "));
    }

    // Khởi tạo các ô chọn ngày và xác định các giới hạn
    @SuppressWarnings("deprecation")
    @Override
    protected Dialog onCreateDialog(int id) {
        setCheckOutMinDate();
        switch (id) {
            case DATE_DIALOG_ID_FROM:
                DatePickerDialog fromDatePickerDialog = new DatePickerDialog
                        (this, checkInDateSetListener, checkInYear, checkInMonth,
                                checkInDay);

                //giới hạn khoảng thời gian có thể đặt phòng
                fromDatePickerDialog.getDatePicker().setMinDate(checkInMinDate);
                fromDatePickerDialog.getDatePicker().setMaxDate(checkInMaxDate);

                return fromDatePickerDialog;

            case DATE_DIALOG_ID_TO:
                DatePickerDialog toDatePickerDialog = new DatePickerDialog
                        (this, checkOutDateSetListener, checkInYear, checkInMonth,
                                checkInDay);

                toDatePickerDialog.getDatePicker().setMinDate(checkOutMinDate);
                toDatePickerDialog.getDatePicker().setMaxDate(checkInMaxDate);

                return toDatePickerDialog;
        }
        return null;
    }

    public void setCheckOutMinDate() {
        //vì giá trị tháng trả về nhỏ hơn thực tế 1 đơn vị
        // nên cần cộng thêm số milisecond giây của 1 tháng và 2 ngày
        checkOutMinDate =
                DateTime.dateToMiliseconds(checkInDay, checkInMonth, checkInYear) + 2592000000L;
    }

    //Lấy link từ các thuộc tính lọc
    public String filterLink() {
        int inMonth = DateTime.configMonth(checkInMonth);
        int outMonth = DateTime.configMonth(checkOutMonth);

        checkInDate = checkInYear + "-" + inMonth + "-" + checkInDay;
        checkOutDate = checkOutYear + "-" + outMonth + "-" + checkOutDay;

        String link = PSFString.MAIN_LINK_FILTER_GET_ROOM
                .concat(PSFString
                        .BED_TYPE_FILTER.concat(StringConfig.configString_toNoSign(sp_bed_type.getSelectedItem().toString())))
                .concat(PSFString
                        .ROOM_TYPE_FILTER.concat(StringConfig.configString_toNoSign(sp_room_type.getSelectedItem().toString())))
                .concat(PSFString
                        .CHECK_IN_FILTER.concat(checkInDate))
                .concat(PSFString
                        .CHECK_OUT_FILTER.concat(checkOutDate));
        Log.i("here is link", link);
        return link;
    }

    @SuppressLint("StaticFieldLeak")
    private class HttpGetTask extends AsyncTask<Integer, Integer, String> {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String abc = "a";

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... params) {
            JsonArrayRequest jsonArrayRequest;
            if (params.length == 1) {
                arrRoom.clear();
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                        , filterLink(), null, response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("LoaiPhong");
                            RoomBom room = RoomBom.builder()
                                    .id(jsonObject.getString("MaPhong"))
                                    .bedtype(jsonObject1.getString("TenLoaiPhong"))
                                    .roomtype(jsonObject1.getString("ChatLuong"))
                                    .price(jsonObject1.getInt("Gia")).build();
                            arrRoom.add(room);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);


            } else {
                jsonArrayRequest = new JsonArrayRequest(Request.Method.GET
                        , PSFString.HISTORY.concat(SignInActivity.userBom.getCmnd())
                        , null, response -> {

                    SignInActivity.userBom.setHistory(response.length());
                    Log.i("history", String.valueOf(response.length()));

                }, Throwable::printStackTrace);

                abc = "b";
            }

            requestQueue.add(jsonArrayRequest);


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
            if (arrRoom.size() != 0) {
                lvRoom.setAdapter(customAdapter);
            } else if (abc.equals("b")) {
                Intent intent = new Intent(SearchActivity.this, UserInformationActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(getApplicationContext(), PSFString.NULL_RESULT, Toast.LENGTH_LONG).show();

        }

        private void sleep() {
            try {
                Thread.sleep(mDelay);
            } catch (InterruptedException e) {
                Log.e("ngủ", e.toString());
            }
        }

    }

}
