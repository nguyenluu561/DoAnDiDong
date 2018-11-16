package com.project.nhom2.booking.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.nhom2.booking.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class SearchActivity extends AppCompatActivity {

    TextView CheckInDateDisplay;
    TextView CheckOutDateDisplay;
    Spinner sp_room_type;
    Spinner sp_bed_type;
    Spinner sp_floor;
    Spinner sp_no_beds;

    private int checkInYear;
    private int checkInMonth;
    private int checkInDay;

    static final int DATE_DIALOG_ID_FROM = 0;
    static final int DATE_DIALOG_ID_TO = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Gán các component trên giao diện
        CheckInDateDisplay = findViewById(R.id.check_in_date_picker);
        CheckOutDateDisplay = findViewById(R.id.check_out_date_picker);

        sp_room_type = findViewById(R.id.sp_room_type);
        sp_bed_type = findViewById(R.id.sp_bed_type);
        sp_no_beds = findViewById(R.id.sp_no_beds);
        sp_floor = findViewById(R.id.sp_floor);


        //Khởi tạo các mảng dữ liệu
        ArrayAdapter<CharSequence> arr_room_type = ArrayAdapter.createFromResource(this,
                R.array.sp_room_type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> arr_bed_type = ArrayAdapter.createFromResource(this,
                R.array.sp_bed_type, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> arr_floor = ArrayAdapter.createFromResource(this,
                R.array.sp_floor, android.R.layout.simple_spinner_item);
        final ArrayAdapter<CharSequence> arr_floor_vip = ArrayAdapter.createFromResource(this,
                R.array.sp_floor_vip, android.R.layout.simple_spinner_item);

        //Gắn dữ liệu cho các spinner
        sp_room_type.setAdapter(arr_room_type);
        sp_bed_type.setAdapter(arr_bed_type);
        sp_floor.setAdapter(arr_floor);
        sp_no_beds.setAdapter(arr_floor);

        //Lấy ngày hiện tại
        final Calendar c = Calendar.getInstance();
        checkInYear = c.get(Calendar.YEAR);
        checkInMonth = c.get(Calendar.MONTH);
        checkInDay = c.get(Calendar.DAY_OF_MONTH);


        // HIển thị ngày hiện tại khi bắt đầu màn hình
        updateDisplay(CheckInDateDisplay, checkInDay, checkInMonth, checkInYear);
        updateDisplay(CheckOutDateDisplay, checkInDay, checkInMonth, checkInYear);

        //Ràng buộc chọn lầu khi đã chọn phòng
        //phòng thường từ lầu 1 đến 4
        //vip từ 5 đến 8
        sp_floor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (sp_room_type.getSelectedItemId() == 1) {
                    sp_floor.setAdapter(arr_floor_vip);
                }

                if (sp_room_type.getSelectedItemId() == 0) {
                    sp_floor.setAdapter(arr_floor);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });


    }


    //Biến nhận xử lý sự kiện  khi ấn vào ngày đặt phòng
    private DatePickerDialog.OnDateSetListener checkInDateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
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
            updateDisplay(CheckOutDateDisplay, dayOfMonth, monthOfYear, year);
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
        // nên cần cộng thêm số milisecond giây của 1 tháng
        long checkOutMinDate =
                dateToMiliseconds(checkInDay,checkInMonth,checkInYear) + 2592000000L + 259200000;
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

}
