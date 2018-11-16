package com.project.nhom2.booking.Modle;

public class Room {
    private  String IdRoom;
    private String IdRoomType;
    private String Status;

    public Room() {
    }

    public Room(String idRoom, String idRoomType, String status) {
        IdRoom = idRoom;
        IdRoomType = idRoomType;
        Status = status;
    }

    public String getIdRoom() {
        return IdRoom;
    }

    public void setIdRoom(String idRoom) {
        IdRoom = idRoom;
    }

    public String getIdRoomType() {
        return IdRoomType;
    }

    public void setIdRoomType(String idRoomType) {
        IdRoomType = idRoomType;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
