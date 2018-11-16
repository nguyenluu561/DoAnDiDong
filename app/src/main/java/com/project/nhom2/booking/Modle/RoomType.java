package com.project.nhom2.booking.Modle;

public class RoomType {
    private String IdRoomType;
    private String RoomTypeName;
    private String Quality;
    private float price;

    public RoomType() {
    }

    public RoomType(String idRoomType, String roomTypeName, String quality, float price) {
        IdRoomType = idRoomType;
        RoomTypeName = roomTypeName;
        Quality = quality;
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
        RoomTypeName = roomTypeName;
    }

    public String getQuality() {
        return Quality;
    }

    public void setQuality(String quality) {
        Quality = quality;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
