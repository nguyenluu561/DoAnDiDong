package com.project.nhom2.booking.Modle;

public class Bill {
    private String IdBill;
    private String IdBookRoom;
    private String Discount;

    public Bill() {
    }

    public Bill(String idBill, String idBookRoom, String discount) {
        IdBill = idBill;
        IdBookRoom = idBookRoom;
        Discount = discount;
    }

    public String getIdBill() {
        return IdBill;
    }

    public void setIdBill(String idBill) {
        IdBill = idBill;
    }

    public String getIdBookRoom() {
        return IdBookRoom;
    }

    public void setIdBookRoom(String idBookRoom) {
        IdBookRoom = idBookRoom;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }
}
