package com.project.nhom2.booking.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class BookRoom {
    private String IdBookRoom;
    private String IdCard;
    private String IdRoom;
    private String DateBook;
    private String DateBegin;
    private String DateEnd;
    private String Status;


}
