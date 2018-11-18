package com.project.nhom2.booking.Entity;

public class BookRoom {
    private String IdBookRoom;
    private String IdCard;
    private String IdRoom;
    private String DateBook;
    private String DateBegin;
    private String DateEnd;
    private String Status;

    public BookRoom() {
    }

    public BookRoom(String idBookRoom, String idCard, String idRoom, String dateBook, String dateBegin, String dateEnd, String status) {
        IdBookRoom = idBookRoom;
        IdCard = idCard;
        IdRoom = idRoom;
        DateBook = dateBook;
        DateBegin = dateBegin;
        DateEnd = dateEnd;
        Status = status;
    }

    public String getIdBookRoom() {
        return IdBookRoom;
    }

    public void setIdBookRoom(String idBookRoom) {
        IdBookRoom = idBookRoom;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public String getIdRoom() {
        return IdRoom;
    }

    public void setIdRoom(String idRoom) {
        IdRoom = idRoom;
    }

    public String getDateBook() {
        return DateBook;
    }

    public void setDateBook(String dateBook) {
        DateBook = dateBook;
    }

    public String getDateBegin() {
        return DateBegin;
    }

    public void setDateBegin(String dateBegin) {
        DateBegin = dateBegin;
    }

    public String getDateEnd() {
        return DateEnd;
    }

    public void setDateEnd(String dateEnd) {
        DateEnd = dateEnd;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
