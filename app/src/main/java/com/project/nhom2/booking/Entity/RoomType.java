package com.project.nhom2.booking.Entity;

public class RoomType {
    private String IdRoomType;
    private String RoomTypeName;
    private String Quality;
    private int price;


    public RoomType(String idRoomType, String roomTypeName, String quality, int price) {
        this.IdRoomType = idRoomType;
        this. RoomTypeName = roomTypeName;
        this.Quality = quality;
        this.price = price;
    }

    public String getIdRoomType() {
        return IdRoomType;
    }

    public void setIdRoomType(String idRoomType) {
        IdRoomType = idRoomType;
    }

    public String getRoomTypeName() {
        return RoomTypeName;
    }

    public void setRoomTypeName(String roomTypeName) {
        this.RoomTypeName = roomTypeName;
    }

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String quality) {
        Quality = quality;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
