package com.project.nhom2.booking.Bom;

import android.os.Parcel;
import android.os.Parcelable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
//Parcelable để có thể gửi custom object bằng bundle
public class RoomBom implements Parcelable {
    private String id;
    private String bedtype;
    private String roomtype;
    private int price;

    private RoomBom(Parcel in) {
        builder().id(in.readString())
                .bedtype(in.readString())
                .roomtype(in.readString())
                .price(in.readInt()).build();

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
        dest.writeString(this.id);
        dest.writeString(this.bedtype);
        dest.writeString(this.roomtype);
        dest.writeInt(this.price);
    }
}
