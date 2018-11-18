package com.project.nhom2.booking.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.nhom2.booking.Bom.RoomBom;
import com.project.nhom2.booking.R;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private ArrayList<RoomBom> arrRoom = new ArrayList<RoomBom>();


    public RecyclerViewAdapter( ArrayList<RoomBom> arrRoom) {
        this.arrRoom = arrRoom;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.activity_room_list_row_item, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.tvRoomType.setText(arrRoom.get(position).getType().getQuality());
        holder.tvBedType.setText(arrRoom.get(position).getType().getRoomTypeName());
        holder.tvPrice.setText(arrRoom.get(position).getType().getPrice());
        holder.ivRoom.setImageResource(R.drawable.room_normal_1);
    }

    @Override
    public int getItemCount() {
        return arrRoom.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoomType;
        TextView tvBedType;
        TextView tvPrice;
        ImageView ivRoom;
        LinearLayout line;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            tvRoomType = (TextView) itemView.findViewById(R.id.room_type);
            tvBedType = (TextView) itemView.findViewById(R.id.bed_type);
            tvPrice = (TextView) itemView.findViewById(R.id.price);
            ivRoom = (ImageView) itemView.findViewById(R.id.ivRoom);
        }
    }

}
