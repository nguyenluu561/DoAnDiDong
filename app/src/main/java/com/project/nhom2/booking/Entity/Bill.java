package com.project.nhom2.booking.Entity;


import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class Bill {
    private String IdBill;
    private String IdBookRoom;
    private String Discount;


}
