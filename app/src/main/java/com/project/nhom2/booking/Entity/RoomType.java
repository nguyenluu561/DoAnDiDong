package com.project.nhom2.booking.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class RoomType {
    private String IdRoomType;
    private String RoomTypeName;
    private String Quality;
    private int price;

}
