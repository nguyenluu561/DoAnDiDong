package com.project.nhom2.booking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;

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

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        RoomBom roomBom = arrRoom.get(position);

        viewHolder.tvBedType.setText(roomBom.getBedtype());
        viewHolder.tvRoomType.setText(roomBom.getRoomtype());
        viewHolder.tvPrice.setText(String.valueOf(roomBom.getPrice()));

        viewHolder.ivImage.setImageResource(R.drawable.room_normal_1);
        return convertView;
    }

    //khởi tạo các biến nắm nội dung hiển thị của một row
    public class ViewHolder {
        TextView tvBedType, tvRoomType, tvPrice;
        ImageView ivImage;
    }
}
