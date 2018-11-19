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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;
import com.project.nhom2.booking.Util.StaticFinalString;

import java.util.List;

public class ViewListAdapter extends ArrayAdapter<RoomBom> {

    Context context;
    int resource;
    List<RoomBom> arrRoom;

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

        viewHolder.ivImage.setImageResource(R.drawable.room_normal_1);

        viewHolder.btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = StaticFinalString.MAIN_LINK_FILTER_POST_ROOM;

                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, link,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(context,));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextView.setText("That didn't work!");
                    }
                });
                queue.add(stringRequest);
            }
        });

        return convertView;
    }

    //khởi tạo các biến nắm nội dung hiển thị của một row
    public class ViewHolder {
        TextView tvBedType, tvRoomType, tvPrice;
        ImageView ivImage;
        Button btnBook;
    }
}
