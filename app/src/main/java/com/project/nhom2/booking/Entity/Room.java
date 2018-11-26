package com.project.nhom2.booking.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Room {
    private String IdRoom;
    private String IdRoomType;
    private String Status;

}
