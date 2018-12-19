package com.project.nhom2.booking.Adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.nhom2.booking.Activity.SearchActivity;
import com.project.nhom2.booking.Activity.SignInActivity;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.PSFString;
import com.project.nhom2.booking.Util.StringConfig;

import java.util.List;

import static com.project.nhom2.booking.Activity.SearchActivity.mDelay;
import static com.project.nhom2.booking.Activity.SearchActivity.mProgressBar;

public class RoomListAdapterSearchActivity extends ArrayAdapter<RoomBom> {

    private Context context;
    private int resource;
    private List<RoomBom> arrRoom;
    private String roomId;
    private String result = "";

    //khởi tạo mảng các row của danh sách phòng
    public RoomListAdapterSearchActivity(SearchActivity context, int resource, List<RoomBom> arrRoomBom) {
        super(context, resource, arrRoomBom);
        this.context = context;
        this.resource = resource;
        this.arrRoom = arrRoomBom;
    }

    //gán nội dung cho từng row item
    @SuppressLint("SetTextI18n")
    @TargetApi(Build.VERSION_CODES.N)
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.activity_room_list_row_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvPrice = convertView.findViewById(R.id.price);
            viewHolder.tvBedType = convertView.findViewById(R.id.bed_type);
            viewHolder.tvRoomType = convertView.findViewById(R.id.room_type);
            viewHolder.tvNumber = convertView.findViewById(R.id.room_numer);
            viewHolder.ivImage = convertView.findViewById(R.id.ivRoom);
            viewHolder.btnBook = convertView.findViewById(R.id.btn_book);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RoomBom roomBom = arrRoom.get(position);

        viewHolder.tvBedType.setText("Giường ".concat(StringConfig.configString_toSign(roomBom.getBedtype())));
        viewHolder.tvRoomType.setText("Phòng ".concat(StringConfig.configString_toSign(roomBom.getRoomtype())));
        viewHolder.tvPrice.setText("Giá ".concat(String.valueOf(roomBom.getPrice())));
        viewHolder.tvNumber.setText("Phòng ".concat(String.valueOf(roomBom.getId())));

        roomId = roomBom.getId();

        if (roomBom.getRoomtype().equals("thuong")) {
            viewHolder.ivImage.setImageResource(R.drawable.room_vip_1);
        } else {
            viewHolder.ivImage.setImageResource(R.drawable.room_vip_3);
        }

        if (SignInActivity.userBom.getUserType() == 10) {
            viewHolder.btnBook.setEnabled(false);
            viewHolder.btnBook.setClickable(false);
            viewHolder.btnBook.setFocusable(false);
        }

        viewHolder.btnBook.setOnClickListener((View v) -> new HttpGetTask().execute());
        return convertView;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    //khởi tạo các biến nắm nội dung hiển thị của một row
    public class ViewHolder {
        TextView tvBedType, tvRoomType, tvPrice, tvNumber;
        ImageView ivImage;
        Button btnBook;
    }

    public String getLink() {
        return PSFString.MAIN_LINK_FILTER_BOOK_ROOM
                .concat(PSFString.ID_FIELD.concat(SignInActivity.userBom.getCmnd()))
                .concat(PSFString.ROOM_ID_FILTER.concat(roomId))
                .concat(PSFString.CHECK_IN_FILTER.concat(SearchActivity.getCheckInDate()))
                .concat(PSFString.CHECK_OUT_FILTER.concat(SearchActivity.getCheckOutDate()));
    }

    @SuppressLint("StaticFieldLeak")
    private class HttpGetTask extends AsyncTask<Void, Integer, String> {
        RequestQueue requestQueue = Volley.newRequestQueue(context);


        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected String doInBackground(Void... params) {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET
                    , getLink(), null
                    , response -> result = response.toString(), error -> {
            });

            requestQueue.add(jsonObjectRequest);

            for (int x = 1; x < 11; x++) {
                sleep();
                publishProgress(x * 10);
            }

            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mProgressBar.setProgress(values[0]);
        }


        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String result) {
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            SearchActivity.customAdapter.notifyDataSetChanged();
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