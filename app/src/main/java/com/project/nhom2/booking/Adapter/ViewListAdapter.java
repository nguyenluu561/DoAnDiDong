package com.project.nhom2.booking.Adapter;

import android.content.Context;
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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.project.nhom2.booking.Activity.SearchActivity;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.StaticFinalString;

import org.json.JSONObject;

import java.util.List;

public class ViewListAdapter extends ArrayAdapter<RoomBom> {

    Context context;
    int resource;
    List<RoomBom> arrRoom;
    String roomid;

    //khởi tạo mảng các row của danh sách phòng
    public ViewListAdapter(Context context, int resource, List<RoomBom> arrRoomBom) {
        super(context, resource, arrRoomBom);
        this.context = context;
        this.resource = resource;
        this.arrRoom = arrRoomBom;
    }

    //gán nội dung cho từng row item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.activity_room_list_row_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.price);
            viewHolder.tvBedType = (TextView) convertView.findViewById(R.id.bed_type);
            viewHolder.tvRoomType = (TextView) convertView.findViewById(R.id.room_type);
            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivRoom);
            viewHolder.btnBook = (Button) convertView.findViewById(R.id.btn_book);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RoomBom roomBom = arrRoom.get(position);

        viewHolder.tvBedType.setText(roomBom.getBedtype());
        viewHolder.tvRoomType.setText(roomBom.getRoomtype());
        viewHolder.tvPrice.setText(String.valueOf(roomBom.getPrice()));
        roomid = roomBom.getId();
        viewHolder.ivImage.setImageResource(R.drawable.room_normal_1);
        viewHolder.btnBook.setOnClickListener(bookBtnListener);
        return convertView;
    }

    //khởi tạo các biến nắm nội dung hiển thị của một row
    public class ViewHolder {
        TextView tvBedType, tvRoomType, tvPrice;
        ImageView ivImage;
        Button btnBook;
    }

    private View.OnClickListener bookBtnListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String link = StaticFinalString.MAIN_LINK_FILTER_POST_ROOM
                    .concat(StaticFinalString.ROOM_ID_FILTER.concat(roomid))
                    .concat(StaticFinalString.CHECK_IN_FILTER.concat(SearchActivity.getCheckInDate()))
                    .concat(StaticFinalString.CHECK_OUT_FILTER.concat(SearchActivity.getCheckOutDate()));
            RequestQueue rq = Volley.newRequestQueue(context);
            JSONObject params = new JSONObject();

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    link, params, //Not null.
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(context, StaticFinalString.SUCCESS_RESULT, Toast.LENGTH_LONG);
                            // pDialog.hide();
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, StaticFinalString.FAILURE_RESULT, Toast.LENGTH_LONG);
                }
            });
            Toast.makeText(context,"link:"+link,Toast.LENGTH_LONG);
            rq.add(jsonObjReq);
        }
    };
}