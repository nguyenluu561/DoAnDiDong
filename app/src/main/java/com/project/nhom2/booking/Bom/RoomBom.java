package com.project.nhom2.booking.Bom;

import android.os.Parcel;
import android.os.Parcelable;

//Parcelable để có thể gửi custom object bằng bundle
public class RoomBom implements Parcelable {
    private String id;
    private String bedtype;
    private String roomtype;
    private int price;

    public RoomBom (String id, String bedType, String roomType, int price) {
        this.id = id;
        this.bedtype = bedType;
        this.roomtype = roomType;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getBedtype() {
        return bedtype;
    }

    public void setBedtype(String bedtype) {
        this.bedtype = bedtype;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(String id) {

        this.id = id;
    }

    protected RoomBom(Parcel in) {
        this.id = in.readString();
        this.bedtype = in.readString();
        this.roomtype =(in.readString());
        this.price = in.readInt();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(this.bedtype);
        dest.writeString(this.roomtype);
        dest.writeInt(this.price);
    }
}
