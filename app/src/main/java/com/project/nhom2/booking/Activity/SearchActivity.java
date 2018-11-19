package com.project.nhom2.booking.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Server.SOService;
import com.project.nhom2.booking.Util.ApiUtils;
import com.project.nhom2.booking.Util.CheckConnection;
import com.project.nhom2.booking.Util.StaticFinalString;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchActivity extends AppCompatActivity {

    String link;
    TextView CheckInDateDisplay;
    TextView CheckOutDateDisplay;
    Spinner sp_room_type;
    Spinner sp_bed_type;
    Button btn_search;

    ArrayList<RoomBom> arrRoom = new ArrayList<RoomBom>();


    private int checkInYear;
    private int checkInMonth;
    private int checkInDay;

    private int checkOutYear;
    private int checkOutMonth;
    private int checkOutDay;

    private String checkInDate;
    private String checkOutDate;

    static final int DATE_DIALOG_ID_FROM = 0;
    static final int DATE_DIALOG_ID_TO = 1;

    private SOService mService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Kiểm tra kết nối, nếu không có kết nối sẽ chạy else
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            init();
        } else {
            CheckConnection.showState(getApplicationContext(), StaticFinalString.INTERNET_STATE_NOTIFY);
            finish();
        }
    }

    //Khởi tạo các component
    private void init() {
        // Gán các component trên giao diện
        CheckInDateDisplay = findViewById(R.id.check_in_date_picker);
        CheckOutDateDisplay = findViewById(R.id.check_out_date_picker);

        sp_room_type = findViewById(R.id.sp_room_type);
        sp_bed_type = findViewById(R.id.sp_bed_type);

        btn_search = findViewById(R.id.btn_search);

        //Khởi tạo các mảng dữ liệu
        ArrayAdapter<CharSequence> arr_room_type = ArrayAdapter.createFromResource(this,
                R.array.sp_room_type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arr_bed_type = ArrayAdapter.createFromResource(this,
                R.array.sp_bed_type, android.R.layout.simple_spinner_item);

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

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searching(v);
            }
        });

        mService = ApiUtils.getSOService();
    }


    //Biến nhận xử lý sự kiện  khi ấn vào ngày đặt phòng
    private DatePickerDialog.OnDateSetListener checkInDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            int inMonth = configMonth(checkInMonth);
            checkInYear = year;
            checkInMonth = monthOfYear;
            checkInDay = dayOfMonth;
            updateDisplay(CheckInDateDisplay, checkInDay, checkInMonth, checkInYear);

            String checkInDate = checkInYear + "-" + inMonth + "-" + checkInDay;
        }
    };


    //Biến nhận xử lý sự kiện khi ấn vào ngày trả phòng
    private DatePickerDialog.OnDateSetListener checkOutDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            int outMonth = configMonth(checkOutMonth);
            checkOutYear = year;
            checkOutMonth = monthOfYear;
            checkOutDay = dayOfMonth;
            updateDisplay(CheckOutDateDisplay, checkOutDay, checkOutMonth, checkOutYear);

            String checkInDate = checkOutYear + "-" + outMonth + "-" + checkOutDay;
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

        //ngày nhỏ nhất và khoảng thời gian dài nhất có thể đặt phòng
        long checkInMinDate = System.currentTimeMillis();
        //cho phép đặt phòng sớm nhất là trước 3 tháng tính từ thời điểm hiện tại
        long checkInMaxDate = System.currentTimeMillis() + 7889238000L;

        //ngày nhỏ nhất và khoảng thời gian dài nhất có thể trả phòng
        //vì giá trị tháng trả về nhỏ hơn thực tế 1 đơn vị
        // nên cần cộng thêm số milisecond giây của 1 tháng và 2 ngày
        long checkOutMinDate =
                dateToMiliseconds(checkInDay, checkInMonth, checkInYear) + 2592000000L + 172800000;
        long checkOutMaxDate = checkInMaxDate;

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
                toDatePickerDialog.getDatePicker().setMaxDate(checkOutMaxDate);

                return toDatePickerDialog;
        }
        return null;
    }


    // chuyển mốc thời gian đặt phòng thành miliseconds
    private long dateToMiliseconds(int day, int month, int year) {

        GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gc.clear();
        gc.set(year, month - 1, day);
        return gc.getTimeInMillis();
    }

    //xử lý nút tìm
    public void searching(View view) {
        loadAnswers();
        if (arrRoom != null) {
            Intent intent = new Intent(SearchActivity.this, RoomListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("LISTROOM", arrRoom);
            intent.putExtra("BUNDLE", bundle);
            startActivity(intent);
        } else {
            Toast.makeText(SearchActivity.this, link, Toast.LENGTH_SHORT).show();
        }
    }

    public void loadAnswers() {
        mService.getAnswers(sp_bed_type.getSelectedItem().toString()
                , sp_room_type.getSelectedItem().toString()
                , checkInDate, checkOutDate).enqueue(new Callback<List<RoomBom>>() {
            @Override
            public void onResponse(@NonNull Call<List<RoomBom>> call, @NonNull retrofit2.Response<List<RoomBom>> response) {
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    String data = Objects.requireNonNull(response.body()).toString();
                    Log.i("Data", data);
                    if (data != null) {
                        Type listRoomBom = new TypeToken<ArrayList<RoomBom>>() {
                        }.getType();
                        arrRoom = gson.fromJson(data, listRoomBom);
                    } else {
                        Toast.makeText(getApplicationContext(),"DATA NULL"+link, Toast.LENGTH_LONG);
                    }
                } else {
                    int statusCode = response.code();
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<RoomBom>> call, @NonNull Throwable t) {

            }
        });
    }


//    //hàm lấy kết quả json, nếu có kết quả trả về true
//    public void getResult() {
//        String link = filterLink();
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest stringRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null) {
//                    try {
//                        for (int i = 0; i < response.length(); i++) {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            JSONObject jsonObject1 = jsonObject.getJSONObject("LoaiPhong");
//                            RoomBom room =
//                                    new RoomBom(jsonObject.getString("MaPhong"),
//                                            jsonObject1.getString("TenLoaiPhong"),
//                                            jsonObject1.getString("ChatLuong"),
//                                            jsonObject1.getInt("Gia"));
//                            arrRoom.add(room);
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }, new Response.ErrorListener()
//
//        {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//        requestQueue.add(stringRequest);
//    }

//    public String filterLink() {
//        int inMonth = configMonth(checkInMonth);
//        int outMonth = configMonth(checkOutMonth);
//
//        String checkInDate = checkInYear + "-" + inMonth + "-" + checkInDay;
//        String checkOutDate = checkOutYear + "-" + outMonth + "-" + checkOutDay;
//
//        return link = StaticFinalString.MAIN_LINK_FILTER_GET_ROOM
//                .concat(StaticFinalString.BED_TYPE_FILTER.concat(sp_bed_type.getSelectedItem().toString()))
//                .concat(StaticFinalString.ROOM_TYPE_FILTER.concat(sp_room_type.getSelectedItem().toString()))
//                .concat(StaticFinalString.CHECK_IN_FILTER.concat(checkInDate))
//                .concat(StaticFinalString.CHECK_OUT_FILTER.concat(checkOutDate));
//    }

    public int configMonth(int x) {
        return x < 12 ? (x + 1) : x;
    }

}
