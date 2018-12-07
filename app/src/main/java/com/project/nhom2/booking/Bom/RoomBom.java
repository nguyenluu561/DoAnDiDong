package com.project.nhom2.booking.Bom;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
//Parcelable để có thể gửi custom object bằng bundle
public class RoomBom {
    private String id;
    private String bedtype;
    private String roomtype;
    private int price;


}
