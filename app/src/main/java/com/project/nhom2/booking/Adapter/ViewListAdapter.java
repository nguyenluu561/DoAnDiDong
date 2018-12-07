package com.project.nhom2.booking.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.nhom2.booking.Activity.SearchActivity;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.StaticFinalString;

import java.util.List;

public class ViewListAdapter extends ArrayAdapter<RoomBom> {

    private Context context;
    private int resource;
    private List<RoomBom> arrRoom;
    private String roomId;

    //khởi tạo mảng các row của danh sách phòng
    public ViewListAdapter(SearchActivity context, int resource, List<RoomBom> arrRoomBom) {
        super(context, resource, arrRoomBom);
        this.context = context;
        this.resource = resource;
        this.arrRoom = arrRoomBom;
    }

    //gán nội dung cho từng row item
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
            viewHolder.ivImage = convertView.findViewById(R.id.ivRoom);
            viewHolder.btnBook = convertView.findViewById(R.id.btn_book);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RoomBom roomBom = arrRoom.get(position);

        viewHolder.tvBedType.setText(roomBom.getBedtype());
        viewHolder.tvRoomType.setText(roomBom.getRoomtype());
        viewHolder.tvPrice.setText(String.valueOf(roomBom.getPrice()));
        roomId = roomBom.getId();
        viewHolder.ivImage.setImageResource(R.drawable.room_normal_1);
        viewHolder.btnBook.setOnClickListener(v -> new HttpGetTask().execute());
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
        TextView tvBedType, tvRoomType, tvPrice;
        ImageView ivImage;
        Button btnBook;
    }

    public String getLink() {
        return StaticFinalString.MAIN_LINK_FILTER_POST_ROOM
                .concat(StaticFinalString.ROOM_ID_FILTER.concat(roomId))
                .concat(StaticFinalString.CHECK_IN_FILTER.concat(SearchActivity.getCheckInDate()))
                .concat(StaticFinalString.CHECK_OUT_FILTER.concat(SearchActivity.getCheckOutDate()));
    }

    @SuppressLint("StaticFieldLeak")
    private class HttpGetTask extends AsyncTask<Void, Void, String> {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        @Override
        protected String doInBackground(Void... params) {
            @SuppressLint("ShowToast") StringRequest stringRequest = new StringRequest(Request.Method.GET, getLink(),
                    response -> Toast.makeText(context, response, Toast.LENGTH_LONG).show(),
                    error -> Toast.makeText(context, StaticFinalString.FAILURE_RESULT, Toast.LENGTH_LONG).show());
            requestQueue.add(stringRequest);
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }
}