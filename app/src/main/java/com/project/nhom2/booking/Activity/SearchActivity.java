package com.project.nhom2.booking.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.project.nhom2.booking.Adapter.ViewListAdapter;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.CheckConnection;
import com.project.nhom2.booking.Util.StaticFinalString;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class SearchActivity extends AppCompatActivity {

    String link;
    TextView CheckInDateDisplay;
    TextView CheckOutDateDisplay;
    Spinner sp_room_type;
    Spinner sp_bed_type;
    Button btn_search;
    ListView lvRoom;

    ArrayList<RoomBom> arrRoom = new ArrayList<>();

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

    static final int DATE_DIALOG_ID_FROM = 0;
    static final int DATE_DIALOG_ID_TO = 1;

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

        lvRoom = findViewById(R.id.lvRoom);

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

        btn_search.setOnClickListener(this::searching);
    }


    //Biến nhận xử lý sự kiện  khi ấn vào ngày đặt phòng
    private DatePickerDialog.OnDateSetListener checkInDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            checkInYear = year;
            checkInMonth = monthOfYear;
            checkInDay = dayOfMonth;
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

        //ngày nhỏ nhất và khoảng thời gian dài nhất có thể đặt phòng
        long checkInMinDate = System.currentTimeMillis();
        //cho phép đặt phòng sớm nhất là trước 3 tháng tính từ thời điểm hiện tại
        long checkInMaxDate = System.currentTimeMillis() + 7889238000L;

        //ngày nhỏ nhất và khoảng thời gian dài nhất có thể trả phòng
        //vì giá trị tháng trả về nhỏ hơn thực tế 1 đơn vị
        // nên cần cộng thêm số milisecond giây của 1 tháng và 2 ngày
        long checkOutMinDate =
                dateToMiliseconds(checkInDay, checkInMonth, checkInYear) + 2592000000L + 172800000;

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


    // chuyển mốc thời gian đặt phòng thành miliseconds
    private long dateToMiliseconds(int day, int month, int year) {

        GregorianCalendar gc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        gc.clear();
        gc.set(year, month - 1, day);
        return gc.getTimeInMillis();
    }

    //Lấy link từ các thuộc tính lọc
    public String filterLink() {
        int inMonth = configMonth(checkInMonth);
        int outMonth = configMonth(checkOutMonth);

        checkInDate = checkInYear + "-" + inMonth + "-" + checkInDay;
        checkOutDate = checkOutYear + "-" + outMonth + "-" + checkOutDay;

        return link = StaticFinalString.MAIN_LINK_FILTER_GET_ROOM
                .concat(StaticFinalString.BED_TYPE_FILTER.concat(sp_bed_type.getSelectedItem().toString()))
                .concat(StaticFinalString.ROOM_TYPE_FILTER.concat(sp_room_type.getSelectedItem().toString()))
                .concat(StaticFinalString.CHECK_IN_FILTER.concat(checkInDate))
                .concat(StaticFinalString.CHECK_OUT_FILTER.concat(checkOutDate));
    }

    public int configMonth(int x) {
        return x < 12 ? (x + 1) : x;
    }

    //xử lý nút tìm
    public void searching(View view) {
        getResult();
        if (arrRoom != null && arrRoom.size() != 0) {
            ViewListAdapter customAdapter
                    = new ViewListAdapter(this, R.layout.activity_room_list_row_item, arrRoom);
            lvRoom.setAdapter(customAdapter);
        } else {
            Toast.makeText(this, StaticFinalString.NULL_RESULT, Toast.LENGTH_SHORT).show();
        }
    }

    //hàm lấy kết quả json
    public void getResult() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(filterLink(), response -> {
            if (response != null) {
                arrRoom.clear();
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONObject jsonObject1 = jsonObject.getJSONObject("LoaiPhong");
                        RoomBom room =
                                new RoomBom(jsonObject.getString("MaPhong"),
                                        jsonObject1.getString("TenLoaiPhong"),
                                        jsonObject1.getString("ChatLuong"),
                                        jsonObject1.getInt("Gia"));
                        arrRoom.add(room);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, Throwable::printStackTrace);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy
                (20 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

}
