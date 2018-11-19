package com.project.nhom2.booking.Bom;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.project.nhom2.booking.Entity.RoomType;


//Parcelable để có thể gửi custom object bằng bundle
public class RoomBom implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private RoomType type;

    public RoomBom(String id, String bedType, String roomType, int price) {
        this.id = id;
        this.type.setRoomTypeName(bedType);
        this.type.setQuality(roomType);
        this.type.setPrice(price);
    }

    protected RoomBom(Parcel in) {
        id = in.readString();
        this.type.setRoomTypeName(in.readString());
        this.type.setQuality(in.readString());
        this.type.setPrice(in.readInt());
    }

    public static final Creator<RoomBom> CREATOR = new Creator<RoomBom>() {
        @Override
        public RoomBom createFromParcel(Parcel in) {
            return new RoomBom(in);
        }

        @Override
        public RoomBom[] newArray(int size) {
            return new RoomBom[size];
        }
    };

    public RoomType getType() {
        return this.getType();
    }

    public void setType(RoomType roomType) {
        this.type = roomType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(this.type.getRoomTypeName());
        dest.writeString(this.type.getQuality());
        dest.writeInt(this.type.getPrice());
    }
}
